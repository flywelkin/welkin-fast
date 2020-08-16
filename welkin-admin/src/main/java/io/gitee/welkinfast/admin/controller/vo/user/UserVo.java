package io.gitee.welkinfast.admin.controller.vo.user;

import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "io-gitee-welkinfast-admin-controller-vo-UserVo")
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String loginName;


    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;


    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    private Byte status;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID")
    private String deptId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "权限列表")
    private List<MenuVo> permissions;

    @ApiModelProperty(value = "角色列表")
    private List<RoleVo> roles;

}

