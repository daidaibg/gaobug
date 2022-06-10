package com.gaobug.limiter.handler;

import com.gaobug.base.handler.IProceedHandler;
import com.gaobug.limiter.context.DistributedContext;
import com.gaobug.limiter.extend.IDistributedLockListener;
import com.gaobug.limiter.DistributedLock;
import com.gaobug.limiter.distributedlock.DistributedLockCallback;
import com.gaobug.limiter.distributedlock.IDistributedLockTemplate;
import com.gaobug.limiter.exception.AcquireLockTimeoutException;
import com.gaobug.limiter.extend.IAcquireLockTimeoutHandler;
import com.gaobug.limiter.strategy.Strategy;
import com.gaobug.base.metadata.MethodMetaData;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁处理器 Redis 实现
 *
 * @author hzq
 */
@Slf4j
@AllArgsConstructor
public class DistributedLockHandler implements IProceedHandler {
    private final RedisKeyParser redisKeyParser;
    private final DistributedContext distributedContext;
    /**
     * 为每一个key加上一个前缀，
     * 1.到时候使用scan 0 match [registryKey]*可以查看当前正在使用的分布式锁
     * 2.有时候一个多个项目的key有可能相同，有前缀也减少重复的概率
     */
    private static final String REGISTRY_KEY_PREFIX = "redis-lock";

    @Override
    public Object proceed(ProceedingJoinPoint pjp, MethodMetaData methodMetadata) {
        // 获取分布式锁超时处理器
        IAcquireLockTimeoutHandler acquireLockTimeoutHandler = distributedContext.getAcquireLockTimeoutHandler(methodMetadata);
        // 监听器
        List<IDistributedLockListener> distributedLimitListeners = distributedContext.getDistributedLimitListeners(methodMetadata);
        // 构建当前请求的key
        DistributedLock distributedLock = methodMetadata.getAnnotation();
        String key = distributedLock.key();
        Strategy[] strategy = distributedLock.strategy();
        final String lockKey = redisKeyParser.buildKey(methodMetadata, key, strategy, REGISTRY_KEY_PREFIX);
        IDistributedLockTemplate distributedLockTemplate = distributedContext.getDistributedLockTemplate();
        long expire = DurationStyle.detectAndParse(distributedLock.tryAcquireTimeout()).getSeconds();

        DistributedLockCallback callback = this.builderDistributedLockCallback(pjp, methodMetadata, acquireLockTimeoutHandler,
                distributedLimitListeners, lockKey);

        beforeDistributedLock(methodMetadata, distributedLimitListeners, distributedLockTemplate.completeLockKey(lockKey));
        try {
            return distributedLockTemplate.execute(lockKey, expire, TimeUnit.SECONDS, callback);
        } finally {
            distributedLockFinally(methodMetadata, distributedLimitListeners, distributedLockTemplate.completeLockKey(lockKey));
        }
    }

    private DistributedLockCallback builderDistributedLockCallback(ProceedingJoinPoint joinPoint, MethodMetaData methodMetadata,
                                                                   IAcquireLockTimeoutHandler acquireLockTimeoutHandler,
                                                                   List<IDistributedLockListener> distributedLimitListeners,
                                                                   String lockKey) {
        return new DistributedLockCallback() {

            @SneakyThrows
            @Override
            public Object onGetLock() {
//                if (log.isDebugEnabled()) {
//                    log.debug("Success acquire distribute lock[{}]", lockKey);
//                }
                log.info("Success acquire distribute lock[{}]", lockKey);
                afterDistributedLock(distributedLimitListeners, methodMetadata, lockKey);

                Object result = joinPoint.proceed();

                afterExecute(result, distributedLimitListeners, methodMetadata, lockKey);
                return result;
            }

            @Override
            public Object onTimeout() {
                // 有自定义超时处理策略，执行自定义超时处理策略
                if (null != acquireLockTimeoutHandler) {
                    return acquireLockTimeoutHandler.onDistributedLockFailure(methodMetadata);
                }
                // 否则抛出默认异常
                DistributedLock distributedLock = methodMetadata.getAnnotation();
                String acquireTimeoutMessage = Optional.of(distributedLock.acquireTimeoutMessage())
                        .filter(StringUtils::hasLength)
                        .orElse(String.format("method [%s] acquire distributed lock fail", methodMetadata.getClassMethodName()));
                throw new AcquireLockTimeoutException(acquireTimeoutMessage);
            }
        };
    }

    /*  ---------------------- 执行监听器 ----------------------  */

    private void beforeDistributedLock(MethodMetaData methodMetadata, List<IDistributedLockListener> distributedLimitListeners,
                                       String lockKey) {
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            distributedLimitListeners.forEach(distributedLimitListener ->
                    distributedLimitListener.beforeDistributedLock(methodMetadata, lockKey));
        }
    }

    private void afterDistributedLock(List<IDistributedLockListener> distributedLimitListeners, MethodMetaData methodMetadata,
                                      String lockKey) {
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            distributedLimitListeners.forEach(distributedLimitListener ->
                    distributedLimitListener.afterDistributedLock(methodMetadata, lockKey));
        }
    }

    private void afterExecute(Object result, List<IDistributedLockListener> distributedLimitListeners,
                              MethodMetaData methodMetadata, String lockKey) {
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            distributedLimitListeners.forEach(distributedLimitListener ->
                    distributedLimitListener.afterExecute(methodMetadata, lockKey, result));
        }
    }

    private void distributedLockFinally(MethodMetaData methodMetadata, List<IDistributedLockListener> distributedLimitListeners,
                                        String lockKey) {
        if (!ObjectUtils.isEmpty(distributedLimitListeners)) {
            distributedLimitListeners.forEach(distributedLimitListener ->
                    distributedLimitListener.distributedLockFinally(methodMetadata, lockKey));
        }
    }

}
