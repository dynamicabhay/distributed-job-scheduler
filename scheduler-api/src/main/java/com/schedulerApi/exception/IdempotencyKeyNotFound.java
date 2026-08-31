package com.schedulerApi.exception;

public class IdempotencyKeyNotFound extends RuntimeException{
    public IdempotencyKeyNotFound(String idempotencyKey){
        super("IdempotencyKey: " + idempotencyKey + " not present in DB");
    }
}
