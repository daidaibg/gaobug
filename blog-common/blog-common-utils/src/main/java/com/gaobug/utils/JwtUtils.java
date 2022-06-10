package com.gaobug.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * jwt工具类
 */
public class JwtUtils {
    private static final String ISSUER = "auth";
    private static final String SECRET = "gaobug@!";
    private static final long EXPIRE = 2 * 60 * 1000;  //过期时间2分钟


    /**
     * 构建一个 token
     *
     * @param authToken 登录令牌
     * @return jwtToken
     */
    public static String createToken(String authToken, Map<String, ?> payloadClaims) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SECRET);
            Date now = new Date();
            long expMillis = now.getTime() + EXPIRE;
            Date expDate = new Date(expMillis);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withJWTId(authToken)
                    .withIssuedAt(now)
                    .withExpiresAt(expDate)
                    .withPayload(payloadClaims)
                    .sign(algorithmHS);
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
            Algorithm algorithmHS = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithmHS)
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
     * 解析 token，获取JWT ID
     *
     * @param token jwt令牌 {@link #createToken(String)}
     * @return JWT ID 值或 null {@link DecodedJWT#getId()} ()}}
     */
    public static String getJwtId(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getId();
    }

    /**
     * 解析token，获取JWT的标头
     * <p>
     * 带校验jwt有效性
     *
     * @param token jwt令牌 {@link #createToken(String)}
     * @return JWT的标头 {@link DecodedJWT#getHeader()}}
     */
    public static String getHeader(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getHeader();
    }

    /**
     * 解析token，JWT的有效负载
     *
     * @param token jwt令牌 {@link #createToken(String)}
     * @return JWT的有效负载 {@link DecodedJWT#getPayload()}
     */
    public static String getPayload(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getPayload();
    }

    /**
     * 解析 token
     * <p>
     * 带校验jwt有效性
     *
     * @param token 解析token
     * @return DecodedJWT {@link DecodedJWT}
     */
    public static DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build(); //Reusable verifier instance
        return verifier.verify(token);
    }

}
