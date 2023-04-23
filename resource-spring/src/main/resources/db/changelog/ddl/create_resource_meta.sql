--liquibase formatted sql

--changeset vegura:pre_start

CREATE SEQUENCE IF NOT EXISTS resource_id_seq;
CREATE TABLE IF NOT EXISTS resource_meta (
    id bigint PRIMARY KEY DEFAULT nextval('resource_id_seq'),
    resource_key UUID,
    signature varchar(258),
    file_size int
)