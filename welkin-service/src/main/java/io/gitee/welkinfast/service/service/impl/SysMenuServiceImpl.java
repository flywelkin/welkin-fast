package io.gitee.welkinfast.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.service.mapper.SysMenuMapper;
import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import io.gitee.welkinfast.service.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  菜单管理服务
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据ID查询菜单信息
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu getMenuById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysMenu queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new CustomExcption(CustomErrorType.DATA_NULL);
        }
        return queryResult;
    }

    /**
     * 保存菜单信息
     *
     * @param sysMenu 被保存的菜单信息
     */
    @Override
    public void saveMenu(SysMenu sysMenu) {
        if (checkMenuName(sysMenu)) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL, "名称已存在");
        }
        this.save(sysMenu);
    }

    /**
     * 根据ID更新菜单信息
     *
     * @param sysMenu 需要被更新的菜单信息
     */
    @Override
    public void updateMenu(SysMenu sysMenu) {
        if (checkMenuName(sysMenu)) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL, "名称已存在");
        }
        boolean success = this.updateById(sysMenu);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_UPDATE_FAIL);
        }
    }

    /**
     * 根据ID删除菜单信息
     *
     * @param id 菜单ID
     */
    @Override
    public void deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParentId, id);
        Integer count = sysMenuMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL, "存在子菜单无法删除");
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL);
        }
    }

    /**
     * 获取所有菜单
     *
     * @return 查询到的菜单
     */
    @Override
    public List<SysMenu> getAllMenu() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.<SysMenu>lambdaQuery()
                .orderByAsc(SysMenu::getOrderNum);
        return sysMenuMapper.selectList(queryWrapper);
    }

    /**
     * 根据用户ID查询该用户的所有菜单
     *
     * @param userId 用户ID
     * @return 查询到的菜单集合
     */
    @Override
    public List<SysMenu> getMenuByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        return sysMenuMapper.getByUserId(userId);
    }

    /**
     * 查询当前用户的已分配的菜单，如果是超级管理员则查询所有菜单
     *
     * @param userId  用户ID
     * @param isAdmin 是否是超级管理员
     * @return 查询到的菜单集合
     */
    @Override
    public List<SysMenu> getByUserIdAndIsAdmin(String userId, Boolean isAdmin) {
        if (isAdmin) {
            return getAllMenu();
        }
        return getMenuByUserId(userId);
    }

    /**
     * 查询当前用户已分配的菜单并构建菜单树，如果是超级管理员则查询所有菜单
     *
     * @param userId  用户ID
     * @param isAdmin 是否是超级管理员
     * @return 查询到的菜单树
     */
    @Override
    public List<SysMenu> getMenuTree(String userId, Boolean isAdmin) {

        // 获取用户所有菜单
        List<SysMenu> menus = this.getByUserIdAndIsAdmin(userId, isAdmin);
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }

        // 获取所有菜单ID
        List<String> idList = menus.stream().map(SysMenu::getId)
                .collect(Collectors.toList());

        // 筛选父及菜单集合
        List<SysMenu> collectMenus = menus.stream()
                .filter(item -> !idList.contains(item.getParentId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collectMenus)) {
            return Collections.emptyList();
        }
        // 构建菜单树
        return collectMenus.stream()
                .map(item -> buildMenuTree(item, menus))
                .collect(Collectors.toList());
    }

    /**
     * 构建菜单树
     *
     * @param parentMenu 父级菜单
     * @param menus      所有菜单集合
     * @return 根据父及菜单构建好的菜单树
     */
    private SysMenu buildMenuTree(SysMenu parentMenu, List<SysMenu> menus) {
        SysMenu menuVo = new SysMenu();
        BeanUtils.copyProperties(parentMenu, menuVo);
        List<SysMenu> childList = menus.stream()
                .filter(item -> item.getParentId().equals(menuVo.getId()))
                .map(item -> buildMenuTree(item, menus))
                .collect(Collectors.toList());
        menuVo.setChildren(childList);
        return menuVo;
    }

    /**
     * 检角色名或角色标识是否存在
     *
     * @param sysMenu 被检测的角色信息
     * @return 存在返回 true, 不存在返回false
     */
    private boolean checkMenuName(SysMenu sysMenu) {
        if (sysMenu == null || StringUtils.isEmpty(sysMenu.getName())) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParentId, sysMenu.getParentId())
                .eq(SysMenu::getName, sysMenu.getName())
                .ne(StringUtils.isNotEmpty(sysMenu.getId()), SysMenu::getId, sysMenu.getId());
        Integer count = sysMenuMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }
}

