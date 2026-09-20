CREATE TABLE schedule_jobs (
    job_id UUID PRIMARY KEY,

    schedule_type VARCHAR(50) NOT NULL,
    schedule_definition VARCHAR(500) NOT NULL,

    payload JSONB NOT NULL,

    status VARCHAR(50) NOT NULL,

    next_run_at TIMESTAMPTZ NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);