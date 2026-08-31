package com.schedulerApi.dto;

import com.schedulerApi.domain.ScheduleStatus;

import java.time.Instant;
import java.util.UUID;

public record ScheduleResponse(
        ScheduleStatus status,
        UUID jobId,
        Instant nextRunAt
) {}
