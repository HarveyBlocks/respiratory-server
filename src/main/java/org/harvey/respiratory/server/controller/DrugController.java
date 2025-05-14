package org.harvey.respiratory.server.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.FillDrugDto;
import org.harvey.respiratory.server.pojo.entity.Drug;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 具体药物
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 21:01
 */
@Slf4j
@RestController
@Api(tags = {"具体药物"})
@RequestMapping("/drug")
public class DrugController {
    @DeleteMapping("/{id}")
    @ApiOperation("删除某一药品")
    public Result<NullPlaceholder> del(
            @PathVariable("id") @ApiParam(value = "药品id", required = true) Long drugId) {
        throw new UnfinishedException();
    }

    @GetMapping("/visit/{id}")
    @ApiOperation(value = "查询抓药信息")
    @ApiResponses({@ApiResponse(code = 200, message = "ok")})
    public Result<List<FillDrugDto>> queryDrugForFill(
            @PathVariable("id") @ApiParam(value = "问诊号", required = true) Long visitId) {
        // 病人可以查, 医生可以查
        throw new UnfinishedException();
    }


    @GetMapping("/{id}")
    @ApiOperation("查询药物具体信息")
    public Result<Drug> queryById(
            @PathVariable("id") @ApiParam(value = "药物id", required = true) Long id) {
        // 病人可以查, 医生可以查, 费用可以查吗?
        throw new UnfinishedException();
    }

    @GetMapping("/name/{name}/{page}/{limit}")
    @ApiOperation("用名字模糊查询药物具体信息")
    public Result<List<Drug>> queryByName(
            @PathVariable("name") @ApiParam(value = "药物名", required = true) String name,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false) @ApiParam(value = "页长", defaultValue = "10")
            Integer limit
    ) {
        // 用药物名模糊查询药物
        throw new UnfinishedException();
    }
}
