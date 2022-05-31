package imantou.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录表单
 * @author imantou
 */
@Getter
@Setter
@ToString
public class LoginForm {
    private String username;
    private String password;
    private Integer captcha;
}
