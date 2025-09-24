--liquibase formatted sql

--changeset  abaquero:01 
--comment: Actualización PILA

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
	pipEstadoArchivo varchar(75),
	pipTipoCargaArchivo varchar(10),
	pipUsuario varchar(255),
	pipIdentificadorDocumento varchar(255),
	pipVersionDocumento smallint,
	pipFechaProceso datetime,
	pipUsuarioProceso varchar(255),
	pipFechaEliminacion datetime,
	pipUsuarioEliminacion varchar(255),
	pipProcesar bit,
	pipRegistroActivo bit,
	CONSTRAINT PK_PilaIndicePlanilla_pipId PRIMARY KEY (pipId)
);

create table dbo.PilaIndicePlanillaOF(
	pioId bigint NOT NULL IDENTITY, 
	pioFechaPago datetime,
	pioNombreArchivo varchar(80),
	pioFechaRecibo datetime,
	pioCodigoAdministradora varchar(6),
	pioCodigoBancoRecaudador varchar(3),
	pioTipoArchivo varchar(20),
	pioTipoCargaArchivo varchar(10),
	pioUsuario varchar(255),
	pioEstadoArchivo varchar(75),
	pioIdentificadorDocumento varchar(255),
	pioVersionDocumento smallint,
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
	ppvBloque numeric(1,0) NOT NULL,
	ppvNombreVariable varchar(75) NOT NULL,
	ppvValorVariable varchar(200),
	CONSTRAINT PK_PilaPasoValores_ppvId PRIMARY KEY (ppvId)
);

create table dbo.PilaDiasFestivos(
	pdfId bigint NOT NULL IDENTITY,
	pdfAnio smallint,
	pdfMes smallint,
	pdfDia smallint,
	pdfConcepto varchar(100),
	pdfFecha date,
	CONSTRAINT PK_PilaDiasFestivos_pdfId PRIMARY KEY (pdfId)
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

CREATE TABLE dbo.PilaOperadorInformacion(
	oinId bigint NOT NULL IDENTITY,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinUrl varchar(255),
	oinPuerto smallint,
	oinHost varchar(75),
	oinUsuario varchar(255),
	oinContrasena varchar(32),
	oinOperadorActivo bit NOT NULL,
	CONSTRAINT PK_PilaOperadorInformacion_oinId PRIMARY KEY (oinId)
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

ALTER TABLE [PilaEstadoBloque] ADD CONSTRAINT [FK_PilaEstadoBloque_PilaIndicePlanilla_id] FOREIGN KEY([pebIdPlanilla]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaEstadoBloqueOF] ADD CONSTRAINT [FK_PilaEstadoBloqueOF_PilaIndicePlanillaOF_id] FOREIGN KEY([peoIdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaNormatividadFechaVencimiento] ADD CONSTRAINT [FK_PilaNormatividadFechaVencimiento_PilaClasificacionAportante_id] FOREIGN KEY([pfvClasificacion]) REFERENCES [PilaClasificacionAportante] ([pcaId]);
ALTER TABLE [PilaIndiceCorreccionPlanilla] ADD CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_PilaIndicePlanilla_id_Afectada] FOREIGN KEY([picIdIndicePlanillaAfectada]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaIndiceCorreccionPlanilla] ADD CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_PilaIndicePlanilla_id_Cargado] FOREIGN KEY([picIdIndicePlanillaCargado]) REFERENCES [PilaIndicePlanilla] ([pipId]);

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
	pi2Tarifa int NOT NULL,
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
	ip2Tarifa int NOT NULL,
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

ALTER TABLE [PilaArchivoAPRegistro1] ADD CONSTRAINT [FK_PilaArchivoAPRegistro1_PilaIndicePlanilla_id] FOREIGN KEY([ap1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoARegistro1] ADD CONSTRAINT [FK_PilaArchivoARegistro1_PilaIndicePlanilla_id] FOREIGN KEY([pa1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoFRegistro1] ADD CONSTRAINT [FK_PilaArchivoFRegistro1_PilaIndicePlanillaOF_id] FOREIGN KEY([pf1IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro5] ADD CONSTRAINT [FK_PilaArchivoFRegistro5_PilaIndicePlanillaOF_id] FOREIGN KEY([pf5IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro6] ADD CONSTRAINT [FK_PilaArchivoFRegistro6_PilaIndicePlanillaOF_id] FOREIGN KEY([pf6IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro8] ADD CONSTRAINT [FK_PilaArchivoFRegistro8_PilaIndicePlanillaOF_id] FOREIGN KEY([pf8IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoFRegistro9] ADD CONSTRAINT [FK_PilaArchivoFRegistro9_PilaIndicePlanillaOF_id] FOREIGN KEY([pf9IdIndice]) REFERENCES [PilaIndicePlanillaOF] ([pioId]);
ALTER TABLE [PilaArchivoIPRegistro1] ADD CONSTRAINT [FK_PilaArchivoIPRegistro1_PilaIndicePlanilla_id] FOREIGN KEY([ip1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIPRegistro2] ADD CONSTRAINT [FK_PilaArchivoIPRegistro2_PilaIndicePlanilla_id] FOREIGN KEY([ip2IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIPRegistro3] ADD CONSTRAINT [FK_PilaArchivoIPRegistro3_PilaIndicePlanilla_id] FOREIGN KEY([ip3IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro1] ADD CONSTRAINT [FK_PilaArchivoIRegistro1_PilaIndicePlanilla_id] FOREIGN KEY([pi1IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro2] ADD CONSTRAINT [FK_PilaArchivoIRegistro2_PilaIndicePlanilla_id] FOREIGN KEY([pi2IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);
ALTER TABLE [PilaArchivoIRegistro3] ADD CONSTRAINT [FK_PilaArchivoIRegistro3_PilaIndicePlanilla_id] FOREIGN KEY([pi3IdIndice]) REFERENCES [PilaIndicePlanilla] ([pipId]);


--changeset  jocampo:02 
--comment: Adicionar la ubicación a los representantes legales
ALTER TABLE Empresa ADD empUbicacionRepresentanteLegal BIGINT;
ALTER TABLE Empresa ADD empUbicacionRepresentanteLegalSuplente BIGINT;

ALTER TABLE Empresa ADD CONSTRAINT FK_Empresa_empUbicacionRepresentanteLegal FOREIGN KEY (empUbicacionRepresentanteLegal) REFERENCES Ubicacion;
ALTER TABLE Empresa ADD CONSTRAINT FK_Empresa_empUbicacionRepresentanteLegalSuplente FOREIGN KEY (empUbicacionRepresentanteLegalSuplente) REFERENCES Ubicacion;
