package com.gaobug.limiter.distributedlock;

/**
 * 分布式锁处理接口
 *
 * @author hzq
 */
public interface DistributedLockCallback {

    /**
     * 成功获取分布式锁后执行方法
     *
     * @return 实体
     * @throws InterruptedException 中断异常
     */
    Object onGetLock() throws InterruptedException;

    /**
     * 获取分布式锁超时回调
     *
     * @return 实体
     * @throws InterruptedException 中断异常
     */
    Object onTimeout() throws InterruptedException;

}
