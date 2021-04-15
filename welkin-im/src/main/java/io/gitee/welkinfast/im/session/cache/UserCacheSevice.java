package io.gitee.welkinfast.im.session.cache;

import io.gitee.welkinfast.im.session.cache.entity.SessionCache;
import io.gitee.welkinfast.im.session.cache.entity.UserCache;

/**
 * 用户缓存接口
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:51
 * @Version 1.0.0
 */
public interface UserCacheSevice {

    /**
     * 保持用户缓存
     */
    void save(UserCache userCache);

    /**
     * 根据用户ID获取用户缓存
     *
     * @param userId
     * @return
     */
    UserCache get(String userId);

    /**
     * 增加 用户的  会话
     */
    void addSession(String userId, SessionCache session);


    /**
     * 删除 用户的  会话
     */
    void removeSession(String userId, String sessionId);
}
