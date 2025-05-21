package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.entity.SpecificUsingDrugRecord;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 症状表现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface SymptomaticPresentationService extends IService<SymptomaticPresentation> {
    void logicDelete(Long symptomaticPresentationId, String currentUserIdentityCardId);

    /**
     * @return 更新后的新记录的字敦啊
     */
    Long updateRetainTrace(String currentUserIdentityCardId, Long oldVersionId, SymptomaticPresentation newData);

    SymptomaticPresentation queryByIdIgnoreDeleted(Long oldVersionId);

    SymptomaticPresentation queryById(Long oldVersionId);

    @Transactional
    void logicUpdate(Long oldId, SymptomaticPresentation newData);


    /**
     * 只允许服务器内调用, 只有医生用户可调用
     * 外部进行校验
     * @return 插入后的多个症状的id
     */
    @Transactional
    List<Long> saveSymptomaticPresentationBatch(List<SymptomaticPresentation> presentationList);
    /**
     * 读不出已经逻辑删除的
     */
    List<SymptomaticPresentation> selectByVisitId(long visitId);

}
