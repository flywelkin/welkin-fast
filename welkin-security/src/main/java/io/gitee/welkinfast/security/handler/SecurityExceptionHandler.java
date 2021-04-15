package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.response.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 *  注解的异常处理： @PreAuthorize注解中hasAuthorize判断权限,
 * 会抛出AccessDeniedException异常.不会被accessDeniedHandler捕获，所以使用
 * 全局异常捕获处理
 * @Author yuanjg
 * @CreateTime 2020/08/20 16:35
 * @Version 1.0.0
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(SecurityExceptionHandler.class);

    /**
     * 访问权限被拒绝的异常处理程序
     *
     * @param request   HttpServletRequest
     * @param exception Exception
     * @return 错误码及错误信息
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public CustomResponse accessDeniedExceptionHandler(HttpServletRequest request, Exception exception) {
        logger.warn("用户未经授权，访问[{}]失败,{}", request.getRequestURI(), exception.getMessage());
        return CustomResponse.FAIL(CustomErrorType.UNAUTHORIZED.getErrorCode(), CustomErrorType.UNAUTHORIZED.getErrorMsg());
    }
}
