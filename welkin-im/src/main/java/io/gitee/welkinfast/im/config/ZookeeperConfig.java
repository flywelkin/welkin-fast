package io.gitee.welkinfast.im.config;

import io.gitee.welkinfast.im.config.properties.ZookeeperProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper配置
 *
 * @Author yuanjg
 * @CreateTime 2021/03/18 15:00
 * @Version 1.0.0
 */
@Slf4j
@Configuration
public class ZookeeperConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean
    public CuratorFramework curatorFramework() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(
                zookeeperProperties.getBaseSleepTimeMs(),
                zookeeperProperties.getMaxRetries());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperProperties.getServer())
                .connectionTimeoutMs(zookeeperProperties.getConnectionTimeoutMs())
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }

}
