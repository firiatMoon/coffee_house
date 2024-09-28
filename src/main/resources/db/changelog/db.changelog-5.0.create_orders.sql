--liquibase formatted sql

--changeset veta:1
CREATE TABLE IF NOT EXISTS orders(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    client_id BIGINT REFERENCES client (id),
    user_id BIGINT NOT NULL REFERENCES person (id),
    bonus_points_action VARCHAR(5) CHECK (bonus_points_action IN ('USE', 'EARN') NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) NOT NULL,
    final_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_item(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    order_id BIGINT NOT NULL REFERENCES orders (id),
    menu_id BIGINT REFERENCES menu_item (id),
    quantity DECIMAL(5, 2) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS client_bonus_card_transaction(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    order_id BIGINT NOT NULL REFERENCES orders (id),
    transaction_type VARCHAR(10) CHECK (transaction_type IN ('DEBIT', 'CREDIT')) NOT NULL,
    points DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP
);
