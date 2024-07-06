--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS client(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64),
    birthday DATE,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP,
    is_active BOOLEAN DEFAULT FALSE,
    chat_id BIGSERIAL UNIQUE,
    registration_state VARCHAR(32)
    );


