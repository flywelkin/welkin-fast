package io.gitee.welkinfast.im.server.handler.sharable;

import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.ChannelType;
import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通告处理handler
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 18:11
 * @Version 1.0.0
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class NotificationHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ImEntity.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        ImEntity.Message pkg = (ImEntity.Message) msg;
        ImEntity.HeadType headType = pkg.getType();
        if (!headType.equals(ImEntity.HeadType.MESSAGE_NOTIFICATION)) {
            super.channelRead(ctx, msg);
            return;
        }
        //处理消息的内容
        ImEntity.MessageNotification notificationPkg = pkg.getNotification();
        String json = notificationPkg.getJson();
        log.debug("收到分布式节点连接成功通知, distributed={}", json);
        //节点的链接成功
        if (notificationPkg.getMsgType() == 1) {
            ctx.pipeline().remove(LoginHandler.class);
            ctx.channel().attr(SessionManager.CHANNEL_TYPE).set(ChannelType.REMOTE_CLIENT.name());
            ctx.channel().attr(SessionManager.CHANNEL_NAME).set(json);
        }
    }

}
