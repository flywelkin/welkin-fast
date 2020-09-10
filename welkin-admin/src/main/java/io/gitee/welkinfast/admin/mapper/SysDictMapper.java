package io.gitee.welkinfast.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.gitee.welkinfast.admin.mapper.dao.SysDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 字典数据持久层接口
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
}