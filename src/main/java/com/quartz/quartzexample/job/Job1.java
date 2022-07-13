package com.quartz.quartzexample.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job1 extends QuartzJobBean
{
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }
}
