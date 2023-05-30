package com.app.taima.service.impl;

import com.app.taima.Entity.SchedulerJob;
import com.app.taima.dto.JobDTO;
import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.SchedulerJobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulerJobServiceImpl implements SchedulerJobService {
    private final ModelMapper modelMapper;
    private final SchedulerRepository schedulerRepository;
    @Override
    @Transactional
    public void save(JobDTO job) {
        SchedulerJob schedulerJob = modelMapper.map(job, SchedulerJob.class);

        schedulerJob.getProcesses().forEach(process -> process.setJob(schedulerJob));
        schedulerRepository.save(schedulerJob);
    }

    @Override
    public JobDTO getByNameAndGroup(String name, String group) {
        return modelMapper.map(schedulerRepository.findByNameAndGroup(name, group), JobDTO.class);
    }

    @Override
    public void delete(String name, String group) {
        schedulerRepository.deleteByNameAndGroup(name, group);
    }
}
