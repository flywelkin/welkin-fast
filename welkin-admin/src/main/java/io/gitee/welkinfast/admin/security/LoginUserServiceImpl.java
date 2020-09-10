package io.gitee.welkinfast.admin.security;

import io.gitee.welkinfast.admin.mapper.dao.SysMenu;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.service.SysMenuService;
import io.gitee.welkinfast.admin.service.SysRoleService;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.security.LoginUserService;
import io.gitee.welkinfast.security.entity.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 13:15
 * @Version 1.0.0
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public CustomUserDetails getUserByUsername(String userName) {
        // 用户密码查询
        SysUser sysUser = sysUserService.getByUserName(userName);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("登录失败用户不存在");
        }

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(sysUser.getId());
        userDetails.setUsername(userName);
        userDetails.setPassword(sysUser.getPassword());

        // 角色信息
        List<SysRole> sysRoles = sysRoleService.getAllByUserId(sysUser.getId());
        if (!CollectionUtils.isEmpty(sysRoles)) {
            List<String> roleKeys = sysRoles.stream()
                    .map(SysRole::getRoleKey)
                    .collect(Collectors.toList());
            userDetails.setRoles(roleKeys);

            // 权限信息
            List<SysMenu> sysMenus = sysMenuService.getByUserIdAndIsAdmin(sysUser.getId(),userDetails.isAdmin());
            if (!CollectionUtils.isEmpty(sysMenus)) {
                List<String> permissionsDetailsList = sysMenus.stream()
                        .filter(item -> StringUtils.isNotEmpty(item.getPerms()))
                        .map(SysMenu::getPerms)
                        .collect(Collectors.toList());
                userDetails.setPermissions(permissionsDetailsList);
            }
        }
        return userDetails;
    }

}
