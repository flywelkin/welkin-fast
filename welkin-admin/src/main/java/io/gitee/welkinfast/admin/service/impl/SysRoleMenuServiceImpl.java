package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysRoleMenuMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;
import io.gitee.welkinfast.admin.service.SysRoleMenuService;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 20:55
 * @Version 1.0.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 为角色添加权限信息
     *
     * @param roleId 角色ID
     * @param menus  权限ID集合
     */
    @Override
    public void addRoleMenu(String roleId, List<String> menus) {
        if (StringUtils.isEmpty(roleId) || CollectionUtils.isEmpty(menus)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        List<SysRoleMenu> sysRoleMenus = menus.stream().map(item -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(item);
            return sysRoleMenu;
        }).collect(Collectors.toList());
        this.saveBatch(sysRoleMenus);
    }

    /**
     * 为角色更新权限信息
     *
     * @param roleId 角色ID
     * @param menus  权限ID集合
     */
    @Override
    public void updateRoleMenu(String roleId, List<String> menus) {
        if (StringUtils.isEmpty(roleId) || CollectionUtils.isEmpty(menus)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        deleteMenuByRoleId(roleId);
        addRoleMenu(roleId, menus);
    }

    /**
     * 根据角色ID删除权限
     *
     * @param roleId 角色ID
     */
    @Override
    public void deleteMenuByRoleId(String roleId) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysRoleMenu.COL_ROLE_ID, roleId);
        sysRoleMenuMapper.delete(queryWrapper);
    }

    @Override
    public List<SysRoleMenu> getByRoleId(String roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId);
        return sysRoleMenuMapper.selectList(queryWrapper);
    }
}
