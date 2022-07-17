package com.quartz.quartzexample.service.impl;


import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.QuartzJobTrackingService;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.quartz.JobKey.jobKey;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService
{
    private final Scheduler scheduler;
    private final QuartzJobTrackingService jobTrackingService;

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

    @Override
    public Boolean removeJob(QuartzJobDTO quartzJobDTO)
    {
        try
        {
            scheduler.deleteJob(new JobKey(quartzJobDTO.getName(), quartzJobDTO.getGroup()));

            log.info("Job deleted successfully with name {}, group {}", quartzJobDTO.getName(), quartzJobDTO.getGroup());

            return true;
        }
        catch (SchedulerException schedulerException)
        {
            log.error("Error at deleting Job with name {} - {} {}", quartzJobDTO.getName(), schedulerException.getMessage(), schedulerException.toString());

            return false;
        }
    }

    @Override
    public Set<JobTrackingDTO> getJobTracking() {
        return jobTrackingService.getAllJobTracking();
    }

    @Override
    public Boolean reScheduleWithCron(String jobName, String jobGroup, String cron)
    {
        log.info("SchedulerService rescheduleByCron method started");

        QuartzJobDTO jobDTO = QuartzUtils.getJobDetails(jobName, jobGroup);
        jobDTO.getTrigger().setCron(cron);

        TriggerKey triggerKey = new TriggerKey(jobDTO.getTrigger().getName(), jobDTO.getTrigger().getGroup());
        JobKey jobKey = new JobKey(jobDTO.getName(), jobDTO.getGroup());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup())
                    .withDescription(trigger.getDescription())
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobDTO.getTrigger().getCron()))
                    .build();
            scheduler.rescheduleJob(triggerKey, newTrigger);

        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        log.info("SchedulerService rescheduleByCron method finished successfully");
        return true;
    }

    @Override
    public Boolean pause(String jobName, String jobGroup)
    {
        try {
            scheduler.pauseJob(jobKey(jobName, jobGroup));
            log.info("Paused job with key | {} | {}", jobName, jobGroup);
            return true;
        } catch (SchedulerException e) {
            log.error("Could not pause job with key - {}.{} due to error - {}", jobName, jobGroup, e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Boolean resume(String jobName, String jobGroup) {
        try
        {
            scheduler.resumeJob(jobKey(jobName, jobGroup));
            log.info("Resumed job with key - {}.{}", jobName, jobGroup);
            return true;
        }
        catch (SchedulerException e) {
            log.error("Could not resume job with key - {}.{} due to error - {}", jobName, jobGroup, e.getLocalizedMessage());
            return false;
        }
    }
}
