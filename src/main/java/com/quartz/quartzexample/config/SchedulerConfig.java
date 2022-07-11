package com.quartz.quartzexample.config;

import com.quartz.quartzexample.factory.SchedulerJobFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@RequiredArgsConstructor
public class SchedulerConfig
{
    private final DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        Properties properties = new Properties();
        properties.setProperty("org.quartz.jobStore.isClustered", "false");
        //Check in every 3 seconds
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "3000");
        //Can be any string, but must be unique for all schedulers working as if they are the same ‘logical’ Scheduler within a cluster.
        //You may use the value “AUTO” as the instanceId if you wish the Id to be generated for you.
        //Or the value “SYS_PROP” if you want the value to come from the system property “org.quartz.scheduler.instanceId”.
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        //JobStoreTX manages all transactions itself by calling commit() (or rollback()) on the database connection after every action
        //(such as the addition of a job). JDBCJobStore is appropriate if you are using Quartz in a stand-alone application,
        //or within a servlet container if the application is not using JTA transactions.
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        //properties.setProperty("org.quartz.jobStore.dataSource", "targetDataSource");


        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setSchedulerName("GalcReplicator-g4");

        return schedulerFactoryBean;
    }
}