package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.PatientMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.entity.UserPatientIntermediation;
import org.harvey.respiratory.server.service.HealthcareService;
import org.harvey.respiratory.server.service.PatientService;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.harvey.respiratory.server.util.RegexUtils;
import org.harvey.respiratory.server.util.identifier.IdentifierCardId;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:38
 * @see Patient
 * @see PatientMapper
 * @see PatientService
 */
@Service
@Slf4j
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Resource
    private UserPatientIntermediationService userPatientIntermediationService;
    @Resource
    private HealthcareService healthcareService;


    private static PatientService currentProxy() {
        return (PatientService) AopContext.currentProxy();
    }

    @Override
    public long registerPatientInformation(PatientDto patientDto, long currentUserId) {
        // TODO TEST
        // 先查询确认不存在
        String phone = patientDto.getPhone();
        if (!RegexUtils.isPhoneEffective(phone)) {
            throw new BadRequestException("错误的电话号码格式");
        }
        String identityCardId = patientDto.getIdentityCardId();
        IdentifierCardId cardId = IdentifierCardId.phase(identityCardId);
        if (cardId == null) {
            throw new BadRequestException("错误的身份证号格式");
        }
        if (!cardId.getBrithDate().equals(patientDto.getBirthDate())) {
            throw new BadRequestException("身份证号上的生日和提交的生日冲突");
        }
        // 查询是否存在记录
        Patient patientFromDb = selectByIdCard(phone);
        PatientService patientService = currentProxy();
        if (patientFromDb != null) {
            // 存在则添加关系, 检查医保
            return patientService.registerForExistPatient(patientFromDb, patientDto.buildHealthcare(), currentUserId);
        } else {
            // 不存在就保存
            // insert patient
            return patientService.registerForNotExistPatient(
                    patientDto.buildPatient(), patientDto.buildHealthcare(), currentUserId);
        }
    }

    @Override
    @Transactional
    public long registerForExistPatient(
            Patient patientFromDb, Healthcare healthcare, long currentUserId) {
        // 查询是否user-patient建立关系
        Long patientId = patientFromDb.getId();
        if (!userPatientIntermediationService.exist(currentUserId, patientId)) {
            // 如果没有建立关系, 需要建立关系
            userPatientIntermediationService.save(new UserPatientIntermediation(currentUserId, patientId));
        }
        // 查询是否有healthcare了;
        if (healthcare == null) {
            // 没有就采用老的医保
            return patientId;
        }
        if (patientFromDb.getHealthcareId() != null) {
            // 已经存在医保
            return patientId;
        }
        // 还没有医保, 就添加医保
        // 保存healthcare, 产生id
        healthcareService.register(healthcare);
        // 病患处的更新医保id
        Long healthcareId = healthcare.getHealthcareId();
        updateHealthcare(healthcareId, patientId);
        return patientId;
    }

    private void updateHealthcare(Long healthcareId, Long patientId) {
        boolean update = super.lambdaUpdate()
                .set(Patient::getHealthcareId, healthcareId)
                .eq(Patient::getId, patientId)
                .update();
        if (update) {
            log.debug("成功添加患者{}的医保号{}", patientId, healthcareId);
        } else {
            log.error("添加患者{}医保号{}失败, 患者可能不存在. ", patientId, healthcareId);
        }
    }


    @Override
    @Transactional
    public Long registerForNotExistPatient(Patient patient, Healthcare healthcare, long currentUserId) {
        // 保存病患实体
        boolean saved = super.save(patient);
        if (saved) {
            log.debug("成功登记患者记录");
        } else {
            log.error("登记患者失败, 未知原因");
        }
        // insert user-patient中间表
        Long patientId = patient.getId();
        userPatientIntermediationService.save(new UserPatientIntermediation(currentUserId, patientId));
        // insert health-care
        if (healthcare == null) {
            return patientId;
        }
        healthcareService.register(healthcare);
        // 病患处的更新医保id
        Long healthcareId = healthcare.getHealthcareId();
        updateHealthcare(healthcareId, patientId);
        return patientId;
    }

    @Override
    public Patient selectByIdCard(String identityCardId) {
        LambdaQueryChainWrapper<Patient> lambdaQuery = super.lambdaQuery();
        return lambdaQuery.eq(Patient::getIdentityCardId, identityCardId).one();
    }
}