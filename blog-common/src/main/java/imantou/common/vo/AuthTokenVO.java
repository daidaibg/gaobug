package imantou.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录令牌
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthTokenVO {
    public static final String AUTH_TOKEN = "auth_token";

    /**
     * 登录令牌
     */
    private final String authToken;
}
