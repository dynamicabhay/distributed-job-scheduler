package com.schedulerApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.common.repository","com.schedulerApi.repository"})
@EntityScan(basePackages = {"com.common.domain","com.schedulerApi.domain"})
public class SchedulerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchedulerApiApplication.class,args);
    }
}
