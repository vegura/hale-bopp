--liquibase formatted sql

--changeset illia_savenko:1

CREATE SEQUENCE IF NOT EXISTS song_id_seq START 1;
CREATE TABLE IF NOT EXISTS song (
    id bigint NOT NULL DEFAULT nextval('song_id_seq'),
    song_name varchar(255) NOT NULL,
    artist varchar(255) NOT NULL,
    album varchar(255),
    song_length bigint NOT NULL,
    resource_id varchar(255) NOT NULL,
    song_year varchar(5)
);