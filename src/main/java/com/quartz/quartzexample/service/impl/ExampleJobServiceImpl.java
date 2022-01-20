package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.job.ExampleJob;
import com.quartz.quartzexample.service.ExampleJobService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ExampleJobServiceImpl implements Job, ExampleJobService
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("execute from quartz");
    }

    @Override
    public void execute() {
        log.info("execute from service");
    }
}
