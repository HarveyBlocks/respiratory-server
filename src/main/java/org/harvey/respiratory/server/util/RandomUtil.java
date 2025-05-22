package org.harvey.respiratory.server.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 生产随机数的工具
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-22 01:46
 */
@Component
public class RandomUtil {
    private final Random random;

    private RandomUtil() {
        random = new Random(System.currentTimeMillis());
    }

    public int uniform(int left, int right) {
        return random.nextInt(right - left) + left;
    }
}
