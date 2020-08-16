package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 20:54
 * @Version 1.0.0
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void updateRoleMenu(List<SysRoleMenu> sysRoleMenus);
}
