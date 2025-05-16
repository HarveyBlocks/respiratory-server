package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 疾病
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 22:26
 */
@Slf4j
@RestController
@Api(tags = {"疾病"})
@RequestMapping("/disease")
public class DiseaseController {
    @DeleteMapping("/{id}")
    @ApiOperation("删除某一疾病")
    public Result<NullPlaceholder> del(
            @PathVariable("id") @ApiParam(value = "疾病id", required = true) Long diseaseId) {
        // 逻辑删除
        throw new UnfinishedException();
    }

    @GetMapping("/visit/{id}")
    @ApiOperation("查询问诊号下所有疾病信息")
    public Result<List<Disease>> queryDrugForFill(
            @PathVariable("id") @ApiParam(value = "问诊号", required = true) Long visitId) {
        // 病人可以查, 医生可以查
        throw new UnfinishedException();
    }


    @GetMapping("/{id}")
    @ApiOperation("查询疾病具体信息")
    public Result<Disease> queryById(
            @PathVariable("id") @ApiParam(value = "药物id", required = true) Long id) {
        // 病人可以查, 医生可以查
        throw new UnfinishedException();
    }

    @GetMapping("/name/{name}/{page}/{limit}")
    @ApiOperation("用名字模糊查询疾病具体信息")
    public Result<List<Disease>> queryByName(
            @PathVariable("name") @ApiParam(value = "疾病名", required = true) String name,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false) @ApiParam(value = "页长", defaultValue = "10")
            Integer limit
    ) {
        // 用药物名模糊查询药物
        throw new UnfinishedException();
    }


}
