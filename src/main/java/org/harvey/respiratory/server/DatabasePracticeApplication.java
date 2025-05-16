package org.harvey.respiratory.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
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
