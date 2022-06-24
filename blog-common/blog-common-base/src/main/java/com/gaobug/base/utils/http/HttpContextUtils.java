package com.gaobug.base.utils.http;

import com.gaobug.base.utils.parse.JacksonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * http请求工具类
 *
 * @author gaobug
 */
public class HttpContextUtils {

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * 获取请求头中的Origin
     *
     * @param request {@link HttpServletRequest}
     * @return 请求头中的Origin
     */
    public static String getOrigin(HttpServletRequest request) {
        return request.getHeader("Origin");
    }

    /**
     * 请求头或者请求中查询指定参数
     *
     * @param request {@link HttpServletRequest}
     * @param name    参数名称
     * @return 指定参数
     */
    public static String getHeaderOrParameter(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        return StringUtils.hasLength(value) ? value : request.getParameter(name);
    }

    /**
     * 请求中所有参数
     *
     * @param request {@link HttpServletRequest}
     * @return 请求中所有参数（自然排序）
     */
    public static SortedMap<String, String> getRequestSortedParamsMap(HttpServletRequest request) {
        try {
            // 重新包装流
            if (Objects.equals(HttpMethod.GET.name(), request.getMethod())) {
                // 处理get方法
                return doGetRequest(request);
            } else if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
                return doPostRequest(request);
            } else if (request.getContentType().contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                return doPostRequest(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取get请求中所有参数
     *
     * @param request {@link HttpServletRequest}
     * @return 请求中所有参数（自然排序）
     */
    private static SortedMap<String, String> doGetRequest(HttpServletRequest request) {
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

    /**
     * 获取post请求中所有参数
     *
     * @param request {@link HttpServletRequest}
     * @return 请求中所有参数（自然排序）
     */
    @SuppressWarnings("unchecked")
    private static SortedMap<String, String> doPostRequest(HttpServletRequest request) throws IOException {
        // 请求body内容处理
        SortedMap<String, String> parameterMap = new TreeMap<>();
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String jsonStr = new String(bodyBytes, request.getCharacterEncoding());
        if (StringUtils.hasLength(jsonStr) && jsonStr.contains("{")) {
            parameterMap = JacksonUtils.parse(jsonStr, TreeMap.class);
        }
        return parameterMap;
    }

}
