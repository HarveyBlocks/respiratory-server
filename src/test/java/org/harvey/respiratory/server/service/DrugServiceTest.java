package org.harvey.respiratory.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DrugServiceTest {
    @Resource
    private DrugService drugService;

    @Test
    void deplete() {
        drugService.deplete(Map.of(1, 2, 3, 3));
    }
}