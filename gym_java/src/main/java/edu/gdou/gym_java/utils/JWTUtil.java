package edu.gdou.gym_java.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.gdou.gym_java.exception.CustomException;
import edu.gdou.gym_java.shiro.redis.Constant;
import edu.gdou.gym_java.shiro.redis.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        try {
            String currentTimeMillis= String.valueOf(System.currentTimeMillis());
            Date date = new Date(currentTimeMillis+EXPIRE_TIME);
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(会覆盖已有的RefreshToken)
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN +username, currentTimeMillis, Constant.refreshTokenExpireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim(Constant.USERNAME, username)
                    .withClaim(Constant.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token token
     * @param claim key
     * @return String
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new CustomException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return 返回是否已过期，false为未过期，true为过期
     */
    public static boolean isExp(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("exp").asDate().compareTo(new Date())<=0;
        } catch (JWTDecodeException e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return 返回签发时间
     */
    public static String getCreateTime(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.CURRENT_TIME_MILLIS).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
