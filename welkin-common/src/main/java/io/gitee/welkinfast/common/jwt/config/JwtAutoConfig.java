package io.gitee.welkinfast.common.jwt.config;

import io.gitee.welkinfast.common.jwt.JwtTokenService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuanjg
 * @CreateTime 2021/03/29 14:08
 * @Version 1.0.0
 */

@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class JwtAutoConfig {

    @Bean
    public JwtTokenService jwtTokenService(JwtProperties jwtProperties) {
        return new JwtTokenService(jwtProperties);
    }
}
