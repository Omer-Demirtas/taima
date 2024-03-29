package com.app.taima.service;

import com.app.taima.dto.JobDTO;
import com.app.taima.dto.MultiJobDTO;

import java.util.List;

public interface SchedulerService {

    List<JobDTO> getAllJob();

    boolean createJob(JobDTO job);

    boolean reScheduleJob(JobDTO job);

    boolean resumeJob(JobDTO job);

    boolean pauseJob(JobDTO job);

    boolean deleteJob(MultiJobDTO jobs);
}
