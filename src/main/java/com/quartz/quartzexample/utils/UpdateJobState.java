package com.quartz.quartzexample.utils;

import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateJobState
{
    private final QrtzJobStateTrackerService qrtzJobStateTrackerService;

    public UpdateJobState(QrtzJobStateTrackerService qrtzJobStateTrackerService) {
        this.qrtzJobStateTrackerService = qrtzJobStateTrackerService;
    }

    public JobTracking updateJobState(String jobName) {
        JobTracking qrtzJobStateTracker = qrtzJobStateTrackerService.findFirstByJobName(jobName);
        if (qrtzJobStateTracker == null) {
            JobTracking newQrtzJobStateTracker = new JobTracking();
            newQrtzJobStateTracker.setJobName(jobName);
            newQrtzJobStateTracker.setLastUpdate(LocalDateTime.now().toString());
            newQrtzJobStateTracker.setState(JobStateEnum.RUNNING.toString());
            qrtzJobStateTrackerService.save(newQrtzJobStateTracker);
            qrtzJobStateTracker = qrtzJobStateTrackerService.findFirstByJobName(jobName);
        } else {
            if (qrtzJobStateTracker.getState() == JobStateEnum.RUNNING.toString()) {
                return null;
            }
            qrtzJobStateTracker.setState(JobStateEnum.RUNNING.toString());
            qrtzJobStateTracker.setLastUpdate(LocalDateTime.now().toString());
            qrtzJobStateTrackerService.save(qrtzJobStateTracker);
        }
        return qrtzJobStateTracker;
    }

}
