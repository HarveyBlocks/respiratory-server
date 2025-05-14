package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.SpecificUsingDrugsIntermediation;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 症状
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 21:57
 */
@Slf4j
@RestController
@Api(tags = "症状")
@RequestMapping("/symptomatic-presentation")
public class SymptomaticPresentationController {
    @DeleteMapping("/{id}")
    @ApiOperation("删除某一问诊的某一具体用药")
    public Result<NullPlaceholder> del(
            @PathVariable("id") @ApiParam(value = "症状id", required = true) Long id) {
        // 由于症状留证据, 所以是逻辑删除
        // 依据id删除
        throw new UnfinishedException();
    }

    @PutMapping("/")
    @ApiOperation("更新症状")
    public Result<NullPlaceholder> updatePatientDrugHistory(
            @RequestBody @ApiParam("symptomaticPresentation") SymptomaticPresentation symptomaticPresentation) {
        // 更新
        throw new UnfinishedException();
    }


    @GetMapping("history/patient/{id}/{name}")
    @ApiOperation("查询病人既往用药史")
    public Result<List<SpecificUsingDrugsIntermediation>> queryPatientDrugHistoryByDrug(
            @PathVariable("id") @ApiParam(value = "病患id", required = true) Long patientId,
            @PathVariable(value = "name", required = false) @ApiParam(value = "药品名") Long name) {
        // 病人可以查, 医生可以查
        throw new UnfinishedException();
    }

    @GetMapping("history/patient/{id}/{start}/{end}")
    @ApiOperation("查询病人既往用药史")
    public Result<List<SpecificUsingDrugsIntermediation>> queryPatientDrugHistoryByDate(
            @PathVariable("id") @ApiParam(value = "病患id", required = true) Long patientId,
            @PathVariable(value = "start", required = false) @ApiParam(value = "yyyy-MM-dd, 0补前") String startDate,
            @PathVariable(value = "end", required = false) @ApiParam(value = "yyyy-MM-dd, 0补前") String endDate) {
        // 病人可以查, 医生可以查
        // param.startDate<drug.end or drug.start < param.endDate, 表示在时间范围内的用药
        throw new UnfinishedException();
    }
}
