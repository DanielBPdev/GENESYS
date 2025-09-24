--liquibase formatted sql

--changeset abaquero:01
--comment: Se elimina la constraint de no nulo en campo pi1Fax de PilaArchivoIRegistro1
ALTER TABLE dbo.PilaArchivoIRegistro1 ALTER COLUMN pi1Fax bigint null