package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.HealthcareMapper;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.pojo.dto.QueryBalanceDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.service.HealthcareService;
import org.harvey.respiratory.server.service.PatientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 22:38
 * @see Healthcare
 * @see HealthcareMapper
 * @see HealthcareService
 */
@Slf4j
@Service
public class HealthcareServiceImpl extends ServiceImpl<HealthcareMapper, Healthcare> implements HealthcareService {
    @Resource
    private PatientService patientService;

    @Override
    public void register(Healthcare healthcare) {
        // 强制设置为0, 防止请求不好的东西
        healthcare.setBalance(0);
        boolean saved = this.save(healthcare);
        if (saved) {
            log.debug("成功添加医保号{}", healthcare.getHealthcareId());
        } else {
            log.error("添加医保号{}失败. ", healthcare.getHealthcareId());
        }
    }

    @Override
    public Healthcare queryByCode(long healthcareCode) {
        return super.lambdaQuery().eq(Healthcare::getHealthcareCode, healthcareCode).one();
    }

    @Override
    public Healthcare queryById(long healthcareId) {
        Healthcare healthcare = super.getById(healthcareId);
        if (healthcare == null) {
            throw new ResourceNotFountException("依据医保id " + healthcareId + " 未查询到医保");
        }
        return healthcare;
    }

    @Override
    public Healthcare query(QueryBalanceDto queryBalanceDto) {
        Long healthcareId = queryBalanceDto.getHealthcareId();
        if (healthcareId != null) {
            Healthcare one = super.lambdaQuery().eq(Healthcare::getHealthcareId, healthcareId).one();
            if (one != null) {
                return one;
            }
        }
        String healthcareCode = queryBalanceDto.getHealthcareCode();
        if (healthcareCode != null && !healthcareCode.isEmpty()) {
            Healthcare one = super.lambdaQuery().eq(Healthcare::getHealthcareCode, healthcareCode).one();
            if (one != null) {
                return one;
            }
        }
        Long patientId = queryBalanceDto.getPatientId();
        if (patientId != null) {
            Patient patient = patientService.queryByIdSimply(patientId);
            if (patient != null) {
                if (patient.getHealthcareId() == null) {
                    return null;
                }
                return super.lambdaQuery().eq(Healthcare::getHealthcareId, patient.getHealthcareId()).one();
            }
        }
        String identifierCardId = queryBalanceDto.getIdentifierCardId();
        if (identifierCardId != null && !identifierCardId.isEmpty()) {
            Patient patient = patientService.queryByCardIdSimply(identifierCardId);
            if (patient != null) {
                if (patient.getHealthcareId() == null) {
                    return null;
                }
                return super.lambdaQuery().eq(Healthcare::getHealthcareId, patient.getHealthcareId()).one();
            }
        }
        return null;
    }

    @Override
    public void updateBalance(long healthcareId, int newBalance) {
        super.lambdaUpdate()
                .set(Healthcare::getBalance, newBalance)
                .eq(Healthcare::getHealthcareId, healthcareId)
                .update();
    }
}