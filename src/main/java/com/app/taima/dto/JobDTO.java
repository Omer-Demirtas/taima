package com.app.taima.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobDTO {
    private Long id;
    private String name;
    private String group;
    private String cron;

    private Boolean isCron;

    private Long repeatTime;

    private List<ProcessDTO> processes;

    public JobDTO(String name, String group) {
        this.name = name;
        this.group = group;
    }
}
