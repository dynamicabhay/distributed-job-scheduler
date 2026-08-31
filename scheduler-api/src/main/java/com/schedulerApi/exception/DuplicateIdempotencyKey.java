package com.schedulerApi.exception;

public class DuplicateIdempotencyKey extends RuntimeException{

    public DuplicateIdempotencyKey(String idempotencyKey){
        super("idempotencyKey: " + idempotencyKey + " is already present inside DB.");
    }
}
