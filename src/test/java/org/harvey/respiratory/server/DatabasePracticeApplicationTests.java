package org.harvey.respiratory.server;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 测试顺序, 由于需要数据, 所以可以先填充数据的...!
 * 1. disease                           ok
 * 2. drug                              ok
 * 3. family history                    ok
 * 4. medical provider form             ok
 * 5. medical provider department       ok
 * 6. medical provider job              ok
 * 7. medical provider                  ok
 * 8. patient                           ok
 * 9. visit doctor                      ok
 * 10. interview                        ok
 * 11. symptomatic                      ok
 * 12. special using                    ok
 * 13. expanse                          ok
 * 14. pay
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DatabasePracticeApplicationTests {


}
