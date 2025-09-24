--liquibase formatted sql

--changeset arocha:01
--comment: Se adiciona campo en la tabla staging.RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redOUTTipoAfiliado VARCHAR(50);

--changeset abaquero:02
--comment: Se adiciona campos en las tablas  PilaIndicePlanilla y staging.RegistroDetallado
ALTER TABLE dbo.PilaIndicePlanilla ADD pipHabilitadoProcesoManual bit;
ALTER TABLE staging.RegistroDetallado ADD redOUTRegistrado bit;