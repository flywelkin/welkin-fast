package io.gitee.welkinfast.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  权限配置文件父类 X-Auth-Token
 * @Author yuanjg
 * @CreateTime 2020/8/20 12:12
 * @Version 1.0.0
 */
@ConfigurationProperties(value = "welkin.security", ignoreInvalidFields = true)
@Component
public class CustomSecurityProperties {

    // 认证头信息Key
    private String authKey = "Authentication";
    // 匿名名单
    private List<String> anonymous = new ArrayList<>();

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
}
