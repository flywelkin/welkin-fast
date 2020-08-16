package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.controller.vo.user.SaveUserVo;
import io.gitee.welkinfast.admin.controller.vo.user.UserVo;
import io.gitee.welkinfast.admin.mapper.dao.SysUser;
import io.gitee.welkinfast.admin.mapper.dao.SysUserRole;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysUserService extends IService<SysUser> {


    void updateUser(SysUser user);

    void deleteById(String id);

    void saveUserVo(SaveUserVo user);

    UserVo getUserVoById(String id);

    SysUser getByLoginName(String loginName);

}

