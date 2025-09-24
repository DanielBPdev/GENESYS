--liquibase formatted sql

--changeset ecastano:01
--comment: Se agrega columna sldCantidadReintentos a tabla SolicitudLegalizacionDesembolso_aud
ALTER TABLE SolicitudLegalizacionDesembolso_aud ADD sldCantidadReintentos SMALLINT NULL;
