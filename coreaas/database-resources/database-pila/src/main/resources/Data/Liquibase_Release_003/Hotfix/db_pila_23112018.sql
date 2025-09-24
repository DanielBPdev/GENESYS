--liquibase formatted sql

--changeset abaquero:01
--comment: Se remueven las restricciones de NOT NULL en primer apellido y nombre de rep legal en archivo A y AP
alter table PilaArchivoARegistro1 alter column pa1PrimerApellidoRep varchar(20)
alter table PilaArchivoARegistro1 alter column pa1PrimerNombreRep varchar(20)
alter table PilaArchivoAPRegistro1 alter column ap1PrimerApellidoRep varchar(20)
alter table PilaArchivoAPRegistro1 alter column ap1SegundoApellidoRep varchar(30)
alter table PilaArchivoAPRegistro1 alter column ap1PrimerNombreRep varchar(20) 