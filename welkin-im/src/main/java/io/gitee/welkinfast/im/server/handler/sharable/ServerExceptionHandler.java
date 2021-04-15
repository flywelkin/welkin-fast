package io.gitee.welkinfast.im.server.handler.sharable;


import io.gitee.welkinfast.im.session.SessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@ChannelHandler.Sharable
@Service
public class ServerExceptionHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //捕捉异常信息
        cause.printStackTrace();
        log.error(cause.getMessage());
        sessionManager.closeSession(ctx);
        ctx.close();
    }

    /**
     * 通道 Read 读取 Complete 完成
     * 做刷新操作 ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.closeSession(ctx);
    }

}