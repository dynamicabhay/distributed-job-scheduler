package com.schedulerApi.exception;

import com.schedulerApi.dto.ScheduleRequest;
import tools.jackson.databind.JsonNode;

public class MalformedPayloadException extends RuntimeException{
    public MalformedPayloadException(ScheduleRequest request){
        super("payload is not valid pls check and try again : " + request.payload());
    }
}
