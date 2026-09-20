package com.worker.service;

import com.common.dto.JobExecutionEvent;
import com.common.enums.ScheduleJobType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JobExecutorservice {
    private final Map<String, JobExecutor> jobExecutorMap;
    public  JobExecutorservice(List<JobExecutor> executorList){
       jobExecutorMap = executorList.stream().collect(Collectors.toMap(
                JobExecutor::getJobExecutorType,
                Function.identity()
        ));
    }

    public void executeJob(JobExecutionEvent event){
        JobExecutor jobExecutor = jobExecutorMap.get(event.jobType().name());
        jobExecutor.execute(event);
    }

}
