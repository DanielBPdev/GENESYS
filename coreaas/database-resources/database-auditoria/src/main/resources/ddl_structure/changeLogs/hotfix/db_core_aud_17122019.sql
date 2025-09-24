--liquibase formatted sql

--changeset mamonroy:01
----comment: Creación tablas aud

CREATE TABLE RetiroPersonaAutorizadaCobroSubsidio_aud(
	rpaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rpaPersonaAutorizada bigint NOT NULL,
	rpaCuentaAdministradorSubsidio bigint NOT NULL,
	rpaDocumentoSoporte bigint NULL
)

CREATE TABLE RegistroArchivoConsumosAnibol_aud(
	racId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	racArchivoConsumosAnibol bigint NOT NULL,
	racTipoRegistroArchivoConsumo varchar(16) NOT NULL,
	racCuentaAdministradorSubsidio bigint NULL,
	racFechaHoraCreacion datetime NOT NULL,
	racFechaHoraValidacion datetime NOT NULL,
	racBinCCF varchar(9) NOT NULL,
	racNumeroTarjeta varchar(19) NOT NULL,
	racNitEmpresa varchar(15) NOT NULL,
	racCuentaRelacionada varchar(19) NOT NULL,
	racDispositivoOrigen varchar(2) NOT NULL,
	racDescripcionCobroSubsidio varchar(30) NOT NULL,
	racDescripcionTransaccion varchar(30) NOT NULL,
	racValorTransaccion numeric(17, 2) NOT NULL,
	racValorDispensando numeric(17, 2) NOT NULL,
	racFechaTransaccion varchar(8) NOT NULL,
	racValorACobrar numeric(17, 2) NOT NULL,
	racValorImpuestos numeric(17, 2) NOT NULL,
	racTotalACobrar numeric(17, 2) NOT NULL,
	racImpuestoEmergenciaEconomica numeric(17, 2) NOT NULL,
	racIndicadorReverso char(1) NULL,
	racRespuestaAutorizador varchar(2) NOT NULL,
	racDescripcionRespuesta varchar(30) NOT NULL,
	racCodigoAutorizacion varchar(6) NOT NULL,
	racSubtipo varchar(3) NOT NULL,
	racFechaAutorizador varchar(8) NOT NULL,
	racHoraAutorizador varchar(9) NOT NULL,
	racHoraDispositivo varchar(6) NOT NULL,
	racNumeroReferencia varchar(12) NOT NULL,
	racRed varchar(4) NOT NULL,
	racNumeroDispositivo varchar(16) NOT NULL,
	racCodigoEstablecimiento varchar(10) NOT NULL,
	racCodigoCuentaBolsillo varchar(2) NOT NULL,
	racEstadoRegistro varchar(30) NULL,
	racTipoInconsistenciaResultadoValidacion varchar(34) NULL
)

CREATE TABLE ArchivoConsumosAnibol_aud(
	acnId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acnNombreArchivo varchar(40) NULL,
	acnIdentificadorDocumento varchar(255) NULL,
	acnVersionDocumento varchar(20) NULL,
	acnFechaHoraCargue datetime NOT NULL,
	acnUsuarioCargue varchar(50) NOT NULL,
	acnFechaHoraProcesamiento datetime NULL,
	acnUsuarioProcesamiento varchar(50) NULL,
	acnTipoCargue varchar(10) NOT NULL,
	acnEstadoArchivo varchar(29) NULL,
	acnResultadoValidacionEstructura varchar(20) NULL,
	acnResultadoValidacionContenido varchar(20) NULL,
	acnTipoInconsistenciaArchivo varchar(40) NULL,
	acnArchivoNotificado smallint NULL
)

