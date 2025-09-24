--liquibase formatted sql

--changeset clmarin:01
--comment:
ALTER TABLE AporteDetallado add apdModificadoAportesOK bit null;
ALTER TABLE aud.AporteDetallado_aud add apdModificadoAportesOK bit null;