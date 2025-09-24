--liquibase formatted sql

--changeset clmarin:01
--comment: Modificacion de tamaño del campo en la tabla 
ALTER TABLE RegistroEstadoAporte ALTER COLUMN reaUsuario varchar (255) NULL;