package io.gitee.welkinfast.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:47
 * @Version 1.0.0
 */
@Slf4j
//持久化
@PersistJobDataAfterExecution
//禁止并发执行(Quartz不要并发地执行同一个job定义)
@DisallowConcurrentExecution
@Service
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        log.debug("这是一个定时任务");
    }
}
