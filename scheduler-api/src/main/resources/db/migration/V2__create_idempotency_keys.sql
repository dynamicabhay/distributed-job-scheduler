CREATE TABLE idempotency_keys (
    idempotency_key VARCHAR(255) PRIMARY KEY,

    job_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_idempotency_job
        FOREIGN KEY (job_id)
        REFERENCES schedule_jobs(job_id)
);