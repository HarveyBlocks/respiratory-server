package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.UserPatientIntermediationMapper;
import org.harvey.respiratory.server.pojo.entity.UserPatientIntermediation;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:39
 * @see UserPatientIntermediation
 * @see UserPatientIntermediationMapper
 * @see UserPatientIntermediationService
 */
@Service
public class UserPatientIntermediationServiceImpl extends
        ServiceImpl<UserPatientIntermediationMapper, UserPatientIntermediation> implements
        UserPatientIntermediationService {

    @Override
    public boolean exist(long userId, long patientId) {
        return super.lambdaQuery()
                .eq(UserPatientIntermediation::getUserId, userId)
                .and(w -> w.eq(UserPatientIntermediation::getPatientId, patientId))
                .oneOpt().isPresent();
    }
}