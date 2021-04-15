package io.gitee.welkinfast.security;

import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  用户信息认证服务
 * @Author yuanjg
 * @CreateTime 2020/08/15 13:48
 * @Version 1.0.0
 */
@Component
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsServiceImpl.class);

    @Autowired
    private LoginUserService loginUserService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        if (loginUserService == null) {
            throw new NoClassDefFoundError(String.format("%s not found instances", LoginUserService.class.getCanonicalName()));
        }
        if (StringUtils.isBlank(username)) {
            logger.warn("[登录失败:{}] - 用户不存在", username);
            throw new UsernameNotFoundException("登录失败用户不存在");
        }
        CustomUserDetails userDetails = loginUserService.getUserByUsername(username);
        if (ObjectUtils.isEmpty(userDetails)) {
            logger.warn("[登录失败:{}] - 用户不存在", username);
            throw new UsernameNotFoundException("登录失败用户不存在");
        }
        List<String> permissions = userDetails.getPermissions();
        List<GrantedAuthority> grantedAuthoritys = permissions.stream().map(item -> {
            return new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return item;
                }
            };
        }).collect(Collectors.toList());
        return new User(
                username,
                userDetails.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthoritys);
    }
}
