package com.outboxPublisher.configProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;


@ConfigurationProperties(prefix = "scheduler.publisher")
public record OutboxPublisherProperties( Duration leaseDuration) {
}
