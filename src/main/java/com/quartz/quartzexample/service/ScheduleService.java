package com.quartz.quartzexample.service;

import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.ScheduleDTO;
import com.quartz.quartzexample.model.JobTracking;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Set;

public interface ScheduleService
{
    QuartzJobDTO createJob(QuartzJobDTO job, Class jobClass);

    List<QuartzJobDTO> findAllJobs();

    Boolean removeJob(QuartzJobDTO quartzJobDTO);

    Set<JobTrackingDTO> getJobTracking();

    Boolean reScheduleWithCron(String jobName, String jobGroup, String cron);

    Boolean pause(String jobName, String jobGroup);

    Boolean resume(String jobName, String jobGroup);
}
