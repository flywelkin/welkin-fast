package io.gitee.welkinfast.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.gitee.welkinfast.service.mapper.dao.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  菜单信息数据持久层接口
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getByUserId(String userId);
}