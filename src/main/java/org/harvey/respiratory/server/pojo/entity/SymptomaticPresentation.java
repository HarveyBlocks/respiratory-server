package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.harvey.respiratory.server.pojo.enums.Severity;

import java.sql.Date;

/**
 * 症状表现表
 * 就诊号/就诊表id
 * 症状id
 * 名称(varchar(63))
 * 严重程度(enum-轻度/中度/重度)
 * 频率(varchar(63))
 * 开始时间(date)
 * 诱因(varchar(63))
 * 环境因素(varchar(63))
 * 体征描述(varchar(63))
 * 描述(TEXT)
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_symptomatic_presentation")
@NoArgsConstructor
@AllArgsConstructor
public class SymptomaticPresentation {
    /**
     * 症状id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    /**
     * 就诊号/就诊表id
     */
    private long visitDoctorId;
    /**
     * 名称(varchar(63))
     */
    private String name;

    /**
     * 严重程度(enum-轻度/中度/重度)
     */
    private Severity severity;
    /**
     * 频率(varchar(63)), 由于不知道单位
     */
    private String frequency;
    /**
     * 开始时间(date)
     */
    private Date startTime;
    /**
     * 诱因(varchar(63))
     */
    private String incentive;
    /**
     * 环境因素(varchar(63))
     */
    private String environmentalFactor;
    /**
     * 体征描述(varchar(63))
     */
    private String  signDescription;
    /**
     * 描述(TEXT)
     */
    private String description;
}
