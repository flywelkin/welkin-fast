package io.gitee.welkinfast.common.jwt;

import com.alibaba.fastjson.JSON;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.jwt.config.JwtProperties;
import io.gitee.welkinfast.common.jwt.entity.CustomPayload;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * jwt生成 验证服务
 *
 * @Author yuanjg
 * @CreateTime 2020/08/16 14:39
 * @Version 1.0.0
 */
@Slf4j
public class JwtTokenService {


    private static final String USER_INFO = "user_info";

    private JwtProperties jwtProperties;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 公钥解析token
     *
     * @param token 用户请求中的token
     * @return
     */
    private Jws<Claims> parserToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(jwtProperties.getBase64Secret());
        return jwtParser.parseClaimsJws(token);
    }


    /**
     * 创建token
     *
     * @return String
     */
    public String createJwt(CustomUserDetails userInfo) {
        try {
            String jwt = Jwts.builder()
                    .setId(createJti())
                    // 自定义属性 放入用户拥有权限
                    .claim(USER_INFO, JSON.toJSONString(userInfo))
                    // 失效时间
                    .setExpiration(DateTime.now().plusSeconds(jwtProperties.getExpiresSecond()).toDate())
                    // 签名算法和密钥
                    .signWith(SignatureAlgorithm.HS256, jwtProperties.getBase64Secret())
                    .compact();
            return jwt;
        } catch (Exception e) {
            log.error("签名失败: {}", e.getMessage());
            throw new CustomExcption(CustomErrorType.UNAUTHORIZED);
        }
    }

    private static String createJti() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


    /**
     * 是否已过期
     *
     * @param token token
     * @return boolean
     */
    public boolean isExpiration(String token) {
        return parserToken(token).getBody().getExpiration().before(new Date());
    }


    /**
     * 获取token中的用户信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public <T> CustomPayload<T> getInfoFromToken(String token, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token);
        if (claimsJws == null) {
            return null;
        }
        Claims body = claimsJws.getBody();
        CustomPayload<T> claims = new CustomPayload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JSON.parseObject(body.get(USER_INFO).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public <T> CustomPayload<T> getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        CustomPayload<T> claims = new CustomPayload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}
