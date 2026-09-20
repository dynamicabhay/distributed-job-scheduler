package com.common.domain;

import com.common.enums.OutboxEventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEvent implements Persistable<UUID> {

    @Id
    @Column(name = "event_id ",nullable = false)
    UUID eventId;

    @Column(name=" execution_id ",nullable = false)
    UUID executionId;

    @Column(name ="event_type " ,nullable = false)
    String eventType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private JsonNode payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    OutboxEventType status;

    @Column(name="publisher_id")
    String publisherId;

    @Column(name="lease_expires_at")
    Instant leaseExpiresAt;

    @Column(name="attempt_count",nullable = false)
    Integer attemptCount;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;

    @Transient
    boolean isNew = true;


    @Override
    public @Nullable UUID getId() {
        return getEventId();
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
