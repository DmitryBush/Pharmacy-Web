--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE medicine_image ADD UNIQUE (image_path);