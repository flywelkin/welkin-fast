package io.gitee.welkinfast.admin.service;

import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/21 15:24
 * @Version 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void getUserById(){
        SysUser byId = sysUserService.getById("22");
        Assert.assertNotNull(byId);
    }

    @Test
    public void test(){
        redisTemplate.opsForValue().set("TEST","test");
        Object test = redisTemplate.opsForValue().get("TEST");
        Assert.assertNotEquals(test,"test");
    }
}
