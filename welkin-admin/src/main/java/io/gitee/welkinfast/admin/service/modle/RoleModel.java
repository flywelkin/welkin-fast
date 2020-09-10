package io.gitee.welkinfast.admin.service.modle;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
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
