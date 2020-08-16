package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysRoleMapper;
import io.gitee.welkinfast.admin.mapper.SysRoleMenuMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;
import io.gitee.welkinfast.admin.service.SysRoleService;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.id.WelkinIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRole getRoleById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysRole queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new WelkinExcption(WelkinErrorType.DATA_NULL);
        }
        return queryResult;
    }

    @Override
    public void saveRole(SysRole sysRole) {
        sysRole.setId(WelkinIdGenerator.getId());
        this.save(sysRole);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        boolean success = this.updateById(sysRole);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        boolean success = this.removeById(id);
        if (!success) {
            throw new WelkinExcption(WelkinErrorType.DATA_DELETE_FAIL);
        }
    }

    @Override
    public List<SysRole> getByUserId(String id) {
        return sysRoleMapper.getByUserId(id);
    }




}

