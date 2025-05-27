--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE pharmacy_branches ADD COLUMN warehouse_limitation INT;