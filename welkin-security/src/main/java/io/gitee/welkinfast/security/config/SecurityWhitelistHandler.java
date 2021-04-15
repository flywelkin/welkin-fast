package io.gitee.welkinfast.security.config;

import io.gitee.welkinfast.security.properties.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  认证白名单处理器
 * @Author yuanjg
 * @CreateTime 2020/08/20 09:09
 * @Version 1.0.0
 */
@Component
public class SecurityWhitelistHandler {

    @Autowired
    private CustomSecurityProperties customSecurityProperties;

    public HttpSecurity handle(HttpSecurity http) throws Exception {
        List<String> anonymous = customSecurityProperties.getAnonymous();
        String[] anonymousArray = new String[anonymous.size()];
        anonymous.toArray(anonymousArray);
        return http
                .authorizeRequests()
                .antMatchers("/login", "/", "/*.html", "/**/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers(anonymousArray).permitAll()
                .anyRequest().authenticated()
                .and();
    }

}
