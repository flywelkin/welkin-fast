package io.gitee.welkinfast.security.properties;

import io.gitee.welkinfast.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 权限配置文件父类 X-Auth-Token
 * @Author yuanjg
 * @CreateTime 2020/8/20 12:12
 * @Version 1.0.0
 */
@ConfigurationProperties(value = "welkin.security", ignoreInvalidFields = true)
@Component
public class CustomSecurityProperties {

    public static String ADMIN_ROLE = "";

    @PostConstruct
    public void init() {
        ADMIN_ROLE = adminRole;
    }

    // 认证头信息Key
    private String authKey = "Authentication";
    // 超级管理员标识
    private String adminRole = "administrator";
    // jwt 配置
    private JwtProperties jwt = new JwtProperties();
    // 匿名名单
    private List<String> anonymous = new ArrayList<>();

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public List<String> getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(List<String> anonymous) {
        this.anonymous = anonymous;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }
}
