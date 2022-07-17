package com.quartz.quartzexample.repository;

import com.quartz.quartzexample.dto.JobTrackingDTO;
import com.quartz.quartzexample.model.JobTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface QuartzJobTrackingRepository extends JpaRepository<JobTracking, Long>
{
    JobTracking findFirstByJobName(String jobName);

    @Query(
            value = "SELECT new com.quartz.quartzexample.dto.JobTrackingDTO( " +
                    "t.jobName, t.jobGroup, t.description, t.jobStatus, t.lastStartAt, t.lastFinishAt " +
                    ") FROM JobTracking t"
    )
    Set<JobTrackingDTO> getAll();
}
