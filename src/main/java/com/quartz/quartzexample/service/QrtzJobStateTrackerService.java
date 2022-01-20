package com.quartz.quartzexample.service;

import com.quartz.quartzexample.DTO.QrtzJobStateTrackerDTO;
import com.quartz.quartzexample.model.QrtzJobStateTracker;

public interface QrtzJobStateTrackerService
{
    QrtzJobStateTracker findFirstByJobName(String jobName);

    QrtzJobStateTracker save(QrtzJobStateTracker qrtzJobStateTracker);

    QrtzJobStateTrackerDTO getUpdateJobState();

    QrtzJobStateTrackerDTO getUpdateJobState(String jobNameExampleJob);
}
