package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.Disease;
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
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", "330282200410080030"));
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
        System.out.println(JacksonUtil.pretty(diseaseController.queryByName("", 1, 10)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryByName("", 2, 10)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryByName("", null, null)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryByName("C", null, null)));
    }

    @Test
    void queryById() {
        System.out.println(JacksonUtil.pretty(diseaseController.queryById(1)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryById(2)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryById(3)));
        System.out.println(JacksonUtil.pretty(diseaseController.queryById(4)));
    }
    @Test
    void del() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        System.out.println(JacksonUtil.pretty(diseaseController.del(1)));
        System.out.println(JacksonUtil.pretty(diseaseController.del(2)));
        System.out.println(JacksonUtil.pretty(diseaseController.del(3)));
        System.out.println(JacksonUtil.pretty(diseaseController.del(4)));
    }

}