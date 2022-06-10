package com.gaobug.limiter.extend;

import com.gaobug.limiter.metadata.DistributedLockMethodMetaData;
import com.gaobug.base.metadata.MethodMetaData;

/**
 * 分布式锁扩展
 *
 * @author hzq
 */
public interface IDistributedLockListener {

    /**
     * 判断是否支持当前监听
     *
     * @param methodMetadata 当前运行时参数 {@link DistributedLockMethodMetaData}
     * @return 是否支持
     */
    boolean supports(MethodMetaData methodMetadata);

    /**
     * 在加锁之前需要运行
     *
     * @param methodMetadata 当前方法的运行时参数 {@link DistributedLockMethodMetaData}
     * @param redisKey       redisKey
     */
    void beforeDistributedLock(MethodMetaData methodMetadata, String redisKey);

    /**
     * 在加锁之后还未执行用户方法时运行
     *
     * @param methodMetadata 当前方法的运行时参数 {@link DistributedLockMethodMetaData}
     * @param redisKey       redisKey
     */
    void afterDistributedLock(MethodMetaData methodMetadata, String redisKey);


    /**
     * 在加锁之后并运行用户方法后运行
     * 注意：此方法有可能因执行用户方法后异常不运行
     *
     * @param methodMetadata 当前方法的运行时参数 {@link DistributedLockMethodMetaData}
     * @param redisKey       redisKey
     * @param result         结果
     */
    void afterExecute(MethodMetaData methodMetadata, String redisKey, Object result);

    /**
     * 一定会运行的方法，可能还未加锁成功
     *
     * @param methodMetadata 当前方法的运行时参数 {@link DistributedLockMethodMetaData}
     * @param redisKey       redisKey
     */
    void distributedLockFinally(MethodMetaData methodMetadata, String redisKey);

}
