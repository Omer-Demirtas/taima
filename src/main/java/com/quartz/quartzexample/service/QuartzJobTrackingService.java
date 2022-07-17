package com.quartz.quartzexample.service;

import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.model.JobTracking;

import java.util.Set;

public interface QuartzJobTrackingService
{
    JobTracking save(JobTracking jobTracking);

    Set<JobTrackingDTO> getAllJobTracking();
}
