--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE storage ADD UNIQUE(f_key_medicine_id, f_key_branch_id);

ALTER TABLE pharmacy_branches ADD COLUMN name VARCHAR(32);

CREATE TABLE branch_reservation(
    id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    amount INTEGER NOT NULL CHECK (amount > 0),
    reserved_at TIMESTAMPTZ NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL CHECK (expires_at > reserved_at),
    f_key_medicine_id BIGINT NOT NULL REFERENCES medicine(medicine_id),
    f_key_branch_id BIGINT NOT NULL REFERENCES pharmacy_branches(branch_id),
    f_key_customer_id VARCHAR(15) NOT NULL REFERENCES customers(mobile_phone),
    f_key_order_id BIGINT REFERENCES orders(order_id)
);

CREATE TABLE transaction_types(
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    transaction_name VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE transaction_history(
    id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    completed_at TIMESTAMPTZ NOT NULL CHECK (completed_at <= NOW()),
    f_key_transaction_type INT NOT NULL REFERENCES transaction_types(id),
    f_key_branch_id BIGINT NOT NULL REFERENCES pharmacy_branches(branch_id),
    f_key_order_id BIGINT REFERENCES orders(order_id)
);