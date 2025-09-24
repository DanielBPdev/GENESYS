--liquibase formatted sql

--changeset abaquero:01
--comment: Se agrega campo para la cantidad de días de novedad en Core para el período de aporte
ALTER TABLE staging.RegistroDetallado ADD redOUTDiasCotizadosNovedadesBD smallint

--changeset abaquero:02
--comment: Se crea la tabla de área de trabajo para la discriminación de días de novedad por período y cotizante en core
CREATE TABLE staging.DiasNovedadCore (
	dncId bigint identity NOT NULL,
	dncIdNovedad bigint NOT NULL,
	dncTipoIdCotizante varchar(20),
	dncNumeroIdCotizante varchar(16),
	dncPeriodo varchar(7),
	dcnDiasNovedad smallint,
	CONSTRAINT PK_DiasNovedadCore PRIMARY KEY (dncId)
)

--changeset abaquero:03
--comment: Se agrega campo dncTransaccion en la tabla staging.DiasNovedadCore
ALTER TABLE staging.DiasNovedadCore ADD dncTransaccion bigint NOT NULL

--changeset abaquero:04
--comment: Corrección de nombre de campo
ALTER TABLE staging.DiasNovedadCore DROP COLUMN dcnDiasNovedad
ALTER TABLE staging.DiasNovedadCore ADD dncDiasNovedad smallint
