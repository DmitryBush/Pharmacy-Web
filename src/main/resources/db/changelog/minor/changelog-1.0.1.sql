--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE manufacturers ADD COLUMN manufacturer_name VARCHAR(25);