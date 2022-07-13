package com.quartz.quartzexample.controller;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.QuartzConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quartz/")
public class QuartzJobController
{
    private final ScheduleService scheduleService;

    @PostMapping("/create/cron/{name}")
    public ResponseEntity<QuartzJobDTO> createWithCron(@RequestBody QuartzTriggerDTO trigger, @PathVariable("name") String name)
    {
        QuartzJobDTO jobDTO = QuartzConstants.JOBS.get(name);

        jobDTO.getTrigger().setCron(trigger.getCron());

        return ResponseEntity.ok(scheduleService.createJob(jobDTO, jobDTO.getJobClass()));
    }

    @GetMapping("/job-details")
    public ResponseEntity<List<QuartzJobDTO>> createWithCron()
    {
        return ResponseEntity.ok(scheduleService.findAllJobs());
    }


}
