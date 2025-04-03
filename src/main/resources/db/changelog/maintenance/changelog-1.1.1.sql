--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE order_items
(
    obj_id bigint PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY,
    obj_amount int DEFAULT 1,
    obj_price NUMERIC(10,2) NOT NULL,
    f_key_medicine_id bigint NOT NULL REFERENCES medicine(medicine_id),
    f_key_order_id bigint NOT NULL REFERENCES orders(order_id)
);

ALTER TABLE orders DROP COLUMN f_key_cart_id;

ALTER TABLE carts ADD UNIQUE(f_key_customer_id);
