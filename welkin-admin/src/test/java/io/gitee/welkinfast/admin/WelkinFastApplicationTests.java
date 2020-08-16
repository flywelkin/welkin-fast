package io.gitee.welkinfast.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class WelkinFastApplicationTests {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    void contextLoads() {
        System.out.println(bCryptPasswordEncoder.encode("admin"));
    }

}
