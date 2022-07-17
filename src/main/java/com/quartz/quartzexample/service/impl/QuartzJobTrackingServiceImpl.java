package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.repository.QuartzJobTrackingRepository;
import com.quartz.quartzexample.service.QuartzJobTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuartzJobTrackingServiceImpl implements QuartzJobTrackingService
{
    private final QuartzJobTrackingRepository quartzJobTrackingRepository;

    @Override
    public JobTracking save(JobTracking jobTracking) {
        return quartzJobTrackingRepository.save(jobTracking);
    }

    @Override
    public Set<JobTrackingDTO> getAllJobTracking() {
        return null;
    }


}
