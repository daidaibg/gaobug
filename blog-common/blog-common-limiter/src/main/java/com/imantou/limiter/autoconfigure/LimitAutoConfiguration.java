package com.gaobug.limiter.autoconfigure;

import com.gaobug.limiter.context.DefaultDistributedContext;
import com.gaobug.limiter.context.DistributedContext;
import com.gaobug.limiter.extend.IDistributedLockListener;
import com.gaobug.limiter.handler.DistributedLockHandler;
import com.gaobug.limiter.handler.RateLimitHandler;
import com.gaobug.limiter.handler.RedisKeyParser;
import com.gaobug.limiter.strategy.IdempotencyKeyGenerateStrategy;
import com.gaobug.limiter.aspect.DistributedLockAspect;
import com.gaobug.limiter.aspect.RateLimitAspect;
import com.gaobug.limiter.distributedlock.IDistributedLockTemplate;
import com.gaobug.limiter.distributedlock.RedisDistributedLockTemplate;
import com.gaobug.limiter.extend.IAcquireLockTimeoutHandler;
import com.gaobug.limiter.strategy.IKeyGenerateStrategy;
import com.gaobug.limiter.strategy.IpKeyGenerateStrategy;
import com.gaobug.limiter.strategy.ParamKeyGenerateStrategy;
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
public class LimitAutoConfiguration {

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

    @Bean
    public RedisKeyParser rateLimitKeyParser(List<IKeyGenerateStrategy> rateLimitStrategyList) {
        return new RedisKeyParser(rateLimitStrategyList);
    }

    /*  -------------------- 限流相关配置  --------------------  */

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
}
