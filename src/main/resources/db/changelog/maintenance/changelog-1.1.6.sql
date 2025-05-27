--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE manufacturers ALTER COLUMN manufacturer_name TYPE VARCHAR(64);