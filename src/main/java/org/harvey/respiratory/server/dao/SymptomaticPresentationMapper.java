package org.harvey.respiratory.server.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;

/**
 * 症状表现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */
@Mapper
public interface SymptomaticPresentationMapper extends BaseMapper<SymptomaticPresentation> {
}
