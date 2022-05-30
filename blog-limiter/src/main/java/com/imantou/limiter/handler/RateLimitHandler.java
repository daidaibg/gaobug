package com.imantou.limiter.handler;

import com.imantou.handler.IProceedHandler;
import com.imantou.limiter.RateLimit;
import com.imantou.limiter.exception.RateLimitException;
import com.imantou.metadata.MethodMetaData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * 限流处理handler
 *
 * @author hzq
 */
@Slf4j
@AllArgsConstructor
public class RateLimitHandler implements IProceedHandler {
    private static RedisScript<Long> REDIS_SCRIPT_RATE_LIMIT = null;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisKeyParser redisKeyParser;

    private static final String RARE_LIMIT_PREFIX = "rate-limit";

    @SuppressWarnings("unchecked")
    @Override
    public Object proceed(ProceedingJoinPoint pjp, MethodMetaData methodMetadata) throws Throwable {
        RateLimit rateLimit = methodMetadata.getAnnotation();
        if (null == REDIS_SCRIPT_RATE_LIMIT) {
            REDIS_SCRIPT_RATE_LIMIT = RedisScript.of(this.luaScript(), Long.class);
        }
        // 间隔时间解析为秒
        long interval = DurationStyle.detectAndParse(rateLimit.interval()).getSeconds();
        final String key = redisKeyParser.buildKey(methodMetadata, rateLimit.key(), rateLimit.strategy(), RARE_LIMIT_PREFIX);
        log.info("Success acquire limit key[{}]", key);
        Long currentCount = redisTemplate.execute(REDIS_SCRIPT_RATE_LIMIT, Collections.singletonList(key),
                String.valueOf(rateLimit.count()), String.valueOf(interval));
        // 大于限制时间执行次数限制执行
        if (null != currentCount && currentCount > rateLimit.count() && rateLimit.throwError()) {
            throw new RateLimitException(StringUtils.hasLength(rateLimit.message()) ? rateLimit.message() : "请求频繁");
        }
        return pjp.proceed();

    }

    /**
     * Lua 限流脚本
     */
    public String luaScript() {
        return "local key = KEYS[1];" +
                "local count = tonumber(ARGV[1]);" +
                "local interval = tonumber(ARGV[2]);" +
                "local current = tonumber(redis.call('get', key) or \"0\") " +
                "if current + 1 > count then return current + 1 " +
                "else redis.call(\"INCRBY\", key, \"1\") redis.call(\"expire\", key, interval) return current + 1 end";
    }
}
