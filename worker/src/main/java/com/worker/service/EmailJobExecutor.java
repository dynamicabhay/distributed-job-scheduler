package com.worker.service;

import com.common.dto.JobExecutionEvent;
import com.common.enums.ScheduleJobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class EmailJobExecutor implements JobExecutor{
    @Override
    public String getJobExecutorType() {
        return ScheduleJobType.EMAIL.name();
    }

    @Override
    public void execute(JobExecutionEvent event) {
        log.info("executing Email Job!!, payload: {}",event.payload());
    }
}
