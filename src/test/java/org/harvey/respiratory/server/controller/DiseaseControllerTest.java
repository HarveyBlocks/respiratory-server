package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DiseaseControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @Resource
    private DiseaseController diseaseController;

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        JacksonUtil.pretty(diseaseController.register(new Disease(null, "A", "描述A")));
        JacksonUtil.pretty(diseaseController.register(new Disease(null, "B", "描述B")));
        JacksonUtil.pretty(diseaseController.register(new Disease(null, "C", "描述C")));
        JacksonUtil.pretty(diseaseController.register(new Disease(null, "D", "描述D")));
    }

    @Test
    void queryByName() {
        JacksonUtil.printPretty(diseaseController.queryByName("", 1, 10));
        JacksonUtil.printPretty(diseaseController.queryByName("", 2, 10));
        JacksonUtil.printPretty(diseaseController.queryByName("", null, null));
        JacksonUtil.printPretty(diseaseController.queryByName("C", null, null));
    }

    @Test
    void queryById() {
        JacksonUtil.printPretty(diseaseController.queryById(1));
        JacksonUtil.printPretty(diseaseController.queryById(2));
        JacksonUtil.printPretty(diseaseController.queryById(3));
        JacksonUtil.printPretty(diseaseController.queryById(4));
    }

    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        JacksonUtil.printPretty(diseaseController.del(1));
        JacksonUtil.printPretty(diseaseController.del(2));
        JacksonUtil.printPretty(diseaseController.del(3));
        JacksonUtil.printPretty(diseaseController.del(4));
    }

}