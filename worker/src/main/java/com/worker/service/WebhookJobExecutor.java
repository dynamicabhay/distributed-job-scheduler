package com.worker.service;

import com.common.dto.JobExecutionEvent;
import com.common.enums.ScheduleJobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class WebhookJobExecutor implements JobExecutor{
    @Override
    public String getJobExecutorType() {
        return ScheduleJobType.WEBHOOK.name();
    }

    @Override
    public void execute(JobExecutionEvent event) {
        log.info("executing webhook job");
    }
}
