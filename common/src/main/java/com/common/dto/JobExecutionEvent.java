package com.common.dto;

import com.common.enums.ScheduleJobType;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;

@Builder
public record JobExecutionEvent(UUID executionId,
                                UUID jobId,
                                Instant scheduledFor,
                                ScheduleJobType jobType,
                                JsonNode payload) {
}
