package com.schedulerApi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "schedule_jobs")
@Getter
@Setter
public class ScheduleJob implements Persistable<UUID> {
    @Id
    @Column(name = "job_id", nullable = false, updatable = false)
    private UUID jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false, length = 50)
    private ScheduleType scheduleType;

    @Column(name = "schedule_definition", nullable = false, length = 500)
    private String scheduleDefinition;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private JsonNode payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ScheduleStatus status;

    @Column(name = "next_run_at", nullable = false)
    private Instant nextRunAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Transient
    private boolean isNew = true;

    public ScheduleJob() {
        // Required by JPA
    }

    @Override
    public @Nullable UUID getId() {
        return jobId;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PostLoad
    @PostPersist
    private void markNotNew(){
        this.isNew = false;
    }
}
