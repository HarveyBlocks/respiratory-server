package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
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
class MedicalProviderFormControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;
    @Resource
    private JacksonUtil jacksonUtil;
    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));    }

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private MedicalProviderFormController medicalProviderFormController;
    @Resource
    private RandomUtil randomUtil;

    private List<MedicalProviderForm> createData(int len) {
        List<MedicalProviderForm> list = new ArrayList<>(len);
        while (len-- > 0) {
            String name = randomUtil.randomString(5, 12);
            String address = randomUtil.randomString(5, 250);
            list.add(new MedicalProviderForm(null, name, address));
        }
        return list;
    }

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<MedicalProviderForm> dataList = createData(randomUtil.uniform(10, 20));
        for (MedicalProviderForm data : dataList) {
            medicalProviderFormController.register(data);
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
        int uniform = randomUtil.uniform(10, 20);
        List<MedicalProviderForm> dataList = createData(uniform);
        for (MedicalProviderForm data : dataList) {
            data.setId(randomUtil.uniform(1, uniform));
            medicalProviderFormController.update(data);
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
        medicalProviderFormController.del(id);
    }

    @Test
    void query() {
        jacksonUtil.printPretty(medicalProviderFormController.query(2, 3));
        jacksonUtil.printPretty(medicalProviderFormController.query(1, 3));
        jacksonUtil.printPretty(medicalProviderFormController.query(3, 3));
        jacksonUtil.printPretty(medicalProviderFormController.query(1));
        jacksonUtil.printPretty(medicalProviderFormController.query(2));
    }

    @Test
    void queryByName() {
        jacksonUtil.printPretty(medicalProviderFormController.queryByName("A"));
    }

    @Test
    void queryByAddress() {
        jacksonUtil.printPretty(medicalProviderFormController.queryByAddress("A"));
    }
}