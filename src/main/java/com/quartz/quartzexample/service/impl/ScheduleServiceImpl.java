package com.quartz.quartzexample.service.impl;


import com.quartz.quartzexample.dto.ScheduleDTO;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.quartz.JobKey.jobKey;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleServiceImpl implements ScheduleService
{
    private final Scheduler scheduler;
    private final QuartzUtils quartzUtils;

    @Override
    public List<String> getAllRunningTimers()
    {
        log.info("SchedulerService getAllRunningTimers method started");
        try
        {
            List<String> runningQuartz = scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream().map(jobKey -> {
                        try
                        {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return jobDetail.getKey().getName() + " - " + jobDetail.getKey().getGroup();
                        }
                        catch (SchedulerException e)
                        {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            log.info("SchedulerService getAllRunningTimers method finished successfully");
            return runningQuartz;
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public ResponseEntity<?> rescheduleByHour(ScheduleDTO scheduleDTO, String triggerName, String triggerGroup, String jobName, String jobGroup) {
        log.info("SchedulerService rescheduleByHour method started");

        String cron = createCronFromHour(scheduleDTO.getHour());
        if (cron == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hour should be lower than 720");
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup())
                    .withDescription(trigger.getDescription())
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.rescheduleJob(triggerKey, newTrigger);
        }
        catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        log.info("SchedulerService rescheduleByHour method finished successfully");
        return ResponseEntity.ok("successfully updated");
    }

    @Override
    public ResponseEntity<?> rescheduleByCron(ScheduleDTO scheduleDTO, String triggerName, String triggerGroup, String jobName, String jobGroup) {
        log.info("SchedulerService rescheduleByCron method started");

        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup())
                    .withDescription(trigger.getDescription())
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleDTO.getCron()))
                    .build();
            scheduler.rescheduleJob(triggerKey, newTrigger);

        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        log.info("SchedulerService rescheduleByCron method finished successfully");
        return ResponseEntity.ok("successfully updated");
    }

    @Override
    public Boolean pauseJob(String jobName, String jobGroup)
    {
        log.info("SchedulerService pauseJob method started");

        try {
            scheduler.pauseJob(jobKey(jobName, jobGroup));
            log.info("Paused job with key {} - {}", jobGroup, jobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Could not pause job with key {} - {} error message - {} ", jobGroup, jobName, e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Boolean resumeJob(String jobName, String jobGroup)
    {
        log.info("SchedulerService resumeJob method started");

        try {
            scheduler.resumeJob(jobKey(jobName, jobGroup));
            log.info("Resumed job with key - {}", jobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Could not resume job with key - {} due to error - {}", jobName, e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public <T extends QuartzJobBean> Boolean createWithCron(String jobName, String jobGroup, String jobDescription, Class<T> jobClass, String triggerName, String triggerGroup, String triggerDescription, String cron) {
        log.info("SchedulerService createWithCron method started");

        try {
            JobDetail jobDetail = quartzUtils.buildJobDetail(jobName, jobGroup, jobDescription, jobClass);
            Trigger trigger = quartzUtils.buildTrigger(jobDetail, triggerName, triggerGroup, triggerDescription, cron);
            scheduler.scheduleJob(jobDetail, trigger);

            log.info("Job created with name {}", jobName);
            return true;
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private String createCronFromHour(int hour) {
        String cron = null;
        int day = hour / 24;
        if (day >= 30) {
            return null;
        }
        if (day > 0) {
            hour = hour % 24;
            cron = "0 0 " + hour + " 1/" + day + " * ? *";
        } else {
            cron = "0 0 0/" + hour + " " + "1/1 * ? *";
        }

        return cron;
    }

}
