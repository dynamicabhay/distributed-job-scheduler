package com.schedulerApi.repository;

import com.schedulerApi.domain.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, UUID> {
}
