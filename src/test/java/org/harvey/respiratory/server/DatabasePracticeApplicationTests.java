package org.harvey.respiratory.server;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 测试顺序, 由于需要数据, 所以可以先填充数据的...!
 * disease                          ok
 * drug                             ok
 * medical provider form            ok
 * medical provider department      ok
 * medical provider job             ok
 * medical provider                 ok
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
class DatabasePracticeApplicationTests {


}
