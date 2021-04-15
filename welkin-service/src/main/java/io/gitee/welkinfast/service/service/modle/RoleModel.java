package io.gitee.welkinfast.service.service.modle;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/30 14:28
 * @Version 1.0.0
 */
@Data
public class RoleModel {

    private String id;
    private String name;
    private String roleKey;
    private String remark;
    private String createBy;
    private Date createTime;
    private String lastUpdateBy;
    private Date lastUpdateTime;
    private List<String> menuIds;
}
