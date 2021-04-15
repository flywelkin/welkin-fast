package io.gitee.welkinfast.im.config;

import io.gitee.welkinfast.im.distributed.LocalNodeProcesser;
import io.gitee.welkinfast.im.distributed.RemoteNodeProcesser;
import io.gitee.welkinfast.im.server.WsHandlerInitialzer;
import io.gitee.welkinfast.im.server.WsServer;
import io.gitee.welkinfast.im.session.SessionManager;
import io.gitee.welkinfast.im.session.cache.SessionCacheService;
import io.gitee.welkinfast.im.session.cache.UserCacheSevice;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/14 10:28
 * @Version 1.0.0
 */
@Slf4j
@Configuration
public class WebSocketConfig {


    @Bean
    @ConditionalOnBean({SessionCacheService.class, UserCacheSevice.class})
    public SessionManager sessionManager(SessionCacheService sessionCacheService,
                                         UserCacheSevice userCacheSevice) {
        return new SessionManager(sessionCacheService, userCacheSevice);
    }

    @Bean
    public WsServer wsServer(WsHandlerInitialzer wsHandlerInitialzer) {
        return new WsServer(wsHandlerInitialzer);
    }


    /**
     * 实例化当前节点管理程序
     *
     * @param curatorFramework
     * @return
     */
    @Bean
    public LocalNodeProcesser localNodeProcesser(CuratorFramework curatorFramework) {
        return new LocalNodeProcesser(curatorFramework);
    }

    /**
     * 实例化远程节点管理程序
     *
     * @param curatorFramework
     * @return
     */
    @Bean
    public RemoteNodeProcesser remoteNodeProcesser(CuratorFramework curatorFramework) {
        return new RemoteNodeProcesser(curatorFramework);
    }
}
