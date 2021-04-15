package io.gitee.welkinfast.im.store;

import io.gitee.welkinfast.im.proto.ImEntity;

/**
 * @Author yuanjg 消息存储
 * @CreateTime 2021/04/11 13:55
 * @Version 1.0.0
 */
public interface MessageStore {

    boolean save(ImEntity.Message message);
}
