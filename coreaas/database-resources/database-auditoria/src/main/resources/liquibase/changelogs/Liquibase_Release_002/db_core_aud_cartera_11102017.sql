--liquibase formatted sql

--changeset atoro:01
--comment: Se agregan tablas y campos a tablas relacionados con el proceso Cartera
--Creacion de la tabla ParametrizacionCartera_aud
CREATE TABLE ParametrizacionCartera_aud(
	pacId BIGINT NOT NULL IDENTITY(1,1), 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pacAplicar BIT NOT NULL,
	pacIncluirIndependientes BIT NOT NULL,
	pacIncluirPensionados BIT NOT NULL,
	pacEstadoCartera VARCHAR(255) NOT NULL,
	pacValorPromedioAportes VARCHAR(255) NOT NULL,
	pacCantidadPeriodos SMALLINT NOT NULL,
	pacTrabajadoresActivos VARCHAR(255) NOT NULL,
	pacPeriodosMorosos VARCHAR(255) NOT NULL,
	pacMayorValorPromedio SMALLINT NOT NULL,
	pacMayorTrabajadoresActivos SMALLINT NOT NULL,
	pacMayorVecesMoroso SMALLINT NOT NULL,
	pacTipoParametrizacion VARCHAR(31) NOT NULL,
);
ALTER TABLE ParametrizacionCartera_aud ADD CONSTRAINT FK_ParametrizacionCartera_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ParametrizacionPreventiva_aud
CREATE TABLE ParametrizacionPreventiva_aud(
	pacId BIGINT NOT NULL, 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pprDiasHabilesPrevios SMALLINT NOT NULL,
	pprHoraEjecucion DATETIME NOT NULL,
);
ALTER TABLE ParametrizacionPreventiva_aud ADD CONSTRAINT FK_ParametrizacionPreventiva_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla SolicitudPreventiva_aud
CREATE TABLE SolicitudPreventiva_aud(
	sprId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sprActualizacionEfectiva BIT NULL,
	sprBackActualizacion VARCHAR(255) NOT NULL,
	sprContactoEfectivo BIT NULL,
	sprEstadoSolicitudPreventiva VARCHAR(255) NOT NULL,
	sprPersona BIGINT NOT NULL,
	sprRequiereFiscalizacion BIT NULL,
	sprTipoSolicitanteMovimientoAporte INT NOT NULL,
	sprSolicitudGlobal BIGINT NOT NULL,
);
ALTER TABLE SolicitudPreventiva_aud ADD CONSTRAINT FK_SolicitudPreventiva_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset atoro:02
--comment: Se modifica campo de la tabla SolicitudPreventiva_aud
ALTER TABLE SolicitudPreventiva_aud ALTER COLUMN sprTipoSolicitanteMovimientoAporte VARCHAR(14) NOT NULL;

--changeset jusanchez:03
--comment: Se adiciona el campo en la tabla SolicitudPreventiva_aud
ALTER TABLE SolicitudPreventiva_aud ADD sprTipoGestionCartera VARCHAR(10) NULL;

--changeset atoro:04
--comment: Se modifican tamaño de campos de la tabla ParametrizacionCartera_aud
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacAplicar BIT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacIncluirIndependientes BIT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacIncluirPensionados BIT NULL
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacEstadoCartera VARCHAR(7) NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacValorPromedioAportes VARCHAR(16) NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacCantidadPeriodos SMALLINT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacTrabajadoresActivos VARCHAR(18) NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacPeriodosMorosos VARCHAR (21) NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacMayorValorPromedio SMALLINT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacMayorTrabajadoresActivos SMALLINT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacMayorVecesMoroso SMALLINT NULL;
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacTipoParametrizacion VARCHAR(31) NULL;
ALTER TABLE SolicitudPreventiva_aud ALTER COLUMN sprEstadoSolicitudPreventiva VARCHAR(34) NULL;

--changeset borozco:05
--comment: Creacion de tablas del proceso de Fiscalizacion
--Creacion de la tabla ParametrizacionFiscalizacion_aud
CREATE TABLE ParametrizacionFiscalizacion_aud(
	pacId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pfiAlertaValidacionPila BIT NULL,
	pfiEstadoAporteNoOk BIT NULL,
	pfiIbcMenorUltimo BIT NULL,
	pfiNovedadRetiro BIT NULL,
	pfiPeriodosRetroactivos SMALLINT NULL,
	pfiSalarioMenorUltimo BIT NULL,
);
ALTER TABLE ParametrizacionFiscalizacion_aud ADD CONSTRAINT FK_ParametrizacionFiscalizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CicloFiscalizacion_aud
CREATE TABLE CicloFiscalizacion_aud(
	cfiId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cfiEstadoCicloFiscalizacion VARCHAR(10) NOT NULL,
	cfiFechaInicio DATE NOT NULL,
	cfiFechaFin DATE NOT NULL,
	cfiFechaCreacion DATETIME NOT NULL,
);
ALTER TABLE CicloFiscalizacion_aud ADD CONSTRAINT FK_CicloFiscalizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CicloAportante_aud
CREATE TABLE CicloAportante_aud(
	capId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	capPersona BIGINT NULL,
	capTipoSolicitante VARCHAR(14) NULL,
	capCicloFiscalizacion BIGINT NULL,
);
ALTER TABLE CicloAportante_aud ADD CONSTRAINT FK_CicloAportante_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla SolicitudFiscalizacion_aud
CREATE TABLE SolicitudFiscalizacion_aud(
	sfiId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sfiEstadoFiscalizacion VARCHAR(11) NULL,
	sfiSolicitudGlobal BIGINT NOT NULL,
	sfiCicloAportante BIGINT NOT NULL,
);
ALTER TABLE SolicitudFiscalizacion_aud ADD CONSTRAINT FK_SolicitudFiscalizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ActividadFiscalizacion_aud
CREATE TABLE ActividadFiscalizacion_aud(
	acfId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	acfActividadFiscalizacion VARCHAR(40) NOT NULL,
	acfResultadoFiscalizacion VARCHAR(33) NULL,
	acfComentarios VARCHAR(500) NULL,
	acfCicloAportante BIGINT NOT NULL,
);
ALTER TABLE ActividadFiscalizacion_aud ADD CONSTRAINT FK_ActividadFiscalizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla AgendaFiscalizacion_aud
CREATE TABLE AgendaFiscalizacion_aud(
	afsId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	afsVisitaAgenda VARCHAR(13) NULL,
	afsFecha DATE NOT NULL,
	afsHorario DATETIME NOT NULL,
	afsContacto VARCHAR(255) NOT NULL,
	afsTelefono VARCHAR(255) NULL,
	afsCicloAportante BIGINT NOT NULL,
);
ALTER TABLE AgendaFiscalizacion_aud ADD CONSTRAINT FK_AgendaFiscalizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ActividadDocumento_aud
CREATE TABLE ActividadDocumento_aud(
	adoId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	adoIdentificadorDocumento VARCHAR(255) NOT NULL,
	adoTipoDocumento VARCHAR(12) NOT NULL,
	adoActividadFiscalizacion BIGINT NOT NULL
);
ALTER TABLE ActividadDocumento_aud ADD CONSTRAINT FK_ActividadDocumento_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset borozco:06
--comment: Se adiciona campo en la tabla ActividadDocumento_aud
ALTER TABLE ActividadDocumento_aud ADD adoFecha DATETIME NOT NULL;

--changeset borozco:07
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento_aud ADD adoCorteEntidades BIGINT NULL;

--changeset borozco:08
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento_aud DROP COLUMN adoCorteEntidades;
ALTER TABLE ParametrizacionFiscalizacion_aud ADD pfiCorteEntidades BIGINT NULL;

--changeset borozco:09
--comment: Se adiciona campo en la tabla ActividadDocumento_aud
ALTER TABLE ActividadDocumento_aud DROP COLUMN adoFecha;
ALTER TABLE ActividadFiscalizacion_aud ADD afsFecha DATETIME NOT NULL;

--changeset borozco:10
--comment: Se adiciona campo en la tabla ActividadFiscalizacion
ALTER TABLE ActividadFiscalizacion_aud DROP COLUMN afsFecha;
ALTER TABLE ActividadFiscalizacion_aud ADD acfFecha DATETIME NOT NULL;

--changeset atoro:11
--comment: Se modifica campo en la tabla SolicitudPreventiva_aud
ALTER TABLE SolicitudPreventiva_aud ALTER COLUMN sprBackActualizacion VARCHAR(255) NULL;

--changeset borozco:12
--comment: Se modifica campo en la tabla ParametrizacionCartera_aud
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacTipoParametrizacion VARCHAR(67);