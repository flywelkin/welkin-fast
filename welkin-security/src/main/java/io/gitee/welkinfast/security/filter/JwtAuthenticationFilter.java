package io.gitee.welkinfast.security.filter;

import io.gitee.welkinfast.common.jwt.entity.CustomPayload;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.gitee.welkinfast.security.handler.CustomAuthenticationEntryPoint;
import io.gitee.welkinfast.common.jwt.JwtTokenService;
import io.gitee.welkinfast.security.properties.CustomSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  jwt 认证过滤器
 * @Author yuanjg
 * @CreateTime 2020/08/16 11:23
 * @Version 1.0.0
 */
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private CustomSecurityProperties customSecurityProperties;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //请求体的头中是否包含Authorization
        String header = request.getHeader(customSecurityProperties.getAuthKey());
        if (header != null) {
            //获取权限失败，会抛出异常
            try {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                logger.warn("无效的token认证,{}", e.getMessage());
                customAuthenticationEntryPoint.commence(request, response, e);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 通过token，获取用户信息
     *
     * @param bearerToken 令牌
     * @return UsernamePasswordAuthenticationToken 用户认证信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String bearerToken) {
        if (StringUtils.isEmpty(bearerToken)) {
            throw new BadCredentialsException("token is null");
        }
        //通过token解析出载荷信息
        try {
            CustomPayload<CustomUserDetails> payload = jwtTokenService.getInfoFromToken(bearerToken, CustomUserDetails.class);
            CustomUserDetails user = payload.getUserInfo();
            //判断是否已经过期
            boolean expiration = jwtTokenService.isExpiration(bearerToken);
            if (user == null && expiration) {
                throw new BadCredentialsException("token is expiration");
            }
            List<String> permissions = user.getPermissions();
            List<GrantedAuthority> grantedAuthoritys = permissions.stream().map(item -> {
                return new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return item;
                    }
                };
            }).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(user, null, grantedAuthoritys);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
