--liquibase formatted sql

--changeset dsuesca:02
--comment: CC archivo tercero pagador efectivo
DROP TABLE TempArchivoRetiroTerceroPagadorEfectivo;
CREATE TABLE TempArchivoRetiroTerceroPagadorEfectivo (
	tatId bigint IDENTITY(1,1) NOT NULL,
	tatIdConvenio bigint,
	tatLinea int,
	tatTipoIdentificacionAdmin varchar(20) NOT NULL,
	tatNumeroIdentificacionAdmin varchar(16) NOT NULL,
	tatIdTransaccion varchar(20),
	tatValorTransaccion numeric(19,5),
	tatFechaHoraTransaccion datetime,
	tatCodigoDepartamento varchar(2),
	tatCodigoMunicipio varchar(5),
	tatNombreCampo varchar(100),
	tatResultado varchar(20),
	tatMotivo varchar(500),
	tatSitioCobro bigint,
	tatArchivoRetiroTerceroPagadorEfectivo bigint,
	CONSTRAINT PK_TempArchivoRetiroTerceroPagadorEfectivo_tatId PRIMARY KEY (tatId)
);


--changeset dsuesca:03
--comment: CC archivo tercero pagador efectivo
CREATE TABLE ValidacionNombreArchivoTerceroPagador (
	vnaId bigint IDENTITY(1,1) NOT NULL,
	vnaArchivoRetiroTerceroPagadorEfectivo bigint NOT NULL,
	vnaValidacion varchar(20) NOT NULL,
	vnaResultado varchar(500) NOT NULL,
	CONSTRAINT PK_ValidacionNombreArchivoTerceroPagador_vnaId PRIMARY KEY (vnaId),
	CONSTRAINT FK_ValidacionNombreArchivoTerceroPagador_vnaArchivoRetiroTerceroPagadorEfectivo FOREIGN KEY (vnaArchivoRetiroTerceroPagadorEfectivo) REFERENCES ArchivoRetiroTerceroPagadorEfectivo(ateId)
);

--changeset dsuesca:04
--comment: CC archivo tercero pagador efectivo
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN ateResultadoCargueArchivo varchar(11);
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN ateMotivo varchar(29);
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN ateResultadoValidacionEstructura varchar(20);
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN ateResultadoValidacionContenido varchar(20);

ALTER TABLE ValidacionNombreArchivoTerceroPagador ALTER COLUMN vnaValidacion varchar(25);
ALTER TABLE ValidacionNombreArchivoTerceroPagador ALTER COLUMN vnaResultado varchar(11);