package io.gitee.welkinfast.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.gitee.welkinfast.service.mapper.dao.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 *  系统配置信息数据持久层接口
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}