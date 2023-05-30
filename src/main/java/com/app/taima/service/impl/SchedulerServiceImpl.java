package com.app.taima.service.impl;

import com.app.taima.Entity.SchedulerJob;
import com.app.taima.component.JobScheduleCreator;
import com.app.taima.dto.JobDTO;
import com.app.taima.dto.MultiJobDTO;
import com.app.taima.exception.AlreadyExistsException;
import com.app.taima.exception.NotfoundException;
import com.app.taima.exception.OperationFailedException;
import com.app.taima.job.SampleCronJob;
import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.SchedulerJobService;
import com.app.taima.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.quartz.JobKey.jobKey;

@Log4j2
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final Scheduler scheduler;
    private final SchedulerFactoryBean schedulerFactoryBean;
    private final ApplicationContext context;
    private final JobScheduleCreator scheduleCreator;
    private final SchedulerJobService schedulerJobService;

    private void areJobsExists(MultiJobDTO jobs) {
        for (JobDTO job : jobs.getJobs()) {
            isJobExists(job);
        }
    }

    private void isJobExists(JobDTO job) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(job.getName(), job.getGroup()));

            if (jobDetail == null) {
                log.info("JOB NOT FOUND WITH {} {}", job.getName(), job.getGroup());
                throw new NotfoundException("job");
            }
        } catch (Exception e) {
            log.error(e, e);
            throw new NotfoundException("job");
        }
    }

    @Override
    /**
     * Get all jobs in scheduler
    */
    public List<JobDTO> getAllJob() {
        List<JobDTO> jobList = new ArrayList<>();

        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String name = jobKey.getName();
                    String group = jobKey.getGroup();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
                    jobList.add(new JobDTO(name, group));
                }
            }
        } catch (SchedulerException e) {
            log.error("Could not find all jobs due to error - {}", e.getLocalizedMessage());
        }

        return jobList;
    }

    /**
     * Create JOB
    */
    @Override
    @Transactional
    public boolean createJob(JobDTO job) {
        return scheduleNewJob(job);
    }

    @Override
    public boolean reScheduleJob(JobDTO job) {
        log.info("SchedulerService rescheduleByCron method started");

        JobKey jobKey = new JobKey(job.getName(), job.getGroup());

        try {
            Trigger trigger = scheduler.getTriggersOfJob(jobKey).get(0);

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup())
                    .withDescription(trigger.getDescription())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                    .build();

            scheduler.rescheduleJob(new TriggerKey(trigger.getKey().getName(), trigger.getKey().getGroup()), newTrigger);

        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        log.info("SchedulerService rescheduleByCron method finished successfully");
        return true;
    }

    @Override
    public boolean resumeJob(JobDTO job) {
        isJobExists(job);

        try {
            scheduler.resumeJob(jobKey(job.getName(), job.getGroup()));
            log.info("Resumed job with key - {}.{}", job.getName(), job.getGroup());
            return true;
        }
        catch (Exception e) {
            throw new OperationFailedException("jobResume");
        }
    }

    @Override
    public boolean pauseJob(JobDTO job) {
        isJobExists(job);

        try {
            scheduler.pauseJob(jobKey(job.getName(), job.getGroup()));
            log.info("Paused job with key | {} | {}", job.getName(), job.getGroup());
            return true;
        } catch (Exception e) {
            log.error(e, e);

            throw new OperationFailedException("jobPause");
        }
    }

    /**
     * Delete job
     */
    @Override
    @Transactional
    public boolean deleteJob(MultiJobDTO jobs) {
        areJobsExists(jobs);

        try {
            for (JobDTO job : jobs.getJobs()) {
                schedulerJobService.delete(job.getName(), job.getGroup());
                scheduler.deleteJob(new JobKey(job.getName(), job.getGroup()));

                log.info("Job deleted successfully with name {}, group {}", job.getName(), job.getGroup());
            }

            return true;
        }
        catch (Exception e) {
            log.error(e, e);

            throw new OperationFailedException("jobDelete");
        }
    }


    private boolean scheduleNewJob(JobDTO job) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            schedulerJobService.save(job);

            JobDetail jobDetail = JobBuilder
                    .newJob(SampleCronJob.class)
                    .withIdentity(job.getName(), job.getGroup()).build();

            if (!scheduler.checkExists(jobDetail.getKey())) {
                jobDetail = scheduleCreator.createJob(
                        SampleCronJob.class,
                        false,
                        context,
                        job.getName(), job.getGroup()
                );

                Trigger trigger;

                if (job.getIsCron()) {
                    trigger = scheduleCreator.createCronTrigger(job.getName(), new Date(),
                            job.getCron(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                } else {
                    trigger = scheduleCreator.createSimpleTrigger(job.getName(), new Date(),
                            job.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                }

                scheduler.scheduleJob(jobDetail, trigger);

                return true;
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
                throw new AlreadyExistsException("");
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException("job");
        }
    }
}
