package com.store.dev.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * Token处理工具
 * CREATED BY X.
 */
public class JwtUtils {
    /**
     * 生成token
     * 默认过期时长30分钟
     */
    public static String newToken(Long userId) {
        return newToken(userId, Constants.DEFAULT_EXPIRED_SECONDS);
    }

    /**
     * @param userId
     * @param expiredTime 过期时长
     * @return
     */
    public static String newToken(Long userId, long expiredTime) {
        return JWT.create()
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime * 1000))
                .sign(Algorithm.HMAC256(Constants.SECRET_KEY));
    }

    /**
     * 校验token是否合法
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constants.SECRET_KEY)).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新token
     */
    public static String refreshToken(String token) {
        // decode解码
        DecodedJWT jwt = JWT.decode(token);
        Long userId = jwt.getClaim("userId").asLong();
        return newToken(userId);
    }

    /**
     * token时间超过一半, 重新刷新时间, 返回新token;
     * 时间没超过一半, 返回原token; 时间过期, 抛异常;
     */
    public static String autoRequire(String token) {
        boolean checkToken = checkToken(token);
        if (checkToken) {
            DecodedJWT jwt = JWT.decode(token);
            // 计算时间是否过时超过50%
            long current = System.currentTimeMillis() / 1000;
            Long start = jwt.getClaim("iat").asLong();
            Long end = jwt.getClaim("exp").asLong();
            if ((current - start) * 1.0 / (end - start) > 0.5) {
                return refreshToken(token);
            } else {
                return token;
            }
        } else {
            throw new JWTVerificationException("token不合法");
        }
    }

    // 从token中获得userId
    public static Long getUserId(String jwtToken) {
        return JWT.decode(jwtToken).getClaim("userId").asLong();
    }


}
