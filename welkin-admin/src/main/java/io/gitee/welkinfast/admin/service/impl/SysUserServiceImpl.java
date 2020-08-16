package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.controller.vo.MenuVo;
import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.gitee.welkinfast.admin.controller.vo.user.SaveUserVo;
import io.gitee.welkinfast.admin.controller.vo.user.UserVo;
import io.gitee.welkinfast.admin.mapper.SysUserMapper;
import io.gitee.welkinfast.admin.mapper.SysUserRoleMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysMenu;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.mapper.dao.SysUserRole;
import io.gitee.welkinfast.admin.service.SysMenuService;
import io.gitee.welkinfast.admin.service.SysRoleService;
import io.gitee.welkinfast.admin.service.SysUserService;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.id.WelkinIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void updateUser(SysUser sysUser) {
        boolean success = this.updateById(sysUser);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteById(String id) {
        SysUser queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new WelkinExcption(WelkinErrorType.USER_NOT_EXIST);
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_DELETE_FAIL);
        }
    }

    @Override
    public UserVo getUserVoById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysUser sysUser = this.getById(id);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new WelkinExcption(WelkinErrorType.USER_NOT_EXIST);
        }
        UserVo userVo = dao2Vo(sysUser);
        List<SysRole> sysRoleList = sysRoleService.getByUserId(id);
        if (!ObjectUtils.isEmpty(sysRoleList)) {
            List<RoleVo> collect = sysRoleList.stream().map(item -> {
                RoleVo roleVo = new RoleVo();
                BeanUtils.copyProperties(item, roleVo);
                return roleVo;
            }).collect(Collectors.toList());
            userVo.setRoles(collect);
        }
        List<SysMenu> sysMenuList = sysMenuService.getByUserId(id);
        if (!ObjectUtils.isEmpty(sysMenuList)) {
            List<MenuVo> collect = sysMenuList.stream().map(item -> {
                MenuVo roleVo = new MenuVo();
                BeanUtils.copyProperties(item, roleVo);
                return roleVo;
            }).collect(Collectors.toList());
            userVo.setPermissions(collect);
        }
        return userVo;
    }

    @Override
    public void saveUserVo(SaveUserVo user) {
        SysUser queryResult = this.getByLoginName(user.getLoginName());
        if (!ObjectUtils.isEmpty(queryResult)) {
            throw new WelkinExcption(WelkinErrorType.USER_EXIST, "用户名已存在");
        }
        SysUser sysUser = saveUserVo2Dao(user);
        sysUser.setStatus(1);
        sysUser.setId(WelkinIdGenerator.getId());
        sysUser.setSalt(WelkinIdGenerator.getId());
        sysUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.save(sysUser);
    }

    @Override
    public SysUser getByLoginName(String loginName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysUser.COL_LOGIN_NAME, loginName);
        return sysUserMapper.selectOne(queryWrapper);
    }


    private UserVo dao2Vo(SysUser sysUser) {
        UserVo user = new UserVo();
        BeanUtils.copyProperties(sysUser, user);
        return user;
    }

    private SysUser saveUserVo2Dao(SaveUserVo user) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        return sysUser;
    }
}

