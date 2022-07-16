package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.job.JobService;
import com.quartz.quartzexample.service.QuartzJobTrackingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
public class Job1Service extends JobService
{
    @Override
    public String process()
    {
        log.info("Running with {}", job.getName());


        return "ASD";
        //throw new JobFailedException("asd");
    }
}
