package io.gitee.welkinfast.im.session.cache.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * user entity cache to redis
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:49
 * @Version 1.0.0
 */
public class UserCache {

    /**
     * 用户session,考虑多端登录，所以用户session是一个集合
     */
    @JSONField
    private Map<String, SessionCache> map = new LinkedHashMap<>(10);

    private String userId;

    public UserCache(String userId) {
        this.userId = userId;
    }

    //为用户增加session
    public void addSession(SessionCache session) {
        map.put(session.getSessionId(), session);
    }

    //为用户移除session
    public void removeSession(String sessionId) {
        map.remove(sessionId);
    }


    public Map<String, SessionCache> getMap() {
        return map;
    }

    public void setMap(Map<String, SessionCache> map) {
        this.map = map;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
