package com.database;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.URL;
import java.util.Enumeration;

@SpringBootApplication(scanBasePackages = {"com.common,com.database"})
@EnableJpaRepositories(basePackages = "com.common.repository")
@EntityScan(basePackages = "com.common.domain")

public class DatabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class);
    }

    @PostConstruct
    public void debugFlywayResources() throws Exception {

        ClassLoader classLoader =

                Thread.currentThread().getContextClassLoader();

        System.out.println("========== DEBUG ==========");

        System.out.println(

                "application.yml = " +

                        classLoader.getResource("application.yml")

        );

        System.out.println(

                "V5 = " +

                        classLoader.getResource(

                                "db/migration/V5__add_jobType_to_schedule_jobs.sql"

                        )

        );

        System.out.println("===========================");
    }


}


