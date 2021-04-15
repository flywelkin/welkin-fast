package io.gitee.welkinfast.quartz.service;

import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.quartz.mapper.dao.SysTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:34
 * @Version 1.0.0
 */
public interface SysTaskService extends IService<SysTask> {

    void initSchedule();

    PageResult<SysTask> getPage(SysTask sysTask, Integer current, Integer size);

    SysTask getByNameAndGroup(String name, String group);

    void updateByTaskStatus(SysTask sysTask) throws SchedulerException;
}
