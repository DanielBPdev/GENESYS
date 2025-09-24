--liquibase formatted sql

--changeset dsuesca:01
--comment: CC archivo tercero pagador efectivo

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
	tatSitioCobro bigint
);

--changeset dsuesca:02
--comment: CC archivo tercero pagador efectivo

CREATE TABLE ArchivoRetiroTerceroPagadorEfectivo (
	ateId bigint IDENTITY(1,1) NOT NULL,
	ateNombreArchivo varchar(40) NULL,
	ateIdentificadorDocumento varchar(255) NULL,
	ateVersionDocumento varchar(20) NULL,
	ateFechaHoraCargue datetime NOT NULL,
	ateUsuarioCargue varchar(50) NOT NULL,
	ateFechaHoraProcesamiento datetime NULL,
	ateUsuarioProcesamiento varchar(50) NULL,
	ateTipoCargue varchar(10) NOT NULL,
	ateEstadoArchivo varchar(29) NULL,
	ateResultadoValidacionEstructura varchar(20) NULL,
	ateResultadoValidacionContenido varchar(20) NULL,
	ateTipoInconsistenciaArchivo varchar(40) NULL,
	ateArchivoNotificado smallint NULL,
	CONSTRAINT PK_ArchivoRetiroTerceroPagadorEfectivo_ateId PRIMARY KEY (ateId)
);
