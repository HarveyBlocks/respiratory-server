package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 23:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("充值")
public class RechargeDto {
    @ApiModelProperty("充值的查询依据, 或医保号, 或病患号, 或电话号码, 三选一")
    private int queryBasisId;

    @ApiModelProperty("充值的查询依据, 或医保号, 或病患号, 或电话号码, 三选一")
    private String phone;
    @ApiModelProperty(value = "充值金额, 单位, 分, 必正", required = true)
    private int amount;
}
