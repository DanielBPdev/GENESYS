--liquibase formatted sql

--changeset abaquero:01
--comment: Adici�n de campo que clasifica al usuario espec�fico del histor�al de estados
ALTER TABLE HistorialEstadoBloque ADD hebClaseUsuario SMALLINT