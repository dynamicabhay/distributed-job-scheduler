package com.schedulerApi.dto;

import com.common.enums.ScheduleJobType;
import com.common.enums.ScheduleType;

import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public record ScheduleRequest (
        @NotNull
        ScheduleType scheduleType,
        @NotBlank
        String scheduleDefinition,

        @NotNull
        JsonNode payload,

        @NotNull ScheduleJobType jobType
){}
