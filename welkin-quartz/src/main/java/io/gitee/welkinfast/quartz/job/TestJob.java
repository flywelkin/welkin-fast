package io.gitee.welkinfast.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

/**
 * @Author yuanjg
 * @CreateTime 2021/06/08 09:44
 * @Version 1.0.0
 */
@Service
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("io.gitee.welkinfast.quartz.job.TestJob");
    }
}
