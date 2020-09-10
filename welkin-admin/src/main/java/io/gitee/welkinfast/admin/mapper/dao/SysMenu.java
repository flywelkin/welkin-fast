package io.gitee.welkinfast.admin.mapper.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */

/**
 * 菜单管理
 */
@Data
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)
     */
    @TableField(value = "url")
    private String url;

    /**
     * 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "last_update_by", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    /**
     * 是否删除  1：已删除  0：正常
     */
    @TableLogic
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag;


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_URL = "url";

    public static final String COL_PERMS = "perms";

    public static final String COL_TYPE = "type";

    public static final String COL_ICON = "icon";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_LAST_UPDATE_BY = "last_update_by";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_DEL_FLAG = "del_flag";
}
