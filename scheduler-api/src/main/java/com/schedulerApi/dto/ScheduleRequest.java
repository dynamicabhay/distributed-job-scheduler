package com.schedulerApi.dto;

import com.schedulerApi.domain.ScheduleType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public record ScheduleRequest (
        @NotNull
        ScheduleType scheduleType,
        @NotBlank
        String scheduleDefinition,

        @NotNull
        JsonNode payload
){}
