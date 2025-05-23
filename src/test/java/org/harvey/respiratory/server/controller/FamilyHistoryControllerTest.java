package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.PatientDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.entity.FamilyHistory;
import org.harvey.respiratory.server.pojo.entity.FamilyRelationshipEntity;
import org.harvey.respiratory.server.pojo.entity.Patient;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.DiseaseService;
import org.harvey.respiratory.server.service.FamilyRelationshipEntityService;
import org.harvey.respiratory.server.service.PatientService;
import org.harvey.respiratory.server.service.ServiceUtil;
import org.harvey.respiratory.server.util.ConstantsInitializer;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.RandomUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class FamilyHistoryControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private JacksonUtil jacksonUtil;
    @Resource
    private FamilyHistoryController familyHistoryController;
    @Resource
    private RandomUtil randomUtil;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private PatientService patientService;
    @Resource
    private FamilyRelationshipEntityService familyRelationshipEntityService;

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<FamilyHistory> list = createData(191);
        for (FamilyHistory familyHistory : list) {
            familyHistoryController.register(familyHistory);
        }
    }

    private List<FamilyHistory> createData(int count) {
        ArrayList<FamilyHistory> data = new ArrayList<>();
        List<Long> patientIds = ServiceUtil.selectUnique(patientService, Patient::getId);
        List<Integer> diseaseIds = ServiceUtil.selectUnique(diseaseService, Disease::getId);
        List<Integer> relationshipIds = ServiceUtil.selectUnique(
                familyRelationshipEntityService, FamilyRelationshipEntity::getId);
        for (int i = 0; i < count; i++) {
            Long patientId = randomUtil.chose(patientIds);
            Integer familyRelationshipId = randomUtil.chose(relationshipIds);
            Integer diseaseId = randomUtil.chose(diseaseIds);
            String livingEnvironmentCommonFactor = randomUtil.randomString(5, 60);
            boolean livingInSmokingEnvironment = randomUtil.bit(0.2);
            data.add(new FamilyHistory(null, patientId, familyRelationshipId, diseaseId, livingEnvironmentCommonFactor,
                    livingInSmokingEnvironment
            ));
        }
        return data;
    }

    @Test
    void queryFamilyHistory() {
        List<Long> patientIds = patientService.querySelfPatients(
                        UserHolder.currentUserId(), ConstantsInitializer.initPage(1, 100))
                .stream()
                .map(PatientDto::getId)
                .collect(Collectors.toList());
        Long chosen = randomUtil.chose(patientIds);
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistory(chosen, 1, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistory(chosen, 2, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistory(chosen, 3, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistory(chosen, 4, 3));
    }

    @Test
    void queryFamilyHistoryByName() {
        List<Long> patientIds = patientService.querySelfPatients(
                        UserHolder.currentUserId(), ConstantsInitializer.initPage(1, 100))
                .stream()
                .map(PatientDto::getId)
                .collect(Collectors.toList());
        Long chosen = randomUtil.chose(patientIds);
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByName(chosen, "A", 1, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByName(chosen, "B", 2, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByName(chosen, "C", 3, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByName(chosen, "D", 1, 3));
    }

    @Test
    void queryFamilyHistoryByDiseaseId() {
        List<Long> patientIds = patientService.querySelfPatients(
                        UserHolder.currentUserId(), ConstantsInitializer.initPage(1, 100))
                .stream()
                .map(PatientDto::getId)
                .collect(Collectors.toList());
        Long chosen = randomUtil.chose(patientIds);
        List<Integer> diseaseIds = ServiceUtil.selectUnique(diseaseService, Disease::getId);
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByDiseaseId(chosen, randomUtil.chose(diseaseIds), 1, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByDiseaseId(chosen, randomUtil.chose(diseaseIds), 2, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByDiseaseId(chosen, randomUtil.chose(diseaseIds), 3, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByDiseaseId(chosen, randomUtil.chose(diseaseIds), 4, 3));
    }

    @Test
    void queryFamilyHistoryByRelationship() {
        List<Long> patientIds = patientService.querySelfPatients(
                        UserHolder.currentUserId(), ConstantsInitializer.initPage(1, 100))
                .stream()
                .map(PatientDto::getId)
                .collect(Collectors.toList());
        Long chosen = randomUtil.chose(patientIds);
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByRelationship(chosen, "1,3,4", 1, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByRelationship(chosen, "3,5,1", 2, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByRelationship(chosen, "", 3, 3));
        jacksonUtil.printPretty(familyHistoryController.queryFamilyHistoryByRelationship(chosen, "1,2,4,1,4", 4, 3));
    }
}