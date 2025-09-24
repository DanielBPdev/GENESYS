--liquibase formatted sql

--changeset atoro:01
--comment:Se crean tablas ParametrizacionConveniosPago_aud y ParametrizacionExclusiones_aud
CREATE TABLE ParametrizacionConveniosPago_aud(
	pcpId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pcpCantidadPeriodos smallint NULL,
	pcpNumeroConveniosPermitido smallint NULL,
);
ALTER TABLE ParametrizacionConveniosPago_aud ADD CONSTRAINT FK_ParametrizacionConveniosPago_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE ParametrizacionExclusiones_aud(
	pexId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pexExclusionNegocios bit NULL,
	pexImposicionRecurso bit NULL,
	pexConvenioPago bit NULL,
	pexAclaracionMora bit NULL,
	pexRiesgoIncobrabilidad bit NULL,
);
ALTER TABLE ParametrizacionExclusiones_aud ADD CONSTRAINT FK_ParametrizacionExclusiones_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset atoro:02
--comment:Creacion de la tabla ParametrizacionDesafiliacion_aud
CREATE TABLE ParametrizacionDesafiliacion_aud(
    pdeId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
    pdeLineaCobro VARCHAR(3) NOT NULL,
    pdeProgramacionEjecucion VARCHAR(8) NOT NULL,
    pdeMontoMoraInexactitud NUMERIC(19,5) NOT NULL,
    pdePeriodosMora BIGINT NOT NULL,
    pdeMetodoEnvioComunicado VARCHAR(11) NOT NULL,
    pdeOficinaPrincipalFisico BIT NULL,
    pdeCorrespondenciaFisico BIT NULL,
    pdeNotificacionJudicialFisico BIT NULL,
    pdeOficinaPrincipalElectronico BIT NULL,
    pdeRepresentanteLegalElectronico BIT NULL,
    pdeResponsableAportesElectronico BIT NULL,
    pdeSiguienteAccion VARCHAR(29) NOT NULL,
    pdeHabilitado BIT NOT NULL,
);
ALTER TABLE ParametrizacionDesafiliacion_aud ADD CONSTRAINT FK_ParametrizacionDesafiliacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset atoro:03
--comment:Creacion de las tablas ConvenioPago_aud, PagoPeriodoConvenio_aud, ExclusionCartera_aud y PeriodoExclusionMora_aud
CREATE TABLE ConvenioPago_aud(
	copId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	copPersona BIGINT NOT NULL,
	copTipoSolicitante VARCHAR(13) NOT NULL,
	copDeudaPresuntaRegistrada NUMERIC(19,5) NOT NULL,
	copDeudaRealRegistrada NUMERIC(19,5) NULL,
	copCuotasPorPagar SMALLINT NOT NULL,
	copEstadoConvenioPago VARCHAR(30) NOT NULL,
	copMotivoAnulacion VARCHAR(30) NULL,
	copNombreUsuario VARCHAR(255) NOT NULL,
	copFechaAnulacion DATETIME NULL,
	copFechaRegistro DATETIME NOT NULL,
);
ALTER TABLE ConvenioPago_aud ADD CONSTRAINT FK_ConvenioPago_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE  PagoPeriodoConvenio_aud(
	ppcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ppcConvenioPago BIGINT NOT NULL,
	ppcFechaPago DATE NOT NULL ,
	ppcValorCuota NUMERIC(19,5) NOT NULL,
	ppcPeriodo DATE NOT NULL,
);
ALTER TABLE PagoPeriodoConvenio_aud ADD CONSTRAINT FK_PagoPeriodoConvenio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE ExclusionCartera_aud(
	excId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	excPersona BIGINT NOT NULL,
	excTipoSolicitante VARCHAR(15) NOT NULL,
	excEstadoExclusionCartera VARCHAR(10) NOT NULL,
	excFechaInicio DATE NOT NULL,
	excFechaFin DATE NULL,
	excFechaRegistro DATE NOT NULL,
	excFechaMovimiento DATE NULL,
	excObservacion VARCHAR(400) NULL,
	excTipoExclusionCartera VARCHAR(25) NOT NULL,
	excEstadoAntesExclusion VARCHAR(45) NOT NULL,
	excNumeroOperacionMora BIGINT NULL,
	excUsuarioRegistro VARCHAR(400) NULL,
	excResultado VARCHAR(11) NULL,
	excObservacionCambioResultado VARCHAR(400) NULL,
);
ALTER TABLE ExclusionCartera_aud ADD CONSTRAINT FK_ExclusionCartera_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE PeriodoExclusionMora_aud(
	pemId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pemPeriodo DATE NOT NULL,
	pemExclusionCartera BIGINT NOT NULL,
);
ALTER TABLE PeriodoExclusionMora_aud ADD CONSTRAINT FK_PeriodoExclusionMora_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset borozco:04
--comment:Se modifican campos en la tabla ActividadFiscalizacion
ALTER TABLE ActividadFiscalizacion_aud ALTER COLUMN acfActividadFiscalizacion VARCHAR(42) NOT NULL;
ALTER TABLE ActividadFiscalizacion_aud ALTER COLUMN acfResultadoFiscalizacion VARCHAR(33) NULL;

--changeset jusanchez:05
--comment:Se adiciona campo en la tabla PeriodoExclusionMora_aud
ALTER TABLE PeriodoExclusionMora_aud ADD pemEstadoPeriodo VARCHAR(10) NOT NULL;

--changeset atoro:06
--comment:Se adiciona campo en las tablas ParametrizacionPreventiva y ParametrizacionCartera_aud
ALTER TABLE ParametrizacionPreventiva_aud ADD pprEjecucionAutomatica BIT NULL;
ALTER TABLE ParametrizacionCartera_aud ADD pacFechaActualizacion DATETIME NULL;

--changeset atoro:07
--comment:Creacion de tablas relacionadas con la parametrizacion gestion de cobro
CREATE TABLE ParametrizacionGestionCobro_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pgcOficinaPrincipalFisico BIT NULL,
	pgcCorrespondenciaFisico BIT NULL,
	pgcNotificacionJudicialFisico BIT NULL,
	pgcOficinaPrincipalElectronico BIT NULL,
	pgcRepresentanteLegalElectronico BIT NULL,
	pgcResponsableAportesElectronico BIT NULL,
	pgcMetodoEnvioComunicado VARCHAR(11) null,
	pgcTipoParametrizacion VARCHAR(18) null,
);
ALTER TABLE ParametrizacionGestionCobro_aud ADD CONSTRAINT FK_ParametrizacionGestionCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE LineaCobro_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	lcoHabilitarAccionCobroA BIT NULL,
	lcoDiasFechaLimite BIGINT NULL,
	lcoDiasParametrizados BIGINT NULL,
	lcoHabilitarAccionCobroB BIT NULL,
	lcoTipoLineaCobro VARCHAR(3) NULL,
);
ALTER TABLE LineaCobro_aud ADD CONSTRAINT FK_LineaCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobroA_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	acaSuspensionAutomatica BIT NULL,
	acaDiasLimitePago BIGINT NULL,
	acaHoraEjecucion DATE NULL,
	acaLimiteEnvioComunicado BIGINT NULL,
	acaMetodo VARCHAR(8) NULL,
);
ALTER TABLE AccionCobroA_aud ADD CONSTRAINT FK_AccionCobroA_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
	
CREATE TABLE AccionCobroB_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	acbFechaGeneracion BIGINT NULL,
	acbHoraEjecucion date NULL,
	acbLimiteEnvioComunicado BIGINT NULL,
	acbMetodo VARCHAR(8) NULL,
);
ALTER TABLE AccionCobroB_aud ADD CONSTRAINT FK_AccionCobroB_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro1C_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	accFechaLiquidacion BIGINT NULL,
	accHoraEjecucion date NULL,
	accLimiteEnvioDocumento BIGINT NULL,
	accVariableCalculo VARCHAR(16) NULL,
	accCantidadPeriodos BIGINT NULL,
);
ALTER TABLE AccionCobro1C_aud ADD CONSTRAINT FK_AccionCobro1C_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro1D1E_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	acdFechaConteo VARCHAR(13) NULL,
	acdDiasTranscurridos BIGINT NULL,
	acdHoraEjecucion DATE NULL,
	acdLimiteEnvio BIGINT NULL,
	acdTipoCobro VARCHAR(12) NULL,
);
ALTER TABLE AccionCobro1D1E_aud ADD CONSTRAINT FK_AccionCobro1D1E_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro1F_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	abfAccionCobro1F BIT NULL,
	abfDiasParametrizados SMALLINT NULL,
	abfSiguienteAccion VARCHAR(29) NULL,
);
ALTER TABLE AccionCobro1F_aud ADD CONSTRAINT FK_AccionCobro1F_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro2C_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	aocAnexoLiquidacion BIT NULL,
	aocFechaEjecucion BIGINT NULL,
	aocHoraEjecucion DATE NULL,
);
ALTER TABLE AccionCobro2C_aud ADD CONSTRAINT FK_AccionCobro2C_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro2D_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	aodFechaConteo VARCHAR(13) NULL,
	aodDiasTranscurridos BIGINT NULL,
	aodHoraEjecucion DATE NULL,
);
ALTER TABLE AccionCobro2D_aud ADD CONSTRAINT FK_AccionCobro2D_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro2F2G_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	aofFechaConteo VARCHAR(13) NULL,
	aofDiasTranscurridos BIGINT NULL,
	aofHoraEjecucion DATE NULL,
	aofLimiteEnvio BIGINT NULL,
	aofTipoCobro VARCHAR(12) NULL,
);
ALTER TABLE AccionCobro2F2G_aud ADD CONSTRAINT FK_AccionCobro2F2G_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro2H_aud(
	pgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	achAccionCobro2H BIT NULL,
	achDiasRegistro BIGINT NULL,
	achDiasParametrizados BIGINT NULL,
	achSiguienteAccion VARCHAR(29) NULL,
);
ALTER TABLE AccionCobro2H_aud ADD CONSTRAINT FK_AccionCobro2H_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE AccionCobro2E_aud(
	aceId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	aceFechaConteo VARCHAR(13) NULL,
	aceDiasTranscurridos BIGINT NULL,
	aceHoraEjecucion DATE NULL,
);
ALTER TABLE AccionCobro2E_aud ADD CONSTRAINT FK_AccionCobro2E_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset fvasquez:08
--comment:Creacion de la tabla Cartera_aud
CREATE TABLE Cartera_aud(
	carId BIGINT IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	carDeudaPresunta NUMERIC(19,5) NULL,
	carEstadoCartera VARCHAR(6) NOT NULL,
	carEstadoOperacion VARCHAR(10) NOT NULL,
	carFechaCreacion DATETIME NOT NULL,
	carPersona BIGINT NOT NULL,
	carMetodo VARCHAR(8) NOT NULL,
	carNumeroOperacion VARCHAR(50) NOT NULL,
	carPeriodoDeuda DATETIME NOT NULL,
	carRiesgoIncobrabilidad VARCHAR(48) NULL,
	carTipoAccionCobro VARCHAR(4) NULL,
	carTipoDeuda VARCHAR(11) NULL,
	carTipoLineaCobro VARCHAR(3) NULL,
    carTipoSolicitante VARCHAR(13) NULL,
);
ALTER TABLE Cartera_aud ADD CONSTRAINT FK_Cartera_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset clmarin:09
--comment:Se modifican campos en las tabla ParametrizacionGestionCobro_aud y ConvenioPago_aud
ALTER TABLE ParametrizacionGestionCobro_aud ALTER COLUMN pgcTipoParametrizacion VARCHAR(55)NULL;
ALTER TABLE ConvenioPago_aud ALTER COLUMN copNombreUsuario VARCHAR(255) NULL;

--changeset abaquero:10
--comment:Se adicionan campos en la tabla Empleador_aud y RolAfiliado_aud
ALTER TABLE Empleador_aud ADD empDiaHabilVencimientoAporte SMALLINT;
ALTER TABLE RolAfiliado_aud ADD roaDiaHabilVencimientoAporte SMALLINT;

--changeset fvasquez:11
--comment:Se modifica campo en la tabla Cartera_aud
ALTER TABLE Cartera_aud ALTER COLUMN carPeriodoDeuda DATE NOT NULL;

--changeset clmarin:12
--comment:Se modifican campos en las tablas relacionadas con ParametrizacionGestionCobro
EXEC sp_rename 'dbo.LineaCobro_aud.lcoDiasFechaLimite', 'lcoDiasLimitePago', 'COLUMN';
EXEC sp_rename 'dbo.AccionCobroA_aud.acaHoraEjecucion', 'acaFechaHoraEjecucion', 'COLUMN';
EXEC sp_rename 'dbo.AccionCobroA_aud.acaLimiteEnvioComunicado', 'acaDiasLimiteEnvioComunicado', 'COLUMN';
ALTER TABLE AccionCobroA_aud ALTER COLUMN acaFechaHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobroB_aud.acbFechaGeneracion', 'acbDiasGeneracionAviso', 'COLUMN';
ALTER TABLE AccionCobroB_aud ALTER COLUMN acbHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro1C_aud.accFechaLiquidacion', 'accDiasLiquidacion', 'COLUMN';
ALTER TABLE AccionCobro1C_aud ALTER COLUMN accHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro1D1E_aud.acdFechaConteo', 'acdInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro1D1E_aud ALTER COLUMN acdHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2C_aud.aocFechaEjecucion', 'aocDiasEjecucion', 'COLUMN';
ALTER TABLE AccionCobro2C_aud ALTER COLUMN aocHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2D_aud.aodFechaConteo', 'aodInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2D_aud ALTER COLUMN aodHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2E_aud.aceFechaConteo', 'aceInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2E_aud ALTER COLUMN aceHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2F2G_aud.aofFechaConteo', 'aofInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2F2G_aud ALTER COLUMN aofHoraEjecucion DATETIME NULL;

--changeset fvasquez:13
--comment:Creacion de la tabla CarteraDependiente_aud
CREATE TABLE CarteraDependiente_aud(
	cadId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cadDeudaPresunta NUMERIC(19,5) NULL,
	cadEstadoOperacion VARCHAR(10) NOT NULL,						
	cadCartera BIGINT NOT NULL,
	cadPersona BIGINT NOT NULL,	
);
ALTER TABLE CarteraDependiente_aud ADD CONSTRAINT FK_CarteraDependiente_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
