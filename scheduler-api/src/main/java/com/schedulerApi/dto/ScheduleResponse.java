package com.schedulerApi.dto;

import com.common.enums.ScheduleStatus;

import java.time.Instant;
import java.util.UUID;

public record ScheduleResponse(
        ScheduleStatus status,
        UUID jobId,
        Instant nextRunAt
) {}
