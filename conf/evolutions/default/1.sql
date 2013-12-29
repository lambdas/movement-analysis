# Create log_records table

# --- !Ups

CREATE TABLE log_records (
    id SERIAL PRIMARY KEY,
    device_id VARCHAR(255) NOT NULL,
    unix_time BIGINT NOT NULL,
    x DOUBLE PRECISION NOT NULL,
    y DOUBLE PRECISION NOT NULL
);

# --- !Downs

DROP TABLE log_records;
