package io.gitee.welkinfast.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * websocker 服务
 *
 * @Author yuanjg
 * @CreateTime 2021/3/14 10:23
 * @Version 1.0.0
 */
@Slf4j
public class WsServer {

    private WsHandlerInitialzer wsHandlerInitialzer;

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WsServer(WsHandlerInitialzer wsHandlerInitialzer) {
        this.wsHandlerInitialzer = wsHandlerInitialzer;
    }

    public void start(int port) {
        try {
            mainGroup = new NioEventLoopGroup();
            subGroup = new NioEventLoopGroup();
            server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(wsHandlerInitialzer);
            this.future = server.bind(port).sync();
            log.info("im服务启动成功，端口: {}", port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }

    }

    public void shutdown() {
        try {
            if (future != null) {
                future.channel().close().syncUninterruptibly();
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        } finally {
            if (mainGroup != null) {
                mainGroup.shutdownGracefully();
            }
            if (subGroup != null) {
                subGroup.shutdownGracefully();
            }
            log.info("im服务关闭成功");
        }
    }
}
