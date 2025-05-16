package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.DiseaseDiagnosisIntermediationMapper;
import org.harvey.respiratory.server.pojo.entity.DiseaseDiagnosisIntermediation;
import org.harvey.respiratory.server.service.DiseaseDiagnosisIntermediationService;
import org.springframework.stereotype.Service;

/**
 * 疾病诊断中间表
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:21
 */
@Service
public class DiseaseDiagnosisIntermediationServiceImpl extends
        ServiceImpl<DiseaseDiagnosisIntermediationMapper, DiseaseDiagnosisIntermediation> implements
        DiseaseDiagnosisIntermediationService {

}
