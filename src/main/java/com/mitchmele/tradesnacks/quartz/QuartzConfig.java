package com.mitchmele.tradesnacks.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(DailyPurgeJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Daily Purge Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Trigger")
                .withDescription("Sample trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
                .build();
    }
}
