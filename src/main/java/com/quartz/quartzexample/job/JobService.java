package com.quartz.quartzexample.job;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.QuartzJobTrackingService;
import com.quartz.quartzexample.utils.JobStatus;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
public abstract class JobService extends QuartzJobBean
{
    public QuartzJobDTO job;
    private QuartzJobTrackingService jobTrackingService;

    @Autowired
    public final void setJobTrackingService(QuartzJobTrackingService jobTrackingService) {
        this.jobTrackingService = jobTrackingService;
    }

    public abstract String process();

    @Override
    protected void executeInternal(JobExecutionContext context)
    {
        LocalDateTime startAt = LocalDateTime.now();
        String description;
        JobStatus jobStatus;
        String jobName = context.getJobDetail().getKey().getName();
        String jobGroup = context.getJobDetail().getKey().getGroup();

        job = QuartzUtils.getJobDetails(jobName, jobGroup);

        log.info("Job start {} {} || at {} ", jobName, jobGroup , startAt);

        try
        {
            description = process();
            jobStatus = JobStatus.SUCCESS;
        }
        catch (Exception ex)
        {
            description = ex.getMessage();
            jobStatus = JobStatus.ERROR;
            log.error("error at {} : {}", job.getName(), ex.getMessage());
        }

        LocalDateTime finishAt = LocalDateTime.now();
        JobTracking tracking = new JobTracking(job, jobStatus, description, startAt, finishAt);

        jobTrackingService.save(tracking);
        log.info("Job finish at {}", finishAt);
    }
}
