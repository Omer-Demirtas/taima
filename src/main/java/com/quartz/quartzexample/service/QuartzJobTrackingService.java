package com.quartz.quartzexample.service;

import com.quartz.quartzexample.dto.QrtzJobStateTrackerDTO;
import com.quartz.quartzexample.model.JobTracking;

public interface QuartzJobTrackingService
{
    JobTracking findFirstByJobName(String jobName);

    JobTracking save(JobTracking qrtzJobStateTracker);

    QrtzJobStateTrackerDTO getUpdateJobState();

    QrtzJobStateTrackerDTO getUpdateJobState(String jobNameExampleJob);
}
