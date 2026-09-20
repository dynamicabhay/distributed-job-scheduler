package com.schedulerApi.service;

import com.common.enums.ScheduleStatus;
import com.common.repository.ScheduleJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class JobScheduler {

    JobSchedulerService jobSchedulerService;
    ScheduleJobRepository scheduleJobRepository;
    private static final int batchSize = 100;

    public JobScheduler(JobSchedulerService jobSchedulerService,ScheduleJobRepository scheduleJobRepository){
        this.jobSchedulerService = jobSchedulerService;
        this.scheduleJobRepository = scheduleJobRepository;
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduleJobs()
    {
        log.info("scheduler running....");
        try{
            jobSchedulerService.processPendingJobs(batchSize,ScheduleStatus.SCHEDULED.name());
        }catch (Exception ex){
            log.error("exception: ",ex);
        }
    }

}
