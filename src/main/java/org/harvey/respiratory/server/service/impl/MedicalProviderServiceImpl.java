package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderMapper;
import org.harvey.respiratory.server.exception.DaoException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.UserSecurityService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:38
 * @see MedicalProvider
 * @see MedicalProviderMapper
 * @see MedicalProviderService
 */
@Service
public class MedicalProviderServiceImpl extends ServiceImpl<MedicalProviderMapper, MedicalProvider> implements
        MedicalProviderService {
    @Resource
    private UserSecurityService userSecurityService;

    @Override
    public Long register(MedicalProvider medicalProvider) {
        boolean saved = super.save(medicalProvider);
        if (saved) {
            log.debug("保存医疗提供者成功");
        } else {
            throw new DaoException(DaoException.Operation.SAVE_FAIL, "保存医疗提供者失败, 未知原因");
        }
        return medicalProvider.getId();
    }

    @Override
    public void update(MedicalProvider medicalProvider) {
        boolean updated = super.updateById(medicalProvider);
        if (updated) {
            log.debug("更新医疗提供者成功");
        } else {
            throw new DaoException(DaoException.Operation.UPDATE_FAIL, "更新医疗提供者失败, 未知原因");
        }
    }

    @Override
    public void delete(long id) {
        boolean removed = super.removeById(id);
        if (removed) {
            log.debug("删除医疗提供者成功");
        } else {
            throw new DaoException(DaoException.Operation.UPDATE_FAIL, "删除医疗提供者失败, 未知原因");
        }
    }

    @Override
    public MedicalProvider selectByUser(long userId) {
        UserSecurity userSecurity = userSecurityService.selectById(userId);
        if (userSecurity.getIdentityCardId() == null) {
            throw new UnauthorizedException("不能查询医疗提供者信息");
        }
        return super.lambdaQuery().eq(MedicalProvider::getIdentityCardId, userSecurity.getIdentityCardId()).one();
    }

    @Override
    public MedicalProvider selectByIdentityCardId(String identityCardId) {
        return super.lambdaQuery().eq(MedicalProvider::getIdentityCardId, identityCardId).one();
    }

    @Override
    public MedicalProvider selectByPhone(String phoneNumber) {
        UserSecurity userSecurity = userSecurityService.selectByPhone(phoneNumber);
        String identityCardId = userSecurity.getIdentityCardId();
        if (identityCardId == null) {
            // 没有被实名的用户, 其实一定不是医生了
            return null;
        }
        return this.selectByIdentityCardId(identityCardId);
    }

    @Override
    public List<MedicalProvider> selectByAny(String name, Integer formId, Page<MedicalProvider> page) {
        return super.lambdaQuery()
                .eq(formId != null, MedicalProvider::getFormId, formId)
                .like(name == null || name.isEmpty(), MedicalProvider::getName, name)
                .page(page)
                .getRecords();
    }

    @Override
    public boolean samePeople(long id, String identityCardId) {
        return super.lambdaQuery()
                .eq(MedicalProvider::getId, id)
                .eq(MedicalProvider::getIdentityCardId, identityCardId)
                .oneOpt().isPresent();
    }

    @Override
    public MedicalProvider queryById(long medicalProviderId) {
        return super.lambdaQuery().eq(MedicalProvider::getId, medicalProviderId).one();
    }
}