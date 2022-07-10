package com.quartz.quartzexample.service;

import com.quartz.quartzexample.dto.ScheduleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public interface ScheduleService
{
    List<String> getAllRunningTimers();

    Boolean pauseJob(String jobName, String jobGroup);

    Boolean resumeJob(String jobName, String jobGroup);

    ResponseEntity<?> rescheduleByHour(ScheduleDTO scheduleDTO, String triggerName, String triggerGroup, String jobName, String jobGroup);

    ResponseEntity<?> rescheduleByCron(ScheduleDTO scheduleDTO, String triggerName, String triggerGroup, String jobName, String jobGroup);

    <T extends QuartzJobBean> Boolean createWithCron(String jobName, String jobGroup, String jobDescription, Class<T> jobClass, String triggerName, String triggerGroup, String triggerDescription, String cron);
}
