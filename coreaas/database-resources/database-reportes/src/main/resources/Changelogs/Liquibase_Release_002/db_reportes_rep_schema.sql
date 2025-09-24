--liquibase formatted sql

--changeset dsuesca:01

CREATE TABLE rno.HistoricoNumeroCuotas(
  hncId bigint IDENTITY(1,1) NOT NULL,
  hncFechaHistorico date NOT NULL,  
  hncCodigoDepartamento varchar(2),
  hncAnio smallint,
  hncValor numeric(19,5),
  hncFechaInicialReporte date,
  hncFechaFinalReporte date,
  CONSTRAINT PK_HistoricoNumeroCuotas_hncId PRIMARY KEY (hncId)
);

INSERT rno.ReportesNormativos (rnoNombre,rnoNombreSP,rnoGenerarDiario,rnoDiaSemana,rnoMensual,rnoMesDia)
VALUES
('HistoricoNumeroCuotas','USP_GET_HistoricoNumeroCuotas',1,NULL,1,NULL)

--changeset dsuesca:02
CREATE TABLE rno.HistoricoNumeroPersonasACargo(
  hpcId bigint IDENTITY(1,1) NOT NULL,
  hpcFechaHistorico date NOT NULL,  
  hpcCodigoDepartamento varchar(2),
  hpcNumeroPersonasMes int,
  hpcNumeroPersonasRetroactivo int,
  hpcFechaInicialReporte date,
  hpcFechaFinalReporte date,
  CONSTRAINT PK_HistoricoNumeroPersonasACargo_hpcId PRIMARY KEY (hpcId)
);

INSERT rno.ReportesNormativos (rnoNombre,rnoNombreSP,rnoGenerarDiario,rnoDiaSemana,rnoMensual,rnoMesDia)
VALUES
('HistoricoNumeroPersonasACargo','USP_GET_HistoricoNumeroPersonasACargo',1,NULL,1,NULL)

--changeset dsuesca:03
CREATE TABLE rno.HistoricoAportantesProcesoEnUnidad(
  hauId bigint IDENTITY(1,1) NOT NULL,
  hauFechaHistorico date NOT NULL,  
  hauTipoDocumento varchar(20),
  hauNumeroDocumento varchar(16),
  hauRazonSocial varchar(250),
  hauPeriodoInicio date,
  hauPeriodoFin date,
  hauAdelantadoAccion bit,
  hauTipoAccion smallint,
  hauCodigoAdmin varchar(150),
  hauNombreAdmin varchar(100),
  hauFechaInicialReporte date,
  hauFechaFinalReporte date,
  CONSTRAINT PK_HistoricoAportantesProcesoEnUnidad_hauId PRIMARY KEY (hauId)
);

INSERT rno.ReportesNormativos (rnoNombre,rnoNombreSP,rnoGenerarDiario,rnoDiaSemana,rnoMensual,rnoMesDia)
VALUES
('HistoricoAportantesProcesoEnUnidad','USP_GET_HistoricoAportantesProcesoEnUnidad',1,NULL,1,NULL)

--changeset dsuesca:04
ALTER TABLE rno.HistoricoAportantesProcesoEnUnidad ADD hauValorDeuda NUMERIC(19,5);

--changeset dsuesca:05
CREATE TABLE rno.HistoricoSeguimientosTrasladosMora(
  hstId bigint IDENTITY(1,1) NOT NULL,
  hstFechaHistorico date NOT NULL,
  hstFechaCorteReporte varchar(10),
  hstNumeroAsignadoUnidad varchar(15),
  hstFechaAsignadaUnidad varchar(10),
  hstProcesoTraslado varchar(41),
  hstTipoDocumento varchar(2),
  hstNumeroDocumento varchar(16),
  hstRazonSocial varchar(250),
  hstTipoSubsistema varchar(3),
  hstCodigoAdmin varchar(150),
  hstNombreAdmin varchar(100),
  hstNumComunicacionSalida varchar(15),
  hstFechaSalidaUnidad varchar(10),
  hstUltimaGestionAdra varchar(1),
  hstFechaUltimaGestion varchar(10),
  hstObservaciones varchar(1),
  hstFechaInicialReporte date,
  hstFechaFinalReporte date,
  CONSTRAINT PK_HistoricoSeguimientosTrasladosMora_hstId PRIMARY KEY (hstId)
);

INSERT rno.ReportesNormativos (rnoNombre,rnoNombreSP,rnoGenerarDiario,rnoDiaSemana,rnoMensual,rnoMesDia,rnoActivo)
VALUES
('HistoricoSeguimientosTrasladosMora','USP_GET_HistoricoSeguimientosTrasladosMora',1,NULL,NULL,'03-30,06-30,09-30,12-30',1);

UPDATE rno.ReportesNormativos SET rnoActivo=1;
UPDATE rno.ReportesNormativos
SET rnoMensual = NULL,
	rnoMesDia = '03-30,06-30,09-30,12-30'
WHERE rnoNombre = 'HistoricoDesagregadoCarteraAportante';

--changeset dsuesca:06
ALTER TABLE rno.HistoricoEmpresasAportantes ADD hepsituacion590 int;
ALTER TABLE rno.HistoricoEmpresasAportantes ADD hepprogresividad590 smallint;

--changeset dsuesca:07
ALTER TABLE rno.HistoricoAfiliadosACargo ADD hacValorTotal bigint;

--changeset dsuesca:08
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfNumeroDocumento VARCHAR(16);
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfIngresoBaseDeCotizacion NUMERIC(19,5);
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfValor NUMERIC(19,5);
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfTipoDocumento VARCHAR(3);
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfPlanilla VARCHAR(10);
ALTER TABLE rno.HistoricoPagoPorFueraPila ALTER COLUMN hpfCedulaCotizante VARCHAR(16);

--changeset dsuesca:09
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaCodigoGeneroAfiliado  varchar (1);
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE12  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE21  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE22  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE40  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE41  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE42  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE43  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE44  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE45  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE46  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE47  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE48  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE49  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE50  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE51  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE52  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE53  int;
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaVARIABLE54  int;

--changeset dsuesca:10
ALTER TABLE rno.HistoricoNovedadesAfiliadosYSubsidios ADD hnaFechaNacimientoAfiliado DATE;

--changeset dsuesca:11
ALTER TABLE rno.HistoricoDevolucionesUGPP ALTER COLUMN hduTipoDocumento VARCHAR(3);
ALTER TABLE rno.HistoricoDevolucionesUGPP ALTER COLUMN hduNumeroActo VARCHAR(20);


--changeset flopez:11
CREATE TABLE rno.HistoricoConsolidadoPagosReintegroFOVIS (
  hprId BIGINT IDENTITY(1,1) NOT NULL,
  hprFechaHistorico DATE,
  hprAnioVigenciaAsignacionSubsidio INT,
  hprCodigoTipoPlanVivienda INT, 
  hprEstadoSubsidio INT, 
  hprCantidadSubsidios INT,
  hprValorSubsidios BIGINT,
  hprFechaInicialReporte DATE,
  hprFechaFinalReporte DATE
);

ALTER TABLE rno.HistoricoConsolidadoPagosReintegroFOVIS ADD CONSTRAINT PK_HistoricoConsolidadoPagosReintegroFOVIS_hprId
PRIMARY KEY (hprId) ;

INSERT INTO rno.ReportesNormativos (rnoNombre, rnoNombreSP , rnoGenerarDiario, rnoMesDia, rnoActivo)
VALUES ('HistoricoConsolidadoPagosReintegroFOVIS', 'USP_GET_HistoricoConsolidadoPagosReintegroFOVIS', 1, '12-31', 1);


CREATE TABLE rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS (
  hcmId BIGINT IDENTITY(1,1) NOT NULL,
  hcmFechaHistorico DATE,
  hcmTipoIdentificacion VARCHAR(1),
  hcmNumeroIdentificacion VARCHAR(16),
  hcmAnioVigenciaAsignacionSubsidio INT,
  hcmCodigoTipoPlanVivienda INT, 
  hcmEstadoSubsidio INT, 
  hcmValorSubsidios BIGINT,
  hcmFechaInicialReporte DATE,
  hcmFechaFinalReporte DATE
);

ALTER TABLE rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS ADD CONSTRAINT PK_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS_hcmId
PRIMARY KEY (hcmId) ;

INSERT INTO rno.ReportesNormativos (rnoNombre, rnoNombreSP , rnoGenerarDiario, rnoMesDia, rnoActivo)
VALUES ('HistoricoConsolidadoPagosReintegroMicroDatoFOVIS', 'USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS', 1, '12-31', 1);

CREATE TABLE rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS (
  hamId BIGINT IDENTITY(1,1) NOT NULL,
  hamFechaHistorico DATE,
  hamTipoIdentificacion VARCHAR(1),
  hamNumeroIdentificacion VARCHAR(16),
  hamComponenteHogar VARCHAR(1),
  hamTipoIdIntegrante VARCHAR(1),
  hamNumeroIdIntegrante VARCHAR(16),
  hamAfiliadoACaja VARCHAR(1),
  hamPrimerNombre VARCHAR(50),
  hamSegundoNombre VARCHAR(50),
  hamPrimerApellido VARCHAR(50),
  hamSegundoApellido VARCHAR(50),
  hamParentezcoIntegrante VARCHAR(1),
  hamIngresosIntegrante NUMERIC(19,5),
  hamNivelIngreso INT,
  hamComponente INT,
  hamAnioVigenciaAsignacionSubsidio INT,
  hamEstadoSubsidio INT, 
  hamValorSubsidios BIGINT,
  hamFechaInicialReporte DATE,
  hamFechaFinalReporte DATE
);
ALTER TABLE rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS ADD CONSTRAINT PK_HistoricoAsignacionEntregaReintegroMicrodatoFOVIS_hamId
PRIMARY KEY (hamId);

INSERT INTO rno.ReportesNormativos (rnoNombre, rnoNombreSP , rnoGenerarDiario, rnoMensual, rnoActivo)
VALUES ('HistoricoAsignacionEntregaReintegroMicrodatoFOVIS', 'USP_GET_HistoricoAsignacionEntregaReintegroMicrodatoFOVIS', 1, 1, 1);

ALTER TABLE rno.HistoricoAfiliados ADD hraOrientacionSexual varchar(30);
ALTER TABLE rno.HistoricoAfiliados ADD hraNivelEducativo varchar(50);
ALTER TABLE rno.HistoricoAfiliados ADD hraOcupacionProfesional varchar(2);
ALTER TABLE rno.HistoricoAfiliados ADD hraFactorVulnerabilidad varchar(60);
ALTER TABLE rno.HistoricoAfiliados ADD hraEstadoCivil varchar(20);
ALTER TABLE rno.HistoricoAfiliados ADD hraPertenenciaEtnica varchar(70);
ALTER TABLE rno.HistoricoAfiliados ADD hraPaisResidencia varchar(70); 
ALTER TABLE rno.HistoricoAfiliadosACargo add hacMunicipioResidencia VARCHAR(5);
ALTER TABLE rno.HistoricoAfiliadosACargo add hacAreaGeografica VARCHAR(1);