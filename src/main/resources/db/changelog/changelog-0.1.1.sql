--liquibase formatted sql
--changeset Bushuev:Added_news_types_constants

INSERT INTO news_type (type) VALUES ('Полезное'), ('События и мероприятия'), ('Акции'), ('Актуальное'),
    ('Новые поступления');