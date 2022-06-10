package com.gaobug.limiter.handler;

import com.gaobug.base.metadata.MethodMetaData;
import com.gaobug.limiter.strategy.DefaultKeyGenerateStrategy;
import com.gaobug.limiter.strategy.IKeyGenerateStrategy;
import com.gaobug.limiter.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @author hzq
 */
@Slf4j
public class RedisKeyParser {
    private final ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    private final ExpressionParser parser = new SpelExpressionParser();
    private final List<IKeyGenerateStrategy> keyGenerateStrategyList;
    private final IKeyGenerateStrategy defaultKeyGenerateStrategy;

    public RedisKeyParser(List<IKeyGenerateStrategy> keyGenerateStrategyList) {
        this.keyGenerateStrategyList = keyGenerateStrategyList;
        // 默认key生成策略
        defaultKeyGenerateStrategy = new DefaultKeyGenerateStrategy();
        keyGenerateStrategyList.add(defaultKeyGenerateStrategy);
    }

    /**
     * 构建唯一标示 KEY
     */
    public String buildKey(MethodMetaData methodMetadata, String spElKey, Strategy[] strategyArray, String prefix) {
        StringBuffer key = new StringBuffer();
        key.append(prefix);
        // SpEL Key 解析
        String parseKey = Optional.ofNullable(spElKey)
                .filter(StringUtils::hasLength)
                .map(str -> {
                    Method method = methodMetadata.getMethod();
                    Object[] args = methodMetadata.getArgs();
                    return this.parser(spElKey, method, args);
                }).orElse("");
        // 组装自定义策略
        if (strategyArray.length > 0) {
            for (Strategy strategy : strategyArray) {
                keyGenerateStrategyList.stream()
                        .filter(t -> Objects.equals(t.getType(), strategy) && t.supports(methodMetadata.getAnnotation()))
                        .findFirst()
                        .map(keyGenerateStrategy -> keyGenerateStrategy.getKey(methodMetadata, parseKey))
                        .filter(StringUtils::hasLength)
                        .ifPresent(currentGenerateKey -> {
                                    if (key.length() > 0) {
                                        key.append(":");
                                    }
                                    key.append(currentGenerateKey);
                                }
                        );
            }
        }
        if (key.length() == 0) {
            log.warn("The generated key is empty, then will use the default strategy");
            key.append(defaultKeyGenerateStrategy.getKey(methodMetadata, parseKey));
        }
        return key.toString();
    }

    public String parser(String key, Method method, Object[] args) {
        EvaluationContext context = new MethodBasedEvaluationContext(null, method, args, nameDiscoverer);
        Object objKey = parser.parseExpression(key).getValue(context);
        return ObjectUtils.nullSafeToString(objKey);
    }
}
