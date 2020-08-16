package io.gitee.welkinfast.security;


import io.gitee.welkinfast.security.entity.DefaultUserDetails;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 12:55
 * @Version 1.0.0
 */
public interface LoginUserService {
    public DefaultUserDetails getUserByUsername(String userName);
}
