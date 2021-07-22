package io.gitee.welkinfast.quartz.listener;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:24
 * @Version 1.0.0
 */

import io.gitee.welkinfast.quartz.service.SysTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//@Component
//@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(ScheduleJobInitListener.class);

    @Autowired
    SysTaskService sysTaskService;

    @Override
    public void run(String... arg0) throws Exception {
        try {
            sysTaskService.initSchedule();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}