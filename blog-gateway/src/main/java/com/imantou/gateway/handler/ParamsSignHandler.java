package com.imantou.gateway.handler;


import com.alibaba.nacos.common.utils.JacksonUtils;
import com.imantou.gateway.exception.ParamsSignException;
import com.imantou.response.constant.RequestHeader;
import com.imantou.utils.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.SortedMap;
import java.util.function.Supplier;

/**
 * 请求参数验签处理器 MD5验签实现
 *
 * @author gaobug
 */
@Slf4j
@Component
public class ParamsSignHandler implements IParamsSignHandler {

    @Override
    public boolean checkSign(HttpHeaders httpHeaders, Supplier<SortedMap<String, String>> sortedMapSupplier) {
        // 签名
        String signValue = httpHeaders.getFirst(RequestHeader.SIGN_HEADER);
        if (!StringUtils.hasLength(signValue)) {
            log.error("Sign error: sign field does not exist");
            throw new ParamsSignException("参数验签失败");
        }
        // 时间戳
        String timestampValue = httpHeaders.getFirst(RequestHeader.TIMESTAMP);
        if (!StringUtils.hasLength(timestampValue)) {
            log.error("Sign error: timestamp field does not exist");
            throw new ParamsSignException("参数验签失败");
        }
        // 转换为毫秒对比时间戳
        if (20000L < System.currentTimeMillis() - Long.parseLong(timestampValue)) {
            log.error("Sign error:request timestamp is expired");
            throw new ParamsSignException("参数验签失败");
        }
        SortedMap<String, String> parameterMap = sortedMapSupplier.get();
        log.info("parameterMap：[{}]", parameterMap);
        // 移除 时间戳、签名
        if (null != parameterMap.get(RequestHeader.SIGN_HEADER)) {
            parameterMap.remove(RequestHeader.SIGN_HEADER);
        }
        if (null != parameterMap.get(RequestHeader.TIMESTAMP)) {
            parameterMap.remove(RequestHeader.TIMESTAMP);
        }
        // 验证签名
        StringBuilder sb = new StringBuilder();
        String jsonString = JacksonUtils.toJson(parameterMap);
        // 加密参数内容
        if (StringUtils.hasText(jsonString)) {
            sb.append(jsonString);
        }
        // 混淆时间戳
        sb.append(timestampValue);
        log.info("加密数据：[{}]", sb);
        log.info("sign：[{}]", signValue);
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8));
        log.info("md5DigestAsHex：[{}]", md5DigestAsHex);
        log.info("对比结果：[{}]", Objects.equals(md5DigestAsHex, signValue));
        return Objects.equals(md5DigestAsHex, signValue);
    }
}
