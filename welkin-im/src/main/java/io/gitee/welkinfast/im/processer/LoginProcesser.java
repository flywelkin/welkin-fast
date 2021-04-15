package io.gitee.welkinfast.im.processer;

import io.gitee.welkinfast.common.jwt.JwtTokenService;
import io.gitee.welkinfast.common.jwt.entity.CustomPayload;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.gitee.welkinfast.im.message.ResponceBuilder;
import io.gitee.welkinfast.im.message.ResponseEnmuType;
import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.session.LocalSession;
import io.gitee.welkinfast.im.session.SessionManager;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录程序
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 15:11
 * @Version 1.0.0
 */
@Slf4j
@Service
public class LoginProcesser {

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ResponceBuilder responceBuilder;
    @Autowired
    private JwtTokenService jwtTokenService;

    public boolean action(LocalSession session, ImEntity.Message proto) {
        ImEntity.LoginRequest loginRequest = proto.getLoginRequest();

        String token = loginRequest.getToken();
        if (StringUtils.isEmpty(token)) {
            ImEntity.Message response = responceBuilder
                    .loginError(ResponseEnmuType.NO_TOKEN, proto.getId(), "-1");
            session.writeAndClose(response);
            return false;
        }
        //判断是否已经过期
        try {
            boolean expiration = jwtTokenService.isExpiration(token);
            if ( expiration) {
                ImEntity.Message response = responceBuilder
                        .loginError(ResponseEnmuType.TOKEN_EXPIRED, proto.getId(), "-1");
                session.writeAndClose(response);
                return false;
            }
        } catch (MalformedJwtException e) {
            ImEntity.Message response = responceBuilder
                    .loginError(ResponseEnmuType.TOKEN_EXPIRED, proto.getId(), "-1");
            session.writeAndClose(response);
            return false;
        }
        String userId = checkToken(token);
        if (StringUtils.isEmpty(userId)) {
            ImEntity.Message response = responceBuilder
                    .loginError(ResponseEnmuType.AUTH_FAILED, proto.getId(), "-1");
            session.writeAndClose(response);
            return false;
        }
        // 绑定session
        session.bind(userId);
        sessionManager.add(session);
        ImEntity.Message response = responceBuilder
                .loginOk(ResponseEnmuType.SUCCESS, proto.getId(), session.getSessionId());
        session.writeAndFlush(response);
        return true;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    private String checkToken(String token) {
        try {
            CustomPayload<CustomUserDetails> payload = jwtTokenService.getInfoFromToken(token, CustomUserDetails.class);
            CustomUserDetails user = payload.getUserInfo();
            return user.getId();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

}
