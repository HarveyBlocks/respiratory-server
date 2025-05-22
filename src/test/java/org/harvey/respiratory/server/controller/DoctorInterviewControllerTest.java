package org.harvey.respiratory.server.controller;

import org.harvey.respiratory.server.pojo.dto.InterviewDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.UserHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

/**
 * 测试顺序, 由于需要数据, 所以可以先填充数据的...!
 * disease
 * drug
 * medical provider form
 * medical provider department
 * medical provider job
 * medical provider
 * patient
 * visit doctor 取号
 * interview
 * symptomatic
 * special using
 * expanse
 * pay
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DoctorInterviewControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", "330282200410080030"));
    }

    @Resource
    private DoctorInterviewController doctorInterviewController;

    @Test
    void interview() {
        System.out.println(JacksonUtil.pretty(doctorInterviewController.interview(createDto())));
    }

    private InterviewDto createDto() {
        return new InterviewDto();
    }

}