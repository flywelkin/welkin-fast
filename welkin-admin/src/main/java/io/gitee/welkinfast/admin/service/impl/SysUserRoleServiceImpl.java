package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysUserRoleMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysUserRole;
import io.gitee.welkinfast.admin.service.SysUserRoleService;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.id.WelkinIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 20:56
 * @Version 1.0.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void updateUserRole(List<SysUserRole> sysUserRoles) {
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysUserRole sysUserRole = sysUserRoles.get(0);
        deletUserRoleByUserId(sysUserRole.getUserId());
        sysUserRoles.stream().forEach(item -> {
            item.setId(WelkinIdGenerator.getId());
        });
        sysUserRoleMapper.selectBatchIds(sysUserRoles);
    }

    @Override
    public void deletUserRoleByUserId(String userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysUserRole.COL_USER_ID, userId);
        sysUserRoleMapper.delete(queryWrapper);
    }
}
