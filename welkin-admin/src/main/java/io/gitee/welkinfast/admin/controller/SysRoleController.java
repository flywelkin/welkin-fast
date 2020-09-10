package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.service.SysRoleService;
import io.gitee.welkinfast.admin.service.modle.RoleModel;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.CustomUserUtils;
import io.gitee.welkinfast.security.entity.CustomUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 角色管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/14 19:14
 * @Version 1.0.0
 */
@Api(tags = "角色管理")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("根据ID获取角色信息")
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping("/role/{id}")
    public CustomResponse<RoleVo> getById(@PathVariable("id") String id) {
        RoleModel roleModel = sysRoleService.getRoleById(id);
        RoleVo sysRole = new RoleVo();
        BeanUtils.copyProperties(roleModel, sysRole);
        return CustomResponse.OK(sysRole);
    }

    @ApiOperation("保存角色信息")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping("/role")
    public CustomResponse<String> save(@RequestBody RoleVo roleVo) {
        RoleModel roleMode = new RoleModel();
        BeanUtils.copyProperties(roleVo, roleMode);
        sysRoleService.saveRole(roleMode);
        return CustomResponse.OK("保存成功");
    }

    @ApiOperation("根据ID更新角色信息")
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PutMapping("/role")
    public CustomResponse<String> updateById(@RequestBody RoleVo roleVo) {
        RoleModel roleMode = new RoleModel();
        BeanUtils.copyProperties(roleVo, roleMode);
        sysRoleService.updateRole(roleMode);
        return CustomResponse.OK("更新成功");
    }

    @ApiOperation("根据ID删除角色信息")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/role/{id}")
    public CustomResponse<String> deleteById(@PathVariable("id") String id) {
        sysRoleService.deleteById(id);
        return CustomResponse.OK("删除成功");
    }

    @ApiOperation("分页获取角色信息列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @PostMapping("/role/list")
    public CustomResponse<PageResult<SysRole>> getUserList(@RequestBody PageRequest<RoleVo> pageRequest) {
        PageResult<SysRole> result = sysRoleService.getUserList(pageRequest);
        return CustomResponse.OK(result);
    }

    @ApiOperation("分页获取角色信息列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("current/role/list")
    public CustomResponse<List<SysRole>> getUserList() {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        List<SysRole> sysRoles = sysRoleService.getByUserIdAndIsAdmin(currentUserInfo.getId(),currentUserInfo.isAdmin());
        return CustomResponse.OK(sysRoles);
    }

}
