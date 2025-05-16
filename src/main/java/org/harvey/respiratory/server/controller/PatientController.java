package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.harvey.respiratory.server.service.PatientService;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 患者与医保
 * 如果一个系统的使用者含有多个患者的记录
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 22:07
 */
@Slf4j
@RestController
@Api(tags = "患者")
@RequestMapping("/patient")
public class PatientController {
    @Resource
    private PatientService patientService;

    @PostMapping("/register")
    @ApiOperation("登记患者信息+医保信息, 响应患者id")
    public Result<Long> registerPatientInformation(
            @RequestBody @ApiParam("新增时, 除id, 和医保相关外, 字段都不得为null.") PatientDto patientDto) {
        Long currentUserId = UserHolder.currentUserId();
        if (currentUserId == null) {
            throw new UnauthorizedException("登录后可使用");
        }
        return Result.success(patientService.registerPatientInformation(patientDto, currentUserId));
    }

    @PutMapping("/")
    @ApiOperation("更新患者信息, 不会更新医保信息")
    public Result<Long> update(
            @RequestBody
            @ApiParam(
                    "更新字段可以为null, 表示保留原有; 也可以不为null, 即使字段值没有发生变化. 一般不把非null字段重新更新为null.")
            Patient patient) {
        // 当前患者存在个人信息才能更新
        // 用phone检查是否存在
        // 如果不存在, 新增, 存在更新
        // 然后返回id
        // 如果医保更新了, 如果旧医保还有费用, 就抛出异常
        // 如果
        throw new UnfinishedException(patient);
    }

    @GetMapping("/self")
    @ApiOperation("查询当前用户的所有患者")
    public Result<List<PatientDto>> querySelfPatient() {
        throw new UnfinishedException();
    }

    @GetMapping("/healthcare/{id}")
    @ApiOperation("依据医保号查询")
    public Result<PatientDto> queryPatientByHealthcareId(@PathVariable("id") @ApiParam("医保号") Long healthcareId) {
        throw new UnfinishedException(healthcareId);
    }

    @GetMapping("/{id}")
    @ApiOperation("依据患者id查询")
    public Result<PatientDto> queryPatientByPatientId(@PathVariable("id") @ApiParam("patient id") Long patientId) {
        // 是否需要校验, 当前用户是否有权限查询这个病患的信息?
        throw new UnfinishedException(patientId);
    }

    @GetMapping("/identityCardId/{cardId}")
    @ApiOperation("依据身份证号吗查询")
    public Result<NullPlaceholder> queryPatientByPhone(
            @PathVariable("cardId") @ApiParam("电话号码") String cardId) {
        throw new UnfinishedException();
    }


}
