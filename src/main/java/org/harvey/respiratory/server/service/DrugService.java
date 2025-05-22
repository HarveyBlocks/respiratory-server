package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Drug;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 疾病
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface DrugService extends IService<Drug> {
    void writeValid(UserDto user);

    void deleteById(long drugId);

    Drug queryById(long id);

    List<Drug> queryByName(String name, Page<Drug> page);

    Map<Integer, Drug> queryByIds(Collection<Integer> drugIds);

    Map<Integer, Drug> queryByIdsFilterName(Set<Integer> drugIds, String name);

    void saveDrug(Drug drug);
}
