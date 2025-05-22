package org.harvey.respiratory.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1. 拆分微服务
 * 2. redis查询增强
 *      - 写增强, 先更新数据库, 再更新缓存
 * 3. 消息队列异步写
 *      - 同时存储es
 * 4. websocket
 * 5. es增强倒排索引查询
 * 6. canal同步写操作/消息队列同步写操作
 * 7. 数据安全与加密(以下是对有索引查询需求的敏感字段的查询解决方案)
 *      - 创建敏感数据表. 有字段id 加密字符串 hash_值
 *      - 实体表中的敏感数据全部变成`创建敏感数据表_id`的外键
 *      - 敏感数据存入时, 先从敏感数据表中查
 *          1. hash(上索引) 比对
 *          2. 对于冲突逐一匹配
 *      - 实体数据库的敏感数据密文-密文比对时, 直接比对那个外键id
 *      - 明文往实体数据库中依据查询该敏感字段时, 先去敏感数据表中查出id外键, 然后id外键再查询(自带索引)
 *
 *      - hash 会被攻击, 解决方案是定期更换盐,
 *          - 然后在更换时, 如果用户来查询了, 如果数据库里存的是老版本盐的hash, 就先用老版本比对, 然后用新版本的盐出来的hash替换老版本的hash
 *          - 直接使用"安全索引技术（如可搜索对称加密 SSE 或 属性基加密 ABE）"
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 15:17
 */
@MapperScan(basePackages = "org.harvey.respiratory.server.dao")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class DatabasePracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatabasePracticeApplication.class, args);
    }
}
