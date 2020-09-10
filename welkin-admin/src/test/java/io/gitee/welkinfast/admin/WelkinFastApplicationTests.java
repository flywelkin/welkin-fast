package io.gitee.welkinfast.admin;

import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.service.impl.SysMenuServiceImpl;
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
        List<MenuVo> sysMenus = sysMenuService.getMenuTree("22",true);
        log.info("");
    }


}
