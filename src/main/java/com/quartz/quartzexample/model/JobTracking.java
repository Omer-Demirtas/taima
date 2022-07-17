package com.quartz.quartzexample.model;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.utils.JobStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "QRTZ_JOB_TRACKING")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobTracking
{
    @Id
    @Column(name = "JOB_NAME", nullable = false, length = 50)
    private String jobName;

    @Column(name = "JOB_GROUP", nullable = false, length = 50)
    private String jobGroup;

    @Column(name = "TRIGGER_NAME", nullable = false, length = 50)
    private String triggerName;

    @Column(name = "LAST_START_AT", nullable = false)
    private LocalDateTime lastStartAt;

    @Column(name = "LAST_FINISH_AT", nullable = false)
    private LocalDateTime lastFinishAt;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @Column(name = "JOB_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;

    public JobTracking(QuartzJobDTO job, JobStatus jobStatus, String description, LocalDateTime lastStartAt, LocalDateTime lastFinishAt)
    {
        this.jobName = job.getName();
        this.jobGroup = job.getGroup();
        this.triggerName = job.getTrigger().getName();
        this.jobStatus = jobStatus;
        this.description = description;
        this.lastStartAt = lastStartAt;
        this.lastFinishAt = lastFinishAt;
    }
}
