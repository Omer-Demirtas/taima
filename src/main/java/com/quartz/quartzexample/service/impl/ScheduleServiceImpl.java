package com.quartz.quartzexample.service.impl;


import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobKey.jobKey;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService
{
    private final Scheduler scheduler;

    @Override
    public QuartzJobDTO createJob(QuartzJobDTO job, Class jobClass)
    {
        JobDetail jobDetail = job.buildJobDetail(jobClass);
        Trigger trigger = job.getTrigger().buildTrigger();
        log.info("About to save job with key - {}", jobDetail.getKey());
        try
        {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job with key - {} saved successfully", jobDetail.getKey());
        }
        catch (Exception e)
        {
            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
            //throw new IllegalArgumentException(e.getLocalizedMessage());
            return null;
        }
        return job;
    }

    @Override
    public List<QuartzJobDTO> findAllJobs()
    {
        List<QuartzJobDTO> jobList = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String name = jobKey.getName();
                    String group = jobKey.getGroup();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
                    jobList.add(QuartzJobDTO.buildJobDTO(jobDetail, triggers, scheduler));
                }
            }
        } catch (SchedulerException e) {
            log.error("Could not find all jobs due to error - {}", e.getLocalizedMessage());
        }
        return jobList;
    }
}
