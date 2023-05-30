package com.app.taima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    private String name;
    private String group;
    private String cron;

    private Boolean isCron;

    private Long repeatTime;

    private List<ProcessDTO> processes;

    public JobDTO(String name, String group, String cron) {
        this.name = name;
        this.group = group;
        this.cron = cron;
    }
}
