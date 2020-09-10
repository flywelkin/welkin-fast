package io.gitee.welkinfast.admin.config;

import io.gitee.welkinfast.common.response.CustomResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description 自定义全局返回格式
 * @Author yuanjg
 * @CreateTime 2020/09/08 22:02
 * @Version 1.0.0
 */

//@ControllerAdvice
public class SimpleResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return !methodParameter.getMethod().getReturnType().isAssignableFrom(CustomResponse.class) &&
                (methodParameter.hasMethodAnnotation(ResponseBody.class) ||
                        AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), RestController.class)
                );

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return CustomResponse.OK(body);
    }
}
