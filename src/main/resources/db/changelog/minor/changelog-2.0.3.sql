--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE branch_user_assignment(
    user_id VARCHAR(18) NOT NULL REFERENCES customers(mobile_phone),
    branch_id bigint NOT NULL REFERENCES pharmacy_branches(branch_id),
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (user_id, branch_id)
);