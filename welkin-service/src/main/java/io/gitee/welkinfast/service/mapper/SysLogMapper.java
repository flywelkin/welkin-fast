package io.gitee.welkinfast.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.gitee.welkinfast.service.mapper.dao.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 *  日志数据持久层接口
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:41
 * @Version 1.0.0
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}