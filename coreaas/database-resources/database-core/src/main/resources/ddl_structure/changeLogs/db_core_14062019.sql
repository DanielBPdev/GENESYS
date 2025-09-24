--liquibase formatted sql

--changeset jroa:01
--comment:
ALTER TABLE RegistroSolicitudAnibol ADD rsaIdProceso VARCHAR(20) NULL;
ALTER TABLE RegistroSolicitudAnibol ADD rsaSolicitudLiquidacionSubsidio BIGINT;
ALTER TABLE RegistroSolicitudAnibol ALTER COLUMN rsaParametrosIn VARCHAR(500) NULL; 