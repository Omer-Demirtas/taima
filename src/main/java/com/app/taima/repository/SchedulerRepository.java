package com.app.taima.repository;

import com.app.taima.Entity.SchedulerJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJob, Long> {

    SchedulerJob findByNameAndGroup(String name, String group);
}
