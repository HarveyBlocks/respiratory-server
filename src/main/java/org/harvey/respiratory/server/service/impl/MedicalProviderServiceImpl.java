package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderMapper;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.springframework.stereotype.Service;


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

}