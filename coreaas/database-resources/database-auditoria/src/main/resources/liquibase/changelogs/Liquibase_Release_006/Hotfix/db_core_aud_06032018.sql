--liquibase formatted sql

--changeset jocampo:01
--comment:Se modifica campo en la tabla ParametrizacionCondicionesSubsidio_aud
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ALTER COLUMN pcsCodigoCajaCompensacion VARCHAR(5) NULL;
