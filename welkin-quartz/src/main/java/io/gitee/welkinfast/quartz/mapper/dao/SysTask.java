package io.gitee.welkinfast.quartz.mapper.dao;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:34
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@TableName(value = "sys_task")
public class SysTask {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 任务名
     */
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 任务描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @TableField(value = "bean_class")
    private String beanClass;

    /**
     * 任务状态
     */
    @TableField(value = "job_status")
    private String jobStatus;

    /**
     * 任务分组
     */
    @TableField(value = "job_group")
    private String jobGroup;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "last_update_by", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

}