package io.gitee.welkinfast.im.session.cache.impl;

import io.gitee.welkinfast.im.session.cache.JSONUtil;
import io.gitee.welkinfast.im.session.cache.UserCacheSevice;
import io.gitee.welkinfast.im.session.cache.entity.SessionCache;
import io.gitee.welkinfast.im.session.cache.entity.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户缓存实现
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:51
 * @Version 1.0.0
 */
@Slf4j
@Service
public class UserCacheSeviceImpl implements UserCacheSevice {

    public static final String USER_CACHE_PREFIX = "im:user:";
    //缓存时间
    private static final long CASHE_LONG = 60;

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(UserCache userCache) {
        if (ObjectUtils.isEmpty(userCache)) {
            return;
        }
        String userId = userCache.getUserId();
        String s = JSONUtil.toJSONString(userCache);
        redisTemplate.opsForValue().set(USER_CACHE_PREFIX + userId, s, CASHE_LONG, TimeUnit.SECONDS);
    }

    @Override
    public UserCache get(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        String s = redisTemplate.opsForValue().get(USER_CACHE_PREFIX + userId);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return JSONUtil.parseObject(s, UserCache.class);
    }

    @Override
    public void addSession(String userId, SessionCache session) {
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                UserCache userCache = get(userId);
                if (null == userCache) {
                    userCache = new UserCache(userId);
                }
                userCache.addSession(session);
                save(userCache);
                return operations.exec();
            }
        });
    }

    @Override
    public void removeSession(String userId, String sessionId) {
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                UserCache userCache = get(userId);
                if (null == userCache) {
                    userCache = new UserCache(userId);
                }
                userCache.removeSession(sessionId);
                save(userCache);
                return operations.exec();
            }
        });
    }
}
