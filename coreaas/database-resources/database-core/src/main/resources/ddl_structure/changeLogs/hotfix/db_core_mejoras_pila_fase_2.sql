--liquibase formatted sql

--changeset jocampo:01
--comment: 
ALTER TABLE pila.Aporte ADD appShardName VARCHAR(500)
