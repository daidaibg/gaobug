package com.gaobug.limiter.exception;

/**
 * 获取分布式锁超时
 *
 * @author hzq
 */
public class AcquireLockTimeoutException extends DistributedLockException {

    public AcquireLockTimeoutException(String message) {
        super(message);
    }
}
