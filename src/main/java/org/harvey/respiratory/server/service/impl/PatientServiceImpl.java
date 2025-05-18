package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.PatientMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.entity.UserPatientIntermediation;
import org.harvey.respiratory.server.service.HealthcareService;
import org.harvey.respiratory.server.service.PatientService;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.harvey.respiratory.server.util.RegexUtils;
import org.harvey.respiratory.server.util.identifier.IdentifierCardId;
import org.harvey.respiratory.server.util.identifier.IdentifierIdPredicate;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


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
        // 先查询确认不存在
        String phone = patientDto.getPhone();
        if (!RegexUtils.isPhoneEffective(phone)) {
            throw new BadRequestException("错误的电话号码格式");
        }
        String identityCardId = patientDto.getIdentityCardId();
        if (!identifierIdPredicate.test(identityCardId)) {
            throw new BadRequestException("错误的身份证号格式");
        }
        IdentifierCardId cardId = IdentifierCardId.phase(identityCardId);
        if (!cardId.getBrithDate().equals(patientDto.getBirthDate())) {
            throw new BadRequestException("身份证号上的生日和提交的生日冲突");
        }
        // 查询是否存在记录
        Patient patientFromDb = selectByIdCard(identityCardId);
        PatientService proxy = currentProxy();
        if (patientFromDb != null) {
            // 存在则添加关系, 检查医保
            return proxy.registerForExistPatient(patientFromDb, patientDto.buildHealthcare(), currentUserId);
        } else {
            // 不存在就保存
            // insert patient
            return proxy.registerForNotExistPatient(
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
            userPatientIntermediationService.register(currentUserId, patientId);
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
        boolean savedPatient = super.save(patient);
        if (savedPatient) {
            log.debug("成功登记患者记录");
        } else {
            log.error("登记患者失败, 未知原因");
        }
        // insert user-patient中间表
        // 数据库存入之后, 如果存中间表失败了, 还需要
        Long patientId = patient.getId();
        boolean savedIntermediation = userPatientIntermediationService.save(
                new UserPatientIntermediation(currentUserId, patientId));
        if (savedIntermediation) {
            log.debug("登记患者-用户连接成功");
        } else {
            log.error("登记患者-用户连接失败, 未知原因");
        }
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
        return super.lambdaQuery().eq(Patient::getIdentityCardId, identityCardId).one();
    }

    @Override
    public void updatePatient(Patient patient, long currentUserId) {
        boolean exist = userPatientIntermediationService.exist(currentUserId, patient.getId());
        if (!exist) {
            throw new UnauthorizedException("不能更新无关患者的信息");
        }
        update0(patient, currentUserId);
    }

    private void update0(Patient patient, long currentUserId) {
        boolean update = super.lambdaUpdate().setEntity(patient).eq(Patient::getId, patient.getId()).update();
        if (update) {
            log.debug("{}用户成功更新{}病患", currentUserId, patient.getId());
        } else {
            log.debug("{}用户更新{}病患失败", currentUserId, patient.getId());
        }
    }

    @Resource
    private PatientMapper patientMapper;

    @Override
    public List<PatientDto> querySelfPatients(long currentUserId, int page, int limit) {
        log.debug("准备查询当前用户的有关患者");
        int start = (page - 1) * limit;
        List<PatientDto> results = patientMapper.queryByRegisterUser(currentUserId, start, limit);
        log.debug("查询当前用户的有关患者成功!, 有记录: {} 条", results.size());
        return results;
    }

    @Override
    public PatientDto queryByHealthcare(UserDto user, long healthcareCode) {
        Healthcare healthcare = healthcareService.queryByCode(healthcareCode);
        if (healthcare == null) {
            throw new ResourceNotFountException("依据医保号 " + healthcareCode + " 未查询到医保");
        }
        Patient patient = queryByHealthcare(healthcare.getHealthcareId());
        validRoleOnQueryAny(user, patient);
        return PatientDto.union(patient, healthcare);
    }

    @Override
    public PatientDto queryById(UserDto user, long patientId) {
        Patient patient = queryById0(patientId);
        validRoleOnQueryAny(user, patient);
        if (patient.getHealthcareId() == null) {
            return PatientDto.adapt(patient);
        } else {
            Healthcare healthcare = healthcareService.queryById(patient.getHealthcareId());
            return PatientDto.union(patient, healthcare);
        }
    }

    @Resource
    private IdentifierIdPredicate identifierIdPredicate;

    @Override
    public PatientDto queryByIdentity(long currentUserId, String cardId) {
        if (!identifierIdPredicate.test(cardId)) {
            throw new BadRequestException("不正确的身份证格式");
        }
        Patient patient = selectByIdCard(cardId);
        if (patient == null) {
            throw new ResourceNotFountException("依据身份证号" + cardId + "未找到患者");
        }
        boolean exist = userPatientIntermediationService.exist(currentUserId, patient.getId());
        if (!exist) {
            // 注册
            userPatientIntermediationService.register(currentUserId, patient.getId());
        }
        if (patient.getHealthcareId() == null) {
            return PatientDto.adapt(patient);
        }
        Healthcare healthcare = healthcareService.queryById(patient.getHealthcareId());
        return PatientDto.union(patient, healthcare);
    }

    private Patient queryById0(long patientId) {
        Patient patient = super.getById(patientId);
        if (patient == null) {
            throw new ResourceNotFountException("不能依据患者id " + patientId + " 查到患者");
        }
        return patient;
    }

    /**
     * 是否有查询任意病患的权限
     */
    private void validRoleOnQueryAny(UserDto user, Patient patient) {
        switch (user.getRole()) {
            case PATIENT:
                Long userId = user.getId();
                Long patientId = patient.getId();
                boolean exist = userPatientIntermediationService.exist(userId, patientId);
                if (!exist) {
                    log.warn("用户{}由于权限不能获取患者{}", userId, patientId);
                    throw new UnauthorizedException("没有获取其他病患的权限");
                }
            case UNKNOWN:
                throw new UnauthorizedException("没有获取其他病患的权限");
            case NORMAL_DOCTOR:
            case CHARGE_DOCTOR:
            case MEDICATION_DOCTOR:
            case DEVELOPER:
            case DATABASE_ADMINISTRATOR:
                break;
            default:
                throw new ServerException("Unexpected role value: " + user.getRole());
        }
    }

    private Patient queryByHealthcare(long healthcare) {
        Patient patient = super.lambdaQuery().eq(Patient::getHealthcareId, healthcare).one();
        if (patient == null) {
            log.warn("依据医保id{}未找到患者", healthcare);
        }
        return patient;
    }
}