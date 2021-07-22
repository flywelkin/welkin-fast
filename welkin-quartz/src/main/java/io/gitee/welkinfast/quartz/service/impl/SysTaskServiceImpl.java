package io.gitee.welkinfast.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.common.id.CustomIdGenerator;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.quartz.constant.JobStatusEnum;
import io.gitee.welkinfast.quartz.mapper.SysTaskMapper;
import io.gitee.welkinfast.quartz.mapper.dao.SysTask;
import io.gitee.welkinfast.quartz.service.QuartzManager;
import io.gitee.welkinfast.quartz.service.SysTaskService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:34
 * @Version 1.0.0
 */
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements SysTaskService {

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private SysTaskMapper sysTaskMapper;

    @Override
    public void initSchedule() {
        List<SysTask> jobList = this.list();
        for (SysTask task : jobList) {
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
                quartzManager.addJob(task);
            }
        }
    }

    @Override
    public PageResult<SysTask> getPage(SysTask sysTask, Integer current, Integer size) {
        LambdaQueryWrapper<SysTask> eq = Wrappers.<SysTask>lambdaQuery()
                .eq(StringUtils.isNotEmpty(sysTask.getJobGroup()), SysTask::getJobGroup, sysTask.getJobGroup())
                .eq(StringUtils.isNotEmpty(sysTask.getJobStatus()), SysTask::getJobStatus, sysTask.getJobStatus())
                .like(StringUtils.isNotEmpty(sysTask.getJobName()), SysTask::getJobName, sysTask.getJobName());
        Page<SysTask> page = new Page<>();
        Page<SysTask> selectResult = sysTaskMapper.selectPage(page, eq);
        PageResult<SysTask> result = new PageResult<>();
        result.setRecords(selectResult.getRecords());
        result.setTotal(selectResult.getTotal());
        result.setCurrent(selectResult.getTotal());
        result.setSize(selectResult.getSize());
        return result;
    }


    @Override
    public SysTask getByNameAndGroup(String name, String group) {
        LambdaQueryWrapper<SysTask> queryWrapper = Wrappers.<SysTask>lambdaQuery()
                .eq(SysTask::getJobName, name)
                .eq(SysTask::getJobGroup, group);
        return sysTaskMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateByTaskStatus(SysTask sysTask) throws SchedulerException {
        if (StringUtils.isEmpty(sysTask.getId())) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(sysTask.getJobStatus())) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        sysTaskMapper.updateById(sysTask);
        if (JobStatusEnum.STOP.getCode().equals(sysTask.getJobStatus())) {
            quartzManager.pauseJob(sysTask);
        }
        if (JobStatusEnum.RUNNING.getCode().equals(sysTask.getJobStatus())) {
            quartzManager.resumeJob(sysTask);
        }
    }

    @Override
    public void runJobNow(String id) {
        SysTask sysTask = getById(id);
        if (ObjectUtils.isEmpty(sysTask)) {
            throw new CustomExcption(CustomErrorType.DATA_NULL, "任务不存在");
        }
        try {
            quartzManager.runJobNow(sysTask);
        } catch (Exception e) {
            throw new CustomExcption(CustomErrorType.TASK_ERROR, "执行出错:" + e.getMessage());
        }
    }


    @Override
    public boolean save(SysTask entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        SysTask sysTask = getByNameAndGroup(entity.getJobName(), entity.getJobGroup());
        if (!ObjectUtils.isEmpty(sysTask)) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL, "分组下的任务名称已存在");
        }
        entity.setId(CustomIdGenerator.getId());
        entity.setJobStatus(JobStatusEnum.STOP.getCode());
        int insert = sysTaskMapper.insert(entity);
        if (insert != 1) {
            throw new CustomExcption(CustomErrorType.DATA_SAVE_FAIL);
        }
        return true;
    }

    public SysTask getById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomExcption(CustomErrorType.PARAMETER_VALIDATION_ERROR);
        }
        return sysTaskMapper.selectById(id);
    }

}
