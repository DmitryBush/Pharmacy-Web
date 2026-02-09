--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE news_image
    ADD COLUMN f_key_news_id bigint REFERENCES news(id),
    ADD CONSTRAINT p_key_id PRIMARY KEY (id);

ALTER TABLE news
    ADD COLUMN f_key_type VARCHAR(25) REFERENCES news_type(type),
    ALTER COLUMN title TYPE VARCHAR(255);