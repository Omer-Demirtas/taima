package com.quartz.quartzexample.job;

import com.quartz.quartzexample.service.impl.Job1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class Job1 extends QuartzJobBean
{
    private final Job1Service job1Service;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        job1Service.run();
    }
}
