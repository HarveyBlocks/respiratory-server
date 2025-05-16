package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.springframework.transaction.annotation.Transactional;


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
     * @see #registerForExistPatient(Patient, Healthcare, long)
     * @see #registerForNotExistPatient(Patient, Healthcare, long)
     */

    long registerPatientInformation(PatientDto patientDto, long currentUserId);

    /**
     * 如果不存在用户-病患关系, 登记用户-病患关系
     * 如果新增healthcare登记, 就加入healthcare
     *
     * @return patient id
     */
    @Transactional
    long registerForExistPatient(Patient patientByPhone, Healthcare healthcare, long currentUserId);


    /**
     * 插入Patient
     * 登记用户-病患关系
     * 如果含有healthcare, 就加入healthcare
     */
    @Transactional
    Long registerForNotExistPatient(Patient patient, Healthcare healthcare, long currentUserId);


    Patient selectByIdCard(String identityCardId);
}
