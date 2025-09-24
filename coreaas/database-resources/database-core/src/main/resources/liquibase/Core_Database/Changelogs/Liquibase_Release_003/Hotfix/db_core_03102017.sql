--liquibase formatted sql

--changeset abaquero:01
--comment: Se actualizan registro en las tablas ValidatorCatalog y ValidatorDefinition
UPDATE ValidatorCatalog SET [scope]='LINE' WHERE id=211039;
UPDATE ValidatorCatalog SET [scope]='LINE' WHERE id=211046;
UPDATE ValidatorCatalog SET [scope]='LINE' WHERE id=211047;
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=3 WHERE id=2110110;
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=8 WHERE id=2110214;
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=10 WHERE id=2110212;
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=10 WHERE id=2110213;

--changeset atoro:02
--comment: Se modifica el tamaño de un campo de la tabla Solicitud 
ALTER TABLE Solicitud ALTER COLUMN solCanalRecepcion VARCHAR (21) NULL;
