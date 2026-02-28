--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS person(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(32) NOT NULL UNIQUE,
    birthday DATE NOT NULL,
    created_at TIMESTAMP,
    is_active BOOLEAN DEFAULT FALSE,
    role VARCHAR(32) NOT NULL
    );


