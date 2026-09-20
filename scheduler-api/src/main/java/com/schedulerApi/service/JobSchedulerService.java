package com.schedulerApi.service;

import com.common.domain.Execution;
import com.common.domain.OutboxEvent;
import com.common.domain.ScheduleJob;
import com.common.enums.*;
import com.common.dto.JobExecutionEvent;
import com.common.repository.ExecutionRepository;
import com.common.repository.OutboxEventRepository;
import com.common.repository.ScheduleJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class JobSchedulerService {
    ExecutionRepository executionRepository;
    ScheduleJobRepository scheduleJobRepository;
    OutboxEventRepository outboxEventRepository;
    ObjectMapper objectMapper;

    public JobSchedulerService(ExecutionRepository executionRepository, ScheduleJobRepository scheduleJobRepository, OutboxEventRepository outboxEventRepository,ObjectMapper objectMapper){
        this.executionRepository = executionRepository;
        this.scheduleJobRepository = scheduleJobRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void processJob(int batchSize, UUID uuid){
       ScheduleJob job = scheduleJobRepository.findForUpdate(uuid, ScheduleStatus.SCHEDULED.name())
               .orElse(null);

       if(job == null) return;
       if(job.getStatus() != ScheduleStatus.SCHEDULED) return;
       if(job.getNextRunAt().isAfter(Instant.now())) return ;


        // create execution
        Execution execution = this.createExecution(job);
        OutboxEvent outboxEvent = this.crateOutboxEvent(job,execution);
        executionRepository.save(execution);
        outboxEventRepository.save(outboxEvent);
        if(job.getScheduleType() == ScheduleType.ONCE){
            job.setStatus(ScheduleStatus.DISPATCHED);
        }
        else if(job.getScheduleType() == ScheduleType.FIXED_INTERVAL){
          Duration duration =  Duration.parse(job.getScheduleDefinition());
          job.setNextRunAt(job.getNextRunAt().plus(duration));
        }

    }

    private Execution createExecution(ScheduleJob job){
        Execution execution = new Execution();
        execution.setExecutionId(UUID.randomUUID());
        execution.setJobId(job.getJobId());
        execution.setStatus(ExecutionStatus.PENDING);
        execution.setScheduledFor(job.getNextRunAt());
        execution.setCreatedAt(Instant.now());
        execution.setUpdatedAt(Instant.now());

        return execution;
    }

    private OutboxEvent crateOutboxEvent(ScheduleJob job,Execution execution){
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setEventId(UUID.randomUUID());
        outboxEvent.setEventType("JOB_EXECUTION_REQUESTED");
        outboxEvent.setStatus(OutboxEventType.PENDING);
        outboxEvent.setExecutionId(execution.getExecutionId());
        outboxEvent.setCreatedAt(Instant.now());
        outboxEvent.setUpdatedAt(Instant.now());
        outboxEvent.setAttemptCount(0);
        JobExecutionEvent jobExecutionEvent = JobExecutionEvent.builder().jobId(job.getJobId())
                .scheduledFor(job.getNextRunAt())
                .executionId(execution.getExecutionId())
                .jobType(job.getScheduleJobType())
                .payload(objectMapper.valueToTree(job.getPayload()))
                .build();
        outboxEvent.setPayload(objectMapper.valueToTree(jobExecutionEvent));

        return outboxEvent;
    }


    @Transactional
    public void processPendingJobs(int batchSize, String status){
        List<ScheduleJob> pendingJobList = scheduleJobRepository.getDueJobs(batchSize,status);
        List<OutboxEvent> outboxEvents = new ArrayList<>();
        List<Execution> executionList = new ArrayList<>();
        for(ScheduleJob job : pendingJobList){
            // create execution
            Execution execution = this.createExecution(job);
            // create outbox-event
            OutboxEvent outboxEvent = this.crateOutboxEvent(job,execution);

            advanceJobSchedule(job);

            outboxEvents.add(outboxEvent);
            executionList.add(execution);


        }
        executionRepository.saveAll(executionList);
        outboxEventRepository.saveAll(outboxEvents);

    }

    private void advanceJobSchedule(ScheduleJob job){
        ScheduleType scheduleType = job.getScheduleType();
         switch(scheduleType){
            case ONCE -> job.setStatus(ScheduleStatus.DISPATCHED);
            case FIXED_INTERVAL -> {
                 Duration duration =  Duration.parse(job.getScheduleDefinition());
                 job.setNextRunAt(job.getNextRunAt().plus(duration));
             }
             case CRON -> {}
        }
    }
}
