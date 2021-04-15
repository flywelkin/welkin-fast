package io.gitee.welkinfast.im.service.dao;

import io.gitee.welkinfast.im.service.entity.MessageEntity;

import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/11 18:48
 * @Version 1.0.0
 */
public interface WelkinImDao {

    void save(MessageEntity messageEntity);

    List<MessageEntity> getByUser(String userId);

}
