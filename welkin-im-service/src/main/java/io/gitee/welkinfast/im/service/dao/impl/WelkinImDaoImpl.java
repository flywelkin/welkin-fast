package io.gitee.welkinfast.im.service.dao.impl;

import io.gitee.welkinfast.im.service.dao.WelkinImDao;
import io.gitee.welkinfast.im.service.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/11 18:56
 * @Version 1.0.0
 */
@Slf4j
@Service
public class WelkinImDaoImpl implements WelkinImDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(MessageEntity messageEntity) {
        messageEntity.setUserId(messageEntity.getFrom());
        mongoTemplate.save(messageEntity);
        // 确保点对点会话中每个用户都保存一份完整的记录
        MessageEntity newEntity = new MessageEntity();
        BeanUtils.copyProperties(messageEntity, newEntity);
        newEntity.setUserId(messageEntity.getTo());
        mongoTemplate.save(messageEntity);
    }

    @Override
    public List<MessageEntity> getByUser(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, MessageEntity.class);
    }
}
