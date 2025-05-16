package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 医保
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 23:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "患者信息")
public class HealthcareDto {
    @ApiModelProperty(value = "医保表id, 可以为null")
    private Long healthcareId;

    @ApiModelProperty(value = "医保类型, 可以为null, varchar(63)")
    private String healthcareType;

    @ApiModelProperty(value = "医保余额,单位, 分, 可以为null. 不能通过该字段进行更新. 在更新场景下, 该字段的值会自动忽略")
    private Integer balance;
}
