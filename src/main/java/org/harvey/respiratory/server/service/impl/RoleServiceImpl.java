package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.RoleMapper;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
import org.harvey.respiratory.server.pojo.entity.RoleEntity;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.MedicalProviderJobService;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.util.identifier.IdentifierIdPredicate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-16 20:28
 * @see RoleEntity
 * @see RoleMapper
 * @see RoleService
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    @Resource
    private IdentifierIdPredicate predicate;
    @Resource
    private MedicalProviderService medicalProviderService;
    @Resource
    private MedicalProviderJobService medicalProviderJobService;

    /**
     * 只有服务器内部调用
     * 1. 先依据身份证, 如果是未实名, 就是Unknown, 否则进入2
     * 2. medicalProvider查询, 如果有, 则从job中取出role
     * 3. 其他权限表, 给开发者用的, 暂且没有这一选项
     */
    @Override
    public Role queryRole(String identityCardId) {
        // 1. 判断身份证有效
        if (identityCardId == null || !predicate.test(identityCardId)) {
            return Role.UNKNOWN;
        }
        // 2. 从医生处查询
        MedicalProvider medicalProvider = medicalProviderService.selectByIdentityCardId(identityCardId);
        if (medicalProvider != null) {
            return getMedicalProviderRole(medicalProvider.getJobId());
        }
        return Role.PATIENT;
    }

    @Override
    public Role getMedicalProviderRole(int jobId) {
        MedicalProviderJob job = medicalProviderJobService.getById(jobId);
        if (job == null) {
            throw new ServerException("can not find job witch register in medical provider.");
        }
        int roleId = job.getRoleId();
        return selectRole(roleId);
    }

    @Override
    public Role selectRole(int roleId) {
        // TODO
        return Role.create(roleId);
    }
}