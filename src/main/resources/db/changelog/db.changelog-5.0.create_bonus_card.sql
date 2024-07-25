--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS bonus_card(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    client_id BIGINT UNIQUE REFERENCES client (id) ON DELETE CASCADE,
    amount NUMERIC,
    discount_percent INT DEFAULT 5 NOT NULL
    );


