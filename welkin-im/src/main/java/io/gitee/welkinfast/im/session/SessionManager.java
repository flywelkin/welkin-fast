package io.gitee.welkinfast.im.session;

import io.gitee.welkinfast.im.distributed.LocalNodeClient;
import io.gitee.welkinfast.im.distributed.entity.NodeEntity;
import io.gitee.welkinfast.im.session.cache.JSONUtil;
import io.gitee.welkinfast.im.session.cache.SessionCacheService;
import io.gitee.welkinfast.im.session.cache.UserCacheSevice;
import io.gitee.welkinfast.im.session.cache.entity.SessionCache;
import io.gitee.welkinfast.im.session.cache.entity.UserCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session管理
 * 1、本地session缓存
 * 2、redis session 缓存
 * 2.1、 sessionId session 关系缓存
 * 2.2、 userId sessions 关系缓存
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:28
 * @Version 1.0.0
 */
@Slf4j
public class SessionManager {

    public static final AttributeKey<String> CHANNEL_TYPE = AttributeKey.valueOf("channel_type");
    public static final AttributeKey<String> CHANNEL_NAME = AttributeKey.valueOf("channel_name");

    /**
     * 本地会话缓存
     */
    private ConcurrentHashMap<String, ServerSession> SESSION_MAP = new ConcurrentHashMap();

    private SessionCacheService sessionCacheService;
    private UserCacheSevice userCacheSevice;

    public SessionManager(SessionCacheService sessionCacheService, UserCacheSevice userCacheSevice) {
        this.sessionCacheService = sessionCacheService;
        this.userCacheSevice = userCacheSevice;
    }

    /**
     * 添加session
     *
     * @param session
     */
    public void add(ServerSession session) {
        // 保存本地的session 到会话清单
        String sessionId = session.getSessionId();
        SESSION_MAP.put(sessionId, session);

        String userId = session.getUserId();

        //缓存session到redis
        NodeEntity node = LocalNodeClient.getInstance().get();
        SessionCache sessionCache = new SessionCache(userId, sessionId, node);
        sessionCacheService.save(sessionCache);

        //增加用户的session 信息到用户缓存
        userCacheSevice.addSession(userId, sessionCache);
    }


    public void update(ServerSession session) {
        // 保存本地的session 到会话清单
        String sessionId = session.getSessionId();
        String userId = session.getUserId();

        //缓存session到redis
        NodeEntity node = LocalNodeClient.getInstance().get();
        SessionCache sessionCache = new SessionCache(userId, sessionId, node);
        sessionCacheService.save(sessionCache);

        //增加用户的session 信息到用户缓存
        userCacheSevice.addSession(userId, sessionCache);
        log.debug("更新用户缓存: {}", JSONUtil.toJSONString(sessionCache));
    }

    /**
     * 删除session
     */
    public void removeSession(String sessionId) {
        if (!SESSION_MAP.containsKey(sessionId)) {
            return;
        }
        ServerSession session = SESSION_MAP.get(sessionId);
        String userId = session.getUserId();

        //根据 sessionId 删除用户的会话
        userCacheSevice.removeSession(userId, sessionId);

        //删除缓存session
        sessionCacheService.remove(sessionId);

        //从会话集合中，删除会话
        SESSION_MAP.remove(sessionId);
    }

    public List<ServerSession> get(String userId) {
        UserCache userCache = userCacheSevice.get(userId);
        if (ObjectUtils.isEmpty(userCache)) {
            log.debug("用户[{}]已下线,没有在缓存中找到记录 ", userId);
            return null;
        }
        Map<String, SessionCache> allSession = userCache.getMap();
        if (CollectionUtils.isEmpty(allSession)) {
            log.debug("用户[{}]已下线， 没有任何会话 ", userId);
            return null;
        }
        List<ServerSession> sessions = new LinkedList<>();

        allSession.values().stream().forEach(sessionCache -> {
            String sid = sessionCache.getSessionId();
            // 在本地会话session
            ServerSession session = SESSION_MAP.get(sid);
            // 创建远程的session
            if (session == null) {
                session = new RemoteSession(sessionCache);
            }
            sessions.add(session);
        });
        return sessions;
    }


    /**
     * 关闭本地会话
     *
     * @param ctx
     */
    public void closeSession(ChannelHandlerContext ctx) {
        LocalSession session = ctx.channel().attr(LocalSession.SESSION_ID_KEY).get();
        if (null == session || !session.isValid()) {
            log.error("session is null or isValid");
            return;
        }
        session.close();
        // 删除本地的会话
        this.removeSession(session.getSessionId());
        log.debug("清除本地会话：userId:{}, sessionId:{}",
                session.getUserId(), session.getSessionId());
    }

}
