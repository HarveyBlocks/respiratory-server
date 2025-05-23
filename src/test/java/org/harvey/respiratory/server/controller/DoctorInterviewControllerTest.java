package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.InterviewDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.entity.Drug;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.pojo.enums.Severity;
import org.harvey.respiratory.server.service.DiseaseService;
import org.harvey.respiratory.server.service.DrugService;
import org.harvey.respiratory.server.service.ServiceUtil;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.RandomUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DoctorInterviewControllerTest {
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
    private DoctorInterviewController doctorInterviewController;
    @Resource
    private RandomUtil randomUtil;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private DrugService drugService;

    @Test
    void interview() {
        /*if (!OPEN_NON_IDEMPOTENT_TEST){
            return;
        }*/
        jacksonUtil.printPretty(doctorInterviewController.interview(createDto(1925757829877166081L)));
    }

    private InterviewDto createDto(long visitId) {
        List<Integer> drugList = ServiceUtil.selectUnique(drugService, Drug::getId);
        List<InterviewDto.SpecificUsingDrugRecordDto> specificUsingDrugRecordDtoList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Integer drugId = randomUtil.chose(drugList);
            Integer count = randomUtil.uniform(1, 5);
            String dosageUsed = randomUtil.randomString(10, 60);
            Integer dayTimeUsed = randomUtil.uniform(1, 5);
            String frequencyUsed = randomUtil.randomString(1, 20);
            Date treatStart = new Date(System.currentTimeMillis() - randomUtil.uniform(360000, 360000 * 120));
            Date treatEnd = new Date(System.currentTimeMillis() - randomUtil.uniform(360000, 360000 * 120));
            String otherMedicationGuidance = randomUtil.randomString(10, 250);
            specificUsingDrugRecordDtoList.add(
                    new InterviewDto.SpecificUsingDrugRecordDto(drugId, count, dosageUsed, dayTimeUsed, frequencyUsed,
                            treatStart, treatEnd, otherMedicationGuidance
                    ));
        }
        List<InterviewDto.SymptomaticPresentationDto> symptomaticPresentationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            String name = randomUtil.randomString(5, 60);
            Severity severity = randomUtil.chose(Severity.values());
            String frequency = randomUtil.randomString(5, 60);
            Date startTime = new Date(System.currentTimeMillis() - randomUtil.uniform(360000, 360000 * 120));
            String incentive = randomUtil.randomString(5, 60);
            String environmentalFactor = randomUtil.randomString(5, 60);
            String signDescription = randomUtil.randomString(5, 60);
            String description = randomUtil.randomString(5, 250);
            symptomaticPresentationList.add(
                    new InterviewDto.SymptomaticPresentationDto(name, severity, frequency, startTime, incentive,
                            environmentalFactor, signDescription, description
                    ));
        }
        List<Integer> diseaseIds = ServiceUtil.selectUnique(diseaseService, Disease::getId);
        return new InterviewDto(visitId, "测试的问诊", "其他辅助治疗", symptomaticPresentationList,
                specificUsingDrugRecordDtoList, diseaseIds
        );
    }

}