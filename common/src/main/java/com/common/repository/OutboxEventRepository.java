package com.common.repository;

import com.common.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    @Query(value = """
        SELECT event_id
        FROM outbox_events
        WHERE
            (
               status = 'PENDING'
                AND (
                    lease_expires_at IS NULL
                    OR lease_expires_at < CURRENT_TIMESTAMP
                )
            )
            OR
            (
                status = 'PROCESSING'
                AND lease_expires_at < CURRENT_TIMESTAMP
            )
        ORDER BY created_at
        LIMIT :batchSize
        """, nativeQuery = true)
    List<UUID> getPendingEvents( @Param("batchSize") int batchSize);

    @Modifying
    @Query(value = """
    UPDATE outbox_events
    SET
        status = :status,
        publisher_id = :publisherId,
        lease_expires_at = CURRENT_TIMESTAMP + (:leaseSeconds * INTERVAL '1 second'),
        attempt_count = attempt_count + 1,
        updated_at = CURRENT_TIMESTAMP
    WHERE event_id = :eventId
      AND (
          status = 'PENDING'
          OR (
              status = 'PROCESSING'
              AND lease_expires_at < CURRENT_TIMESTAMP
          )
      )
    """, nativeQuery = true)
    int claimEvent(
            @Param("status") String status,
            @Param("eventId") UUID eventId,
            @Param("publisherId") String publisherId,
            @Param("leaseSeconds") long leaseSeconds
    );


    @Modifying
    @Query(value = """
    UPDATE outbox_events
    SET
        status = 'PUBLISHED',
        publisher_id = :publisherId,
        updated_at = CURRENT_TIMESTAMP,
        published_at = CURRENT_TIMESTAMP
    WHERE event_id = :eventId
      AND status = 'PROCESSING'
      AND publisher_id = :publisherId
      
    """, nativeQuery = true)
    int markEventPublished(@Param("publisherId") String publisherId, @Param("eventId") UUID eventId);



}
