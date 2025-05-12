package org.harvey.respiratory.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan(basePackages = "org.harvey.respiratory.server.dao")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class DatabasePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabasePracticeApplication.class, args);
    }

}
