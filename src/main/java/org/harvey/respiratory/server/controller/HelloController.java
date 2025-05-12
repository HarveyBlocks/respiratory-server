package org.harvey.respiratory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-08 01:00
 */
@Slf4j
@RestController
public class HelloController {
    @GetMapping(path = "/hello")
    public Result<String> hello() {

        log.info("visit hello");
        return Result.success("Hello");
    }
}
