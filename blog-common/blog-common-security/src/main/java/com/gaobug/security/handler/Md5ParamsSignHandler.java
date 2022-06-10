package com.gaobug.security.handler;


import com.gaobug.utils.JacksonUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.SortedMap;

/**
 * 请求参数验签处理器 MD5验签实现
 *
 * @author gaobug
 */
@Component
public class Md5ParamsSignHandler extends AbstractParamsSignHandler {

    @Override
    public boolean checkSign(SortedMap<String, String> parameterMap, String timestamp, String sign) {
        StringBuilder sb = new StringBuilder();
        String jsonString = JacksonUtils.toJSONString(parameterMap);
        // 加密参数内容
        if (StringUtils.hasText(jsonString)) {
            sb.append(jsonString);
        }
        // 混淆时间戳
        sb.append(timestamp);
        return Objects.equals(this.toMd5(sb.toString()), sign);
    }

    private String toMd5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes(StandardCharsets.UTF_8));
    }


}
