package com.worker.service;

import com.common.dto.JobExecutionEvent;

public interface JobExecutor {
    String getJobExecutorType();
    void execute(JobExecutionEvent event);
}
