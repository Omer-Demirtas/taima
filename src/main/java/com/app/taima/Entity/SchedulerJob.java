package com.app.taima.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "t_scheduler_job")
public class SchedulerJob {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Process> processes;
}
