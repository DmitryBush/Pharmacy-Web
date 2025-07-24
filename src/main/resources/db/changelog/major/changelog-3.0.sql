--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE medicine_types ADD COLUMN parent_id INT REFERENCES medicine_types(type_id);

CREATE TABLE product_categories(
    product_id bigint NOT NULL REFERENCES medicine(medicine_id),
    category_id INT NOT NULL REFERENCES medicine_types(type_id),
    PRIMARY KEY(product_id, category_id)
);