--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS product(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL
    );
