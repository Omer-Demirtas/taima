package com.quartz.quartzexample.controller;

import com.quartz.quartzexample.dto.ScheduleDTO;
import com.quartz.quartzexample.job.ExampleJob;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import com.quartz.quartzexample.service.ScheduleService;
import com.quartz.quartzexample.utils.JobStatus;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/test")
    public Boolean example()
    {
        qrtzJobStateTrackerService.save(
            new JobTracking()
                    .builder()
                    .jobName(LocalDateTime.now().toString())
                    .triggerName(LocalDateTime.now().toString())
                    .description(LocalDateTime.now().toString())
                    .lastFinishAt(new Date())
                    .lastStartAt(new Date())
                    .jobStatus(JobStatus.SUCCESS)
                    .build()
        );
        return true;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createJob()
    {
        return ResponseEntity.ok(
                scheduleService.createWithCron(
                        jobNameExampleJob,
                        jobGroupExampleJob,
                        "Barsan Delete Old Data Service Job",
                        ExampleJob.class,
                        triggerNameExampleJob,
                        triggerGroupExampleJob,
                        "Barsan Delete Old Data Service Trigger",
                        "0 0 0 ? * * *"
                )
        );
    }

    @PostMapping("/reschedule/withHour")
    public ResponseEntity<?> rescheduleByHour(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.rescheduleByHour(scheduleDTO, triggerNameExampleJob, triggerGroupExampleJob, jobNameExampleJob, jobGroupExampleJob);
    }

    @PostMapping("/reschedule/withCron")
    public ResponseEntity<?> rescheduleByCron(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.rescheduleByCron(scheduleDTO, triggerNameExampleJob, triggerGroupExampleJob, jobNameExampleJob, jobGroupExampleJob);
    }

    @GetMapping("/getUpdateJobState")
    public ResponseEntity<?> getUpdateJobState() {
        return ResponseEntity.ok(qrtzJobStateTrackerService.getUpdateJobState(jobNameExampleJob));
    }

    @GetMapping("/getAllRunning")
    public ResponseEntity<?> getAllRunningJobs()
    {
        return ResponseEntity.ok(scheduleService.getAllRunningTimers());
    }

    @PostMapping("/pauseJob")
    public ResponseEntity<Boolean> pauseJob()
    {
        return ResponseEntity.ok(scheduleService.pauseJob(jobNameExampleJob, jobGroupExampleJob));
    }

    @PostMapping("/resumeJob")
    public ResponseEntity<Boolean> resumeJob()
    {
        return ResponseEntity.ok(scheduleService.resumeJob(jobNameExampleJob, jobGroupExampleJob));
    }
}
