--liquibase formatted sql

--changeset jocampo:01
--comment:Se modifica campo en la tabla ParametrizacionCondicionesSubsidio
ALTER TABLE ParametrizacionCondicionesSubsidio ALTER COLUMN pcsCodigoCajaCompensacion VARCHAR(5) NULL;

--changeset flopez:02
--comment: Se elimina llave unica en la tabla Licencia
ALTER TABLE Licencia DROP CONSTRAINT UK_Licencia_licMatriculaInmobiliaria;
