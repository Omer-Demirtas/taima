package com.app.taima.controller;

import com.app.taima.dto.JobDTO;
import com.app.taima.service.SchedulerService;
import com.app.taima.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/scheduler")
public class SchedulerController {

    private final SchedulerService schedulerService;
    @GetMapping
    public GenericResponse<List<JobDTO>> getAll() {
        return GenericResponse.success(schedulerService.getAllJob());
    }


    @PostMapping
    public GenericResponse<Boolean> save(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.createJob(job));
    }

    @DeleteMapping
    public GenericResponse<Boolean> delete(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.deleteJob(job));
    }

    @PutMapping("/pause")
    public GenericResponse<Boolean> pause(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.pauseJob(job));
    }

    @PutMapping("/resume")
    public GenericResponse<Boolean> resume(@RequestBody JobDTO job) {
        return GenericResponse.success(schedulerService.resumeJob(job));
    }
}
