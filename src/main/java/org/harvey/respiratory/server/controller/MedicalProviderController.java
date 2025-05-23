package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.util.ConstantsInitializer;
import org.harvey.respiratory.server.util.RoleConstant;
import org.harvey.respiratory.server.util.RoleUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    private MedicalProviderService medicalProviderService;
    @Resource
    private RoleService roleService;

    @PostMapping("/register")
    @ApiOperation("登记医疗提供者信息")
    @ApiResponse(code = 200, message = "返回插入后的id")
    public Result<Long> register(
            @RequestBody @ApiParam("新增时, 除id, 和医保相关外, 字段都不得为null.") MedicalProvider medicalProvider) {
        // 当前用户必须是主管/开发者
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        RoleUtil.validOnRole(user.getRole(), RoleConstant.MEDICAL_PROVIDER_UPDATE);
        return Result.success(medicalProviderService.register(medicalProvider));
    }

    @PutMapping("/")
    @ApiOperation("更新医疗提供者信息")
    public Result<NullPlaceholder> update(
            @RequestBody @ApiParam("更新字段可以为null, 表示保留原有; 也可以不为null, 即使字段值没有发生变化")
            MedicalProvider medicalProvider) {
        // 当前用户必须是主管/开发者
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        RoleUtil.validOnRole(user.getRole(), RoleConstant.MEDICAL_PROVIDER_UPDATE);
        medicalProviderService.update(medicalProvider);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除医疗提供者信息")
    public Result<NullPlaceholder> del(@PathVariable("id") @ApiParam("医疗提供者 id") Long id) {
        // 当前用户必须是主管/开发者
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        RoleUtil.validOnRole(user.getRole(), RoleConstant.MEDICAL_PROVIDER_UPDATE);
        medicalProviderService.delete(id);
        return Result.ok();
    }


    @GetMapping("/self")
    @ApiOperation("查询医疗提供者自己的信息")
    public Result<MedicalProvider> query() {
        // 任何医疗提供者可执行
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        RoleUtil.validOnRole(user.getRole(), RoleConstant.MEDICAL_PROVIDER_READ);
        MedicalProvider medicalProvider;
        try {
            medicalProvider = medicalProviderService.selectByUser(user.getId());
        } catch (ResourceNotFountException e) {
            throw new ResourceNotFountException("不能通过当前用户的身份证查询到医疗提供者信息", e);
        }
        return Result.success(medicalProvider);
    }

    @GetMapping("/one/id/{id}")
    @ApiOperation("查询医疗提供者信息")
    public Result<MedicalProvider> query(@PathVariable("id") @ApiParam("医疗提供者 id") Long id) {
        throw new UnfinishedException(id);
    }

    @GetMapping("/one/phone/{phoneNumber}")
    @ApiOperation("查询医疗提供者信息")
    public Result<MedicalProvider> query(
            @PathVariable("phoneNumber") @ApiParam("医疗提供者电话") String phoneNumber) {
        return Result.success(medicalProviderService.selectByPhone(phoneNumber));
    }


    @GetMapping(value = {"/all/{form}/{name}/{page}/{limit}", "/all/{form}/{name}/{page}", "/all/{form}/{name}",
            "/all/{form}", "/all"})
    @ApiOperation("查询医疗提供者信息")
    public Result<List<MedicalProvider>> query(
            @PathVariable(value = "name", required = false) @ApiParam(value = "医生名, 模糊查询, 缺省则全查")
            String name,
            @PathVariable(value = "form", required = false) @ApiParam(value = "医疗提供机构的id, 缺省则全查")
            Integer form,
            @PathVariable(value = "page", required = false) @ApiParam("页码, 从1开始, 缺省则是1") Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页面长度", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        return Result.success(
                medicalProviderService.selectByAny(name, form, ConstantsInitializer.initPage(page, limit)));
    }


}
