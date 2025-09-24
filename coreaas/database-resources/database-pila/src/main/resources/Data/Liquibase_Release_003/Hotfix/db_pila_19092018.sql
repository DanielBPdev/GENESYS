--liquibase formatted sql

--changeset abaquero:01
--comment: Se agregan campos para marcar la condición de grupo familiar reintegrable
ALTER TABLE staging.Cotizante ADD cotGrupoFamiliarReintegrable bit
ALTER TABLE staging.RegistroDetallado ADD redOUTGrupoFamiliarReintegrable bit
ALTER TABLE dbo.TemCotizante ADD tctGrupoFamiliarReintegrable bit