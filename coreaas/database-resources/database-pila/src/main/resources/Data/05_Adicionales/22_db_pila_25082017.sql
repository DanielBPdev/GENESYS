--liquibase formatted sql

--changeset arocha:01
--comment: Se adicionan campos en la tabla staging.RegistroDetallado y se crea la tabla staging.RegistroDetalladoNovedad
ALTER TABLE staging.RegistroDetallado ADD redOUTTipoNovedadSituacionPrimaria VARCHAR(15);
ALTER TABLE staging.RegistroDetallado ADD redOUTFechaInicioNovedadSituacionPrimaria DATE;
ALTER TABLE staging.RegistroDetallado ADD redOUTFechaFinNovedadSituacionPrimaria DATE;

CREATE TABLE staging.RegistroDetalladoNovedad(
	rdnId INT IDENTITY(1,1) NOT NULL,
	rdnRegistroDetallado BIGINT NOT NULL,
	rdnTipotransaccion VARCHAR(100),
	rdnTipoNovedad VARCHAR(15) NOT NULL,
	rdnAccionNovedad VARCHAR(20) NOT NULL,
	rdnMensajeNovedad VARCHAR(250),
	rdnFechaInicioNovedad DATE,
	rdnFechaFinNovedad DATE,
	CONSTRAINT PK_RegistroDetalladoNovedad_rndId PRIMARY KEY (rdnId)
);
ALTER TABLE staging.RegistroDetalladoNovedad ADD CONSTRAINT FK_RegistroDetalladoNovedad_rdnRegistroDetallado FOREIGN KEY (rdnRegistroDetallado) REFERENCES staging.RegistroDetallado (redId);