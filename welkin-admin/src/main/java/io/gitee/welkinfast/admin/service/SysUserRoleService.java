package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.mapper.dao.SysUserRole;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 20:56
 * @Version 1.0.0
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 添加用户角色
     *
     * @param userId 用户ID
     * @param roles  角色ID集合
     */
    void addUserRole(String userId, List<String> roles);

    /**
     * 更新用户角色
     *
     * @param userId 用户ID
     * @param roles  角色ID集合
     */
    void updateUserRole(String userId, List<String> roles);

    /**
     * 删除用户角色
     *
     * @param userId 用户ID
     */
    void deletUserRoleByUserId(String userId);

    /**
     * 根据角色ID检测该角色是否存在用户绑定
     *
     * @param roleId 用户ID
     * @return 存在返回 true 不存在返回 false
     */
    boolean checkUserRolesExist(String roleId);

}
