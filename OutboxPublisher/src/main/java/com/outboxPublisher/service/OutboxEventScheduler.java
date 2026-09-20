package com.outboxPublisher.service;

import ch.qos.logback.core.util.FixedDelay;
import com.common.repository.OutboxEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OutboxEventScheduler {

    OutboxEventRepository outboxEventRepository;
    OutboxEventOrchestratorService outboxEventOrchestratorService;
    public OutboxEventScheduler(OutboxEventRepository outboxEventRepository,
                                OutboxEventOrchestratorService outboxEventOrchestratorService)
    {

        this.outboxEventRepository = outboxEventRepository;
        this.outboxEventOrchestratorService = outboxEventOrchestratorService;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleOutboxEvents(){
        List<UUID> eventIds = outboxEventRepository.getPendingEvents(100);
        outboxEventOrchestratorService.processEvents(eventIds);

    }
}
