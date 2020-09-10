package io.gitee.welkinfast.admin.mapper.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 角色菜单
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenu implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

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


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_LAST_UPDATE_BY = "last_update_by";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}
