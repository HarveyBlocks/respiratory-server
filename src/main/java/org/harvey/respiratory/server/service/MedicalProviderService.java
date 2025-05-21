package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    void delete(long id);

    MedicalProvider selectByUser(long userId);

    MedicalProvider selectByIdentityCardId(String identityCardId);


    MedicalProvider selectByPhone(String phoneNumber);

    /**
     * @param name 空字符和null将退化为全查
     */
    List<MedicalProvider> selectByAny(String name, Integer formId, Page<MedicalProvider> page);


    boolean samePeople(long id, String identityCardId);

    /**
     * 依据 id 查询
     */
    MedicalProvider queryById(long medicalProviderId);
}
