--liquibase formatted sql

--changeset arocha:1 comment:alter column redTipoIdentificacionCotizante
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redTipoIdentificacionCotizante VARCHAR(20) NOT NULL;