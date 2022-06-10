package com.gaobug.security.handler;

import com.gaobug.response.constant.RequestHeader;
import com.gaobug.utils.HttpContextUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.SortedMap;
import java.util.function.Supplier;

/**
 * 验签抽类
 *
 * @author gaobug
 */
public abstract class AbstractParamsSignHandler implements IParamsSignHandler {

    /**
     * 执行验签
     *
     * @param request   {@link HttpServletRequest}
     * @param paramFunc 待验签参数返回函数
     * @return 验签结果
     */
    protected boolean doCheck(HttpServletRequest request, Supplier<SortedMap<String, String>> paramFunc) {
        // 签名
        String signValue = HttpContextUtils.getHeaderOrParameter(request, RequestHeader.SIGN_HEADER);
        if (!StringUtils.hasLength(signValue)) {
            return false;
        }
        // 时间戳
        String timestampValue = HttpContextUtils.getHeaderOrParameter(request, RequestHeader.TIMESTAMP);
        if (!StringUtils.hasLength(timestampValue)) {
            return false;
        }
        // 转换为毫秒对比时间戳
        if (20000L < System.currentTimeMillis() - Long.parseLong(timestampValue)) {
            return false;
        }
        SortedMap<String, String> parameterMap = paramFunc.get();
        // 移除 时间戳、签名
        if (null != parameterMap.get(RequestHeader.SIGN_HEADER)) {
            parameterMap.remove(RequestHeader.SIGN_HEADER);
        }
        if (null != parameterMap.get(RequestHeader.TIMESTAMP)) {
            parameterMap.remove(RequestHeader.TIMESTAMP);
        }
        // 验证签名
        return this.checkSign(parameterMap, timestampValue, signValue);
    }

    @Override
    public boolean doCheckSign(HttpServletRequest request) {
        return doCheck(request, () -> HttpContextUtils.getRequestSortedParamsMap(request));
    }


    /**
     * 验证参数签名
     *
     * @param parameterMap {@link SortedMap}
     * @param timestamp    时间戳
     * @param sign         签名值
     * @return
     */
    public abstract boolean checkSign(SortedMap<String, String> parameterMap, String timestamp, String sign);

}
