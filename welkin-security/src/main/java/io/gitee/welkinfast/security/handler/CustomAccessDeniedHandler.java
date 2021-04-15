package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  注解的异常处理： @PreAuthorize注解中hasAuthorize,抛出AccessDeniedException异常，
 * 不会被accessDeniedHandler捕获;hasRole,如果出现异常,会调用设置的accessDeniedHandler方法
 *
 * @Author yuanjg
 * @CreateTime 2020/08/20 12:21
 * @Version 1.0.0
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    /**
     * Handles an access denied failure.
     *
     * @param request  that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param e        that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        logger.warn("用户未经授权，访问[{}]失败 {}", request.getRequestURI(), e.getMessage());
        CustomResponse customResponse = CustomResponse.FAIL(CustomErrorType.UNAUTHORIZED.getErrorCode(), CustomErrorType.UNAUTHORIZED.getErrorMsg());
        ServletUtils.render(response, customResponse);
    }
}
