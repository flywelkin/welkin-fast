package io.gitee.welkinfast.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.service.mapper.dao.SysUser;
import io.gitee.welkinfast.service.service.modle.UserModle;
import io.gitee.welkinfast.service.vo.user.UserInfo;
import io.gitee.welkinfast.service.vo.user.UserQuery;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String loginName);

    UserModle getUserById(String id);

    void saveUserVo(UserModle userModle);

    void updateUser(UserModle userModle);

    void deleteById(String id);

    PageResult<SysUser> getUserList(PageRequest<UserQuery> pageRequest);

    UserInfo getUserInfoById(String id, Boolean isAdmin);
}

