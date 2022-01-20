package com.quartz.quartzexample.controller;


import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qrtzTracker")
public class QrtzStateTrackerController
{
    private final QrtzJobStateTrackerService qrtzJobStateTrackerService;

    @GetMapping("/getUpdateJobState")
    public ResponseEntity<?> getUpdateJobState() {
        return ResponseEntity.ok(qrtzJobStateTrackerService.getUpdateJobState());
    }

}
