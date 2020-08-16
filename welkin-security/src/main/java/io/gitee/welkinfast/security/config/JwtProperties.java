package io.gitee.welkinfast.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description jwt配置
 * @Author yuanjg
 * @CreateTime 2020/8/16 14:41
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    private String clientId = "098f6bcd4621d373cade4e832627b4f6";
    private String base64Secret = "welkin";
    private String name = "welkin";
    private int expiresSecond = 172800;

}
