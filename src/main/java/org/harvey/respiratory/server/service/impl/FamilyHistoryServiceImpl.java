package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.FamilyHistoryMapper;
import org.harvey.respiratory.server.pojo.entity.FamilyHistory;
import org.harvey.respiratory.server.service.FamilyHistoryService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:36
 * @see FamilyHistory
 * @see FamilyHistoryMapper
 * @see FamilyHistoryService
 */
@Service
public class FamilyHistoryServiceImpl extends ServiceImpl<FamilyHistoryMapper, FamilyHistory> implements
        FamilyHistoryService {

}