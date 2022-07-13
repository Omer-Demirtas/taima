package com.quartz.quartzexample.controller;

import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.dto.ScheduleDTO;
import com.quartz.quartzexample.job.ExampleJob;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.JobStatus;
import com.quartz.quartzexample.utils.QuartzConstants;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quartz/")
public class ExampleQuartzJobController
{
    private final ScheduleService scheduleService;
    private final QrtzJobStateTrackerService qrtzJobStateTrackerService;

    private static final String triggerNameExampleJob = "example-job-trigger-name";
    private static final String triggerGroupExampleJob = "example-job-triggers";
    private static final String jobNameExampleJob = "example-job-job-name";
    private static final String jobGroupExampleJob = "example-job-jobs";

    @PostMapping("/create/cron/{name}")
    public ResponseEntity<QuartzJobDTO> createWithCron(@RequestBody QuartzTriggerDTO trigger, @PathVariable("name") String name)
    {
        QuartzJobDTO jobDTO = QuartzConstants.JOBS.get(name);

        jobDTO.getTrigger().setCron(trigger.getCron());

        return ResponseEntity.ok(scheduleService.createJob(jobDTO, jobDTO.getJobClass()));
    }

    @GetMapping("/getUpdateJobState")
    public ResponseEntity<?> getUpdateJobState() {
        return ResponseEntity.ok(qrtzJobStateTrackerService.getUpdateJobState(jobNameExampleJob));
    }

}
