package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.mapper.dao.SysMenu;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysMenuService extends IService<SysMenu> {


    SysMenu getMenuById(String id);

    void saveMenu(SysMenu sysMenu);

    void updateMenu(SysMenu sysMenu);

    void deleteById(String id);

    List<SysMenu> getByUserId(String id);
}

