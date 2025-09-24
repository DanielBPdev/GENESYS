--liquibase formatted sql

--changeset squintero:01
--comment: 
ALTER TABLE staging.RegistroDetallado
ADD redUsuarioAccion varchar(100);

ALTER TABLE staging.RegistroDetallado
ADD redFechaAccion datetime;