--liquibase formatted sql

--changeset atoro:01
--comment: Se modifica campos en la tabla ParametrizacionPreventiva_aud
ALTER TABLE ParametrizacionPreventiva_aud ALTER COLUMN pprDiasHabilesPrevios SMALLINT NULL;
ALTER TABLE ParametrizacionPreventiva_aud ALTER COLUMN pprHoraEjecucion DATETIME NULL;
