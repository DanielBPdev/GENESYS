--liquibase formatted sql

--changeset heinsohn:01
--comment:Creacioon modelo Pila 
--Creacion de la tabla dbo.CajaCompensacion    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.CajaCompensacion(
	ccfId int IDENTITY(1,1) NOT NULL,
	ccfHabilitado bit NULL,
	ccfMetodoGeneracionEtiquetas varchar(20) NULL,
	ccfNombre varchar(100) NULL,
	ccfSocioAsopagos bit NULL,
	ccfDepartamento smallint NULL,
	ccfCodigo varchar(5) NULL,
	ccfCodigoRedeban int NULL,
 CONSTRAINT PK_CajaCompensacion_ccfId PRIMARY KEY CLUSTERED 
(
	ccfId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.ConexionOperadorInformacion    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.ConexionOperadorInformacion(
	coiId bigint IDENTITY(1,1) NOT NULL,
	coiOperadorInformacionCcf bigint NULL,
	coiProtocolo varchar(10) NULL,
	coiUrl varchar(255) NULL,
	coiPuerto smallint NULL,
	coiHost varchar(75) NULL,
	coiUsuario varchar(255) NULL,
	coiContrasena varchar(32) NULL,
 CONSTRAINT PK_PilaConexionOperadorInformacion_coiId PRIMARY KEY CLUSTERED 
(
	coiId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creacion de la tabla dbo.OperadorInformacion    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.OperadorInformacion(
	oinId bigint IDENTITY(1,1) NOT NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
 CONSTRAINT PK_PilaOperadorInformacion_oinId PRIMARY KEY CLUSTERED 
(
	oinId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.OperadorInformacionCcf    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.OperadorInformacionCcf(
	oicId bigint IDENTITY(1,1) NOT NULL,
	oicOperadorInformacion bigint NULL,
	oicCajaCompensacion int NULL,
	oicEstado bit NULL,
 CONSTRAINT PK_PilaOperadorInformacionCcf_oicId PRIMARY KEY CLUSTERED 
(
	oicId ASC
)
) ON [PRIMARY]

GO


--Creacion de la tabla dbo.PilaArchivoAPRegistro1    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoAPRegistro1(
	ap1Id bigint IDENTITY(1,1) NOT NULL,
	ap1IndicePlanilla bigint NOT NULL,
	ap1NombrePagador varchar(150) NOT NULL,
	ap1TipoIdPagador varchar(2) NOT NULL,
	ap1IdPagador varchar(16) NOT NULL,
	ap1DigVerPagador smallint NULL,
	ap1CodSucursal varchar(10) NULL,
	ap1NomSucursal varchar(40) NULL,
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
	ap1DigVerRepresentante smallint NULL,
	ap1TipoIdRepresentante varchar(2) NOT NULL,
	ap1PrimerApellidoRep varchar(20) NOT NULL,
	ap1SegundoApellidoRep varchar(30) NOT NULL,
	ap1PrimerNombreRep varchar(20) NOT NULL,
	ap1SegundoNombreRep varchar(30) NULL,
	ap1CodOperador smallint NOT NULL,
	ap1PeriodoAporte varchar(7) NOT NULL,
	ap1TipoPagador smallint NOT NULL,
 CONSTRAINT PK_PilaArchivoAPRegistro1_ap1Id PRIMARY KEY CLUSTERED 
(
	ap1Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoARegistro1    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoARegistro1(
	pa1Id bigint IDENTITY(1,1) NOT NULL,
	pa1IndicePlanilla bigint NOT NULL,
	pa1NombreAportante varchar(200) NOT NULL,
	pa1TipoIdAportante varchar(2) NOT NULL,
	pa1IdAportante varchar(16) NOT NULL,
	pa1DigVerAportante smallint NULL,
	pa1CodSucursal varchar(10) NULL,
	pa1NomSucursal varchar(40) NULL,
	pa1ClaseAportante varchar(1) NOT NULL,
	pa1NaturalezaJuridica smallint NOT NULL,
	pa1TipoPersona varchar(1) NOT NULL,
	pa1FormaPresentacion varchar(1) NOT NULL,
	pa1Direccion varchar(40) NOT NULL,
	pa1CodCiudad varchar(3) NOT NULL,
	pa1CodDepartamento varchar(2) NOT NULL,
	pa1CodActividadEconomica smallint NOT NULL,
	pa1Telefono bigint NOT NULL,
	pa1Fax bigint NULL,
	pa1Email varchar(60) NULL,
	pa1IdRepresentante varchar(16) NOT NULL,
	pa1DigVerRepresentante smallint NULL,
	pa1TipoIdRepresentante varchar(2) NOT NULL,
	pa1PrimerApellidoRep varchar(20) NOT NULL,
	pa1SegundoApellidoRep varchar(30) NULL,
	pa1PrimerNombreRep varchar(20) NOT NULL,
	pa1SegundoNombreRep varchar(30) NULL,
	pa1FechaInicioConcordato date NULL,
	pa1TipoAccion smallint NULL,
	pa1FechaFinActividades date NULL,
	pa1CodOperador smallint NULL,
	pa1PeriodoAporte varchar(7) NOT NULL,
	pa1TipoAportante smallint NOT NULL,
	pa1FechaMatricula date NULL,
	pa1CodDepartamentoBene varchar(2) NULL,
	pa1AportanteExonerado varchar(1) NOT NULL,
	pa1AcogeBeneficio varchar(1) NOT NULL,
 CONSTRAINT PK_PilaArchivoARegistro1_pa1Id PRIMARY KEY CLUSTERED 
(
	pa1Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoFRegistro1    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoFRegistro1(
	pf1Id bigint IDENTITY(1,1) NOT NULL,
	pf1IndicePlanillaOF bigint NOT NULL,
	pf1FechaRecaudo int NOT NULL,
	pf1CodigoEntidadFinanciera smallint NOT NULL,
	pf1IdAdministradora varchar(15) NOT NULL,
	pf1NombreAdministradora varchar(22) NOT NULL,
 CONSTRAINT PK_PilaArchivoFRegistro1_pf1Id PRIMARY KEY CLUSTERED 
(
	pf1Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoFRegistro5    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoFRegistro5(
	pf5Id bigint IDENTITY(1,1) NOT NULL,
	pf5IndicePlanillaOF bigint NOT NULL,
	pf5NumeroCuenta varchar(17) NOT NULL,
	pf5TipoCuenta smallint NOT NULL,
	pf5NumeroLote smallint NOT NULL,
	pf5SistemaPago varchar(2) NOT NULL,
 CONSTRAINT PK_PilaArchivoFRegistro5_pf5Id PRIMARY KEY CLUSTERED 
(
	pf5Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoFRegistro6    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoFRegistro6(
	pf6Id bigint IDENTITY(1,1) NOT NULL,
	pf6IndicePlanillaOF bigint NOT NULL,
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
	pf6EstadoConciliacion varchar(64) NULL,
	pf6PilaArchivoFRegistro5 bigint NOT NULL, 
 CONSTRAINT PK_PilaArchivoFRegistro6_pf6Id PRIMARY KEY CLUSTERED 
(
	pf6Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoFRegistro8    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.PilaArchivoFRegistro8(
	pf8Id bigint IDENTITY(1,1) NOT NULL,
	pf8IndicePlanillaOF bigint NOT NULL,
	pf8CantidadPlanillas int NOT NULL,
	pf8CantidadRegistros int NOT NULL,
	pf8ValorRecaudado bigint NOT NULL,
	pf8PilaArchivoFRegistro5 bigint NOT NULL,
 CONSTRAINT PK_PilaArchivoFRegistro8_pf8Id PRIMARY KEY CLUSTERED 
(
	pf8Id ASC
)
) ON [PRIMARY]

GO


--Creacion de la tabla dbo.PilaArchivoFRegistro9    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.PilaArchivoFRegistro9(
	pf9Id bigint IDENTITY(1,1) NOT NULL,
	pf9IndicePlanillaOF bigint NOT NULL,
	pf9CantidadTotalPlanillas int NOT NULL,
	pf9CantidadTotalRegistros int NOT NULL,
	pf9ValorTotalRecaudo bigint NOT NULL,
	pf9CantidadTotalLotes int NOT NULL,
 CONSTRAINT PK_PilaArchivoFRegistro9_pf9Id PRIMARY KEY CLUSTERED 
(
	pf9Id ASC
)
) ON [PRIMARY]

GO


--Creacion de la tabla dbo.PilaArchivoIPRegistro1    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoIPRegistro1(
	ip1Id bigint IDENTITY(1,1) NOT NULL,
	ip1IndicePlanilla bigint NOT NULL,
	ip1Secuencia int NOT NULL,
	ip1IdAdministradora varchar(16) NOT NULL,
	ip1DigVerAdministradora smallint NOT NULL,
	ip1CodAdministradora varchar(6) NOT NULL,
	ip1NombrePagador varchar(150) NOT NULL,
	ip1TipoIdPagador varchar(2) NOT NULL,
	ip1IdPagador varchar(16) NOT NULL,
	ip1DigVerPagador smallint NULL,
	ip1PeriodoAporte varchar(7) NOT NULL,
	ip1FechaPago date NULL,
	ip1NumPlanilla varchar(10) NOT NULL,
	ip1FormaPresentacion varchar(1) NOT NULL,
	ip1CodSucursal varchar(10) NULL,
	ip1NomSucursal varchar(40) NULL,
	ip1ValorTotalMesadas bigint NOT NULL,
	ip1CantPensionados int NOT NULL,
	ip1DiasMora smallint NOT NULL,
	ip1CodOperador smallint NOT NULL,
	ip1CantidadReg2 int NOT NULL,
	ip1FechaActualizacion date NULL,
 CONSTRAINT PK_PilaArchivoIPRegistro1_ip1Id PRIMARY KEY CLUSTERED 
(
	ip1Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoIPRegistro2    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoIPRegistro2(
	ip2Id bigint IDENTITY(1,1) NOT NULL,
	ip2IndicePlanilla bigint NOT NULL,
	ip2Secuencia int NOT NULL,
	ip2TipoIdPensionado varchar(2) NOT NULL,
	ip2IdPensionado varchar(16) NOT NULL,
	ip2PrimerApellido varchar(20) NOT NULL,
	ip2SegundoApellido varchar(30) NULL,
	ip2PrimerNombre varchar(20) NOT NULL,
	ip2SegundoNombre varchar(30) NULL,
	ip2CodDepartamento varchar(2) NOT NULL,
	ip2CodMunicipio varchar(3) NOT NULL,
	ip2Tarifa numeric(5, 5) NOT NULL,
	ip2ValorAporte int NOT NULL,
	ip2ValorMesada int NOT NULL,
	ip2DiasCotizados smallint NOT NULL,
	ip2NovING varchar(1) NULL,
	ip2NovRET varchar(1) NULL,
	ip2NovVSP varchar(1) NULL,
	ip2NovSUS varchar(1) NULL,
	ip2FechaIngreso date NULL,
	ip2FechaRetiro date NULL,
	ip2FechaInicioVSP date NULL,
	ip2FechaInicioSuspension date NULL,
	ip2FechaFinSuspension date NULL,
 CONSTRAINT PK_PilaArchivoIPRegistro2_ip2Id PRIMARY KEY CLUSTERED 
(
	ip2Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoIPRegistro3    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.PilaArchivoIPRegistro3(
	ip3Id bigint IDENTITY(1,1) NOT NULL,
	ip3IndicePlanilla bigint NOT NULL,
	ip3ValorTotalAporte bigint NOT NULL,
	ip3DiasMora smallint NOT NULL,
	ip3ValorMora bigint NOT NULL,
	ip3ValorTotalPagar bigint NOT NULL,
 CONSTRAINT PK_PilaArchivoIPRegistro3_ip3Id PRIMARY KEY CLUSTERED 
(
	ip3Id ASC
)
) ON [PRIMARY]

GO


--Creacion de la tabla dbo.PilaArchivoIRegistro1    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoIRegistro1(
	pi1Id bigint IDENTITY(1,1) NOT NULL,
	pi1IndicePlanilla bigint NOT NULL,
	pi1IdCCF varchar(16) NOT NULL,
	pi1DigVerCCF smallint NOT NULL,
	pi1RazonSocial varchar(200) NOT NULL,
	pi1TipoDocAportante varchar(2) NOT NULL,
	pi1IdAportante varchar(16) NOT NULL,
	pi1DigVerAportante smallint NULL,
	pi1TipoAportante varchar(2) NOT NULL,
	pi1Direccion varchar(40) NOT NULL,
	pi1CodCiudad varchar(3) NOT NULL,
	pi1CodDepartamento varchar(2) NOT NULL,
	pi1Telefono bigint NOT NULL,
	pi1Fax bigint NOT NULL,
	pi1Email varchar(60) NOT NULL,
	pi1PeriodoAporte varchar(7) NOT NULL,
	pi1TipoPlanilla varchar(1) NOT NULL,
	pi1FechaPagoAsociado date NULL,
	pi1FechaPago date NOT NULL,
	pi1NumPlanillaAsociada varchar(10) NULL,
	pi1NumPlanilla varchar(10) NOT NULL,
	pi1Presentacion varchar(1) NOT NULL,
	pi1CodSucursal varchar(10) NULL,
	pi1NomSucursal varchar(40) NULL,
	pi1CantidadEmpleados int NOT NULL,
	pi1CantidadAfiliados int NOT NULL,
	pi1CodOperador smallint NOT NULL,
	pi1ModalidadPlanilla smallint NOT NULL,
	pi1DiasMora smallint NOT NULL,
	pi1CantidadReg2 int NOT NULL,
	pi1FechaMatricula date NULL,
	pi1CodDepartamentoBeneficio varchar(2) NULL,
	pi1AcogeBeneficio varchar(1) NOT NULL,
	pi1ClaseAportante varchar(1) NOT NULL,
	pi1NaturalezaJuridica smallint NOT NULL,
	pi1TipoPersona varchar(1) NOT NULL,
	pi1FechaActualizacion date,
	 CONSTRAINT PK_PilaArchivoIRegistro1_pi1Id PRIMARY KEY CLUSTERED 
(
	pi1Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaArchivoIRegistro2    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaArchivoIRegistro2(
	pi2Id bigint IDENTITY(1,1) NOT NULL,
	pi2IndicePlanilla bigint NOT NULL,
	pi2Secuencia int NOT NULL,
	pi2TipoIdCotizante varchar(2) NOT NULL,
	pi2IdCotizante varchar(16) NOT NULL,
	pi2TipoCotizante smallint NOT NULL,
	pi2SubTipoCotizante smallint NOT NULL,
	pi2ExtrangeroNoObligado varchar(1) NULL,
	pi2ColombianoExterior varchar(1) NULL,
	pi2CodDepartamento varchar(2) NULL,
	pi2CodMunicipio varchar(3) NULL,
	pi2PrimerApellido varchar(20) NOT NULL,
	pi2SegundoApellido varchar(30) NULL,
	pi2PrimerNombre varchar(20) NOT NULL,
	pi2SegundoNombre varchar(30) NULL,
	pi2NovIngreso varchar(1) NULL,
	pi2NovRetiro varchar(1) NULL,
	pi2NovVSP varchar(1) NULL,
	pi2NovVST varchar(1) NULL,
	pi2NovSLN varchar(1) NULL,
	pi2NovIGE varchar(1) NULL,
	pi2NovLMA varchar(1) NULL,
	pi2NovVACLR varchar(1) NULL,
	pi2DiasIRL varchar(2) NULL,
	pi2DiasCotizados smallint NOT NULL,
	pi2SalarioBasico int NOT NULL,
	pi2ValorIBC int NOT NULL,
	pi2Tarifa numeric(5, 5) NOT NULL,
	pi2AporteObligatorio int NOT NULL,
	pi2Correcciones varchar(1) NULL,
	pi2SalarioIntegral varchar(1) NULL,
	pi2FechaIngreso date NULL,
	pi2FechaRetiro date NULL,
	pi2FechaInicioVSP date NULL,
	pi2FechaInicioSLN date NULL,
	pi2FechaFinSLN date NULL,
	pi2FechaInicioIGE date NULL,
	pi2FechaFinIGE date NULL,
	pi2FechaInicioLMA date NULL,
	pi2FechaFinLMA date NULL,
	pi2FechaInicioVACLR date NULL,
	pi2FechaFinVACLR date NULL,
	pi2FechaInicioVCT date NULL,
	pi2FechaFinVCT date NULL,
	pi2FechaInicioIRL date NULL,
	pi2FechaFinIRL date NULL,
	pi2HorasLaboradas smallint NULL,
 CONSTRAINT PK_PilaArchivoIRegistro2_pi2Id PRIMARY KEY CLUSTERED 
(
	pi2Id ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creacion de la tabla dbo.PilaArchivoIRegistro3    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.PilaArchivoIRegistro3(
	pi3Id bigint IDENTITY(1,1) NOT NULL,
	pi3IndicePlanilla bigint NOT NULL,
	pi3ValorTotalIBC bigint NULL,
	pi3ValorTotalAporteObligatorio bigint NULL,
	pi3DiasMora smallint NULL,
	pi3ValorMora bigint NULL,
	pi3ValorTotalAportes bigint NULL,
 CONSTRAINT PK_PilaArchivoIRegistro3_pi3Id PRIMARY KEY CLUSTERED 
(
	pi3Id ASC
)
) ON [PRIMARY]

GO


--Creacion de la tabla dbo.PilaClasificacionAportante    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaClasificacionAportante(
	pcaId bigint IDENTITY(1,1) NOT NULL,
	pcaTipoArchivo varchar(20) NULL,
	pcaCampo smallint NULL,
	pcaValor varchar(30) NULL,
	pcaComparacion varchar(20) NULL,
 CONSTRAINT PK_PilaClasificacionAportante_pcaId PRIMARY KEY CLUSTERED 
(
	pcaId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaEjecucionProgramada    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaEjecucionProgramada(
	pepId bigint IDENTITY(1,1) NOT NULL,
	pepFechaDefinicion datetime NULL,
	pepUsuario varchar(255) NULL,
	pepFrecuencia varchar(50) NULL,
	pepHoraInicio varchar(5) NULL,
	pepFechaInicioVigencia datetime NULL,
	pepFechaFinVigencia datetime NULL,
	pepCajaCompensacion int NULL,
 CONSTRAINT PK_PilaEjecucionProgramada_pepId PRIMARY KEY CLUSTERED 
(
	pepId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaErrorValidacionLog    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaErrorValidacionLog(
	pevId bigint IDENTITY(1,1) NOT NULL,
	pevTipoArchivo varchar(20) NULL,
	pevTipoError varchar(20) NULL,
	pevNumeroLinea smallint NULL,
	pevBloqueValidacion varchar(11) NULL,
	pevNombreCampo varchar(150) NULL,
	pevPosicionInicial smallint NULL,
	pevPosicionFinal smallint NULL,
	pevValorCampo varchar(200) NULL,
	pevCodigoError varchar(10) NULL,
	pevMensajeError varchar(255) NULL,
	pevEstadoInconsistencia varchar(30) NULL,
	pevIndicePlanilla bigint NULL,
	pevIndicePlanillaOF bigint NULL,
 CONSTRAINT PK_PilaErrorValidacionLog_pevId PRIMARY KEY CLUSTERED 
(
	pevId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaEstadoBloque    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaEstadoBloque(
	pebId bigint IDENTITY(1,1) NOT NULL,
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
 CONSTRAINT PK_PilaEstadoBloque_pebId PRIMARY KEY CLUSTERED 
(
	pebId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaEstadoBloqueOF    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaEstadoBloqueOF(
	peoId bigint IDENTITY(1,1) NOT NULL,
	peoIndicePlanillaOF bigint NOT NULL,
	peoEstadoBloque0 varchar(75) NULL,
	peoAccionBloque0 varchar(75) NULL,
	peoEstadoBloque1 varchar(75) NULL,
	peoAccionBloque1 varchar(75) NULL,
	peoIdLogLecturaBloque1 bigint NULL,
	peoEstadoBloque6 varchar(75) NULL,
	peoAccionBloque6 varchar(75) NULL,
 CONSTRAINT PK_PilaEstadoBloqueOF_peoId PRIMARY KEY CLUSTERED 
(
	peoId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaIndiceCorreccionPlanilla    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaIndiceCorreccionPlanilla(
	picId bigint IDENTITY(1,1) NOT NULL,
	picAccion varchar(75) NULL,
	picEstadoArchivoAfectado varchar(75) NULL,
	picFechaSolicitud date NOT NULL,
	picUsuario varchar(25) NOT NULL,
	picNumeroIdentificacion bigint NOT NULL,
	picArchivosCorreccion varchar(225) NULL,
	picUsuarioAprobador varchar(25) NULL,
	picFechaRespuesta date NULL,
	picRazonRechazo varchar(25) NULL,
	picIdPlanillaInformacion bigint NOT NULL,
	picIdPlanillaFinanciera bigint NOT NULL,
	picPilaIndicePlanilla bigint NOT NULL,
	picComentarios varchar(500) NULL,
 CONSTRAINT PK_PilaIndiceCorreccionPlanilla_picId PRIMARY KEY CLUSTERED 
(
	picId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaIndicePlanilla    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaIndicePlanilla(
	pipId bigint IDENTITY(1,1) NOT NULL,
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
	pipVersionDocumento varchar(10) NULL,
	pipFechaProceso datetime NULL,
	pipUsuarioProceso varchar(255) NULL,
	pipFechaEliminacion datetime NULL,
	pipUsuarioEliminacion varchar(255) NULL,
	pipProcesar bit NULL,
	pipRegistroActivo bit NULL,
	pipEnLista bit NULL,
	pipTamanoArchivo bigint NULL,
 CONSTRAINT PK_PilaIndicePlanilla_pipId PRIMARY KEY CLUSTERED 
(
	pipId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaIndicePlanillaOF    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaIndicePlanillaOF(
	pioId bigint IDENTITY(1,1) NOT NULL,
	pioFechaPago datetime NULL,
	pioNombreArchivo varchar(80) NULL,
	pioFechaRecibo datetime NULL,
	pioFechaFtp datetime NULL,
	pioCodigoAdministradora varchar(6) NULL,
	pioCodigoBancoRecaudador varchar(3) NULL,
	pioTipoArchivo varchar(20) NULL,
	pioTipoCargaArchivo varchar(10) NULL,
	pioUsuario varchar(255) NULL,
	pioEstadoArchivo varchar(75) NULL,
	pioIdentificadorDocumento varchar(255) NULL,
	pioVersionDocumento varchar(10) NULL,
	pioFechaProceso datetime NULL,
	pioUsuarioProceso varchar(255) NULL,
	pioFechaEliminacion datetime NULL,
	pioUsuarioEliminacion varchar(255) NULL,
	pioRegistroActivo bit NULL,
	pioTamanoArchivo bigint NULL,
 CONSTRAINT PK_PilaIndicePlanillaOF_pioId PRIMARY KEY CLUSTERED 
(
	pioId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaNormatividadFechaVencimiento    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON


GO
CREATE TABLE dbo.PilaNormatividadFechaVencimiento(
	pfvId bigint IDENTITY(1,1) NOT NULL,
	pfvPeriodoInicial varchar(7) NULL,
	pfvPeriodoFinal varchar(7) NULL,
	pfvUltimoDigitoId varchar(50) NULL,
	pfvClasificacionAportante bigint NULL,
	pfvDiaVencimiento smallint NULL,
	pfvTipoFecha varchar(20) NULL,

 CONSTRAINT PK_PilaNormatividadFechaVencimiento_pfvId PRIMARY KEY CLUSTERED 
(
	pfvId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaOportunidadPresentacionPlanilla    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaOportunidadPresentacionPlanilla(
	popId bigint IDENTITY(1,1) NOT NULL,
	popTipoPlanilla varchar(1) NULL,
	popOportunidad varchar(20) NULL,
	popTipoCotizanteEspecifico varchar(2) NULL,
 CONSTRAINT PK_PilaOportunidadPresentacionPlanilla_popId PRIMARY KEY CLUSTERED 
(
	popId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaPasoValores    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaPasoValores(
	ppvId bigint IDENTITY(1,1) NOT NULL,
	ppvIdPlanilla bigint NOT NULL,
	ppvTipoPlanilla varchar(75) NOT NULL,
	ppvBloque varchar(11) NOT NULL,
	ppvNombreVariable varchar(75) NOT NULL,
	ppvValorVariable varchar(200) NULL,
	ppvCodigoCampo varchar(10) NULL,
 CONSTRAINT PK_PilaPasoValores_ppvId PRIMARY KEY CLUSTERED 
(
	ppvId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaProceso    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaProceso(
	pprId bigint IDENTITY(1,1) NOT NULL,
	pprNumeroRadicado varchar(12) NOT NULL,
	pprTipoProceso varchar(30) NOT NULL,
	pprFechaInicioProceso datetime NOT NULL,
	pprFechaFinProceso datetime NULL,
	pprUsuarioProceso varchar(255) NOT NULL,
	pprEstadoProceso varchar(75) NOT NULL,
 CONSTRAINT PK_PilaProcesoValidacion_pprId PRIMARY KEY CLUSTERED 
(
	pprId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla dbo.PilaTasasInteresMora    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.PilaTasasInteresMora(
	ptiId bigint IDENTITY(1,1) NOT NULL,
	ptiFechaInicioTasa date NULL,
	ptiFechaFinTasa date NULL,
	ptiNumeroPeriodoTasa smallint NULL,
	ptiPorcentajeTasa numeric(4, 4) NULL,
	ptiNormativa varchar(100) NULL,
	ptiTipoInteres varchar(20) NULL,
 CONSTRAINT PK_PilaTasasInteresMora_ptiId PRIMARY KEY CLUSTERED 
(
	ptiId ASC
)
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

GO
ALTER TABLE dbo.OperadorInformacionCcf  WITH CHECK ADD  CONSTRAINT FK_OperadorInformacionCcf_oicCajaCompensacion FOREIGN KEY(oicCajaCompensacion)
REFERENCES dbo.CajaCompensacion (ccfId)
GO
ALTER TABLE dbo.OperadorInformacionCcf CHECK CONSTRAINT FK_OperadorInformacionCcf_oicCajaCompensacion
GO
ALTER TABLE dbo.OperadorInformacionCcf  WITH CHECK ADD  CONSTRAINT FK_OperadorInformacionCcf_oicOperadorInformacion FOREIGN KEY(oicOperadorInformacion)
REFERENCES dbo.OperadorInformacion (oinId)
GO
ALTER TABLE dbo.OperadorInformacionCcf CHECK CONSTRAINT FK_OperadorInformacionCcf_oicOperadorInformacion
GO
ALTER TABLE dbo.PilaArchivoAPRegistro1  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoAPRegistro1_ap1IndicePlanilla FOREIGN KEY(ap1IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoAPRegistro1 CHECK CONSTRAINT FK_PilaArchivoAPRegistro1_ap1IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoARegistro1  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoARegistro1_pa1IndicePlanilla FOREIGN KEY(pa1IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoARegistro1 CHECK CONSTRAINT FK_PilaArchivoARegistro1_pa1IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoFRegistro1  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoFRegistro1_pf1IndicePlanillaOF FOREIGN KEY(pf1IndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaArchivoFRegistro1 CHECK CONSTRAINT FK_PilaArchivoFRegistro1_pf1IndicePlanillaOF
GO
ALTER TABLE dbo.PilaArchivoFRegistro5  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoFRegistro5_pf5IndicePlanillaOF FOREIGN KEY(pf5IndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaArchivoFRegistro5 CHECK CONSTRAINT FK_PilaArchivoFRegistro5_pf5IndicePlanillaOF
GO
ALTER TABLE dbo.PilaArchivoFRegistro6  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoFRegistro6_pf6IndicePlanillaOF FOREIGN KEY(pf6IndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaArchivoFRegistro6 CHECK CONSTRAINT FK_PilaArchivoFRegistro6_pf6IndicePlanillaOF
GO
ALTER TABLE dbo.PilaArchivoFRegistro8  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoFRegistro8_pf8IndicePlanillaOF FOREIGN KEY(pf8IndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaArchivoFRegistro8 CHECK CONSTRAINT FK_PilaArchivoFRegistro8_pf8IndicePlanillaOF
GO
ALTER TABLE dbo.PilaArchivoFRegistro9  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoFRegistro9_pf9IndicePlanillaOF FOREIGN KEY(pf9IndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaArchivoFRegistro9 CHECK CONSTRAINT FK_PilaArchivoFRegistro9_pf9IndicePlanillaOF
GO
ALTER TABLE dbo.PilaArchivoIPRegistro1  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIPRegistro1_ip1IndicePlanilla FOREIGN KEY(ip1IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIPRegistro1 CHECK CONSTRAINT FK_PilaArchivoIPRegistro1_ip1IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoIPRegistro2  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIPRegistro2_ip2IndicePlanilla FOREIGN KEY(ip2IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIPRegistro2 CHECK CONSTRAINT FK_PilaArchivoIPRegistro2_ip2IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoIPRegistro3  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIPRegistro3_ip3IndicePlanilla FOREIGN KEY(ip3IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIPRegistro3 CHECK CONSTRAINT FK_PilaArchivoIPRegistro3_ip3IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoIRegistro1  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIRegistro1_pi1IndicePlanilla FOREIGN KEY(pi1IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIRegistro1 CHECK CONSTRAINT FK_PilaArchivoIRegistro1_pi1IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoIRegistro2  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIRegistro2_pi2IndicePlanilla FOREIGN KEY(pi2IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIRegistro2 CHECK CONSTRAINT FK_PilaArchivoIRegistro2_pi2IndicePlanilla
GO
ALTER TABLE dbo.PilaArchivoIRegistro3  WITH CHECK ADD  CONSTRAINT FK_PilaArchivoIRegistro3_pi3IndicePlanilla FOREIGN KEY(pi3IndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaArchivoIRegistro3 CHECK CONSTRAINT FK_PilaArchivoIRegistro3_pi3IndicePlanilla
GO
ALTER TABLE dbo.PilaEjecucionProgramada  WITH CHECK ADD  CONSTRAINT FK_PilaEjecucionProgramada_pepCajaCompensacion FOREIGN KEY(pepCajaCompensacion)
REFERENCES dbo.CajaCompensacion (ccfId)
GO
ALTER TABLE dbo.PilaEjecucionProgramada CHECK CONSTRAINT FK_PilaEjecucionProgramada_pepCajaCompensacion
GO
ALTER TABLE dbo.PilaErrorValidacionLog  WITH CHECK ADD  CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanilla FOREIGN KEY(pevIndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaErrorValidacionLog CHECK CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanilla
GO
ALTER TABLE dbo.PilaErrorValidacionLog  WITH CHECK ADD  CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanillaOF FOREIGN KEY(pevIndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaErrorValidacionLog CHECK CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanillaOF
GO
ALTER TABLE dbo.PilaEstadoBloque  WITH CHECK ADD  CONSTRAINT FK_PilaEstadoBloque_pebIndicePlanilla FOREIGN KEY(pebIndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaEstadoBloque CHECK CONSTRAINT FK_PilaEstadoBloque_pebIndicePlanilla
GO
ALTER TABLE dbo.PilaEstadoBloqueOF  WITH CHECK ADD  CONSTRAINT FK_PilaEstadoBloqueOF_peoIndicePlanillaOF FOREIGN KEY(peoIndicePlanillaOF)
REFERENCES dbo.PilaIndicePlanillaOF (pioId)
GO
ALTER TABLE dbo.PilaEstadoBloqueOF CHECK CONSTRAINT FK_PilaEstadoBloqueOF_peoIndicePlanillaOF
GO
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla  WITH CHECK ADD  CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picPilaIndicePlanilla FOREIGN KEY(picPilaIndicePlanilla)
REFERENCES dbo.PilaIndicePlanilla (pipId)
GO
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla CHECK CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picPilaIndicePlanilla
GO
ALTER TABLE dbo.PilaNormatividadFechaVencimiento  WITH CHECK ADD  CONSTRAINT FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante FOREIGN KEY(pfvClasificacionAportante)
REFERENCES dbo.PilaClasificacionAportante (pcaId)
GO
ALTER TABLE dbo.PilaNormatividadFechaVencimiento CHECK CONSTRAINT FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante
GO
ALTER TABLE PilaArchivoFRegistro8 ADD CONSTRAINT FK_PilaArchivoFRegistro8_pf8PilaArchivoFRegistro5 FOREIGN KEY(pf8PilaArchivoFRegistro5) REFERENCES PilaArchivoFRegistro5 (pf5Id);
GO
ALTER TABLE PilaArchivoFRegistro6 ADD CONSTRAINT FK_PilaArchivoFRegistro6_pf6PilaArchivoFRegistro5 FOREIGN KEY(pf6PilaArchivoFRegistro5) REFERENCES PilaArchivoFRegistro5 (pf5Id);
GO
ALTER TABLE PilaArchivoIRegistro1 ADD CONSTRAINT UQ_PilaArchivoIRegistro1 UNIQUE (pi1IndicePlanilla);
GO
ALTER TABLE PilaArchivoIRegistro3 ADD CONSTRAINT UQ_PilaArchivoIRegistro3 UNIQUE (pi3IndicePlanilla);
GO
ALTER TABLE PilaArchivoIPRegistro1 ADD CONSTRAINT UQ_PilaArchivoIPRegistro1 UNIQUE (ip1IndicePlanilla);
GO
ALTER TABLE PilaArchivoIPRegistro3 ADD CONSTRAINT UQ_PilaArchivoIPRegistro3 UNIQUE (ip3IndicePlanilla);
GO
