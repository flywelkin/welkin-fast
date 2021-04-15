package io.gitee.welkinfast.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.service.mapper.SysUserRoleMapper;
import io.gitee.welkinfast.service.mapper.dao.SysUserRole;
import io.gitee.welkinfast.service.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/16 20:56
 * @Version 1.0.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 添加用户角色
     *
     * @param userId 用户ID
     * @param roles  角色ID集合
     */
    @Override
    public void addUserRole(String userId, List<String> roles) {
        if(!CollectionUtils.isEmpty(roles)){
            List<SysUserRole> sysUserRoles = roles.stream().map(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                return sysUserRole;
            }).collect(Collectors.toList());
            this.saveBatch(sysUserRoles);
        }
    }

    /**
     * 更新用户角色
     *
     * @param userId 用户ID
     * @param roles  角色ID集合
     */
    @Override
    public void updateUserRole(String userId, List<String> roles) {
        this.deletUserRoleByUserId(userId);
        if(!CollectionUtils.isEmpty(roles)){
            List<SysUserRole> sysUserRoles = roles.stream().map(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                return sysUserRole;
            }).collect(Collectors.toList());
            this.saveBatch(sysUserRoles);
        }
    }

    /**
     * 删除用户角色
     *
     * @param userId 用户ID
     */
    @Override
    public void deletUserRoleByUserId(String userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(queryWrapper);
    }

    /**
     * 根据角色ID检测该角色是否存在用户绑定
     *
     * @param roleId 用户ID
     * @return 存在返回 true 不存在返回 false
     */
    @Override
    public boolean checkUserRolesExist(String roleId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, roleId);
        Integer count = sysUserRoleMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }
}
