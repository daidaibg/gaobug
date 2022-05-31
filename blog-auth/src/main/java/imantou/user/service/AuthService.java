package imantou.user.service;

import imantou.common.vo.JwtTokenVO;

/**
 * @author gaobug
 */
public interface AuthService {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    JwtTokenVO authorization();
}
