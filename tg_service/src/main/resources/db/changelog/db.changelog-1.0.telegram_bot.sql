--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS telegram_bot(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT UNIQUE,
    registration_state VARCHAR(32)
);

