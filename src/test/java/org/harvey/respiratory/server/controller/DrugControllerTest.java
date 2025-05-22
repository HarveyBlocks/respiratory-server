package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Drug;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.RandomUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DrugControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @Resource
    private DrugController drugController;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<Drug> drugList = createDrugs(12);
        for (Drug drug : drugList) {
            drugController.register(drug);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Drug> createDrugs(int len) {
        List<Drug> list = new ArrayList<>(len);
        while (len-- > 0) {
            String name = randomUtil.randomString(5, 12);
            int expenseEach = randomUtil.uniform(500, 12000);
            String specification = randomUtil.randomString(5, 60);
            String administrationRoute = randomUtil.randomString(5, 60);
            String medicationSite = randomUtil.randomString(5, 60);
            String medicationPrecaution = randomUtil.randomString(5, 250);
            String guidance = randomUtil.randomString(5, 250);
            list.add(new Drug(null, name, expenseEach, specification, administrationRoute, medicationSite,
                    medicationPrecaution, guidance
            ));
        }
        return list;
    }

    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        drugController.del(2L);
    }

    @Test
    void queryById() {
        JacksonUtil.printPretty(drugController.queryById(1L));
    }

    @Test
    void queryByName() {
        JacksonUtil.printPretty(drugController.queryByName("A", 1, 2));
    }

}