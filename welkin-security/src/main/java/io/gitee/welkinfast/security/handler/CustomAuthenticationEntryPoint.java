package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *  处理匿名用户访问无权限资源时的异常
 * 401 需要登录或者说没有通过认证
 * 403 没有权限，服务器受到请求但拒绝提供服务
 * @Author yuanjg
 * @CreateTime 2020/08/15 13:44
 * @Version 1.0.0
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        logger.warn("用户需要登录，访问[{}]失败 {}", request.getRequestURI(), e.getMessage());
        CustomResponse customResponse = CustomResponse.FAIL(CustomErrorType.FORBIDDEN.getErrorCode(), CustomErrorType.FORBIDDEN.getErrorMsg());
        ServletUtils.render(response, customResponse);
    }
}


