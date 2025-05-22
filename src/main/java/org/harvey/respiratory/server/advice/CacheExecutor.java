package org.harvey.respiratory.server.advice;

import java.util.concurrent.TimeUnit;

/**
 * 缓存写增强的执行者
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-22 12:11
 */
public interface CacheExecutor<T> {
    String fastGet();

    T slowGet();

    /**
     * @return 缓存保留时间
     */
    long ttl();

    void saveFakeCache(String fakeData, long time, TimeUnit unit);

    void saveEntityCache(String value, long time, TimeUnit unit);

}
