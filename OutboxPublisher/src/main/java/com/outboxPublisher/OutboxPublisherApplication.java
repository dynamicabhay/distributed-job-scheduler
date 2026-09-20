package com.outboxPublisher;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@SpringBootApplication(scanBasePackages = {
        "com.outboxPublisher",
        "com.common"
})
@EnableScheduling
@Getter
@EnableJpaRepositories(basePackages = "com.common.repository")
@EntityScan(basePackages = "com.common.domain")
@ConfigurationPropertiesScan
public class OutboxPublisherApplication {

    private final UUID publisherId = UUID.randomUUID();
    public static void main(String[] args) {
        SpringApplication.run(OutboxPublisherApplication.class);
    }

}
