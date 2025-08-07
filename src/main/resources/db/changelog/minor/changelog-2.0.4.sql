--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE branches_opening_hours(
    id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    branch_id bigint NOT NULL REFERENCES pharmacy_branches(branch_id),
    day_of_week SMALLINT NOT NULL CHECK(day_of_week BETWEEN 0 AND 6),
    open_time TIME NOT NULL,
    close_time TIME NOT NULL,
    UNIQUE(branch_id, day_of_week)
);

ALTER TABLE pharmacy_branches ADD COLUMN branch_phone VARCHAR(18),
    ADD COLUMN user_supervisor VARCHAR(18) REFERENCES customers(mobile_phone),
    ADD COLUMN is_active BOOLEAN DEFAULT TRUE NOT NULL;
