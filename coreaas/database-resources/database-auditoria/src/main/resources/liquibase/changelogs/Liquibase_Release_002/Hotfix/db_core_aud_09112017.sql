--liquibase formatted sql

--changeset anbuitrago:01
--comment: Se modifica tamaño de campo en la tabla GestionNotiNoEnviadas_aud
ALTER TABLE GestionNotiNoEnviadas_aud ALTER COLUMN gneEstadoGestion VARCHAR(25);