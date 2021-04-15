package io.gitee.welkinfast.im.session;

import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import io.gitee.welkinfast.im.distributed.RemoteNodeProcesser;
import io.gitee.welkinfast.im.distributed.client.WebSocketClient;
import io.gitee.welkinfast.im.session.cache.entity.SessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * 远程用户会话
 *
 * @Author yuanjg
 * @CreateTime 2021/3/23 17:17
 * @Version 1.0.0
 */
@Slf4j
public class RemoteSession implements ServerSession, Serializable {

    private static final long serialVersionUID = -400010884211394846L;

    SessionCache cache;

    public RemoteSession(SessionCache cache) {
        this.cache = cache;
    }

    @Override
    public boolean isValid() {
        return cache == null;
    }

    @Override
    public String getSessionId() {
        return cache.getSessionId();
    }

    @Override
    public String getUserId() {
        return cache.getUserId();
    }


    /**
     * 通过远程节点客户端转发消息
     */
    @Override
    public void writeAndFlush(Object pkg) {
        NodeEntity nodeEntity = cache.getNodeEntity();
        long nodeId = nodeEntity.getId();
        WebSocketClient webSocketClient = RemoteNodeProcesser.WORKER_ROUTER.get(nodeId);
        if (!ObjectUtils.isEmpty(webSocketClient)) {
            webSocketClient.writeAndFlush(pkg);
        } else {
            log.warn("远程节点客户端为空:{}", nodeId);
        }
    }


}
