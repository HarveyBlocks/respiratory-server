package org.harvey.respiratory.server.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.entity.Patient;

import java.util.List;

/**
 * 患者
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
    /**
     * <pre>{@code
     *
     * select tb_patient.id,
     *        tb_patient.phone,
     *        tb_patient.name,
     *        tb_patient.sex,
     *        tb_patient.birth_date,
     *        tb_patient.address,
     *        tb_patient.height,
     *        tb_patient.weight,
     *        tb_patient.identity_card_id,
     *        tb_healthcare.type healthcare_type,
     *        tb_healthcare.healthcare_code
     * from tb_user_patient_intermediation
     *          JOIN user_security on tb_user_patient_intermediation.user_id = user_security.id
     *          JOIN tb_patient on tb_user_patient_intermediation.patient_id = tb_patient.id
     *          LEFT JOIN tb_healthcare on tb_healthcare.healthcare_id = tb_patient.healthcare_id
     * where user_security.id = 1923431434245177346
     * limit 0,10;
     *
     * }</pre>
     */
    List<PatientDto> queryByRegisterUser(
            @Param("userId") long userId,
            @Param("start") int start,
            @Param("len") int len);
}
