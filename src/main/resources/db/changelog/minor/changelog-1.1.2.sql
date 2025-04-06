--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE medicine_types
(
    type_id INTEGER NOT NULL PRIMARY KEY,
    type VARCHAR(64) NOT NULL UNIQUE
);

ALTER TABLE medicine DROP COLUMN medicine_type,
                     ADD COLUMN f_key_medicine_type INTEGER REFERENCES medicine_types(type_id)