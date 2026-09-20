package com.schedulerApi.controller;

import com.schedulerApi.dto.ScheduleRequest;
import com.schedulerApi.dto.ScheduleResponse;
import com.schedulerApi.exception.MalformedPayloadException;
import com.schedulerApi.service.JobPayloadValidator;
import com.schedulerApi.service.JobPayloadValidatorService;
import com.schedulerApi.service.ScheduleJobServiceOrchestor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedule")
@Slf4j
public class SchedulerController {

    @Autowired
    ScheduleJobServiceOrchestor scheduleJobService;

    @Autowired
    JobPayloadValidatorService jobPayloadValidatorService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ScheduleRequest request) {
            log.info("schedule request: " + request);
            boolean isValid = jobPayloadValidatorService.validate(request.jobType(),request.payload());
            if(!isValid)
              throw new MalformedPayloadException(request);
        return new ResponseEntity<>(scheduleJobService.createSchedule(idempotencyKey,request),HttpStatus.CREATED);
    }
}
