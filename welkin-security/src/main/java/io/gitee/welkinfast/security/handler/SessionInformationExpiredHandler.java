package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.response.WelkinResult;
import io.gitee.welkinfast.security.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 同一账号同时登录的用户数受限的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:12
 * @Version 1.0.0
 */

@Slf4j
@Component
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        HttpServletRequest request = sessionInformationExpiredEvent.getRequest();
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ServletUtils.render(request, response,
                WelkinResult.CREAT(WelkinErrorType.BEYOND_MAX_LOGIN_LIMIT.getErrorCode(),WelkinErrorType.BEYOND_MAX_LOGIN_LIMIT.getErrorMsg()));
    }
}