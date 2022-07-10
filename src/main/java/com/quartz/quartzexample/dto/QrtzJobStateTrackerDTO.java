package com.quartz.quartzexample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quartz.quartzexample.model.JobTracking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrtzJobStateTrackerDTO {

    private String jobName;

    private String state;

    private String lastUpdate;

    public QrtzJobStateTrackerDTO(JobTracking qrtzJobStateTracker) {
        this.jobName = qrtzJobStateTracker.getJobName();
        //this.state = qrtzJobStateTracker.getState();
        //this.lastUpdate = qrtzJobStateTracker.getLastUpdate();
    }
}
