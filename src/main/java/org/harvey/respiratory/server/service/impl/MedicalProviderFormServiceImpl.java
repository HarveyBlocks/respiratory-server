package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderFormMapper;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
import org.harvey.respiratory.server.service.MedicalProviderFormService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:37
 * @see MedicalProviderForm
 * @see MedicalProviderFormMapper
 * @see MedicalProviderFormService
 */
@Service
public class MedicalProviderFormServiceImpl extends
        ServiceImpl<MedicalProviderFormMapper, MedicalProviderForm> implements MedicalProviderFormService {

}