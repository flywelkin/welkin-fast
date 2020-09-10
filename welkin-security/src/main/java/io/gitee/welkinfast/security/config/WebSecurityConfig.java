package io.gitee.welkinfast.security.config;


import io.gitee.welkinfast.security.DefaultUserDetailsServiceImpl;
import io.gitee.welkinfast.security.filter.JwtAuthenticationFilter;
import io.gitee.welkinfast.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @Description Spring Security 配置类
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:15
 * @Version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsServiceImpl userDetailsService;
    @Autowired
    public LoginSuccessHandler loginSuccessHandler;
    @Autowired
    public LoginFailureHandler loginFailureHandler;
    @Autowired
    public LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    public CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    public JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    public SecurityWhitelistHandler securityWhitelistHandler;

    /**
     * 配置认证方式等
     *
     * @param auth 身份验证管理器
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }


    /**
     * http相关的配置，包括登入登出、异常处理、会话管理等
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        securityWhitelistHandler.handle(http)
                // 异常处理(匿名访问异常，无权限异常)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                // 登入,允许所有用户
                .and().formLogin().permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // 退出 允许所有用户退出
                .and().logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                //jwt 认证过滤器
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // session 生成策略用无状态策略
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 密码编码器
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        // 暴露 header 中的其他属性给客户端应用程序
        config.setExposedHeaders(Arrays.asList(
                "Authorization", "X-Total-Count", "Link",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
