--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE transaction_items
    ADD COLUMN amount INTEGER NOT NULL,
    ADD COLUMN price NUMERIC(10,2) NOT NULL;