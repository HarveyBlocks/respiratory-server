package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.FamilyHistoryMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.FamilyHistory;
import org.harvey.respiratory.server.pojo.enums.FamilyRelationship;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.DiseaseService;
import org.harvey.respiratory.server.service.FamilyHistoryService;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:36
 * @see FamilyHistory
 * @see FamilyHistoryMapper
 * @see FamilyHistoryService
 */
@Service
@Slf4j
public class FamilyHistoryServiceImpl extends ServiceImpl<FamilyHistoryMapper, FamilyHistory> implements
        FamilyHistoryService {

    @Resource
    private UserPatientIntermediationService userPatientIntermediationService;
    @Resource
    private RoleService roleService;
    @Resource
    private DiseaseService diseaseService;

    /**
     * 病患只能添加自己的
     * 医生可以都过
     */
    @Override
    public void validRoleToRegister(UserDto currentUser, Long patientId) {
        if (currentUser == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        if (patientId == null) {
            throw new BadRequestException("需要有关病患id");
        }
        Role role = roleService.queryRole(currentUser.getIdentityCardId());
        // 病患只能添加自己的
        // 医生可以都过
        switch (role) {
            case UNKNOWN:
                throw new UnauthorizedException("你还未实名, 没有权限");
            case PATIENT:
                boolean exist = userPatientIntermediationService.existRelation(currentUser.getId(), patientId);
                if (!exist) {
                    throw new UnauthorizedException("不能修改未在自己账号处注册的用户的家族史");
                }
                return;
            case NORMAL_DOCTOR:
            case MEDICATION_DOCTOR:
            case CHARGE_DOCTOR:
            case DEVELOPER:
            case DATABASE_ADMINISTRATOR:
                break;
            default:
                throw new ServerException("Unexpected role value: " + role);
        }

    }

    @Override
    public Long register(FamilyHistory familyHistory) {
        Long patientId = familyHistory.getPatientId();
        if (patientId == null) {
            throw new BadRequestException("需要病患id");
        }

        // 关系校验成功, 可以注册
        boolean saved = super.save(familyHistory);
        if (saved) {
            log.debug("保存患者{}的{}家族史成功", patientId, familyHistory.getFamilyRelationshipId());
        } else {
            log.warn("保存患者{}的{}家族史失败", patientId, familyHistory.getFamilyRelationshipId());
        }
        return familyHistory.getId();
    }

    @Override
    public List<FamilyHistory> queryByPatient(long patientId, Page<FamilyHistory> page) {
        return super.lambdaQuery().eq(FamilyHistory::getPatientId, patientId).page(page).getRecords();
    }

    @Override
    public List<FamilyHistory> queryByDiseaseName(long patientId, String diseaseName, Page<FamilyHistory> page) {
        List<Integer> ids = diseaseService.queryIdsByName(diseaseName);

        return super.lambdaQuery()
                .eq(FamilyHistory::getPatientId, patientId)
                .in(FamilyHistory::getDiseaseId, ids)
                .page(page)
                .getRecords();
    }

    @Override
    public List<FamilyHistory> queryByDisease(long patientId, long diseaseId, Page<FamilyHistory> page) {
        return super.lambdaQuery()
                .eq(FamilyHistory::getPatientId, patientId)
                .eq(FamilyHistory::getDiseaseId, diseaseId)
                .page(page)
                .getRecords();
    }

    @Override
    public List<FamilyHistory> queryByRelationship(
            long patientId, List<FamilyRelationship> relationshipList, Page<FamilyHistory> page) {
        List<Integer> ids = relationshipList.stream().map(Enum::ordinal).collect(Collectors.toList());
        return super.lambdaQuery()
                .eq(FamilyHistory::getPatientId, patientId)
                .in(FamilyHistory::getFamilyRelationshipId, ids)
                .page(page)
                .getRecords();
    }


}