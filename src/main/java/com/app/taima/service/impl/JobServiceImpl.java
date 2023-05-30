package com.app.taima.service.impl;

import com.app.taima.Entity.SchedulerJob;
import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final SchedulerRepository schedulerRepository;

    @Override
    public void execute(String name, String group) {
        SchedulerJob schedulerJob = schedulerRepository.findByNameAndGroup(name, group);
        log.info(schedulerJob);
    }
}
