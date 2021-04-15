package io.gitee.welkinfast.im.distributed.client;

import com.alibaba.fastjson.JSONObject;
import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.im.distributed.LocalNodeClient;
import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/22 19:48
 * @Version 1.0.0
 */
@Slf4j
public class WebSocketClientHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 50;

    private WebSocketClientHandshaker handshaker = null;
    private ChannelPromise handshakeFuture = null;
    private NodeEntity nodeEntity = null;

    public WebSocketClientHandler(NodeEntity nodeEntity) {
        this.nodeEntity = nodeEntity;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!this.handshaker.isHandshakeComplete()) {
            FullHttpResponse response = (FullHttpResponse) msg;
            this.handshaker.finishHandshake(ctx.channel(), response);
            this.handshakeFuture.setSuccess();
            log.debug("分布式节点客户端[{}]握手协议返回，设置结束握手", nodeEntity);
            //发送链接成功的通知
            ImEntity.Message.Builder mb =
                    ImEntity.Message
                            .newBuilder()
                            .setType(ImEntity.HeadType.MESSAGE_NOTIFICATION)
                            .setSessionId("unknown")
                            .setId(CustomIdGenerator.getNext());
            ImEntity.Message message = mb.buildPartial();
            ImEntity.MessageNotification.Builder rb =
                    ImEntity.MessageNotification.newBuilder()
                            .setMsgType(1)
                            .setSender(LocalNodeClient.getInstance().get().getHost() + ":" + LocalNodeClient.getInstance().get().getHost())
                            .setTimestamp(String.valueOf(System.currentTimeMillis()))
                            .setJson(JSONObject.toJSONString(LocalNodeClient.getInstance().get()));
            ImEntity.Message pkg = message.toBuilder().setNotification(rb).build();
            ctx.channel().writeAndFlush(pkg);
            log.debug("分布式节点客户端[{}]连接成功发送上线通知", nodeEntity);
            heartBeat(ctx);
            log.debug("分布式节点客户端[{}]连接成功设置心跳定时器", nodeEntity);
        }
    }

    /**
     * 使用定时器，发送心跳报文
     */
    public void heartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            log.debug("分布式节点客户端[{}]发送心跳定,isActive={}", nodeEntity, ctx.channel().isActive());
            ImEntity.Message.Builder mb =
                    ImEntity.Message
                            .newBuilder()
                            .setType(ImEntity.HeadType.KEEPALIVE_REQUEST)
                            .setSessionId("unknown")
                            .setId(CustomIdGenerator.getNext());
            ImEntity.Message heartbeatMsg = mb.buildPartial();
            ImEntity.MessageHeartBeat.Builder lb =
                    ImEntity.MessageHeartBeat.newBuilder()
                            .setContent("{\"from\":\"imNode\"}");
            heartbeatMsg.toBuilder().setMessageHeartBeat(lb).build();
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(heartbeatMsg);
                //递归调用，发送下一次的心跳
                heartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

}
