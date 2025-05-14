package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.dto.InterviewDto;
import org.harvey.respiratory.server.pojo.dto.TakeVisitNumberDto;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 医生问诊
 * 由于涉及好多其他模块的工作, 所以单独提出来
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 13:27
 */
@Slf4j
@RestController
@Api(tags = {"医生问诊"})
@RequestMapping("/interview")
public class DoctorInterviewController {
    @PostMapping("/execute")
    @ApiOperation(
            "问诊. 1. 更新就诊信息 2. 生成多条费用单 3. 计算费用价格 4. 插入多条具体药物使用 5. 插入多个症状的一系列操作具有原子性")
    public Result<NullPlaceholder> interview(
            @RequestBody @ApiParam("interview")
            InterviewDto interviewDto) {
        // 1. 获取就诊信息id
        // 3.
        // 4. 获取数据库中病患id和医生id
        // 5. 依据医生id 查询医生职位和所在科室, 计算此次问诊费用, 并生成费用记录
        // 6. 依次插入症状信息
        // 7. 插入疾病-就诊中间表
        // 7. 从药物使用中取出药物id, 查询出药物单价, 与药物数量相乘, 并生成费用记录
        // 8. 将所有费用记录相加, 获取总费用, 更新
        // 9. 已知总费用, 就诊时间就是执行当前业务的时间
        // 10. 依据id更新数据库就诊信息
        throw new UnfinishedException(interviewDto);
    }

}
