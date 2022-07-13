package com.quartz.quartzexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.*;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobDTO
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("group")
    private String group;

    private String description;

    private Map<String, Object> data;

    @JsonProperty("trigger")
    private QuartzTriggerDTO trigger;

    private Class jobClass;

    public static QuartzJobDTO buildJobDTO(JobDetail jobDetail, List<? extends Trigger> triggers, Scheduler scheduler) throws SchedulerException {

        QuartzTriggerDTO triggerDTO = QuartzTriggerDTO.buildTriggerDTO(triggers.get(0), scheduler.getTriggerState(triggers.get(0).getKey()));

        return QuartzJobDTO.builder()
                .name(jobDetail.getKey().getName())
                .group(jobDetail.getKey().getGroup())
                .description(jobDetail.getDescription())
                .data(jobDetail.getJobDataMap())
                .trigger(triggerDTO)
                .jobClass(jobDetail.getJobClass())
                .build();
    }

    public <T extends QuartzJobBean> JobDetail buildJobDetail(Class<T> jobClass)
    {
        JobDataMap jobDataMap = data == null ? new JobDataMap(new LinkedHashMap<>()) : new JobDataMap(data);
        jobDataMap.put("jobGroup", group);
        jobDataMap.put("jobName", name);

        return newJob(jobClass)
                .withIdentity(getName(), getGroup())
                .withDescription(description)
                .usingJobData(jobDataMap)
                .build();
    }
}
