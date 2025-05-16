package org.harvey.respiratory.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.UnfinishedException;
import org.harvey.respiratory.server.pojo.entity.SpecificUsingDrugRecord;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药物使用
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 20:57
 */
@Slf4j
@RestController
@Api(tags = "针对某一问诊的药物具体使用")
@RequestMapping("/special-using-drug")
public class SpecificUsingDrugController {
    @DeleteMapping("{visitDoctorId}/{drugId}")
    @ApiOperation("删除某一问诊的某一具体用药")
    public Result<NullPlaceholder> del(
            @PathVariable("visitDoctorId") @ApiParam(value = "问诊id", required = true) Long visitDoctorId,
            @PathVariable("drugId") @ApiParam(value = "药品id", required = true) Long drugId) {
        // 由于药留证据, 所以是逻辑删除
        // visitDoctorId的medical provider才有权限删除
        throw new UnfinishedException();
    }

    @PutMapping("/")
    @ApiOperation("更新某一问诊的需要用药")
    public Result<NullPlaceholder> updatePatientDrugHistory(
            @RequestBody SpecificUsingDrugRecord specificUsing) {
        // 更新药品使用
        // 医生可以更新, 用户不行
        // 为了保留证据, 旧信息逻辑删除, 本信息保持不变
        // update specific_using set specific_using.deleted = true where id = param.id and (
        //  specific_using.name != param.name or
        //  specific_using.count != param.count or
        //  specific_using.drugId != param.drugId
        // ....
        // )
        // 只有在有更改的情况下, 就将旧数据删除, 否则不删除
        // 然后插入新药品
        // 这个API给用户限流, 如果一个用户短时间内大量对数据进行更新, 那么认为他不可信, 就拒绝其访问该接口, 然后将用户保存
        // 先更新数据库, 再删除缓存
        throw new UnfinishedException();
    }

    @GetMapping("visit/{id}")
    @ApiOperation("查询该问诊的有关症状")
    public Result<List<SpecificUsingDrugRecord>> queryVisitDrug(
            @PathVariable("id") @ApiParam(value = "问诊号", required = true) Long visitId) {
        throw new UnfinishedException();
    }


}
