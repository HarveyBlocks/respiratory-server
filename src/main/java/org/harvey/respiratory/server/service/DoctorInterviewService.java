package org.harvey.respiratory.server.service;


import org.harvey.respiratory.server.pojo.dto.InterviewDto;
import org.harvey.respiratory.server.pojo.entity.ExpenseRecord;
import org.harvey.respiratory.server.pojo.entity.SpecificUsingDrugRecord;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-21 15:27
 */
public interface DoctorInterviewService {
    void interview(InterviewDto interviewDto, String currentUserIdentifierCardId);

    @Transactional
    void transitionallySaveRecords(
            long visitDoctorId,
            List<ExpenseRecord> expenseRecordList,
            List<SpecificUsingDrugRecord> usingDrugRecords,
            List<SymptomaticPresentation> symptomaticPresentationList,
            List<Integer> diseaseIds);
}
