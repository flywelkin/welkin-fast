package io.gitee.welkinfast.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description mybatis plus 配置类
 * @Author yuanjg
 * @CreateTime 2020/8/12 9:43
 * @Version 1.0.0
 */
@Configuration
@MapperScan("io.gitee.welkinfast.admin.**.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
