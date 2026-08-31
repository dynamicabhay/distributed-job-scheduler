package com.schedulerApi.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiErrorResponse(String code, String message, Instant createdAt) {
}
