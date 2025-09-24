--liquibase formatted sql

--changeset arocha:01
--comment:Creacion modelo de reportes
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='RegistroLog' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.RegistroLog;
CREATE TABLE dbo.RegistroLog(
	relId bigint IDENTITY(1,1) NOT NULL,
	relFecha datetime NOT NULL,
	relParametrosEjecucion varchar(255) NULL,
	relErrorMessage varchar(max) NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='RevisionAuditoriaReportes' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.RevisionAuditoriaReportes;
CREATE TABLE dbo.RevisionAuditoriaReportes(
	rarId bigint IDENTITY(1,1) NOT NULL,
	rarFechaInicioEjecucion datetime NOT NULL,
	rarFechaFinEjecucion datetime NULL,
	rarRevisionInicio bigint NOT NULL,
	rarRevisionTimeStampInicio bigint NOT NULL,
	rarRevisionTimeInicio DATETIME NOT NULL,
	rarRevisionFin bigint NOT NULL,
	rarRevisionTimeStampFin bigint NOT NULL,
	rarRevisionTimeFin DATETIME NOT NULL,
	rarRunId UNIQUEIDENTIFIER NOT NULL,
	rarEnColaProceso BIT NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='WaterMarkedRows' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.WaterMarkedRows;
CREATE TABLE WaterMarkedRows(
	wmrId bigint IDENTITY(1,1) NOT NULL,
	wmrTable varchar(100) NOT NULL,
	wmrKeyRowValue bigint NOT NULL,
	wmrDeleted bit NOT NULL,
	wmrFirstRevisionDateTime DATETIME NOT NULL,
	wmrLastRevisionDateTime DATETIME NOT NULL,
	wmrJsonOldValue NVARCHAR(MAX) NULL,
	wmrJsonNewValue NVARCHAR(MAX) NULL,
	wmrRevisionAuditoriaReportes bigint NOT NULL
);

--changeset arocha:02
--comment:Creacion modelo de tablas de Metas para KPIs
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MetaPeriodoKPI' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MetaPeriodoKPI;
CREATE TABLE dbo.MetaPeriodoKPI
(
	mpkId INT IDENTITY NOT NULL,
	mpkMeta VARCHAR(100) NOT NULL, --'PORCENTAJE_AFILIACIONES_EMPLEADORES_POR_CANAL_ANIO_MES', 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES_POR_CANAL_ANIO_MES'
	mpkAnio SMALLINT NOT NULL,
	mpkFrecuencia VARCHAR(10) NOT NULL --'Mensual','Bimensual','Trimestral','Semestral','Anual'
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ValorMetaPeriodoCanalKPI' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.ValorMetaPeriodoCanalKPI;
CREATE TABLE dbo.ValorMetaPeriodoCanalKPI
(
	vmpId INT IDENTITY NOT NULL,
	vmpMetaPeriodoKPI INT NOT NULL,
	vmpCanal VARCHAR(20) NOT NULL,  --'Web','Presencial','PILA'
	vmpPeriodo TINYINT NOT NULL,
	vmpValorInt INT NOT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimPeriodo' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimPeriodo;
CREATE TABLE DimPeriodo
(
	dipId INT IDENTITY NOT NULL,
	dipMes TINYINT NOT NULL,
	dipAnio SMALLINT NOT NULL,
	dipTextoMes VARCHAR(20) NOT NULL,
	dipBimestre TINYINT NOT NULL,
	dipTextoBimestre VARCHAR(20) NOT NULL,
	dipTrimestre TINYINT NOT NULL,
	dipTextoTrimestre VARCHAR(20) NOT NULL,
	dipSemestre TINYINT NOT NULL,
	dipTextoSemestre VARCHAR(20) NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimCanal' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimCanal;
CREATE TABLE DimCanal
(
	dicId TINYINT NOT NULL,
	dicDescripcion VARCHAR(20) NOT NULL --'Web','Presencial','PILA'
);
INSERT INTO dbo.DimCanal VALUES (1,'Web'),(2,'Presencial'),(3,'PILA');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimMetasCanalPeriodo' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimMetasCanalPeriodo;
CREATE TABLE DimMetasCanalPeriodo
(
	dmcId INT IDENTITY NOT NULL,
	dmcDimCanal TINYINT NOT NULL,
	dmcDimPeriodo INT NOT NULL,
	dmcMetaAfiEmpMes INT NOT NULL,
	dmcMetaAfiEmpBimensual INT NOT NULL,
	dmcMetaAfiEmpTrimestral INT NOT NULL,
	dmcMetaAfiEmpSemestral INT NOT NULL,
	dmcMetaAfiEmpAnual INT NOT NULL,
	dmcMetaRechEmpMes INT NOT NULL,
	dmcMetaRechEmpBimensual INT NOT NULL,
	dmcMetaRechEmpTrimestral INT NOT NULL,
	dmcMetaRechEmpSemestral INT NOT NULL,
	dmcMetaRechEmpAnual INT NOT NULL
);


IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoAfiliacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoAfiliacion;
CREATE TABLE DimTipoAfiliacion
(
	ditId TINYINT NOT NULL,
	ditDescripcion VARCHAR(20) NOT NULL --'Nueva','Reintegro'
);
INSERT INTO dbo.DimTipoAfiliacion VALUES (1,'Nueva'),(2,'Reintegro');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimResultadoSolicitud' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimResultadoSolicitud;
CREATE TABLE DimResultadoSolicitud
(
	dirId TINYINT NOT NULL,
	dirDescripcion VARCHAR(20) NOT NULL --'Intento','Rechazo'
);
INSERT INTO dbo.DimResultadoSolicitud VALUES (1,'Intento'),(2,'Rechazo');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTamanioEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTamanioEmpleador;
CREATE TABLE DimTamanioEmpleador
(
	dteId TINYINT NOT NULL,
	dteDescripcion VARCHAR(30) NOT NULL --'I (0 a 10 empleados)','II (11 a 50 empleados)','III (51 a 200 empleados)','IV (más de 200 empleados)'

);
INSERT INTO dbo.DimTamanioEmpleador VALUES (1,'I (0 a 10 empleados)'),(2,'II (11 a 50 empleados)'),(3,'III (51 a 200 empleados)'),(4,'IV (más de 200 empleados)');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimNaturalezaJuridica' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimNaturalezaJuridica;
CREATE TABLE DimNaturalezaJuridica
(
	dinId TINYINT NOT NULL,
	dinDescripcion VARCHAR(100) NOT NULL --'Pública','Privada','Mixta','Organismos multilaterales','Entidades de derecho público no sometidas a la legislación colombiana'

);
INSERT INTO dbo.DimNaturalezaJuridica VALUES (1,'Pública'),(2,'Privada'),(3,'Mixta'),(4,'Organismos multilaterales'),(5,'Entidades de derecho público no sometidas a la legislación colombiana');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoEmpleador;
CREATE TABLE DimEstadoEmpleador
(
	deeId TINYINT NOT NULL,
	deeDescripcion VARCHAR(20) NOT NULL --'Activo','Inactivo','No formalizada','Sin información'

);
INSERT INTO dbo.DimEstadoEmpleador VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimPagoAportes' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimPagoAportes;
CREATE TABLE DimPagoAportes
(
	dpaId TINYINT NOT NULL,
	dpaDescripcion VARCHAR(20) NOT NULL --'Pago aportes','No pago de aportes'

);
INSERT INTO dbo.DimPagoAportes VALUES (1,'Pago aportes'),(2,'No pago de aportes');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimRangoTiempoRespuesta' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimRangoTiempoRespuesta;
CREATE TABLE DimRangoTiempoRespuesta
(
	drtId TINYINT NOT NULL,
	drtDescripcion VARCHAR(20) NOT NULL --'0 a 1 día','1 a 2 días','2 a 3 días','3 días o más'

);
INSERT INTO dbo.DimRangoTiempoRespuesta VALUES (1,'0 a 1 día'),(2,'1 a 2 días'),(3,'2 a 3 días'),(4,'3 días o más');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimSede' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimSede;
CREATE TABLE DimSede
(
	disId INT NOT NULL,
	disDescripcion VARCHAR(100) NOT NULL --'0 a 1 día','1 a 2 días','2 a 3 días','3 días o más'

);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEmpleador;
CREATE TABLE DimEmpleador
(
	demId BIGINT IDENTITY NOT NULL,
	demTipoIdentificacion VARCHAR(20) NOT NULL,
	demNumeroIdentificacion VARCHAR(16) NOT NULL,
	demDigitoVerificacion SMALLINT NULL,
	demRazonSocial VARCHAR(250) NULL,
	demPrimerNombre VARCHAR(50) NULL,
	demSegundoNombre VARCHAR(50) NULL,
	demPrimerApellido VARCHAR(50) NULL,
	demSegundoApellido VARCHAR(50) NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactCondicionEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactCondicionEmpleador;
CREATE TABLE FactCondicionEmpleador
(
	fceId BIGINT NOT NULL IDENTITY,
	fceDimEmpleador BIGINT NOT NULL,
	fceDimPeriodo INT NOT NULL,
	fceDimCanal TINYINT NULL,
	fceDimSede INT NULL,
	fceDimTipoAfiliacion TINYINT NULL,
	fceDimTamanioEmpleador TINYINT NULL,
	fceDimNaturalezaJuridica TINYINT NULL,
	fceDimEstadoEmpleador TINYINT NULL,
	fceDimPagoAportes TINYINT NULL,
	fceFechaCambioEstadoAfiliacion DATETIME NULL,
	fceFechaPrimeraRevision DATETIME NULL,
	fceFechaUltimoCanalRecepcion DATETIME NULL,
	fceFechaAportes DATETIME NULL,
	fceAccionActivacion BIT NULL
);


IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactSolicitudAfiliacionEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactSolicitudAfiliacionEmpleador;
CREATE TABLE FactSolicitudAfiliacionEmpleador
(
	fseId BIGINT IDENTITY NOT NULL,
	fseDimEmpleador BIGINT NOT NULL,
	fseDimPeriodo INT NOT NULL,
	fseSolicitud BIGINT NOT NULL,
	fseEmpleador BIGINT NULL,
	fseFechaInicioSolicitud DATETIME NOT NULL,
	fseFechaCambioEstadoSolicitud DATETIME NOT NULL,
	fseDimRangoTiempoRespuestaNotificacion TINYINT NULL,
	fseDimResultadoSolicitud TINYINT NULL,
	fseCerrada BIT
);


--changeset arocha:03
--comment:Creacion modelo de reportes
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MarcaProcesamiento' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MarcaProcesamiento;
CREATE TABLE dbo.MarcaProcesamiento (mapFechaUltimaEjecucionUTC datetime)

--changeset arocha:04
--comment:Tablas indicadores de Afiliacion de Personas

/*
DROP TABLE FactCondicionBeneficiario
DROP TABLE DimEstadoBeneficiario
DROP TABLE DimTipoBeneficiario
DROP TABLE DimBeneficiario
DROP TABLE FactSolicitudAfiliacionPersona
DROP TABLE FactCondicionPersona
DROP TABLE DimEstadoSolicitud
DROP TABLE DimNaturalezaPersona
DROP TABLE DimEstadoPersona
DROP TABLE DimPersona
--DROP TABLE FactCondicionEmpleador
--DROP TABLE DimMetasCanalPeriodo
*/

ALTER TABLE FactCondicionEmpleador ADD fceActivoPeriodoAnterior BIT NULL

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimPersona;
CREATE TABLE DimPersona
(
	dpeId BIGINT IDENTITY NOT NULL,
	dpeTipoIdentificacion VARCHAR(20) NOT NULL,
	dpeNumeroIdentificacion VARCHAR(16) NOT NULL,
	dpePrimerNombre VARCHAR(50) NULL,
	dpeSegundoNombre VARCHAR(50) NULL,
	dpePrimerApellido VARCHAR(50) NULL,
	dpeSegundoApellido VARCHAR(50) NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoPersona;
CREATE TABLE DimEstadoPersona

(
	depId TINYINT NOT NULL,
	depDescripcion VARCHAR(20) NOT NULL --'Activo','Inactivo','No formalizada','Sin información'
);

INSERT INTO dbo.DimEstadoPersona VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimNaturalezaPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimNaturalezaPersona;
CREATE TABLE DimNaturalezaPersona

(
	dnpId TINYINT NOT NULL,
	dnpDescripcion VARCHAR(20) NOT NULL --'Dependiente','Independiente','Pensionado'
);

INSERT INTO dbo.DimNaturalezaPersona VALUES (1,'Dependiente'),(2,'Independiente'),(3,'Pensionado');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoSolicitud' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoSolicitud;
CREATE TABLE DimEstadoSolicitud

(
	desId TINYINT NOT NULL,
	desDescripcion VARCHAR(20) NOT NULL --'Dependiente','Independiente','Pensionado'
);

INSERT INTO dbo.DimEstadoSolicitud VALUES (1,'Rechazada'),(2,'Cerrada');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactCondicionPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactCondicionPersona;
CREATE TABLE FactCondicionPersona
(
	fcpId BIGINT NOT NULL IDENTITY,
	fcpDimPersona BIGINT NOT NULL,
	fcpDimPeriodo INT NOT NULL,
	fcpDimCanal TINYINT NULL,
	fcpDimTipoAfiliacion TINYINT NULL,
	fcpDimNaturalezaPersona TINYINT NULL,
	fcpDimEstadoPersona TINYINT NULL,
	fcpDimPagoAportes TINYINT NULL,
	fcpFechaCambioEstadoAfiliacion DATETIME NULL,
	fcpFechaPrimeraRevision DATETIME NULL,
	fcpFechaUltimoCanalRecepcion DATETIME NULL,
	fcpFechaAportes DATETIME NULL,
	fcpAccionActivacion BIT NULL,
	fcpActivoPeriodoAnterior BIT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactSolicitudAfiliacionPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactSolicitudAfiliacionPersona;
CREATE TABLE FactSolicitudAfiliacionPersona
(
	fspId BIGINT IDENTITY NOT NULL,
	fspDimPersona BIGINT NOT NULL,
	fspDimPeriodo INT NOT NULL,
	fspSolicitud BIGINT NOT NULL,
	fspRolAfiliado BIGINT NULL,
	fspFechaInicioSolicitud DATETIME NOT NULL,
	fspFechaCambioEstadoSolicitud DATETIME NOT NULL,
	fspDimRangoTiempoRespuestaNotificacion TINYINT NULL,
	fspDimResultadoSolicitud TINYINT NULL,
	fspDimEstadoSolicitud TINYINT NULL,
	fspCerrada BIT
);


UPDATE dbo.DimMetasCanalPeriodo SET 
dmcMetaAfiPerMes = 0,
dmcMetaAfiPerBimensual = 0,
dmcMetaAfiPerTrimestral = 0,
dmcMetaAfiPerSemestral = 0,
dmcMetaAfiPerAnual = 0,
dmcMetaRechPerMes = 0,
dmcMetaRechPerBimensual = 0,
dmcMetaRechPerTrimestral = 0,
dmcMetaRechPerSemestral = 0,
dmcMetaRechPerAnual = 0;

ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaAfiPerMes INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaAfiPerBimensual INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaAfiPerTrimestral INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaAfiPerSemestral INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaAfiPerAnual INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaRechPerMes INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaRechPerBimensual INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaRechPerTrimestral INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaRechPerSemestral INT NOT NULL
ALTER TABLE dbo.DimMetasCanalPeriodo ALTER COLUMN dmcMetaRechPerAnual INT NOT NULL

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoBeneficiario' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoBeneficiario;
CREATE TABLE DimEstadoBeneficiario

(
	debId TINYINT NOT NULL,
	debDescripcion VARCHAR(20) NOT NULL --'Activo','Inactivo'
);

INSERT INTO dbo.DimEstadoBeneficiario VALUES (1,'Activo'),(2,'Inactivo');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoBeneficiario' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoBeneficiario;
CREATE TABLE DimTipoBeneficiario

(
	dtbId TINYINT NOT NULL,
	dtbDescripcion VARCHAR(30) NOT NULL --'Activo','Inactivo'
);

INSERT INTO dbo.DimTipoBeneficiario VALUES (1,'Cónyuge (compañero permanente)'),(2,'Padres'),(3,'Hermano'),(4,'Hijo'),(5,'En custodia');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimBeneficiario' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimBeneficiario;
CREATE TABLE DimBeneficiario
(
	dbeId BIGINT IDENTITY NOT NULL,
	dbeTipoIdentificacion VARCHAR(20) NOT NULL,
	dbeNumeroIdentificacion VARCHAR(16) NOT NULL,
	dbePrimerNombre VARCHAR(50) NULL,
	dbeSegundoNombre VARCHAR(50) NULL,
	dbePrimerApellido VARCHAR(50) NULL,
	dbeSegundoApellido VARCHAR(50) NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactCondicionBeneficiario' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactCondicionBeneficiario;
CREATE TABLE FactCondicionBeneficiario
(
	fcbId BIGINT IDENTITY NOT NULL,
	fcbDimPeriodo INT NOT NULL,
	fcbDimPersona BIGINT NOT NULL,
	fcbDimBeneficiario BIGINT NOT NULL,
	fcbDimEstadoBeneficiario TINYINT NOT NULL,
	fcbDimTipoBeneficiario TINYINT NOT NULL,
	fcbAccionActivacion BIT NULL,
	fcbActivoPeriodoAnterior BIT NULL
);

--changeset arocha:05
--comment:Tablas indicadores de Aportes PILA

/*
DROP TABLE FactArchivoPILA
DROP TABLE FactCondicionCotizante
DROP TABLE FactCondicionAportante
DROP TABLE DimTipoNovedad
DROP TABLE DimEstadoAportante
DROP TABLE DimEstadoRecaudo
DROP TABLE DimTipoAportante
DROP TABLE DimRangoTiempoAporte
DROP TABLE DimCotizante
DROP TABLE DimAportante
DROP TABLE EstadoArchivoPILA
DROP TABLE PlanillaPILA
*/

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='PlanillaPILA' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.PlanillaPILA;
CREATE TABLE dbo.PlanillaPILA(
	pplId bigint NOT NULL IDENTITY,
	pplIndicePlanilla bigint NOT NULL,
	pplIdPlanilla bigint NOT NULL,
	pplFechaRecibo datetime NULL,
	pplEstadoArchivo varchar(75) NULL,
	pplFechaRecaudo date NULL,
	pplPeriodo date NULL,
	pplTipoIdentificacionAportante varchar(40) NULL,
	pplNumeroIdentificacionAportante varchar(16) NOT NULL,
	pplDigitoVerificacion smallint NULL,
	pplRazonSocial varchar(200) NOT NULL,
	pplTipoPlanilla varchar(13) NOT NULL,
	pplValorAporte numeric(19, 5) NULL,
	pplTipoIdentificacionCotizante varchar(40) NULL,
	pplNumeroIdentificacionCotizante varchar(16) NOT NULL,
	pplPrimerApellido varchar(20) NOT NULL,
	pplSegundoApellido varchar(30) NULL,
	pplPrimerNombre varchar(20) NOT NULL,
	pplSegundoNombre varchar(30) NULL,
	pplTipoCotizantePILA smallint NULL,
	pplTipoCotizante varchar(13) NOT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='EstadoArchivoPILA' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.EstadoArchivoPILA;
CREATE TABLE dbo.EstadoArchivoPILA(
	eppId bigint NOT NULL IDENTITY,
	eppIndicePlanilla bigint NOT NULL,
	eppIdPlanilla bigint NOT NULL,
	eppEstadoArchivo varchar(75) NULL,
	eppFechaRecaudo date NULL,
	eppEnColaProceso BIT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimAportante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimAportante;
CREATE TABLE DimAportante
(
	dapId BIGINT IDENTITY NOT NULL,
	dapTipoIdentificacion VARCHAR(20) NOT NULL,
	dapNumeroIdentificacion VARCHAR(16) NOT NULL,
	dapDigitoVerificacion SMALLINT NULL,
	dapRazonSocial VARCHAR(250) NULL,
	dapPrimerNombre VARCHAR(50) NULL,
	dapSegundoNombre VARCHAR(50) NULL,
	dapPrimerApellido VARCHAR(50) NULL,
	dapSegundoApellido VARCHAR(50) NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimCotizante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimCotizante;
CREATE TABLE DimCotizante
(
	dcoId BIGINT IDENTITY NOT NULL,
	dcoTipoIdentificacion VARCHAR(20) NOT NULL,
	dcoNumeroIdentificacion VARCHAR(16) NOT NULL,
	dcoPrimerNombre VARCHAR(50) NULL,
	dcoSegundoNombre VARCHAR(50) NULL,
	dcoPrimerApellido VARCHAR(50) NULL,
	dcoSegundoApellido VARCHAR(50) NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimRangoTiempoAporte' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimRangoTiempoAporte;
CREATE TABLE DimRangoTiempoAporte
(
	draId TINYINT NOT NULL,
	draDescripcion VARCHAR(25) NOT NULL
);
INSERT INTO dbo.DimRangoTiempoAporte VALUES (1,'10 o más días antes'),(2,'10 a 5 días antes'),(3,'5 a 0 días antes'),(4,'0 a 5 días después'),(5,'5 a 10 días después'),(6,'10 o más días después');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoAportante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoAportante;
CREATE TABLE DimTipoAportante
(
	dtaId TINYINT NOT NULL,
	dtaDescripcion VARCHAR(25) NOT NULL
	)
);
INSERT INTO dbo.DimTipoAportante VALUES (1,'Independiente'),(2,'Empresa > 200 aportantes'),(3,'Empresa < 200 aportantes');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoRecaudo' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoRecaudo;
CREATE TABLE DimEstadoRecaudo
(
	derId TINYINT NOT NULL,
	derDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimEstadoRecaudo VALUES (1,'Exitoso'),(2,'Inconsistente');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoAportante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoAportante;
CREATE TABLE DimEstadoAportante
(
	deaId TINYINT NOT NULL,
	deaDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimEstadoAportante VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoNovedad' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoNovedad;
CREATE TABLE DimTipoNovedad
(
	dtnId TINYINT NOT NULL,
	dtnDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimTipoNovedad VALUES (1,'Ingreso'),(2,'Retiro');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactCondicionAportante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactCondicionAportante;
CREATE TABLE FactCondicionAportante
(
	fcaId BIGINT IDENTITY NOT NULL,
	fcaDimPeriodo INT NOT NULL,
	fcaDimAportante BIGINT NOT NULL,
	fcaDimEstadoAportante TINYINT NOT NULL,
	fcaDimTipoAportante TINYINT NOT NULL,
	fcaDimRangoTiempoAporte TINYINT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactCondicionCotizante' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactCondicionCotizante;
CREATE TABLE FactCondicionCotizante
(
	fccId BIGINT IDENTITY NOT NULL,
	fccDimPeriodo INT NOT NULL,
	fccDimAportante BIGINT NOT NULL,
	fccDimCotizante BIGINT NOT NULL,
	fccDimEstadoRecaudo TINYINT NULL,
	fccDimTipoNovedad TINYINT NULL
);


IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactArchivoPILA' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactArchivoPILA;
CREATE TABLE FactArchivoPILA
(
	fapId BIGINT IDENTITY NOT NULL,
	fapDimPeriodo INT NOT NULL,
	fapDimAportante BIGINT NOT NULL,
	fapDimEstadoRecaudo TINYINT NOT NULL,
	fapDimEstadoAportante TINYINT NOT NULL,
	fapDimTipoAportante TINYINT NOT NULL,
	fapDimRangoTiempoAporte TINYINT NULL,
	fapIndicePlanilla BIGINT NOT NULL,
	fapIdPlanilla BIGINT NOT NULL,
	fapFechaRecibo DATETIME,
	fapPeriodoAporte DATE,
	fapFechaRecaudo DATETIME,
	fapValorAporte NUMERIC(19,5)
);


--changeset arocha:06
--comment:Adicion DimTipoPersona y relacion FactCondicionAportante
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoPersona;
CREATE TABLE DimTipoPersona
(
	dtpId TINYINT NOT NULL,
	dtpDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimTipoPersona VALUES (1,'Jurídica'),(2,'Natural');

ALTER TABLE dbo.FactCondicionAportante ADD fcaDimTipoPersona TINYINT;



--changeset arocha:07
--comment:Adicion Dimensiones y tablas de hechos para Novedades

/*
DROP TABLE FactNovedadEmpleador
DROP TABLE FactNovedadPersona
DROP TABLE DimTipoNovedadEmpleador
DROP TABLE DimTipoNovedadPersona
DROP TABLE DimEstadoNovedad
*/
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimEstadoNovedad' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimEstadoNovedad;
CREATE TABLE DimEstadoNovedad
(
	denId TINYINT NOT NULL,
	denDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimEstadoNovedad VALUES (1,'Radicada'),(2,'Aprobado'),(3,'Rechazada'),(4,'Cerrada');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoNovedadPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoNovedadPersona;
CREATE TABLE DimTipoNovedadPersona
(
	dnoId TINYINT NOT NULL,
	dnoDescripcion VARCHAR(100) NOT NULL
);
INSERT INTO dbo.DimTipoNovedadPersona VALUES 
(1,'Activar o inactivar beneficiarios'),
(2,'ctualización de datos básicos'),
(3,'Actualización de información laboral'),
(4,'Actualizar información relativa al pago de aportes para afiliados facultativos'),
(5,'Cambio de medio de pago'),
(6,'Fallecimiento'),
(7,'Grupo familiar'),
(8,'Invalidez'),
(9,'Medios de pago'),
(10,'Otras novedades'),
(11,'Pignoración del subsidio'),
(12,'Retiro y anulación de afiliación'),
(13,'Solicitud de activación/desactivación de cesión del subsidio monetario'),
(14,'Solicitud de activación/desactivación de retención del subsidio monetario');

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DimTipoNovedadEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DimTipoNovedadEmpleador;
CREATE TABLE DimTipoNovedadEmpleador
(
	dneId TINYINT NOT NULL,
	dneDescripcion VARCHAR(20) NOT NULL
);
INSERT INTO dbo.DimTipoNovedadEmpleador VALUES 
(1,'Cambiar'),
(2,'Actualizar'),
(3,'Activar'),
(4,'Agregar'),
(5,'Inactivar'),
(6,'Trasladar'),
(7,'Sustituir'),
(8,'Desafiliar'),
(9,'Anular')

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactNovedadPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactNovedadPersona;
CREATE TABLE FactNovedadPersona
(
	fnpId BIGINT NOT NULL IDENTITY,
	fnpDimPersona BIGINT NOT NULL,
	fnpDimEmpleador BIGINT NULL,
	fnpDimBeneficiario BIGINT NULL,
	fnpDimPeriodo INT NOT NULL,
	fnpDimCanal TINYINT NULL,
	fnpDimSede INT NULL,
	fnpDimEstadoNovedad TINYINT NULL,
	fnpDimTipoNovedadPersona TINYINT NULL,
	fnpDimRangoTiempoRespuestaNovedad TINYINT NULL,
	fnpDimRangoTiempoRespuestaGestion TINYINT NULL,
	fnpDimEstadoPersona TINYINT NULL,
	fnpDimTipoBeneficiario TINYINT NULL,
	fnpDimEstadoEmpleador TINYINT NULL,
	fnpDimEstadoBeneficiario TINYINT NULL,
	fnpDimNaturalezaPersona TINYINT NULL,
	fnpSolicitud BIGINT NOT NULL
);


IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='FactNovedadEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.FactNovedadEmpleador;
CREATE TABLE FactNovedadEmpleador
(
	fneId BIGINT NOT NULL IDENTITY,
	fneDimEmpleador BIGINT NULL,
	fneDimPeriodo INT NOT NULL,
	fneDimCanal TINYINT NULL,
	fneDimSede INT NULL,
	fneDimEstadoNovedad TINYINT NULL,
	fneDimEstadoEmpleador TINYINT NULL,
	fneDimTipoNovedadEmpleador TINYINT NULL,
	fneDimRangoTiempoRespuestaNovedad TINYINT NULL,
	fneDimRangoTiempoRespuestaGestion TINYINT NULL,
	fneSolicitud BIGINT NOT NULL
);
