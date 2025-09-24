--liquibase formatted sql

--changeset atoro:01
--comment: Se agregan campos a la tabla InformacionFaltanteAportante_aud y SolicitudAporte_aud
ALTER TABLE dbo.InformacionFaltanteAportante_aud ADD ifaFechaRegistro date NULL;
ALTER TABLE dbo.InformacionFaltanteAportante_aud ADD ifaUsuario varchar(20) NULL;
ALTER TABLE dbo.SolicitudAporte_aud ADD soaNumeroIdentificacion varchar(16) NULL;
ALTER TABLE dbo.SolicitudAporte_aud ADD soaTipoIdentificacion varchar(20) NULL;
ALTER TABLE dbo.SolicitudAporte_aud ADD soaNombreAportante varchar(200) NULL;
ALTER TABLE dbo.SolicitudAporte_aud ADD soaPeriodoAporte varchar(7) NULL;

--changeset abaquero:02
--comment: Se agregan campos nuevos del Control de cambios
ALTER TABLE dbo.PilaArchivoIRegistro1_aud ADD pi1FechaActualizacion date;
ALTER TABLE dbo.PilaArchivoIPRegistro1_aud ADD ip1FechaActualizacion date;
ALTER TABLE dbo.PilaArchivoIPRegistro2_aud ADD ip2FechaInicioSuspension date;
ALTER TABLE dbo.PilaArchivoIPRegistro2_aud ADD ip2FechaFinSuspension date;

--changeset criparra:03
--comment: Se adiciona el campo traEstadoInicialSolicitud y traEstadoFinalSolicitud a la tablas Trazabilidad
ALTER TABLE dbo.Trazabilidad_aud ADD traEstadoInicialSolicitud varchar(20) NULL;

--changeset rarboleda:04
--comment: Se crea la tabla BandejaEmpleadorSinAfiliar_aud
CREATE TABLE dbo.BandejaEmpleadorSinAfiliar_aud(
	besId bigint IDENTITY(1,1) NOT NULL, 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	besEmpresa bigint NOT NULL,
	besFechaUltimoRecaudoAporte date NULL,
	besFechaIngresoBandeja date NULL, 
	besEstadoAportante varchar(50) NULL,
	besGestionado bit NULL, 
);
ALTER TABLE BandejaEmpleadorSinAfiliar_aud WITH CHECK ADD CONSTRAINT FK_BandejaEmpleadorSinAfiliar_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset anbuitrago:05
--comment: Se crea tabla ActualizacionDatosEmpleador
CREATE TABLE dbo.ActualizacionDatosEmpleador_aud (
	adeId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	adeEmpresa bigint NOT NULL,
	adeTipoInconsistencia varchar(20) NOT NULL,
	adeCanalContacto varchar(20) NOT NULL,
	adeFechaIngreso datetime NOT NULL,
	adeEstadoGestion varchar(20) NOT NULL,
	adeObservaciones varchar(60) NULL,
	adeFechaRespuesta datetime NULL,
);
ALTER TABLE dbo.ActualizacionDatosEmpleador_aud WITH CHECK ADD CONSTRAINT FK_ActualizacionDatosEmpleador_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);