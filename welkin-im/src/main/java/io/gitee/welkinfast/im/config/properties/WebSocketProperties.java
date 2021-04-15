package io.gitee.welkinfast.im.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/14 10:25
 * @Version 1.0.0
 */
@Component
@ConfigurationProperties(value = "welkin.im")
public class WebSocketProperties {
    private Integer port = 8888;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
