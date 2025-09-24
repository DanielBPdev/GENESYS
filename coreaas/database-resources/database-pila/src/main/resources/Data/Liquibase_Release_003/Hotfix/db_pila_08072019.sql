--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campo que clasifica al usuario específico del historíal de estados
ALTER TABLE HistorialEstadoBloque ADD hebClaseUsuario SMALLINT