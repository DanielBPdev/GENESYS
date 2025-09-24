--liquibase formatted sql

--changeset  mgiraldo:01
--comment:Cambio de tamaño  empEstadoEmpleador Empleador
ALTER TABLE Empleador ALTER COLUMN empEstadoEmpleador varchar(50);


