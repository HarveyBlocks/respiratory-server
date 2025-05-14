package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.harvey.respiratory.server.pojo.enums.Role;

/**
 * 医疗提供者的职位
 * 医疗科室
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 00:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_medical_provider_form")
@ApiModel("医生职务")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProviderJob {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("职位名")
    private String name;

    @ApiModelProperty("职位可以持有的权限")
    private Role role;
    /**
     * 单位, 分
     */
    @ApiModelProperty("职位的对每次问诊的消费,单位, 分")
    private Integer expenseEveryVisit;
}
