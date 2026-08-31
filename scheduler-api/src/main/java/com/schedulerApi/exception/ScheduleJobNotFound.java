package com.schedulerApi.exception;

import java.util.UUID;

public class ScheduleJobNotFound extends RuntimeException{
    public ScheduleJobNotFound(UUID jobId){
        super("JobId: " + jobId + " not present in DB");
    }
}
