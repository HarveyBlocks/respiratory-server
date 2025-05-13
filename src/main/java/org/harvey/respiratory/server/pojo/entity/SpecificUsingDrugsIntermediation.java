package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * 特定药品使用方式中间表
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-12 23:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_specific_using_drugs_intermediation")
@NoArgsConstructor
@AllArgsConstructor
public class SpecificUsingDrugsIntermediation {
    /**
     * 就诊号/就诊表id, 和 drug id 一起是联合主键
     */
    private long visitDoctorId;
    /**
     * 药品表id, 和 visit doctor id 一起是联合主键
     */
    private long drugId;
    /**
     * 患者表id(冗余, 为了提高效率)
     */
    private String patientId;
    /**
     * 使用剂量(varchar(63)), 由于单位不确定, 所以是字符串
     */
    private String dosageUsed;
    /**
     * 使用天数(int)
     */
    private int dayTimeUsed;
    /**
     * 使用频率(varchar(63)), 同样不知道单位
     */
    private String frequencyUsed;
    /**
     * 治疗开始时间(date)
     */
    private Date treatStart;
    /**
     * 治疗结束时间(date)
     */
    private Date treatEnd;

    /**
     * 其他用药指导(TEXT)
     */
    private String otherMedicationGuidance;
}
