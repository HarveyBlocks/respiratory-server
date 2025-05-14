package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
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
@Api(tags = {"医疗提供者"})
@RequestMapping("/medical/provider")
public class MedicalProviderController {
    @PostMapping("/register")
    @ApiOperation("登记医疗提供者信息")
    @ApiResponse(code = 200, message = "返回插入后的id")
    public Result<Long> registerPatientInformation(
            @RequestBody@ApiParam("新增时, 除id, 和医保相关外, 字段都不得为null.")
            MedicalProvider medicalProvider) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(medicalProvider);
    }

    @PutMapping("/")
    @ApiOperation("更新医疗提供者信息")
    public Result<Long> update(
            @RequestBody
            @ApiParam("更新字段可以为null, 表示保留原有; 也可以不为null, 即使字段值没有发生变化")
            MedicalProvider medicalProvider) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(medicalProvider);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除医疗提供者信息")
    public Result<NullPlaceholder> del(@PathVariable("id") @ApiParam("医疗提供者 id") Long id) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(id);
    }


    @GetMapping("/self")
    @ApiOperation("查询医疗提供者自己的信息")
    public Result<MedicalProvider> query() {
        // 任何医疗提供者可执行
        throw new UnfinishedException();
    }

    @GetMapping("/one/{id}")
    @ApiOperation("查询医疗提供者信息")
    public Result<MedicalProvider> query(@PathVariable("id") @ApiParam("医疗提供者 id") Long id) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(id);
    }

    @GetMapping("/one/{phoneNumber}")
    @ApiOperation("查询医疗提供者信息")
    public Result<MedicalProvider> query(
            @PathVariable("phoneNumber") @ApiParam("医疗提供者电话") String phoneNumber) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(phoneNumber);
    }

    @GetMapping("/all/{form}/{page}/{limit}")
    @ApiOperation("查询医疗提供者信息")
    public Result<List<MedicalProvider>> query(
            @PathVariable(name = "form", required = false) @ApiParam(value = "医疗提供机构的id, 缺省则全查")
            Integer form,
            @PathVariable(name = "page", required = false) @ApiParam("页码, 从1开始, 缺省则是1") Integer page,
            @PathVariable(name = "limit", required = false) @ApiParam("页面长度, 缺省则是10") Integer limit) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(form, page, limit);
    }

    @GetMapping("/multiple/{name}")
    @ApiOperation("查询医疗提供者信息")
    public Result<List<MedicalProvider>> queryByName(
            @PathVariable(name = "name") @ApiParam(value = "依据姓名模糊查询医疗提供者") String name) {
        // 当前用户必须是主管/开发者
        throw new UnfinishedException(name);
    }


}
