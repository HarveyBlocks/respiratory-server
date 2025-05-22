package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;

import java.util.List;

/**
 * 疾病
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface DiseaseService extends IService<Disease> {
    void deleteById(int id);

    List<Disease> selectByVisitDoctor(long visitId);

    Disease selectById(int id);

    List<Disease> selectByPage(Page<Disease> page);

    List<Disease> selectByName(String name, Page<Disease> page);

    List<Integer> queryIdsByName(String diseaseName);

    List<String> queryDiseaseNameByIds(List<Integer> diseaseIds);

    void validOnWrite(UserDto user);

    Integer register(Disease disease);
}
