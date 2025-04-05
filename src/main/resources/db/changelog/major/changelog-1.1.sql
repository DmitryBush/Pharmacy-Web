--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE carts
(
    cart_id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    f_key_customer_id VARCHAR(15) NOT NULL REFERENCES customers(mobile_phone)
);

ALTER TABLE orders ADD COLUMN f_key_cart_id bigint REFERENCES carts(cart_id);

ALTER TABLE cart_items DROP COLUMN f_key_order_id,
    ADD COLUMN f_key_cart_id bigint REFERENCES carts(cart_id);