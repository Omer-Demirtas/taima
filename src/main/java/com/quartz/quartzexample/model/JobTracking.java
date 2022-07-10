package com.quartz.quartzexample.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "QRTZ_JOB_TRACKING")
@Data
public class JobTracking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "last_update", nullable = false)
    private String lastUpdate;
}
