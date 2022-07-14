package com.quartz.quartzexample.job;

import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import com.quartz.quartzexample.utils.UpdateJobState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j2
public class ExampleJob extends QuartzJobBean
{
    private final UpdateJobState updateJobState;
    private final QrtzJobStateTrackerService qrtzJobStateTrackerService;

    //private final BarsanServiceDeleteOldData barsanServiceDeleteOldData;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        String jobName = context.getJobDetail().getKey().getName();

        log.info("{} Executed {}:{}:{}", jobName,  LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
    }
}
