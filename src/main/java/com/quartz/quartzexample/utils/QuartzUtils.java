package com.quartz.quartzexample.utils;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.service.impl.Job1Service;

import java.util.*;

public class QuartzUtils
{
    public static QuartzJobDTO getJobDetails(String jobName, String jobGroup)
    {
        return JOBS.stream().filter(job -> (job.getName().equals(jobName) && job.getGroup().equals(jobGroup))).findAny().orElse(null);
    }
    private static final Set<QuartzJobDTO> JOBS = new HashSet<>(Arrays.asList(
        QuartzJobDTO.builder()
            .name("job-1")
            .group("jobs")
            .description("Example of jobs")
            .jobClass(Job1Service.class)
            .trigger(
                QuartzTriggerDTO.builder()
                    .name("job-1-trigger")
                    .group("triggers")
                    .cron("0 0 2 1/1 * ? *")
                    .build()
            )
            .build()
    ));
}
