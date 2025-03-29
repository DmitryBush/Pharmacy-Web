--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE medicine_image
(
    image_id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    image_path VARCHAR(512) NOT NULL,
    f_key_medicine_id bigint,
    FOREIGN KEY (f_key_medicine_id) REFERENCES medicine(medicine_id)
);

CREATE TABLE country
(
    country_id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    country VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE manufacturers
(
    manufacturer_id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    f_key_country_id bigint NOT NULL,
    FOREIGN KEY (f_key_country_id) REFERENCES country(country_id)
);

ALTER TABLE customers ADD COLUMN password VARCHAR(256);

ALTER TABLE medicine
    ADD COLUMN active_ingredient VARCHAR(25),
    ADD COLUMN expiration VARCHAR(20),
    ADD COLUMN composition TEXT,
    ADD COLUMN indications TEXT,
    ADD COLUMN contraindications TEXT,
    ADD COLUMN side_effects TEXT,
    ADD COLUMN interaction TEXT,
    ADD COLUMN admission_course TEXT,
    ADD COLUMN overdose TEXT,
    ADD COLUMN release_form TEXT,
    ADD COLUMN special_instructions TEXT,
    ADD COLUMN storage_conditions TEXT,
    ADD COLUMN medicine_image_id bigint,
    ADD CONSTRAINT f_key_medicine_image_id FOREIGN KEY (medicine_image_id) REFERENCES medicine_image(image_id),
    DROP COLUMN medicine_manufacturer,
    ADD COLUMN fk_medicine_manufacturer bigint,
    ADD CONSTRAINT f_key_medicine_manufacturer FOREIGN KEY (fk_medicine_manufacturer) REFERENCES manufacturers(manufacturer_id);
