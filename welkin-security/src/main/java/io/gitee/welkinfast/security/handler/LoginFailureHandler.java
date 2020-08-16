package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.security.util.ServletUtils;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.response.WelkinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录失败的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:06
 * @Version 1.0.0
 */

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        WelkinResult result;
        String username = (String) request.getAttribute("username");
        if (e instanceof AccountExpiredException) {
            log.warn("[登录失败:{}] - 账号过期", username);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "账号过期");
        } else if (e instanceof BadCredentialsException) {
            log.warn("[登录失败:{}] - 密码错误", username);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "用户名或密码不正确");
        } else if (e instanceof CredentialsExpiredException) {
            log.warn("[登录失败:{}] - 密码过期", username);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "凭证过期");
        } else if (e instanceof DisabledException) {
            log.warn("[登录失败:{}] - 用户被禁用", username);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "用户被禁用");
        } else if (e instanceof LockedException) {
            log.warn("[登录失败:{}] - 用户被锁定", username);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "用户被锁定");
        } else if (e instanceof InternalAuthenticationServiceException) {
            log.warn("[登录失败:{}] - 内部错误", username, e);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "内部身份验证错误");
        } else {
            log.warn("[登录失败:{}] - 其他错误", username, e);
            result = WelkinResult.CREAT(WelkinErrorType.LOGIN_FAIL.getErrorCode(), "其他错误");
        }
        // TODO 登录失败 记录日志
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ServletUtils.render(request, response, result);
    }
}
