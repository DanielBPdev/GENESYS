--liquibase formatted sql

--changeset mamonroy:02
--comment: Creación de indices
CREATE INDEX IX_RegistroDetallado_redRegralRedTIRedNI ON staging.RegistroDetallado (redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante ) WITH (ONLINE = ON);
