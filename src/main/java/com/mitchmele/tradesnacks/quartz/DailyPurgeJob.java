package com.mitchmele.tradesnacks.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class DailyPurgeJob implements Job {

    private PurgeService purgeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        purgeService.executePricePurge(5.00);
    }
}
