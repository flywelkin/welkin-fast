package io.gitee.welkinfast.common.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  jwt配置
 * @Author yuanjg
 * @CreateTime 2020/8/16 14:41
 * @Version 1.0.0
 */
@ConfigurationProperties(value = "welkin.jwt", ignoreInvalidFields = true)
public class JwtProperties {

    public static String ADMIN_ROLE = "admin";

    private String issuer = "welkin";
    private String base64Secret = "24758638-3739-4030-A497-2137FE072002[www.welkincode.top]";
    private int expiresSecond = 172800;


    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
