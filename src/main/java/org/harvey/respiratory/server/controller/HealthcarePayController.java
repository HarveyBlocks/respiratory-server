package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.RechargeDto;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 医保, 也包含医保付费
 * 医保付费
 * 医保充值
 * 依据患者号,
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 20:05
 */
@Slf4j
@RestController
@Api(tags = {"医保"})
@RequestMapping("/healthcare")
public class HealthcarePayController {
    @PutMapping("pay")
    @ApiOperation("付款")
    public Result<NullPlaceholder> pay(
            @RequestBody @ApiParam(value = "问诊号", required = true) Long visitId) {
        // 依据问诊号, 获取到账单记录/总费用
        // 依据总费用扣除医保费用
        // 如果余额不足就抛出异常
        // 保存支付记录, 防止重复付款
        // 无订单->尝试付款->扣费->修改订单状态
        // 有订单->订单已付款->直接返回记录, 不扣费
        // 有订单->订单未付款->尝试付款->扣费->修改订单状态
        // 但是本系统无订单这种东西, 只有visitId, visitId充当订单的部分
        throw new UnfinishedException();
    }


    @ApiOperation("用查询医保后, 然后充值")
    @PutMapping("recharge/healthcare/")
    @ApiResponse(code = 200, message = "返回是充值之后的余额")
    public Result<Integer> rechargeByHealthcare(
            @RequestBody @ApiParam(value = "医保号id", required = true) RechargeDto patientId) {
        // 找不到医保, 抛出异常, 让用户重新创建医保
        throw new UnfinishedException();
    }

    @ApiOperation("用病号查询余额")
    @PutMapping("balance/healthcare/{healthcareId}")
    @ApiResponse(code = 200, message = "返回余额")
    public Result<Integer> queryBalance(
            @PathVariable("healthcareId") @ApiParam(value = "医保号id", required = true) Long healthcareId) {
        // 找不到医保, 抛出异常, 让用户重新创建医保
        throw new UnfinishedException();
    }
}
