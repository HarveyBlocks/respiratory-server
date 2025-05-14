package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.ExpenseRecord;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/visit/{visit-id}")
    @ApiOperation("查询该问诊下的医疗费用记录")
    public Result<List<ExpenseRecord>> querySelfPatient(
            @PathVariable("visit-id")
            @ApiParam("问诊号") Long visitId) {
        throw new UnfinishedException();
    }

}
