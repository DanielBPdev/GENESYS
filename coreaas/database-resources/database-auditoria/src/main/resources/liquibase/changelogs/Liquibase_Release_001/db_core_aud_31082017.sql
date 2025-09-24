--liquibase formatted sql

--changeset clmarin:01
--comment: Creacion de la tabla MovimientoAportante y RegistroEstadoAporte, y la eliminacion de la tabla Trazabilidad

--Creacion de la tabla MovimientoAporte_aud
CREATE TABLE MovimientoAporte_aud(
	moaId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	moaTipoAjuste varchar(20) NULL,
	moaTipoMovimiento varchar(23) NULL,
	moaEstadoAporte varchar (22) NULL,
	moaValorAporte numeric(19,5) NULL,
	moaValorInteres numeric(19,5) NULL,
	moaFechaActualizacionEstado datetime NULL,
	moaFechaCreacion datetime NULL,
	moaAporteDetallado bigint NOT NULL,
);
ALTER TABLE MovimientoAporte_aud ADD CONSTRAINT FK_MovimientoAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla RegistroEstadoAporte_aud
CREATE TABLE dbo.RegistroEstadoAporte_aud (
	reaId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	reaSolicitud bigint NOT NULL,
	reaActividad varchar(29) NULL,
	reaEstadoSolicitud varchar(18) NULL,
	reaFecha datetime NULL,
	reaComunicado bigint NULL,
	reaUsuario varchar (255) NULL,
	reaEstadoInicialSolicitud varchar (20) NULL,
);
ALTER TABLE RegistroEstadoAporte_aud ADD CONSTRAINT FK_RegistroEstadoAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Eliminacion de la tabla Trazabilidad
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_Trazabilidad_aud_REV')) ALTER TABLE Trazabilidad_aud DROP CONSTRAINT FK_Trazabilidad_aud_REV;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Trazabilidad_aud')) DROP TABLE Trazabilidad_aud;

--changeset jocorrea:02
--comment: Creacion de la tabla ExpulsionSubsanada_aud
CREATE TABLE ExpulsionSubsanada_aud(
	exsId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	exsExpulsionSubsanada bit NULL,
	exsFechaSubsanacionExpulsion date NULL,
	exsMotivoSubsanacionExpulsion varchar (200) NOT NULL,
	exsEmpleador bigint NULL,
	exsRolAfiliado bigint NULL,
);
ALTER TABLE ExpulsionSubsanada_aud ADD CONSTRAINT FK_ExpulsionSubsanada_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset fvasquez:03
--comment: Creacion de las tablas DevolucionAporte_aud, DevolucionAporteDetalle_aud y SolicitudDevolucionAporte_aud
CREATE TABLE DevolucionAporte_aud(
	dapId bigint IDENTITY(1,1) NOT NULL, 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dapFechaRecepcion datetime NULL,
	dapMotivoPeticion varchar(28) NULL,
	dapDestinatarioDevolucion varchar(13) NULL,
	dapCajaCompensacion int NULL, 
	dapOtroDestinatario varchar(255) NULL,
	dapMontoAportes numeric(19,5) NULL,
	dapMontoIntereses numeric(19,5) NULL,
	dapPeriodoReclamado varchar(255) NULL,
	dapMedioPago bigint NULL, 
	dapSedeCajaCompensacion bigint NULL,
	dapDescuentoGestionPagoOI numeric(19,5) NULL,
	dapDescuentoGestionFinanciera numeric(19,5) NULL,
	dapDescuentoOtro numeric(19,5) NULL,
);
ALTER TABLE DevolucionAporte_aud ADD CONSTRAINT FK_DevolucionAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DevolucionAporteDetalle_aud(
	dadId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dadIncluyeAporteObligatorio bit NULL,
	dadIncluyeMoraCotizante bit NULL,
	dadComentarioHistorico varchar(255) NULL,
	dadComentarioNovedades varchar(255) NULL,
	dadComentarioAportes varchar(255) NULL,
	dadUsuario varchar(255) NULL,
	dadFechaGestion datetime NULL,
	dadDevolucionAporte bigint NOT NULL,
	dadMovimientoAporte bigint NULL,
CONSTRAINT PK_DevolucionAporteDetalle_dadId PRIMARY KEY (dadId)
);
ALTER TABLE DevolucionAporteDetalle_aud ADD CONSTRAINT FK_DevolucionAporteDetalle_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudDevolucionAporte_aud(
	sdaId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sdaEstadoSolicitud varchar(25) NULL,
	sdaTipoSolicitante varchar(13) NULL,
	sdaPersona bigint NULL,
	sdaObservacionAnalista varchar(255) NULL,
	sdaObservacionSupervisor varchar(255) NULL,
	sdaResultadoAnalista varchar(10) NULL,
	sdaResultadoSupervisor varchar(10) NULL,
	sdaDevolucionAporte bigint NULL,
	sdaSolicitudGlobal bigint NULL,
);
ALTER TABLE SolicitudDevolucionAporte_aud ADD CONSTRAINT FK_SolicitudDevolucionAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);