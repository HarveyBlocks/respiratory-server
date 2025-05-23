package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.VisitDoctorMapper;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.VisitDoctor;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.harvey.respiratory.server.service.VisitDoctorService;
import org.harvey.respiratory.server.util.RangeDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:39
 * @see VisitDoctor
 * @see VisitDoctorMapper
 * @see VisitDoctorService
 */
@Service
@Slf4j
public class VisitDoctorServiceImpl extends ServiceImpl<VisitDoctorMapper, VisitDoctor> implements VisitDoctorService {
    @Resource
    private MedicalProviderService medicalProviderService;
    @Resource
    private UserPatientIntermediationService userPatientIntermediationService;

    @Override
    public Long queryMedicalProviderId(long visitDoctorId) {
        return super.lambdaQuery()
                .select(VisitDoctor::getMedicalProviderId)
                .eq(VisitDoctor::getId, visitDoctorId)
                .one()
                .getMedicalProviderId();
    }

    @Override
    public VisitDoctor queryMedicalProviderAndPatientId(long visitId) {
        return super.lambdaQuery()
                .select(VisitDoctor::getMedicalProviderId)
                .select(VisitDoctor::getPatientId)
                .eq(VisitDoctor::getId, visitId)
                .one();
    }

    @Override
    public VisitDoctor querySimplyById(long visitDoctorId) {
        return super.lambdaQuery().eq(VisitDoctor::getId, visitDoctorId).one();
    }

    @Override
    public void updateAfterInterview(VisitDoctor visitDoctor) {
        boolean update = super.updateById(visitDoctor);
        if (update) {
            log.debug("更新{}成功", visitDoctor.getId());
        } else {
            log.warn("更新{}失败", visitDoctor.getId());
        }
    }

    @Override
    public Long createVisitDoctorId(long patientId, long medicalProviderId) {
        VisitDoctor takeTheNumber = VisitDoctor.takeTheNumber(patientId, medicalProviderId);
        takeTheNumber.setVisitTime(LocalDateTime.now());
        super.save(takeTheNumber);
        return takeTheNumber.getId();
    }

    @Override
    public List<VisitDoctor> doctorQuery(String identityCardId, RangeDate rangeDate, Page<VisitDoctor> page) {
        MedicalProvider medicalProvider = medicalProviderService.selectByIdentityCardId(identityCardId);
        return super.lambdaQuery()
                .eq(VisitDoctor::getMedicalProviderId, medicalProvider.getId())
                .gt(rangeDate.getStart() != null, VisitDoctor::getVisitTime, rangeDate.getStart())
                .lt(rangeDate.getEnd() != null, VisitDoctor::getVisitTime, rangeDate.getEnd())
                .page(page)
                .getRecords();
    }

    @Override
    public List<VisitDoctor> patientQuery(long currentUserId, RangeDate rangeDate, Page<VisitDoctor> page) {
        List<Long> patientIds = userPatientIntermediationService.queryPatientOnUser(currentUserId);
        return super.lambdaQuery()
                .in(VisitDoctor::getPatientId, patientIds)
                .gt(rangeDate.getStart() != null, VisitDoctor::getVisitTime, rangeDate.getStart())
                .lt(rangeDate.getEnd() != null, VisitDoctor::getVisitTime, rangeDate.getEnd())
                .page(page)
                .getRecords();
    }

    @Override
    public void queryValid(UserDto currentUser, long visitId) {
        Role role = currentUser.getRole();
        switch (role) {
            case UNKNOWN:
                throw new UnauthorizedException("无权限执行");
            case PATIENT:
            case NORMAL_DOCTOR:
            case MEDICATION_DOCTOR:
                VisitDoctor related = this.relatedOnPatient(currentUser.getId(), visitId);
                if (related == null) {
                    throw new UnauthorizedException("不能访问与自己无关的问诊信息");
                }
                break;
            case CHARGE_DOCTOR:
            case DEVELOPER:
            case DATABASE_ADMINISTRATOR:
                // 直接同行
                break;
            default:
                throw new ServerException("Unexpected role value: " + role);
        }
    }

    private VisitDoctor relatedOnPatient(long userId, long visitId) {
        List<Long> patientIds = userPatientIntermediationService.queryPatientOnUser(userId);
        if (patientIds == null || patientIds.isEmpty()) {
            return null;
        }
        return super.lambdaQuery()
                .eq(VisitDoctor::getId, visitId)
                .in(VisitDoctor::getPatientId, patientIds)
                .one();
    }


}