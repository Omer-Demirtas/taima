package com.quartz.quartzexample.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO
{
    private int hour;
    private String cron;
    private String triggername;
    private String triggergroup;
    private String jobname;
    private String jobgroup;
}
