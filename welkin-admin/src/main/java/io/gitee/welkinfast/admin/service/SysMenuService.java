package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.mapper.dao.SysMenu;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 根据ID查询菜单信息
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    SysMenu getMenuById(String id);

    /**
     * 保存菜单信息
     *
     * @param sysMenu 需要被保存的菜单信息
     */
    void saveMenu(SysMenu sysMenu);

    /**
     * 根据ID更新菜单信息
     *
     * @param sysMenu 需要被更新的菜单信息
     */
    void updateMenu(SysMenu sysMenu);

    /**
     * 根据ID删除菜单信息
     *
     * @param id 菜单ID
     */
    void deleteById(String id);

    /**
     * 获取所有菜单
     *
     * @return 查询到的菜单
     */
    List<SysMenu> getAllMenu();

    /**
     * 根据用户ID查询该用户的所有菜单
     *
     * @param userId 用户ID
     * @return 查询到的菜单集合
     */
    List<SysMenu> getMenuByUserId(String userId);

    /**
     * 查询当前用户的已分配的菜单，如果是超级管理员则查询所有菜单
     *
     * @param userId  用户ID
     * @param isAdmin 是否是超级管理员
     * @return 查询到的菜单集合
     */
    List<SysMenu> getByUserIdAndIsAdmin(String userId, Boolean isAdmin);

    /**
     * 查询当前用户已分配的菜单并构建菜单树，如果是超级管理员则查询所有菜单
     *
     * @param userId  用户ID
     * @param isAdmin 是否是超级管理员
     * @return 查询到的菜单树
     */
    List<MenuVo> getMenuTree(String userId, Boolean isAdmin);
}

