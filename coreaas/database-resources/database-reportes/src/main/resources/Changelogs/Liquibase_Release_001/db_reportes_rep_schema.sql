--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true

IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'rno')
BEGIN
EXEC('CREATE SCHEMA rno')
END


--changeset dsuesca:02 runOnChange:true
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='EjecucionReporteNormativo' AND TABLE_SCHEMA = 'rep')
DROP TABLE rep.EjecucionReporteNormativo;
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='EjecucionReporteNormativo' AND TABLE_SCHEMA = 'rno')
DROP TABLE rno.EjecucionReporteNormativo;
CREATE TABLE rno.EjecucionReporteNormativo(
  ernId bigint IDENTITY(1,1) NOT NULL, 
  ernFecha DATETIME NOT NULL,
  ernReporte VARCHAR(100) NOT NULL,
  ernPeriodo DATE NOT NULL,
CONSTRAINT PK_EjecucionReporteNormativo_ernId PRIMARY KEY (ernId),
CONSTRAINT UK_EjecucionReporteNormativo_ernPeriodo_ernReporte UNIQUE (ernPeriodo,ernReporte)
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='HistoricoDesagregadoCarteraAportante' AND TABLE_SCHEMA = 'rep')
DROP TABLE rep.HistoricoDesagregadoCarteraAportante;
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='HistoricoDesagregadoCarteraAportante' AND TABLE_SCHEMA = 'rno')
DROP TABLE rno.HistoricoDesagregadoCarteraAportante;
CREATE TABLE rno.HistoricoDesagregadoCarteraAportante(
  hdcId bigint IDENTITY(1,1) NOT NULL, 
  hdcFechaHistorico date NOT NULL,
  hdcCodAdministradora varchar(150),
  hdcNombreAdministradora varchar(100),
  hdcNombreRazonSocial varchar(250),
  hdcTipoDocumento varchar(2),
  hdcNumeroDocumento varchar(16),
  hdcDigitoVerificacion smallint,
  hdcTipoDeuda smallint,
  hdcOrigenCartera smallint,
  hdcTotalDeuda bigint,
  hdcAnioCartera int,
  hdcNumeroPeriodos int,
  hdcUltimaAccion varchar(1),
  hdcFechaUltimaAccion varchar(10),
  hdcEstadoAportante varchar(1),
  hdcClasificacion varchar(2),
  hdcConvenioCobro varchar(1),  
CONSTRAINT PK_HistoricoDesagregadoCarteraAportante_hdcId PRIMARY KEY (hdcId)
);

IF EXISTS (SELECT * FROM sys.schemas WHERE name = 'rep')
BEGIN
EXEC('DROP SCHEMA rep')
END

--changeset dsuesca:03 runOnChange:true
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='HistoricoAvisoIncumplimiento' AND TABLE_SCHEMA = 'rno')
DROP TABLE rno.HistoricoAvisoIncumplimiento;
CREATE TABLE rno.HistoricoAvisoIncumplimiento(
  haiId bigint IDENTITY(1,1) NOT NULL, 
  haiFechaHistorico date NOT NULL,
  haiRazonSocial varchar(250),
  haiTipoDocumento varchar(2),
  haiNumeroDocumento varchar(16),
  haiDigitoVerificacion smallint,
  haiMetodoEnvio varchar(1),
  haiVacio varchar(1),
  haiFecha varchar(10),
  CONSTRAINT PK_HistoricoAvisoIncumplimiento_haiId PRIMARY KEY (haiId)
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='HistoricoPostulacionesYAsignacionesFOVIS' AND TABLE_SCHEMA = 'rno')
DROP TABLE rno.HistoricoPostulacionesYAsignacionesFOVIS;
CREATE TABLE rno.HistoricoPostulacionesYAsignacionesFOVIS(
  hpaId bigint IDENTITY(1,1) NOT NULL,
  hpaFechaHistorico date NOT NULL,
  hpaAnio int,
  hpaFechaInicio varchar(30),
  hpaFechaFin varchar(30),
  hpaFechaAceptacion varchar(30),
  CONSTRAINT PK_HistoricoPostulacionesYAsignacionesFOVIS_hpaId PRIMARY KEY (hpaId)
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='HistoricoEmpresasAportantes' AND TABLE_SCHEMA = 'rno')
DROP TABLE rno.HistoricoEmpresasAportantes;
CREATE TABLE rno.HistoricoEmpresasAportantes(
  hepId bigint IDENTITY(1,1) NOT NULL,
  hepFechaHistorico date NOT NULL,
  heptipoIdentificaicon varchar(1),
  hepperNumeroIdentificacion varchar(16),
  hepNombre varchar(255),
  hepmunCodigo varchar(5),
  hepubiDireccionFisica varchar(100),
  hepestadoVinculacion varchar(1),
  heptipoDeAportante varchar(1),
  heptipoDeSector varchar(1),
  hepactEconomicaPpl varchar(4),
  hepsituacion1429 int,
  hepprogresividad1429 smallint,
  hepaporteTotalMensual bigint,
  hepinteresesMora bigint,
  hepvalorReintegros bigint,
  CONSTRAINT PK_HistoricoEmpresasAportantes_hepId PRIMARY KEY (hepId)
);

--changeset dsuesca:04
DROP TABLE rno.HistoricoPostulacionesYAsignacionesFOVIS;

CREATE TABLE rno.HistoricoAfiliadosACargo(
  hacId bigint IDENTITY(1,1) NOT NULL,
  hacFechaHistorico date NOT NULL,
  hacTipoIdentificacionEmpresa  varchar(1),
  hacNumeroIdentificacionEmpresa  varchar(16),
  hacTipoIdentificacionAfiliado varchar(1),
  hacNumeroIdentificacionAfiliado varchar(16),
  hacTipoIdentificacionPersonaACargo  varchar(1),
  hacNumeroIdentificacionPersonaACargo  varchar(16),
  hacPrimerNombrePersonaACargo  varchar(50),
  hacSegundoNombrePersonaACargo varchar(50),
  hacPrimerApellidoPersonaACargo  varchar(50),
  hacSegundoApellidoPersonaACargo varchar(50),
  hacFechaNacimientoPersonaACargo varchar(30),
  hacGeneroPersonaACargo  int,
  hacBenParentesco  int,
  hacCondicionDiscapacidad  int,
  hacTipoCuotaMonetariaPagadoPersonaCargo int,
  hacNumeroCuotasPagadas  int,
  hacNumeroPeriodosPagados  int,
  hacFechaInicialReporte  date,
  hacFechaFinalReporte  date,
  CONSTRAINT PK_HistoricoAfiliadosACargo_hacId PRIMARY KEY (hacId)
);

-- changeset dsuesca:05
CREATE TABLE rno.HistoricoAfiliadosVivienda(
  havId bigint IDENTITY(1,1) NOT NULL,
  havFechaHistorico date NOT NULL,
  havNitEntidad varchar(8000),
  havNumeroDocAfiliado varchar(16),
  havApellidos varchar(101),
  havNombres varchar(101),
  havNombreEntidad varchar(150),
  havFechaAfiliacion varchar(30),
  havSalario numeric,
  havTipoDocAfiliado varchar(20),
  havFechaInicialReporte date,
  havFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAfiliadosVivienda_havId PRIMARY KEY (havId)
);

-- changeset dsuesca:05
CREATE TABLE rno.HistoricoAfiliadosVivienda(
  havId bigint IDENTITY(1,1) NOT NULL,
  havFechaHistorico date NOT NULL,
  havNitEntidad varchar(8000),
  havNumeroDocAfiliado varchar(16),
  havApellidos varchar(101),
  havNombres varchar(101),
  havNombreEntidad varchar(150),
  havFechaAfiliacion varchar(30),
  havSalario numeric,
  havTipoDocAfiliado varchar(20),
  havFechaInicialReporte date,
  havFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAfiliadosVivienda_havId PRIMARY KEY (havId)
);

-- changeset dsuesca:06
CREATE TABLE rno.HistoricoEmpresasMora(
  hemId bigint IDENTITY(1,1) NOT NULL,
  hemFechaHistorico date NOT NULL,
  hemTipoIdentificacion varchar(1),
  hemPerNumeroIdentificacion varchar(16),
  hemNombreEmpresa varchar(200),
  hemMunCodigo varchar(5),
  hemUbiDireccionFisica varchar(300),
  hemNombreRepreLegal varchar(203),
  hemFechaCreacion varchar(30),
  hemSaldoMora bigint,
  hemPeriodoMora int,
  hemGestionProcesoCobro int,
  hemAcuerdoPago int,
  hemCarteraRecuperada numeric(19,5),
  hemEmailEmpresa_repLegal varchar(255),
  hemFechaInicialReporte date,
  hemFechaFinalReporte date,
  CONSTRAINT PK_HistoricoEmpresasMora_hemId PRIMARY KEY (hemId)
);

-- TABLA DE REPORTES
CREATE TABLE rno.ReportesNormativos(
  rnoId int IDENTITY(1,1) NOT NULL,
  rnoNombre varchar(200) NOT NULL,
  rnoNombreSP varchar(100) NOT NULL,
  rnoGenerarDiario bit,
  rnoDiaSemana smallint,
  rnoMensual bit,
  rnoMesDia VARCHAR(100),
  CONSTRAINT PK_ReportesNormativos_rnoId PRIMARY KEY (rnoId)
)

INSERT rno.ReportesNormativos (rnoNombre,rnoNombreSP,rnoGenerarDiario,rnoDiaSemana,rnoMensual,rnoMesDia)
VALUES
('HistoricoDesagregadoCarteraAportante','USP_GET_HistoricoDesagregadoCarteraAportante',1,NULL,1,NULL),
('HistoricoEmpresasAportantes','USP_GET_HistoricoEmpresasAportantes',1,NULL,1,NULL),
('HistoricoAvisoIncumplimiento','USP_GET_HistoricoAvisoIncumplimiento',1,NULL,1,NULL),
('HistoricoAfiliadosACargo','USP_GET_HistoricoAfiliadosACargo',1,NULL,1,NULL),
('HistoricoAfiliadosVivienda','USP_GET_HistoricoAfiliadosVivienda',1,NULL,1,NULL),
('HistoricoEmpresasMora','USP_GET_HistoricoEmpresasMora',1,NULL,1,NULL),
('HistoricoUbicacionYContacto','USP_GET_HistoricoUbicacionYContacto',1,NULL,NULL,'12-31'),
('HistoricoConsolidadoCartera','USP_GET_HistoricoConsolidadoCartera',1,NULL,1,NULL),
('HistoricoDevolucionesUGPP','USP_GET_HistoricoDevolucionesUGPP',1,NULL,1,NULL),
('HistoricoRegistroUnicoEmpleadores','USP_GET_HistoricoRegistroUnicoEmpleadores',1,NULL,NULL,'06-30,12-31'),
('HistoricoMaestroAfiliados','USP_GET_HistoricoMaestroAfiliados',1,6,NULL,NULL),
('HistoricoArchivoMaestroSubsidios','USP_GET_HistoricoArchivoMaestroSubsidios',1,6,NULL,NULL),
('HistoricoNovedadesAfiliadosYSubsidios','USP_GET_HistoricoNovedadesAfiliadosYSubsidios',1,6,NULL,NULL),
('HistoricoNovedadesEstadoAfiliacion','USP_GET_HistoricoNovedadesEstadoAfiliacion',1,6,NULL,NULL),
('HistoricoAfiliados','USP_GET_HistoricoAfiliados',1,NULL,1,NULL),
('HistoricoAsignacionEntregaReintegroFOVIS','USP_GET_HistoricoAsignacionEntregaReintegroFOVIS',1,NULL,1,NULL),
('HistoricoPagoPorFueraPila','USP_GET_HistoricoPagoPorFueraPila',1,NULL,1,NULL),
('HistoricoTrabajadoresSectorAgropecuario','USP_GET_HistoricoTrabajadoresSectorAgropecuario',1,NULL,NULL,'06-30,12-31'),
('HistoricoInconsistenciasUGPP','USP_GET_HistoricoInconsistenciasUGPP',1,NULL,1,NULL),
('HistoricoEmpleadoresMorosos','USP_GET_HistoricoEmpleadoresMorosos',1,NULL,NULL,'03-30,06-30,09-30,12-30'),
('HistoricoAfiliadosBeneficiariosFOVIS','USP_GET_HistoricoAfiliadosBeneficiariosFOVIS',1,NULL,1,NULL);


ALTER TABLE rno.HistoricoEmpresasAportantes ADD hepFechaInicialReporte date;
ALTER TABLE rno.HistoricoEmpresasAportantes ADD hepFechaFinalReporte date;

ALTER TABLE rno.EjecucionReporteNormativo DROP CONSTRAINT UK_EjecucionReporteNormativo_ernPeriodo_ernReporte;
ALTER TABLE rno.EjecucionReporteNormativo DROP COLUMN ernReporte;
DELETE FROM rno.EjecucionReporteNormativo;
ALTER TABLE rno.EjecucionReporteNormativo ADD ernReporteNormativo int NOT NULL;
ALTER TABLE rno.EjecucionReporteNormativo ADD CONSTRAINT FK_EjecucionReporteNormativo_ernReporteNormativo
FOREIGN KEY (ernReporteNormativo) REFERENCES rno.ReportesNormativos(rnoId);
ALTER TABLE rno.EjecucionReporteNormativo ADD CONSTRAINT UK_EjecucionReporteNormativo_ernPeriodo_ernReporteNormativo
UNIQUE (ernPeriodo,ernReporteNormativo);
ALTER TABLE rno.EjecucionReporteNormativo ADD ernInicioEjecucion DATETIME;
ALTER TABLE rno.EjecucionReporteNormativo ADD ernFinEjecucion DATETIME;
ALTER TABLE rno.EjecucionReporteNormativo DROP COLUMN ernFecha;

-- changeset dsuesca:07
CREATE TABLE rno.HistoricoUbicacionYContacto(
  hucId bigint IDENTITY(1,1) NOT NULL,
  hucFechaHistorico date NOT NULL,
  hubCodAdministradora varchar(150),
  hubNombreAdministradora varchar(150),
  hubNombreRazonSocialAportante varchar(250),
  hubTipoDocumento varchar(2),
  hubNumeroDocumento varchar(16),
  hubNumeroDigitoVerificacion smallint,
  hubDireccion1 varchar(300),
  hubCodDepartamento1 varchar(2),
  hubCodMunicipio1 varchar(5),
  hubNombreDepartamento1 varchar(100),
  hubNombreMunicipio1 varchar(50),
  hubDireccion2 varchar(300),
  hubCodDepartamento2 varchar(2),
  hubCodMunicipio2 varchar(5),
  hubNombreDepartamento2 varchar(100),
  hubNombreMunicipio2 varchar(50),
  hubTelefono1 varchar(10),
  hubIndTelefono1 varchar(2),
  hubTelefono2 varchar(10),
  hubIndTelefono2 varchar(2),
  hubCelular1 varchar(10),
  hubCelular2 varchar(10),
  hubEmail1 varchar(255),
  hubEmail2 varchar(255),
  hubUltimaFechaActualizacion date,
  hucFechaInicialReporte date,
  hucFechaFinalReporte date,
  CONSTRAINT PK_HistoricoUbicacionYContacto_hucId PRIMARY KEY (hucId)
);

CREATE TABLE rno.HistoricoConsolidadoCartera(
  hccId bigint IDENTITY(1,1) NOT NULL,
  hccFechaHistorico date NOT NULL,
  hccCodAdministradora varchar(150),
  hccNombreAdministradora varchar(100),
  hccTipoDeuda int,
  hccOrigenCartera int,
  hccAnioCartera int,
  hccNumeroPeriodos int,
  hccTotalDeuda bigint,
  hccFechaInicialReporte date,
  hccFechaFinalReporte date,
  CONSTRAINT PK_HistoricoConsolidadoCartera_hccId PRIMARY KEY (hccId)
);

CREATE TABLE rno.HistoricoDevolucionesUGPP(
  hduId bigint IDENTITY(1,1) NOT NULL,
  hduFechaHistorico date NOT NULL,
  hduTipoDocumento varchar(2),
  hduNumeroDocumento varchar(16),
  hduRazonSocial varchar(100),
  hduDireccion varchar(100),
  hduMunicipio varchar(5),
  hduDepartamento varchar(2),
  hduConcepto varchar(28),
  hduAnio int,
  hduMes int,
  hduSubsistemaDevolucion varchar(13),
  hduNombreActo varchar(100),
  hduNumeroActo varchar(12),
  hduFechaActo varchar(30),
  hduValor numeric(9,0),
  hduNombreAdministradora varchar(50),
  hduCodigoAdministradora varchar(150),
  hduFechaInicialReporte date,
  hduFechaFinalReporte date,
  CONSTRAINT PK_HistoricoDevolucionesUGPP_hduId PRIMARY KEY (hduId)
);

CREATE TABLE rno.HistoricoRegistroUnicoEmpleadores(
  hruId bigint IDENTITY(1,1) NOT NULL,
  hruFechaHistorico date NOT NULL,
  hruAportante varchar(250),
  hruTipoDocumento int,
  hruPerNumeroIdentificacion varchar(16),
  hruRepresentanteLegal varchar(250),
  hruDireccion varchar(300),
  hruMunicipio varchar(50),
  hruDepartamento varchar(100),
  hruDivipola varchar(5),
  hruTelefono varchar(10),
  hruCorreoElectronico varchar(255),
  hruPaginaWeb varchar(256),
  hruTrabajadoresA int,
  hruTrabajadoresB int,
  hruTrabajadoresC int,
  hruTotalTrabajadores int,
  hruCiiCodigo varchar(4),
  hruResponsableAfiliaciones varchar(250),
  hruTelefonoFijoResponsable varchar(10),
  hruTelefonoCelularResponsable varchar(10),
  hruEMailResponsable varchar(255),
  hruFechaInicialReporte date,
  hruFechaFinalReporte date,
  CONSTRAINT PK_HistoricoRegistroUnicoEmpleadores_hruId PRIMARY KEY (hruId)
);

CREATE TABLE rno.HistoricoMaestroAfiliados(
  hmaId bigint IDENTITY(1,1) NOT NULL,
  hmaFechaHistorico date NOT NULL,
  hmaTipoRegistro varchar(1),
  hmaTipoIdentificacionAfiliado varchar(2),
  hmaNumeroIdentificacionAfiliado varchar(16),
  hmaTipoIdentificacionPoblacion varchar(2),
  hmaNumeroIdentificacionPoblacion varchar(16),
  hmaCodigoGenero varchar(1),
  hmaFechaNacimiento date,
  hmaPrimerApellido varchar(50),
  hmaSegundoApellido varchar(50),
  hmaPrimerNombre varchar(50),
  hmaSegundoNombre varchar(50),
  hmaDepartamentoResidencia varchar(2),
  hmaMunicipioResidencia varchar(5),
  hmaFechaAfiliacion date,
  hmaCodigoCaja varchar(150),
  hmaCodTipoAfiliado varchar(1),
  hmaTipoIdentificacionAportante varchar(2),
  hmaNumeroIdentificacionAportante varchar(16),
  hmaDigitoVerficacionAportante smallint,
  hmaRazonSocialAportante varchar(250),
  hmaFechaVinculacion date,
  hmaDepartamentroLaboral varchar(2),
  hmaMunicipioLaboral varchar(5),
  hmaAlDia varchar(1),
  hmaCodigoTipoMiembro varchar(1),
  hmaTipoRelacionConAfiliado varchar(1),
  hmaFechaInicialReporte date,
  hmaFechaFinalReporte date,
  CONSTRAINT PK_HistoricoMaestroAfiliados_hmaId PRIMARY KEY (hmaId)
);

CREATE TABLE rno.HistoricoArchivoMaestroSubsidios(
  hmsId bigint IDENTITY(1,1) NOT NULL,
  hmsFechaHistorico date NOT NULL,
  hmsTipoRegistro int,
  hmsIdentificadorUnicoSubsidio bigint,
  hmsCodigoCCF varchar(150),
  hmsAQuienSeOtorgoSubsidio int,
  hmsTipoIdAfiliado varchar(2),
  hmsNumeroIdAfiliado varchar(16),
  hmsPrimerApellidoAfiliado varchar(50),
  hmsSegundoApellidoAfiliado varchar(50),
  hmsPrimerNombreAfiliado varchar(50),
  hmsSegundoNombreAfiliado varchar(50),
  hmsFechaAsignacionSubsidio datetime,
  hmsValorSubsidio varchar(30),
  hmsCodigoTipoSubsidio int,
  hmsEstadoSubsidio int,
  hmsDepartamentoSubsidio varchar(150),
  hmsMunicipioSubsidio varchar(150),
  hmsFechaEntregaUltimoSubsidio date,
  hmsTipoIdbeneficiario varchar(2),
  hmsNumeroIdBeneficiario varchar(16),
  hmsCodigoGeneroBeneficiario varchar(1),
  hmsFechaNacimientoBeneficiario date,
  hmsPrimerApellidoBeneficiario varchar(50),
  hmsSegundoApellidoBeneficiario varchar(50),
  hmsPrimerNombreBeneficiario varchar(50),
  hmsSegundoNombreBeneficiario varchar(50),
  hmsTipoIdEmpresaRecibeSubsidio int,
  hmsNumeroIdEmpresaRecibeSubsidio varchar(16),
  hmsDigitoVerificacionIdEmpresaSubsidio smallint,
  hmsRazonSocialEmpresaSubsidio varchar(250),
  hmsFechaInicialReporte date,
  hmsFechaFinalReporte date,
  CONSTRAINT PK_HistoricoArchivoMaestroSubsidios_hmsId PRIMARY KEY (hmsId)
);

CREATE TABLE rno.HistoricoNovedadesAfiliadosYSubsidios(
  hnaId bigint IDENTITY(1,1) NOT NULL,
  hnaFechaHistorico date NOT NULL,
  hnaTipoRegistro int,
  hnaCodigoCCF varchar(150),
  hnaTipoIdAfiliado varchar(2),
  hnaNumeroIdAfiliado varchar(16),
  hnaCodigoGeneroAfiliado varchar(1),
  hnaFechaNacimientoAfiliado date,
  hnaPrimerApellidoAfiliado varchar(50),
  hnaSegundoApellidoAfiliado varchar(50),
  hnaPrimerNombreAfiliado varchar(50),
  hnaSegundoNombreAfiliado varchar(50),
  hnaCodigoNovedad varchar(3),
  hnaTipoIdAfiliado2 varchar(2),
  hnaNumeroIdAfiliado2 varchar(16),
  hnaCodigoGeneroAfiliado2 varchar(1),
  hnaFechaNacimientoAfiliado2 date,
  hnaPrimerApellidoAfiliado2 varchar(50),
  hnaSegundoApellidoAfiliado2 varchar(50),
  hnaPrimerNombreAfiliado2 varchar(50),
  hnaSegundoNombreAfiliado2 varchar(50),
  hnaDepartamentoAfiliado varchar(2),
  hnaMunicipioAfiliado varchar(5),
  hnaFechaAfiliacion datetime,
  hnaCodigoTipoAfiliado int,
  hnaTipoIdAportante varchar(2),
  hnaNumeroIdAportante varchar(16),
  hnaDigitoVerificacionAportante smallint,
  hnaRazonSocialAportante varchar(250),
  hnaFechaVinculacionAportante date,
  hnaDepartamentoUbicacion varchar(2),
  hnaMunicipioUbicacion varchar(5),
  hnaCodigoTipoMiembroPoblacionCubierta int,
  hnaTipoIdMiembroPoblacionCubierta varchar(20),
  hnaNumeroIdMiembroPoblacionCubierta varchar(16),
  hnaCodigoCondicionBeneficiario varchar(1),
  hnaCodigoTipoRelacionConAfiliadoBen int,
  hnaFechaAsignacionSubsidio datetime,
  hnaValorSubsidio varchar(30),
  hnaCodigoTipoSubsidio int,
  hnaEstadoSubsidio int,
  hnaDepartamentoSubsidio varchar(150),
  hnaMunicipioSubsidio varchar(150),
  hnaFechaEntregaUltimoSubsidio datetime,
  hnaFechaDesvinculacionAportante datetime,
  hnaFechaRetiroAfiliado datetime,
  hnaFechaFallecimiento date,
  hnaTipoIdbeneficiario varchar(2),
  hnaNumeroIdBeneficiario varchar(16),
  hnaIdentificadorUnicoSubsidio bigint,
  hnaNuevoTipoIdMiembrePoblacionCubierta varchar(20),
  hnaNuevoNumeroIdMiembroPoblacionCubierta varchar(16),
  hnaCausaRetiro int,
  hnaFechaInicialReporte date,
  hnaFechaFinalReporte date,
  CONSTRAINT PK_HistoricoNovedadesAfiliadosYSubsidios_hnaId PRIMARY KEY (hnaId)
);

CREATE TABLE rno.HistoricoNovedadesEstadoAfiliacion(
  hneId bigint IDENTITY(1,1) NOT NULL,
  hneFechaHistorico date NOT NULL,
  hneTipoRegistro int,
  hneCodigoCCF varchar (150),
  hnePerTipoIdentificacion varchar (20),
  hnePerNumeroIdentificacion varchar (16),
  hnePerPrimerApellido varchar (50),
  hnePerSegundoApellido varchar (50),
  hnePerPrimerNombre varchar (50),
  hnePerSegundoNombre varchar (50),
  hneCodigoNovedad varchar (3),
  hneEstadoAfiliacion varchar (8),
  hneTipoIdApor varchar (20),
  hneNumIdApor varchar (16),
  hneDigitoVerificacion smallint,
  hneRazonSocial varchar (250),
  hneEstadoMora int,
  hneFechaInicialReporte date,
  hneFechaFinalReporte date,
  CONSTRAINT PK_HistoricoNovedadesEstadoAfiliacion_hneId PRIMARY KEY (hneId)
);


CREATE TABLE rno.HistoricoAfiliados(
  hraId bigint IDENTITY(1,1) NOT NULL,
  hraFechaHistorico date NOT NULL,
  hraTipoIdentificacionEmpresa varchar(1),
  hraNumeroIdentificacion varchar(16),
  hraTipoIdentificacionAfiliado varchar(1),
  hraNumeroIdentificacionAfiliado varchar(16),
  hraPerPrimerNombre varchar(50),
  hraPerSegundoNombre varchar(50),
  hraPerPrimerApellido varchar(50),
  hraPerSegundoApellido varchar(50),
  hraPedFechaNacimiento varchar(30),
  hraGenero varchar(1),
  hraCodigoMunicipio varchar(5),
  hraAreaGeografica varchar(1),
  hraSalarioBasico bigint,
  hraTipoAfiliado varchar(2),
  hraCategoria varchar(1),
  hraBeneficiarioCuota varchar(1),
  hraFechaInicialReporte date,
  hraFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAfiliados_hraId PRIMARY KEY (hraId)
);

CREATE TABLE rno.HistoricoAsignacionEntregaReintegroFOVIS(
  hapId bigint IDENTITY(1,1) NOT NULL,
  hapFechaHistorico date NOT NULL,  
  hapFuenteFinanciamiento varchar(1),
  hapTipoPlanVivienda int,
  hapCodigoMunicipio varchar(5),
  hapGenero int,
  hapRangoEdad int,
  hapNivelIngreso int,
  hapComponente int,
  hapEstadoSubsidio int,
  hapAnioVigencia int,
  hapCantidadSubsidios int,
  hapValorSubsidios bigint,
  hapFechaInicialReporte date,
  hapFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAsignacionEntregaReintegroFOVIS_hapId PRIMARY KEY (hapId)
);


CREATE TABLE rno.HistoricoPagoPorFueraPila(
  hpfId bigint IDENTITY(1,1) NOT NULL,
  hpfFechaHistorico date NOT NULL,    
  hpfTipoDocumento varchar(2),
  hpfNumeroDocumento varchar(15),
  hpfRazonSocial varchar(100),
  hpfDireccion varchar(200),
  hpfMunicipio varchar(5),
  hpfDepartamento varchar(2),
  hpfAnio int,
  hpfMes int,
  hpfSubsistema varchar(20),
  hpfDiasCotizados smallint,
  hpfIngresoBaseDeCotizacion numeric(9,0),
  hpfCedulaCotizante varchar(15),
  hpfNombreCotizante varchar(100),
  hpfNovedad varchar(26),
  hpfPlanilla varchar(1),
  hpfFechaPago varchar(30),
  hpfValor numeric(9,0),
  hpfNombreAdministradora varchar(50),
  hpfCodigoAdministradora varchar(5),
  hpfFechaInicialReporte date,
  hpfFechaFinalReporte date,
  CONSTRAINT PK_HistoricoPagoPorFueraPila_hpfId PRIMARY KEY (hpfId)
);

CREATE TABLE rno.HistoricoInconsistenciasUGPP(
  hinId bigint IDENTITY(1,1) NOT NULL,
  hinFechaHistorico date NOT NULL,    
  hinTipoAdmin varchar(3),
  hinCodAdmin varchar(150),
  hinNomAdmin varchar(100),
  hinTipoIdAportante varchar(2),
  hinNumIdAportante varchar(16),
  hinRazonSocial varchar(250),
  hinCodigoDep varchar(2),
  hinCod varchar(5),
  hinDireccion varchar(300),
  hinTipoIdCotizante varchar(2),
  hinNumIdCotizante varchar(16),
  hinConcepto varchar(11),
  hinAnioInicio int,
  hinMesInicio int,
  hinAnioFin int,
  hinMesFin int,
  hinDeuda numeric(9,0),
  hinUltimaAccion varchar(7),
  hinFechaAccion date,
  hinObservaciones int,
  hinFechaInicialReporte date,
  hinFechaFinalReporte date,
  CONSTRAINT PK_HistoricoInconsistenciasUGPP_hinId PRIMARY KEY (hinId)
);

CREATE TABLE rno.HistoricoEmpleadoresMorosos(
  hesId bigint IDENTITY(1,1) NOT NULL,
  hesFechaHistorico date NOT NULL,  
  hesNombreEmpresa varchar(250),
  hesPerNumeroIdentificacion varchar(16),
  hesNombreRepreLegal varchar(203),
  hesUbiDireccionFisica varchar(300),
  hesMunNombre varchar(50),
  hesFechaMora date,
  hesPresuntoValorCartera numeric(19),
  hesFechaInicialReporte date,
  hesFechaFinalReporte date,
  CONSTRAINT PK_HistoricoEmpleadoresMorosos_hesId PRIMARY KEY (hesId)
);


CREATE TABLE rno.HistoricoAfiliadosBeneficiariosFOVIS(
  habId bigint IDENTITY(1,1) NOT NULL,
  habFechaHistorico date NOT NULL,    
  habValor varchar(150),
  habNumeroIdentificacion1 varchar(16),
  habApellido varchar(101),
  habNombre varchar(101),
  habValor2 varchar(150),
  habSafFechaAceptacion varchar(20),
  habValorAsignadoSFV varchar(30),
  habNumeroResolucion varchar(20),
  habVacio varchar(1),
  habTipoIdentificacion varchar(1),
  habFechaInicialReporte date,
  habFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAfiliadosBeneficiariosFOVIS_habId PRIMARY KEY (habId)
);

ALTER TABLE rno.ReportesNormativos ADD rnoActivo bit;
UPDATE rno.ReportesNormativos SET rnoActivo = 1;
DELETE rno.ReportesNormativos WHERE rnoNombre = 'HistoricoTrabajadoresSectorAgropecuario';

-- changeset dsuesca:08

EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCodAdministradora','hucCodAdministradora', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreAdministradora','hucNombreAdministradora', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreRazonSocialAportante','hucNombreRazonSocialAportante', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubTipoDocumento','hucTipoDocumento', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNumeroDocumento','hucNumeroDocumento', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNumeroDigitoVerificacion','hucNumeroDigitoVerificacion', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubDireccion1','hucDireccion1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCodDepartamento1','hucCodDepartamento1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCodMunicipio1','hucCodMunicipio1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreDepartamento1','hucNombreDepartamento1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreMunicipio1','hucNombreMunicipio1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubDireccion2','hucDireccion2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCodDepartamento2','hucCodDepartamento2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCodMunicipio2','hucCodMunicipio2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreDepartamento2','hucNombreDepartamento2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubNombreMunicipio2','hucNombreMunicipio2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubTelefono1','hucTelefono1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubIndTelefono1','hucIndTelefono1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubTelefono2','hucTelefono2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubIndTelefono2','hucIndTelefono2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCelular1','hucCelular1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubCelular2','hucCelular2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubEmail1','hucEmail1', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubEmail2','hucEmail2', 'COLUMN';
EXEC sp_rename 'rno.HistoricoUbicacionYContacto.hubUltimaFechaActualizacion','hucUltimaFechaActualizacion', 'COLUMN';

-- changeset dsuesca:09
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios DROP COLUMN hnaCodigoGeneroAfiliado;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios DROP COLUMN hnaFechaNacimientoAfiliado;

-- changeset dsuesca:10
ALTER TABLE rno.HistoricoEmpleadoresMorosos ADD hesPeriodosSinPago smallint;