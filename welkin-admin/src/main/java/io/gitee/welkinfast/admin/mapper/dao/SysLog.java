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
 * 系统操作日志
 */
@Data
@TableName(value = "sys_log")
public class SysLog implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户操作
     */
    @TableField(value = "operation")
    private String operation;

    /**
     * 请求方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * 执行时长(毫秒)
     */
    @TableField(value = "time")
    private Long time;

    /**
     * IP地址
     */
    @TableField(value = "ip")
    private String ip;

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

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_OPERATION = "operation";

    public static final String COL_METHOD = "method";

    public static final String COL_PARAMS = "params";

    public static final String COL_TIME = "time";

    public static final String COL_IP = "ip";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_LAST_UPDATE_BY = "last_update_by";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}