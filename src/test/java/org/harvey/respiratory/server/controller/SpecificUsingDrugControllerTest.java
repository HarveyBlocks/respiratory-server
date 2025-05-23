package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.entity.SpecificUsingDrugRecord;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
import org.harvey.respiratory.server.pojo.enums.Role;
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
class SpecificUsingDrugControllerTest {
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
    @Resource
    private SpecificUsingDrugController specificUsingDrugController;

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        jacksonUtil.printPretty(specificUsingDrugController.del(1925812701934714882L));
    }

    @Test
    void updatePatientDrugHistory() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
           return;
        }
        SpecificUsingDrugRecord specificUsingDrugRecord = new SpecificUsingDrugRecord();
        specificUsingDrugRecord.setId(1925602978153148419L);
        specificUsingDrugRecord.setDosageUsed("更新了一个用法");
        // 1925787170468052993L
        jacksonUtil.printPretty(specificUsingDrugController.updatePatientDrugHistory(specificUsingDrugRecord));
    }

    @Test
    void queryDrugInVisit() {
        jacksonUtil.printPretty(specificUsingDrugController.queryDrugInVisit(1925567571042529282L));
    }

    @Test
    void queryPatientDrugHistoryByDrug() {
        List<Long> ids = ServiceUtil.selectUnique(patientService, Patient::getId);
        jacksonUtil.printPretty(specificUsingDrugController.queryPatientDrugHistoryByDrug(randomUtil.chose(ids),"A"));
    }

    @Resource
    private PatientService patientService;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void queryPatientDrugHistoryByDate() {
        List<Long> ids = ServiceUtil.selectUnique(patientService, Patient::getId);
        jacksonUtil.printPretty(
                specificUsingDrugController.queryPatientDrugHistoryByDate(randomUtil.chose(ids), "2000-10-02", null));
    }
}