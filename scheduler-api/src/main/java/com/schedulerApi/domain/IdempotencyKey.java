package com.schedulerApi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
public class IdempotencyKey implements Persistable<String> {

    @Id
    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;

    @Column(name = "job_id", nullable = false, updatable = false)
    private UUID jobId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Transient
    private boolean isNew = true;

    public IdempotencyKey() {
    }


    @Override
    public @Nullable String getId() {
        return this.idempotencyKey;
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

    @Column(
            name = "request_hash",
            nullable = false,
            length = 64,
            updatable = false
    )
    private String requestHash;
}