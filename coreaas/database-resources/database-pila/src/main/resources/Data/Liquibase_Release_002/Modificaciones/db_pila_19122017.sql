--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tabla AporteGeneral_aud
ALTER TABLE TemAportante ADD tapTipoDocTramitador VARCHAR(20) NULL;
ALTER TABLE TemAportante ADD tapIdTramitador VARCHAR(16) NULL;
ALTER TABLE TemAportante ADD tapDigVerTramitador SMALLINT NULL;
ALTER TABLE TemAportante ADD tapNombreTramitador VARCHAR(200) NULL;