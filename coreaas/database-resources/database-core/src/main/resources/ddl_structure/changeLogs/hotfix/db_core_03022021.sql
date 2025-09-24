--liquibase formatted sql

--changeset erhernandez:01
--comment:

UPDATE FieldDefinitionLoad set required=0 where id='2110056';