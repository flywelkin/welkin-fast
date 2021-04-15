package io.gitee.welkinfast.im.server.handler.sharable;

import io.gitee.welkinfast.im.processer.LoginProcesser;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.LocalSession;
import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录管理handler
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 09:04
 * @Version 1.0.0
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LoginProcesser loginProcesser;
    @Autowired
    private SessionManager sessionManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ImEntity.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        ImEntity.Message message = (ImEntity.Message) msg;
        ImEntity.HeadType type = message.getType();
        if (!ImEntity.HeadType.LOGIN_REQUEST.equals(type)) {
            super.channelRead(ctx, msg);
            return;
        }

        LocalSession localSession = new LocalSession(ctx.channel());
        try {
            boolean action = loginProcesser.action(localSession, message);
            if (action) {
                ctx.pipeline().remove(LoginHandler.this);
                log.debug("登录成功,uesrId: {}, session: {}", localSession.getUserId(), localSession.getSessionId());
            } else {
                sessionManager.closeSession(ctx);
                log.debug("登录失败,sessionId:{}", localSession.getSessionId());
            }
        } catch (Exception e) {
            sessionManager.closeSession(ctx);
            log.debug("登录失败:", e);
        }
    }
}
