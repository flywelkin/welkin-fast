package io.gitee.welkinfast.im.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/18 15:03
 * @Version 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "welkin.zookeeper")
public class ZookeeperProperties {

    Integer baseSleepTimeMs = 1000;
    //最大重试次数
    Integer maxRetries = 3;
    //服务器地址
    String server = "127.0.0.1:2181";
    //命名空间，被称为ZNode
    String namespace = "/welkin";
    //权限控制，加密
    String digest = "smile:123456";
    //连接超时时间
    Integer connectionTimeoutMs = 60000;
    //会话超时时间
    Integer sessionTimeoutMs = 3000;


    public Integer getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public Integer getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
}
