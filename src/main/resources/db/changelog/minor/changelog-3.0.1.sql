--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE product_categories ADD COLUMN is_main BOOLEAN DEFAULT TRUE;