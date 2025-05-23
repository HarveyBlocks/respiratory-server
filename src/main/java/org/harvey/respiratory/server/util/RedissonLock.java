package org.harvey.respiratory.server.util;

import lombok.Getter;
import org.harvey.respiratory.server.exception.ServerException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson锁
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-21 21:20
 */
@Component
public class RedissonLock<T> {
    @Resource
    private RedissonClient redissonClient;

    @Getter
    private T errorResult = null;

    public void setErrorResult(T errorResult) {
        this.errorResult = errorResult;
    }

    /**
     * 分布式锁
     *
     * @param lockKey    lock的键
     * @param fastSupply 获取值的快方法
     * @param slowSupply 获取值的慢方法
     * @return id
     */

    @Nullable
    public T asynchronousLock(
            String lockKey, Supplier<T> fastSupply, Supplier<T> slowSupply) throws InterruptedException {
        // 获取锁(可重入)
        try {
            return fastSupply.get();
        } catch (HaveToExecuteSlowException ignore1) {
            // 只能执行下面的慢操作
            RLock lock = redissonClient.getLock(lockKey);
            // 尝试获取锁, 参数分别为: 获取锁的最大等待时间(期间会重试),锁自动释放时间, 时间单位
            long waitTime = -1L;// 等待时间, 默认-1不等待
            long releaseTime = RedisConstants.LOCK_TTL;// 自动释放时间,默认30s
            boolean isLock = lock.tryLock(waitTime, releaseTime, TimeUnit.SECONDS);
            if (!isLock) {
                // 没有成功上锁
                return errorResult;
            }
            try {
                // 上完锁之后是不是还要再分析一下
                try {
                    return fastSupply.get();
                } catch (HaveToExecuteSlowException ignore2) {
                    return slowSupply.get();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class HaveToExecuteSlowException extends ServerException {

    }

    public static ServerException haveToExecuteSlowException() {
        return new HaveToExecuteSlowException();
    }

    public boolean isErrorResult(T result) {
        return result == errorResult;
    }

}
