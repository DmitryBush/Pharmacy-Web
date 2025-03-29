--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE roles
(
    role_id INT NOT NULL PRIMARY KEY generated always as identity,
    role_name VARCHAR(25) UNIQUE NOT NULL
);

CREATE TABLE user_roles
(
    id_user VARCHAR(15) NOT NULL REFERENCES customers(mobile_phone),
    id_role int NOT NULL REFERENCES roles(role_id),
    PRIMARY KEY(id_user, id_role)
);
