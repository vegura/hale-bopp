--liquibase formatted sql

--changeset illia_savenko:2

CREATE INDEX resource_id_idx ON song (resource_id);
