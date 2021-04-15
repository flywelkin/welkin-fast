package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.vo.MenuVo;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.CustomUserUtils;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import io.gitee.welkinfast.service.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  菜单管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(tags = "菜单管理")
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("根据ID获取菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("/menu/{id}")
    public CustomResponse<SysMenu> getById(@PathVariable("id") String id) {
        SysMenu sysMenu = sysMenuService.getMenuById(id);
        return CustomResponse.OK(sysMenu);
    }

    @ApiOperation("保存菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @PostMapping("/menu")
    public CustomResponse<String> save(@Validated @RequestBody SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return CustomResponse.OK("保存成功");
    }

    @ApiOperation("根据ID更新菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PutMapping("/menu")
    public CustomResponse<String> updateById(@Validated @RequestBody SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return CustomResponse.OK("更新成功");
    }

    @ApiOperation("根据ID删除菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/menu/{id}")
    public CustomResponse<String> deleteById(@PathVariable("id") String id) {
        sysMenuService.deleteById(id);
        return CustomResponse.OK("删除成功");
    }


    @ApiOperation("查询当前用户菜单树")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("/menu/tree")
    public CustomResponse<List<MenuVo>> getMenuTree() {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        List<SysMenu> result = sysMenuService.getMenuTree(currentUserInfo.getId(), currentUserInfo.isAdmin());
        List<MenuVo> collect = result.stream().map(item -> {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(item, menuVo);
            return menuVo;
        }).collect(Collectors.toList());
        return CustomResponse.OK(collect);
    }

}
