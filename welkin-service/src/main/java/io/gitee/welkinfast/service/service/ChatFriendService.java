package io.gitee.welkinfast.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.service.mapper.dao.ChatFriend;
import io.gitee.welkinfast.service.mapper.dao.SysUser;

import java.util.List;

/**
 *  好友管理
 * @Author yuanjg
 * @CreateTime 2021/03/18 17:30
 * @Version 1.0.0
 */
public interface ChatFriendService extends IService<ChatFriend> {

    /**
     * 根据用户ID获取好友列表
     *
     * @param userId 用户ID
     * @return 好友列表
     */
    List<SysUser> getByUserId(String userId);
}
