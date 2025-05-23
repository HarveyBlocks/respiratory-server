package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.TakeVisitNumberDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.PatientService;
import org.harvey.respiratory.server.service.ServiceUtil;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.RandomUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class VisitDoctorControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        // 医疗医生
        UserHolder.saveUser(new UserDto(1923431434245177347L, "", Role.NORMAL_DOCTOR, "330282200410084384"));
    }

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private VisitDoctorController visitDoctorController;
    @Resource
    private PatientService patientService;
    @Resource
    private MedicalProviderService medicalProviderService;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void createVisitDoctorId() {
        List<Long> patientIds = ServiceUtil.selectUnique(patientService, Patient::getId);
        List<Long> medicalProviderIds = ServiceUtil.selectUnique(medicalProviderService, MedicalProvider::getId);
        Long patientId = randomUtil.chose(patientIds);
        Long medicalProviderId = randomUtil.chose(medicalProviderIds);
        jacksonUtil.printPretty(
                visitDoctorController.createVisitDoctorId(new TakeVisitNumberDto(patientId, medicalProviderId)));
    }
    @Resource
    private JacksonUtil jacksonUtil;
    @Test
    void getById() {
        jacksonUtil.printPretty(visitDoctorController.getById(1925567571042529282L));
    }

    @Test
    void doctorQuery() {
        // doctor id: 1925528143926394882
        jacksonUtil.printPretty(visitDoctorController.doctorQuery("2000-01-01", "2025-12-01", 1, 3));
        jacksonUtil.printPretty(visitDoctorController.doctorQuery("2000-01-01", "2025-12-01", 2, 3));
        jacksonUtil.printPretty(visitDoctorController.doctorQuery("2000-01-01", "2025-12-01", 3, 3));
    }

    @Test
    void userQuery() {
        jacksonUtil.printPretty(visitDoctorController.userQuery("2000-01-01", "2025-12-01", 1, 3));
        jacksonUtil.printPretty(visitDoctorController.userQuery("2000-01-01", "2025-12-01", 2, 3));
        jacksonUtil.printPretty(visitDoctorController.userQuery("2000-01-01", "2025-12-01", 3, 3));
    }
}