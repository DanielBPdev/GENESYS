--liquibase formatted sql

--changeset atoro:01
--comment: Creacion de las tablas SolicitudNovedadEmpleador y SolicitudNovedadAfiliado
CREATE TABLE SolicitudNovedadEmpleador
(
   sneId BIGINT IDENTITY(1,1) NOT NULL,
   sneIdEmpleador BIGINT,
   CONSTRAINT PK_SolicitudNovedadEmpleador_sneId PRIMARY KEY (sneId) 
 )
 
 ALTER TABLE SolicitudNovedadEmpleador ADD CONSTRAINT FK_SolicitudNovedadEmpleador_sneIdEmpleador FOREIGN KEY (sneIdEmpleador) REFERENCES Empleador
 
 
 CREATE TABLE SolicitudNovedadAfiliado
(
   snaId BIGINT IDENTITY(1,1) NOT NULL,
   snaIdAfiliado BIGINT,
   CONSTRAINT PK_SolicitudNovedadAfiliado_snaId PRIMARY KEY (snaId) 
 )
 
 ALTER TABLE SolicitudNovedadAfiliado ADD CONSTRAINT FK_SolicitudNovedadAfiliado_snaIdAfiliado FOREIGN KEY (snaIdAfiliado) REFERENCES Afiliado
 
 
 --changeset abaquero:02
--comment: Actualizacion de modulo datos PILA 1
 
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoARegistro1' where id = 1
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoAPRegistro1' where id = 2
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIRegistro1' where id = 3
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIRegistro2' where id = 4
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIRegistro3' where id = 5
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIRegistro3' where id = 6
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIRegistro3' where id = 7
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIPRegistro1' where id = 8
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIPRegistro2' where id = 9
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoIPRegistro3' where id = 10
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoFRegistro1' where id = 11
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoFRegistro5' where id = 12
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoFRegistro6' where id = 13
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoFRegistro8' where id = 14
update lineloadcatalog set classname = 'com.asopagos.pila.persistencia.PersistirArchivoFRegistro9' where id = 15

update dbo.validatorparameter set name = 'campoFechaPago' where id = 43;
update dbo.validatorparamvalue set value = '5' where id = 464;

drop table dbo.PilaArchivoIRegistro1;
drop table dbo.PilaArchivoIRegistro2;
drop table dbo.PilaArchivoIRegistro3;
drop table dbo.PilaArchivoARegistro1;
drop table dbo.PilaArchivoIPRegistro1;
drop table dbo.PilaArchivoIPRegistro2;
drop table dbo.PilaArchivoIPRegistro3;
drop table dbo.PilaArchivoAPRegistro1;
drop table dbo.PilaArchivoFRegistro1;
drop table dbo.PilaArchivoFRegistro5;
drop table dbo.PilaArchivoFRegistro6;
drop table dbo.PilaArchivoFRegistro8;
drop table dbo.PilaArchivoFRegistro9;

drop table dbo.PilaEstadoBloque;
drop table dbo.PilaEstadoBloqueOF;
drop table dbo.PilaNormatividadFechaVencimiento;
drop table dbo.PilaIndiceCorreccionPlanilla;
drop table dbo.PilaIndicePlanilla;
drop table dbo.PilaIndicePlanillaOF;
drop table dbo.PilaPasoValores;
drop table dbo.PilaDiasFestivos;
drop table dbo.PilaClasificacionAportante;
drop table dbo.PilaTasasInteresMora;
drop table dbo.PilaOportunidadPresentacionPlanilla;
drop table dbo.PilaConexionOperadorInformacion;
drop table dbo.PilaOperadorInformacionCcf;
drop table dbo.PilaOperadorInformacion;
drop table dbo.PilaEjecucionProgramada;
drop table dbo.PilaProceso;

CREATE TABLE dbo.PilaProceso(
	pprId bigint NOT NULL IDENTITY,
	pprNumeroRadicado varchar(12) NOT NULL,
	pprTipoProceso varchar(30) NOT NULL,
	pprFechaInicioProceso datetime NOT NULL,
	pprFechaFinProceso datetime,
	pprUsuarioProceso varchar(255) NOT NULL,
	pprEstadoProceso varchar(75) NOT NULL,
	CONSTRAINT PK_PilaProcesoValidacion_pprId PRIMARY KEY (pprId)
);

CREATE TABLE dbo.PilaIndicePlanilla (
	pipId bigint NOT NULL IDENTITY,
	pipIdPlanilla bigint NOT NULL,
	pipTipoArchivo varchar(20) NOT NULL,
	pipNombreArchivo varchar(80),
	pipFechaRecibo datetime,
	pipFechaFtp datetime,
	pipCodigoOperadorInformacion varchar(2), 
	pipEstadoArchivo varchar(75),
	pipTipoCargaArchivo varchar(30),
	pipUsuario varchar(255),
	pipIdentificadorDocumento varchar(255),
	pipVersionDocumento varchar(10),
	pipFechaProceso datetime,
	pipUsuarioProceso varchar(255),
	pipFechaEliminacion datetime,
	pipUsuarioEliminacion varchar(255),
	pipProcesar bit,
	pipRegistroActivo bit,
	pipEnLista bit,
	CONSTRAINT PK_PilaIndicePlanilla_pipId PRIMARY KEY (pipId)
);

create table dbo.PilaIndicePlanillaOF(
	pioId bigint NOT NULL IDENTITY, 
	pioFechaPago datetime,
	pioNombreArchivo varchar(80),
	pioFechaRecibo datetime,
	pioFechaFtp datetime,
	pioCodigoAdministradora varchar(6),
	pioCodigoBancoRecaudador varchar(3),
	pioTipoArchivo varchar(20),
	pioTipoCargaArchivo varchar(10),
	pioUsuario varchar(255),
	pioEstadoArchivo varchar(75),
	pioIdentificadorDocumento varchar(255),
	pioVersionDocumento varchar(10),
	pioFechaProceso datetime,
	pioUsuarioProceso varchar(255),
	pioFechaEliminacion datetime,
	pioUsuarioEliminacion varchar(255),
	pioRegistroActivo bit,
	CONSTRAINT PK_PilaIndicePlanillaOF_pioId PRIMARY KEY (pioId)
);

CREATE TABLE dbo.PilaEstadoBloque (
	pebId bigint NOT NULL IDENTITY,
	pebIdPlanilla bigint NOT NULL,
	pebTipoArchivo varchar(20) NOT NULL,
	pebEstadoBloque0 varchar(75),
	pebAccionBloque0 varchar(75),
	pebEstadoBloque1 varchar(75),
	pebAccionBloque1 varchar(75),
	pebEstadoBloque2 varchar(75),
	pebAccionBloque2 varchar(75),
	pebIdLogLecturaBloque2 bigint,
	pebEstadoBloque3 varchar(75),
	pebAccionBloque3 varchar(75),
	pebEstadoBloque4 varchar(75),
	pebAccionBloque4 varchar(75),
	pebIdLogLecturaBloque4 bigint,
	pebEstadoBloque5 varchar(75),
	pebAccionBloque5 varchar(75),
	pebEstadoBloque6 varchar(75),
	pebAccionBloque6 varchar(75),
	CONSTRAINT PK_PilaEstadoBloque_pebId PRIMARY KEY (pebId)
);

create table dbo.PilaEstadoBloqueOF(
	peoId bigint NOT NULL IDENTITY,
	peoIdIndice bigint NOT NULL,
	peoEstadoBloque0 varchar(75),
	peoAccionBloque0 varchar(75),
	peoEstadoBloque1 varchar(75),
	peoAccionBloque1 varchar(75),
	peoIdLogLecturaBloque1 bigint,
	peoEstadoBloque6 varchar(75),
	peoAccionBloque6 varchar(75),
	CONSTRAINT PK_PilaEstadoBloqueOF_peoId PRIMARY KEY (peoId)
);

CREATE TABLE dbo.PilaPasoValores (
	ppvId bigint NOT NULL IDENTITY,
	ppvIdPlanilla bigint NOT NULL,
	ppvTipoPlanilla varchar(75) NOT NULL,
	ppvBloque varchar(11) NOT NULL,
	ppvNombreVariable varchar(75) NOT NULL,
	ppvValorVariable varchar(200),
	CONSTRAINT PK_PilaPasoValores_ppvId PRIMARY KEY (ppvId)
);

create table dbo.DiasFestivos(
	pifId bigint NOT NULL IDENTITY,
	pifConcepto varchar(100),
	pifFecha date,
	CONSTRAINT PK_PilaDiasFestivos_pifId PRIMARY KEY (pifId)
);

create table PilaNormatividadFechaVencimiento(
	pfvId bigint NOT NULL IDENTITY,
	pfvPeriodoInicial varchar(7),
	pfvPeriodoFinal varchar(7),
	pfvUltimoDigitoId varchar(50),
	pfvClasificacion bigint,
	pfvDiaVencimiento smallint,
	pfvTipoFecha varchar(20),
	CONSTRAINT PK_PilaNormatividadFechaVencimiento_pfvId PRIMARY KEY (pfvId)
);

create table PilaClasificacionAportante(
	pcaId bigint NOT NULL IDENTITY,
	pcaTipoArchivo varchar(20),
	pcaCampo smallint,
	pcaValor varchar(30),
	pcaComparacion varchar(20),
	CONSTRAINT PK_PilaClasificacionAportante_pcaId PRIMARY KEY (pcaId)
);

create table PilaOportunidadPresentacionPlanilla(
	popId bigint NOT NULL IDENTITY,
	popTipoPlanilla varchar(1),
	popOportunidad varchar(20),
	popTipoCotizanteEspecifico varchar(2),
	CONSTRAINT PK_PilaOportunidadPresentacionPlanilla_popId PRIMARY KEY (popId)
);

create table PilaTasasInteresMora(
	ptiId bigint NOT NULL IDENTITY,
	ptiFechaInicioTasa date,
	ptiFechaFinTasa date,
	ptiNumeroPeriodoTasa smallint,
	ptiPorcentajeTasa numeric(4,4),
	ptiNormativa varchar(100),
	ptiTipoInteres varchar(20),
	CONSTRAINT PK_PilaTasasInteresMora_ptiId PRIMARY KEY (ptiId)
);

CREATE TABLE dbo.OperadorInformacion(
	oinId bigint NOT NULL IDENTITY,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
	CONSTRAINT PK_PilaOperadorInformacion_oinId PRIMARY KEY (oinId)
);

CREATE TABLE dbo.OperadorInformacionCcf(
	oicId bigint NOT NULL IDENTITY,
	oicIdOperadorInformacion bigint,
	oicIdCajaCompensacion int,
	oicEstado bit,
	CONSTRAINT PK_PilaOperadorInformacionCcf_oicId PRIMARY KEY (oicId)
);

CREATE TABLE dbo.ConexionOperadorInformacion(
	coiId bigint NOT NULL IDENTITY,
	coiIdOperadorinformacionCcf bigint,
	coiProtocolo varchar(10),
	coiUrl varchar(255),
	coiPuerto smallint,
	coiHost varchar(75),
	coiUsuario varchar(255),
	coiContrasena varchar(32),
	CONSTRAINT PK_PilaConexionOperadorInformacion_coiId PRIMARY KEY (coiId)
);

CREATE TABLE dbo.PilaEjecucionProgramada(
	pepId bigint NOT NULL IDENTITY,
	pepFechaDefinicion datetime,
	pepUsuario varchar(255),
	pepFrecuencia varchar(50),
	pepHoraInicio varchar(5),
	pepFechaInicioVigencia datetime,
	pepFechaFinVigencia datetime,
	pepIdCcf int, 
	CONSTRAINT PK_PilaEjecucionProgramada_pepId PRIMARY KEY (pepId)
);

create table PilaIndiceCorreccionPlanilla(
	picId bigint NOT NULL IDENTITY,
	picFechaAccion datetime,
	picAccion varchar(75),
	picEstadoArchivoAfectado varchar(75),
	picIdIndicePlanillaAfectada bigint NOT NULL,
	picIdIndicePlanillaCargado bigint NOT NULL,
	CONSTRAINT PK_PilaIndiceCorreccionPlanilla_picId PRIMARY KEY (picId)
);

create table dbo.PilaErrorValidacionLog (
	pevId bigint NOT NULL IDENTITY,
	pevIdIndicePlanilla bigint,
	pevTipoArchivo varchar(20),
	pevTipoError varchar(20),
	pevNumeroLinea smallint,
	pevBloqueValidacion varchar(11),
	pevCodigoError varchar(10),
	pevMensajeError varchar(255),
	CONSTRAINT PK_PilaErrorValidacionLog_pevId PRIMARY KEY (pevId)
)

ALTER TABLE [PilaEstadoBloque] ADD CONSTRAINT [FK_PilaEstadoBloque_pebIdPlanilla] FOREIGN KEY([pebIdPlanilla]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaEstadoBloqueOF] ADD CONSTRAINT [FK_PilaEstadoBloqueOF_peoIdIndice] FOREIGN KEY([peoIdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaNormatividadFechaVencimiento] ADD CONSTRAINT [FK_PilaNormatividadFechaVencimiento_pfvClasificacion] FOREIGN KEY([pfvClasificacion]) REFERENCES [PilaClasificacionAportante] ([pcaId]);
ALTER TABLE [PilaIndiceCorreccionPlanilla] ADD CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIdIndicePlanillaAfectada] FOREIGN KEY([picIdIndicePlanillaAfectada]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaIndiceCorreccionPlanilla] ADD CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIdIndicePlanillaCargado] FOREIGN KEY([picIdIndicePlanillaCargado]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [OperadorInformacionCcf] ADD CONSTRAINT [FK_PilaOperadorInformacionCcf_oicIdOperadorInformacion] FOREIGN KEY([oicIdOperadorInformacion]) REFERENCES [OperadorInformacion] ([oinId]);
ALTER TABLE [OperadorInformacionCcf] ADD CONSTRAINT [FK_PilaOperadorInformacionCcf_oicIdCajaCompensacion] FOREIGN KEY([oicIdCajaCompensacion]) REFERENCES [CajaCompensacion] ([ccfId]);
ALTER TABLE [ConexionOperadorInformacion] ADD CONSTRAINT [FK_PilaConexionOperadorInformacion_coiIdOperadorinformacionCcf] FOREIGN KEY([coiIdOperadorinformacionCcf]) REFERENCES [OperadorInformacionCcf] ([oicId]);
ALTER TABLE [PilaEjecucionProgramada] ADD CONSTRAINT [FK_PilaEjecucionProgramada_pepIdCcf] FOREIGN KEY([pepIdCcf]) REFERENCES [CajaCompensacion] ([ccfId]);

create table dbo.PilaArchivoIRegistro1(
	pi1Id bigint NOT NULL IDENTITY,
	pi1IdIndice bigint NOT NULL,
	pi1IdCCF varchar(16) NOT NULL,
	pi1DigVerCCF smallint NOT NULL,
	pi1RazonSocial varchar(200) NOT NULL,
	pi1TipoDocAportante varchar(2) NOT NULL,
	pi1IdAportante varchar(16) NOT NULL,
	pi1DigVerAportante smallint,
	pi1TipoAportante varchar(2) NOT NULL,
	pi1Direccion varchar(40) NOT NULL,
	pi1CodCiudad varchar(3) NOT NULL,
	pi1CodDepartamento varchar(2) NOT NULL,
	pi1Telefono bigint NOT NULL,
	pi1Fax bigint NOT NULL,
	pi1Email varchar(60) NOT NULL,
	pi1PeriodoAporte varchar(7) NOT NULL,
	pi1TipoPlanilla varchar(1) NOT NULL,
	pi1FechaPagoAsociado date,
	pi1FechaPago date NOT NULL,
	pi1NumPlanillaAsociada varchar(10),
	pi1NumPlanilla varchar(10) NOT NULL,
	pi1Presentacion varchar(1) NOT NULL,
	pi1CodSucursal varchar(10),
	pi1NomSucursal varchar(40),
	pi1CantidadEmpleados int NOT NULL,
	pi1CantidadAfiliados int NOT NULL,
	pi1CodOperador smallint NOT NULL,
	pi1ModalidadPlanilla smallint NOT NULL,
	pi1DiasMora smallint NOT NULL,
	pi1CantidadReg2 int NOT NULL,
	pi1FechaMatricula date,
	pi1CodDepartamentoBeneficio varchar(2),
	pi1AcogeBeneficio varchar(1) NOT NULL,
	pi1ClaseAportante varchar(1) NOT NULL,
	pi1NaturalezaJuridica smallint NOT NULL,
	pi1TipoPersona varchar(1) NOT NULL,
	CONSTRAINT PK_PilaArchivoIRegistro1_pi1Id PRIMARY KEY (pi1Id)
);

create table PilaArchivoIRegistro2(
	pi2Id bigint NOT NULL IDENTITY,
	pi2IdIndice bigint NOT NULL,
	pi2Secuencia int NOT NULL,
	pi2TipoIdCotizante varchar(2) NOT NULL,
	pi2IdCotizante varchar(16) NOT NULL,
	pi2TipoCotizante smallint NOT NULL,
	pi2SubTipoCotizante smallint NOT NULL,
	pi2ExtrangeroNoObligado varchar(1),
	pi2ColombianoExterior varchar(1),
	pi2CodDepartamento varchar(2),
	pi2CodMunicipio varchar(3),
	pi2PrimerApellido varchar(20) NOT NULL,
	pi2SegundoApellido varchar(30),
	pi2PrimerNombre varchar(20) NOT NULL,
	pi2SegundoNombre varchar(30),
	pi2NovIngreso varchar(1),
	pi2NovRetiro varchar(1),
	pi2NovVSP varchar(1),
	pi2NovVST varchar(1),
	pi2NovSLN varchar(1),
	pi2NovIGE varchar(1),
	pi2NovLMA varchar(1),
	pi2NovVACLR varchar(1),
	pi2DiasIRL varchar(2),
	pi2DiasCotizados smallint NOT NULL,
	pi2SalarioBasico int NOT NULL,
	pi2ValorIBC int NOT NULL,
	pi2Tarifa numeric(5,5) NOT NULL,
	pi2AporteObligatorio int NOT NULL,
	pi2Correcciones varchar(1),
	pi2SalarioIntegral varchar(1),
	pi2FechaIngreso date,
	pi2FechaRetiro date,
	pi2FechaInicioVSP date,
	pi2FechaInicioSLN date,
	pi2FechaFinSLN date,
	pi2FechaInicioIGE date,
	pi2FechaFinIGE date,
	pi2FechaInicioLMA date,
	pi2FechaFinLMA date,
	pi2FechaInicioVACLR date,
	pi2FechaFinVACLR date,
	pi2FechaInicioVCT date,
	pi2FechaFinVCT date,
	pi2FechaInicioIRL date,
	pi2FechaFinIRL date,
	pi2HorasLaboradas smallint,
	CONSTRAINT PK_PilaArchivoIRegistro2_pi2Id PRIMARY KEY (pi2Id)
);

create table dbo.PilaArchivoIRegistro3(
	pi3Id bigint NOT NULL IDENTITY,
	pi3IdIndice bigint NOT NULL,
	pi3ValorTotalIBC bigint,
	pi3ValorTotalAporteObligatorio bigint,
	pi3DiasMora smallint,
	pi3ValorMora bigint,
	pi3ValorTotalAportes bigint,
	CONSTRAINT PK_PilaArchivoIRegistro3_pi3Id PRIMARY KEY (pi3Id)
);

create table dbo.PilaArchivoARegistro1(
	pa1Id bigint NOT NULL IDENTITY,
	pa1IdIndice bigint NOT NULL,
	pa1NombreAportante varchar(200) NOT NULL,
	pa1TipoIdAportante varchar(2) NOT NULL,
	pa1IdAportante varchar(16) NOT NULL,
	pa1DigVerAportante smallint,
	pa1CodSucursal varchar(10),
	pa1NomSucursal varchar(40),
	pa1ClaseAportante varchar(1) NOT NULL,
	pa1NaturalezaJuridica smallint NOT NULL,
	pa1TipoPersona varchar(1) NOT NULL,
	pa1FormaPresentacion varchar(1) NOT NULL,
	pa1Direccion varchar(40) NOT NULL,
	pa1CodCiudad varchar(3) NOT NULL,
	pa1CodDepartamento varchar(2) NOT NULL,
	pa1CodActividadEconomica smallint NOT NULL,
	pa1Telefono bigint NOT NULL,
	pa1Fax bigint,
	pa1Email varchar(60),
	pa1IdRepresentante varchar(16) NOT NULL,
	pa1DigVerRepresentante smallint,
	pa1TipoIdRepresentante varchar(2) NOT NULL,
	pa1PrimerApellidoRep varchar(20) NOT NULL,
	pa1SegundoApellidoRep varchar(30),
	pa1PrimerNombreRep varchar(20) NOT NULL,
	pa1SegundoNombreRep varchar(30),
	pa1FechaInicioConcordato date,
	pa1TipoAccion smallint,
	pa1FechaFinActividades date,
	pa1CodOperador smallint,
	pa1PeriodoAporte varchar(7) NOT NULL,
	pa1TipoAportante smallint NOT NULL,
	pa1FechaMatricula date,
	pa1CodDepartamentoBene varchar(2),
	pa1AportanteExonerado varchar(1) NOT NULL,
	pa1AcogeBeneficio varchar(1) NOT NULL,
	CONSTRAINT PK_PilaArchivoARegistro1_pa1Id PRIMARY KEY (pa1Id)
);

create table dbo.PilaArchivoIPRegistro1(
	ip1Id bigint NOT NULL IDENTITY,
	ip1IdIndice bigint NOT NULL,
	ip1Secuencia int NOT NULL,
	ip1IdAdministradora varchar(16) NOT NULL,
	ip1DigVerAdministradora smallint NOT NULL,
	ip1CodAdministradora varchar(6) NOT NULL,
	ip1NombrePagador varchar(150) NOT NULL,
	ip1TipoIdPagador varchar(2) NOT NULL,
	ip1IdPagador varchar(16) NOT NULL,
	ip1DigVerPagador smallint,
	ip1PeriodoAporte varchar(7) NOT NULL,
	ip1FechaPago date,
	ip1NumPlanilla varchar(10) NOT NULL,
	ip1FormaPresentacion varchar(1) NOT NULL,
	ip1CodSucursal varchar(10),
	ip1NomSucursal varchar(40),
	ip1ValorTotalMesadas bigint NOT NULL,
	ip1CantPensionados int NOT NULL,
	ip1DiasMora smallint NOT NULL,
	ip1CodOperador smallint NOT NULL,
	ip1CantidadReg2 int NOT NULL,
	CONSTRAINT PK_PilaArchivoIPRegistro1_ip1Id PRIMARY KEY (ip1Id)
);

create table dbo.PilaArchivoIPRegistro2(
	ip2Id bigint NOT NULL IDENTITY,
	ip2IdIndice bigint NOT NULL,
	ip2Secuencia int NOT NULL,
	ip2TipoIdPensionado varchar(2) NOT NULL,
	ip2IdPensionado varchar(16) NOT NULL,
	ip2PrimerApellido varchar(20) NOT NULL,
	ip2SegundoApellido varchar(30),
	ip2PrimerNombre varchar(20) NOT NULL,
	ip2SegundoNombre varchar(30),
	ip2CodDepartamento varchar(2) NOT NULL,
	ip2CodMunicipio varchar(3) NOT NULL,
	ip2Tarifa numeric(5,5) NOT NULL,
	ip2ValorAporte int NOT NULL,
	ip2ValorMesada int NOT NULL,
	ip2DiasCotizados smallint NOT NULL,
	ip2NovING varchar(1),
	ip2NovRET varchar(1),
	ip2NovVSP varchar(1),
	ip2NovSUS varchar(1),
	ip2FechaIngreso date,
	ip2FechaRetiro date,
	ip2FechaInicioVSP date,
	CONSTRAINT PK_PilaArchivoIPRegistro2_ip2Id PRIMARY KEY (ip2Id)
);

create table dbo.PilaArchivoIPRegistro3(
	ip3Id bigint NOT NULL IDENTITY,
	ip3IdIndice bigint NOT NULL,
	ip3ValorTotalAporte bigint NOT NULL,
	ip3DiasMora smallint NOT NULL,
	ip3ValorMora bigint NOT NULL,
	ip3ValorTotalPagar bigint NOT NULL,
	CONSTRAINT PK_PilaArchivoIPRegistro3_ip3Id PRIMARY KEY (ip3Id)
);

create table dbo.PilaArchivoAPRegistro1(
	ap1Id bigint NOT NULL IDENTITY,
	ap1IdIndice bigint NOT NULL,
	ap1NombrePagador varchar(150) NOT NULL,
	ap1TipoIdPagador varchar(2) NOT NULL,
	ap1IdPagador varchar(16) NOT NULL,
	ap1DigVerPagador smallint,
	ap1CodSucursal varchar(10),
	ap1NomSucursal varchar(40),
	ap1ClasePagador varchar(1) NOT NULL,
	ap1NaturalezaJuridica smallint NOT NULL,
	ap1TipoPersona varchar(1) NOT NULL,
	ap1FormaPresentacion varchar(1) NOT NULL,
	ap1Direccion varchar(40) NOT NULL,
	ap1CodCiudad varchar(3) NOT NULL,
	ap1CodDepartamento varchar(2) NOT NULL,
	ap1CodActividadEconomica smallint NOT NULL,
	ap1Telefono bigint NOT NULL,
	ap1Fax bigint NOT NULL,
	ap1Email varchar(60) NOT NULL,
	ap1IdRepresentante varchar(16) NOT NULL,
	ap1DigVerRepresentante smallint,
	ap1TipoIdRepresentante varchar(2) NOT NULL,
	ap1PrimerApellidoRep varchar(20) NOT NULL,
	ap1SegundoApellidoRep varchar(30) NOT NULL,
	ap1PrimerNombreRep varchar(20) NOT NULL,
	ap1SegundoNombreRep varchar(30),
	ap1CodOperador smallint NOT NULL,
	ap1PeriodoAporte varchar(7) NOT NULL,
	ap1TipoPagador smallint NOT NULL,
	CONSTRAINT PK_PilaArchivoAPRegistro1_ap1Id PRIMARY KEY (ap1Id)
);

create table PilaArchivoFRegistro1(
	pf1Id bigint NOT NULL IDENTITY,
	pf1IdIndice bigint NOT NULL,
	pf1FechaRecaudo int NOT NULL,
	pf1CodigoEntidadFinanciera smallint NOT NULL,
	pf1IdAdministradora varchar(15) NOT NULL,
	pf1NombreAdministradora varchar(22) NOT NULL,
	CONSTRAINT PK_PilaArchivoFRegistro1_pf1Id PRIMARY KEY (pf1Id)
);

create table PilaArchivoFRegistro5(
	pf5Id bigint NOT NULL IDENTITY,
	pf5IdIndice bigint NOT NULL,
	pf5NumeroCuenta varchar(17) NOT NULL,
	pf5TipoCuenta smallint NOT NULL,
	pf5NumeroLote smallint NOT NULL,
	pf5SistemaPago varchar(2) NOT NULL,
	CONSTRAINT PK_PilaArchivoFRegistro5_pf5Id PRIMARY KEY (pf5Id)
);

create table PilaArchivoFRegistro6(
	pf6Id bigint NOT NULL IDENTITY,
	pf6IdIndice bigint NOT NULL,
	pf6IdAportante varchar(16) NOT NULL,
	pf6NombreAportante varchar(16) NOT NULL,
	pf6CodBanco varchar(8) NOT NULL,
	pf6NumeroPlanilla varchar(15) NOT NULL,
	pf6PeriodoPago varchar(6) NOT NULL,
	pf6CanalPago varchar(2) NOT NULL,
	pf6CantidadRegistros int NOT NULL,
	pf6CodOperadorInformacion varchar(2) NOT NULL,
	pf6ValorPlanilla bigint NOT NULL,
	pf6HoraMinuto varchar(4) NOT NULL,
	pf6Secuencia int NOT NULL,
	pf6EstadoConciliacion varchar(64),
	CONSTRAINT PK_PilaArchivoFRegistro6_pf6Id PRIMARY KEY (pf6Id)
);

create table PilaArchivoFRegistro8(
	pf8Id bigint NOT NULL IDENTITY,
	pf8IdIndice bigint NOT NULL,
	pf8CantidadPlanillas int NOT NULL,
	pf8CantidadRegistros int NOT NULL,
	pf8ValorRecaudado bigint NOT NULL,
	CONSTRAINT PK_PilaArchivoFRegistro8_pf8Id PRIMARY KEY (pf8Id)
);

create table PilaArchivoFRegistro9(
	pf9Id bigint NOT NULL IDENTITY,
	pf9IdIndice bigint NOT NULL,
	pf9CantidadTotalPlanillas int NOT NULL,
	pf9CantidadTotalRegistros int NOT NULL,
	pf9ValorTotalRecaudo bigint NOT NULL,
	pf9CantidadTotalLotes int NOT NULL,
	CONSTRAINT PK_PilaArchivoFRegistro9_pf9Id PRIMARY KEY (pf9Id)
);

ALTER TABLE [PilaArchivoAPRegistro1] ADD CONSTRAINT [FK_PilaArchivoAPRegistro1_ap1IdIndice] FOREIGN KEY([ap1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoARegistro1] ADD CONSTRAINT [FK_PilaArchivoARegistro1_pa1IdIndice] FOREIGN KEY([pa1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoFRegistro1] ADD CONSTRAINT [FK_PilaArchivoFRegistro1_pf1IdIndice] FOREIGN KEY([pf1IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro5] ADD CONSTRAINT [FK_PilaArchivoFRegistro5_pf5IdIndice] FOREIGN KEY([pf5IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro6] ADD CONSTRAINT [FK_PilaArchivoFRegistro6_pf6IdIndice] FOREIGN KEY([pf6IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro8] ADD CONSTRAINT [FK_PilaArchivoFRegistro8_pf8IdIndice] FOREIGN KEY([pf8IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro9] ADD CONSTRAINT [FK_PilaArchivoFRegistro9_pf9IdIndice] FOREIGN KEY([pf9IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoIPRegistro1] ADD CONSTRAINT [FK_PilaArchivoIPRegistro1_ip1IdIndice] FOREIGN KEY([ip1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIPRegistro2] ADD CONSTRAINT [FK_PilaArchivoIPRegistro2_ip2IdIndice] FOREIGN KEY([ip2IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIPRegistro3] ADD CONSTRAINT [FK_PilaArchivoIPRegistro3_ip3IdIndice] FOREIGN KEY([ip3IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro1] ADD CONSTRAINT [FK_PilaArchivoIRegistro1_pi1IdIndice] FOREIGN KEY([pi1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro2] ADD CONSTRAINT [FK_PilaArchivoIRegistro2_pi2IdIndice] FOREIGN KEY([pi2IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro3] ADD CONSTRAINT [FK_PilaArchivoIRegistro3_pi3IdIndice] FOREIGN KEY([pi3IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);

-- Contenido para la tabla DiasFestivos (actualizado a 2017-03-06)

insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Año Nuevo', '2014-01-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de los Reyes Magos', '2014-01-13');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San José', '2014-03-24');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Ramos', '2014-04-13');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Jueves Santo', '2014-04-17');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Viernes Santo', '2014-04-18');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Pascua o Resurrección', '2014-04-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día del trabajo', '2014-05-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Ascensión', '2014-06-02');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de Corpus Christi', '2014-06-23');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San Pedro y San Pablo', '2014-06-30');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día del Sagrado Corazón de Jesús', '2014-06-30');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día de la independencia', '2014-07-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Batalla de Boyacá', '2014-08-07');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Asunción de la Virgen', '2014-08-18');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Raza', '2014-10-13');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de todos los Santos', '2014-11-03');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Independencia de Cartagena', '2014-11-17');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Inmaculada Concepción', '2014-12-08');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Navidad', '2014-12-25');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Año Nuevo', '2015-01-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de los Reyes Magos', '2015-01-12');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San José', '2015-03-23');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Ramos', '2015-03-29');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Jueves Santo', '2015-04-02');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Viernes Santo', '2015-04-03');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Pascua o Resurrección', '2015-04-05');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día del trabajo', '2015-05-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Ascensión', '2015-05-18');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de Corpus Christi', '2015-06-08');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día del Sagrado Corazón de Jesús', '2015-06-15');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San Pedro y San Pablo', '2015-06-29');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día de la independencia', '2015-07-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Batalla de Boyacá', '2015-08-07');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Asunción de la Virgen', '2015-08-17');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Raza', '2015-10-12');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de todos los Santos', '2015-11-02');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Independencia de Cartagena', '2015-11-16');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Inmaculada Concepción', '2015-12-08');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Navidad', '2015-12-25');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Año Nuevo', '2016-01-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de los Reyes Magos', '2016-01-11');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Ramos', '2016-03-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San José', '2016-03-21');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Jueves Santo', '2016-03-24');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Viernes Santo', '2016-03-25');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Domingo de Pascua o Resurrección', '2016-03-27');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día del trabajo', '2016-05-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Ascensión', '2016-05-09');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de Corpus Christi', '2016-05-30');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día del Sagrado Corazón de Jesús', '2016-06-06');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San Pedro y San Pablo', '2016-07-04');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día de la independencia', '2016-07-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Batalla de Boyacá', '2016-08-07');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Asunción de la Virgen', '2016-08-15');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Raza', '2016-10-17');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de todos los Santos', '2016-11-07');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Independencia de Cartagena', '2016-11-14');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Inmaculada Concepción', '2016-12-08');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Navidad', '2016-12-25');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Año Nuevo', '2017-01-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de los Reyes Magos', '2017-09-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San José', '2017-03-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Jueves Santo', '2017-04-13');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Viernes Santo', '2017-04-14');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día del trabajo', '2017-05-01');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Ascensión', '2017-05-29');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de Corpus Christi', '2017-06-19');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día del Sagrado Corazón de Jesús', '2017-06-26');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de San Pedro y San Pablo', '2017-07-03');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Día de la independencia', '2017-07-20');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Batalla de Boyacá', '2017-08-07');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Asunción de la Virgen', '2017-08-21');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el Día de la Raza', '2017-10-16');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por el día de todos los Santos', '2017-11-06');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Festivo por la Independencia de Cartagena', '2017-11-13');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Inmaculada Concepción', '2017-12-08');
insert into dbo.DiasFestivos (pifConcepto, pifFecha) values ('Navidad', '2017-12-25');

-- Contenido para la tabla PilaClasificacionAportante (actualizado a 2016-11-10)

insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', NULL, NULL, NULL);
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '26', '20', 'MAYOR_IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '26', '20', 'MENOR');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'I', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'A', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'B', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', NULL, NULL, NULL);
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '26', '20', 'MAYOR_IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '26', '20', 'MENOR');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'I', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'A', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'B', 'IGUAL');

-- Contenido para la tabla PilaNormatividadFechaVencimiento (actualizado a 2016-11-10)

insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('0000-00', '1994-12', NULL, '1', 10, 'CALENDARIO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1995-01', '1999-09', '1,2,3,4,5', NULL, 7, 'PRIMER_HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1995-01', '1999-09', '6,7,8,9,0', NULL, 10, 'PRIMER_HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '2', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '2', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '2', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '3', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '4', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '3', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '4', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '3', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '4', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2', '3', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2', '4', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '3,4', '3', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '3,4', '4', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '5,6', '3', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '5,6', '4', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8', '3', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8', '4', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '9,0', '3', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '9,0', '4', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-10', '5', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '11-23', '5', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '24-36', '5', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '37-49', '5', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '50-62', '5', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '63-75', '5', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '76-88', '5', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '88-99', '5', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-08', '6', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '09-16', '6', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '17-24', '6', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '25-32', '6', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '33-40', '6', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '41-48', '6', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '49-56', '6', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '57-64', '6', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '65-72', '6', 9, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '73-79', '6', 10, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '80-86', '6', 11, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '87-93', '6', 12, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '94-99', '6', 13, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-07', '4', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '08-14', '4', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '15-21', '4', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '22-28', '4', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '29-35', '4', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '36-42', '4', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '43-49', '4', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '50-56', '4', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '57-63', '4', 9, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '64-69', '4', 10, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '70-75', '4', 11, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '76-81', '4', 12, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '82-87', '4', 13, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '88-93', '4', 14, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '94-99', '4', 15, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('0000-00', '1994-12', NULL, '7', 10, 'CALENDARIO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1995-01', '1999-09', '1,2,3,4,5', NULL, 7, 'PRIMER_HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1995-01', '1999-09', '6,7,8,9,0', NULL, 10, 'PRIMER_HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '8', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '8', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '8', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '9', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2,3', '10', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '9', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '4,5,6', '10', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '9', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8,9,0', '10', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2', '9', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '1,2', '10', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '3,4', '9', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '3,4', '10', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '5,6', '9', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '5,6', '10', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8', '9', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '7,8', '10', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '9,0', '9', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('1999-10', '2007-05', '9,0', '10', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-10', '11', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '11-23', '11', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '24-36', '11', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '37-49', '11', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '50-62', '11', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '63-75', '11', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '76-88', '11', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '88-99', '11', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-08', '12', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '09-16', '12', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '17-24', '12', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '25-32', '12', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '33-40', '12', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '41-48', '12', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '49-56', '12', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '57-64', '12', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '65-72', '12', 9, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '73-79', '12', 10, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '80-86', '12', 11, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '87-93', '12', 12, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '94-99', '12', 13, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '00-07', '10', 1, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '08-14', '10', 2, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '15-21', '10', 3, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '22-28', '10', 4, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '29-35', '10', 5, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '36-42', '10', 6, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '43-49', '10', 7, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '50-56', '10', 8, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '57-63', '10', 9, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '64-69', '10', 10, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '70-75', '10', 11, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '76-81', '10', 12, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '82-87', '10', 13, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '88-93', '10', 14, 'HABIL');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacion, pfvDiaVencimiento, pfvTipoFecha) values ('2007-06', NULL, '94-99', '10', 15, 'HABIL');

-- Contenido para la tabla PilaOportunidadPresentacionPlanilla (actualizado a 2016-11-10)

insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('E', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('Y', 'MES_ACTUAL', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('A', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('I', 'MES_ACTUAL', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('S', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('M', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('M', 'MES_ACTUAL', '03');
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('N', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('N', 'MES_ACTUAL', '03');
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('H', 'MES_ACTUAL', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('T', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('F', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('J', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('X', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('U', 'MES_VENCIDO', NULL);
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('U', 'MES_ACTUAL', '03');
insert into PilaOportunidadPresentacionPlanilla (popTipoPlanilla, popOportunidad, popTipoCotizanteEspecifico) values ('K', 'MES_ACTUAL', NULL);

-- Contenido para la tabla PilaTasasInteresMora (actualizado a 2016-11-10)

insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('1900-01-01', '2006-07-28', 1, 0.2063, 'Circular 69 de 2006', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2006-07-29', '2006-07-31', 2, 0.2262, 'Resolución 1103', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2006-08-01', '2006-08-31', 3, 0.2253, 'Resolución 1305', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2006-09-01', '2006-09-30', 4, 0.2258, 'Resolución 1468', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2006-10-01', '2006-12-31', 5, 0.2261, 'Resolución 1715', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2007-01-01', '2007-01-04', 6, 0.3102, 'Resolución 2441', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2007-01-05', '2007-03-31', 7, 0.2075, 'Resolución 0008', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2007-04-01', '2007-06-30', 8, 0.2512, 'Resolución 0428', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2007-07-01', '2007-09-30', 9, 0.2851, 'Resolución 1086', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2007-10-01', '2007-12-31', 10, 0.3189, 'Resolución 1742', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2008-01-01', '2008-03-31', 11, 0.3275, 'Resolución 2366', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2008-04-01', '2008-06-30', 12, 0.3288, 'Resolución 0474', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2008-07-01', '2008-09-30', 13, 0.3227, 'Resolución 1011', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2008-10-01', '2008-12-31', 14, 0.3153, 'Resolución 1555', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2009-01-01', '2009-03-31', 15, 0.3071, 'Resolución 2163', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2009-04-01', '2009-06-30', 16, 0.3042, 'Resolución 0388', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2009-07-01', '2009-09-30', 17, 0.2798, 'Resolución 0937', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2009-10-01', '2009-12-31', 18, 0.2592, 'Resolución 1486', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2010-01-01', '2010-03-31', 19, 0.2421, 'Resolución 2039', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2010-04-01', '2010-06-30', 20, 0.2297, 'Resolución 0699', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2010-07-01', '2010-09-30', 21, 0.2241, 'Resolución 1311', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2010-10-01', '2010-12-31', 22, 0.2132, 'Resolución 1920', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2011-01-01', '2011-03-31', 23, 0.2342, 'Resolución 2476', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2011-04-01', '2011-06-30', 24, 0.2654, 'Resolución 0487', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2011-07-01', '2011-09-30', 25, 0.2795, 'Resolución 1047', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2011-10-01', '2011-12-31', 26, 0.2909, 'Resolución 1684', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2012-01-01', '2012-03-31', 27, 0.2988, 'Resolución 2336', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2012-04-01', '2012-06-30', 28, 0.3078, 'Resolución0465', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2012-07-01', '2012-09-30', 29, 0.3129, 'Resolución0984', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2012-10-01', '2012-12-31', 30, 0.3134, 'Resolución 1528', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2013-01-01', '2013-03-31', 31, 0.3113, 'Resolución 2200', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2013-04-01', '2013-06-30', 32, 0.3125, 'Resolución 0605 ', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2013-07-01', '2013-09-30', 33, 0.3051, 'Resolución 1192', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2013-10-01', '2013-12-31', 34, 0.2978, 'Resolución 1779', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2014-01-01', '2014-03-31', 35, 0.2948, 'Resolución 2372', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2014-04-01', '2014-06-30', 36, 0.2945, 'Resolución 0503', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2014-07-01', '2014-09-30', 37, 0.29, 'Resolución 1041', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2014-10-01', '2014-12-31', 38, 0.2876, 'Resolución 1707', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2015-01-01', '2015-03-31', 39, 0.2882, 'Resolución 2359', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2015-04-01', '2015-06-30', 40, 0.2906, 'Resolución 0369', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2015-07-01', '2015-09-30', 41, 0.2889, 'Resolución 0913', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2015-10-01', '2015-12-31', 42, 0.29, 'Resolución 1341', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2016-01-01', '2016-03-31', 43, 0.2952, 'Resolución 1788', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2016-04-01', '2016-06-30', 44, 0.3081, 'Resolución 0334', 'SIMPLE');
insert into PilaTasasInteresMora (ptiFechaInicioTasa, ptiFechaFinTasa, ptiNumeroPeriodoTasa, ptiPorcentajeTasa, ptiNormativa, ptiTipoInteres) values ('2016-07-01', '2016-09-30', 45, 0.3201, 'Resolución 0811', 'SIMPLE');

insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('01', 'Banco de Bogota', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('02', 'Banco Popular', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('06', 'Banco Corpbanca', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('07', 'Banco de Colombia', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('09', 'CitiBank', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('13', 'Banco BBVA Colombia', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('19', 'Banco de Colpatria', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('23', 'Banco de Occidente', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('32', 'Banco BCSC', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('51', 'Davivienda', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('61', 'Coomeva Financiera', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('83', 'Compensar', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('84', 'Gestión y Contacto', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('86', 'Asopagos', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('87', 'Fedecajas', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('88', 'Simple', 1);
insert into OperadorInformacion (oinCodigo, oinNombre, oinOperadorActivo) values ('89', 'Enlace Operativo', 1);
