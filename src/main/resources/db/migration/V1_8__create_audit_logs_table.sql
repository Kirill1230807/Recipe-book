CREATE TABLE audit_logs
(
    id          BIGSERIAL PRIMARY KEY,
    action      VARCHAR(50)  NOT NULL,
    entity_name VARCHAR(100) NOT NULL,
    entity_id   BIGINT,
    user_email  VARCHAR(255) NOT NULL,
    details     TEXT,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);