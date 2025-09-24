--liquibase formatted sql

--changeset abaquero:01
--comment: Eliminar registros asociados a la tabla PilaNormatividadFechaVencimiento
delete from dbo.PilaNormatividadFechaVencimiento where pfvId between 317 and 331
DBCC CHECKIDENT ('dbo.PilaNormatividadFechaVencimiento', RESEED, 316);

--changeset atoro:02
--comment: Se agregan campos a la tabla InformacionFaltanteAportante y SolicitudAporte
ALTER TABLE dbo.InformacionFaltanteAportante ADD ifaFechaRegistro date NULL;
ALTER TABLE dbo.InformacionFaltanteAportante ADD ifaUsuario varchar(20) NULL;

ALTER TABLE dbo.SolicitudAporte ADD soaNumeroIdentificacion varchar(16) NULL;
ALTER TABLE dbo.SolicitudAporte ADD soaTipoIdentificacion varchar(20) NULL;
ALTER TABLE dbo.SolicitudAporte ADD soaNombreAportante varchar(200) NULL;
ALTER TABLE dbo.SolicitudAporte ADD soaPeriodoAporte varchar(7) NULL;

--changeset anbuitrago:03
--comment: Se crea tabla ActualizacionDatosEmpleador
CREATE TABLE dbo.ActualizacionDatosEmpleador (
	adeId bigint NOT NULL IDENTITY(1,1),
	adeEmpresa bigint NOT NULL,
	adeTipoInconsistencia varchar(20) NOT NULL,
	adeCanalContacto varchar(20) NOT NULL,
	adeFechaIngreso datetime NOT NULL,
	adeEstadoGestion varchar(20) NOT NULL,
	adeObservaciones varchar(60) NULL,
	adeFechaRespuesta datetime NULL,
	CONSTRAINT PK_ActualizacionDatosEmpleador_adeId PRIMARY KEY (adeId)
);
ALTER TABLE dbo.ActualizacionDatosEmpleador ADD CONSTRAINT FK_ActualizacionDatosEmpleador_adeEmpresa FOREIGN KEY (adeEmpresa) REFERENCES Empresa(empId);