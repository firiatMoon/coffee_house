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

CREATE TABLE IF NOT EXISTS unit(
     id SERIAL PRIMARY KEY NOT NULL,
     name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO unit (name)
VALUES ('psc'),
       ('ml'),
       ('gm')
;

CREATE TABLE IF NOT EXISTS menu_item(
      id BIGSERIAL PRIMARY KEY,
      product_id INT REFERENCES product (id) ON DELETE CASCADE,
      unit_id INT REFERENCES unit (id) ON DELETE CASCADE,
      quantity INT,
      price NUMERIC DEFAULT 0.0
);
