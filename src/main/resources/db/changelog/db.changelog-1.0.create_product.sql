--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS category(
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO category (title)
VALUES ('Classic drink'),
       ('Author drink'),
       ('Seasonal drink'),
       ('Yummy menu');

CREATE TABLE IF NOT EXISTS product(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL,
    category_id INT REFERENCES category (id) ON DELETE CASCADE,
    price NUMERIC DEFAULT 0.0
    );



