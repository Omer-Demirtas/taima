package com.app.taima.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "t_scheduler_job")
public class SchedulerJob {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "job_name")
    private String name;
    @Column(name = "job_group")
    private String group;
    @Column(name = "cron")
    private String cron;
    @Column(name = "repeat_time")
    private Long repeatTime;

    @Column(name = "is_cron")
    private Boolean isCron;

    @OneToMany
    private List<Process> processes;
}
