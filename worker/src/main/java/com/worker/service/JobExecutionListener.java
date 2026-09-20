package com.worker.service;

import com.common.configs.RabbitMQConfig;

import com.common.dto.JobExecutionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobExecutionListener {

    @RabbitListener(queues = RabbitMQConfig.JOB_QUEUE,containerFactory = "rabbitListenerContainerFactory")
    public void consume(JobExecutionEvent event){
        try {
            log.info("job is consumed by the worker: {}", event.payload());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
