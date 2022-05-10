package com.imantou.auth.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;


/**
 * @author hzq
 */
public class OAuth2Token implements AuthenticationToken, Serializable {

    private static final long serialVersionUID = 1380346913490651431L;

    private final String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
