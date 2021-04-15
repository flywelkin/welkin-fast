package io.gitee.welkinfast.im.session.cache.impl;

import io.gitee.welkinfast.im.session.cache.JSONUtil;
import io.gitee.welkinfast.im.session.cache.SessionCacheService;
import io.gitee.welkinfast.im.session.cache.entity.SessionCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * session 缓存实现
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:34
 * @Version 1.0.0
 */
@Slf4j
@Service
public class SessionCacheServerImpl implements SessionCacheService {

    private static final String SESSION_CACHE_PREFIX = "im:session:";
    //缓存时间
    private static final long CASHE_LONG = 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(SessionCache sessionCache) {
        if (ObjectUtils.isEmpty(sessionCache)) {
            return;
        }
        String key = SESSION_CACHE_PREFIX + sessionCache.getSessionId();
        String value = JSONUtil.toJSONString(sessionCache);
        redisTemplate.opsForValue().set(key, value, CASHE_LONG, TimeUnit.SECONDS);
    }

    @Override
    public SessionCache get(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        String s = redisTemplate.opsForValue().get(SESSION_CACHE_PREFIX + sessionId);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return JSONUtil.parseObject(s, SessionCache.class);
    }

    @Override
    public void remove(String sessionId) {
        redisTemplate.delete(SESSION_CACHE_PREFIX + sessionId);
    }
}
