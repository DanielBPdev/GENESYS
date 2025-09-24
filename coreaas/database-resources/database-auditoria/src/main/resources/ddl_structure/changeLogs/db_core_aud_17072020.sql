--liquibase formatted sql

--changeset amarin:01
--comment: CC FOVIS
ALTER TABLE RangoTopeValorSFV_aud ALTER COLUMN rtvValorMinimo NUMERIC(4,2) NOT NULL;
ALTER TABLE RangoTopeValorSFV_aud ALTER COLUMN rtvValorMaximo NUMERIC(4,2) NOT NULL;
ALTER TABLE CicloAsignacion_aud ADD ciaFechaAsignacionPlanificada DATE;