package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.security.util.ServletUtils;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.response.WelkinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录超时的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:10
 * @Version 1.0.0
 */

@Slf4j
@Component
public class InvalidSessionHandler implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.warn("用户登录超时，访问[{}]失败", request.getRequestURI());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ServletUtils.render(request, response,
                WelkinResult.CREAT(WelkinErrorType.LOGIN_TIMEOUT.getErrorCode(), WelkinErrorType.LOGIN_TIMEOUT.getErrorMsg()));
    }
}
