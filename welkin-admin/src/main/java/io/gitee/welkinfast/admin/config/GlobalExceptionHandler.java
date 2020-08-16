package io.gitee.welkinfast.admin.config;


import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.response.WelkinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description 全局异常处理
 * @Author yuanjg
 * @CreateTime 2020/8/15 19:57
 * @Version 1.0.0
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    /**
     * 未知异常捕获
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WelkinResult handlerException(HttpServletRequest request, Exception exception) {
        log.error(exception.getMessage(), exception);
        return WelkinResult.CREAT(WelkinErrorType.UNKNOWN_ERROR.getErrorCode(), exception.getMessage());
    }

    /**
     * 通用异常捕获
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(WelkinExcption.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WelkinResult handlerWelkinExcption(HttpServletRequest request, Exception exception) {
        if (log.isDebugEnabled()) {
            log.warn(exception.getMessage(), exception);
        }
        WelkinExcption bussinessExcption = (WelkinExcption) exception;
        return WelkinResult.CREAT(bussinessExcption.getErrorCode(), bussinessExcption.getErrorMsg());
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public WelkinResult parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        if (log.isDebugEnabled()) {
            log.error(e.getMessage(), e);
        }
        return WelkinResult.CREAT(WelkinErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), "参数体不能为空");
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WelkinResult parameterExceptionHandler(MethodArgumentNotValidException e) {
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
                return WelkinResult.CREAT(WelkinErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), fieldError.getDefaultMessage());
            }
        }
        return WelkinResult.CREAT(WelkinErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode(), WelkinErrorType.PARAMETER_VALIDATION_ERROR.getErrorCode());
    }
}

