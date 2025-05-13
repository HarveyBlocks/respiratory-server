package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
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

    @GetMapping("/{id}")
    @ApiOperation("获取就诊信息")
    public Result<?> getById(@PathVariable("id") @ApiParam("就诊表的id") long id) {
        throw new UnfinishedException();
    }


    @PostMapping("/")
    @ApiOperation("插入就诊信息, 这是一大坨")
    public Result<List<?>> insert(long id) {
        throw new UnfinishedException();
    }
}
