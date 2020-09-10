package io.gitee.welkinfast.admin.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "菜/按钮信息")
@Data
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    private String parentId;
    @ApiModelProperty(value = "菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)")
    private String url;
    @ApiModelProperty(value = "授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)")
    private String perms;
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
    private Integer type;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @ApiModelProperty(value = "排序")
    private Integer orderNum;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private String lastUpdateBy;
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;

    private List<MenuVo> children;
}
