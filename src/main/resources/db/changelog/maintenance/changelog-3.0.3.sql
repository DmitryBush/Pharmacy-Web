--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE transaction_items(
    f_key_product_id bigint REFERENCES medicine(medicine_id),
    f_key_transaction_id bigint REFERENCES transaction_history(id),
    PRIMARY KEY(f_key_product_id, f_key_transaction_id)
);