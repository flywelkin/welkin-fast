package io.gitee.welkinfast.service.vo.user;

import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import io.gitee.welkinfast.service.mapper.dao.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/22 12:44
 * @Version 1.0.0
 */
@Data
public class UserInfo {


    /**
     * 编号
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 机构ID
     */
    private String deptId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private List<SysMenu> permissions;
    private List<SysRole> roles;
}
