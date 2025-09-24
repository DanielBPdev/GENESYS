--liquibase formatted sql

--changeset jocampo:01
--comment: Se agregan tablas y campos a tablas relacionados con el proceso Subsidio Monetario
--Creacion de la tabla Periodo_aud
CREATE TABLE Periodo_aud(
	priId BIGINT NOT NULL IDENTITY(1,1), 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	priPeriodo DATE NOT NULL,
);
ALTER TABLE Periodo_aud ADD CONSTRAINT FK_Periodo_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla SolicitudLiquidacionSubsidio_aud
CREATE TABLE SolicitudLiquidacionSubsidio_aud(
	slsId BIGINT NOT NULL IDENTITY(1,1), 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	slsSolicitudGlobal BIGINT NOT NULL,
	slsFechaCorteAporte DATETIME NULL,
	slsFechaInicio DATETIME NULL,
	slsFechaFin DATETIME NULL,
	slsTipoLiquidacion VARCHAR(10) NOT NULL,
	slsTipoLiquidacionEspecifica VARCHAR(30) NULL,
	slsEstadoLiquidacion VARCHAR(25) NOT NULL,
	slsTipoEjecucionProceso VARCHAR(10) NOT NULL,
	slsFechaEjecucionProgramada DATETIME NULL,
	slsUsuarioEvaluacionPrimerNivel VARCHAR(50) NULL,
	slsObservacionesPrimerNivel VARCHAR(250) NULL,
	slsUsuarioEvaluacionSegundoNivel VARCHAR(50) NULL,
	slsObservacionesSegundoNivel VARCHAR(250) NULL,
	slsRazonRechazoLiquidacion VARCHAR(250) NULL,
	slsObservacionesProceso VARCHAR(250) NULL,
);
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD CONSTRAINT FK_SolicitudLiquidacionSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla PeriodoLiquidacion_aud
CREATE TABLE PeriodoLiquidacion_aud(
	pelId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pelSolicitudLiquidacionSubsidio BIGINT NOT NULL,
	pelPeriodo BIGINT NOT NULL,
	pelTipoPeriodo VARCHAR(10) NOT NULL,
);
ALTER TABLE PeriodoLiquidacion_aud ADD CONSTRAINT FK_PeriodoLiquidacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla EntidadDescuento_aud
CREATE TABLE EntidadDescuento_aud(
	endId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	endCodigo BIGINT NOT NULL,
	endTipo VARCHAR(10) NOT NULL,
	endEmpresa BIGINT NOT NULL,
	endPrioridad INT NOT NULL,
	endEstado VARCHAR(10) NOT NULL,
	endNombreContacto VARCHAR(250) NULL,
	endObservaciones VARCHAR(250) NULL,
	endFechaCreacion DATE NOT NULL,
);
ALTER TABLE EntidadDescuento_aud ADD CONSTRAINT FK_EntidadDescuento_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ParametrizacionCondicionesSubsidio_aud
CREATE TABLE ParametrizacionCondicionesSubsidio_aud(
	pcsId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pcsAnioVigenciaParametrizacion INT NOT NULL,
	pcsPeriodoInicio DATE NOT NULL,
	pcsPeriodoFin DATE NOT NULL,
	pcsValorCuotaAnualBase NUMERIC(19,5) NOT NULL,
	pcsValorCuotaAnualAgraria NUMERIC(19,5) NOT NULL,
	pcsProgramaDecreto4904 BIT NOT NULL,
	pcsRetroactivoNovInvalidez BIT NOT NULL,
	pcsRetroactivoReingresoEmpleadores BIT NOT NULL,
);
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD CONSTRAINT FK_ParametrizacionCondicionesSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ParametrizacionLiquidacionSubsidio_aud
CREATE TABLE ParametrizacionLiquidacionSubsidio_aud(
	plsId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	plsAnioVigenciaParametrizacion INT NOT NULL,
	plsPeriodoInicio DATE NOT NULL,
	plsPeriodoFin DATE NOT NULL,
	plsFactorCuotaInvalidez NUMERIC(19,5) NOT NULL,
	plsFactorPorDefuncion NUMERIC(19,5) NOT NULL,
	plsHorasTrabajadas INT NOT NULL,
);
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud ADD CONSTRAINT FK_ParametrizacionLiquidacionSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset jocampo:02
--comment: Se modifica campo de la tabla EntidadDescuento
ALTER TABLE EntidadDescuento_aud ALTER COLUMN endEmpresa BIGINT NULL;

--changeset jocampo:03
--comment: Se modifica campo de la tabla ParametrizacionLiquidacionSubsidio_aud
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud ADD pcsSMLMV NUMERIC(19,5) NULL;

--changeset jocampo:04
--comment: Se adiciona campo en la tabla EntidadDescuento_aud
ALTER TABLE EntidadDescuento_aud ADD endNombre VARCHAR(250) NULL;

--changeset rlopez:05
--comment: Creacion de las tablas ArchivoEntidadDescuentoSubsidioPignorado_aud y SubsidioMonetarioValorPignorado_aud
--Creacion de la tabla ArchivoEntidadDescuentoSubsidioPignorado_aud
CREATE TABLE ArchivoEntidadDescuentoSubsidioPignorado_aud(
	ardId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ardFechaRecepcion DATETIME NOT NULL,
	ardFechaCargue DATETIME NULL,
	ardCodigoIdentificacionECM VARCHAR(255) NOT NULL,
	ardEntidadDescuento BIGINT NULL,
	ardPrioridadEntidadDescuento INT NULL,
	ardEstado VARCHAR(10) NULL,
	ardCausalAnulacion VARCHAR(21) NULL,
);
ALTER TABLE ArchivoEntidadDescuentoSubsidioPignorado_aud ADD CONSTRAINT FK_ArchivoEntidadDescuentoSubsidioPignorado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla SubsidioMonetarioValorPignorado_aud
CREATE TABLE SubsidioMonetarioValorPignorado_aud(
	smvId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	smvTipoIdentificacionTrabajador VARCHAR(20) NOT NULL,
	smvNumeroIdentificacionTrabajador VARCHAR(16) NOT NULL,
	smvNombreTrabajador VARCHAR(200) NOT NULL,
	smvTipoIdentificacionAdministrador VARCHAR(20) NOT NULL,
	smvNumeroIdentificacionAdministrador VARCHAR(16) NOT NULL,
	smvCodigoGrupoFamiliar SMALLINT NOT NULL,
	smvValorPignorar NUMERIC(19,5) NOT NULL,
	smvValorPignorado NUMERIC(19,5) NULL,
	smvArchivoEntidadDescuentoSubsidioPignorado BIGINT NOT NULL,
);
ALTER TABLE SubsidioMonetarioValorPignorado_aud ADD CONSTRAINT FK_SubsidioMonetarioValorPignorado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset mosorio:06
--comment:Se adiciona campo en la tabla EntidadDescuento_aud
ALTER TABLE EntidadDescuento_aud ADD endNumeroCelular VARCHAR(10) NULL;

--changeset jocampo:07
--comment:Se modifica campo en la tabla Solicitud_aud
ALTER TABLE Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(22) NULL;

--changeset jocampo:08
--comment:Se modifica tamaño de campos en la tabla SolicitudLiquidacionSubsidio_aud
ALTER TABLE SolicitudLiquidacionSubsidio_aud ALTER COLUMN slsTipoLiquidacion VARCHAR(27) NOT NULL;
ALTER TABLE SolicitudLiquidacionSubsidio_aud ALTER COLUMN slsTipoLiquidacionEspecifica VARCHAR(23) NULL;