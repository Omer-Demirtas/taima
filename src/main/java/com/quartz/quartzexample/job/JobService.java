package com.quartz.quartzexample.job;

import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public abstract class JobService extends QuartzJobBean
{
    public abstract void process();

    public void run()
    {
        log.info("Start Job");

        process();

        log.info("Finish Job");
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        log.info("Start Job");

        process();

        log.info("Finish Job");
    }
}
