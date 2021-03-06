package io.gitee.welkinfast.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/16 16:55
 * @Version 1.0.0
 */
@ApiModel(value = "SaveUserVo")
@Data
public class SaveUserVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String Id;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
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
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String mobile;


    @ApiModelProperty(value = "账号状态")
    private Integer status;

    /**
     * 机构ID
     */
    @NotNull(message = "机构ID不能为空")
    @ApiModelProperty(value = "机构ID")
    private String deptId;

    @ApiModelProperty(value = "角色集合ID")
    private List<String> roleIds;
}


