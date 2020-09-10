package io.gitee.welkinfast.admin.controller.vo.user;

import lombok.Data;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/20 16:13
 * @Version 1.0.0
 */
@Data
public class UserQuery {

    private String id;
    private String userName;
    private String nickName;
    private String email;
    private String mobile;
    private String deptId;
    private String createBy;

}
