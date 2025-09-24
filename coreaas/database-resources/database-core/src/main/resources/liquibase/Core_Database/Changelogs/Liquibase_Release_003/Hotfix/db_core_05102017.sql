--liquibase formatted sql

--changeset abaquero:01
--comment: Se agrega campo en la tabla Infraestructura
ALTER TABLE Infraestructura ADD infCapacidadEstimada NUMERIC(7,2);