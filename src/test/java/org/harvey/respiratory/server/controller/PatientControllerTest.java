package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.pojo.enums.Sex;
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class PatientControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }
    @Resource
    private JacksonUtil jacksonUtil;
    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private PatientController patientController;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void registerPatientInformation() throws ParseException {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        for (int i = 0; i < 12; i++) {
            PatientDto patientDto = createData();
            patientController.registerPatientInformation(patientDto);
        }
    }

    private PatientDto createData() throws ParseException {
        String phone = "1836652" + String.format("%04d", randomUtil.uniform(0, 9999));
        String identityCardId = "33028220040223" + String.format("%04d", randomUtil.uniform(0, 9999));
        String name = randomUtil.randomString(7, 40);
        Sex sex = Sex.values()[randomUtil.uniform(0, 2)];
        Date birthDate = Constants.DEFAULT_DATE_FORMAT.parse("2004-02-23");
        String address = randomUtil.randomString(7, 40);
        float height = 1.7f;
        float weight = 64f;
        String healthcareCode = randomUtil.randomString(20, 21);
        String healthcareType = "NORMAL";
        Integer balance = 0;
        return new PatientDto(null, phone, identityCardId, name, sex, birthDate, address, height, weight,
                healthcareCode, healthcareType, balance
        );
    }

    @Test
    void delPatientInformation() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<Long> ids = ServiceUtil.selectLongIds(patientService, Patient::getId);
        Long chose = randomUtil.chose(ids);
        log.debug("id = {}", chose);
        patientController.delPatientInformation(chose);

    }

    @Resource
    private PatientService patientService;

    @Test
    void update() throws ParseException {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<Long> ids = ServiceUtil.selectLongIds(patientService, Patient::getId);
        for (int i = 0; i < 12; i++) {
            Patient patient = createData().buildPatient();
            Long chosen = randomUtil.chose(ids);
            patient.setId(chosen);
            patientController.update(patient);
        }
    }

    @Test
    void querySelfPatients() {
        jacksonUtil.printPretty(patientController.querySelfPatients(1, 5));
        jacksonUtil.printPretty(patientController.querySelfPatients(2, 5));
        jacksonUtil.printPretty(patientController.querySelfPatients(3, 5));
    }

    @Test
    void queryPatientByHealthcareId() {
        jacksonUtil.printPretty(patientController.queryPatientByHealthcareId("1SLpkD2eMk4Cy6WENxAG"));
    }

    @Test
    void queryPatientByPatientId() {
        List<Long> ids = ServiceUtil.selectLongIds(patientService, Patient::getId);
        Long chose = randomUtil.chose(ids);
        log.debug("id = {}", chose);
        jacksonUtil.printPretty(patientController.queryPatientByPatientId(chose));
    }

    @Test
    void queryPatientByPhone() {
        List<String> phones = ServiceUtil.selectUnique(patientService, Patient::getIdentityCardId);
        String chose = randomUtil.chose(phones);
        log.debug("phone = {}", chose);
        jacksonUtil.printPretty(patientController.queryByIdentity(chose));
    }
}