--liquibase formatted sql

--changeset atoro:01
--comment: Se elimina campo de la tabla RegistroEstadoAporte_aud
ALTER TABLE RegistroEstadoAporte_aud DROP COLUMN reaEstadoInicialSolicitud;

--changeset jocorrea:02
--comment: Se modifica el tamaño del campo hrvValidacion para la tabla HistoriaResultadoValidacion_aud
ALTER TABLE HistoriaResultadoValidacion_aud ALTER COLUMN hrvValidacion varchar(100);

--changeset fvasquez:03
--comment: Se modifica campos de la tabla MovimientoAporte
ALTER TABLE MovimientoAporte_aud ALTER COLUMN moaAporteDetallado bigint NULL;
ALTER TABLE MovimientoAporte_aud ADD moaAporteGeneral bigint NOT NULL;