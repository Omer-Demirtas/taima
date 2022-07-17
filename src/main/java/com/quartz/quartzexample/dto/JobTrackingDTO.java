package com.quartz.quartzexample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.utils.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTrackingDTO {

    private String jobName;

    private String jobGroup;

    private String description;

    private JobStatus jobStatus;

    private LocalDateTime lastStartAt;

    private LocalDateTime lastFinishAt;

}
