package com.common.domain;

import com.common.enums.ExecutionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="executions")
@Getter
@Setter
@NoArgsConstructor
public class Execution implements Persistable<UUID> {

    @Id
    @Column(name = "execution_id", nullable = false)
    UUID executionId;

    @Column(name = "job_id", nullable = false)
    UUID jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    ExecutionStatus status;

    @Column(name = "scheduled_for", nullable = false)
    Instant scheduledFor;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;


    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;

    @Transient
    boolean isNew = true;


    @Override
    public @Nullable UUID getId() {
        return getExecutionId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    private void markNotNew(){
        this.isNew = false;
    }
}