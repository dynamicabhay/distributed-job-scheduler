package com.schedulerApi.controller;

import com.schedulerApi.dto.ScheduleRequest;
import com.schedulerApi.dto.ScheduleResponse;
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

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ScheduleRequest request) {
            log.info("schedule request: " + request);
        return new ResponseEntity<>(scheduleJobService.createSchedule(idempotencyKey,request),HttpStatus.CREATED);

    }
}
