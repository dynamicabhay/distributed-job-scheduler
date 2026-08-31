package com.schedulerApi.exception;

import com.schedulerApi.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdempotencyKeyNotFound.class)
    public ResponseEntity<ApiErrorResponse> idempotencyKeyNotFoundExceptionHandler(IdempotencyKeyNotFound ex){
        ApiErrorResponse res = ApiErrorResponse.builder().code("IDEMPOTENCY_KEY_NOT_FOUND")
                .message(ex.getMessage())
                .createdAt(Instant.now())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateIdempotencyKey.class)
    public ResponseEntity<ApiErrorResponse> duplicateIdempotencyKeyExceptionHandler(DuplicateIdempotencyKey ex){
        ApiErrorResponse res = ApiErrorResponse.builder().code("DUPLICATE_IDEMPOTENCY_KEY")
                .message(ex.getMessage())
                .createdAt(Instant.now())
                .build();
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ScheduleJobNotFound.class)
    public ResponseEntity<ApiErrorResponse> scheduleJobNotFoundException(ScheduleJobNotFound ex){
        ApiErrorResponse res = ApiErrorResponse.builder().code("DUPLICATE_IDEMPOTENCY_KEY")
                .message(ex.getMessage())
                .createdAt(Instant.now())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


}
