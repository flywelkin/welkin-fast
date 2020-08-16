package io.gitee.welkinfast.admin.security;

import io.gitee.welkinfast.admin.mapper.dao.SysMenu;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.service.SysMenuService;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.security.LoginUserService;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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

    @Override
    public DefaultUserDetails getUserByUsername(String userName) {
        // 用户密码查询
        SysUser sysUser = sysUserService.getByLoginName(userName);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("登录失败用户不存在");
        }
        DefaultUserDetails userDetails = new DefaultUserDetails();
        userDetails.setId(sysUser.getId());
        userDetails.setUsername(userName);
        userDetails.setPassword(sysUser.getPassword());
        // 权限查询
        List<SysMenu> sysMenu = sysMenuService.getByUserId(sysUser.getId());
        if (!ObjectUtils.isEmpty(sysMenu)) {
            List<String> permissionsDetailsList = sysMenu.stream()
                    .filter(item -> StringUtils.isNotEmpty(item.getPerms()))
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toList());
            userDetails.setPermissions(permissionsDetailsList);
        }
        return userDetails;
    }

}
