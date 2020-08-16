package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysRoleMenuMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysRoleMenu;
import io.gitee.welkinfast.admin.service.SysRoleMenuService;
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
 * @CreateTime 2020/08/16 20:55
 * @Version 1.0.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void updateRoleMenu(List<SysRoleMenu> sysRoleMenus) {
        if (CollectionUtils.isEmpty(sysRoleMenus)) {
            throw new WelkinExcption(WelkinErrorType.PERMISSION_SIGNATURE_ERROR);
        }
        SysRoleMenu sysRoleMenu = sysRoleMenus.get(0);
        deleteMenuByRoleId(sysRoleMenu.getRoleId());
        sysRoleMenus.stream().forEach(item -> {
            item.setId(WelkinIdGenerator.getId());
        });
        this.saveBatch(sysRoleMenus);
    }

    private void deleteMenuByRoleId(String roleId) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysRoleMenu.COL_ROLE_ID, roleId);
        sysRoleMenuMapper.delete(queryWrapper);
    }
}
