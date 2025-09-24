--liquibase formatted sql

--changeset abaquero:01
--comment: Se modifica la propiedad NOT NULL del # de fax del archivo AP
alter table PilaArchivoAPRegistro1 alter column ap1Fax bigint null