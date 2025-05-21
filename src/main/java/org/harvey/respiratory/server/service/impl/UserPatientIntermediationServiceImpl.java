package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.UserPatientIntermediationMapper;
import org.harvey.respiratory.server.pojo.entity.UserPatientIntermediation;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:39
 * @see UserPatientIntermediation
 * @see UserPatientIntermediationMapper
 * @see UserPatientIntermediationService
 */
@Service
@Slf4j
public class UserPatientIntermediationServiceImpl extends
        ServiceImpl<UserPatientIntermediationMapper, UserPatientIntermediation> implements
        UserPatientIntermediationService {

    @Override
    public boolean existRelation(long userId, long patientId) {
        return super.lambdaQuery()
                .eq(UserPatientIntermediation::getUserId, userId)
                .and(w -> w.eq(UserPatientIntermediation::getPatientId, patientId))
                .oneOpt()
                .isPresent();
    }

    @Override
    public boolean register(long userId, long patientId) {
        boolean saved = super.save(new UserPatientIntermediation(userId, patientId));
        if (saved) {
            log.debug("保存用户{}-患者{},成功", userId, patientId);
        } else {
            log.warn("保存用户{}-患者{},失败", userId, patientId);
        }
        return saved;
    }

    @Override
    public boolean delete(long patientId, long userId) {
        boolean removed = super.remove(super.lambdaQuery()
                .eq(UserPatientIntermediation::getUserId, userId)
                .and(w -> w.eq(UserPatientIntermediation::getPatientId, patientId)));
        if (removed) {
            log.debug("删除用户{}-患者{},成功", userId, patientId);
        } else {
            log.warn("删除用户{}-患者{},失败", userId, patientId);
        }
        return removed;
    }

    @Override
    public List<Long> queryPatientOnUser(long userId) {
        return super.lambdaQuery()
                .select(UserPatientIntermediation::getPatientId)
                .eq(UserPatientIntermediation::getUserId, userId)
                .list()
                .stream()
                .map(UserPatientIntermediation::getPatientId)
                .collect(Collectors.toList());
    }
}