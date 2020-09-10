package io.gitee.welkinfast.admin.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "用户信息")
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    private Integer status;
    @ApiModelProperty(value = "机构ID")
    private String deptId;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private String lastUpdateBy;
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;
    @ApiModelProperty(value = "角色ID列表")
    private List<String> roleIds;

}

