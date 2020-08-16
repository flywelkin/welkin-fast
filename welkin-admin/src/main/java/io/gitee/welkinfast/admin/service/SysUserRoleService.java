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

    void updateUserRole(List<SysUserRole> sysUserRoles);

    void deletUserRoleByUserId(String userId);
}
