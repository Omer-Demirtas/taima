package com.app.taima.service;

import com.app.taima.dto.JobDTO;

public interface SchedulerJobService {
    void save(JobDTO job);

    void delete(String name, String group);
}
