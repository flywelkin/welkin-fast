package io.gitee.welkinfast.security;

import io.gitee.welkinfast.security.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description 获取当前用户信息工具类
 * @Author yuanjg
 * @CreateTime 2020/08/16 16:13
 * @Version 1.0.0
 */
public class CustomUserUtils {

    public static CustomUserDetails getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

}
