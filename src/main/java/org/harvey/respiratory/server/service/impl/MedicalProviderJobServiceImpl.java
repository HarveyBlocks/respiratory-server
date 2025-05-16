package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderJobMapper;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
import org.harvey.respiratory.server.service.MedicalProviderJobService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:38
 * @see MedicalProviderJob
 * @see MedicalProviderJobMapper
 * @see MedicalProviderJobService
 */
@Service
public class MedicalProviderJobServiceImpl extends ServiceImpl<MedicalProviderJobMapper, MedicalProviderJob> implements
        MedicalProviderJobService {

}