package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.user.SaveUserVo;
import io.gitee.welkinfast.admin.controller.vo.user.UserVo;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.mapper.dao.SysUserRole;
import io.gitee.welkinfast.admin.service.SysUserRoleService;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.common.response.WelkinResult;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import io.gitee.welkinfast.security.util.WelkinUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 13:07
 * @Version 1.0.0
 */

@Api(description = "用户管理")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @ApiOperation("根据ID获取用户信息")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping("/user/{id}")
    public WelkinResult getById(@PathVariable("id") String id) {
        UserVo user = sysUserService.getUserVoById(id);
        return WelkinResult.OK(user);
    }

    @ApiOperation("保存用户")
    @PostMapping("/user")
    public WelkinResult save(@RequestBody SaveUserVo user) {
        sysUserService.saveUserVo(user);
        return WelkinResult.OK("保存成功");
    }

    @ApiOperation("根据ID更新用户信息")
    @PutMapping("/user")
    public WelkinResult updateById(@RequestBody UserVo user) {
        SysUser sysUser = vo2Dao(user);
        sysUserService.updateUser(sysUser);
        return WelkinResult.OK("更新成功");
    }

    @ApiOperation("根据ID删除用户信息")
    @DeleteMapping("/user/{id}")
    public WelkinResult deleteById(@PathVariable("id") String id) {
        sysUserService.deleteById(id);
        return WelkinResult.OK("删除成功");
    }

    @ApiOperation("更新用户角色信息")
    @PutMapping("/user/role")
    public WelkinResult updateUserRole(@RequestBody List<SysUserRole> sysUserRoles) {
        sysUserRoleService.updateUserRole(sysUserRoles);
        return WelkinResult.OK("更新成功");
    }

    @ApiOperation("获取当前登录用户信息,权限信息,角色信息")
    @GetMapping("/user/ifno")
    public WelkinResult getCurrentUserInfo() {
        DefaultUserDetails currentUserInfo = WelkinUserUtils.getCurrentUserInfo();
        UserVo user = sysUserService.getUserVoById(currentUserInfo.getId());
        return WelkinResult.OK(user);
    }

    private SysUser vo2Dao(UserVo user) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        return sysUser;
    }
}
