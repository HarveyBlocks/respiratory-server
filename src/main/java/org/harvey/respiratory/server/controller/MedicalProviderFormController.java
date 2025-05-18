package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医疗提供者和医疗提供机构
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 23:20
 */
@Slf4j
@RestController
@Api(tags = {"医疗提供机构"})
@RequestMapping("/medical/form")
public class MedicalProviderFormController {
    @PostMapping("/register")
    @ApiOperation("登记医疗提供机构信息")
    public Result<Long> registerPatientInformation(
            @RequestBody @ApiParam("新增时, 除id, 和医保相关外, 字段都不得为null.") MedicalProviderForm form) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(form);
    }

    @PutMapping("/")
    @ApiOperation("更新医疗提供机构信息")
    public Result<Long> update(
            @RequestBody @ApiParam("更新字段可以为null, 表示保留原有; 也可以不为null, 即使字段值没有发生变化")
            MedicalProviderForm form) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(form);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除医疗提供者信息")
    public Result<NullPlaceholder> del(@PathVariable("id") @ApiParam("医疗提供者 id") Long id) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(id);
    }


    @GetMapping("/self")
    @ApiOperation("查询医疗提供者自己的机构信息")
    public Result<MedicalProviderForm> query() {
        // 任何医疗提供者可执行
        throw new UnfinishedException();
    }


    @GetMapping("/all/{page}/{limit}")
    @ApiOperation("查询医疗提供者信息")
    public Result<List<MedicalProviderForm>> query(
            @PathVariable(name = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(name = "limit", required = false)
            @ApiParam(value = "页面长度", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(page, limit);
    }


    @GetMapping("/one/{id}")
    @ApiOperation("查询医疗提供机构信息")
    public Result<MedicalProviderForm> query(
            @PathVariable(name = "id") @ApiParam(value = "医疗提供机构的id", required = true) Integer form) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(form);
    }

    @GetMapping("/multiple/{name}")
    @ApiOperation("查询医疗提供者信息")
    public Result<List<MedicalProviderForm>> queryByName(
            @PathVariable(name = "name", required = false) @ApiParam(value = "依据姓名模糊查询医疗提供机构")
            String name) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(name);
    }
}
