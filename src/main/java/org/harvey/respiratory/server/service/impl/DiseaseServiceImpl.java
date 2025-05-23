package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.DiseaseMapper;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.DiseaseDiagnosisIntermediationService;
import org.harvey.respiratory.server.service.DiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Disease的Service实现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:32
 */
@Slf4j
@Service
@NonNull
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {
    @Resource
    private DiseaseDiagnosisIntermediationService diseaseDiagnosisIntermediationService;

    @Override
    public void deleteById(int id) {
        boolean removed = super.removeById(id);
        if (removed) {
            log.debug("正常删除{}的疾病", id);
        }
    }

    @Override
    @NonNull
    public List<Disease> selectByVisitDoctor(long visitId) {
        List<Integer> ids = diseaseDiagnosisIntermediationService.selectDiseaseByVisit(visitId);
        return super.listByIds(ids);
    }

    @Override
    @NonNull
    public Disease selectById(int id) {
        Disease disease = super.getById(id);
        if (disease != null) {
            return disease;
        } else {
            throw new ResourceNotFountException("not found disease: " + id);
        }
    }

    @Override
    @NonNull
    public List<Disease> selectByPage(Page<Disease> page) {
        return super.lambdaQuery().page(page).getRecords();
    }

    @Override
    @NonNull
    public List<Disease> selectByName(String name, Page<Disease> page) {
        return super.lambdaQuery().like(Disease::getName, name).page(page).getRecords();
    }

    @Override
    @NonNull
    public List<Integer> queryIdsByName(String diseaseName) {
        return super.lambdaQuery()
                .select(Disease::getId)
                .like(Disease::getName, diseaseName)
                .list()
                .stream()
                .map(Disease::getId)
                .collect(Collectors.toList());
    }

    @Override
    @NonNull
    public List<String> queryDiseaseNameByIds(List<Integer> diseaseIds) {
        return super.lambdaQuery()
                .in(Disease::getId, diseaseIds)
                .list()
                .stream()
                .map(Disease::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void validOnWrite(UserDto user) {
        if (user == null) {
            throw new UnauthorizedException("未登录不能执行");
        }
        String identityCardId = user.getIdentityCardId();
        if (identityCardId == null) {
            throw new UnauthorizedException("未实名不能执行");
        }
        Role role = user.getRole();
        switch (role) {
            case UNKNOWN:
            case PATIENT:
            case NORMAL_DOCTOR:
            case MEDICATION_DOCTOR:
                throw new UnauthorizedException("权限不足");
            case CHARGE_DOCTOR:
            case DEVELOPER:
            case DATABASE_ADMINISTRATOR:
                break;
            default:
                throw new ServerException("Unexpected role value: " + role);
        }
    }

    @Override
    @NonNull
    public Integer register(Disease disease) {
        disease.setId(null);
        boolean saved = super.save(disease);
        return disease.getId();
    }
}