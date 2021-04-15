package io.gitee.welkinfast.im.processer;

import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.ServerSession;
import io.gitee.welkinfast.im.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/24 15:26
 * @Version 1.0.0
 */

@Slf4j
@Service
public class ImSendProcesser {

    @Autowired
    private SessionManager sessionManager;

    /**
     * 发送给指定用户
     *
     * @param userId  用户id
     * @param message 消息体
     */
    public void sendUser(String userId, ImEntity.Message message) {
        List<ServerSession> toSessions = sessionManager.get(userId);
        if (toSessions != null) {
            toSessions.forEach((sessionItem) -> {
                // 将IM消息发送到接收方
                sessionItem.writeAndFlush(message);
            });
        } else {
            //接收方离线
            log.debug("[" + userId + "] 不在线，发送失败!");
        }
    }

    /**
     * 发送给指定用户列表
     *
     * @param userIds 用户id列表
     * @param message 消息体
     */
    public void sendUsers(List<String> userIds, ImEntity.Message message) {

    }

    /**
     * 发送指定群组
     *
     * @param groupId 群组id
     * @param message 消息体
     */
    public void sendGroup(String groupId, ImEntity.Message message) {

    }

    /**
     * 发送指定群组列表
     *
     * @param groupIds 群组列表
     * @param message  消息体
     */
    public void sendGroups(List<String> groupIds, ImEntity.Message message) {

    }

    /**
     * 发送给全部用户
     *
     * @param message
     */
    public void sendAll(ImEntity.Message message) {

    }

}
