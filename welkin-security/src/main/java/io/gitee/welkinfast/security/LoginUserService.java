package io.gitee.welkinfast.security;


import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;

/**
 *  获取用户信息接口定义
 * @Author yuanjg
 * @CreateTime 2020/08/16 12:55
 * @Version 1.0.0
 */
public interface LoginUserService {
    CustomUserDetails getUserByUsername(String userName);
}
