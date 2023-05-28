package com.app.taima.service.impl;

import com.app.taima.Entity.SchedulerJobInfo;
import com.app.taima.component.JobScheduleCreator;
import com.app.taima.dto.JobDTO;
import com.app.taima.exception.AlreadyExistsException;
import com.app.taima.exception.OperationFailedException;
import com.app.taima.job.SampleCronJob;
import com.app.taima.repository.SchedulerRepository;
import com.app.taima.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private final SchedulerRepository schedulerRepository;

    private final ApplicationContext context;

    private final JobScheduleCreator scheduleCreator;

    private List<String> getAllJobList() {
        return schedulerRepository.findAll().stream().map(schedulerJobInfo -> schedulerJobInfo.getJobName() + schedulerJobInfo.getJobGroup()).collect(Collectors.toList());
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

    @Override
    /**
     * Create JOB
    */
    public boolean createJob(JobDTO job) {
        return scheduleNewJob(job);
    }

    @Override
    public boolean reScheduleJob(JobDTO job) {
        return false;
    }

    @Override
    public boolean resumeJob(JobDTO job) {
        return false;
    }

    @Override
    public boolean pauseJob(JobDTO job) {
        return false;
    }

    @Override
    public boolean deleteJob(JobDTO job) {
        try {
            scheduler.deleteJob(new JobKey(job.getName(), job.getGroup()));

            log.info("Job deleted successfully with name {}, group {}", job.getName(), job.getGroup());
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
                //jobInfo.setJobStatus("SCHEDULED");
                //schedulerRepository.save(jobInfo);
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
