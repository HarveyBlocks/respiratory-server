package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
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

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class SymptomaticPresentationControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410084384"));
    }

    @Resource
    private JacksonUtil jacksonUtil;

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private SymptomaticPresentationController symptomaticPresentationController;
    @Resource
    private RandomUtil randomUtil;

    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        jacksonUtil.printPretty(symptomaticPresentationController.del(1925787170468052993L));
    }

    @Test
    void updatePatientDrugHistory() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        SymptomaticPresentation symptomaticPresentation = new SymptomaticPresentation();
        symptomaticPresentation.setId(1925602978295754754L);
        symptomaticPresentation.setDescription("更新了一个描述");
        // 1925787170468052993L
        jacksonUtil.printPretty(symptomaticPresentationController.updatePatientSymptomaticPresentation(symptomaticPresentation));
    }

    @Test
    void queryVisitDrug() {
        jacksonUtil.printPretty(symptomaticPresentationController.queryVisitDrug(1925567571042529282L));
    }
}