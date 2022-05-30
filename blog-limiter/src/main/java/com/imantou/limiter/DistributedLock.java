package com.imantou.limiter;

import com.imantou.metadata.MethodMetaData;
import com.imantou.limiter.strategy.DefaultKeyGenerateStrategy;
import com.imantou.limiter.strategy.IKeyGenerateStrategy;
import com.imantou.limiter.strategy.Strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁限制注解类
 * <p>
 * 支持可重入
 *
 * @author hzq
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 唯一标示，支持SpEL表达式
     */
    String key() default "";

    /**
     * 获取分布式锁超时失败时间，默认 10 秒
     * <p>
     * 例如 5s 五秒，6m 六分钟，7h 七小时，8d 八天
     */
    String tryAcquireTimeout() default "10s";

    /**
     * 加key策略
     * <p>
     * 默认的key生成策略{@link DefaultKeyGenerateStrategy}作为前缀
     * 需要实现{@link IKeyGenerateStrategy}接口注入到spring中
     * 通过{@link IKeyGenerateStrategy#getKey(MethodMetaData, String)}返回的key将会作为后缀加在默认得到的key后
     */
    Strategy[] strategy() default Strategy.DEFAULT;

    /**
     * parameter限制策略时需填写限制参数的属性值
     */
    String[] keys() default {};

    /**
     * 获取分布式锁超时提示
     */
    String acquireTimeoutMessage() default "";

}
