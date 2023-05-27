package com.app.taima.dto;

import lombok.Data;

@Data
public class JobDTO {
    private Long id;
    private String name;
    private String group;
    private String cron;

    public JobDTO(String name, String group) {
        this.name = name;
        this.group = group;
    }
}
