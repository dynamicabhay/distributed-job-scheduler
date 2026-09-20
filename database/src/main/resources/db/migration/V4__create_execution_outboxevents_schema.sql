CREATE TABLE executions (
    execution_id UUID PRIMARY KEY,

    job_id UUID NOT NULL,

    status VARCHAR(50) NOT NULL,

    scheduled_for TIMESTAMPTZ NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_execution_job
        FOREIGN KEY (job_id)
        REFERENCES schedule_jobs(job_id),

    CONSTRAINT uq_execution_job_scheduled_for
        UNIQUE (job_id, scheduled_for)
);

CREATE TABLE outbox_events (
    event_id UUID PRIMARY KEY,

    execution_id UUID NOT NULL,

    event_type VARCHAR(100) NOT NULL,

    payload JSONB NOT NULL,

    status VARCHAR(50) NOT NULL,

    publisher_id VARCHAR(100),

    lease_expires_at TIMESTAMPTZ,

    attempt_count INTEGER NOT NULL DEFAULT 0,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    published_at TIMESTAMPTZ,

    CONSTRAINT fk_outbox_execution
        FOREIGN KEY (execution_id)
        REFERENCES executions(execution_id),

    CONSTRAINT uq_outbox_execution
        UNIQUE (execution_id)
);