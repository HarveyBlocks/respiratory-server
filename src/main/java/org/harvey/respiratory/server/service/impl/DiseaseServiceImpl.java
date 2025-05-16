package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.DiseaseMapper;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.service.DiseaseService;
import org.springframework.stereotype.Service;


/**
 * Disease的Service实现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:32
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

}