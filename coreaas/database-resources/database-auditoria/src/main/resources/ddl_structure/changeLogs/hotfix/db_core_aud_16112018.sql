--liquibase formatted sql

--changeset dsuesca:01
--comment: Cambio tipo de datos diferentes de core a core_aud
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaPorcentajePagoAportes NUMERIC(5,5);
ALTER TABLE SolicitudLegalizacionDesembolso_aud ALTER COLUMN sldEstadoSolicitud VARCHAR(60);
ALTER TABLE CargueArchivoActualizacion_aud ALTER COLUMN caaNombreArchivo VARCHAR(255);
ALTER TABLE CargueArchivoCruceFovis_aud ALTER COLUMN cacNombreArchivo VARCHAR(255);
ALTER TABLE MedioDePago_aud ALTER COLUMN mdpTipo VARCHAR(30);
