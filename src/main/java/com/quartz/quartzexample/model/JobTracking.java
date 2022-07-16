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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "JOB_NAME", nullable = false)
    private String jobName;

    @Column(name = "JOB_GROUP", nullable = false)
    private String jobGroup;

    @Column(name = "TRIGGER_NAME")
    private String triggerName;

    @Column(name = "LAST_START_AT", nullable = false)
    private LocalDateTime lastStartAt;

    @Column(name = "LAST_FINISH_AT")
    private LocalDateTime lastFinishAt;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "JOB_STATUS")
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
