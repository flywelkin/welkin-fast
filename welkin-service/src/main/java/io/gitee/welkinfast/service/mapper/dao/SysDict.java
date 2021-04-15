package io.gitee.welkinfast.service.mapper.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */

/**
 * 字典表
 */
@Data
@TableName(value = "sys_dict")
public class SysDict implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 数据值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 标签名
     */
    @TableField(value = "label")
    private String label;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 排序（升序）
     */
    @TableField(value = "sort")
    private Long sort;

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

    /**
     * 备注信息
     */
    @TableField(value = "remarks")
    private String remarks;


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_VALUE = "value";

    public static final String COL_LABEL = "label";

    public static final String COL_TYPE = "type";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_SORT = "sort";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_LAST_UPDATE_BY = "last_update_by";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_DEL_FLAG = "del_flag";
}
