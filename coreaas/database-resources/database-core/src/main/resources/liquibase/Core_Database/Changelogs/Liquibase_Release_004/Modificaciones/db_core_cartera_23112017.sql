--liquibase formatted sql

--changeset atoro:01
--comment:Se crean tablas ParametrizacionConveniosPago y ParametrizacionExclusiones
CREATE TABLE ParametrizacionConveniosPago (
	pcpId bigint NOT NULL IDENTITY(1,1),
	pcpCantidadPeriodos smallint NULL,
	pcpNumeroConveniosPermitido smallint NULL,
CONSTRAINT PK_ParametrizacionConveniosPago_pcpId PRIMARY KEY (pcpId)
);

CREATE TABLE ParametrizacionExclusiones(
	pexId bigint NOT NULL IDENTITY(1,1),
	pexExclusionNegocios bit NULL,
	pexImposicionRecurso bit NULL,
	pexConvenioPago bit NULL,
	pexAclaracionMora bit NULL,
	pexRiesgoIncobrabilidad bit NULL,
CONSTRAINT PK_ParametrizacionExclusiones_pexId PRIMARY KEY (pexId)	
);

--changeset atoro:02
--comment:Creacion de la tabla ParametrizacionDesafiliacion
CREATE TABLE ParametrizacionDesafiliacion(
    pdeId BIGINT NOT NULL IDENTITY(1,1),
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
    pdeHabilitado BIT NOT null,
CONSTRAINT PK_ParametrizacionDesafiliacion_pdeId PRIMARY KEY (pdeId)
);

--changeset atoro:03
--comment:Creacion de las tablas ConvenioPago, PagoPeriodoConvenio, ExclusionCartera y PeriodoExclusionMora
CREATE TABLE ConvenioPago(
	copId BIGINT NOT NULL IDENTITY(1,1),
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
CONSTRAINT PK_ConvenioPago_copId PRIMARY KEY (copId)
);
ALTER TABLE ConvenioPago ADD CONSTRAINT FK_ConvenioPago_copPersona FOREIGN KEY (copPersona) REFERENCES Persona(perId);

CREATE TABLE  PagoPeriodoConvenio(
	ppcId BIGINT NOT NULL IDENTITY(1,1),
	ppcConvenioPago BIGINT NOT NULL,
	ppcFechaPago DATE NOT NULL,
	ppcValorCuota NUMERIC(19,5) NOT NULL,
	ppcPeriodo DATE NOT NULL,
CONSTRAINT PK_PagoPeriodoConvenio_ppcId PRIMARY KEY (ppcId)
);
ALTER TABLE PagoPeriodoConvenio ADD CONSTRAINT FK_PagoPeridoConvenio_ppcConvenioPago FOREIGN KEY (ppcConvenioPago) REFERENCES ConvenioPago(copId);

CREATE TABLE ExclusionCartera(
	excId BIGINT NOT NULL IDENTITY(1,1),
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
CONSTRAINT PK_ExclusionCartera_excId PRIMARY KEY (excId)
);
ALTER TABLE ExclusionCartera ADD CONSTRAINT FK_ExclusionCartera_excPersona FOREIGN KEY (excPersona) REFERENCES Persona(perId);  

CREATE TABLE PeriodoExclusionMora(
	pemId BIGINT NOT NULL IDENTITY(1,1), 
	pemPeriodo DATE NOT NULL,
	pemExclusionCartera BIGINT NOT NULL,
CONSTRAINT PK_PeriodoExclusionMora_pemId PRIMARY KEY (pemId)
);
ALTER TABLE PeriodoExclusionMora ADD CONSTRAINT FK_PeriodoExclusionMora_pemExclusionCartera FOREIGN KEY (pemExclusionCartera) REFERENCES ExclusionCartera(excId);

--changeset borozco:04
--comment:Se modifican campos en la tabla ActividadFiscalizacion
ALTER TABLE ActividadFiscalizacion ALTER COLUMN acfActividadFiscalizacion VARCHAR(42) NOT NULL;
ALTER TABLE ActividadFiscalizacion ALTER COLUMN acfResultadoFiscalizacion VARCHAR(33) NULL;

--changeset jusanchez:05
--comment:Se adiciona campo en la tabla PeriodoExclusionMora
ALTER TABLE PeriodoExclusionMora ADD pemEstadoPeriodo VARCHAR(10) NOT NULL;

--changeset atoro:06
--comment:Se adiciona campo en las tablas ParametrizacionPreventiva y ParametrizacionCartera
ALTER TABLE ParametrizacionPreventiva ADD pprEjecucionAutomatica BIT NULL;
ALTER TABLE ParametrizacionCartera ADD pacFechaActualizacion DATETIME NULL;

--changeset clmarin:07
--comment:Insercion de registro en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES('Cierre satisfactorio convenio de pago','Cuerpo','Encabezado','Mensaje','Cierre satisfactorio convenio de pago','Pie','CRR_STC_CNV_PAG');

--changeset clmarin:08
--comment:Insercion de registros en la tabla VariableComunicado - Variables
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','0','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','0','Razon social/Nombre','Razón social de la empresa a la cual se encuentra asociado el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionRepresentanteLegal}','0','Dirección Representante Legal','Dirección capturada en Información de ubicación y correspondencia del representante legal ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoRepresentanteLegal}','0','Teléfono Representante Legal','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadRepresentanteLegal}','0','Ciudad Representante Legal','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));

--changeset clmarin:09
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRR_STC_CNV_PAG'));

--changeset clmarin:10
--comment:Actualizacion de registros en la tabla PlantillaComunicado - Variables
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionRepresentanteLegal}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoRepresentanteLegal}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadRepresentanteLegal}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';

--changeset clmarin:11
--comment:Actualizacion de registros en la tabla PlantillaComunicado - Constantes
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'CRR_STC_CNV_PAG';

--changeset atoro:12
--comment:Creacion de tablas relacionadas con la parametrizacion gestion de cobro
CREATE TABLE ParametrizacionGestionCobro(
	pgcId BIGINT NOT NULL IDENTITY(1,1),
	pgcOficinaPrincipalFisico BIT NULL,
	pgcCorrespondenciaFisico BIT NULL,
	pgcNotificacionJudicialFisico BIT NULL,
	pgcOficinaPrincipalElectronico BIT NULL,
	pgcRepresentanteLegalElectronico BIT NULL,
	pgcResponsableAportesElectronico BIT NULL,
	pgcMetodoEnvioComunicado VARCHAR(11) null,
	pgcTipoParametrizacion VARCHAR(18) null,
CONSTRAINT PK_ParametrizacionGestionCobro_pgcId PRIMARY KEY (pgcId)
);

CREATE TABLE LineaCobro(
	pgcId BIGINT NOT NULL,
	lcoHabilitarAccionCobroA BIT NULL,
	lcoDiasFechaLimite BIGINT NULL,
	lcoDiasParametrizados BIGINT NULL,
	lcoHabilitarAccionCobroB BIT NULL,
	lcoTipoLineaCobro VARCHAR(3) NULL,
CONSTRAINT PK_LineaCobro_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE LineaCobro ADD CONSTRAINT FK_LineaCobro_lcoId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobroA(
	pgcId BIGINT NOT NULL,
	acaSuspensionAutomatica BIT NULL,
	acaDiasLimitePago BIGINT NULL,
	acaHoraEjecucion DATE NULL,
	acaLimiteEnvioComunicado BIGINT NULL,
	acaMetodo VARCHAR(8) NULL,
CONSTRAINT PK_AccionCobroA_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobroA ADD CONSTRAINT FK_AccionCobroA_acaId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 
	
CREATE TABLE AccionCobroB(
	pgcId BIGINT NOT NULL,
	acbFechaGeneracion BIGINT NULL,
	acbHoraEjecucion DATE NULL,
	acbLimiteEnvioComunicado BIGINT NULL,
	acbMetodo VARCHAR(8) NULL,
CONSTRAINT PK_AccionCobroB_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobroB ADD CONSTRAINT FK_AccionCobroB_acbId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro1C(
	pgcId BIGINT NOT NULL,
	accFechaLiquidacion BIGINT NULL,
	accHoraEjecucion DATE NULL,
	accLimiteEnvioDocumento BIGINT NULL,
	accVariableCalculo VARCHAR(16) NULL,
	accCantidadPeriodos BIGINT NULL,
CONSTRAINT PK_AccionCobro1C_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro1C ADD CONSTRAINT FK_AccionCobro1C_accId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro1D1E(
	pgcId BIGINT NOT NULL,
	acdFechaConteo VARCHAR(13) NULL,
	acdDiasTranscurridos BIGINT NULL,
	acdHoraEjecucion DATE NULL,
	acdLimiteEnvio BIGINT NULL,
	acdTipoCobro VARCHAR(12) NULL,
CONSTRAINT PK_AccionCobro1D1E_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro1D1E ADD CONSTRAINT FK_AccionCobro1D1E_acdId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro1F(
	pgcId BIGINT NOT NULL,
	abfAccionCobro1F BIT NULL,
	abfDiasParametrizados SMALLINT NULL,
	abfSiguienteAccion VARCHAR(29) NULL,
CONSTRAINT PK_AccionCobro1F_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro1F ADD CONSTRAINT FK_AccionCobro1F_abfId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro2C(
	pgcId BIGINT NOT NULL,
	aocAnexoLiquidacion BIT NULL,
	aocFechaEjecucion BIGINT NULL,
	aocHoraEjecucion DATE NULL,
CONSTRAINT PK_AccionCobro2C_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro2C ADD CONSTRAINT FK_AccionCobro2C_aocId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro2D(
	pgcId BIGINT NOT NULL,
	aodFechaConteo VARCHAR(13) NULL,
	aodDiasTranscurridos BIGINT NULL,
	aodHoraEjecucion DATE NULL,
CONSTRAINT PK_AccionCobro2D_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro2D ADD CONSTRAINT FK_AccionCobro2D_aodId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro2F2G(
	pgcId BIGINT NOT NULL,
	aofFechaConteo VARCHAR(13) NULL,
	aofDiasTranscurridos BIGINT NULL,
	aofHoraEjecucion DATE NULL,
	aofLimiteEnvio BIGINT NULL,
	aofTipoCobro VARCHAR(12) NULL,
CONSTRAINT PK_AccionCobro2F2G_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro2F2G ADD CONSTRAINT FK_AccionCobro2F2G_aofId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro2H(
	pgcId BIGINT NOT NULL,
	achAccionCobro2H BIT NULL,
	achDiasRegistro BIGINT NULL,
	achDiasParametrizados BIGINT NULL,
	achSiguienteAccion VARCHAR(29) NULL,
CONSTRAINT PK_AccionCobro2H_pgcId PRIMARY KEY (pgcId)
);
ALTER TABLE AccionCobro2H ADD CONSTRAINT FK_AccionCobro2H_achId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId); 

CREATE TABLE AccionCobro2E(
	aceId BIGINT NOT NULL IDENTITY(1,1),
	aceFechaConteo VARCHAR(13) NULL,
	aceDiasTranscurridos BIGINT NULL,
	aceHoraEjecucion DATE NULL,
CONSTRAINT PK_AccionCobro2E_aceId PRIMARY KEY (aceId)
);

--changeset fvasquez:13
--comment:Creacion de la tabla Cartera
CREATE TABLE Cartera(
	carId BIGINT IDENTITY(1,1) NOT NULL,	
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
CONSTRAINT PK_Cartera_carId PRIMARY KEY	(carId)
);
ALTER TABLE Cartera ADD CONSTRAINT FK_Cartera_carPersona FOREIGN KEY (carPersona) REFERENCES Persona(perId);

--changeset clmarin:14
--comment:Se modifica campo en la tabla ConvenioPago
ALTER TABLE ConvenioPago ALTER COLUMN copNombreUsuario VARCHAR(255) NULL;

--changeset abaquero:15
--comment:Se adicionan campos en la tabla Empleador y RolAfiliado
ALTER TABLE Empleador ADD empDiaHabilVencimientoAporte SMALLINT;
ALTER TABLE RolAfiliado ADD roaDiaHabilVencimientoAporte SMALLINT;

--changeset fvasquez:16
--comment:Se modifica campo en la tabla Cartera
ALTER TABLE Cartera ALTER COLUMN carPeriodoDeuda DATE NOT NULL;

--changeset clmarin:16
--comment:Se elimina check constraints
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1D1E_acdFechaConteo')) ALTER TABLE AccionCobro1D1E DROP CONSTRAINT CK_AccionCobro1D1E_acdFechaConteo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2D_aodFechaConteo')) ALTER TABLE AccionCobro2D DROP CONSTRAINT CK_AccionCobro2D_aodFechaConteo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2E_aceFechaConteo')) ALTER TABLE AccionCobro2E DROP CONSTRAINT CK_AccionCobro2E_aceFechaConteo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2F2G_aaofFechaConteo')) ALTER TABLE AccionCobro2F2G DROP CONSTRAINT CK_AccionCobro2F2G_aofFechaConteo;

--changeset mosanchez:16
--comment:Se elimina check constraints
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2F2G_aofFechaConteo')) ALTER TABLE AccionCobro2F2G DROP CONSTRAINT CK_AccionCobro2F2G_aofFechaConteo;
--changeset clmarin:17
--comment:Se modifican campos en las tablas relacionadas con ParametrizacionGestionCobro
EXEC sp_rename 'dbo.LineaCobro.lcoDiasFechaLimite', 'lcoDiasLimitePago', 'COLUMN';
EXEC sp_rename 'dbo.AccionCobroA.acaHoraEjecucion', 'acaFechaHoraEjecucion', 'COLUMN';
EXEC sp_rename 'dbo.AccionCobroA.acaLimiteEnvioComunicado', 'acaDiasLimiteEnvioComunicado', 'COLUMN';
ALTER TABLE AccionCobroA ALTER COLUMN acaFechaHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobroB.acbFechaGeneracion', 'acbDiasGeneracionAviso', 'COLUMN';
ALTER TABLE AccionCobroB ALTER COLUMN acbHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro1C.accFechaLiquidacion', 'accDiasLiquidacion', 'COLUMN';
ALTER TABLE AccionCobro1C ALTER COLUMN accHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro1D1E.acdFechaConteo', 'acdInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro1D1E ALTER COLUMN acdHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2C.aocFechaEjecucion', 'aocDiasEjecucion', 'COLUMN';
ALTER TABLE AccionCobro2C ALTER COLUMN aocHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2D.aodFechaConteo', 'aodInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2D ALTER COLUMN aodHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2E.aceFechaConteo', 'aceInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2E ALTER COLUMN aceHoraEjecucion DATETIME NULL;
EXEC sp_rename 'dbo.AccionCobro2F2G.aofFechaConteo', 'aofInicioDiasConteo', 'COLUMN';
ALTER TABLE AccionCobro2F2G ALTER COLUMN aofHoraEjecucion DATETIME NULL;

--changeset fvasquez:18
--comment:Creacion de la tabla CarteraDependiente
CREATE TABLE CarteraDependiente(
	cadId BIGINT NOT NULL IDENTITY(1,1),
	cadDeudaPresunta NUMERIC(19,5) NULL,
	cadEstadoOperacion VARCHAR(10) NOT NULL,						
	cadCartera BIGINT NOT NULL,
	cadPersona BIGINT NOT NULL,	
CONSTRAINT PK_CarteraDependiente_cadId PRIMARY KEY (cadId)
);
ALTER TABLE CarteraDependiente ADD CONSTRAINT FK_CarteraDependiente_cadCartera FOREIGN KEY (cadCartera) REFERENCES Cartera(carId);
ALTER TABLE CarteraDependiente ADD CONSTRAINT FK_CarteraDependiente_cadPersona FOREIGN KEY (cadPersona) REFERENCES Persona(perId);

--changeset jusanchez:19
--comment:Insercion de registros en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia,pepEstado) VALUES ('ACTUALIZACION_DIAS_HABILES_VENCIMIENTO','00','01','00',NULL,'01','01',NULL,NULL,NULL,'ANUAL','ACTIVO');

--changeset jusanchez:20
--comment:Se actualiza registro en la tabla ParametrizacionEjecucionProgramada
UPDATE ParametrizacionEjecucionProgramada SET pepDiaMes=null,pepMes=null,pepFrecuencia='DIARIO' WHERE pepProceso='ACTUALIZACION_DIAS_HABILES_VENCIMIENTO';

--changeset atoro:21
--comment:Insercion de registros en las tablas ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepFrecuencia, pepEstado) VALUES ('CALCULO_DEUDA_PRESUNTA', '00','01','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepFrecuencia, pepEstado) VALUES ('CONVENIO_PAGO', '00','01','DIARIO','ACTIVO');
