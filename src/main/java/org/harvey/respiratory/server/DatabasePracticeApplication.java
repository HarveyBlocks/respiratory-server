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
