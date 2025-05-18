package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.FamilyHistory;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 22:34
 */
@Slf4j
@RestController
@Api(tags = {"家庭病史"})
@RequestMapping("/history/family")
public class FamilyHistoryController {
    @GetMapping(value = {"/{patientId}/{page}/{limit}",
            "/{patientId/{page}/", "/{patientId}"})
    @ApiOperation("家族病史, 用疾病名模糊查询")
    public Result<List<FamilyHistory>> queryFamilyHistory(
            @PathVariable("patientId") @ApiParam(value = "病患名", required = true) Long patientId,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 用药物名模糊查询药物
        throw new UnfinishedException();
    }
    @GetMapping(value = {"/disease-name/{patientId}/{diseaseName}/{page}/{limit}",
            "/disease-name/{patientId}/{diseaseName}/{page}/", "/disease-name/{patientId}/{diseaseName}"})
    @ApiOperation("家族病史, 用疾病名模糊查询")
    public Result<List<FamilyHistory>> queryFamilyHistoryByName(
            @PathVariable("patientId") @ApiParam(value = "病患名", required = true) Long patientId,
            @PathVariable("diseaseName") @ApiParam(value = "疾病名模糊查询", required = true) String diseaseName,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 用药物名模糊查询药物
        throw new UnfinishedException();
    }

    @GetMapping(value = {"/disease-id/{patientId}/{diseaseId}/{page}/{limit}",
            "/disease-name/{patientId}/{diseaseId}/{page}/", "/disease-name/{patientId}/{diseaseId}"})
    @ApiOperation("家族病史, 用疾病id查询")
    public Result<List<FamilyHistory>> queryFamilyHistoryByDiseaseId(
            @PathVariable("patientId") @ApiParam(value = "病患名", required = true) Long patientId,
            @PathVariable("diseaseId") @ApiParam(value = "疾病id", required = true) Long diseaseId,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 用药物名模糊查询药物
        throw new UnfinishedException();
    }

    @GetMapping(value = {"/relationship/{patientId}/{relationship}/{page}/{limit}",
            "/disease-name/{patientId}/{relationship}/{page}/", "/disease-name/{patientId}/{relationship}"})
    @ApiOperation("家族病史, 用成员关系查询")
    public Result<List<FamilyHistory>> queryFamilyHistoryByRelationship(
            @PathVariable("patientId") @ApiParam(value = "病患名", required = true) Long patientId,
            @PathVariable("relationship")
            @ApiParam(value = "病患关系数组." +
                              " 即使只有一个, 前后`[]`也不应该省略," +
                              " 但是可能要注意以下URL中`[],`三个符号是不是特殊字符, 是否需要转义",
                    example = "`[FATHER,MATHER]`", required = true) String relationships,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 用药物名模糊查询药物
        // TODO 要注意以下URL中`[],`三个符号是不是特殊字符, 是否需要转义
        throw new UnfinishedException();
    }
}
