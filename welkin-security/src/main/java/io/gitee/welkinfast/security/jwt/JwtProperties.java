package io.gitee.welkinfast.security.jwt;

/**
 * @Description jwt配置
 * @Author yuanjg
 * @CreateTime 2020/8/16 14:41
 * @Version 1.0.0
 */
public class JwtProperties {

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
