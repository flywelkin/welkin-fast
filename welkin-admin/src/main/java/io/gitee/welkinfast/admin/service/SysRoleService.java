package io.gitee.welkinfast.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gitee.welkinfast.admin.controller.vo.RoleVo;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import io.gitee.welkinfast.admin.service.modle.RoleModel;
import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;

import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 12:50
 * @Version 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据ID获取角色信息
     *
     * @param id 角色ID
     * @return 查询到的角色信息
     */
    RoleModel getRoleById(String id);

    /**
     * 保存角色系信息
     *
     * @param roleMode 需要被保存的角色信息
     */
    void saveRole(RoleModel roleMode);

    /**
     * 根据ID更新角色信息
     *
     * @param roleModel 需要被更新的角色信息
     */
    void updateRole(RoleModel roleModel);

    /**
     * 根据ID删除角色信息
     *
     * @param roleId 角色ID
     */
    void deleteById(String roleId);

    /**
     * 根据用户ID，查询用户绑定的所有角色信息
     *
     * @param userId 用户ID
     * @return 查询到的角色信息集合
     */
    List<SysRole> getAllByUserId(String userId);

    /**
     * 分页查询角色信息集合
     *
     * @param pageRequest 分页查询参数
     * @return 查询到的分页结果
     */
    PageResult<SysRole> getUserList(PageRequest<RoleVo> pageRequest);

    List<SysRole> getByUserIdAndIsAdmin(String id, boolean admin);
}

