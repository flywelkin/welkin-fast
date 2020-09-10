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

    /**
     * 为角色添加权限信息
     *
     * @param roleId 角色ID
     * @param menus  权限ID集合
     */
    void addRoleMenu(String roleId, List<String> menus);

    /**
     * 为角色更新权限信息
     *
     * @param roleId 角色ID
     * @param menus  权限ID集合
     */
    void updateRoleMenu(String roleId, List<String> menus);


    /**
     * 根据角色ID删除权限
     *
     * @param roleId 角色ID
     */
    void deleteMenuByRoleId(String roleId);

    List<SysRoleMenu> getByRoleId(String roleId);
}
