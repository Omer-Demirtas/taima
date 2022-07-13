package com.quartz.quartzexample.utils;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.job.Job1;

import java.util.HashMap;
import java.util.Map;

public class QuartzConstants
{
    public static final Map<String, QuartzJobDTO> JOBS = new HashMap<String, QuartzJobDTO>()
    {{
        put(
                "job_1",
                QuartzJobDTO.builder()
                        .name("v_sfn-to-t_sfn-job")
                        .group("transfer-jobs")
                        .description("V_SFN To T_SFN Transfer Job")
                        .jobClass(Job1.class)
                        .trigger(
                                QuartzTriggerDTO.builder()
                                        .name("v_sfn-to-t_sfn-trigger")
                                        .group("transfer-triggers")
                                        .cron("0 0 2 1/1 * ? *")
                                        .build()
                        )
                        .build()
        );
    }};
}
