--liquibase formatted sql

--changeset jusanchez:01
--comment: Se actualiza campo en la tabla FieldDefinitionLoad
UPDATE FieldDefinitionLoad set identifierLine=0 WHERE id = 100000;