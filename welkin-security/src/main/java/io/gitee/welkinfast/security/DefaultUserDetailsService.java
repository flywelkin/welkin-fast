package io.gitee.welkinfast.security;

import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/15 13:48
 * @Version 1.0.0
 */

@Slf4j
@Component
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginUserService loginUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.warn("[登录失败:{}] - 用户不存在", username);
            throw new UsernameNotFoundException("登录失败用户不存在");
        }
        DefaultUserDetails userDetails = loginUserService.getUserByUsername(username);
        if (ObjectUtils.isEmpty(userDetails)) {
            log.warn("[登录失败:{}] - 用户不存在", username);
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
        String[] a = new String[1];
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
