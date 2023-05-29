package com.app.taima.job;

import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.SchedulerService;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SampleCronJob extends QuartzJobBean {

    private SchedulerService schedulerService;

    @Override
    protected void executeInternal(JobExecutionContext context)  {
        try {
            JobDetail jobDetail = context.getJobDetail();
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");

            schedulerService = (SchedulerService) applicationContext.getBean(SchedulerService.class);

            schedulerService.getAllJob();
            log.info("{} {}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup());
        } catch (Exception e) {
            log.error(e, e);
        }
    }
}
