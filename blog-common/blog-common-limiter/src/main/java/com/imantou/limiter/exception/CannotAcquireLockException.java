package com.gaobug.limiter.exception;

import lombok.Getter;

/**
 * 不能获取到分布式锁异常，
 *
 * @author hzq
 */
public class CannotAcquireLockException extends DistributedLockException {
    /**
     * 分布式锁的key
     */
    @Getter
    private final String lockKey;

    public CannotAcquireLockException(String lockKey, String message, Throwable cause) {
        super(message, cause);
        this.lockKey = lockKey;
    }
}
