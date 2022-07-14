package com.quartz.quartzexample.job;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public abstract class JobService extends QuartzJobBean
{
    public QuartzJobDTO job;

    public abstract void process();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        String jobName = context.getJobDetail().getKey().getName();
        String jobGroup = context.getJobDetail().getKey().getGroup();
        job = QuartzUtils.getJobDetails(jobName, jobGroup);

        log.info("Job start {} {} || at {} ", jobName, jobGroup , LocalDateTime.now());

        process();

        log.info("Job finish at {}", LocalDateTime.now());
    }
}
