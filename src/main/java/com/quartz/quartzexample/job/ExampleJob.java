package com.quartz.quartzexample.job;

import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.ExampleJobService;
import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import com.quartz.quartzexample.utils.JobStateEnum;
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
    private final ExampleJobService exampleJobService;
    private final QrtzJobStateTrackerService qrtzJobStateTrackerService;

    //private final BarsanServiceDeleteOldData barsanServiceDeleteOldData;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        String jobName = context.getJobDetail().getKey().getName();
        JobTracking qrtzJobStateTracker = updateJobState.updateJobState(jobName);
        if (qrtzJobStateTracker == null)
        {
            return;
        }

        log.info("{} Executed {}:{}:{}", jobName,  LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
        exampleJobService.execute();

        qrtzJobStateTracker.setState(JobStateEnum.FINISHED.toString());
        qrtzJobStateTracker.setLastUpdate(LocalDateTime.now().toString());
        qrtzJobStateTrackerService.save(qrtzJobStateTracker);
    }
}
