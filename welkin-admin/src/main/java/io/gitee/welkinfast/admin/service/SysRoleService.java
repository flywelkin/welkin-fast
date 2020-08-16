package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {


    SysRole getRoleById(String id);

    void saveRole(SysRole sysRole);

    void updateRole(SysRole sysRole);

    void deleteById(String id);

    List<SysRole> getByUserId(String id);


}

