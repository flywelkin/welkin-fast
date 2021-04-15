package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  登录失败的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:06
 * @Version 1.0.0
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.warn("[登录失败],{}", e.getMessage());
        CustomResponse<String> customResponse = CustomResponse.FAIL(CustomErrorType.LOGIN_FAIL.getErrorCode(), "用户名或密码不正确");
        ServletUtils.render(response, customResponse);
    }
}
