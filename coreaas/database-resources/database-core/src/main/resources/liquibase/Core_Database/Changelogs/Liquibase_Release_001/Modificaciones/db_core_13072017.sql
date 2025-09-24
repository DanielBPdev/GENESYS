--liquibase formatted sql

--changeset abaquero:01
--comment: Eliminar registros asociados a Pila
DELETE FROM dbo.ValidatorParamValue;
DELETE FROM dbo.ValidatorParameter;
DELETE FROM dbo.ValidatorDefinition where id < 1000;
DELETE FROM dbo.ValidatorCatalog where id < 1000;
DELETE FROM dbo.FileLoadedLog;
DELETE FROM dbo.FileLoaded;
DELETE FROM dbo.FIELDDEFINITIONLOAD where id < 1000;
DELETE FROM dbo.FIELDLOADCATALOG where id < 1000;
DELETE FROM dbo.LINEDEFINITIONLOAD where id < 1000;
DELETE FROM dbo.FILEDEFINITIONLOAD where id < 1000;
DELETE FROM dbo.LINELOADCATALOG where id < 1000;
DELETE FROM dbo.FILEDEFINITIONLOADTYPE where id < 1000;

-- Se agregan campos nuevos del Control de cambios 
ALTER TABLE dbo.PilaArchivoIRegistro1 ADD pi1FechaActualizacion date;
ALTER TABLE dbo.PilaArchivoIPRegistro1 ADD ip1FechaActualizacion date;
ALTER TABLE dbo.PilaArchivoIPRegistro2 ADD ip2FechaInicioSuspension date;
ALTER TABLE dbo.PilaArchivoIPRegistro2 ADD ip2FechaFinSuspension date;

-- Actualización de parametrización de tabla de normatividad de Fechas de vencimiento
TRUNCATE TABLE dbo.PilaNormatividadFechaVencimiento;
DELETE FROM dbo.PilaClasificacionAportante
DBCC CHECKIDENT ('dbo.PilaClasificacionAportante', RESEED, 0);

--changeset criparra:02
--comment: Se adiciona el campo traEstadoInicialSolicitud y traEstadoFinalSolicitud a la tablas Trazabilidad
ALTER TABLE dbo.Trazabilidad ADD traEstadoInicialSolicitud varchar(20) NULL;

--changeset rarboleda:03
--comment: Se crea la tabla BandejaEmpleadorSinAfiliar
CREATE TABLE dbo.BandejaEmpleadorSinAfiliar(
	besId bigint IDENTITY(1,1) NOT NULL , 
	besEmpresa bigint NOT NULL,
	besFechaUltimoRecaudoAporte date NULL,
	besFechaIngresoBandeja date NULL, 
	besEstadoAportante varchar(50) NULL,
	besGestionado bit NULL, 
	CONSTRAINT PK_BandejaEmpleadorSinAfiliar_besId PRIMARY KEY (besId)
);
ALTER TABLE dbo.BandejaEmpleadorSinAfiliar ADD CONSTRAINT FK_BandejaEmpleadorSinAfiliar_besEmpresa FOREIGN KEY (besEmpresa) REFERENCES dbo.Empresa(empId);
ALTER TABLE dbo.BandejaEmpleadorSinAfiliar ADD CONSTRAINT UK_BandejaEmpleadorSinAfiliar_besEmpresa UNIQUE (besEmpresa);




