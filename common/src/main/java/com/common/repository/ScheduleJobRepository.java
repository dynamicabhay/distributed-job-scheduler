package com.common.repository;

import com.common.domain.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, UUID> {

    @Query(value = """
        SELECT *
        FROM schedule_jobs
        WHERE status = :status
          AND next_run_at <= CURRENT_TIMESTAMP
        ORDER BY next_run_at
        LIMIT :batchSize
        FOR UPDATE SKIP LOCKED
        """, nativeQuery = true)
    List<ScheduleJob> getDueJobs(@Param("batchSize") int batchSize, @Param("status") String status);

    @Query(value = """
            select *
            FROM schedule_jobs
            where job_id = :uuid
            AND status = :status
            AND next_run_at <= CURRENT_TIMESTAMP
            FOR UPDATE SKIP LOCKED
            """,nativeQuery = true)
    Optional<ScheduleJob> findForUpdate(@Param("uuid") UUID uuid, @Param("status") String status);
}
