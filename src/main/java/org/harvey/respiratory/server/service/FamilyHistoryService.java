package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.FamilyHistory;
import org.harvey.respiratory.server.pojo.entity.FamilyRelationshipEntity;
import org.harvey.respiratory.server.pojo.enums.FamilyRelationship;
import org.harvey.respiratory.server.util.ConstantsInitializer;

import java.util.List;

/**
 * 家族史
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface FamilyHistoryService extends IService<FamilyHistory> {
    Long register(FamilyHistory familyHistory);

    List<FamilyHistory> queryByPatient(long patientId, Page<FamilyHistory> page);

    List<FamilyHistory> queryByDiseaseName(long patientId, String diseaseName, Page<FamilyHistory> page);

    List<FamilyHistory> queryByDisease(long patientId, int diseaseId, Page<FamilyHistory> page);

    List<FamilyHistory> queryByRelationship(
            long patientId, List<Integer> relationshipIds, Page<FamilyHistory> objectPage);

    void validRoleToRegister(UserDto currentUser, Long patientId);

}
