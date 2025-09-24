--liquibase formatted sql

--changeset  mgiraldo:01 
--comment: cambio de tamaño de epaNombreContacto de la tabla EntidadPagadora
ALTER TABLE EntidadPagadora ALTER COLUMN epaNombreContacto VARCHAR(50);