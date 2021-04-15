package io.gitee.welkinfast.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  启动类
 * @Author yuanjg
 * @CreateTime 2020/8/12 9:43
 * @Version 1.0.0
 */

@SpringBootApplication(scanBasePackages = {"io.gitee.welkinfast"})
public class WelkinFastApplication {

    public static void main(String[] args) {
        SpringApplication.run(WelkinFastApplication.class, args);
    }

}
