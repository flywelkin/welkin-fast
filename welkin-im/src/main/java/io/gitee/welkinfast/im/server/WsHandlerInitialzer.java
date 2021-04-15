package io.gitee.welkinfast.im.server;

import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.server.codec.ImProtobufDecoder;
import io.gitee.welkinfast.im.server.codec.ImProtobufEncoder;
import io.gitee.welkinfast.im.server.handler.HeartBeatHandler;
import io.gitee.welkinfast.im.server.handler.sharable.ChatHandler;
import io.gitee.welkinfast.im.server.handler.sharable.LoginHandler;
import io.gitee.welkinfast.im.server.handler.sharable.NotificationHandler;
import io.gitee.welkinfast.im.server.handler.sharable.ServerExceptionHandler;
import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务channlerHandler初始化
 *
 * @Author yuanjg
 * @CreateTime 2021/3/14 10:32
 * @Version 1.0.0
 */
@Slf4j
@Service
public class WsHandlerInitialzer extends ChannelInitializer<SocketChannel> {

    private static final String WEBSOCKET_PATH = "/ws";

    private EventLoopGroup group = new DefaultEventLoop();

    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private NotificationHandler notificationHandler;
    @Autowired
    private ChatHandler chatHandler;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ServerExceptionHandler serverExceptionHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 支持http协议
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 支持httpWebsocket
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));


        //  编/解码
        pipeline.addLast(new ImProtobufDecoder());
        pipeline.addLast(new ProtobufDecoder(ImEntity.Message.getDefaultInstance()));
        pipeline.addLast(new ImProtobufEncoder());

        // 增加心跳支持
        pipeline.addLast(new HeartBeatHandler(sessionManager));

        // 登录处理
        pipeline.addLast(loginHandler);
        // 通告处理
        pipeline.addLast(notificationHandler);
        // 消息处理,可能存在耗时操作，使用独立的eventLoopGroup处理程序，减轻subGroup读写线程压力
        pipeline.addLast(group, chatHandler);
        // 异常处理
        pipeline.addLast(serverExceptionHandler);
    }

}
