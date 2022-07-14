package com.quartz.quartzexample.service;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.ScheduleDTO;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public interface ScheduleService
{
    QuartzJobDTO createJob(QuartzJobDTO job, Class jobClass);

    List<QuartzJobDTO> findAllJobs();

    Boolean removeJob(QuartzJobDTO quartzJobDTO);
}
