package io.gitee.welkinfast.security.config;


import io.gitee.welkinfast.security.DefaultUserDetailsService;
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

/**
 * 401 需要登录或者说没有通过认证
 * 403 没有权限，服务器受到请求但拒绝提供服务
 *
 * @Description
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:15
 * @Version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsService userDetailsService;
    //登出成功的处理
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    //登录成功的处理
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    //登出成功的处理
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    //未登录的处理
    @Autowired
    private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置认证方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * http相关的配置，包括登入登出、异常处理、会话管理等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                // 放行接口
                .antMatchers("/login", "/", "/*.html", "/**/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/csrf").permitAll()
                .anyRequest().authenticated()
                // 异常处理(匿名访问异常，无权限异常)
                .and().exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
                //.accessDeniedHandler(accessDeniedHandler)
                // 当用户无权访问资源时发送 401 响应
                //.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // 当用户访问资源因权限不足时发送 403 响应
                //.accessDeniedHandler(securityProblemSupport)
                // 登入，允许所有用户
                .and().formLogin().permitAll()//
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // 登出
                .and().logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // session 生成策略用无状态策略
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
