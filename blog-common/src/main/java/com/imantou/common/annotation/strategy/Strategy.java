package com.imantou.common.annotation.strategy;

/**
 * 策略枚举
 *
 * @author hzq
 */

public enum Strategy {
    /**
     * 默认策略
     * <p>
     * 默认执行的key策略，获取方法全限定类名
     * </p>
     */
    DEFAULT,
    /**
     * ip策略
     * <p>
     * 通过ip进行限流
     * </p>
     */
    IP,
    /**
     * 参数策略
     * <p>
     * 通过获取接口中指定key的参数值进行限流
     * </p>
     */
    PARAMETER,
    /**
     * 幂等性策略
     * <p>
     * 重排序参数使用md5加密参数对比的方式实现幂等性校验
     * </p>
     */
    IDEMPOTENCY
}