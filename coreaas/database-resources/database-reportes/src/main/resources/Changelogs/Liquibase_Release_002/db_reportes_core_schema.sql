--liquibase formatted sql

--changeset dsuesca:01
CREATE TABLE CicloAportante (
	capId bigint NOT NULL,
	capPersona bigint NULL,
	capTipoSolicitante varchar(14) NULL,
	capCicloCartera bigint NULL,
	CONSTRAINT PK_CicloAportante_capId PRIMARY KEY (capId)
);

CREATE TABLE aud.CicloAportante_aud (
	capId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	capPersona bigint NULL,
	capTipoSolicitante varchar(14) NULL,
	capCicloCartera bigint NULL,
	revTime DATETIME NOT NULL
);

CREATE TABLE CarteraNovedad (
	canId bigint NOT NULL,
	canFechaInicio date NOT NULL,
	canFechaFin date NULL,
	canTipoNovedad varchar(100) NOT NULL,
	canCondicion bit NOT NULL,
	canAplicar bit NOT NULL,
	canNovedadFutura bit NOT NULL,
	canPersona bigint NOT NULL,
	canFechaCreacion datetime NOT NULL,
	CONSTRAINT PK_CarteraNovedad_canId PRIMARY KEY (canId)
);

CREATE TABLE aud.CarteraNovedad_aud (
	canId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	canFechaInicio date NOT NULL,
	canFechaFin date NULL,
	canTipoNovedad varchar(100) NOT NULL,
	canCondicion bit NOT NULL,
	canAplicar bit NOT NULL,
	canNovedadFutura bit NOT NULL,
	canPersona bigint NOT NULL,
	canFechaCreacion datetime NOT NULL,
	revTime DATETIME NOT NULL
)

--changeset dsuesca:02
CREATE TABLE PeriodoLiquidacion (
	pelId bigint NOT NULL,
	pelSolicitudLiquidacionSubsidio bigint NOT NULL,
	pelPeriodo bigint NOT NULL,
	pelTipoPeriodo varchar(10) COLLATE Latin1_General_CI_AI NOT NULL,
	CONSTRAINT PK_PeriodoLiquidacion_pelId PRIMARY KEY (pelId)
)

CREATE TABLE Periodo (
	priId bigint NOT NULL,
	priPeriodo date NOT NULL,
	CONSTRAINT PK_Periodo_priId PRIMARY KEY (priId),
	CONSTRAINT UK_Periodo_priPeriodo UNIQUE (priPeriodo)
)
CREATE UNIQUE INDEX UK_Periodo_priPeriodo ON Periodo (priPeriodo);

--changeset jocorrea:01
--comment:
ALTER TABLE IntegranteHogar ADD inhPostulacionFovis BIGINT 
ALTER TABLE aud.IntegranteHogar_aud ADD inhPostulacionFovis BIGINT

--changeset jocorrea:02
--comment:
ALTER TABLE IntegranteHogar ADD CONSTRAINT FK_IntegranteHogar_inhPostulacionFovis FOREIGN KEY(inhPostulacionFovis) REFERENCES PostulacionFOVIS (pofId);

--changeset jocorrea:03
--comment:
UPDATE inh
SET inh.inhPostulacionFovis = pof.pofId
FROM IntegranteHogar inh
JOIN (SELECT MAX(pof.pofId) pofId, pof.pofJefeHogar FROM PostulacionFOVIS pof GROUP BY pof.pofJefeHogar ) pof ON (pof.pofJefeHogar = inh.inhJefeHogar)

--changeset jocorrea:04
--comment:
ALTER TABLE PostulacionFOVIS ADD pofJsonPostulacion VARCHAR(MAX) NULL;
ALTER TABLE aud.PostulacionFOVIS_aud ADD pofJsonPostulacion VARCHAR(MAX) NULL;

--changeset dsuesca:03
--comment: Creacion tablas pila para reportes operativos
CREATE TABLE RegistroGeneral (
	regId bigint NOT NULL,
	regTransaccion bigint NULL,
	regEsAportePensionados bit NOT NULL,
	regNombreAportante varchar(200) NOT NULL,
	regTipoIdentificacionAportante varchar(20) NOT NULL,
	regNumeroIdentificacionAportante varchar(16) NOT NULL,
	regDigVerAportante smallint NULL,
	regPeriodoAporte varchar(7) NOT NULL,
	regTipoPlanilla varchar(1) NULL,
	regClaseAportante varchar(1) NULL,
	regCodSucursal varchar(10) NULL,
	regNomSucursal varchar(40) NULL,
	regDireccion varchar(40) NULL,
	regCodCiudad varchar(3) NULL,
	regCodDepartamento varchar(2) NULL,
	regTelefono bigint NULL,
	regFax bigint NULL,
	regEmail varchar(60) NULL,
	regFechaMatricula date NULL,
	regNaturalezaJuridica smallint NULL,
	regCantPensionados int NULL,
	regModalidadPlanilla smallint NULL,
	regValTotalApoObligatorio numeric(19,5) NULL,
	regValorIntMora numeric(19,5) NULL,
	regFechaRecaudo date NULL,
	regCodigoEntidadFinanciera smallint NULL,
	regOperadorInformacion bigint NULL,
	regNumeroCuenta varchar(17) NULL,
	regFechaActualizacion date NULL,
	regRegistroControl bigint NULL,
	regRegistroControlManual bigint NULL,
	regRegistroFControl bigint NULL,
	regOUTTarifaEmpleador numeric(5,5) NULL,
	regOUTFinalizadoProcesoManual bit NULL,
	regOUTEsEmpleador bit NULL,
	regOUTEstadoEmpleador varchar(50) NULL,
	regOUTTipoBeneficio varchar(10) NULL,
	regOUTBeneficioActivo bit NULL,
	regOUTEsEmpleadorReintegrable bit NULL,
	regOUTEstadoArchivo varchar(60) NULL,
	regNumPlanilla varchar(10) NULL,
	regNovedadFutura bit NULL,
	regOUTTarifaBaseEmpleador numeric(5,5) NULL,
	regOUTSMMLV numeric(19,5) NULL,
	regEsSimulado bit NOT NULL,
	regEstadoEvaluacion varchar(22) NOT NULL,
	regOUTMarcaSucursalPILA bit NULL,
	regOUTCodSucursalPrincipal varchar(10) NULL,
	regOUTNomSucursalPrincipal varchar(100) NULL,
	regOUTEnviadoAFiscalizacion bit NULL,
	regOUTMotivoFiscalizacion varchar(30) NULL,
	regOUTNovedadFuturaProcesada bit NULL,
	regOUTMotivoProcesoManual varchar(20) NULL,
	regNumPlanillaAsociada varchar(10) NULL,
	regOUTPrimerNombreAportante varchar(20) NULL,
	regOUTSegundoNombreAportante varchar(30) NULL,
	regOUTPrimerApellidoAportante varchar(20) NULL,
	regOUTSegundoApellidoAportante varchar(30) NULL,
	regDiasMora smallint NULL,
	regFechaPagoAporte date NULL,
	regFormaPresentacion varchar(1) NULL,
	regCantidadEmpleados int NULL,
	regCantidadAfiliados int NULL,
	regTipoPersona varchar(1) NULL,
	regOUTEnProceso bit NULL,
	regCantidadReg2 int NULL,
	regFechaPagoPlanillaAsociada date NULL,
	regOUTRegistroActual bit NULL,
	regOUTReginicial bigint NULL,
	regDateTimeInsert datetime,
	regDateTimeUpdate datetime,
	CONSTRAINT PK_RegistroGeneral PRIMARY KEY (regId)
)


CREATE TABLE RegistroDetallado (
	redId bigint NOT NULL,
	redRegistroGeneral bigint NOT NULL,
	redTipoIdentificacionCotizante varchar(20) NOT NULL,
	redNumeroIdentificacionCotizante varchar(16) NULL,
	redTipoCotizante smallint NULL,
	redCodDepartamento varchar(2) NULL,
	redCodMunicipio varchar(6) NULL,
	redPrimerApellido varchar(20) NULL,
	redSegundoApellido varchar(30) NULL,
	redPrimerNombre varchar(20) NULL,
	redSegundoNombre varchar(30) NULL,
	redNovIngreso varchar(1) NULL,
	redNovRetiro varchar(1) NULL,
	redNovVSP varchar(1) NULL,
	redNovVST varchar(1) NULL,
	redNovSLN varchar(1) NULL,
	redNovIGE varchar(1) NULL,
	redNovLMA varchar(1) NULL,
	redNovVACLR varchar(1) NULL,
	redNovSUS varchar(1) NULL,
	redDiasIRL varchar(2) NULL,
	redDiasCotizados smallint NULL,
	redSalarioBasico numeric(19,5) NULL,
	redValorIBC numeric(19,5) NULL,
	redTarifa numeric(5,5) NULL,
	redAporteObligatorio numeric(19,5) NULL,
	redCorrecciones varchar(1) NULL,
	redSalarioIntegral varchar(1) NULL,
	redFechaIngreso date NULL,
	redFechaRetiro date NULL,
	redFechaInicioVSP date NULL,
	redFechaInicioSLN date NULL,
	redFechaFinSLN date NULL,
	redFechaInicioIGE date NULL,
	redFechaFinIGE date NULL,
	redFechaInicioLMA date NULL,
	redFechaFinLMA date NULL,
	redFechaInicioVACLR date NULL,
	redFechaFinVACLR date NULL,
	redFechaInicioVCT date NULL,
	redFechaFinVCT date NULL,
	redFechaInicioIRL date NULL,
	redFechaFinIRL date NULL,
	redFechaInicioSuspension date NULL,
	redFechaFinSuspension date NULL,
	redHorasLaboradas smallint NULL,
	redRegistroControl bigint NULL,
	redOUTMarcaValRegistroAporte varchar(50) NULL,
	redOUTEstadoRegistroAporte varchar(60) NULL,
	redOUTAnalisisIntegral bit NULL,
	redOUTFechaProcesamientoValidRegAporte datetime NULL,
	redOUTEstadoValidacionV0 varchar(30) NULL,
	redOUTEstadoValidacionV1 varchar(30) NULL,
	redOUTEstadoValidacionV2 varchar(30) NULL,
	redOUTEstadoValidacionV3 varchar(30) NULL,
	redOUTClaseTrabajador varchar(50) NULL,
	redOUTPorcentajePagoAportes numeric(5,5) NULL,
	redOUTEstadoSolicitante varchar(50) NULL,
	redOUTEsTrabajadorReintegrable bit NULL,
	redOUTFechaIngresoCotizante date NULL,
	redOUTFechaUltimaNovedad date NULL,
	redOUTFechaFallecimiento date NULL,
	redUsuarioAprobadorAporte varchar(50) NOT NULL,
	redNumeroOperacionAprobacion varchar(12) NULL,
	redEstadoEvaluacion varchar(22) NOT NULL,
	redEstadoRegistroCorreccion varchar(40) NULL,
	redOUTCodSucursal varchar(10) NULL,
	redOUTNomSucursal varchar(100) NULL,
	redOUTDiasCotizadosPlanillas smallint NULL,
	redOUTDiasCotizadosBD smallint NULL,
	redOUTDiasCotizadosNovedades smallint NULL,
	redOUTTipoAfiliado varchar(50) NULL,
	redOUTRegistrado bit NULL,
	redOUTValorMoraCotizante numeric(19,5) NULL,
	redOUTAporteObligatorioMod numeric(19,5) NULL,
	redOUTDiasCotizadosMod smallint NULL,
	redOUTRegistradoAporte bit NULL,
	redOUTRegistradoNovedad bit NULL,
	redOUTTipoNovedadSituacionPrimaria varchar(15) NULL,
	redOUTFechaInicioNovedadSituacionPrimaria date NULL,
	redOUTFechaFinNovedadSituacionPrimaria date NULL,
	redOUTRegDetOriginal bigint NULL,
	redOUTEstadoRegistroRelacionAporte varchar(50) NULL,
	redOUTEstadoEvaluacionAporte varchar(22) NULL,
	redOUTFechaRetiroCotizante date NULL,
	redOUTValorIBCMod numeric(19,5) NULL,
	redOUTValorMoraCotizanteMod numeric(19,5) NULL,
	redFechaInicioVST date NULL,
	redFechaFinVST date NULL,
	redOUTDiasCotizadosNovedadesBD smallint NULL,
	redOUTGrupoFamiliarReintegrable bit NULL,
	redIdRegistro2pila bigint NULL,
	redOUTEnviadoAFiscalizacionInd bit NULL,
	redOUTMotivoFiscalizacionInd varchar(30) NULL,
	redOUTRegistroActual bit NULL,
	redOUTRegInicial bigint NULL,
	redOUTGrupoAC int NULL,
	redOUTTarifaMod numeric(5,5) NULL,
	redDateTimeInsert datetime,
	redDateTimeUpdate datetime,
	CONSTRAINT PK_RegistroDetallado PRIMARY KEY (redId)
)

CREATE TABLE RegistroDetalladoNovedad (
	rdnId int NOT NULL,
	rdnRegistroDetallado bigint NOT NULL,
	rdnTipotransaccion varchar(100) NULL,
	rdnTipoNovedad varchar(15) NOT NULL,
	rdnAccionNovedad varchar(20) NOT NULL,
	rdnMensajeNovedad varchar(250) NULL,
	rdnFechaInicioNovedad date NULL,
	rdnFechaFinNovedad date NULL,
	rdnOUTTipoAfiliado varchar(50) NULL,
	rdnDateTimeInsert datetime,
	rdnDateTimeUpdate datetime,
	CONSTRAINT PK_RegistroDetalladoNovedad_rndId PRIMARY KEY (rdnId)
)

CREATE TABLE PilaErrorValidacionLog (
	pevTipoArchivo varchar(20) NULL,
	pevTipoError varchar(20) NULL,
	pevNumeroLinea smallint NULL,
	pevBloqueValidacion varchar(11) NULL,
	pevNombreCampo varchar(150) NULL,
	pevPosicionInicial smallint NULL,
	pevPosicionFinal smallint NULL,
	pevValorCampo varchar(200) NULL,
	pevCodigoError varchar(10) NULL,
	pevMensajeError varchar(4000) NULL,
	pevEstadoInconsistencia varchar(30) NULL,
	pevIndicePlanilla bigint NULL,
	pevIndicePlanillaOF bigint NULL,
	pevId bigint NOT NULL,
	pevIdRegistroTipo2 bigint NULL,
	pevDateTimeInsert datetime,
	pevDateTimeUpdate datetime,
	CONSTRAINT PK_PilaErrorValidacionLog_pevId PRIMARY KEY (pevId)
)

CREATE TABLE PilaEstadoBloque (
	pebId bigint NOT NULL,
	pebIndicePlanilla bigint NOT NULL,
	pebTipoArchivo varchar(20) NOT NULL,
	pebEstadoBloque0 varchar(75) NULL,
	pebAccionBloque0 varchar(75) NULL,
	pebEstadoBloque1 varchar(75) NULL,
	pebAccionBloque1 varchar(75) NULL,
	pebEstadoBloque2 varchar(75) NULL,
	pebAccionBloque2 varchar(75) NULL,
	pebEstadoBloque3 varchar(75) NULL,
	pebAccionBloque3 varchar(75) NULL,
	pebEstadoBloque4 varchar(75) NULL,
	pebAccionBloque4 varchar(75) NULL,
	pebEstadoBloque5 varchar(75) NULL,
	pebAccionBloque5 varchar(75) NULL,
	pebEstadoBloque6 varchar(75) NULL,
	pebAccionBloque6 varchar(75) NULL,
	pebEstadoBloque7 varchar(75) NULL,
	pebAccionBloque7 varchar(75) NULL,
	pebEstadoBloque8 varchar(75) NULL,
	pebAccionBloque8 varchar(75) NULL,
	pebEstadoBloque9 varchar(75) NULL,
	pebAccionBloque9 varchar(75) NULL,
	pebEstadoBloque10 varchar(75) NULL,
	pebAccionBloque10 varchar(75) NULL,
	pebFechaBloque0 datetime NULL,
	pebFechaBloque1 datetime NULL,
	pebFechaBloque2 datetime NULL,
	pebFechaBloque3 datetime NULL,
	pebFechaBloque4 datetime NULL,
	pebFechaBloque5 datetime NULL,
	pebFechaBloque6 datetime NULL,
	pebFechaBloque7 datetime NULL,
	pebFechaBloque8 datetime NULL,
	pebFechaBloque9 datetime NULL,
	pebFechaBloque10 datetime NULL,
	pebDateTimeInsert datetime,
	pebDateTimeUpdate datetime,
	CONSTRAINT PK_PilaEstadoBloque_pebId PRIMARY KEY (pebId)
)

CREATE TABLE PilaIndicePlanilla (
	pipId bigint NOT NULL,
	pipIdPlanilla bigint NOT NULL,
	pipTipoArchivo varchar(20) NOT NULL,
	pipNombreArchivo varchar(80) NULL,
	pipFechaRecibo datetime NULL,
	pipFechaFtp datetime NULL,
	pipCodigoOperadorInformacion varchar(2) NULL,
	pipEstadoArchivo varchar(75) NULL,
	pipTipoCargaArchivo varchar(30) NULL,
	pipUsuario varchar(255) NULL,
	pipIdentificadorDocumento varchar(255) NULL,
	pipVersionDocumento varchar(20) NULL,
	pipFechaProceso datetime NULL,
	pipUsuarioProceso varchar(255) NULL,
	pipFechaEliminacion datetime NULL,
	pipUsuarioEliminacion varchar(255) NULL,
	pipProcesar bit NULL,
	pipRegistroActivo bit NULL,
	pipEnLista bit NULL,
	pipTamanoArchivo bigint NULL,
	pipHabilitadoProcesoManual bit NULL,
	pipPresentaRegistro4 bit NULL,
	pipMotivoProcesoManual varchar(20) NULL,
	pipDateTimeInsert datetime,
	pipDateTimeUpdate datetime,
	CONSTRAINT PK_PilaIndicePlanilla_pipId PRIMARY KEY (pipId)
)

CREATE TABLE SitioPago (
	sipId bigint NOT NULL,
	sipCodigo varchar(3) NOT NULL,
	sipNombre varchar(255) NOT NULL,
	sipInfraestructura bigint NOT NULL,
	sipActivo bit NOT NULL,
	CONSTRAINT PK_SitioPago_sipId PRIMARY KEY (sipId)
)

CREATE TABLE aud.SitioPago_aud (
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sipCodigo varchar(3) NOT NULL,
	sipNombre varchar(255) NOT NULL,
	sipInfraestructura bigint NOT NULL,
	sipActivo bit NOT NULL,
	sipId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE MedioPagoPersona (
	mppId bigint NOT NULL,
	mppMedioPago bigint NOT NULL,
	mppPersona bigint NOT NULL,
	mppMedioActivo bit NOT NULL,
	CONSTRAINT PK_MedioPagoPersona_mppId PRIMARY KEY (mppId)
)

CREATE TABLE aud.MedioPagoPersona_aud (
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mppMedioPago bigint NOT NULL,
	mppPersona bigint NOT NULL,
	mppMedioActivo bit NOT NULL,
	mppId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE OperadorInformacion (
	oinId bigint NOT NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
	CONSTRAINT PK_PilaOperadorInformacion_oinId PRIMARY KEY (oinId)
)

CREATE TABLE aud.OperadorInformacion_aud (
	oinId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
	revTime DATETIME NOT NULL
)

--changeset dsuesca:04
--comment: Creacion tabla para traer datos incrementales de tablas sin auditoria
create table Watermarktable
(
    wmtTableName varchar(255),
    wmtWatermarkValue datetime,
);

INSERT INTO Watermarktable
VALUES
('RegistroGeneral','1/1/2010 12:00:00 AM'),
('RegistroDetallado','1/1/2010 12:00:00 AM'),
('RegistroDetalladoNovedad','1/1/2010 12:00:00 AM'),
('PilaErrorValidacionLog','1/1/2010 12:00:00 AM'),
('PilaEstadoBloque','1/1/2010 12:00:00 AM'),
('PilaIndicePlanilla','1/1/2010 12:00:00 AM');

--changeset dsuesca:06
--comment: Correcion
UPDATE Watermarktable SET wmtTableName = 'staging.RegistroGeneral' WHERE wmtTableName = 'RegistroGeneral';
UPDATE Watermarktable SET wmtTableName = 'staging.RegistroDetallado' WHERE wmtTableName = 'RegistroDetallado';
UPDATE Watermarktable SET wmtTableName = 'staging.RegistroDetalladoNovedad' WHERE wmtTableName = 'RegistroDetalladoNovedad';

--changeset dsuesca:07
--comment: Correcion
IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_RegistroGeneral')
CREATE TYPE DataTypefor_RegistroGeneral AS TABLE(
	regId bigint NOT NULL,
	regTransaccion bigint NULL,
	regEsAportePensionados bit NOT NULL,
	regNombreAportante varchar(200) NOT NULL,
	regTipoIdentificacionAportante varchar(20) NOT NULL,
	regNumeroIdentificacionAportante varchar(16) NOT NULL,
	regDigVerAportante smallint NULL,
	regPeriodoAporte varchar(7) NOT NULL,
	regTipoPlanilla varchar(1) NULL,
	regClaseAportante varchar(1) NULL,
	regCodSucursal varchar(10) NULL,
	regNomSucursal varchar(40) NULL,
	regDireccion varchar(40) NULL,
	regCodCiudad varchar(3) NULL,
	regCodDepartamento varchar(2) NULL,
	regTelefono bigint NULL,
	regFax bigint NULL,
	regEmail varchar(60) NULL,
	regFechaMatricula date NULL,
	regNaturalezaJuridica smallint NULL,
	regCantPensionados int NULL,
	regModalidadPlanilla smallint NULL,
	regValTotalApoObligatorio numeric(19,5) NULL,
	regValorIntMora numeric(19,5) NULL,
	regFechaRecaudo date NULL,
	regCodigoEntidadFinanciera smallint NULL,
	regOperadorInformacion bigint NULL,
	regNumeroCuenta varchar(17) NULL,
	regFechaActualizacion date NULL,
	regRegistroControl bigint NULL,
	regRegistroControlManual bigint NULL,
	regRegistroFControl bigint NULL,
	regOUTTarifaEmpleador numeric(5,5) NULL,
	regOUTFinalizadoProcesoManual bit NULL,
	regOUTEsEmpleador bit NULL,
	regOUTEstadoEmpleador varchar(50) NULL,
	regOUTTipoBeneficio varchar(10) NULL,
	regOUTBeneficioActivo bit NULL,
	regOUTEsEmpleadorReintegrable bit NULL,
	regOUTEstadoArchivo varchar(60) NULL,
	regNumPlanilla varchar(10) NULL,
	regNovedadFutura bit NULL,
	regOUTTarifaBaseEmpleador numeric(5,5) NULL,
	regOUTSMMLV numeric(19,5) NULL,
	regEsSimulado bit NOT NULL,
	regEstadoEvaluacion varchar(22) NOT NULL,
	regOUTMarcaSucursalPILA bit NULL,
	regOUTCodSucursalPrincipal varchar(10) NULL,
	regOUTNomSucursalPrincipal varchar(100) NULL,
	regOUTEnviadoAFiscalizacion bit NULL,
	regOUTMotivoFiscalizacion varchar(30) NULL,
	regOUTNovedadFuturaProcesada bit NULL,
	regOUTMotivoProcesoManual varchar(20) NULL,
	regNumPlanillaAsociada varchar(10) NULL,
	regOUTPrimerNombreAportante varchar(20) NULL,
	regOUTSegundoNombreAportante varchar(30) NULL,
	regOUTPrimerApellidoAportante varchar(20) NULL,
	regOUTSegundoApellidoAportante varchar(30) NULL,
	regDiasMora smallint NULL,
	regFechaPagoAporte date NULL,
	regFormaPresentacion varchar(1) NULL,
	regCantidadEmpleados int NULL,
	regCantidadAfiliados int NULL,
	regTipoPersona varchar(1) NULL,
	regOUTEnProceso bit NULL,
	regCantidadReg2 int NULL,
	regFechaPagoPlanillaAsociada date NULL,
	regOUTRegistroActual bit NULL,
	regOUTReginicial bigint NULL,
	regDateTimeInsert datetime,
	regDateTimeUpdate datetime
);

IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_RegistroDetallado')
CREATE TYPE DataTypefor_RegistroDetallado  AS TABLE (
	redId bigint NOT NULL,
	redRegistroGeneral bigint NOT NULL,
	redTipoIdentificacionCotizante varchar(20) NOT NULL,
	redNumeroIdentificacionCotizante varchar(16) NULL,
	redTipoCotizante smallint NULL,
	redCodDepartamento varchar(2) NULL,
	redCodMunicipio varchar(6) NULL,
	redPrimerApellido varchar(20) NULL,
	redSegundoApellido varchar(30) NULL,
	redPrimerNombre varchar(20) NULL,
	redSegundoNombre varchar(30) NULL,
	redNovIngreso varchar(1) NULL,
	redNovRetiro varchar(1) NULL,
	redNovVSP varchar(1) NULL,
	redNovVST varchar(1) NULL,
	redNovSLN varchar(1) NULL,
	redNovIGE varchar(1) NULL,
	redNovLMA varchar(1) NULL,
	redNovVACLR varchar(1) NULL,
	redNovSUS varchar(1) NULL,
	redDiasIRL varchar(2) NULL,
	redDiasCotizados smallint NULL,
	redSalarioBasico numeric(19,5) NULL,
	redValorIBC numeric(19,5) NULL,
	redTarifa numeric(5,5) NULL,
	redAporteObligatorio numeric(19,5) NULL,
	redCorrecciones varchar(1) NULL,
	redSalarioIntegral varchar(1) NULL,
	redFechaIngreso date NULL,
	redFechaRetiro date NULL,
	redFechaInicioVSP date NULL,
	redFechaInicioSLN date NULL,
	redFechaFinSLN date NULL,
	redFechaInicioIGE date NULL,
	redFechaFinIGE date NULL,
	redFechaInicioLMA date NULL,
	redFechaFinLMA date NULL,
	redFechaInicioVACLR date NULL,
	redFechaFinVACLR date NULL,
	redFechaInicioVCT date NULL,
	redFechaFinVCT date NULL,
	redFechaInicioIRL date NULL,
	redFechaFinIRL date NULL,
	redFechaInicioSuspension date NULL,
	redFechaFinSuspension date NULL,
	redHorasLaboradas smallint NULL,
	redRegistroControl bigint NULL,
	redOUTMarcaValRegistroAporte varchar(50) NULL,
	redOUTEstadoRegistroAporte varchar(60) NULL,
	redOUTAnalisisIntegral bit NULL,
	redOUTFechaProcesamientoValidRegAporte datetime NULL,
	redOUTEstadoValidacionV0 varchar(30) NULL,
	redOUTEstadoValidacionV1 varchar(30) NULL,
	redOUTEstadoValidacionV2 varchar(30) NULL,
	redOUTEstadoValidacionV3 varchar(30) NULL,
	redOUTClaseTrabajador varchar(50) NULL,
	redOUTPorcentajePagoAportes numeric(5,5) NULL,
	redOUTEstadoSolicitante varchar(50) NULL,
	redOUTEsTrabajadorReintegrable bit NULL,
	redOUTFechaIngresoCotizante date NULL,
	redOUTFechaUltimaNovedad date NULL,
	redOUTFechaFallecimiento date NULL,
	redUsuarioAprobadorAporte varchar(50) NOT NULL,
	redNumeroOperacionAprobacion varchar(12) NULL,
	redEstadoEvaluacion varchar(22) NOT NULL,
	redEstadoRegistroCorreccion varchar(40) NULL,
	redOUTCodSucursal varchar(10) NULL,
	redOUTNomSucursal varchar(100) NULL,
	redOUTDiasCotizadosPlanillas smallint NULL,
	redOUTDiasCotizadosBD smallint NULL,
	redOUTDiasCotizadosNovedades smallint NULL,
	redOUTTipoAfiliado varchar(50) NULL,
	redOUTRegistrado bit NULL,
	redOUTValorMoraCotizante numeric(19,5) NULL,
	redOUTAporteObligatorioMod numeric(19,5) NULL,
	redOUTDiasCotizadosMod smallint NULL,
	redOUTRegistradoAporte bit NULL,
	redOUTRegistradoNovedad bit NULL,
	redOUTTipoNovedadSituacionPrimaria varchar(15) NULL,
	redOUTFechaInicioNovedadSituacionPrimaria date NULL,
	redOUTFechaFinNovedadSituacionPrimaria date NULL,
	redOUTRegDetOriginal bigint NULL,
	redOUTEstadoRegistroRelacionAporte varchar(50) NULL,
	redOUTEstadoEvaluacionAporte varchar(22) NULL,
	redOUTFechaRetiroCotizante date NULL,
	redOUTValorIBCMod numeric(19,5) NULL,
	redOUTValorMoraCotizanteMod numeric(19,5) NULL,
	redFechaInicioVST date NULL,
	redFechaFinVST date NULL,
	redOUTDiasCotizadosNovedadesBD smallint NULL,
	redOUTGrupoFamiliarReintegrable bit NULL,
	redIdRegistro2pila bigint NULL,
	redOUTEnviadoAFiscalizacionInd bit NULL,
	redOUTMotivoFiscalizacionInd varchar(30) NULL,
	redOUTRegistroActual bit NULL,
	redOUTRegInicial bigint NULL,
	redOUTGrupoAC int NULL,
	redOUTTarifaMod numeric(5,5) NULL,
	redDateTimeInsert datetime,
	redDateTimeUpdate datetime
)

IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_RegistroDetalladoNovedad')
CREATE TYPE DataTypefor_RegistroDetalladoNovedad  AS TABLE (
	rdnId int NOT NULL,
	rdnRegistroDetallado bigint NOT NULL,
	rdnTipotransaccion varchar(100) NULL,
	rdnTipoNovedad varchar(15) NOT NULL,
	rdnAccionNovedad varchar(20) NOT NULL,
	rdnMensajeNovedad varchar(250) NULL,
	rdnFechaInicioNovedad date NULL,
	rdnFechaFinNovedad date NULL,
	rdnOUTTipoAfiliado varchar(50) NULL,
	rdnDateTimeInsert datetime,
	rdnDateTimeUpdate datetime
)

IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_PilaErrorValidacionLog')
CREATE TYPE DataTypefor_PilaErrorValidacionLog  AS TABLE  (
	pevTipoArchivo varchar(20) NULL,
	pevTipoError varchar(20) NULL,
	pevNumeroLinea smallint NULL,
	pevBloqueValidacion varchar(11) NULL,
	pevNombreCampo varchar(150) NULL,
	pevPosicionInicial smallint NULL,
	pevPosicionFinal smallint NULL,
	pevValorCampo varchar(200) NULL,
	pevCodigoError varchar(10) NULL,
	pevMensajeError varchar(4000) NULL,
	pevEstadoInconsistencia varchar(30) NULL,
	pevIndicePlanilla bigint NULL,
	pevIndicePlanillaOF bigint NULL,
	pevId bigint NOT NULL,
	pevIdRegistroTipo2 bigint NULL,
	pevDateTimeInsert datetime,
	pevDateTimeUpdate datetime
)

IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_PilaEstadoBloque')
CREATE TYPE DataTypefor_PilaEstadoBloque  AS TABLE (
	pebId bigint NOT NULL,
	pebIndicePlanilla bigint NOT NULL,
	pebTipoArchivo varchar(20) NOT NULL,
	pebEstadoBloque0 varchar(75) NULL,
	pebAccionBloque0 varchar(75) NULL,
	pebEstadoBloque1 varchar(75) NULL,
	pebAccionBloque1 varchar(75) NULL,
	pebEstadoBloque2 varchar(75) NULL,
	pebAccionBloque2 varchar(75) NULL,
	pebEstadoBloque3 varchar(75) NULL,
	pebAccionBloque3 varchar(75) NULL,
	pebEstadoBloque4 varchar(75) NULL,
	pebAccionBloque4 varchar(75) NULL,
	pebEstadoBloque5 varchar(75) NULL,
	pebAccionBloque5 varchar(75) NULL,
	pebEstadoBloque6 varchar(75) NULL,
	pebAccionBloque6 varchar(75) NULL,
	pebEstadoBloque7 varchar(75) NULL,
	pebAccionBloque7 varchar(75) NULL,
	pebEstadoBloque8 varchar(75) NULL,
	pebAccionBloque8 varchar(75) NULL,
	pebEstadoBloque9 varchar(75) NULL,
	pebAccionBloque9 varchar(75) NULL,
	pebEstadoBloque10 varchar(75) NULL,
	pebAccionBloque10 varchar(75) NULL,
	pebFechaBloque0 datetime NULL,
	pebFechaBloque1 datetime NULL,
	pebFechaBloque2 datetime NULL,
	pebFechaBloque3 datetime NULL,
	pebFechaBloque4 datetime NULL,
	pebFechaBloque5 datetime NULL,
	pebFechaBloque6 datetime NULL,
	pebFechaBloque7 datetime NULL,
	pebFechaBloque8 datetime NULL,
	pebFechaBloque9 datetime NULL,
	pebFechaBloque10 datetime NULL,
	pebDateTimeInsert datetime,
	pebDateTimeUpdate datetime
)

IF NOT EXISTS (SELECT 1 FROM sys.types WHERE is_table_type = 1 AND name = 'DataTypefor_PilaIndicePlanilla')
CREATE TYPE DataTypefor_PilaIndicePlanilla  AS TABLE (
	pipId bigint NOT NULL,
	pipIdPlanilla bigint NOT NULL,
	pipTipoArchivo varchar(20) NOT NULL,
	pipNombreArchivo varchar(80) NULL,
	pipFechaRecibo datetime NULL,
	pipFechaFtp datetime NULL,
	pipCodigoOperadorInformacion varchar(2) NULL,
	pipEstadoArchivo varchar(75) NULL,
	pipTipoCargaArchivo varchar(30) NULL,
	pipUsuario varchar(255) NULL,
	pipIdentificadorDocumento varchar(255) NULL,
	pipVersionDocumento varchar(20) NULL,
	pipFechaProceso datetime NULL,
	pipUsuarioProceso varchar(255) NULL,
	pipFechaEliminacion datetime NULL,
	pipUsuarioEliminacion varchar(255) NULL,
	pipProcesar bit NULL,
	pipRegistroActivo bit NULL,
	pipEnLista bit NULL,
	pipTamanoArchivo bigint NULL,
	pipHabilitadoProcesoManual bit NULL,
	pipPresentaRegistro4 bit NULL,
	pipMotivoProcesoManual varchar(20) NULL,
	pipDateTimeInsert datetime,
	pipDateTimeUpdate datetime
)

--changeset dsuesca:08
--comment: Correcion wmtWatermarkValue
UPDATE Watermarktable SET wmtWatermarkValue = '1969-01-01 00:00:00.000';

--changeset dsuesca:09
--comment: 
ALTER TABLE IntegranteHogar DROP CONSTRAINT FK_IntegranteHogar_inhPostulacionFovis;
ALTER TABLE PostulacionFOVIS DROP CONSTRAINT FK_PostulacionFovis_pofUbicacionVivienda;

--changeset dsuesca:10
--comment: 
ALTER TABLE aud.DetalleSubsidioAsignado_aud ADD dsaDetalleSolicitudAnulacionSubsidioCobrado BIGINT;
ALTER TABLE DetalleSubsidioAsignado ADD dsaDetalleSolicitudAnulacionSubsidioCobrado BIGINT;

--changeset mamonroy:11
--comment:
ALTER TABLE IntentoAfiliacion ADD iafComunicado BIGINT;
ALTER TABLE aud.IntentoAfiliacion_aud ADD iafComunicado BIGINT;

--changeset clmarin:01
--comment:
ALTER TABLE agendacartera ADD agrEsVigente BIT;
ALTER TABLE actividadcartera ADD acrEsVigente BIT;
ALTER TABLE aud.agendacartera_aud ADD agrEsVigente BIT;
ALTER TABLE aud.actividadcartera_aud ADD acrEsVigente BIT;

--changeset dsuesca:11
--comment:
ALTER TABLE RegistroDetallado ADD redOUTPeriodicidad VARCHAR(11);

--changeset dsuesca:13
--comment: type para crearle nuevo campo, se añade un _ al final
EXEC sys.sp_rename 'dbo.DataTypefor_RegistroDetallado', 'DataTypefor_RegistroDetallado_aux1';

CREATE TYPE DataTypefor_RegistroDetallado  AS TABLE (
	redId bigint NOT NULL,
	redRegistroGeneral bigint NOT NULL,
	redTipoIdentificacionCotizante varchar(20) NOT NULL,
	redNumeroIdentificacionCotizante varchar(16) NULL,
	redTipoCotizante smallint NULL,
	redCodDepartamento varchar(2) NULL,
	redCodMunicipio varchar(6) NULL,
	redPrimerApellido varchar(20) NULL,
	redSegundoApellido varchar(30) NULL,
	redPrimerNombre varchar(20) NULL,
	redSegundoNombre varchar(30) NULL,
	redNovIngreso varchar(1) NULL,
	redNovRetiro varchar(1) NULL,
	redNovVSP varchar(1) NULL,
	redNovVST varchar(1) NULL,
	redNovSLN varchar(1) NULL,
	redNovIGE varchar(1) NULL,
	redNovLMA varchar(1) NULL,
	redNovVACLR varchar(1) NULL,
	redNovSUS varchar(1) NULL,
	redDiasIRL varchar(2) NULL,
	redDiasCotizados smallint NULL,
	redSalarioBasico numeric(19,5) NULL,
	redValorIBC numeric(19,5) NULL,
	redTarifa numeric(5,5) NULL,
	redAporteObligatorio numeric(19,5) NULL,
	redCorrecciones varchar(1) NULL,
	redSalarioIntegral varchar(1) NULL,
	redFechaIngreso date NULL,
	redFechaRetiro date NULL,
	redFechaInicioVSP date NULL,
	redFechaInicioSLN date NULL,
	redFechaFinSLN date NULL,
	redFechaInicioIGE date NULL,
	redFechaFinIGE date NULL,
	redFechaInicioLMA date NULL,
	redFechaFinLMA date NULL,
	redFechaInicioVACLR date NULL,
	redFechaFinVACLR date NULL,
	redFechaInicioVCT date NULL,
	redFechaFinVCT date NULL,
	redFechaInicioIRL date NULL,
	redFechaFinIRL date NULL,
	redFechaInicioSuspension date NULL,
	redFechaFinSuspension date NULL,
	redHorasLaboradas smallint NULL,
	redRegistroControl bigint NULL,
	redOUTMarcaValRegistroAporte varchar(50) NULL,
	redOUTEstadoRegistroAporte varchar(60) NULL,
	redOUTAnalisisIntegral bit NULL,
	redOUTFechaProcesamientoValidRegAporte datetime NULL,
	redOUTEstadoValidacionV0 varchar(30) NULL,
	redOUTEstadoValidacionV1 varchar(30) NULL,
	redOUTEstadoValidacionV2 varchar(30) NULL,
	redOUTEstadoValidacionV3 varchar(30) NULL,
	redOUTClaseTrabajador varchar(50) NULL,
	redOUTPorcentajePagoAportes numeric(5,5) NULL,
	redOUTEstadoSolicitante varchar(50) NULL,
	redOUTEsTrabajadorReintegrable bit NULL,
	redOUTFechaIngresoCotizante date NULL,
	redOUTFechaUltimaNovedad date NULL,
	redOUTFechaFallecimiento date NULL,
	redUsuarioAprobadorAporte varchar(50) NOT NULL,
	redNumeroOperacionAprobacion varchar(12) NULL,
	redEstadoEvaluacion varchar(22) NOT NULL,
	redEstadoRegistroCorreccion varchar(40) NULL,
	redOUTCodSucursal varchar(10) NULL,
	redOUTNomSucursal varchar(100) NULL,
	redOUTDiasCotizadosPlanillas smallint NULL,
	redOUTDiasCotizadosBD smallint NULL,
	redOUTDiasCotizadosNovedades smallint NULL,
	redOUTTipoAfiliado varchar(50) NULL,
	redOUTRegistrado bit NULL,
	redOUTValorMoraCotizante numeric(19,5) NULL,
	redOUTAporteObligatorioMod numeric(19,5) NULL,
	redOUTDiasCotizadosMod smallint NULL,
	redOUTRegistradoAporte bit NULL,
	redOUTRegistradoNovedad bit NULL,
	redOUTTipoNovedadSituacionPrimaria varchar(15) NULL,
	redOUTFechaInicioNovedadSituacionPrimaria date NULL,
	redOUTFechaFinNovedadSituacionPrimaria date NULL,
	redOUTRegDetOriginal bigint NULL,
	redOUTEstadoRegistroRelacionAporte varchar(50) NULL,
	redOUTEstadoEvaluacionAporte varchar(22) NULL,
	redOUTFechaRetiroCotizante date NULL,
	redOUTValorIBCMod numeric(19,5) NULL,
	redOUTValorMoraCotizanteMod numeric(19,5) NULL,
	redFechaInicioVST date NULL,
	redFechaFinVST date NULL,
	redOUTDiasCotizadosNovedadesBD smallint NULL,
	redOUTGrupoFamiliarReintegrable bit NULL,
	redIdRegistro2pila bigint NULL,
	redOUTEnviadoAFiscalizacionInd bit NULL,
	redOUTMotivoFiscalizacionInd varchar(30) NULL,
	redOUTRegistroActual bit NULL,
	redOUTRegInicial bigint NULL,
	redOUTGrupoAC int NULL,
	redOUTTarifaMod numeric(5,5) NULL,
	redDateTimeInsert datetime,
	redDateTimeUpdate datetime,
	redOUTPeriodicidad VARCHAR(11)
);

--changeset dsuesca:14
--comment:
ALTER TABLE rno.HistoricoMaestroAfiliados ADD hmaCondicionBeneficiario VARCHAR(1);

--changeset dsuesca:15
--comment:
CREATE TABLE PostulacionAsignacion (
	pasId bigint NOT NULL,
	pasPostulacionFovis bigint NOT NULL,
	pasCicloAsignacion bigint NOT NULL,
	pasCalificacionPostulacion bigint NULL,
	pasSolicitudAsignacion bigint NULL,
	pasPrioridadAsignacion varchar(11) COLLATE Latin1_General_CI_AI NULL,
	pasValorAsignadoSFV numeric(19,5) NULL,
	pasResultadoAsignacion varchar(50) COLLATE Latin1_General_CI_AI NULL,
	pasDocumentoActaAsignacion varchar(255) COLLATE Latin1_General_CI_AI NULL
);

CREATE TABLE aud.PostulacionAsignacion_aud (
	pasId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pasPostulacionFovis bigint NOT NULL,
	pasCicloAsignacion bigint NOT NULL,
	pasCalificacionPostulacion bigint NULL,
	pasSolicitudAsignacion bigint NULL,
	pasPrioridadAsignacion varchar(11) COLLATE Latin1_General_CI_AI NULL,
	pasValorAsignadoSFV numeric(19,5) NULL,
	pasResultadoAsignacion varchar(50) COLLATE Latin1_General_CI_AI NULL,
	pasDocumentoActaAsignacion varchar(255) COLLATE Latin1_General_CI_AI NULL,
	revTime DATETIME NOT NULL
);

--changeset dsuesca:16
--comment:
ALTER TABLE rno.EjecucionReporteNormativo ADD ernFechaInicio DATE;
ALTER TABLE rno.EjecucionReporteNormativo ADD ernFechaFin DATE;


--changeset clmarin:17
ALTER TABLE RolAfiliado ADD roaMunicipioDesempenioLabores smallint NULL;
ALTER TABLE aud.RolAfiliado_aud ADD roaMunicipioDesempenioLabores smallint NULL;

--changeset squintero:01
--comment: 
ALTER TABLE RegistroDetallado
ADD redUsuarioAccion varchar(100);

ALTER TABLE RegistroDetallado
ADD redFechaAccion datetime;

--changeset dsuesca:17
--comment: type para crearle nuevo campo, se añade un _ al final
EXEC sys.sp_rename 'dbo.DataTypefor_RegistroDetallado', 'DataTypefor_RegistroDetallado_aux2';

CREATE TYPE DataTypefor_RegistroDetallado  AS TABLE (
	redId bigint NOT NULL,
	redRegistroGeneral bigint NOT NULL,
	redTipoIdentificacionCotizante varchar(20) NOT NULL,
	redNumeroIdentificacionCotizante varchar(16) NULL,
	redTipoCotizante smallint NULL,
	redCodDepartamento varchar(2) NULL,
	redCodMunicipio varchar(6) NULL,
	redPrimerApellido varchar(20) NULL,
	redSegundoApellido varchar(30) NULL,
	redPrimerNombre varchar(20) NULL,
	redSegundoNombre varchar(30) NULL,
	redNovIngreso varchar(1) NULL,
	redNovRetiro varchar(1) NULL,
	redNovVSP varchar(1) NULL,
	redNovVST varchar(1) NULL,
	redNovSLN varchar(1) NULL,
	redNovIGE varchar(1) NULL,
	redNovLMA varchar(1) NULL,
	redNovVACLR varchar(1) NULL,
	redNovSUS varchar(1) NULL,
	redDiasIRL varchar(2) NULL,
	redDiasCotizados smallint NULL,
	redSalarioBasico numeric(19,5) NULL,
	redValorIBC numeric(19,5) NULL,
	redTarifa numeric(5,5) NULL,
	redAporteObligatorio numeric(19,5) NULL,
	redCorrecciones varchar(1) NULL,
	redSalarioIntegral varchar(1) NULL,
	redFechaIngreso date NULL,
	redFechaRetiro date NULL,
	redFechaInicioVSP date NULL,
	redFechaInicioSLN date NULL,
	redFechaFinSLN date NULL,
	redFechaInicioIGE date NULL,
	redFechaFinIGE date NULL,
	redFechaInicioLMA date NULL,
	redFechaFinLMA date NULL,
	redFechaInicioVACLR date NULL,
	redFechaFinVACLR date NULL,
	redFechaInicioVCT date NULL,
	redFechaFinVCT date NULL,
	redFechaInicioIRL date NULL,
	redFechaFinIRL date NULL,
	redFechaInicioSuspension date NULL,
	redFechaFinSuspension date NULL,
	redHorasLaboradas smallint NULL,
	redRegistroControl bigint NULL,
	redOUTMarcaValRegistroAporte varchar(50) NULL,
	redOUTEstadoRegistroAporte varchar(60) NULL,
	redOUTAnalisisIntegral bit NULL,
	redOUTFechaProcesamientoValidRegAporte datetime NULL,
	redOUTEstadoValidacionV0 varchar(30) NULL,
	redOUTEstadoValidacionV1 varchar(30) NULL,
	redOUTEstadoValidacionV2 varchar(30) NULL,
	redOUTEstadoValidacionV3 varchar(30) NULL,
	redOUTClaseTrabajador varchar(50) NULL,
	redOUTPorcentajePagoAportes numeric(5,5) NULL,
	redOUTEstadoSolicitante varchar(50) NULL,
	redOUTEsTrabajadorReintegrable bit NULL,
	redOUTFechaIngresoCotizante date NULL,
	redOUTFechaUltimaNovedad date NULL,
	redOUTFechaFallecimiento date NULL,
	redUsuarioAprobadorAporte varchar(50) NOT NULL,
	redNumeroOperacionAprobacion varchar(12) NULL,
	redEstadoEvaluacion varchar(22) NOT NULL,
	redEstadoRegistroCorreccion varchar(40) NULL,
	redOUTCodSucursal varchar(10) NULL,
	redOUTNomSucursal varchar(100) NULL,
	redOUTDiasCotizadosPlanillas smallint NULL,
	redOUTDiasCotizadosBD smallint NULL,
	redOUTDiasCotizadosNovedades smallint NULL,
	redOUTTipoAfiliado varchar(50) NULL,
	redOUTRegistrado bit NULL,
	redOUTValorMoraCotizante numeric(19,5) NULL,
	redOUTAporteObligatorioMod numeric(19,5) NULL,
	redOUTDiasCotizadosMod smallint NULL,
	redOUTRegistradoAporte bit NULL,
	redOUTRegistradoNovedad bit NULL,
	redOUTTipoNovedadSituacionPrimaria varchar(15) NULL,
	redOUTFechaInicioNovedadSituacionPrimaria date NULL,
	redOUTFechaFinNovedadSituacionPrimaria date NULL,
	redOUTRegDetOriginal bigint NULL,
	redOUTEstadoRegistroRelacionAporte varchar(50) NULL,
	redOUTEstadoEvaluacionAporte varchar(22) NULL,
	redOUTFechaRetiroCotizante date NULL,
	redOUTValorIBCMod numeric(19,5) NULL,
	redOUTValorMoraCotizanteMod numeric(19,5) NULL,
	redFechaInicioVST date NULL,
	redFechaFinVST date NULL,
	redOUTDiasCotizadosNovedadesBD smallint NULL,
	redOUTGrupoFamiliarReintegrable bit NULL,
	redIdRegistro2pila bigint NULL,
	redOUTEnviadoAFiscalizacionInd bit NULL,
	redOUTMotivoFiscalizacionInd varchar(30) NULL,
	redOUTRegistroActual bit NULL,
	redOUTRegInicial bigint NULL,
	redOUTGrupoAC int NULL,
	redOUTTarifaMod numeric(5,5) NULL,
	redDateTimeInsert datetime,
	redDateTimeUpdate datetime,
	redOUTPeriodicidad VARCHAR(11),
	redUsuarioAccion VARCHAR(100),
	redFechaAccion DATETIME
);

--changeset dsuesca:18
ALTER TABLE AporteDetallado ADD apdModalidadRecaudoAporte VARCHAR(40);
ALTER TABLE aud.AporteDetallado_aud ADD apdModalidadRecaudoAporte VARCHAR(40);
ALTER TABLE AporteDetallado ADD apdMarcaCalculoCategoria BIT;
ALTER TABLE aud.AporteDetallado_aud ADD apdMarcaCalculoCategoria BIT;

--changeset clmarin:19
ALTER TABLE AporteDetallado add apdModificadoAportesOK bit null;
ALTER TABLE aud.AporteDetallado_aud add apdModificadoAportesOK bit null;

--changeset mamonroy:20
ALTER TABLE CondicionInvalidez ADD coiFechaInicioInvalidez DATE;
ALTER TABLE aud.CondicionInvalidez_aud ADD coiFechaInicioInvalidez DATE;

--changeset clmarin:20
ALTER TABLE Beneficiario ADD benOmitirValidaciones bit NULL;
ALTER TABLE aud.Beneficiario_aud ADD benOmitirValidaciones bit NULL;

--changeset mamonroy:21
CREATE TABLE IntentoPostulacion(
	ipoId bigint NOT NULL,
	ipoCausaIntentoFallido varchar(50) NULL,
	ipoFechaCreacion datetime NULL,
	ipoFechaInicioProceso datetime NULL,
	ipoSedeCajaCompensacion varchar(2) NULL,
	ipoTipoTransaccion varchar(100) NULL,
	ipoUsuarioCreacion varchar(255) NULL,
	ipoSolicitud bigint NULL,
	ipoProceso varchar(32) NULL,
	ipoTipoSolicitante varchar(5) NULL,
	ipoModalidad varchar(33) NULL,
 CONSTRAINT PK_IntentoPostulacion_ipoId PRIMARY KEY (ipoId)
 )

CREATE TABLE aud.IntentoPostulacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ipoCausaIntentoFallido varchar(50) NULL,
	ipoFechaCreacion datetime NULL,
	ipoFechaInicioProceso datetime NULL,
	ipoSedeCajaCompensacion varchar(2) NULL,
	ipoTipoTransaccion varchar(100) NULL,
	ipoUsuarioCreacion varchar(255) NULL,
	ipoSolicitud bigint NULL,
	ipoProceso varchar(32) NULL,
	ipoTipoSolicitante varchar(5) NULL,
	ipoModalidad varchar(33) NULL,
	ipoId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE CalificacionPostulacion(
	cafId bigint NOT NULL,
	cafPostulacionFovis bigint NOT NULL,
	cafCicloAsignacion bigint NOT NULL,
	cafFechaCalificacion datetime NULL,
	cafPuntaje numeric(12, 6) NULL,
	cafValorB1 numeric(10, 6) NULL,
	cafValorB2 numeric(10, 6) NULL,
	cafValorB3 numeric(10, 6) NULL,
	cafValorB4 numeric(12, 6) NULL,
	cafValorB5 numeric(10, 6) NULL,
	cafValorB6 numeric(10, 6) NULL,
	cafValorParte7 numeric(12, 6) NULL,
	cafValorParte8 numeric(12, 6) NULL,
 CONSTRAINT PK_CalificacionPostulacion_cafId PRIMARY KEY (cafId)
)

CREATE TABLE aud.CalificacionPostulacion_aud(
	cafId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cafPostulacionFovis bigint NOT NULL,
	cafCicloAsignacion bigint NOT NULL,
	cafFechaCalificacion datetime NULL,
	cafPuntaje numeric(12, 6) NULL,
	cafValorB1 numeric(10, 6) NULL,
	cafValorB2 numeric(10, 6) NULL,
	cafValorB3 numeric(10, 6) NULL,
	cafValorB4 numeric(12, 6) NULL,
	cafValorB5 numeric(10, 6) NULL,
	cafValorB6 numeric(10, 6) NULL,
	cafValorParte7 numeric(12, 6) NULL,
	cafValorParte8 numeric(12, 6) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE MedioPagoProyectoVivienda(
	mprId bigint NOT NULL,
	mprProyectoSolucionVivienda bigint NOT NULL,
	mprMedioDePago bigint NOT NULL,
	mprActivo bit NULL,
 CONSTRAINT PK_MedioPagoProyectoVivienda_mprId PRIMARY KEY (mprId)
)

CREATE TABLE aud.MedioPagoProyectoVivienda_aud(
	mprId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mprProyectoSolucionVivienda bigint NOT NULL,
	mprMedioDePago bigint NOT NULL,
	mprActivo bit NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE Visita(
	visId bigint NOT NULL,
	visFecha date NOT NULL,
	visNombresEncargado varchar(50) NOT NULL,
	visCodigoIdentificadorECM varchar(255) NOT NULL,
 CONSTRAINT PK_Visita_visId PRIMARY KEY (visId)
)

CREATE TABLE aud.Visita_aud(
	visId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	visFecha date NOT NULL,
	visNombresEncargado varchar(50) NOT NULL,
	visCodigoIdentificadorECM varchar(255) NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE CondicionVisita(
	covId bigint NOT NULL,
	covCondicion varchar(42) NOT NULL,
	covCumple bit NOT NULL,
	covObservacion varchar(250) NULL,
	covVisita bigint NOT NULL,
 CONSTRAINT PK_CondicionVisita_covId PRIMARY KEY (covId)
)

CREATE TABLE aud.CondicionVisita_aud(
	covId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	covCondicion varchar(42) NOT NULL,
	covCumple bit NOT NULL,
	covObservacion varchar(250) NULL,
	covVisita bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE MedioConsignacion(
	mdpId bigint NOT NULL,
	mcoBanco bigint NOT NULL,
	mcoTipoCuenta varchar(30) NOT NULL,
	mcoNumeroCuenta varchar(30) NOT NULL,
	mcoTipoIdentificacionTitular varchar(20) NULL,
	mcoNumeroIdentificacionTitular varchar(16) NULL,
	mcoDigitoVerificacionTitular smallint NULL,
	mcoNombreTitularCuenta varchar(200) NULL,
 CONSTRAINT PK_MedioConsignacion_mdpId PRIMARY KEY (mdpId)
)

CREATE TABLE aud.MedioConsignacion_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mcoBanco bigint NOT NULL,
	mcoTipoCuenta varchar(30) NOT NULL,
	mcoNumeroCuenta varchar(30) NOT NULL,
	mcoTipoIdentificacionTitular varchar(20) NULL,
	mcoNumeroIdentificacionTitular varchar(16) NULL,
	mcoDigitoVerificacionTitular smallint NULL,
	mcoNombreTitularCuenta varchar(200) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE Infraestructura(
	infId bigint NOT NULL,
	infCodigoParaSSF varchar(13) NOT NULL,
	infCodigoParaCCF varchar(12) NOT NULL,
	infConsecutivoInfraestructura smallint NOT NULL,
	infNombre varchar(255) NOT NULL,
	infTipoInfraestructura bigint NOT NULL,
	infZona varchar(255) NOT NULL,
	infDireccion varchar(300) NOT NULL,
	infAreaGeografica varchar(6) NOT NULL,
	infMunicipio bigint NOT NULL,
	infTipoTenencia bigint NOT NULL,
	infLatitud numeric(9, 6) NOT NULL,
	infLongitud numeric(9, 6) NOT NULL,
	infActivo bit NOT NULL,
	infCapacidadEstimada numeric(7, 2) NULL,
 CONSTRAINT PK_Infraestructura_infId PRIMARY KEY (infId)
)

CREATE TABLE aud.Infraestructura_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	infCodigoParaSSF varchar(13) NOT NULL,
	infCodigoParaCCF varchar(12) NOT NULL,
	infConsecutivoInfraestructura smallint NOT NULL,
	infNombre varchar(255) NOT NULL,
	infTipoInfraestructura bigint NOT NULL,
	infZona varchar(255) NOT NULL,
	infDireccion varchar(300) NOT NULL,
	infAreaGeografica varchar(6) NOT NULL,
	infMunicipio bigint NOT NULL,
	infTipoTenencia bigint NOT NULL,
	infLatitud numeric(9, 6) NOT NULL,
	infLongitud numeric(9, 6) NOT NULL,
	infActivo bit NOT NULL,
	infCapacidadEstimada numeric(7, 2) NULL,
	infId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE Banco(
	banId bigint NOT NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit NULL,
	banCodigo varchar(6) NULL CONSTRAINT DF_Banco_banCodigo  DEFAULT ('000000'),
	banNit varchar(18) NULL,
 CONSTRAINT PK_Banco_banId PRIMARY KEY (banId)

)

CREATE TABLE aud.Banco_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit NULL,
	banId bigint NOT NULL,
	banCodigo varchar(6) NULL,
	banNit varchar(18) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE RetiroPersonaAutorizadaCobroSubsidio(
	rpaId bigint NOT NULL,
	rpaPersonaAutorizada bigint NOT NULL,
	rpaCuentaAdministradorSubsidio bigint NOT NULL,
	rpaDocumentoSoporte bigint NULL,
 CONSTRAINT PK_RetiroPersonaAutorizadaCobroSubsidio_rpaId PRIMARY KEY (rpaId)
)

CREATE TABLE RetiroPersonaAutorizadaCobroSubsidio_aud(
	rpaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rpaPersonaAutorizada bigint NOT NULL,
	rpaCuentaAdministradorSubsidio bigint NOT NULL,
	rpaDocumentoSoporte bigint NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE RegistroArchivoConsumosAnibol(
	racId bigint NOT NULL,
	racArchivoConsumosAnibol bigint NOT NULL,
	racTipoRegistroArchivoConsumo varchar(16) NOT NULL,
	racCuentaAdministradorSubsidio bigint NULL,
	racFechaHoraCreacion datetime NOT NULL,
	racFechaHoraValidacion datetime NOT NULL,
	racBinCCF varchar(9) NOT NULL,
	racNumeroTarjeta varchar(19) NOT NULL,
	racNitEmpresa varchar(15) NOT NULL,
	racCuentaRelacionada varchar(19) NOT NULL,
	racDispositivoOrigen varchar(2) NOT NULL,
	racDescripcionCobroSubsidio varchar(30) NOT NULL,
	racDescripcionTransaccion varchar(30) NOT NULL,
	racValorTransaccion numeric(17, 2) NOT NULL,
	racValorDispensando numeric(17, 2) NOT NULL,
	racFechaTransaccion varchar(8) NOT NULL,
	racValorACobrar numeric(17, 2) NOT NULL,
	racValorImpuestos numeric(17, 2) NOT NULL,
	racTotalACobrar numeric(17, 2) NOT NULL,
	racImpuestoEmergenciaEconomica numeric(17, 2) NOT NULL,
	racIndicadorReverso char(1) NULL,
	racRespuestaAutorizador varchar(2) NOT NULL,
	racDescripcionRespuesta varchar(30) NOT NULL,
	racCodigoAutorizacion varchar(6) NOT NULL,
	racSubtipo varchar(3) NOT NULL,
	racFechaAutorizador varchar(8) NOT NULL,
	racHoraAutorizador varchar(9) NOT NULL,
	racHoraDispositivo varchar(6) NOT NULL,
	racNumeroReferencia varchar(12) NOT NULL,
	racRed varchar(4) NOT NULL,
	racNumeroDispositivo varchar(16) NOT NULL,
	racCodigoEstablecimiento varchar(10) NOT NULL,
	racCodigoCuentaBolsillo varchar(2) NOT NULL,
	racEstadoRegistro varchar(30) NULL,
	racTipoInconsistenciaResultadoValidacion varchar(34) NULL,
 CONSTRAINT PK_RegistroArchivoConsumosAnibol_racId PRIMARY KEY (racId)
)

CREATE TABLE RegistroArchivoConsumosAnibol_aud(
	racId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	racArchivoConsumosAnibol bigint NOT NULL,
	racTipoRegistroArchivoConsumo varchar(16) NOT NULL,
	racCuentaAdministradorSubsidio bigint NULL,
	racFechaHoraCreacion datetime NOT NULL,
	racFechaHoraValidacion datetime NOT NULL,
	racBinCCF varchar(9) NOT NULL,
	racNumeroTarjeta varchar(19) NOT NULL,
	racNitEmpresa varchar(15) NOT NULL,
	racCuentaRelacionada varchar(19) NOT NULL,
	racDispositivoOrigen varchar(2) NOT NULL,
	racDescripcionCobroSubsidio varchar(30) NOT NULL,
	racDescripcionTransaccion varchar(30) NOT NULL,
	racValorTransaccion numeric(17, 2) NOT NULL,
	racValorDispensando numeric(17, 2) NOT NULL,
	racFechaTransaccion varchar(8) NOT NULL,
	racValorACobrar numeric(17, 2) NOT NULL,
	racValorImpuestos numeric(17, 2) NOT NULL,
	racTotalACobrar numeric(17, 2) NOT NULL,
	racImpuestoEmergenciaEconomica numeric(17, 2) NOT NULL,
	racIndicadorReverso char(1) NULL,
	racRespuestaAutorizador varchar(2) NOT NULL,
	racDescripcionRespuesta varchar(30) NOT NULL,
	racCodigoAutorizacion varchar(6) NOT NULL,
	racSubtipo varchar(3) NOT NULL,
	racFechaAutorizador varchar(8) NOT NULL,
	racHoraAutorizador varchar(9) NOT NULL,
	racHoraDispositivo varchar(6) NOT NULL,
	racNumeroReferencia varchar(12) NOT NULL,
	racRed varchar(4) NOT NULL,
	racNumeroDispositivo varchar(16) NOT NULL,
	racCodigoEstablecimiento varchar(10) NOT NULL,
	racCodigoCuentaBolsillo varchar(2) NOT NULL,
	racEstadoRegistro varchar(30) NULL,
	racTipoInconsistenciaResultadoValidacion varchar(34) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE ArchivoConsumosAnibol(
	acnId bigint NOT NULL,
	acnNombreArchivo varchar(40) NULL,
	acnIdentificadorDocumento varchar(255) NULL,
	acnVersionDocumento varchar(20) NULL,
	acnFechaHoraCargue datetime NOT NULL,
	acnUsuarioCargue varchar(50) NOT NULL,
	acnFechaHoraProcesamiento datetime NULL,
	acnUsuarioProcesamiento varchar(50) NULL,
	acnTipoCargue varchar(10) NOT NULL,
	acnEstadoArchivo varchar(29) NULL,
	acnResultadoValidacionEstructura varchar(20) NULL,
	acnResultadoValidacionContenido varchar(20) NULL,
	acnTipoInconsistenciaArchivo varchar(40) NULL,
	acnArchivoNotificado smallint NULL,
 CONSTRAINT PK_ArchivoConsumosAnibol_acnId PRIMARY KEY (acnId)
)

CREATE TABLE ArchivoConsumosAnibol_aud(
	acnId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acnNombreArchivo varchar(40) NULL,
	acnIdentificadorDocumento varchar(255) NULL,
	acnVersionDocumento varchar(20) NULL,
	acnFechaHoraCargue datetime NOT NULL,
	acnUsuarioCargue varchar(50) NOT NULL,
	acnFechaHoraProcesamiento datetime NULL,
	acnUsuarioProcesamiento varchar(50) NULL,
	acnTipoCargue varchar(10) NOT NULL,
	acnEstadoArchivo varchar(29) NULL,
	acnResultadoValidacionEstructura varchar(20) NULL,
	acnResultadoValidacionContenido varchar(20) NULL,
	acnTipoInconsistenciaArchivo varchar(40) NULL,
	acnArchivoNotificado smallint NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE RelacionGrupoFamiliar(
	rgfId smallint NOT NULL,
	rgfNombre varchar(15) NULL,
 CONSTRAINT PK_RelacionGrupoFamiliar_rgfId PRIMARY KEY (rgfId)
)

CREATE TABLE aud.RelacionGrupoFamiliar_aud(
	rgfId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rgfNombre varchar(15) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE GradoAcademico(
	graId smallint NOT NULL,
	graNombre varchar(20) NOT NULL,
	graNivelEducativo varchar(43) NOT NULL,
 CONSTRAINT PK_GradoAcademico_graId PRIMARY KEY (graId)
)

CREATE TABLE aud.GradoAcademico_aud(
	graId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	graNombre varchar(20) NOT NULL,
	graNivelEducativo varchar(43) NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE AsesorResponsableEmpleador(
	areId bigint NOT NULL,
	areNombreUsuario varchar(255) NULL,
	arePrimario bit NULL,
	areEmpleador bigint NULL,
 CONSTRAINT PK_AsesorResponsableEmpleador_areId PRIMARY KEY (areId)
)

CREATE TABLE aud.AsesorResponsableEmpleador_aud(
	areId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	areNombreUsuario varchar(255) NULL,
	arePrimario bit NULL,
	areEmpleador bigint NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE Certificado(
	cerId bigint NOT NULL,
	cerTipoCertificado varchar(40) NOT NULL,
	cerFechaGeneracion datetime2(7) NOT NULL,
	cerDirigidoA varchar(255) NOT NULL,
	cerPersona bigint NOT NULL,
	cerGeneradoComoEmpleador bit NOT NULL,
	cerTipoSolicitud varchar(100) NULL,
	cerComunicado bigint NULL,
	cerAnio smallint NULL,
	cerTipoAfiliado varchar(30) NULL,
	cerEmpleador bigint NULL,
 CONSTRAINT PK_Certificado_cerId PRIMARY KEY (cerId)
)

CREATE TABLE aud.Certificado_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cerId bigint NOT NULL,
	cerTipoCertificado varchar(40) NOT NULL,
	cerFechaGeneracion datetime2(7) NOT NULL,
	cerDirigidoA varchar(255) NOT NULL,
	cerPersona bigint NOT NULL,
	cerGeneradoComoEmpleador bit NOT NULL,
	cerTipoSolicitud varchar(100) NULL,
	cerComunicado bigint NULL,
	cerAnio smallint NULL,
	cerTipoAfiliado varchar(30) NULL,
	cerEmpleador bigint NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE SolicitudAporte(
	soaId bigint NOT NULL,
	soaSolicitudGlobal bigint NOT NULL,
	soaEstadoSolicitud varchar(30) NULL,
	soaRegistroGeneral bigint NULL,
	soaNumeroIdentificacion varchar(16) NULL,
	soaTipoIdentificacion varchar(20) NULL,
	soaNombreAportante varchar(200) NULL,
	soaPeriodoAporte varchar(7) NULL,
	soaTipoSolicitante varchar(13) NULL,
 CONSTRAINT PK_SolicitudAporte_soaId PRIMARY KEY (soaId)
)

CREATE TABLE aud.SolicitudAporte_aud(
	soaSolicitudGlobal bigint NOT NULL,
	soaEstadoSolicitud varchar(30) NULL,
	soaRegistroGeneral bigint NULL,
	soaNumeroIdentificacion varchar(16) NULL,
	soaTipoIdentificacion varchar(20) NULL,
	soaNombreAportante varchar(200) NULL,
	soaPeriodoAporte varchar(7) NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	soaTipoSolicitante varchar(13) NULL,
	soaId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE SolicitudCorreccionAporte(
	scaId bigint NOT NULL,
	scaEstadoSolicitud varchar(25) NULL,
	scaTipoSolicitante varchar(25) NULL,
	scaObservacionSupervisor varchar(255) NULL,
	scaResultadoSupervisor varchar(10) NULL,
	scaSolicitudGlobal bigint NULL,
	scaPersona bigint NULL,
	scaAporteGeneral bigint NULL,
 CONSTRAINT PK_SolicitudCorreccionAporte_scaId PRIMARY KEY (scaId)
) 

CREATE TABLE aud.SolicitudCorreccionAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	scaEstadoSolicitud varchar(25) NULL,
	scaTipoSolicitante varchar(25) NULL,
	scaObservacionSupervisor varchar(255) NULL,
	scaResultadoSupervisor varchar(10) NULL,
	scaSolicitudGlobal bigint NULL,
	scaPersona bigint NULL,
	scaAporteGeneral bigint NULL,
	scaId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE Correccion(
	corId bigint NOT NULL,
	corAporteDetallado bigint NULL,
	corAporteGeneral bigint NULL,
	corSolicitudCorreccionAporte bigint NULL,
 CONSTRAINT PK_Correccion_scaId PRIMARY KEY (corId)
)

CREATE TABLE aud.Correccion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	corAporteDetallado bigint NULL,
	corAporteGeneral bigint NULL,
	corSolicitudCorreccionAporte bigint NULL,
	corId bigint NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE ExclusionCartera(
	excId bigint NOT NULL,
	excPersona bigint NOT NULL,
	excTipoSolicitante varchar(15) NOT NULL,
	excEstadoExclusionCartera varchar(10) NOT NULL,
	excFechaInicio date NOT NULL,
	excFechaFin date NULL,
	excFechaRegistro date NOT NULL,
	excFechaMovimiento date NULL,
	excObservacion varchar(400) NULL,
	excTipoExclusionCartera varchar(25) NOT NULL,
	excEstadoAntesExclusion varchar(45) NOT NULL,
	excNumeroOperacionMora bigint NULL,
	excUsuarioRegistro varchar(400) NULL,
	excResultado varchar(11) NULL,
	excObservacionCambioResultado varchar(400) NULL,
 CONSTRAINT PK_ExclusionCartera_excId PRIMARY KEY (excId)
)

CREATE TABLE aud.ExclusionCartera_aud(
	excId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	excPersona bigint NOT NULL,
	excTipoSolicitante varchar(15) NOT NULL,
	excEstadoExclusionCartera varchar(10) NOT NULL,
	excFechaInicio date NOT NULL,
	excFechaFin date NULL,
	excFechaRegistro date NOT NULL,
	excFechaMovimiento date NULL,
	excObservacion varchar(400) NULL,
	excTipoExclusionCartera varchar(25) NOT NULL,
	excEstadoAntesExclusion varchar(45) NOT NULL,
	excNumeroOperacionMora bigint NULL,
	excUsuarioRegistro varchar(400) NULL,
	excResultado varchar(11) NULL,
	excObservacionCambioResultado varchar(400) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE PagoPeriodoConvenio(
	ppcId bigint NOT NULL,
	ppcConvenioPago bigint NOT NULL,
	ppcFechaPago date NOT NULL,
	ppcValorCuota numeric(19, 5) NOT NULL,
	ppcPeriodo date NOT NULL,
 CONSTRAINT PK_PagoPeriodoConvenio_ppcId PRIMARY KEY (ppcId)
)

CREATE TABLE aud.PagoPeriodoConvenio_aud(
	ppcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ppcConvenioPago bigint NOT NULL,
	ppcFechaPago date NOT NULL,
	ppcValorCuota numeric(19, 5) NOT NULL,
	ppcPeriodo date NOT NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE CicloCartera(
	ccrId bigint NOT NULL,
	ccrEstadoCiclo varchar(10) NOT NULL,
	ccrFechaInicio date NOT NULL,
	ccrFechaFin date NOT NULL,
	ccrFechaCreacion datetime NOT NULL,
	ccrTipoCiclo varchar(14) NULL,
 CONSTRAINT PK_CicloCartera_ccrId PRIMARY KEY (ccrId)
)

CREATE TABLE aud.CicloCartera_aud(
	ccrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ccrEstadoCiclo varchar(10) NOT NULL,
	ccrFechaInicio date NOT NULL,
	ccrFechaFin date NOT NULL,
	ccrFechaCreacion datetime NOT NULL,
	ccrTipoCiclo varchar(14) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE TasasInteresMora(
	timId bigint NOT NULL,
	timFechaInicioTasa date NOT NULL,
	timFechaFinTasa date NOT NULL,
	timNumeroPeriodoTasa smallint NOT NULL,
	timPorcentajeTasa numeric(4, 4) NOT NULL,
	timNormativa varchar(100) NOT NULL,
	timTipoInteres varchar(20) NOT NULL,
 CONSTRAINT PK_TasasInteresMora_timId PRIMARY KEY (timId)
)

CREATE TABLE aud.TasasInteresMora_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	timFechaInicioTasa date NOT NULL,
	timFechaFinTasa date NOT NULL,
	timNumeroPeriodoTasa smallint NOT NULL,
	timPorcentajeTasa numeric(4, 4) NOT NULL,
	timNormativa varchar(100) NOT NULL,
	timTipoInteres varchar(20) NOT NULL,
	timId bigint NOT NULL,
	revTime DATETIME NOT NULL
)


--changeset mamonroy:22
DROP TABLE RetiroPersonaAutorizadaCobroSubsidio_aud;
DROP TABLE RegistroArchivoConsumosAnibol_aud;
DROP TABLE ArchivoConsumosAnibol_aud;

CREATE TABLE aud.RetiroPersonaAutorizadaCobroSubsidio_aud(
	rpaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rpaPersonaAutorizada bigint NOT NULL,
	rpaCuentaAdministradorSubsidio bigint NOT NULL,
	rpaDocumentoSoporte bigint NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE aud.RegistroArchivoConsumosAnibol_aud(
	racId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	racArchivoConsumosAnibol bigint NOT NULL,
	racTipoRegistroArchivoConsumo varchar(16) NOT NULL,
	racCuentaAdministradorSubsidio bigint NULL,
	racFechaHoraCreacion datetime NOT NULL,
	racFechaHoraValidacion datetime NOT NULL,
	racBinCCF varchar(9) NOT NULL,
	racNumeroTarjeta varchar(19) NOT NULL,
	racNitEmpresa varchar(15) NOT NULL,
	racCuentaRelacionada varchar(19) NOT NULL,
	racDispositivoOrigen varchar(2) NOT NULL,
	racDescripcionCobroSubsidio varchar(30) NOT NULL,
	racDescripcionTransaccion varchar(30) NOT NULL,
	racValorTransaccion numeric(17, 2) NOT NULL,
	racValorDispensando numeric(17, 2) NOT NULL,
	racFechaTransaccion varchar(8) NOT NULL,
	racValorACobrar numeric(17, 2) NOT NULL,
	racValorImpuestos numeric(17, 2) NOT NULL,
	racTotalACobrar numeric(17, 2) NOT NULL,
	racImpuestoEmergenciaEconomica numeric(17, 2) NOT NULL,
	racIndicadorReverso char(1) NULL,
	racRespuestaAutorizador varchar(2) NOT NULL,
	racDescripcionRespuesta varchar(30) NOT NULL,
	racCodigoAutorizacion varchar(6) NOT NULL,
	racSubtipo varchar(3) NOT NULL,
	racFechaAutorizador varchar(8) NOT NULL,
	racHoraAutorizador varchar(9) NOT NULL,
	racHoraDispositivo varchar(6) NOT NULL,
	racNumeroReferencia varchar(12) NOT NULL,
	racRed varchar(4) NOT NULL,
	racNumeroDispositivo varchar(16) NOT NULL,
	racCodigoEstablecimiento varchar(10) NOT NULL,
	racCodigoCuentaBolsillo varchar(2) NOT NULL,
	racEstadoRegistro varchar(30) NULL,
	racTipoInconsistenciaResultadoValidacion varchar(34) NULL,
	revTime DATETIME NOT NULL
)

CREATE TABLE aud.ArchivoConsumosAnibol_aud(
	acnId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acnNombreArchivo varchar(40) NULL,
	acnIdentificadorDocumento varchar(255) NULL,
	acnVersionDocumento varchar(20) NULL,
	acnFechaHoraCargue datetime NOT NULL,
	acnUsuarioCargue varchar(50) NOT NULL,
	acnFechaHoraProcesamiento datetime NULL,
	acnUsuarioProcesamiento varchar(50) NULL,
	acnTipoCargue varchar(10) NOT NULL,
	acnEstadoArchivo varchar(29) NULL,
	acnResultadoValidacionEstructura varchar(20) NULL,
	acnResultadoValidacionContenido varchar(20) NULL,
	acnTipoInconsistenciaArchivo varchar(40) NULL,
	acnArchivoNotificado smallint NULL,
	revTime DATETIME NOT NULL
)

--changeset dsuesca:23
--comment:
ALTER TABLE RegistroDetallado ALTER COLUMN redPrimerApellido VARCHAR(50);
ALTER TABLE RegistroDetallado ALTER COLUMN redSegundoApellido VARCHAR(50);
ALTER TABLE RegistroDetallado ALTER COLUMN redPrimerNombre VARCHAR(50);
ALTER TABLE RegistroDetallado ALTER COLUMN redSegundoNombre VARCHAR(50);

--changeset dsuesca:24
--comment:
EXEC sys.sp_rename 'dbo.DataTypefor_RegistroDetallado', 'DataTypefor_RegistroDetallado_aux3';

CREATE TYPE DataTypefor_RegistroDetallado  AS TABLE (
	redId bigint NOT NULL,
	redRegistroGeneral bigint NOT NULL,
	redTipoIdentificacionCotizante varchar(20) NOT NULL,
	redNumeroIdentificacionCotizante varchar(16) NULL,
	redTipoCotizante smallint NULL,
	redCodDepartamento varchar(2) NULL,
	redCodMunicipio varchar(6) NULL,
	redPrimerApellido varchar(50) NULL,
	redSegundoApellido varchar(50) NULL,
	redPrimerNombre varchar(50) NULL,
	redSegundoNombre varchar(50) NULL,
	redNovIngreso varchar(1) NULL,
	redNovRetiro varchar(1) NULL,
	redNovVSP varchar(1) NULL,
	redNovVST varchar(1) NULL,
	redNovSLN varchar(1) NULL,
	redNovIGE varchar(1) NULL,
	redNovLMA varchar(1) NULL,
	redNovVACLR varchar(1) NULL,
	redNovSUS varchar(1) NULL,
	redDiasIRL varchar(2) NULL,
	redDiasCotizados smallint NULL,
	redSalarioBasico numeric(19,5) NULL,
	redValorIBC numeric(19,5) NULL,
	redTarifa numeric(5,5) NULL,
	redAporteObligatorio numeric(19,5) NULL,
	redCorrecciones varchar(1) NULL,
	redSalarioIntegral varchar(1) NULL,
	redFechaIngreso date NULL,
	redFechaRetiro date NULL,
	redFechaInicioVSP date NULL,
	redFechaInicioSLN date NULL,
	redFechaFinSLN date NULL,
	redFechaInicioIGE date NULL,
	redFechaFinIGE date NULL,
	redFechaInicioLMA date NULL,
	redFechaFinLMA date NULL,
	redFechaInicioVACLR date NULL,
	redFechaFinVACLR date NULL,
	redFechaInicioVCT date NULL,
	redFechaFinVCT date NULL,
	redFechaInicioIRL date NULL,
	redFechaFinIRL date NULL,
	redFechaInicioSuspension date NULL,
	redFechaFinSuspension date NULL,
	redHorasLaboradas smallint NULL,
	redRegistroControl bigint NULL,
	redOUTMarcaValRegistroAporte varchar(50) NULL,
	redOUTEstadoRegistroAporte varchar(60) NULL,
	redOUTAnalisisIntegral bit NULL,
	redOUTFechaProcesamientoValidRegAporte datetime NULL,
	redOUTEstadoValidacionV0 varchar(30) NULL,
	redOUTEstadoValidacionV1 varchar(30) NULL,
	redOUTEstadoValidacionV2 varchar(30) NULL,
	redOUTEstadoValidacionV3 varchar(30) NULL,
	redOUTClaseTrabajador varchar(50) NULL,
	redOUTPorcentajePagoAportes numeric(5,5) NULL,
	redOUTEstadoSolicitante varchar(50) NULL,
	redOUTEsTrabajadorReintegrable bit NULL,
	redOUTFechaIngresoCotizante date NULL,
	redOUTFechaUltimaNovedad date NULL,
	redOUTFechaFallecimiento date NULL,
	redUsuarioAprobadorAporte varchar(50) NOT NULL,
	redNumeroOperacionAprobacion varchar(12) NULL,
	redEstadoEvaluacion varchar(22) NOT NULL,
	redEstadoRegistroCorreccion varchar(40) NULL,
	redOUTCodSucursal varchar(10) NULL,
	redOUTNomSucursal varchar(100) NULL,
	redOUTDiasCotizadosPlanillas smallint NULL,
	redOUTDiasCotizadosBD smallint NULL,
	redOUTDiasCotizadosNovedades smallint NULL,
	redOUTTipoAfiliado varchar(50) NULL,
	redOUTRegistrado bit NULL,
	redOUTValorMoraCotizante numeric(19,5) NULL,
	redOUTAporteObligatorioMod numeric(19,5) NULL,
	redOUTDiasCotizadosMod smallint NULL,
	redOUTRegistradoAporte bit NULL,
	redOUTRegistradoNovedad bit NULL,
	redOUTTipoNovedadSituacionPrimaria varchar(15) NULL,
	redOUTFechaInicioNovedadSituacionPrimaria date NULL,
	redOUTFechaFinNovedadSituacionPrimaria date NULL,
	redOUTRegDetOriginal bigint NULL,
	redOUTEstadoRegistroRelacionAporte varchar(50) NULL,
	redOUTEstadoEvaluacionAporte varchar(22) NULL,
	redOUTFechaRetiroCotizante date NULL,
	redOUTValorIBCMod numeric(19,5) NULL,
	redOUTValorMoraCotizanteMod numeric(19,5) NULL,
	redFechaInicioVST date NULL,
	redFechaFinVST date NULL,
	redOUTDiasCotizadosNovedadesBD smallint NULL,
	redOUTGrupoFamiliarReintegrable bit NULL,
	redIdRegistro2pila bigint NULL,
	redOUTEnviadoAFiscalizacionInd bit NULL,
	redOUTMotivoFiscalizacionInd varchar(30) NULL,
	redOUTRegistroActual bit NULL,
	redOUTRegInicial bigint NULL,
	redOUTGrupoAC int NULL,
	redOUTTarifaMod numeric(5,5) NULL,
	redDateTimeInsert datetime,
	redDateTimeUpdate datetime,
	redOUTPeriodicidad VARCHAR(11),
	redUsuarioAccion VARCHAR(100),
	redFechaAccion DATETIME
);

--changeset dsuesca:25
ALTER TABLE DetalleSubsidioAsignado ADD dsaNombreTerceroPagado varchar(200);
ALTER TABLE aud.DetalleSubsidioAsignado_aud ADD dsaNombreTerceroPagado varchar(200);

--changeset dsuesca:26
ALTER TABLE CuentaAdministradorSubsidio ADD casIdCuentaOriginal BIGINT;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casIdCuentaOriginal BIGINT;

--changeset tuestrella:27
CREATE TABLE Pais (
    paiId BIGINT IDENTITY(1,1) NOT NULL,
    paiCodigo VARCHAR(10),
    paiDescripcion VARCHAR(70),
	CONSTRAINT PK_Pais_paiId PRIMARY KEY (paiId)
);

CREATE TABLE aud.Pais_aud (
    paiId BIGINT,
    REV bigint NOT NULL,
	REVTYPE smallint NULL,
    paiCodigo VARCHAR(10),
    paiDescripcion VARCHAR(70),
    revTime DATETIME NOT NULL
);

ALTER TABLE PersonaDetalle ADD pedOrientacionSexual varchar(30);
ALTER TABLE PersonaDetalle ADD pedFactorVulnerabilidad varchar(60);
ALTER TABLE PersonaDetalle ADD pedPertenenciaEtnica varchar(70);
ALTER TABLE PersonaDetalle ADD pedPaisResidencia bigint;
ALTER TABLE aud.PersonaDetalle_aud ADD pedOrientacionSexual varchar(30);
ALTER TABLE aud.PersonaDetalle_aud ADD pedFactorVulnerabilidad varchar(60);
ALTER TABLE aud.PersonaDetalle_aud ADD pedPertenenciaEtnica varchar(70);
ALTER TABLE aud.PersonaDetalle_aud ADD pedPaisResidencia bigint;
ALTER TABLE Ubicacion ADD ubiSectorUbicacion varchar(10);
ALTER TABLE aud.Ubicacion_aud ADD ubiSectorUbicacion varchar(10);

--changeset flopez:28
ALTER TABLE BeneficiarioDetalle DROP COLUMN bedTipoUnionConyugal;
ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedTipoUnionConyugal;

--changeset mamonroy:29
ALTER TABLE CertificadoEscolarBeneficiario ADD cebFechaCreacion DATE NULL;
ALTER TABLE aud.CertificadoEscolarBeneficiario_aud ADD cebFechaCreacion DATE NULL;

--changeset cmarin:29
ALTER TABLE dbo.SitioPago ADD sipPrincipal bit;
ALTER TABLE aud.SitioPago_aud ADD sipPrincipal bit;
ALTER TABLE dbo.CuentaAdministradorSubsidio ADD casIdPuntoDeCobro VARCHAR(200);
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casIdPuntoDeCobro VARCHAR(200);

--changeset fhoyos:30
UPDATE cad
SET cad.cadDeudaReal = 0
FROM aud.CarteraDependiente_aud cad
WHERE cad.cadDeudaReal IS NULL;

UPDATE cad
SET cad.cadDeudaReal = 0
FROM CarteraDependiente cad
WHERE cad.cadDeudaReal IS NULL;

ALTER TABLE CarteraDependiente ALTER COLUMN [cadDeudaReal] NUMERIC(19,5) NOT NULL;