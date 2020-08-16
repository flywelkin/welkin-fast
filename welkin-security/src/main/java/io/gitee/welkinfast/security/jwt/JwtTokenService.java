package io.gitee.welkinfast.security.jwt;

import com.alibaba.fastjson.JSON;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.security.config.JwtProperties;
import io.gitee.welkinfast.security.entity.CustomPayload;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Description jwt生成 验证服务
 * @Author yuanjg
 * @CreateTime 2020/08/16 14:39
 * @Version 1.0.0
 */
@Slf4j
@Service
public class JwtTokenService {

    private static final String USER_INFO = "user_info";
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 公钥解析token
     *
     * @param token 用户请求中的token
     * @return
     */
    private static Jws<Claims> parserToken(String token, JwtProperties audience) {
        return Jwts.parser().setSigningKey(audience.getBase64Secret()).parseClaimsJws(token);
    }


    /**
     * 创建token
     *
     * @return String
     */
    public String createJwt(DefaultUserDetails userInfo) {
        try {
            String id = userInfo.getId();
            //缓存jwt
            String jwt = Jwts.builder()
                    .claim(USER_INFO, JSON.toJSONString(userInfo))
                    .setId(createJti())
                    .setExpiration(DateTime.now().plusSeconds(jwtProperties.getExpiresSecond()).toDate())
                    .signWith(SignatureAlgorithm.HS256, jwtProperties.getBase64Secret())
                    .compact();
            return jwt;
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new WelkinExcption(WelkinErrorType.PERMISSION_SIGNATURE_ERROR);
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
        return parserToken(token, jwtProperties).getBody().getExpiration().before(new Date());
    }


    /**
     * 获取token中的用户信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public <T> CustomPayload<T> getInfoFromToken(String token, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, jwtProperties);
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
    public static <T> CustomPayload<T> getInfoFromToken(String token, JwtProperties audience) {
        Jws<Claims> claimsJws = parserToken(token, audience);
        Claims body = claimsJws.getBody();
        CustomPayload<T> claims = new CustomPayload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}
