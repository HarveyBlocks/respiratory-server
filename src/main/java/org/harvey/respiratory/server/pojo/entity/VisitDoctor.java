package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("就诊, 只读")
public class VisitDoctor {
    /**
     * 就诊号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "就诊号")
    private long id;
    /**
     * 患者id, 不应该为null
     */
    @ApiModelProperty(value = "患者id, 不应该为null")
    private long patientId;
    /**
     * 医疗服务者id, 不可以为null
     */
    @ApiModelProperty(value = "医疗服务者id, 不可以为null")
    private long medicalProviderId;
    /**
     * 简要介绍(varchar(255)), 如果医生不提供, 就是病症名的list
     */
    @ApiModelProperty(value = " 简要介绍(varchar(255)), 如果医生不提供, 就是病症名的list")
    private String briefDescription;
    /**
     * 其他辅助治疗
     */
    @ApiModelProperty(value = "其他辅助治疗")
    private String otherAdjuvantTherapy;

    /**
     * 就诊时间
     */
    @ApiModelProperty(value = "就诊时间, 只读")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDateTime visitTime;
    /**
     * 总费用, 单位分
     * 医生职业+医生科室+(药品*数量)*for-each
     */
    @ApiModelProperty(value = "总费用, 单位分.医生职业+医生科室+(药品*数量)*for-each, 只读")
    private Integer totalExpense;

    @ApiModelProperty(value = "是否已付款, 是外界只读的字段")
    private boolean paid = false;
    /**
     * 随访表, 可以为null
     */
    @ApiModelProperty(value = "随访表, 可以为null")
    private Long followupId;
}
