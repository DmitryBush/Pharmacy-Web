--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE news(
    id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    slug VARCHAR(64) NOT NULL UNIQUE,
    created_time TIMESTAMPTZ NOT NULL,
    title VARCHAR(64) NOT NULL,
    body TEXT NOT NULL
);

CREATE TABLE news_image(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    image_work_path VARCHAR(4096) NOT NULL
);

CREATE TABLE news_type(
    type VARCHAR(25) NOT NULL PRIMARY KEY
);