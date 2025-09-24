--liquibase formatted sql

--changeset ecastano:01
--comment: Se define valor por defecto a columna sldCantidadReintentos = 0
ALTER TABLE SolicitudLegalizacionDesembolso ADD CONSTRAINT DF_SolicitudLegalizacionDesembolso_sldCantidadReintentos DEFAULT 0 FOR sldCantidadReintentos;
