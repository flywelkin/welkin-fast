package io.gitee.welkinfast.service.service.modle;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/23 13:55
 * @Version 1.0.0
 */
@Data
public class UserModle {

    private String id;
    private String userName;
    private String nickName;
    private String avatar;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Integer status;
    private String deptId;
    private String createBy;
    private Date createTime;
    private String lastUpdateBy;
    private Date lastUpdateTime;
    private List<String> roleIds;

}
