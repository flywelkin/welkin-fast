package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;
import io.gitee.welkinfast.admin.service.SysRoleMenuService;
import io.gitee.welkinfast.admin.service.SysRoleService;
import io.gitee.welkinfast.common.response.WelkinResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(description = "角色管理")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @ApiOperation("根据ID获取角色信息")
    @GetMapping("/role/{id}")
    public WelkinResult getById(@Validated @PathVariable("id") String id) {
        SysRole sysRole = sysRoleService.getRoleById(id);
        RoleVo roleVo = dao2Vo(sysRole);
        return WelkinResult.OK(roleVo);
    }

    @ApiOperation("保存角色信息")
    @PostMapping("/role")
    public WelkinResult save(@Validated @RequestBody RoleVo roleVo) {
        SysRole sysRole = vo2Dao(roleVo);
        sysRoleService.saveRole(sysRole);
        return WelkinResult.OK("保存成功");
    }

    @ApiOperation("根据ID更新角色信息")
    @DeleteMapping("/role")
    public WelkinResult updateById(@RequestBody RoleVo roleVo) {
        SysRole sysRole = vo2Dao(roleVo);
        sysRoleService.updateRole(sysRole);
        return WelkinResult.OK("更新成功");
    }

    @ApiOperation("根据ID删除角色信息")
    @PutMapping("/role/{id}")
    public WelkinResult deleteById(@PathVariable("id") String id) {
        sysRoleService.deleteById(id);
        return WelkinResult.OK("删除成功");
    }

    @ApiOperation("更新角色菜单信息")
    @PutMapping("/role/menu")
    public WelkinResult updateUserRole(@RequestBody List<SysRoleMenu> sysRoleMenus) {
        sysRoleMenuService.updateRoleMenu(sysRoleMenus);
        return WelkinResult.OK("更新成功");
    }

    private RoleVo dao2Vo(SysRole sysRole) {
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(sysRole, roleVo);
        return roleVo;
    }

    private SysRole vo2Dao(RoleVo roleVo) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleVo, sysRole);
        return sysRole;
    }
}
