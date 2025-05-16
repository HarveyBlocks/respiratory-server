package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalDepartmentMapper;
import org.harvey.respiratory.server.pojo.entity.MedicalDepartment;
import org.harvey.respiratory.server.service.MedicalDepartmentService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:37
 * @see MedicalDepartment
 * @see MedicalDepartmentMapper
 * @see MedicalDepartmentService
 */
@Service
public class MedicalDepartmentServiceImpl extends ServiceImpl<MedicalDepartmentMapper, MedicalDepartment> implements
        MedicalDepartmentService {
}