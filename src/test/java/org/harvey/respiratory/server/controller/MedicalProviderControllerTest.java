package org.harvey.respiratory.server.controller;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderDepartment;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.MedicalProviderDepartmentService;
import org.harvey.respiratory.server.service.MedicalProviderFormService;
import org.harvey.respiratory.server.service.MedicalProviderJobService;
import org.harvey.respiratory.server.service.MedicalProviderService;
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

    private <T> List<Integer> selectIdSet(IService<T> service, SFunction<T, Integer> mapper) {
        return service.lambdaQuery().select(mapper).list().stream().map(mapper).distinct().collect(Collectors.toList());
    }

    private <T> List<Long> selectLongIdSet(IService<T> service, SFunction<T, Long> mapper) {
        return service.lambdaQuery().select(mapper).list().stream().map(mapper).distinct().collect(Collectors.toList());
    }

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
        List<Integer> formSet = selectIdSet(medicalProviderFormService, MedicalProviderForm::getId);
        List<Integer> departmentSet = selectIdSet(medicalProviderDepartmentService, MedicalProviderDepartment::getId);
        List<Integer> jobSet = selectIdSet(medicalProviderJobService, MedicalProviderJob::getId);
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
        List<Long> ids = this.selectLongIdSet(medicalProviderService, MedicalProvider::getId);
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

    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        medicalProviderController.del(1925528351854821377L);
    }


    @Test
    void query() {
        JacksonUtil.printPretty(medicalProviderController.query());
        JacksonUtil.printPretty(medicalProviderController.query("15958295141"));
        JacksonUtil.printPretty(medicalProviderController.query("A", null, 1, 2));
    }
}