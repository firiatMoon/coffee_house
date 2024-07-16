--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS bonus_card(
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT,
    amount NUMERIC,
    discount_percent INT DEFAULT 0
    );


