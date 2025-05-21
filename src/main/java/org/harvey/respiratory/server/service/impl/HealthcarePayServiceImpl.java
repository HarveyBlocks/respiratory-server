package org.harvey.respiratory.server.service.impl;

import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.pojo.dto.PayDto;
import org.harvey.respiratory.server.pojo.dto.QueryBalanceDto;
import org.harvey.respiratory.server.pojo.dto.RechargeDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.VisitDoctor;
import org.harvey.respiratory.server.service.HealthcarePayService;
import org.harvey.respiratory.server.service.HealthcareService;
import org.harvey.respiratory.server.service.VisitDoctorService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-21 23:11
 */
@Service
public class HealthcarePayServiceImpl implements HealthcarePayService {
    @Resource
    private HealthcareService healthcareService;
    @Resource
    private VisitDoctorService visitDoctorService;

    @Override
    public int queryBalance(QueryBalanceDto queryBalanceDto) {
        Healthcare healthcare = healthcareService.query(queryBalanceDto);
        if (healthcare == null) {
            throw new ResourceNotFountException("未能找到医保资源");
        }
        return healthcare.getBalance();
    }

    private static HealthcarePayService currentProxy() {
        return (HealthcarePayService) AopContext.currentProxy();
    }

    @Override
    public int recharge(RechargeDto rechargeDto) {
        Integer amount = rechargeDto.getAmount();
        if (amount == null || amount <= 0) {
            throw new ResourceNotFountException("充值的金额必须是正数");
        }
        return currentProxy().updateBalance(rechargeDto.getQueryBalanceDto(), amount);
    }

    @Override
    public void pay(PayDto payDto) {
        VisitDoctor visitDoctor = visitDoctorService.queryById(payDto.getVisitId());
        if (visitDoctor == null) {
            throw new ResourceNotFountException("未能找到就诊单");
        }
        if (!visitDoctor.isInterviewed()) {
            throw new BadRequestException("目标就诊单还未完成就诊");
        }
        if (visitDoctor.isPaid()) {
            throw new BadRequestException("目标账单已经付款");
        }
        int totalExpense = visitDoctor.getTotalExpense();
        currentProxy().updateBalance(payDto.getQueryBalanceDto(), -totalExpense);
    }

    @Transactional
    @Override
    public int updateBalance(QueryBalanceDto queryBalanceDto, int deltaExpense) {
        Healthcare healthcare = healthcareService.query(queryBalanceDto);
        if (healthcare == null) {
            throw new ResourceNotFountException("未能找到医保资源");
        }
        int newBalance = healthcare.getBalance() + deltaExpense;
        if (newBalance < 0) {
            throw new BadRequestException("余额不足, 请先充值");
        }
        healthcareService.updateBalance(healthcare.getHealthcareId(), newBalance);
        return newBalance;
    }
}
