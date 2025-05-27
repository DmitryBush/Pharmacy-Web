--liquibase formatted sql
--changeset Bushuev:1


ALTER TABLE medicine_image DROP COLUMN f_key_medicine_id;