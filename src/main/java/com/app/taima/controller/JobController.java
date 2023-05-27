package com.app.taima.controller;

import com.app.taima.Entity.SchedulerJobInfo;
import com.app.taima.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/job")
public class JobController {

    private final SchedulerService schedulerService;
    @GetMapping
    public List<String> getAll() {
        return schedulerService.findAll();
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody SchedulerJobInfo schedulerJobInfo) {
        schedulerService.createJob(schedulerJobInfo);

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
