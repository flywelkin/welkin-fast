package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.admin.controller.vo.user.UserInfo;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.entity.CustomUserDetails;
import io.gitee.welkinfast.security.CustomUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 已登录用户信息管理
 * @Author yuanjg
 * @CreateTime 2020/08/19 17:28
 * @Version 1.0.0
 */
@Api(tags  = "登录用户信息管理")
@RestController
public class AuthInfoController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("获取当前登录用户信息,权限信息,角色信息")
    @GetMapping("/auth/user/info")
    public CustomResponse<UserInfo> getCurrentUserInfo() {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        UserInfo user = sysUserService.getUserInfoById(currentUserInfo.getId(),currentUserInfo.isAdmin());
        return CustomResponse.OK(user);
    }
}
