package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderDepartment;

import java.util.List;

/**
 * 医院科室
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface MedicalProviderDepartmentService extends IService<MedicalProviderDepartment> {
    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    @NonNull
    Integer register(MedicalProviderDepartment department);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    void update(MedicalProviderDepartment newDepartment);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_UPDATE
     */
    void delete(long id);

    /**
     * @see org.harvey.respiratory.server.util.RoleConstant#MEDICAL_PROVIDER_READ
     */
    @NonNull
    MedicalProviderDepartment querySelf(long userId);

    @NonNull
    List<MedicalProviderDepartment> queryAny(Page<MedicalProviderDepartment> page);

    @NonNull
    MedicalProviderDepartment queryById(int departmentId);

    @NonNull
    List<MedicalProviderDepartment> queryByName(String name);
}
