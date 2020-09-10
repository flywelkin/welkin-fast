package io.gitee.welkinfast.admin.config;


import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description 全局异常处理
 * @Author yuanjg
 * @CreateTime 2020/8/15 19:57
 * @Version 1.0.0
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {


    /**
     * 未知异常捕获
     *
     * @param request HttpServletRequest
     * @param exception Exception
     * @return WelkinResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CustomResponse handlerException(HttpServletRequest request, Exception exception) {
        log.error(exception.getMessage(), exception);
        return CustomResponse.FAIL(CustomErrorType.UNKNOWN_ERROR.getErrorCode(), exception.getMessage());
    }

    /**
     * 自定义通用异常捕获
     *
     * @param request HttpServletRequest
     * @param exception Exception
     * @return WelkinResult
     */
    @ExceptionHandler(CustomExcption.class)
    @ResponseBody
    public CustomResponse handlerWelkinExcption(HttpServletRequest request, Exception exception) {
        log.warn(exception.getMessage());
        CustomExcption bussinessExcption = (CustomExcption) exception;
        return CustomResponse.FAIL(bussinessExcption.getErrorCode(), bussinessExcption.getErrorMsg());
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return WelkinResult
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public CustomResponse parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        if (log.isDebugEnabled()) {
            log.error(e.getMessage(), e);
        }
        return CustomResponse.FAIL(CustomErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), "参数体不能为空");
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return WelkinResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CustomResponse parameterExceptionHandler(MethodArgumentNotValidException e) {
        if (log.isDebugEnabled()) {
            log.error(e.getMessage(), e);
        }
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return CustomResponse.FAIL(CustomErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), fieldError.getDefaultMessage());
            }
        }
        return CustomResponse.FAIL(CustomErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), CustomErrorType.PARAMETER_VALIDATION_ERROR.getErrorMsg());
    }


}

