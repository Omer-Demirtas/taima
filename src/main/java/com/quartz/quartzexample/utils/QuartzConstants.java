package com.quartz.quartzexample.utils;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.service.impl.Job1Service;

import java.util.HashMap;
import java.util.Map;

public class QuartzConstants
{
    public static final Map<String, QuartzJobDTO> JOBS = new HashMap<String, QuartzJobDTO>()
    {{
        put(
                "job_1_plus",
                QuartzJobDTO.builder()
                        .name("plus")
                        .group("transfer-jobs")
                        .description("aASDASDSA")
                        .jobClass(Job1Service.class)
                        .trigger(
                                QuartzTriggerDTO.builder()
                                        .name("plus")
                                        .group("transfer-triggers")
                                        .cron("0 0 2 1/1 * ? *")
                                        .build()
                        )
                        .build()
        );
    }};
}
