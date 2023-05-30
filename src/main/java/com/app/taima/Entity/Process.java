package com.app.taima.Entity;

import com.app.taima.enums.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_process")
public class Process {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "process_type")
    private ProcessType processType;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private SchedulerJob job;
}
