package com.imantou.limiter.distributedlock;

import com.imantou.limiter.exception.DistributedLockException;
import com.imantou.limiter.registry.RedisLockRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 默认的Redis执行分布式锁
 *
 * @author hzq
 */
@Slf4j
@Component
public class RedisDistributedLockTemplate implements IDistributedLockTemplate {

    private final RedisLockRegistry redisLockRegistry;

    public RedisDistributedLockTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisLockRegistry = new RedisLockRegistry(redisTemplate);
    }

    @Override
    public Object execute(String lockId, long timeout, TimeUnit unit, DistributedLockCallback callback) {

        Lock lock = null;
        boolean getLock = false;
        try {
            lock = redisLockRegistry.obtain(lockId);
            getLock = lock.tryLock(timeout, unit);
            if (getLock) {
                // 拿到锁
                return callback.onGetLock();
            } else {
                // 未拿到锁
                return callback.onTimeout();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new DistributedLockException(ex);
        } finally {
            if (getLock) {
                // 释放锁
                lock.unlock();
            }
        }
    }
}
