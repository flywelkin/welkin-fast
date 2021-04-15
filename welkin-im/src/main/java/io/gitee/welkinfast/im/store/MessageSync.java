package io.gitee.welkinfast.im.store;

import io.gitee.welkinfast.im.proto.ImEntity;

/**
 * @Author yuanjg 消息同步
 * @CreateTime 2021/04/11 13:56
 * @Version 1.0.0
 */

public interface MessageSync {

    boolean save(ImEntity.Message message);

}
