package io.gitee.welkinfast.im.session;

import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地用户session
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:10
 * @Version 1.0.0
 */
@Slf4j
public class LocalSession implements ServerSession {

    public static final AttributeKey<String> USER_ID_KEY = AttributeKey.valueOf("user_id");
    public static final AttributeKey<LocalSession> SESSION_ID_KEY = AttributeKey.valueOf("session_Id");

    /**
     * session中存储的session 变量属性值
     */
    private Map<String, Object> map = new HashMap<String, Object>();

    private String userId = null;

    /**
     * session唯一标示
     */
    private final String sessionId;

    /**
     * 通道
     */
    private Channel channel;

    /**
     * 登录状态
     */
    private boolean isLogin = false;

    /**
     * 通过会话通告创建session
     *
     * @param channel
     */
    public LocalSession(Channel channel) {
        this.channel = channel;
        this.sessionId = CustomIdGenerator.getNext();
    }

    /**
     * session绑定用户信息
     *
     * @param userId 用户ID
     * @return
     */
    public LocalSession bind(String userId) {
        log.debug(" LocalSession绑定会话，user: {} sessionId: {},", userId, sessionId);
        this.userId = userId;
        channel.attr(LocalSession.SESSION_ID_KEY).set(this);
        channel.attr(LocalSession.USER_ID_KEY).set(userId);
        channel.attr(SessionManager.CHANNEL_TYPE).set(ChannelType.USER.name());
        channel.attr(SessionManager.CHANNEL_NAME).set(userId);
        isLogin = true;
        return this;
    }

    /**
     * 反向导航,通过channel获取到localSession
     */
    public static LocalSession getSession(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        return channel.attr(LocalSession.SESSION_ID_KEY).get();
    }

    @Override
    public boolean isValid() {
        return StringUtils.isNotEmpty(userId) ? true : false;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public synchronized void writeAndFlush(Object pkg) {
        if (channel.isWritable()) {
            //低水位
            channel.writeAndFlush(pkg);
        } else {
            // todo 高水位时
            log.debug("通道很忙，消息被暂存了");
            //写入消息暂存的分布式存储，如果mongo
            //等channel空闲之后，再写出去
        }
    }

    /**
     * 发送消息并关闭通道
     *
     * @param message
     */
    public void writeAndClose(ImEntity.Message message) {
        this.channel.writeAndFlush(message);
        this.close();
    }

    /**
     * 用户下线关闭连接
     */
    public synchronized void close() {
        isLogin = false;
        channel.close();
    }

    public synchronized <T> T get(String key) {
        Object o = map.get(key);
        if (o != null) {
            return (T) o;
        }
        return null;
    }


    public synchronized void set(String key, Object value) {
        map.put(key, value);
    }


    public Boolean isLogin() {
        return isLogin;
    }
}
