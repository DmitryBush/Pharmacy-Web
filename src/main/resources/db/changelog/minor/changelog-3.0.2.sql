--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE daily_featured_products_changelog(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_at TIMESTAMPTZ DEFAULT(NOW())
);

CREATE TABLE daily_featured_products(
    id SMALLINT PRIMARY KEY NOT NULL,
    product_id bigint NOT NULL UNIQUE REFERENCES medicine(medicine_id)
);