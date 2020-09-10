package io.gitee.welkinfast.admin.controller.vo.user;

import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.gitee.welkinfast.admin.controller.vo.UserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/22 12:44
 * @Version 1.0.0
 */
@ApiModel(value = "用户详细信息")
@Data
public class UserInfo extends UserVo{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限列表")
    private List<MenuVo> permissions;
    @ApiModelProperty(value = "角色列表")
    private List<RoleVo> roles;
}
