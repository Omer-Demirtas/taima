package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.job.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1Service extends JobService
{
    @Override
    public void process()
    {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
