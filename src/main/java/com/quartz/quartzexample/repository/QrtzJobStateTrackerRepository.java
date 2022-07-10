package com.quartz.quartzexample.repository;

import com.quartz.quartzexample.model.JobTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzJobStateTrackerRepository extends JpaRepository<JobTracking, Long>
{
    JobTracking findFirstByJobName(String jobName);
}
