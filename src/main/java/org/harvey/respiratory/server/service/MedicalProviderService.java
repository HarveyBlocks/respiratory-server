package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;

import java.util.List;

/**
 * 医疗提供者
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface MedicalProviderService extends IService<MedicalProvider> {
    Long register(MedicalProvider medicalProvider);

    void update(MedicalProvider medicalProvider);

    void delete(Long id);

    MedicalProvider selectByUser(long userId);

    MedicalProvider selectByIdentityCardId(String identityCardId);


    MedicalProvider selectByPhone(String phoneNumber);

    /**
     * @param name 空字符和null将退化为全查
     */
    List<MedicalProvider> selectByAny(String name, Integer formId, int page, int limit);


}
