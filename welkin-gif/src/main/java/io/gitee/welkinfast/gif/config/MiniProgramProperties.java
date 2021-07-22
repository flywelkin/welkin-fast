package io.gitee.welkinfast.gif.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/19 14:41
 * @Version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(value = "mini.program")
public class MiniProgramProperties {

    private String gifFilePath = "E:/";
    private String appId = "appId";
    private String secret = "secret";
    private String cloudEnv = "";
}
