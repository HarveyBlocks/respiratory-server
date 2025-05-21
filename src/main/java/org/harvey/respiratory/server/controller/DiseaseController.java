package org.harvey.respiratory.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.harvey.respiratory.server.service.DiseaseService;
import org.harvey.respiratory.server.util.ConstantsInitializer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    private DiseaseService diseaseService;

    @DeleteMapping("/{id}")
    @ApiOperation("删除某一疾病")
    public Result<NullPlaceholder> del(
            @PathVariable("id") @ApiParam(value = "疾病id", required = true) Integer diseaseId) {
        // 逻辑删除
        diseaseService.deleteById(diseaseId);
        return Result.ok();
    }

    @GetMapping("/visit/{id}")
    @ApiOperation("查询问诊号下所有疾病信息")
    public Result<List<Disease>> queryDrugForFill(
            @PathVariable("id") @ApiParam(value = "问诊号", required = true) Long visitId) {
        // 病人可以查, 医生可以查
        return Result.success(diseaseService.selectByVisitDoctor(visitId));
    }


    @GetMapping("/{id}")
    @ApiOperation("查询疾病具体信息")
    public Result<Disease> queryById(
            @PathVariable("id") @ApiParam(value = "药物id", required = true) Integer id) {
        // 病人可以查, 医生可以查
        return Result.success(diseaseService.selectById(id));
    }

    @GetMapping(value = {"/name/{name}/{page}/{limit}", "/name/{name}/{page}", "/name/{name}"})
    @ApiOperation("用名字模糊查询疾病具体信息")
    public Result<List<Disease>> queryByName(
            @PathVariable("name") @ApiParam(value = "疾病名", required = true) String name,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false) @ApiParam(value = "页长",
                    defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG)
            Integer limit
    ) {
        // 用疾病名模糊查询疾病
        Page<Disease> diseasePage = ConstantsInitializer.initPage(page, limit);
        // name 为null 就查询全部
        List<Disease> list;
        if (name == null || name.isEmpty()) {
            list = diseaseService.selectByPage(diseasePage);
        } else {
            list = diseaseService.selectByName(name, diseasePage);
        }
        return Result.success(list);
    }


}
