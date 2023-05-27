package com.app.taima.service;

import com.app.taima.Entity.SchedulerJobInfo;
import com.app.taima.component.JobScheduleCreator;
import com.app.taima.job.SampleCronJob;
import com.app.taima.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class JobService {
    private final Scheduler scheduler;
    private final SchedulerFactoryBean schedulerFactoryBean;

    private final SchedulerRepository schedulerRepository;

    private final ApplicationContext context;

    private final JobScheduleCreator scheduleCreator;

    public List<String> getAllJobs() {
        try {
            log.info("Groups: {}", scheduler.getJobGroupNames());
            log.info("METADA: {}", scheduler.getMetaData());
            return scheduler.getCalendarNames();
        } catch (Exception e) {
          log.error(e, e);
          return null;
        }
    }

    public List<String> getAllJobList() {
        return schedulerRepository.findAll().stream().map(schedulerJobInfo -> schedulerJobInfo.getJobName() + schedulerJobInfo.getJobGroup()).collect(Collectors.toList());
    }

    public void createJob(SchedulerJobInfo scheduleJob) {
        if (scheduleJob.getCronExpression().length() > 0) {
            scheduleJob.setJobClass(SampleCronJob.class.getName());
            scheduleJob.setCronJob(true);
        } else {
            /*
            scheduleJob.setJobClass(SimpleJob.class.getName());
            scheduleJob.setCronJob(false);
            scheduleJob.setRepeatTime((long) 1);
            */
        }
        if (StringUtils.isEmpty(scheduleJob.getJobId())) {
            log.info("Job Info: {}", scheduleJob);
            scheduleNewJob(scheduleJob);
        } else {
            //updateScheduleJob(scheduleJob);
        }
        scheduleJob.setDescription("i am job number " + scheduleJob.getJobId());
        scheduleJob.setInterfaceName("interface_" + scheduleJob.getJobId());
        log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " created.");
    }

    private void scheduleNewJob(SchedulerJobInfo jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder
                    .newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
            if (!scheduler.checkExists(jobDetail.getKey())) {

                jobDetail = scheduleCreator.createJob(
                        (Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
                        jobInfo.getJobName(), jobInfo.getJobGroup());

                Trigger trigger;
                if (jobInfo.getCronJob()) {
                    trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
                            jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                } else {
                    trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
                            jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                }
                scheduler.scheduleJob(jobDetail, trigger);
                jobInfo.setJobStatus("SCHEDULED");
                schedulerRepository.save(jobInfo);
                log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

}
