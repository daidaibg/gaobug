package imantou.security.handler;

import imantou.security.autoconfigure.SecurityProperties;
import imantou.security.exception.DecryptRequestException;

import imantou.security.utils.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 接口加密处理抽象类
 */
@Slf4j
public abstract class AbstractRestEncryptHandler implements IRestEncryptHandler {

    @Override
    public HttpInputMessage request(SecurityProperties props, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                    Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            String content = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
            byte[] decryptBytes = RSAUtils.encryptByPrivateKey(Base64.encodeBase64(content.getBytes(StandardCharsets.UTF_8)),
                    props.getPrivateKey().getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decryptBytes);
            return new MappingJacksonInputMessage(inputStream, inputMessage.getHeaders());
        } catch (Exception e) {
            throw new DecryptRequestException("RestEncryptHandler request error.", e);
        }
    }

    @Override
    public Object response(SecurityProperties props, Object body, MethodParameter returnType, MediaType selectedContentType,
                           Class<? extends HttpMessageConverter<?>> selectedConverterType,
                           ServerHttpRequest request, ServerHttpResponse response) {
        try {
            /**
             * 返回 base64 加密后的 rsa 密文内容
             */
            return encryptByPrivateKey(this.toJson(body), props.getPrivateKey());
        } catch (Exception e) {
            log.error("RestEncryptHandler response error.", e);
        }
        return body;
    }

    /**
     * 私钥加密
     *
     * @param plaintext  明文
     * @param privateKey 私钥(BASE64编码)
     * @return 返回私钥加密数据
     * @throws Exception
     */
    public String encryptByPrivateKey(String plaintext, String privateKey) throws Exception {
        return Base64.encodeBase64String(RSAUtils.encryptByPrivateKey(plaintext.getBytes(StandardCharsets.UTF_8),
                privateKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 对象转 json 字符串方法
     *
     * @param body 请求对象
     * @return 对象 json 格式化字符串
     */
    public abstract String toJson(Object body);
}
