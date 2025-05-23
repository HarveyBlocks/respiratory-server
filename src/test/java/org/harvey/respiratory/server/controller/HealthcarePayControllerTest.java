package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.QueryBalanceDto;
import org.harvey.respiratory.server.pojo.dto.RechargeDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
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
class HealthcarePayControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @Resource
    private HealthcarePayController healthcarePayController;

    @Test
    void pay() {
        jacksonUtil.printPretty(healthcarePayController.pay(1925567571042529282L));
    }

    @Resource
    private JacksonUtil jacksonUtil;

    @Test
    void rechargeByHealthcare() {
        QueryBalanceDto queryBalanceDto = new QueryBalanceDto();
        queryBalanceDto.setHealthcareId(1925553684184309761L);
        jacksonUtil.printPretty(healthcarePayController.rechargeByHealthcare(new RechargeDto(queryBalanceDto, 999900)));
    }

    @Test
    void queryBalance() {
        QueryBalanceDto queryBalanceDto = new QueryBalanceDto();
        queryBalanceDto.setHealthcareId(1925553684184309761L);
        jacksonUtil.printPretty(healthcarePayController.queryBalance(queryBalanceDto));

    }
}