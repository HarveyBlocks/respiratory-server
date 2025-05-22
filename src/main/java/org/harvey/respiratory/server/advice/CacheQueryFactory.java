package org.harvey.respiratory.server.advice;

import org.harvey.respiratory.server.util.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存写增强的执行者的生产工程
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-22 12:38
 */
@Component
public class CacheQueryFactory {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public <T> CacheExecutor<T> onValue(String key, Supplier<T> slowSupplier) {
        return new CacheExecutor<>() {
            @Override
            public String fastGet() {
                return stringRedisTemplate.opsForValue().get(key);
            }

            @Override
            public T slowGet() {
                return slowSupplier.get();
            }

            @Override
            public long ttl() {
                return  RedisConstants.VALUE_CACHE_TTL;
            }

            @Override
            public void saveFakeCache(String fakeData, long time, TimeUnit unit) {
                stringRedisTemplate.opsForValue().set(key, fakeData, time, unit);
            }

            @Override
            public void saveEntityCache(String value, long time, TimeUnit unit) {
                stringRedisTemplate.opsForValue().set(key, value, time, unit);
            }

        };
    }
}
