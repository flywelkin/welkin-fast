package io.gitee.welkinfast.im.distributed.client;

import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.server.codec.ImProtobufDecoder;
import io.gitee.welkinfast.im.server.codec.ImProtobufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * WebSocket 客户端
 *
 * @Author yuanjg
 * @CreateTime 2021/03/22 19:45
 * @Version 1.0.0
 */
@Slf4j
public class WebSocketClient {

    private NodeEntity nodeEntity = null;
    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    private ChannelFuture channelFuture = null;
    private Channel channel = null;
    private Boolean connectFlag = false;

    public WebSocketClient(NodeEntity nodeEntity) {
        this.nodeEntity = nodeEntity;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();

    }

    /**
     * 启动客户端
     * 初始化客户端引导程序
     */
    public void start() {
        try {
            if (bootstrap != null && bootstrap.group() == null) {
                this.bootstrap.group(this.group)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .channel(NioSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.DEBUG));
                this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 10));

                        pipeline.addLast(new ImProtobufDecoder());
                        pipeline.addLast(new ProtobufDecoder(ImEntity.Message.getDefaultInstance()));
                        pipeline.addLast(new ImProtobufEncoder());

                        pipeline.addLast(new WebSocketClientHandler(nodeEntity));
                    }
                });
                doConnect();
            } else if (bootstrap.group() != null) {
                log.debug("分布式节点客户端[{}]再一次开始连接", nodeEntity);
                doConnect();
            }
        } catch (Exception ex) {
            log.error("分布式节点客户端[{}]连接出错:{}", nodeEntity, ex.getMessage(), ex);
        }
    }

    /**
     * 进行连接操作
     *
     * @throws Exception
     */
    private void doConnect() throws Exception {
        String svrUrl = getUri();
        URI wsURI = new URI(svrUrl);
        this.channelFuture = this.bootstrap.connect(wsURI.getHost(), wsURI.getPort()).sync();
        this.channelFuture.addListener(connectedListener(wsURI));
        this.channel = this.channelFuture.channel();
    }


    /**
     * 连接监听器
     * 连接成功：进行websocket的握手操作
     * 连接失败：10S后进行客户端重启
     *
     * @param wsURI
     * @return
     */
    public GenericFutureListener<ChannelFuture> connectedListener(URI wsURI) {
        return new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("分布式节点客户端[{}]连接结果: {} isSuccess={}", nodeEntity, channelFuture.isSuccess());
                if (channelFuture.isSuccess()) {
                    log.debug("分布式节点客户端[{}]连接握手开始", nodeEntity);
                    Channel channel = channelFuture.channel();
                    HttpHeaders httpHeaders = new DefaultHttpHeaders();
                    WebSocketClientHandler handler = channel.pipeline().get(WebSocketClientHandler.class);
                    WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(wsURI,
                            WebSocketVersion.V13, (String) null, true, httpHeaders);
                    handler.setHandshaker(handshaker);
                    handshaker.handshake(channel);
                    handler.handshakeFuture();
                    log.debug(String.format("分布式节点客户端[{}]连接握手完成", nodeEntity));
                    channelFuture.addListener(closeListener());
                    connectFlag = true;
                } else {
                    log.debug("分布式节点客户端[{}]连接失败,在10s之后准备尝试重连", nodeEntity);
                    final EventLoop eventLoop = channelFuture.channel().eventLoop();
                    eventLoop.schedule(() -> start(), 10, TimeUnit.SECONDS);
                    connectFlag = false;
                }
            }
        };
    }

    /**
     * 连接关闭监听器
     *
     * @return
     */
    public GenericFutureListener<ChannelFuture> closeListener() {
        return new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.channel().isActive()) {
                    log.debug("分布式节点客户端[{}]连接已经断开 ", nodeEntity);
                    connectFlag = false;
                }
            }
        };
    }

    /**
     * 连接uri
     *
     * @return
     */
    public String getUri() {
        return String.format("ws://%s:%s/ws", this.nodeEntity.getHost(), this.nodeEntity.getPort());
    }

    /**
     * 向服务端写数据
     *
     * @param pkg
     */
    public void writeAndFlush(Object pkg) {
        if (!connectFlag) {
            log.error("分布式节点客户端[{}]未连接!", nodeEntity);
            return;
        }
        this.channel.writeAndFlush(pkg);
    }

    /**
     * 同步等待连接关闭
     */
    public void close() {
        try {
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (group != null) {
                group.shutdownGracefully();
            }
            this.connectFlag = false;
            log.debug("分布式节点客户端[{}]连接已关闭", nodeEntity);
        }
    }

    /**
     * 停止客户端
     */
    public void stopConnecting() {
        group.shutdownGracefully();
        connectFlag = false;
    }

    /**
     * 获取客户端节点信息
     *
     * @return
     */
    public NodeEntity getNode() {
        return this.nodeEntity;
    }
}
