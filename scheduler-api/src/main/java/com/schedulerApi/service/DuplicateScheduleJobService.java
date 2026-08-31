package com.schedulerApi.service;

import com.schedulerApi.domain.IdempotencyKey;
import com.schedulerApi.domain.ScheduleJob;
import com.schedulerApi.dto.ScheduleRequest;
import com.schedulerApi.dto.ScheduleResponse;
import com.schedulerApi.exception.DuplicateIdempotencyKey;
import com.schedulerApi.exception.IdempotencyKeyNotFound;
import com.schedulerApi.exception.ScheduleJobNotFound;
import com.schedulerApi.repository.IdempotencyKeyRepository;
import com.schedulerApi.repository.ScheduleJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DuplicateScheduleJobService {

    private IdempotencyKeyRepository idempotencyKeyRepository;
    private ScheduleJobRepository scheduleJobRepository;
    private RequestHashService requestHashService;

    public DuplicateScheduleJobService(IdempotencyKeyRepository idempotencyKeyRepository,ScheduleJobRepository scheduleJobRepository,RequestHashService requestHashService){
        this.idempotencyKeyRepository = idempotencyKeyRepository;
        this.scheduleJobRepository = scheduleJobRepository;
        this.requestHashService = requestHashService;
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(String idempotencyKey, ScheduleRequest request){
        String requestHash = requestHashService.generateHash(request);

        IdempotencyKey ik = idempotencyKeyRepository.findById(idempotencyKey).orElseThrow(
                () -> new IdempotencyKeyNotFound(idempotencyKey)
        );
        if(!requestHash.equals(ik.getRequestHash())){
            throw new DuplicateIdempotencyKey(idempotencyKey);
        }
        ScheduleJob savedJob = scheduleJobRepository.findById(ik.getJobId()).orElseThrow(
                () -> new ScheduleJobNotFound(ik.getJobId())
        );

        return new ScheduleResponse(savedJob.getStatus(),savedJob.getJobId(),savedJob.getNextRunAt());

    }
}
