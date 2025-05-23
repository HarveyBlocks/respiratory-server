package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.ExpenseRecord;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.harvey.respiratory.server.service.ExpenseRecordService;
import org.harvey.respiratory.server.service.VisitDoctorService;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 费用记录
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 20:01
 */
@Slf4j
@RestController
@Api(tags = {"费用记录"})
@RequestMapping("/expense-record")
public class ExpenseRecordController {
    @Resource
    private ExpenseRecordService expenseRecordService;
    @Resource
    private VisitDoctorService visitDoctorService;

    @GetMapping("/visit/{visit-id}")
    @ApiOperation("查询该问诊下的医疗费用记录")
    public Result<List<ExpenseRecord>> querySelfExpenseRecord(
            @PathVariable("visit-id") @ApiParam("问诊号") Long visitId) {
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("未登录无权限");
        }
        if (visitId == null) {
            throw new BadRequestException("需要问诊id");
        }

        return Result.success(expenseRecordService.querySelfExpenseRecord(user, visitId));
    }

}
