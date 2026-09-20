package com.outboxPublisher.service;

import com.common.domain.OutboxEvent;
import com.common.enums.OutboxEventType;
import com.common.repository.OutboxEventRepository;
import com.outboxPublisher.OutboxPublisherApplication;
import com.outboxPublisher.configProperties.OutboxPublisherProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class OutboxEventService {

    OutboxEventRepository outboxEventRepository;
    OutboxPublisherApplication app;
    OutboxPublisherProperties publisherProperties;
    public OutboxEventService(OutboxEventRepository outboxEventRepository, OutboxPublisherApplication app,OutboxPublisherProperties publisherProperties){
        this.outboxEventRepository = outboxEventRepository;
        this.app = app;
        this.publisherProperties = publisherProperties;
    }
    @Transactional
    public boolean claimEvent(UUID eventId, OutboxEventType status){
        log.info("claim event publisherId: {}",app.getPublisherId());
       int success= outboxEventRepository.claimEvent(status.name(), eventId,app.getPublisherId().toString(), publisherProperties.leaseDuration().getSeconds());
       return (success == 1);

    }

    @Transactional(readOnly = true)
    public OutboxEvent getOutboxEventById(UUID eventId){
        return outboxEventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("event not found by id: "+eventId ));
    }

    @Transactional
    public boolean markPublished(UUID eventId){
        log.info("mark publish publisherId: " + app.getPublisherId());
        int updated = outboxEventRepository.markEventPublished(app.getPublisherId().toString(),eventId);
        return updated == 1;
    }
}
