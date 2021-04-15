package io.gitee.welkinfast.im.session.cache.entity;

import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * session entity cache to redis
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:27
 * @Version 1.0.0
 */
@Data
public class SessionCache implements Serializable {

    private static final long serialVersionUID = -403010884211394856L;

    /**
     * 用户的id
     */
    private String userId;
    /**
     * session id
     */
    private String sessionId;
    /**
     * 节点信息
     */
    private NodeEntity nodeEntity;

    public SessionCache(String userId, String sessionId, NodeEntity nodeEntity) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.nodeEntity = nodeEntity;
    }
}
