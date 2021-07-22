package io.gitee.welkinfast.service.mapper.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/04/26 14:25
 * @Version 1.0.0
 */
@Data
@TableName(value = "mini_gif_count")
public class MiniGifCount {

    @TableId(value = "open_id", type = IdType.INPUT)
    private String openId;

    @TableField(value = "count")
    private Integer count;
}