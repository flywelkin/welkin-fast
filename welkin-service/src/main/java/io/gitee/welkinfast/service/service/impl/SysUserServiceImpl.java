package io.gitee.welkinfast.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.service.mapper.SysUserMapper;
import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import io.gitee.welkinfast.service.mapper.dao.SysRole;
import io.gitee.welkinfast.service.mapper.dao.SysUser;
import io.gitee.welkinfast.service.service.SysMenuService;
import io.gitee.welkinfast.service.service.SysRoleService;
import io.gitee.welkinfast.service.service.SysUserRoleService;
import io.gitee.welkinfast.service.service.SysUserService;
import io.gitee.welkinfast.service.service.modle.UserModle;
import io.gitee.welkinfast.service.vo.user.UserInfo;
import io.gitee.welkinfast.service.vo.user.UserQuery;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public SysUser getByUserName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUserName, userName);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public UserModle getUserById(String id) {
        SysUser sysUser = this.getById(id);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomExcption(CustomErrorType.DATA_NULL, "用户信息不存在");
        }
        UserModle userModle = new UserModle();
        BeanUtils.copyProperties(sysUser, userModle);
        List<SysRole> sysRoles = sysRoleService.getAllByUserId(id);
        if (!CollectionUtils.isEmpty(sysRoles)) {
            List<String> roleIds = sysRoles.stream().map(item -> {
                SysRole roleVo = new SysRole();
                BeanUtils.copyProperties(item, roleVo);
                return item.getId();
            }).collect(Collectors.toList());
            userModle.setRoleIds(roleIds);
        }
        return userModle;
    }

    @Override
    public void saveUserVo(UserModle userModle) {
        SysUser queryResult = this.getByUserName(userModle.getUserName());
        if (!ObjectUtils.isEmpty(queryResult)) {
            throw new CustomExcption(CustomErrorType.USER_EXIST, "用户名已存在");
        }
        userModle.setId(CustomIdGenerator.getId());
        userModle.setStatus(1);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userModle, sysUser);
        this.save(sysUser);
        sysUserRoleService.addUserRole(userModle.getId(), userModle.getRoleIds());
    }

    @Override
    public void updateUser(UserModle userModle) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userModle, sysUser);
        this.updateById(sysUser);
        sysUserRoleService.updateUserRole(userModle.getId(), userModle.getRoleIds());
    }

    @Override
    public void deleteById(String id) {
        SysUser queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new CustomExcption(CustomErrorType.USER_NOT_EXIST);
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new CustomExcption(CustomErrorType.DATA_DELETE_FAIL);
        }
        sysUserRoleService.deletUserRoleByUserId(id);
    }

    @Override
    public PageResult<SysUser> getUserList(PageRequest<UserQuery> pageRequest) {
        Page<SysUser> iPage = new Page(pageRequest.getCurrent(), pageRequest.getSize());
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery();        if (pageRequest.paramsIsNotEmpty()) {
            String nickName = pageRequest.getParams().getNickName();
            if (StringUtils.isNotEmpty(nickName)) {
                queryWrapper.eq(SysUser::getNickName, nickName);
            }
        }
        Page<SysUser> sysUserPage = sysUserMapper.selectPage(iPage, queryWrapper);
        PageResult<SysUser> result = new PageResult();
        result.setCurrent(sysUserPage.getCurrent());
        result.setSize(sysUserPage.getSize());
        result.setTotal(sysUserPage.getTotal());
        result.setRecords(sysUserPage.getRecords());
        return result;
    }

    @Override
    public UserInfo getUserInfoById(String id, Boolean isAdmin) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysUser sysUser = this.getById(id);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomExcption(CustomErrorType.USER_NOT_EXIST);
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(sysUser, userInfo);

        List<SysRole> sysRoleList = sysRoleService.getAllByUserId(id);

        userInfo.setRoles(sysRoleList);

        List<SysMenu> sysMenuList = sysMenuService.getByUserIdAndIsAdmin(id, isAdmin);

        userInfo.setPermissions(sysMenuList);

        return userInfo;
    }

}

