--liquibase formatted sql

--changeset silopez:01
--comment: 
UPDATE FieldLoadCatalog SET dataType = 'STRING' where id = 2110144 and name = 'archivoIregistro2campo46';