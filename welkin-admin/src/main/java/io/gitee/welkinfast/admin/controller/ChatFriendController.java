package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.CustomUserUtils;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.gitee.welkinfast.service.mapper.dao.SysUser;
import io.gitee.welkinfast.service.service.ChatFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  好友管理
 * @Author yuanjg
 * @CreateTime 2021/03/18 17:34
 * @Version 1.0.0
 */
@Api(tags = "好友管理")
@RestController
public class ChatFriendController {

    @Autowired
    private ChatFriendService chatFriendService;

    @ApiOperation("获取当前登录用户信息,权限信息,角色信息")
    @GetMapping("/friends")
    public CustomResponse<List<SysUser>> getCurrentUserInfo() {
        CustomUserDetails currentUserInfo = CustomUserUtils.getCurrentUserInfo();
        List<SysUser> users = chatFriendService.getByUserId(currentUserInfo.getId());
        return CustomResponse.OK(users);
    }
}
