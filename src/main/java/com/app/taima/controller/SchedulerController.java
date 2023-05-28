package com.app.taima.controller;

import com.app.taima.dto.JobDTO;
import com.app.taima.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/scheduler")
public class SchedulerController {

    private final com.app.taima.service.SchedulerService schedulerService;
    @GetMapping
    public List<JobDTO> getAll() {
        return schedulerService.getAllJob();
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody JobDTO job) {
        schedulerService.createJob(job);

        return ResponseEntity.ok(Boolean.TRUE);
    }

    @DeleteMapping
    public GenericResponse<Boolean> delete(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.deleteJob(job));
    }

    @PutMapping("/pause")
    public GenericResponse<Boolean> pause(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.pauseJob(job));
    }
}
