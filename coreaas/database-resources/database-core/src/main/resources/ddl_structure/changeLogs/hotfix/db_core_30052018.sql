--liquibase formatted sql

--changeset jocorrea:01
--comment: actualizacion tabla FieldDefinitionLoad
UPDATE FieldDefinitionLoad SET required = 0 WHERE  lineDefinition_Id = 1222 AND label IN ('Segundo nombre', 'Segundo apellido');

