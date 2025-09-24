--liquibase formatted sql

--changeset ecastano:01
--comment: Se agrega columna sldCantidadReintentos a tabla SolicitudLegalizacionDesembolso
ALTER TABLE SolicitudLegalizacionDesembolso ADD sldCantidadReintentos SMALLINT NULL;
