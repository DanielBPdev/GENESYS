--liquibase formatted sql

--changeset clmarin:01
--comment: Creacion de la tabla MovimientoAportante y RegistroEstadoAporte, y la eliminacion de la tabla Trazabilidad

--Creacion de la tabla MovimientoAporte
CREATE TABLE MovimientoAporte(
	moaId bigint IDENTITY(1,1) NOT NULL,
	moaTipoAjuste varchar(20) NULL,
	moaTipoMovimiento varchar(23) NULL,
	moaEstadoAporte varchar (22) NULL,
	moaValorAporte numeric(19,5) NULL,
	moaValorInteres numeric(19,5) NULL,
	moaFechaActualizacionEstado datetime NULL,
	moaFechaCreacion datetime NULL,
	moaAporteDetallado bigint NOT NULL,
CONSTRAINT PK_MovimientoAporte_moaId PRIMARY KEY (moaId)
);
ALTER TABLE MovimientoAporte WITH CHECK ADD CONSTRAINT FK_MovimientoAporte_moaAporteDetallado FOREIGN KEY (moaAporteDetallado) REFERENCES AporteDetallado (apdId);

--Creacion de la tabla RegistroEstadoAporte
CREATE TABLE dbo.RegistroEstadoAporte (
	reaId bigint NOT NULL IDENTITY(1,1),
	reaSolicitud bigint NOT NULL,
	reaActividad varchar(29) NULL,
	reaEstadoSolicitud varchar(18) NULL,
	reaFecha datetime NULL,
	reaComunicado bigint NULL,
	reaUsuario varchar (20) NULL,
	reaEstadoInicialSolicitud varchar (20) NULL,
	CONSTRAINT PK_RegistroEstadoAporte_raeId PRIMARY KEY (reaId)
);
ALTER TABLE RegistroEstadoAporte WITH CHECK ADD CONSTRAINT FK_RegistroEstadoAporte_reaSolicitud FOREIGN KEY (reaSolicitud) REFERENCES Solicitud (solId);
ALTER TABLE RegistroEstadoAporte WITH CHECK ADD CONSTRAINT FK_RegistroEstadoAporte_reaComunicado FOREIGN KEY (reaComunicado) REFERENCES Comunicado (comId);

--Eliminacion de la tabla Trazabilidad
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_Trazabilidad_traSolicitud')) ALTER TABLE Trazabilidad DROP CONSTRAINT FK_Trazabilidad_traSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_Trazabilidad_traComunicado')) ALTER TABLE Trazabilidad DROP CONSTRAINT FK_Trazabilidad_traComunicado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Trazabilidad_traActividad')) ALTER TABLE Trazabilidad DROP CONSTRAINT CK_Trazabilidad_traActividad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Trazabilidad_traEstadoInicialSolicitud')) ALTER TABLE Trazabilidad DROP CONSTRAINT CK_Trazabilidad_traEstadoInicialSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Trazabilidad_traEstadoSolicitud')) ALTER TABLE Trazabilidad DROP CONSTRAINT CK_Trazabilidad_traEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Trazabilidad')) DROP TABLE Trazabilidad;

