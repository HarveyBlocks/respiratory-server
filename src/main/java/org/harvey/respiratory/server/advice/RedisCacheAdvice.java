package org.harvey.respiratory.server.advice;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.util.JacksonUtil;
import org.harvey.respiratory.server.util.RandomUtil;
import org.harvey.respiratory.server.util.RedisConstants;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 对于查询操作的缓存增强
 * 写操作1. 异步写 2. 写完后删除缓存
 * 怎么设计? 写操作的参数全部都序列化, 然后指定函数?
 * 两边都注册一个
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-22 01:09
 */
@Component
@Slf4j
public class RedisCacheAdvice {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RandomUtil randomUtil;
    @Resource
    private CacheQueryFactory cacheQueryFactory;

    public <T> T adviceOnValue(String key, Supplier<T> slowQuery, Class<T> targetType) {
        String lockKey = RedisConstants.LOCK_KEY_PREFIX + key;
        String normalKey = RedisConstants.NORMAL_KEY_PREFIX + key;
        try {
            return advice(lockKey, cacheQueryFactory.onValue(normalKey, slowQuery), targetType);
        } catch (InterruptedException e) {
            throw new ServerException("Redisson分布式锁被中断", e);
        }
    }

    public <T> T advice(
            String lockKey,
            CacheExecutor<T> cacheExecutor,
            Class<T> targetType) throws InterruptedException {
        // 击穿->数据有一瞬间失效, 一瞬间大量请求打击数据库
        // 穿透->假数据
        // 雪崩->随机过期时间
        // 还得看需求
        try {
            return fastQuery(cacheExecutor, targetType);
        } catch (HaveToExecuteSlowException ignore1) {
            log.debug("从缓存获取数据失败, 需要从数据库获取");
            return synchronizedQuery(lockKey, cacheExecutor, targetType);
        }

    }

    private <T> T synchronizedQuery(
            String lockKey,
            CacheExecutor<T> cacheExecutor,
            Class<T> targetType) throws InterruptedException {
        // 上分布式锁防止击穿
        RLock lock = redissonClient.getLock(lockKey);
        long waitTime = -1L;// 等待时间, 默认-1不等待
        boolean trySucceed = lock.tryLock(waitTime, RedisConstants.LOCK_TTL, TimeUnit.SECONDS);
        if (!trySucceed) {
            // 没有成功获取到锁
            return null;
        }
        T entity;
        try {
            try {
                // 上完锁之后再检查一下
                return fastQuery(cacheExecutor, targetType);
            } catch (HaveToExecuteSlowException ignore2) {
                log.debug("再次尝试从缓存中获取, 依旧失败");
                entity = cacheExecutor.slowGet();
            }
        } finally {
            lock.unlock();
        }
        saveEntityFromSlowQuery(cacheExecutor, entity);
        return entity;
    }

    @Resource
    private JacksonUtil jacksonUtil;

    private <T> void saveEntityFromSlowQuery(CacheExecutor<T> cacheExecutor, T entity) {
        if (entity == null) {
            // 防止穿透
            log.warn("没有查到数据, 制造假数据到缓存");
            // key是假数据
            cacheExecutor.saveFakeCache(
                    RedisConstants.NULL_DATA, ttlRandom(RedisConstants.CACHE_NULL_TTL), TimeUnit.SECONDS);
        } else {
            log.warn("将查到数据, 放到缓存");
            cacheExecutor.saveEntityCache(
                    jacksonUtil.toJsonStr(entity), ttlRandom(cacheExecutor.ttl()), TimeUnit.SECONDS);
        }
    }

    private <T> T fastQuery(CacheExecutor<T> cacheExecutor, Class<T> targetType) {
        String value = cacheExecutor.fastGet();
        if (value == null) {
            log.debug("不得不执行的慢查询");
            throw new HaveToExecuteSlowException();
        }
        if (RedisConstants.NULL_DATA.equals(value)) {
            log.warn("Redis中存在的假数据");
            return null;
        } else {
            log.debug("从缓存中成功获取数据");
            return jacksonUtil.toBean(value, targetType);
        }
    }

    private long ttlRandom(long origin) {
        // 随机ttl防止雪崩
        if (origin <= 1) {
            return origin;
        }
        int right = (int) (origin / Math.log1p(origin));

        return origin + randomUtil.uniform(-right, right);
    }

    private static class HaveToExecuteSlowException extends ServerException {

    }
}
