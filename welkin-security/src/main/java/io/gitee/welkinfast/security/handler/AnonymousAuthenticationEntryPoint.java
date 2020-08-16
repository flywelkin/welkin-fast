package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.security.util.ServletUtils;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.response.WelkinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 处理匿名用户访问无权限资源时的异常
 * @Author yuanjg
 * @CreateTime 2020/08/15 13:44
 * @Version 1.0.0
 */

@Slf4j
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.warn("用户需要登录，访问[{}]失败，AuthenticationException={}", request.getRequestURI(), e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ServletUtils.render(request, response,
                WelkinResult.CREAT(WelkinErrorType.UNAUTHORIZED.getErrorCode(),WelkinErrorType.UNAUTHORIZED.getErrorMsg()));
    }
}


