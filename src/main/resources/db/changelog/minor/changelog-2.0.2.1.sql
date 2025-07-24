--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE user_roles ALTER COLUMN id_user TYPE VARCHAR(18);