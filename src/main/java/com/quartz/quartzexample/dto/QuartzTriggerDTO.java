package com.quartz.quartzexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static java.time.ZoneId.systemDefault;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartzTriggerDTO
{
    private String name;
    private String group;
    private String cron;
    private Trigger.TriggerState triggerState;

    public static QuartzTriggerDTO buildTriggerDTO(Trigger trigger, Trigger.TriggerState triggerState) {
        CronTrigger cronTrigger = (CronTrigger) trigger;


        return QuartzTriggerDTO.builder()
                .name(trigger.getKey().getName())
                .group(trigger.getKey().getGroup())
                .triggerState(triggerState)
                .cron(cronTrigger.getCronExpression())
                .build();
    }

    public Trigger buildTrigger()
    {
        JobDataMap jobDataMap = new JobDataMap();
        //jobDataMap.put("fireTime", fireTime);
        return newTrigger()
                .withIdentity(name, group)
                .withSchedule(cronSchedule(cron)
                        .withMisfireHandlingInstructionDoNothing()
                        .inTimeZone(TimeZone.getTimeZone(systemDefault())))
                .usingJobData("cron", cron)
                .build();
    }
}