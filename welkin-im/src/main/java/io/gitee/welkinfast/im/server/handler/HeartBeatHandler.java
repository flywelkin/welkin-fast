package io.gitee.welkinfast.im.server.handler;

import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.ChannelType;
import io.gitee.welkinfast.im.session.ServerSession;
import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的空闲状态检测
 * 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 16:36
 * @Version 1.0.0
 */
@Slf4j
public class HeartBeatHandler extends IdleStateHandler {

    private static final int READ_IDLE_GAP = 60;

    private final SessionManager sessionManager;

    public HeartBeatHandler(SessionManager sessionManager) {
        super(READ_IDLE_GAP, 0, 0, TimeUnit.SECONDS);
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ImEntity.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        ImEntity.Message pkg = (ImEntity.Message) msg;
        ImEntity.HeadType headType = pkg.getType();
        if (headType.equals(ImEntity.HeadType.KEEPALIVE_REQUEST)) {
            String s = ctx.channel().attr(SessionManager.CHANNEL_TYPE).get();
            if (StringUtils.equalsIgnoreCase(s, ChannelType.REMOTE_CLIENT.name())) {
                String channelName = ctx.channel().attr(SessionManager.CHANNEL_NAME).get();
                log.debug("接收到分布式节点心跳: {}", channelName);
            } else {
                String userId = ctx.channel().attr(SessionManager.CHANNEL_NAME).get();
                log.debug("接收到用户心跳: {}", userId);
                // 接收到心跳，更新用户分布式缓存信息
                List<ServerSession> serverSessions = sessionManager.get(userId);
                if (!CollectionUtils.isEmpty(serverSessions)) {
                    serverSessions.stream().forEach(item -> {
                        sessionManager.update(item);
                    });
                }
            }
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(msg);
            }
        }
        super.channelRead(ctx, msg);
    }


    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.debug(READ_IDLE_GAP + "秒内未读到数据，关闭连接", ctx.channel().attr(SessionManager.CHANNEL_NAME).get());
        sessionManager.closeSession(ctx);
    }
}
