package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.harvey.respiratory.server.dao.FamilyRelationshipEntityMapper;
import org.harvey.respiratory.server.pojo.entity.FamilyRelationshipEntity;
import org.harvey.respiratory.server.service.FamilyRelationshipEntityService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-23 09:55
 * @see FamilyRelationshipEntity
 * @see FamilyRelationshipEntityMapper
 * @see FamilyRelationshipEntityService
 */
@Service
public class FamilyRelationshipEntityServiceImpl extends
        ServiceImpl<FamilyRelationshipEntityMapper, FamilyRelationshipEntity> implements
        FamilyRelationshipEntityService {

    @Override
    @NonNull
    public List<FamilyRelationshipEntity> query(Page<FamilyRelationshipEntity> page) {
        return super.lambdaQuery().page(page).getRecords();
    }
}