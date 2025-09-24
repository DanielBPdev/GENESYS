--liquibase formatted sql

--changeset atoro:01
--comment:Se modifica campos en la tabla ParametrizacionPreventiva
ALTER TABLE ParametrizacionPreventiva ALTER COLUMN pprDiasHabilesPrevios SMALLINT NULL;
ALTER TABLE ParametrizacionPreventiva ALTER COLUMN pprHoraEjecucion DATETIME NULL;
