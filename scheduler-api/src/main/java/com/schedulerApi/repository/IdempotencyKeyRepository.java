package com.schedulerApi.repository;

import com.schedulerApi.domain.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey,String> {
}
