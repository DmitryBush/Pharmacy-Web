--liquibase formatted sql
--changeset Bushuev:1


ALTER TABLE medicine DROP COLUMN medicine_image_id;

ALTER TABLE medicine_image ADD COLUMN f_key_medicine_id bigint REFERENCES medicine(medicine_id);