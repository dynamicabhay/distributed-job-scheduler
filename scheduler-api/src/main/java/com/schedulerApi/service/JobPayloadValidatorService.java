package com.schedulerApi.service;

import com.common.enums.ScheduleJobType;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JobPayloadValidatorService {
    private final Map<String,JobPayloadValidator> payloadValidatorMap;

    public JobPayloadValidatorService(List<JobPayloadValidator> validators){
        payloadValidatorMap =  validators.stream().collect(Collectors.toMap(
                JobPayloadValidator::supportedJobType,
                Function.identity()
        ));
    }
    public boolean validate(ScheduleJobType jobType,JsonNode payload){
        if(jobType == null || payload == null) return false;
        JobPayloadValidator payloadValidator = payloadValidatorMap.get(jobType.name());
        return payloadValidator.validatePayload(payload);

    }
}
