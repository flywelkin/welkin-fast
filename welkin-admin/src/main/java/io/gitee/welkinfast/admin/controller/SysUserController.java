package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.UserVo;
import io.gitee.welkinfast.admin.controller.vo.user.SaveUserVo;
import io.gitee.welkinfast.admin.controller.vo.user.UserQuery;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.admin.service.modle.UserModle;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户管理控制器
 * @Author yuanjg
 * @CreateTime 2020/08/13 13:07
 * @Version 1.0.0
 */

@Api(tags  = "用户管理")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping("/user/{id}")
    public CustomResponse<UserVo> getById(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        UserModle userModle = sysUserService.getUserById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModle,userVo);
        return CustomResponse.OK(userVo);
    }

    @ApiOperation("保存用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/user")
    public CustomResponse<String> save(@RequestBody SaveUserVo saveUserVo) {
        UserModle userModle = new UserModle();
        BeanUtils.copyProperties(saveUserVo,userModle);
        sysUserService.saveUserVo(userModle);
        return CustomResponse.OK("保存成功");
    }

    @ApiOperation("根据ID更新用户信息")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PutMapping("/user")
    public CustomResponse<String> updateById(@RequestBody SaveUserVo saveUserVo) {
        UserModle userModle = new UserModle();
        BeanUtils.copyProperties(saveUserVo,userModle);
        sysUserService.updateUser(userModle);
        return CustomResponse.OK("更新成功");
    }

    @ApiOperation("根据ID删除用户信息")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/user/{id}")
    public CustomResponse<String> deleteById(@PathVariable("id") String id) {
        sysUserService.deleteById(id);
        return CustomResponse.OK("删除成功");
    }

    @ApiOperation("分页获取用户信息列表")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/user/list")
    public CustomResponse<PageResult<SysUser>> getUserList(@RequestBody PageRequest<UserQuery> pageRequest) {
        PageResult<SysUser> result = sysUserService.getUserList(pageRequest);
        return CustomResponse.OK(result);
    }

}
