package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.mapper.dao.SysMenu;
import io.gitee.welkinfast.admin.service.SysMenuService;
import io.gitee.welkinfast.common.response.WelkinResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 菜单管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(description = "菜单管理")
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("根据ID获取角色信息")
    @GetMapping("/menu/{id}")
    public WelkinResult getById(@PathVariable("id") String id) {
        SysMenu sysMenu = sysMenuService.getMenuById(id);
        MenuVo menuVo = dao2Vo(sysMenu);
        return WelkinResult.OK(menuVo);
    }

    @ApiOperation("保存角色信息")
    @PostMapping("/menu")
    public WelkinResult save(@Validated @RequestBody MenuVo menuVo) {
        SysMenu sysMenu = vo2Dao(menuVo);
        sysMenuService.saveMenu(sysMenu);
        return WelkinResult.OK("保存成功");
    }

    @ApiOperation("根据ID更新角色信息")
    @PutMapping("/menu")
    public WelkinResult updateById(@Validated @RequestBody MenuVo menuVo) {
        SysMenu sysMenu = vo2Dao(menuVo);
        sysMenuService.updateMenu(sysMenu);
        return WelkinResult.OK("更新成功");
    }

    @ApiOperation("根据ID删除角色信息")
    @DeleteMapping("/menu/{id}")
    public WelkinResult deleteById(@PathVariable("id") String id) {
        sysMenuService.deleteById(id);
        return WelkinResult.OK("删除成功");
    }

    private MenuVo dao2Vo(SysMenu sysMenu) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(sysMenu, menuVo);
        return menuVo;
    }

    private SysMenu vo2Dao(MenuVo menuVo) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menuVo, sysMenu);
        return sysMenu;
    }
}
