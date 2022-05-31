package imantou.limiter.metadata;


import imantou.limiter.RateLimit;
import imantou.limiter.request.CustomRequestWrapper;

import com.imantou.exception.metadata.AbstractMethodMetaData;
import com.imantou.exception.utils.JacksonUtils;
import com.imantou.exception.utils.MethodUtils;
import com.imantou.exception.utils.RequestUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;


/**
 * 限流注解meta
 *
 * @author hzq
 */
public class RateLimitMethodMetaData extends AbstractMethodMetaData {
    /**
     * 速率限制所用到的注解
     */
    private final RateLimit rateLimit;

    private SortedMap<String, String> sortedArgsMap;

    public RateLimitMethodMetaData(Method method, String[] parameterNames, Supplier<Object[]> argsSupplier,
                                   RateLimit rateLimit) {
        super(method, parameterNames, argsSupplier, MethodUtils.getClassMethodName(method));
        Assert.notNull(argsSupplier, "'RateLimitAnnotation' cannot be null");
        this.rateLimit = rateLimit;
    }

    @Override
    public String[] getKeys() {
        return rateLimit.keys();
    }

    @Override
    public SortedMap<String, String> getArgsMap() {
        if (ObjectUtils.isEmpty(sortedArgsMap)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(sortedArgsMap)) {
                    HttpServletRequest request = RequestUtils.getRequest();
                    try {
                        // 重新包装流
                        CustomRequestWrapper customRequest = new CustomRequestWrapper(request);
                        if (Objects.equals(HttpMethod.GET.name(), customRequest.getMethod())) {
                            // 处理get方法
                            sortedArgsMap = this.doGetRequest(customRequest);
                        } else if (customRequest.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
                            sortedArgsMap = this.doPostRequest(customRequest);
                        } else if (customRequest.getContentType().contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                            sortedArgsMap = this.doPostRequest(customRequest);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sortedArgsMap;
    }

    private SortedMap<String, String> doGetRequest(HttpServletRequest request) {
        SortedMap<String, String> parameterMap = new TreeMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String parameter = request.getParameter(name);
            if (StringUtils.hasLength(parameter)) {
                parameterMap.put(name, parameter);
            }
        }
        return parameterMap;
    }

    @SuppressWarnings("unchecked")
    private SortedMap<String, String> doPostRequest(HttpServletRequest request) throws IOException {
        // 请求body内容处理
        SortedMap<String, String> parameterMap = new TreeMap<>();
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String jsonStr = new String(bodyBytes, request.getCharacterEncoding());
        if (StringUtils.hasLength(jsonStr) && jsonStr.contains("{")) {
            parameterMap = JacksonUtils.parse(jsonStr, TreeMap.class);
        }
        return parameterMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Annotation> T getAnnotation() {
        return (T) rateLimit;
    }
}
