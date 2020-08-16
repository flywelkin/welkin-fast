package io.gitee.welkinfast.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.admin.mapper.SysMenuMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysMenu;
import io.gitee.welkinfast.admin.service.SysMenuService;
import io.gitee.welkinfast.common.error.WelkinErrorType;
import io.gitee.welkinfast.common.error.WelkinExcption;
import io.gitee.welkinfast.common.id.WelkinIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysMenu getMenuById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WelkinExcption(WelkinErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysMenu queryResult = this.getById(id);
        if (ObjectUtils.isEmpty(queryResult)) {
            throw new WelkinExcption(WelkinErrorType.DATA_NULL);
        }
        return queryResult;
    }

    @Override
    public void saveMenu(SysMenu sysMenu) {
        sysMenu.setId(WelkinIdGenerator.getId());
        this.save(sysMenu);
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        boolean success = this.updateById(sysMenu);
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
    public List<SysMenu> getByUserId(String id) {
        return sysMenuMapper.getByUserId(id);
    }


}

