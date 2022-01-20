package com.quartz.quartzexample.utils;

import com.quartz.quartzexample.model.QrtzJobStateTracker;
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

    public QrtzJobStateTracker updateJobState(String jobName) {
        QrtzJobStateTracker qrtzJobStateTracker = qrtzJobStateTrackerService.findFirstByJobName(jobName);
        if (qrtzJobStateTracker == null) {
            QrtzJobStateTracker newQrtzJobStateTracker = new QrtzJobStateTracker();
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
