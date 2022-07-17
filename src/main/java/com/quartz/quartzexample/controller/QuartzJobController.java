package com.quartz.quartzexample.controller;

import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.dto.QuartzJobDTO;
import com.quartz.quartzexample.dto.QuartzTriggerDTO;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quartz/")
public class QuartzJobController
{
    private final ScheduleService scheduleService;

    @PostMapping("/cron/{jobName}/{jobGroup}")
    public ResponseEntity<?> createWithCron(@RequestBody QuartzTriggerDTO trigger, @PathVariable("jobName") String jobName, @PathVariable String jobGroup)
    {
        QuartzJobDTO jobDTO = QuartzUtils.getJobDetails(jobName, jobGroup);

        if(jobDTO == null)
        {
            log.info("Hob Not found");
            return ResponseEntity.internalServerError().body("Job Not found");
        }

        if(trigger != null)
        {
            jobDTO.getTrigger().setCron(trigger.getCron());
        }

        return ResponseEntity.ok(scheduleService.createJob(jobDTO, jobDTO.getJobClass()));
    }

    @PutMapping("/cron/{jobName}/{jobGroup}")
    public ResponseEntity<?> reScheduleWithCron(@RequestBody QuartzTriggerDTO trigger, @PathVariable("jobName") String jobName, @PathVariable("jobGroup") String jobGroup)
    {
        return ResponseEntity.ok(scheduleService.reScheduleWithCron(jobName, jobGroup, trigger.getCron()));
    }

    @GetMapping("/job-details")
    public ResponseEntity<List<QuartzJobDTO>> createWithCron()
    {
        return ResponseEntity.ok(scheduleService.findAllJobs());
    }

    @DeleteMapping("/{jobName}/{jobGroup}")
    public ResponseEntity<Boolean> delete(@PathVariable("jobName") String jobName, @PathVariable("jobGroup") String jobGroup)
    {
        return ResponseEntity.ok(
            scheduleService.removeJob(
                    QuartzJobDTO.builder()
                            .name(jobName)
                            .group(jobGroup)
                            .build()
            )
        );
    }

    @GetMapping("/tracking")
    public ResponseEntity<Set<JobTrackingDTO>> getAllJobTracking()
    {
        return ResponseEntity.ok(scheduleService.getJobTracking());
    }

    @GetMapping("/tracking/{jobName}/{jobGroup}")
    public ResponseEntity<JobTrackingDTO> getJobTracking(@PathVariable String jobName, @PathVariable String jobGroup)
    {
        return null;
    }
}
