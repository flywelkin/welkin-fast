package io.gitee.welkinfast.quartz.controller;

import io.gitee.welkinfast.common.page.PageRequest;
import io.gitee.welkinfast.common.page.PageResult;
import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.quartz.mapper.dao.SysTask;
import io.gitee.welkinfast.quartz.service.SysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  定时任务管理控制器
 * @Author yuanjg
 * @CreateTime 2020/10/10 09:40
 * @Version 1.0.0
 */
@Api(tags = "定时任务管理")
@RestController
public class TaskController {

    @Autowired
    private SysTaskService sysTaskService;


    @ApiOperation("分页查询任务列表")
    @PostMapping("/tasks")
    public CustomResponse<PageResult<SysTask>> getPage(@RequestBody PageRequest<SysTask> pageRequest) {
        PageResult<SysTask> result = sysTaskService.getPage(pageRequest.getParams(),pageRequest.getCurrent(),pageRequest.getSize());
        return CustomResponse.OK(result);
    }

    @ApiOperation("根据ID获取定时任务")
    @GetMapping("/task/{id}")
    public CustomResponse<SysTask> getById(@PathVariable("id") String id) {
        SysTask sysTask = sysTaskService.getById(id);
        return CustomResponse.OK(sysTask);
    }

    @ApiOperation("新增定时任务")
    @PostMapping("/task")
    public CustomResponse<Boolean> saveTask(@RequestBody SysTask sysTask) {
        sysTaskService.save(sysTask);
        return CustomResponse.OK(true);
    }

    @ApiOperation("更改定时任务状态")
    @PutMapping("/task/status")
    public CustomResponse<Boolean> updateTaskStatus(@RequestBody SysTask sysTask) throws SchedulerException {
        sysTaskService.updateByTaskStatus(sysTask);
        return CustomResponse.OK(true);
    }

    @ApiOperation("根据ID删除定时任务")
    @DeleteMapping("/task/{id}")
    public CustomResponse<Boolean> deleteTask(@PathVariable("id") String id) {
        sysTaskService.removeById(id);
        return CustomResponse.OK(true);
    }

}
