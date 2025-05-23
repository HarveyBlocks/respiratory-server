package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.ExpenseRecordMapper;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.ExpenseRecord;
import org.harvey.respiratory.server.service.ExpenseRecordService;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.service.UserPatientIntermediationService;
import org.harvey.respiratory.server.service.VisitDoctorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:35
 * @see ExpenseRecord
 * @see ExpenseRecordMapper
 * @see ExpenseRecordService
 */
@Service
public class ExpenseRecordServiceImpl extends ServiceImpl<ExpenseRecordMapper, ExpenseRecord> implements
        ExpenseRecordService {

    @Override
    public void saveOnInterview(List<ExpenseRecord> expenseRecordList) {
        boolean saved = super.saveBatch(expenseRecordList);
        if (saved) {
            log.debug("更新费用记录成功");
        } else {
            log.warn("更新费用记录失败");
        }
    }

    @Resource
    private VisitDoctorService visitDoctorService;

    @Override
    public List<ExpenseRecord> querySelfExpenseRecord(UserDto currentUser, long visitId) {
        visitDoctorService.queryValid(currentUser, visitId);
        return super.lambdaQuery().eq(ExpenseRecord::getVisitDoctorId, visitId).list();
    }
}