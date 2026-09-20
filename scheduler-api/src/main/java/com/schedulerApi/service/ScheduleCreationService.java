package com.schedulerApi.service;

import com.common.domain.IdempotencyKey;
import com.common.domain.ScheduleJob;
import com.common.enums.ScheduleStatus;
import com.common.enums.ScheduleType;
import com.schedulerApi.dto.ScheduleRequest;
import com.schedulerApi.dto.ScheduleResponse;
import com.common.repository.IdempotencyKeyRepository;
import com.common.repository.ScheduleJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class ScheduleCreationService {
    private IdempotencyKeyRepository idempotencyKeyRepository;
    private ScheduleJobRepository scheduleJobRepository;
    private RequestHashService requestHashService;
    public ScheduleCreationService(IdempotencyKeyRepository idempotencyKeyRepository,ScheduleJobRepository scheduleJobRepository, RequestHashService hashService){
        this.idempotencyKeyRepository = idempotencyKeyRepository;
        this.scheduleJobRepository = scheduleJobRepository;
        this.requestHashService = hashService;
    }

    @Transactional
    public ScheduleResponse createSchedule(String idempotencyKey, ScheduleRequest request){

        // first we have to create/insert the job

        ScheduleJob job = new ScheduleJob();
        Instant nextRun = Instant.now();
        if(request.scheduleType() == ScheduleType.FIXED_INTERVAL)
        {
            nextRun.plus(Duration.parse(request.scheduleDefinition()));
        }
        else nextRun = Instant.parse(request.scheduleDefinition());

        log.info("nextRun: " + nextRun.toString());
        String requestHash = requestHashService.generateHash(request);
        job.setScheduleType(request.scheduleType());
        job.setScheduleJobType(request.jobType());
        job.setScheduleDefinition(request.scheduleDefinition());
        job.setJobId(UUID.randomUUID());
        job.setPayload(request.payload());
        job.setNextRunAt(nextRun);
        job.setStatus(ScheduleStatus.SCHEDULED);
        job.setCreatedAt(Instant.now());
        job.setUpdatedAt(Instant.now());

        ScheduleJob savedJob =  scheduleJobRepository.save(job);

        IdempotencyKey ik = new IdempotencyKey();
        ik.setJobId(job.getJobId());
        ik.setRequestHash(requestHash);
        ik.setIdempotencyKey(idempotencyKey);
        ik.setCreatedAt(Instant.now());
        idempotencyKeyRepository.saveAndFlush(ik);

        return new ScheduleResponse(savedJob.getStatus(),savedJob.getJobId(),savedJob.getNextRunAt());

    }
}
