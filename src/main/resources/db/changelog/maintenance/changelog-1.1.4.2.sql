--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE medicine_image ALTER COLUMN f_key_medicine_id SET NOT NULL;