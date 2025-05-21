package org.harvey.respiratory.server.service;


import org.harvey.respiratory.server.pojo.dto.PayDto;
import org.harvey.respiratory.server.pojo.dto.QueryBalanceDto;
import org.harvey.respiratory.server.pojo.dto.RechargeDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-21 23:11
 */
public interface HealthcarePayService {
    int queryBalance(QueryBalanceDto queryBalanceDto);

    int recharge(RechargeDto rechargeDto);

    void pay(PayDto payDto);

    /**
     * 事务, 避免脏读啥的
     *
     * @return 返回更新后的余额
     */
    @Transactional
    int updateBalance(QueryBalanceDto queryBalanceDto, int deltaExpense);
}
