package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.job.JobService;
import com.quartz.quartzexample.utils.QuartzConstants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Job1Service extends JobService
{
    @Override
    public void process()
    {
        job = QuartzConstants.JOBS.get("job_1_plus");
        log.info("Running with {}", job.getName());
    }
}
