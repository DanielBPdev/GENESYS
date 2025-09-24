--liquibase formatted sql

--changeset abaquero:01
--comment:  Se agrega campo en la tabla Infraestructura_aud
ALTER TABLE Infraestructura_aud ADD infCapacidadEstimada NUMERIC(7,2);