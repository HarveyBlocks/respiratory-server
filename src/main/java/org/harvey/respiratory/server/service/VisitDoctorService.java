package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.entity.VisitDoctor;
import org.harvey.respiratory.server.util.RangeDate;

import java.util.List;

/**
 * 就诊
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-09 20:28
 */

public interface VisitDoctorService extends IService<VisitDoctor> {
    Long queryMedicalProviderId(long visitDoctorId);

    VisitDoctor queryMedicalProviderAndPatientId(long visitId);

    VisitDoctor queryById(long visitDoctorId);

    void updateAfterInterview(VisitDoctor visitDoctor);

    Long createVisitDoctorId(long patientId, long medicalProviderId);

    List<VisitDoctor> doctorQuery(String identityCardId, RangeDate rangeDate, Page<VisitDoctor> page);

    List<VisitDoctor> patientQuery(long currentUserId, RangeDate rangeDate, Page<VisitDoctor> page);
}
