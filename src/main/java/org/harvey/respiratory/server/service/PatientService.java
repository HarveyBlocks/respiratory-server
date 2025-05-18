package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 患者
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface PatientService extends IService<Patient> {
    /**
     * 当前患者不存在个人信息才能注册
     * 用phone检查是否存在
     * 如果不存在, 新增, 存在更新
     *
     * @return 返回id
     */

    long registerPatientInformation(PatientDto patientDto, long currentUserId);

    /**
     * 如果不存在用户-病患关系, 登记用户-病患关系
     * 如果新增healthcare登记, 就加入healthcare
     *
     * @return patient id
     */
    @Transactional
    long registerForExistPatient(
            Patient patientFromDb, Healthcare healthcare, long currentUserId);
    /**
     * 插入Patient
     * 登记用户-病患关系
     * 如果含有healthcare, 就加入healthcare
     */
    @Transactional
    Long registerForNotExistPatient(Patient patient, Healthcare healthcare, long currentUserId);

    /**
     * 最简单的查询, 不会做任何的校验
     */
    Patient selectByIdCard(String identityCardId);

    void updatePatient(Patient patient, long currentUserId);


    List<PatientDto> querySelfPatients(long currentUserId, int page, int limit);

    PatientDto queryByHealthcare(UserDto user, long healthcareCode);

    PatientDto queryById(UserDto user, long patientId);

    /**
     * 如果不存在则注册, 如果存在则返回
     */
    PatientDto queryByIdentity(long currentUserId, String cardId);
}
