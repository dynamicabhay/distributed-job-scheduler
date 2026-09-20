package com.outboxPublisher.service;

import com.common.domain.OutboxEvent;
import com.common.enums.OutboxEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OutboxEventOrchestratorService {

    OutboxEventService outboxEventService;
    RabbitMQPublisher rabbitMQPublisher;
    public OutboxEventOrchestratorService(OutboxEventService outboxEventService,RabbitMQPublisher rabbitMQPublisher){
        this.outboxEventService = outboxEventService;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    public void processEvents(List<UUID> eventIds){
            for (UUID eventId : eventIds) {
                try{
                    //1. claim the event
                    boolean success = outboxEventService.claimEvent(eventId, OutboxEventType.PROCESSING);
                    if(!success) continue;

                    // 2. get the event
                    OutboxEvent event = outboxEventService.getOutboxEventById(eventId);

                    //3. send out the payload to message queue
                    rabbitMQPublisher.publish(event);

                    //4. mark the event as published
                    outboxEventService.markPublished(eventId);

                    log.info("event is published with Id : {}",event.getEventId());

                }catch (Exception e){
                    log.info(e.getMessage());
                }



            }
    }
}
