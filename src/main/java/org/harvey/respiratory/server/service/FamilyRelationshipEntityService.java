package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.entity.FamilyRelationshipEntity;
import org.harvey.respiratory.server.util.ConstantsInitializer;

import java.util.List;

/**
 * 家族关系
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-23 09:53
 */
public interface FamilyRelationshipEntityService extends IService<FamilyRelationshipEntity> {

    List<FamilyRelationshipEntity> query(Page<FamilyRelationshipEntity> page);
}
