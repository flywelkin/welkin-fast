package io.gitee.welkinfast.common.jwt.entity;

import lombok.Data;

import java.util.Date;

/**
 *  自定义存储用户信息负载
 * @Author yuanjg
 * @CreateTime 2020/08/16 14:43
 * @Version 1.0.0
 */
@Data
public class CustomPayload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
