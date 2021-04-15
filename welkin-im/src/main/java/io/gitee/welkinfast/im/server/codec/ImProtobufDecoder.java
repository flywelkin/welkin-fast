package io.gitee.welkinfast.im.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * 协议包解码
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 09:25
 * @Version 1.0.0
 */
public class ImProtobufDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) throws Exception {
        ByteBuf buf = ((BinaryWebSocketFrame) frame).content();
        objs.add(buf);
        buf.retain();
    }
}
