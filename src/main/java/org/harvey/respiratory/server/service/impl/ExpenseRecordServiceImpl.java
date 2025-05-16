package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.ExpenseRecordMapper;
import org.harvey.respiratory.server.pojo.entity.ExpenseRecord;
import org.harvey.respiratory.server.service.ExpenseRecordService;
import org.springframework.stereotype.Service;


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

}