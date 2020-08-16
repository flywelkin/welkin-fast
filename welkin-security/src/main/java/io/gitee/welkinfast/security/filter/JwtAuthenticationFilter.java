package io.gitee.welkinfast.security.filter;

import io.gitee.welkinfast.security.entity.CustomPayload;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import io.gitee.welkinfast.security.jwt.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
 * @Description jwt 认证过滤器
 * @Author yuanjg
 * @CreateTime 2020/08/16 11:23
 * @Version 1.0.0
 */
@Slf4j
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    private static final String AUTHENTICATION_KEY = "Authorization";

    @Autowired
    private JwtTokenService JwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 如果已经通过认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
        //请求体的头中是否包含Authorization
        String header = request.getHeader(AUTHENTICATION_KEY);
        if (header != null && header.startsWith(AUTHENTICATION_PREFIX)) {
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 通过token，获取用户信息
     *
     * @param bearerToken
     * @return UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String bearerToken) {
        if (bearerToken != null) {
            //通过token解析出载荷信息
            String token = bearerToken.replace(AUTHENTICATION_PREFIX, "");
            CustomPayload<DefaultUserDetails> payload = JwtTokenService.getInfoFromToken(token, DefaultUserDetails.class);
            DefaultUserDetails user = payload.getUserInfo();
            //判断是否已经过期
            boolean expiration = JwtTokenService.isExpiration(token);
            //userInfo为null 或 token过期
            if (user == null && expiration) {
                return null;
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
        }
        return null;
    }
}
