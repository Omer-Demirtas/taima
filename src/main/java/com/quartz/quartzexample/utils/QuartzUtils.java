package com.quartz.quartzexample.utils;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzUtils
{
    public  <T extends QuartzJobBean> JobDetail buildJobDetail(String jobName, String jobGroup, String description, Class<T> jobClass) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .withDescription(description)
                .storeDurably()
                .build();
    }

    public Trigger buildTrigger(JobDetail jobDetail, String triggerName, String triggerGroup, String description, String cron) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerName, triggerGroup)
                .withDescription(description)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }


    /*
        INFORMATION ABOUT FIRE COUNT

        for this method we can create a trigger with fire count, and more details.
        TimerInfo is a basic class with parameters;
            private int totalFireCount;
            private int remainingFireCount;
            private boolean runForever;
            private long repeatIntervalMs;
            private long initialOffsetMs;
            private String callbackData;

        if you create a job with fire count when it finishes you can run it again
        with this command;
           final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, timerInfo);
            final Trigger trigger = TimerUtils.buildTrigger(jobClass, timerInfo);

            try
            {
                scheduler.scheduleJob(jobDetail, trigger);
            }
    */

    /*
    public static Trigger buildTrigger(final Class jobClass, final TimerInfo timerInfo)
    {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(timerInfo.getRepeatIntervalMs());

        if(timerInfo.isRunForever())
        {
            builder = builder.repeatForever();
        }
        else
        {
            builder = builder.withRepeatCount(timerInfo.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis()  + timerInfo.getInitialOffsetMs()))
                .build();
    }*/
}
