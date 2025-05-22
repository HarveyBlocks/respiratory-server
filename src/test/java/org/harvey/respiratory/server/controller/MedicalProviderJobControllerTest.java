package org.harvey.respiratory.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
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
class MedicalProviderJobControllerTest {
    /**
     * 开启非幂等测试
     */
    public static boolean OPEN_NON_IDEMPOTENT_TEST = false;

    @BeforeAll
    static void addUser() {
        UserHolder.saveUser(new UserDto(1923431434245177346L, "", Role.DATABASE_ADMINISTRATOR, "330282200410080030"));
    }

    @AfterAll
    static void removeUser() {
        UserHolder.removeUser();
    }

    @Resource
    private MedicalProviderJobController medicalProviderJobController;
    @Resource
    private RandomUtil randomUtil;

    private List<MedicalProviderJob> createData(int len) {
        List<MedicalProviderJob> list = new ArrayList<>(len);
        while (len-- > 0) {
            String name = randomUtil.randomString(5, 12);
            int roleId = randomUtil.uniform(1, 5);
            Integer expenseEveryVisit = randomUtil.uniform(5000, 50000);
            list.add(new MedicalProviderJob(null, name, roleId, expenseEveryVisit));
        }
        return list;
    }

    @Test
    void register() {
        if (!OPEN_NON_IDEMPOTENT_TEST) {
            return;
        }
        List<MedicalProviderJob> dataList = createData(randomUtil.uniform(10, 20));
        for (MedicalProviderJob data : dataList) {
            medicalProviderJobController.register(data);
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
        int len = randomUtil.uniform(1, 5);
        List<MedicalProviderJob> dataList = createData(len);
        for (MedicalProviderJob data : dataList) {
            int startId = randomUtil.uniform(1, 5);
            data.setId(randomUtil.uniform(startId, startId + len));
            medicalProviderJobController.update(data);
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
        long id = randomUtil.uniform(1, 10);
        log.debug("id = {}", id);
        medicalProviderJobController.del(id);
    }

    @Test
    void query() {
        JacksonUtil.printPretty(medicalProviderJobController.query(2, 3));
        JacksonUtil.printPretty(medicalProviderJobController.query(1, 3));
        JacksonUtil.printPretty(medicalProviderJobController.query(3, 3));
        // 缓存+分页
        // 记录页码+页数->保存这种情况下的id
        // 数据库发生更改了, 全部删掉?
        // 维护 redis 也可以做排行榜 zset, 毕竟完全无序的分页是不合适的
        // 那排行榜上的数据有删除?
        // 如果新增, 缓存排行榜也新增
        // 如果删除, 缓存排行榜也删除
        // 如果更新, 更新到score字段, 缓存排行榜也更新
        JacksonUtil.printPretty(medicalProviderJobController.query(1));
        JacksonUtil.printPretty(medicalProviderJobController.query(2));
    }

    @Test
    void queryByName() {
        JacksonUtil.printPretty(medicalProviderJobController.queryByName("A"));
    }

}