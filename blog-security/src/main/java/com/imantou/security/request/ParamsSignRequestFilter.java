package com.imantou.security.request;

import com.imantou.security.autoconfigure.SecurityProperties;
import com.imantou.security.handler.IParamsSignHandler;
import com.imantou.security.handler.Md5ParamsSignHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 请求参数签名过滤器
 */
@Setter
@Getter
public class ParamsSignRequestFilter extends OncePerRequestFilter implements Ordered {
    private IParamsSignHandler paramsSignHandler;

    public ParamsSignRequestFilter(SecurityProperties securityProperties) {
        this.paramsSignHandler = new Md5ParamsSignHandler(securityProperties);
    }

    public ParamsSignRequestFilter(IParamsSignHandler paramsSignHandler) {
        this.paramsSignHandler = paramsSignHandler;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 6;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 请求参数验签
        boolean isOk = false;
        ParamsSignRequestWrapper signRequest = new ParamsSignRequestWrapper(request);
        if (Objects.equals(HttpMethod.GET.name(), signRequest.getMethod())) {
            isOk = paramsSignHandler.doGet(signRequest);
        } else if (signRequest.getContentType().contains("application/json")) {
            // 请求body内容处理
            isOk = paramsSignHandler.doPost(signRequest);
        }
        if (isOk) {
            filterChain.doFilter(signRequest, response);
        } else {
            this.invalidSign(response);
        }
    }

    /**
     * 无效签名
     *
     * @param response 请求响应对象
     */
    public void invalidSign(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("The request signature is incorrect".getBytes());
    }
}