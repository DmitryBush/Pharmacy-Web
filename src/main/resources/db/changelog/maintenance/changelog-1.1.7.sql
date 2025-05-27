--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE orders ALTER COLUMN f_key_branch_id SET NOT NULL;