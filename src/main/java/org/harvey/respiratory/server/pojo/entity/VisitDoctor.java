package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-12 23:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_visit_doctor")
@NoArgsConstructor
@AllArgsConstructor
public class VisitDoctor {
    /**
     * 就诊号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    /**
     * 患者id
     */
    private long patientId;
    /**
     * 医疗服务者id
     */
    private long medicalProviderId;

    /**
     * 其他辅助治疗
     */
    private String otherAdjuvantTherapy;

    /**
     * 就诊时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDateTime visitTime;

    /**
     * 随访表
     */
    private long followupId;
}
