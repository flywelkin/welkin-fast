package io.gitee.welkinfast.security.util;

import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 16:13
 * @Version 1.0.0
 */
public class WelkinUserUtils {

    public static DefaultUserDetails getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
        return defaultUserDetails;
    }
}
