--liquibase formatted sql

--changeset rarboleda:01
--comment: Se crea la tabla BandejaEmpCeroTrabajadoresActivos
CREATE TABLE dbo.BandejaEmpCeroTrabajadoresActivos_aud(
	becId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	becEmpleador bigint NOT NULL, 
	becFechaIngresoBandeja date, 
	becCantidadIngresos smallint, 
	becEstadoMorosidad varchar(75),
	becUltimoPeriodoPagado varchar(7), 
	becFechaUltimoPeriodoPagado date,
	becRetiroReportadoTotalEmpleado bit,
	becHistoricoAportes bit,
	becConAfiliacionTrabajadores bit,
	becEmpleadorGestionado bit,
);
ALTER TABLE BandejaEmpCeroTrabajadoresActivos_aud WITH CHECK ADD CONSTRAINT FK_BandejaEmpCeroTrabajadoresActivos_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset mosanchez:02
--comment: Se agregan campos a las tablas Beneficio_aud, PeriodoBeneficio_aud, SolicitudAporte_aud
ALTER TABLE dbo.Beneficio_aud ADD REV int NOT NULL;
ALTER TABLE dbo.Beneficio_aud ADD REVTYPE smallint NULL;
ALTER TABLE Beneficio_aud WITH CHECK ADD CONSTRAINT FK_Beneficio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE dbo.PeriodoBeneficio_aud ADD REV int NOT NULL;
ALTER TABLE dbo.PeriodoBeneficio_aud ADD REVTYPE smallint NULL;
ALTER TABLE PeriodoBeneficio_aud WITH CHECK ADD CONSTRAINT FK_PeriodoBeneficio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE dbo.SolicitudAporte_aud ADD REV int NOT NULL;
ALTER TABLE dbo.SolicitudAporte_aud ADD REVTYPE smallint NULL;
ALTER TABLE SolicitudAporte_aud WITH CHECK ADD CONSTRAINT FK_SolicitudAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset clamrin:03
--comment: Se agrega la tabla GrupoRequisito_aud
 CREATE TABLE dbo.GrupoRequisito_aud(
	grqId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	grqRequisitoCajaClasificacion bigint NULL,
	grqGrupoUsuario varchar(60) NULL,
);
ALTER TABLE GrupoRequisito_aud WITH CHECK ADD CONSTRAINT FK_GrupoRequisito_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);