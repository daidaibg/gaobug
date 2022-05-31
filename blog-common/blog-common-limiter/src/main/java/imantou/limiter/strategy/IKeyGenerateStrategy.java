package imantou.limiter.strategy;

import imantou.limiter.DistributedLock;
import imantou.limiter.RateLimit;
import com.imantou.exception.metadata.MethodMetaData;
import com.imantou.exception.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * key生成策略
 *
 * @author hzq
 */
public interface IKeyGenerateStrategy {
    /**
     * 当前注解是否支持当前key的生成策略
     *
     * @param annotation 当前运行注解 {@link RateLimit} {@link DistributedLock}
     * @return 是否支持
     */
    default boolean supports(Annotation annotation) {
        return true;
    }

    /**
     * 策略类型
     *
     * @return 限流策略类型
     */
    Strategy getType();

    /**
     * 唯一标示 key
     *
     * @param methodMetadata {@link MethodMetaData}
     * @param parseKey       解析spEL得到的Key
     * @return 包装的key
     */
    String getKey(MethodMetaData methodMetadata, String parseKey);

    /**
     * 当前请求
     *
     * @return 当前请求
     */
    default HttpServletRequest getRequest() {
        return RequestUtils.getRequest();
    }
}
