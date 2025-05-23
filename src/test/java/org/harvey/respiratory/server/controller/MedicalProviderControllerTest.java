package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderDepartment;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.*;
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

import static org.harvey.respiratory.server.service.ServiceUtil.selectIntegerIds;
import static org.harvey.respiratory.server.service.ServiceUtil.selectLongIds;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class MedicalProviderControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }


    @Resource
    private MedicalProviderFormService medicalProviderFormService;
    @Resource
    private MedicalProviderJobService medicalProviderJobService;
    @Resource
    private MedicalProviderDepartmentService medicalProviderDepartmentService;


    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private MedicalProviderController medicalProviderController;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<MedicalProvider> data = createData(12);
        for (MedicalProvider each : data) {
            medicalProviderController.register(each);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<MedicalProvider> createData(int count) {
        List<Integer> formSet = selectIntegerIds(medicalProviderFormService, MedicalProviderForm::getId);
        List<Integer> departmentSet = selectIntegerIds(medicalProviderDepartmentService, MedicalProviderDepartment::getId);
        List<Integer> jobSet = selectIntegerIds(medicalProviderJobService, MedicalProviderJob::getId);
        List<MedicalProvider> medicalProviders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String identityCardId = "33028220041008" + String.format("%04d", randomUtil.uniform(0, 9999));
            String name = randomUtil.randomString(10, 60);
            Integer formId = randomUtil.chose(formSet);
            Integer departmentId = randomUtil.chose(departmentSet);
            Integer jobId = randomUtil.chose(jobSet);

            MedicalProvider medicalProvider = new MedicalProvider(
                    null, identityCardId, name, formId, departmentId, jobId);
            medicalProviders.add(medicalProvider);
        }
        return medicalProviders;
    }

    @Resource
    private MedicalProviderService medicalProviderService;

    @Test
    void update() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<Long> ids = selectLongIds(medicalProviderService, MedicalProvider::getId);
        List<MedicalProvider> data = createData(5);
        for (MedicalProvider each : data) {
            each.setId(randomUtil.chose(ids));
            medicalProviderController.update(each);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Resource
    private JacksonUtil jacksonUtil;
    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        medicalProviderController.del(1925528351854821377L);
    }


    @Test
    void query() {
        jacksonUtil.printPretty(medicalProviderController.query());
        jacksonUtil.printPretty(medicalProviderController.query("15958295141"));
        jacksonUtil.printPretty(medicalProviderController.query("A", null, 1, 2));
    }
}