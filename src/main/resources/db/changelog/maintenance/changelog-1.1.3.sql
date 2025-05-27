--liquibase formatted sql
--changeset Bushuev:1


ALTER TABLE medicine_types
    ALTER COLUMN type_id
    ADD GENERATED ALWAYS AS IDENTITY (
        START WITH 1
    );