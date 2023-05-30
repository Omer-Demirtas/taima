package com.app.taima.service.impl;

import com.app.taima.Entity.Process;
import com.app.taima.Entity.SchedulerJob;
import com.app.taima.enums.ProcessType;
import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.JobService;
import com.app.taima.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final SchedulerRepository schedulerRepository;
    private final RestService restService;

    private void executeRestRequest() {

    }

    @Override
    public void execute(String name, String group) {
        log.info("name group {} {} ", name, group);
        SchedulerJob schedulerJob = schedulerRepository.findByNameAndGroup(name, group);
        log.info(schedulerJob);
        for (Process process : schedulerJob.getProcesses()) {
            log.info("pt {} ", process.getProcessType());
            if (process.getProcessType() == ProcessType.RestRequest) {
                String result = restService.get(process.getUrl());
                log.info("Result: {}", result);
            }
        }
    }
}
