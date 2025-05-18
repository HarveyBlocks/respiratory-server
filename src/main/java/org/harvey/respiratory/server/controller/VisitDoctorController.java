package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.TakeVisitNumberDto;
import org.harvey.respiratory.server.pojo.entity.VisitDoctor;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 医生进行问诊
 * <pre>{@code
 * 药->特殊用药 ↘
 *      ↓     问诊 ->费用->医保付费
 * (既往用药)   ↗ ↖
 * 症状   疾病->家族史
 * }</pre>
 * 1. 症状
 * 2. 疾病
 * 3. 家族史
 * 4. 药
 * 5. 特殊用药
 * 6. 问诊interview
 * 6. 问诊visit_doctor
 * 7. 付费
 * 8. 医保付费
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-12 23:40
 */
@Slf4j
@RestController
@Api(tags = "就诊")
@RequestMapping("/visit")
public class VisitDoctorController {
    @PostMapping("/take-number")
    @ApiOperation("取号")
    public Result<Long> createVisitDoctorId(
            @RequestBody @ApiParam("takeVisitNumber") TakeVisitNumberDto medicalProviderId) {
        // 插入取号信息
        // 返回取号id
        throw new UnfinishedException();
    }


    @GetMapping("/{id}")
    @ApiOperation("获取就诊信息")
    public Result<VisitDoctor> getById(@PathVariable("id") @ApiParam("就诊表的id") Long id) {
        // 药物医生能依据具体就诊号查询到需要的具体用药信息, 其余查不到, 也就是说, 这个方法药物医生是查不到任何东西的
        // 普通医生要进行校验
        // 主管医生可以进行查询
        throw new UnfinishedException();
    }


    @GetMapping(value = {"/all/role/{start-date}/{end-date}/{page}/{limit}", "/all/role/{start-date}/{end-date}/{page}",
            "/all/role/{start-date}/{end-date}"})
    @ApiOperation("查询当前角色相关寻访信息")
    public Result<List<VisitDoctor>> queryByRole(
            @PathVariable(value = "start-date") @ApiParam(value = "yyyy-MM-dd, 0补前", required = true)
            String startDate,
            @PathVariable(value = "end-date") @ApiParam(value = "yyyy-MM-dd, 0补前", required = true) String endDate,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页面长度", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 依据权限, 如果是患者查询, 则患者相关
        // 如果是医生查询, 就是医生相关
        SimpleDateFormat simpleDateFormat = Constants.DEFAULT_DATE_FORMAT;
        throw new UnfinishedException();
    }

    @GetMapping(value = {"/all/any/{start-date}/{end-date}/{page}/{limit}", "/all/any/{start-date}/{end-date}/{page}",
            "/all/any/{start-date}/{end-date}",})
    @ApiOperation("查询任意寻访信息")
    public Result<List<VisitDoctor>> queryAny(
            @PathVariable(value = "start-date") @ApiParam("YYYY-MM-DD, 0补前") String startDate,
            @PathVariable(value = "end-date") @ApiParam("YYYY-MM-DD, 0补前") String endDate,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页码, 从1开始", defaultValue = "1")
            Integer page,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页面长度", defaultValue = Constants.DEFAULT_PAGE_SIZE_MSG) Integer limit) {
        // 依据权限, 如果是患者查询, 则患者相关
        // 如果是医生查询, 就是医生相关
        throw new UnfinishedException();
    }
}
