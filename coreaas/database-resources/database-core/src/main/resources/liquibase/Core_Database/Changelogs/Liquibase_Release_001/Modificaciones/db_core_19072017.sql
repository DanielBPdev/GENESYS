--liquibase formatted sql

--changeset rarboleda:01
--comment: Se crea la tabla BandejaEmpCeroTrabajadoresActivos
CREATE TABLE dbo.BandejaEmpCeroTrabajadoresActivos(
	becId bigint NOT NULL IDENTITY(1,1),
	becEmpleador bigint NOT NULL, 
	becFechaIngresoBandeja date, 
	becCantidadIngresos smallint, 
	becEstadoMorosidad varchar(75),
	becUltimoPeriodoAporte varchar(7), 
	becFechaUltimoPeriodoAporte date,
	becRetiroReportadoTotalEmpleado bit,
	becHistoricoAportes bit,
	becConAfiliacionTrabajadores bit,
	becEmpleadorGestionado bit,
	CONSTRAINT PK_BandejaEmpCeroTrabajadoresActivos_becId PRIMARY KEY (becId)
);
ALTER TABLE dbo.BandejaEmpCeroTrabajadoresActivos ADD CONSTRAINT FK_BandejaEmpCeroTrabajadoresActivos_becEmpleador FOREIGN KEY (becEmpleador) REFERENCES dbo.Empleador(empId);

--changeset clamrin:02
--comment: Se elimina la tabla GrupoUsuario y se agrega la tabla GrupoRequisito 
ALTER TABLE dbo.GrupoUsuario DROP CONSTRAINT FK_GrupoUsuario_gusRequisitoCajaCompensacion;
DROP TABLE dbo.GrupoUsuario;

CREATE TABLE dbo.GrupoRequisito(
	grqId bigint NOT NULL IDENTITY(1,1),
	grqRequisitoCajaClasificacion bigint NULL,
	grqGrupoUsuario varchar(60) NULL,
	CONSTRAINT PK_GrupoRequisito_grqId PRIMARY KEY (grqId)
);
ALTER TABLE dbo.GrupoRequisito WITH CHECK ADD CONSTRAINT FK_GrupoRequisito_grqRequisitoCajaCompensacion FOREIGN KEY(grqRequisitoCajaClasificacion) REFERENCES RequisitoCajaClasificacion (rtsId);
