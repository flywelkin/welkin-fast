package io.gitee.welkinfast.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.service.mapper.SysRoleMapper;
import io.gitee.welkinfast.service.mapper.dao.SysRole;
import io.gitee.welkinfast.service.mapper.dao.SysRoleMenu;
import io.gitee.welkinfast.service.service.SysMenuService;
import io.gitee.welkinfast.service.service.SysRoleMenuService;
import io.gitee.welkinfast.service.service.SysRoleService;
import io.gitee.welkinfast.service.service.SysUserRoleService;
import io.gitee.welkinfast.service.service.modle.RoleModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 根据ID获取角色信息
     *
     * @param roleId 角色ID
     * @return 查询到的角色信息
     */
    @Override
    public RoleModel getRoleById(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysRole queryResult = this.getById(roleId);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new CustomExcption(CustomErrorType.DATA_NULL);
        }

        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(queryResult, roleModel);

        List<SysRoleMenu> sysMenus = sysRoleMenuService.getByRoleId(roleId);
        if (!CollectionUtils.isEmpty(sysMenus)) {
            List<String> menuIds = sysMenus.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
            roleModel.setMenuIds(menuIds);
        }

        return roleModel;
    }

    /**
     * 保存角色系信息
     *
     * @param roleModel 需要被保存的角色信息
     */
    @Override
    public void saveRole(RoleModel roleModel) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleModel, sysRole);

        if (checkRoleNameOrKey(sysRole)) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL, "角色名称或标识已存在");
        }

        String roleId = CustomIdGenerator.getId();
        sysRole.setId(roleId);

        boolean success = this.save(sysRole);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL);
        }

        List<String> menuIds = roleModel.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            sysRoleMenuService.addRoleMenu(roleId, menuIds);
        }
    }

    /**
     * 根据ID更新角色信息
     *
     * @param roleModel 需要被更新的角色信息
     */
    @Override
    public void updateRole(RoleModel roleModel) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleModel, sysRole);
        if (checkRoleNameOrKey(sysRole)) {
            throw new CustomExcption(CustomErrorType.DATA_UPDATE_FAIL, "角色名称或标识已存在");
        }

        boolean success = this.updateById(sysRole);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_UPDATE_FAIL);
        }

        sysRoleMenuService.deleteMenuByRoleId(sysRole.getId());

        List<String> menuIds = roleModel.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            sysRoleMenuService.addRoleMenu(sysRole.getId(), menuIds);
        }
    }

    /**
     * 根据ID删除角色信息
     *
     * @param roleId 角色ID
     */
    @Override
    public void deleteById(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }

        // 校验是否存在用户绑定该角色
        boolean exist = sysUserRoleService.checkUserRolesExist(roleId);
        if (exist) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL, "存在用户绑定无法删除");
        }

        // 删除角色绑定的权限
        sysRoleMenuService.deleteMenuByRoleId(roleId);

        // 删除角色信息
        boolean success = this.removeById(roleId);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL);
        }
    }

    /**
     * 根据用户ID，查询用户绑定的所有角色信息
     *
     * @param userId 用户ID
     * @return 查询到的角色信息集合
     */
    @Override
    public List<SysRole> getAllByUserId(String userId) {
        return sysRoleMapper.getByUserId(userId);
    }

    /**
     * 分页查询角色信息集合
     *
     * @param sysRole 分页查询参数
     * @return 查询到的分页结果
     */
    @Override
    public PageResult<SysRole> getUserList(SysRole sysRole, Integer current, Integer size) {
        Page<SysRole> iPage = new Page<>(current, size);
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery()
                .orderByAsc(SysRole::getCreateBy);
        Page<SysRole> sysUserPage = sysRoleMapper.selectPage(iPage, queryWrapper);
        PageResult<SysRole> result = new PageResult<>();
        result.setCurrent(sysUserPage.getCurrent());
        result.setSize(sysUserPage.getSize());
        result.setTotal(sysUserPage.getTotal());
        result.setRecords(sysUserPage.getRecords());
        return result;
    }

    @Override
    public List<SysRole> getByUserIdAndIsAdmin(String id, boolean admin) {
        if (admin) {
            return sysRoleMapper.selectList(null);
        } else {
            return this.getAllByUserId(id);
        }
    }


    /**
     * 检角色名或角色标识是否存在
     *
     * @param sysRole 被检测的角色信息
     * @return 存在返回 true, 不存在返回false
     */
    private boolean checkRoleNameOrKey(SysRole sysRole) {
        if (sysRole == null || StringUtils.isEmpty(sysRole.getRoleKey()) || StringUtils.isEmpty(sysRole.getName())) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery()
                .ne(StringUtils.isNotEmpty(sysRole.getId()), SysRole::getId, sysRole.getId())
                .and(wrapper -> {
                            wrapper.eq(SysRole::getName, sysRole.getName())
                                    .or()
                                    .eq(SysRole::getRoleKey, sysRole.getRoleKey());
                        }
                );
        Integer count = sysRoleMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }

}

