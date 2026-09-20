package com.schedulerApi.service;

import com.common.enums.ScheduleType;
import com.schedulerApi.dto.ScheduleRequest;
import com.schedulerApi.dto.ScheduleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleJobServiceOrchestor {

    private ScheduleCreationService scheduleCreationService;
    private DuplicateScheduleJobService duplicateScheduleJobService;

    public ScheduleJobServiceOrchestor(ScheduleCreationService scheduleCreationService, DuplicateScheduleJobService duplicateScheduleJobService ){
        this.scheduleCreationService = scheduleCreationService;
        this.duplicateScheduleJobService = duplicateScheduleJobService;
    }

    public ScheduleResponse createSchedule(String idempotencyKey, ScheduleRequest request){

//        if(request.scheduleType() != ScheduleType.ONCE)
//            throw new IllegalArgumentException("only ONCE scheduletype is currently accepted");

      try{
         return scheduleCreationService.createSchedule(idempotencyKey,request);
      }catch (Exception ex){
          log.error("error occured: {}", ex.getMessage());
          return duplicateScheduleJobService.getSchedule(idempotencyKey,request);
      }

    }
}
