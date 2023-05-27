package com.app.taima.controller;

import com.app.taima.Entity.SchedulerJobInfo;
import com.app.taima.component.JobScheduleCreator;
import com.app.taima.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/job")
public class JobController {

    private final JobService jobService;
    @GetMapping
    public List<String> getAll() {
        return jobService.findAll();
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody SchedulerJobInfo schedulerJobInfo) {
        jobService.createJob(schedulerJobInfo);

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
