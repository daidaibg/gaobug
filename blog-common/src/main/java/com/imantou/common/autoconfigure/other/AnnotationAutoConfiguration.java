package com.imantou.common.autoconfigure.other;

import com.imantou.common.annotation.distributedlock.IDistributedLockTemplate;
import com.imantou.common.annotation.distributedlock.RedisDistributedLockTemplate;
import com.imantou.common.annotation.strategy.*;
import com.imantou.common.aspect.DistributedLockAspect;
import com.imantou.common.aspect.ParamVerifyAspect;
import com.imantou.common.aspect.RateLimitAspect;
import com.imantou.common.annotation.context.DefaultDistributedContext;
import com.imantou.common.annotation.context.DistributedContext;
import com.imantou.common.annotation.extend.IAcquireLockTimeoutHandler;
import com.imantou.common.annotation.extend.IDistributedLockListener;

import com.imantou.common.annotation.handler.DistributedLockHandler;
import com.imantou.common.annotation.handler.RateLimitHandler;
import com.imantou.common.annotation.handler.RedisKeyParser;
import com.imantou.common.annotation.handler.VerifyHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author hzq
 */
@Configuration
public class AnnotationAutoConfiguration {

    /*  -------------------- key策略配置  --------------------  */

    @Bean
    public ParamKeyGenerateStrategy paramKeyGenerateStrategy(IdempotencyKeyGenerateStrategy strategy) {
        return new ParamKeyGenerateStrategy(strategy);
    }
    @Bean
    public IpKeyGenerateStrategy ipRateLimitStrategy() {
        return new IpKeyGenerateStrategy();
    }

    @Bean
    public IdempotencyKeyGenerateStrategy idempotencyKeyGenerateStrategy() {
        return new IdempotencyKeyGenerateStrategy();
    }

    /*  -------------------- 限流相关配置  --------------------  */

    @Bean
    public RedisKeyParser rateLimitKeyParser(List<IKeyGenerateStrategy> rateLimitStrategyList) {
        return new RedisKeyParser(rateLimitStrategyList);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimitHandler rateLimitHandler(ObjectProvider<RedisTemplate<String, String>> redisTemplate,
                                             RedisKeyParser rateLimitKeyParser) {
        return new RateLimitHandler(redisTemplate.getIfAvailable(), rateLimitKeyParser);
    }

    @Bean
    public RateLimitAspect rateLimitAspect(RateLimitHandler rateLimitHandler) {
        return new RateLimitAspect(rateLimitHandler);
    }

    /*  -------------------- 分布式锁相关配置  --------------------  */

    @Bean
    @ConditionalOnMissingBean
    public DistributedLockHandler distributedLockHandler(DistributedContext distributedContext,
                                                         RedisKeyParser rateLimitKeyParser) {
        return new DistributedLockHandler(rateLimitKeyParser, distributedContext);
    }

    @Bean
    public DistributedLockAspect distributedLockAspect(DistributedLockHandler distributedLockHandler) {
        return new DistributedLockAspect(distributedLockHandler);
    }

    @Bean
    public DistributedContext distributedContext(IDistributedLockTemplate distributedLockTemplate,
                                                 Optional<List<IAcquireLockTimeoutHandler>> acquireLockTimeoutHandlersOptional,
                                                 Optional<List<IDistributedLockListener>> distributedLimitListenersOptional) {
        return new DefaultDistributedContext(distributedLockTemplate, acquireLockTimeoutHandlersOptional.orElse(null),
                distributedLimitListenersOptional.orElse(null));
    }

    @Bean
    @ConditionalOnMissingBean(IDistributedLockTemplate.class)
    public IDistributedLockTemplate iDistributedLockTemplate(ObjectProvider<RedisTemplate<String, String>> redisTemplate) {

        return new RedisDistributedLockTemplate(redisTemplate.getIfAvailable());
    }

    /*  -------------------- 参数校验相关配置  --------------------  */

    @Bean
    public EntityFieldVerifyStrategy getEntityVerifyStrategy() {
        return new EntityFieldVerifyStrategy();
    }

    @Bean
    public MethodParamVerifyStrategy getParamsVerifyStrategy() {
        return new MethodParamVerifyStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public VerifyHandler verifyHandler(List<IVerifyStrategy> verifyStrategyList) {
        return new VerifyHandler(verifyStrategyList);
    }

    @Bean
    public ParamVerifyAspect getParamVerifyAspect(VerifyHandler verifyHandler) {
        return new ParamVerifyAspect(verifyHandler);
    }
}
