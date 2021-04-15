package io.gitee.welkinfast.im.server.handler.sharable;


import io.gitee.welkinfast.im.processer.ImSendProcesser;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.ChannelType;
import io.gitee.welkinfast.im.session.LocalSession;
import io.gitee.welkinfast.im.session.ServerSession;
import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理消息handler
 *
 * @Author yuanjg
 * @CreateTime 2021/3/14 10:32
 * @Version 1.0.0
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class ChatHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ImSendProcesser imSendProcesser;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (null == msg || !(msg instanceof ImEntity.Message)) {
            super.channelRead(ctx, msg);
            return;
        }

        ImEntity.Message pkg = (ImEntity.Message) msg;
        ImEntity.HeadType headType = ((ImEntity.Message) msg).getType();
        if (!headType.equals(ImEntity.HeadType.MESSAGE_REQUEST)) {
            super.channelRead(ctx, msg);
            return;
        }

        // 判断是否登录,即是否为接收到的用户客户端消息
        LocalSession session = LocalSession.getSession(ctx);
        if (null != session && session.isLogin()) {
            ImEntity.MessageRequest messageRequest = pkg.getMessageRequest();
            log.debug("chatMsg | from="
                    + messageRequest.getFrom()
                    + " , to=" + messageRequest.getTo()
                    + " , content=" + messageRequest.getContent());
            // 获取接收方的chatID
            String to = messageRequest.getTo();
            imSendProcesser.sendUser(to, pkg);
            //todo 消息存储
            return;
        }

        // 接受到重定向后的消息
        String channelType = ctx.channel().attr(SessionManager.CHANNEL_TYPE).get();
        if (ChannelType.REMOTE_CLIENT.name().equals(channelType)) {
            ImEntity.MessageRequest request = pkg.getMessageRequest();
            List<ServerSession> toSessions = sessionManager.get(request.getTo());
            Map<String, Boolean> sendSucess = isSendSucess(toSessions);
            toSessions.forEach((serverSession) -> {
                if (serverSession instanceof LocalSession) {
                    // 将消息发送到接收方
                    serverSession.writeAndFlush(pkg);
                    sendSucess.put(serverSession.getSessionId(), true);
                }
            });
        }
    }

    public Map<String, Boolean> isSendSucess(List<ServerSession> toSessions) {
        Map<String, Boolean> result = new HashMap<>();
        for (int i = 0; i < toSessions.size(); i++) {
            ServerSession serverSession = toSessions.get(i);
            result.put(serverSession.getSessionId(), false);
        }
        return result;
    }


}
