package io.gitee.welkinfast.im.session.cache;

import io.gitee.welkinfast.im.session.cache.entity.SessionCache;

/**
 * session 缓存接口
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:34
 * @Version 1.0.0
 */
public interface SessionCacheService {

    /**
     * 保存缓存
     *
     * @param sessionCache
     */
    void save(SessionCache sessionCache);

    /**
     * 根据sessionId获取session缓存
     *
     * @param session
     * @return
     */
    SessionCache get(String session);

    /**
     * 根据sessionId移除session缓存
     *
     * @param sessionId
     */
    void remove(String sessionId);
}
