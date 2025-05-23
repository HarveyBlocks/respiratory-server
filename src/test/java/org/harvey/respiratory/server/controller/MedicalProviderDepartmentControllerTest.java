package org.harvey.respiratory.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderDepartment;
import org.harvey.respiratory.server.pojo.enums.Role;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class MedicalProviderDepartmentControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;
    @Resource
    private JacksonUtil jacksonUtil;
    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private MedicalProviderDepartmentController medicalProviderDepartmentController;
    @Resource
    private RandomUtil randomUtil;

    private List<MedicalProviderDepartment> createData(int len) {
        List<MedicalProviderDepartment> list = new ArrayList<>(len);
        while (len-- > 0) {
            String name = randomUtil.randomString(5, 12);
            Integer expenseEveryVisit = randomUtil.uniform(5000, 50000);
            String description = randomUtil.randomString(5, 250);
            list.add(new MedicalProviderDepartment(null, name, null, expenseEveryVisit, description));
        }
        return list;
    }

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<MedicalProviderDepartment> dataList = createData(randomUtil.uniform(10, 20));
        for (MedicalProviderDepartment data : dataList) {
            medicalProviderDepartmentController.register(data);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void update() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        int uniform = randomUtil.uniform(1, 5);
        List<MedicalProviderDepartment> dataList = createData(uniform);
        for (MedicalProviderDepartment data : dataList) {
            data.setId(randomUtil.uniform(1, uniform));
            medicalProviderDepartmentController.update(data);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
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
        long id = randomUtil.uniform(10, 20);
        log.debug("id = {}", id);
        medicalProviderDepartmentController.del(id);
    }

    @Test
    void query() {
        jacksonUtil.printPretty(medicalProviderDepartmentController.query(2, 3));
        jacksonUtil.printPretty(medicalProviderDepartmentController.query(1, 3));
        jacksonUtil.printPretty(medicalProviderDepartmentController.query(3, 3));
        jacksonUtil.printPretty(medicalProviderDepartmentController.query(1));
        jacksonUtil.printPretty(medicalProviderDepartmentController.query(2));
    }

    @Test
    void queryByName() {
        jacksonUtil.printPretty(medicalProviderDepartmentController.queryByName("A"));
    }

}