package io.gitee.welkinfast.service.mapper.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/18 17:30
 * @Version 1.0.0
 */
@Data
@TableName(value = "chat_friend")
public class ChatFriend {
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 好友ID
     */
    @TableField(value = "friend_id")
    private String friendId;
}