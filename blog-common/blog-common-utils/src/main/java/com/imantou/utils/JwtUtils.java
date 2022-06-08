package com.imantou.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Objects;

/**
 * jwt工具类
 */
public class JwtUtils {
    private static final String ISSUER = "auth";
    private static final String SECRET = "imantou@!";
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 3;  //过期时间3天

    /**
     * 构建一个 token
     *
     * @param authToken 登录令牌
     * @return jwtToken
     */
    public static String createToken(String authToken) {
        try {
            Date now = new Date();
            long expMillis = now.getTime() + EXPIRE;
            Date expDate = new Date(expMillis);
            Algorithm algorithmHS = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withJWTId(authToken)
                    .withIssuedAt(now)
                    .withExpiresAt(expDate)
                    .sign(algorithmHS);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    /**
     * 解析 token
     *
     * @param token jwt令牌 {@link #createToken(String)}
     * @return 是否有效
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return Objects.isNull(jwt.getId());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return false;
        }
    }

    /**
     * 解析 token
     * 返回  userid
     *
     * @param token jwt令牌 {@link #createToken(String)}
     * @return 令牌解析的内容
     */
    public static String decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getId();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return null;
        }
    }
}
