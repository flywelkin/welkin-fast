package io.gitee.welkinfast.admin;

import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import io.gitee.welkinfast.service.service.impl.SysMenuServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class WelkinFastApplicationTests {


    @Autowired
    private SysMenuServiceImpl sysMenuService;
    @Test
    void contextLoads() {
        List<SysMenu> sysMenus = sysMenuService.getMenuTree("22",true);
        log.info("");
    }


}
