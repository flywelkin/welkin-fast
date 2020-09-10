package io.gitee.welkinfast.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 角色信息数据持久层接口
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getByUserId(String id);
}