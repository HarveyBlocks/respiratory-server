package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.harvey.respiratory.server.pojo.enums.DoctorJob;

/**
 * 医疗服务者表
 * id
 * 电话号码(unique, char(11))
 * 名称(varchar(63))
 * 职称(enum-普通/主管/药物)
 * 医疗服务机构表id
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_medical_provider")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProvider {
    /**
     * 医疗服务者
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;

    /**
     *  手机号码(unique, char(11))
     */
    private String phone;

    /**
     * 名称(varchar(63))
     */
    private String name;


    /**
     * 职称(enum-普通/主管/药物)
     */
    private DoctorJob job;
    /**
     * 医疗服务机构表id
     */
    private long formId;


}
