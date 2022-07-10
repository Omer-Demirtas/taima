package com.quartz.quartzexample.model;

import com.fasterxml.jackson.databind.DatabindException;
import com.quartz.quartzexample.utils.JobStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QRTZ_JOB_TRACKING")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobTracking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "JOB_NAME", nullable = false)
    private String jobName;

    @Column(name = "TRIGGER_NAME")
    private String triggerName;

    @Column(name = "LAST_START_AT", nullable = false)
    private Date lastStartAt;

    @Column(name = "LAST_FINISH_AT")
    private Date lastFinishAt;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "JOB_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;
}
