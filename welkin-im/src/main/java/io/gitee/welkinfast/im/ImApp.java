package io.gitee.welkinfast.im;

import io.gitee.welkinfast.im.config.properties.WebSocketProperties;
import io.gitee.welkinfast.im.distributed.LocalNodeProcesser;
import io.gitee.welkinfast.im.distributed.RemoteNodeProcesser;
import io.gitee.welkinfast.im.server.WsServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/14 10:04
 * @Version 1.0.0
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"io.gitee.welkinfast"})
public class ImApp implements CommandLineRunner {

    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private WsServer wsServer;
    @Autowired
    private LocalNodeProcesser localNodeProcesser;
    @Autowired
    private RemoteNodeProcesser remoteNodeProcesser;

    public static void main(String[] args) {
        SpringApplication.run(ImApp.class, args);
    }

    @Override
    public void run(String... strings) {
        wsServer.start(webSocketProperties.getPort());
        localNodeProcesser.initLocalNode(webSocketProperties.getPort());
        remoteNodeProcesser.initWorkerRouter();
    }
}
