package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;

import java.util.List;

/**
 * 医疗提供者之职位
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface MedicalProviderJobService extends IService<MedicalProviderJob> {
    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    @NonNull
    Integer register(MedicalProviderJob job);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    void update(MedicalProviderJob newJob);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    void delete(long id);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_READ
     */
    @NonNull
    MedicalProviderJob querySelf(long userId);

    @NonNull
    List<MedicalProviderJob> queryAny(Page<MedicalProviderJob> page);

    @NonNull
    MedicalProviderJob queryById(int jobId);

    @NonNull
    List<MedicalProviderJob> queryByName(String name);
}
