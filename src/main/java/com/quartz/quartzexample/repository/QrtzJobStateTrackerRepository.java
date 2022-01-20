package com.quartz.quartzexample.repository;

import com.quartz.quartzexample.model.QrtzJobStateTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrtzJobStateTrackerRepository extends JpaRepository<QrtzJobStateTracker, Long>
{
    QrtzJobStateTracker findFirstByJobName(String jobName);
}
