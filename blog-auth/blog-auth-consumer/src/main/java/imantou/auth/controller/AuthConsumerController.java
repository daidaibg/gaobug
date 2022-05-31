package imantou.auth.controller;

import imantou.auth.api.AuthConsumerService;
import imantou.common.response.ResponseWrapped;
import imantou.common.vo.JwtTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author gaobug
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthConsumerController {
    @Resource
    private AuthConsumerService authProviderService;

    @GetMapping("/authorization")
    public ResponseWrapped<JwtTokenVO> authorization() {
        return authProviderService.authorization();
    }

}