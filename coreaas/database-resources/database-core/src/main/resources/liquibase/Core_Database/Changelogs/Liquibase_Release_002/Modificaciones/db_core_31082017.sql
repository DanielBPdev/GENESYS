--liquibase formatted sql

--changeset fvasquez:01
--comment: Creacion de las tablas DevolucionAporte, DevolucionAporteDetalle y SolicitudDevolucionAporte
--En esta tabla se almacenan los datos de cada devolución
CREATE TABLE DevolucionAporte(
	dapId bigint IDENTITY(1,1) NOT NULL, 
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
CONSTRAINT PK_DevolucionAporte_dapId PRIMARY KEY (dapId)
);

ALTER TABLE DevolucionAporte ADD CONSTRAINT FK_DevolucionAporte_dapCajaCompensacion FOREIGN KEY (dapCajaCompensacion) REFERENCES CajaCompensacion (ccfId);
ALTER TABLE DevolucionAporte ADD CONSTRAINT FK_DevolucionAporte_dapMedioPago FOREIGN KEY (dapMedioPago) REFERENCES MedioDePago (mdpId);
ALTER TABLE DevolucionAporte ADD CONSTRAINT FK_DevolucionAporte_dapSedeCajaCompensacion FOREIGN KEY (dapSedeCajaCompensacion) REFERENCES SedeCajaCompensacion (sccfId);
	
--En esta tabla de almacena el detalle de cada aporte gestionado en una devolución
CREATE TABLE DevolucionAporteDetalle(
	dadId bigint IDENTITY(1,1) NOT NULL, 
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

ALTER TABLE DevolucionAporteDetalle ADD CONSTRAINT FK_DevolucionAporteDetalle_dadDevolucionAporte FOREIGN KEY (dadDevolucionAporte) REFERENCES DevolucionAporte (dapId);
ALTER TABLE DevolucionAporteDetalle ADD CONSTRAINT FK_DevolucionAporteDetalle_dadMovimientoAporte FOREIGN KEY (dadMovimientoAporte) REFERENCES MovimientoAporte (moaId);

--En esta tabla se almacenan las solicitud de devolución de aportes
CREATE TABLE SolicitudDevolucionAporte(
	sdaId bigint IDENTITY(1,1) NOT NULL,
	sdaEstadoSolicitud varchar(25) NULL,
	sdaTipoSolicitante varchar(13) NULL,
	sdaPersona bigint NULL, 
	sdaObservacionAnalista varchar(255) NULL,
	sdaObservacionSupervisor varchar(255) NULL,
	sdaResultadoAnalista varchar(10) NULL,
	sdaResultadoSupervisor varchar(10) NULL,
	sdaDevolucionAporte bigint NULL,
	sdaSolicitudGlobal bigint NULL,
CONSTRAINT PK_SolicitudDevolucionAporte_sdaId PRIMARY KEY (sdaId)
);

ALTER TABLE SolicitudDevolucionAporte ADD CONSTRAINT FK_SolicitudDevolucionAporte_sdaPersona FOREIGN KEY (sdaPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudDevolucionAporte ADD CONSTRAINT FK_SolicitudDevolucionAporte_sdaSolicitudGlobal FOREIGN KEY (sdaSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudDevolucionAporte ADD CONSTRAINT FK_SolicitudDevolucionAporte_sdaDevolucionAporte FOREIGN KEY (sdaDevolucionAporte) REFERENCES DevolucionAporte (dapId);