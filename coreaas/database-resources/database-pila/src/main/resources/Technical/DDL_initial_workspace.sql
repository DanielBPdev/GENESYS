--liquibase formatted sql

--changeset heinsohn:01
--comment:Creacioon modelo workspace
--Creacion del Esquema staging
CREATE SCHEMA staging
GO

--Creacion de la tabla staging.Transaccion
CREATE TABLE staging.Transaccion
(
	traId BIGINT IDENTITY NOT NULL,
	traFechaTransaccion DATETIME NOT NULL
)

--Creacion de la tabla staging.RegistroGeneral
CREATE TABLE staging.RegistroGeneral --staging.PilaArchivoIRegistro1, staging.PilaArchivoIPRegistro1
(
--TR1
	regId bigInt IDENTITY NOT NULL,
	regTransaccion bigInt NOT NULL,
	regEsAportePensionados bit NOT NULL,
--PI1	--IP1
	regNombreAportante varchar(200) NOT NULL, --ip1NombrePagador , pi1RazonSocial
	regTipoIdentificacionAportante varchar(20) NOT NULL, --pi1TipoDocAportante
	regNumeroIdentificacionAportante varchar(16) NOT NULL, --pi1IdAportante
	regDigVerAportante smallint NULL, --pi1DigVerAportante
	regPeriodoAporte varchar(7) NOT NULL, --ip1PeriodoAporte, pi1PeriodoAporte
	regTipoPlanilla varchar(1) NULL, --pi1TipoPlanilla NOT NULL
	regClaseAportante varchar(1) NULL, --pi1ClaseAportante
	regCodSucursal varchar(10) NULL, --pi1CodSucursal, ip1CodSucursal
	regNomSucursal varchar(40) NULL, --pi1NomSucursal, ip1NomSucursal
    regDireccion varchar(40) NULL, --pa1Direccion, ap1Direccion
    regCodCiudad varchar(3) NULL, --pa1CodCiudad, ap1CodCiudad
    regCodDepartamento varchar(2) NULL, --pa1CodDepartamento, ap1CodDepartamento
    regTelefono bigint NULL, --pa1CodTelefono, ap1Telefono
    regFax bigint NULL, --pa1Fax, ap1Fax
    regEmail varchar(60) NULL, --pa1Email, ap1Email
    regFechaMatricula date NULL, --pa1FechaMatricula
    regNaturalezaJuridica smallint NULL, --pa1NaturalezaJuridica, ap1NaturalezaJuridica
	regCantPensionados int NULL, --ip1CantPensionados NOT NULL
	regModalidadPlanilla smallint,--pi1ModalidadPlanilla
	regValTotalApoObligatorio bigint, --pi3ValorTotalAportes
	regValorIntMora bigint, --pi3ValorMora
	regFechaRecaudo date, --CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112)
	regCodigoEntidadFinanciera smallint NULL, --pf1CodigoEntidadFinanciera NOT NULL
	regOperadorInformacion bigint, --oinId
	regNumeroCuenta varchar (17) NULL, --pf5NumeroCuenta NOT NULL
	regFechaActualizacion date NULL, --pi1FechaActualizacion, ip1FechaActualizacion
--CONTROL
	regRegistroControl bigint NULL, --PilaIndicePlanilla.pipId
	regRegistroControlManual bigint NULL, 
	regRegistroFControl bigint NULL,
--OUTPUT
	regOUTTarifaEmpleador numeric(5,5) NULL, --nuevo campo
	regOUTFinalizadoProcesoManual bit NULL, --nuevo campo
	regOUTEsEmpleador bit NULL, --esEmpleador
	regOUTEstadoEmpleador varchar(50) NULL, --empEstadoEmpleador
	regOUTTipoBeneficio varchar(10) NULL, --bemTipoBeneficio
	regOUTBeneficioActivo bit NULL, --bemBeneficioActivo	
	regOUTEsEmpleadorReintegrable bit NULL, --nuevo campo
	regOUTEstadoArchivo varchar(60) NULL,  -- estadoArchivo
)

--Creacion de la tabla staging.RegistroDetallado 
CREATE TABLE staging.RegistroDetallado --staging.PilaArchivoIRegistro2, staging.PilaArchivoIPRegistro2
(
--TR1
	redId bigInt IDENTITY NOT NULL,
	redRegistroGeneral bigInt NOT NULL,
	redRegistroGeneralPrincipal bigint NULL, --pi2IndicePlanillaPrincipal (Para aporte Manual debe ser NULL)
--PI2--IP2
	redTipoIdentificacionCotizante varchar(2) NULL, --pi2TipoIdCotizante, ip2TipoIdPensionado
	redNumeroIdentificacionCotizante varchar(16) NULL, --pi2IdCotizante, ip2IdPensionado
	redTipoCotizante smallint NULL, --pi2TipoCotizante
	redCodDepartamento varchar(2) NULL, --pi2CodDepartamento, ip2CodDepartamento
	redCodMunicipio varchar(6) NULL, --pi2CodMunicipio, ip2CodMunicipio
	redPrimerApellido varchar(20) NULL, --pi2PrimerApellido, ip2PrimerApellido
	redSegundoApellido varchar(30) NULL, --pi2SegundoApellido, ip2SegundoApellido
	redPrimerNombre varchar(20) NULL, --pi2PrimerNombre, ip2PrimerNombre
	redSegundoNombre varchar(30) NULL, --pi2SegundoNombre, ip2SegundoApellido
	redNovIngreso varchar(1) NULL, --pi2NovIngreso, ip2NovING (se tiene en cuenta mientras no sea NULL)
	redNovRetiro varchar(1) NULL, --pi2NovRetiro, ip2NovRET (se tiene en cuenta mientras no sea NULL)
	redNovVSP varchar(1) NULL, --pi2NovVSP, ip2NovVSP (se tiene en cuenta mientras no sea NULL)
	redNovVST varchar(1) NULL, --pi2NovVST (se tiene en cuenta mientras no sea NULL)
	redNovSLN varchar(1) NULL, --pi2NovSLN (se tiene en cuenta mientras no sea NULL)
	redNovIGE varchar(1) NULL, --pi2NovIGE (se tiene en cuenta mientras no sea NULL)
	redNovLMA varchar(1) NULL, --pi2NovLMA (se tiene en cuenta mientras no sea NULL)
	redNovVACLR varchar(1) NULL, --pi2NovVACLR (se tiene en cuenta mientras no sea NULL)
	redNovSUS varchar(1) NULL, --ip2NovSUS (se tiene en cuenta mientras no sea NULL)
	redDiasIRL varchar(2) NULL, --pi2DiasIRL
	redDiasCotizados smallint NULL, --pi2DiasCotizados, ip2DiasCotizados
	redSalarioBasico int NULL, --pi2SalarioBasico
	redValorIBC int NULL, --redValorIBC, ip2ValorMesada
	redTarifa numeric(5, 5) NULL, --pi2Tarifa, ip2Tarifa
	redAporteObligatorio int NULL, --pi2AporteObligatorio, ip2ValorAporte
	redCorrecciones varchar(1) NULL, --pi2Correcciones
	redSalarioIntegral varchar(1) NULL, --pi2SalarioIntegral
	redFechaIngreso date NULL, --pi2FechaIngreso, ip2FechaIngreso
	redFechaRetiro date NULL, --pi2FechaRetiro, ip2FechaRetiro
	redFechaInicioVSP date NULL, --pi2FechaInicioVSP, ip2FechaInicioVSP
	redFechaInicioSLN date NULL, --pi2FechaInicioSLN
	redFechaFinSLN date NULL, --pi2FechaFinSLN
	redFechaInicioIGE date NULL, --pi2FechaInicioIGE
	redFechaFinIGE date NULL, --pi2FechaFinIGE
	redFechaInicioLMA date NULL, --pi2FechaInicioLMA
	redFechaFinLMA date NULL, --pi2FechaFinLMA
	redFechaInicioVACLR date NULL, --pi2FechaInicioVACLR
	redFechaFinVACLR date NULL, --pi2FechaFinVACLR
	redFechaInicioVCT date NULL, --pi2FechaInicioVCT
	redFechaFinVCT date NULL, --pi2FechaFinVCT
	redFechaInicioIRL date NULL, --pi2FechaInicioIRL
	redFechaFinIRL date NULL, --pi2FechaFinIRL
	redFechaInicioSuspension date NULL, --ip2FechaInicioSuspension
	redFechaFinSuspension date NULL, --ip2FechaFinSuspension
	redHorasLaboradas smallint NULL, --pi2HorasLaboradas
--CONTROL
	redRegistroControl bigint NULL,  --número de secuencia del archivo I2 o IP2 correspondiente
--OUTPUT
	redOUTMarcaValRegistroAporte varchar(50) NULL, --pi2MarcaValRegistroAporte, ip2MarcaValRegistroAporte
	redOUTEstadoRegistroAporte varchar(60) NULL, --pi2EstadoRegistroAporte, ip2EstadoRegistroAporte
	redOUTAnalisisIntegral bit NULL, --pi2AnalisisIntegral
	redOUTFechaProcesamientoValidRegAporte datetime NULL, --pi2FechaProcesamientoValidRegAporte, ip2FechaProcesamientoValidRegAporte
	redOUTEstadoValidacionV0 varchar(30) NULL, --pi2EstadoValidacionV0
	redOUTEstadoValidacionV1 varchar(30) NULL, --pi2EstadoValidacionV1, ip2EstadoValidacionV1
	redOUTEstadoValidacionV2 varchar(30) NULL, --pi2EstadoValidacionV2
	redOUTEstadoValidacionV3 varchar(30) NULL, --pi2EstadoValidacionV3
	redOUTClaseTrabajador varchar(50) NULL, --roaClaseTrabajador
	redOUTPorcentajePagoAportes numeric(19, 2) NULL, --roaPorcentajePagoAportes
	redOUTEstadoSolicitante varchar(50) NULL, --estadoSolicitante	
	redOUTEsTrabajadorReintegrable bit NULL, --nuevo campo
	redOUTFechaIngresoCotizante Date NULL, --nuevo campo
	redOUTFechaUltimaNovedad Date NULL --nuevo campo
)
GO

--Creacion de la tabla  staging.StagingParametros
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE staging.StagingParametros(
	stpId bigint IDENTITY(1,1) NOT NULL,
	stpNombreParametro varchar(150) NULL,
	stpValorParametro varchar(250) NULL
) ON [PRIMARY]
GO

--Creacion de la tabla  staging.Aportante
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.Aportante(
	apoId bigint IDENTITY NOT NULL,
	apoTransaccion bigint NOT NULL,
	apoTipoIdentificacion varchar(20) NOT NULL,
	apoNumeroIdentificacion varchar(16) NOT NULL,
	apoEsEmpleador bit NOT NULL,
	apoEstadoEmpleador varchar(50) NULL,
	apoTipoBeneficio varchar(16) NULL,
	apoBeneficioActivo bit NULL,
	apoTarifaEmpleador numeric(5,5) NULL,
	apoEsEmpleadorReintegrable bit NULL,
	apoMarcaSucursalPILA bit NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla  staging.Cotizante
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.Cotizante(
	cotId bigint IDENTITY NOT NULL,
	cotTransaccion bigint NOT NULL,
	cotTipoIdentificacion varchar(20) NOT NULL,
	cotNumeroIdentificacion varchar(16) NOT NULL,
	cotTipoIdentificacionEmpleador varchar(20) NULL,
	cotNumeroIdentificacionEmpleador varchar(16) NULL,
	cotPorcentajePagoAportes numeric(19, 2) NULL,
	cotEstadoAfiliado varchar(50) NULL,
	cotClaseTrabajador varchar(50) NULL,
	cotTipoAfiliado varchar(50) NULL,
	cotCodigoSucursal varchar(3) NULL,
	cotNombreSucursal varchar(100) NULL,
	cotEsTrabajadorReintegrable bit NULL,
	cotFechaIngreso date NULL,
	cotFechaUltimaNovedad date NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

--Creacion de la tabla  staging.Novedad
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.Novedad(
	novId bigint IDENTITY NOT NULL,
	novTransaccion bigint NOT NULL,
	novTipoIdentificacion varchar(20) NOT NULL,
	novNumeroIdentificacion varchar(16) NOT NULL,
	novTipoNovedad varchar(15) NOT NULL,
	novFechaInicio date NOT NULL,
	novFechaFin date NOT NULL,
	novPeriodoInicio varchar(7) NULL,
	novPeriodoFin varchar(7) NULL
) ON [PRIMARY]
GO


--Creacion de la tabla  staging.NovedadSituacionPrimaria
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.NovedadSituacionPrimaria(
	nspId bigint IDENTITY NOT NULL,
	nspTransaccion bigint NOT NULL,
	nspTipoIdentificacionCotizante varchar(20) NOT NULL,
	nspNumeroIdentificacionCotizante varchar(16) NOT NULL,
	nspTipoIdentificacionAportante varchar(20) NULL,
	nspNumeroIdentificacionAportante varchar(16)NULL,
	nspTipoNovedad varchar(15) NOT NULL,
	nspFechaInicio date NULL,
	nspFechaFin date NULL,
	nspPeriodoRegular varchar(7) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


--Creacion de la tabla  staging.SucursalEmpresa    Script Date: 30/06/2017 04:56:21 p.m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.SucursalEmpresa(
	sueId bigint IDENTITY NOT NULL,
	sueTransaccion bigint NOT NULL,
	sueTipoIdentificacion varchar(20) NOT NULL,
	sueNumeroIdentificacion varchar(16) NOT NULL,
	sueCodigoSucursal varchar(3) NULL,
	sueNombreSucursal varchar(100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

--Creacion de la tabla  staging.AportePeriodo
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE staging.AportePeriodo(
	appId bigint IDENTITY NOT NULL,
	appTransaccion bigint NOT NULL,
	appTipoIdentificacionCotizante varchar(20) NOT NULL,
	appNumeroIdentificacionCotizante varchar(16) NOT NULL,
	appTipoIdentificacionAportante varchar(20) NOT NULL,
	appNumeroIdentificacionAportante varchar(16) NOT NULL,
	appPeriodoAporte varchar(7) NOT NULL,
	appDiasCotizados smallint NOT NULL
) ON [PRIMARY]

GO

--ALTER TABLE PilaArchivoFRegistro5 ADD CONSTRAINT UQ_PilaArchivoFRegistro5 UNIQUE (pf5IndicePlanillaOF,pf5NumeroCuenta)
--GO
--ALTER TABLE PilaArchivoFRegistro6 ADD CONSTRAINT UQ_PilaArchivoFRegistro6 UNIQUE (pf6IndicePlanillaOF,pf6IdAportante,pf6NumeroPlanilla,pf6PeriodoPago,pf6CodOperadorInformacion)
--GO
--ALTER TABLE PilaArchivoFRegistro8 ADD CONSTRAINT UQ_PilaArchivoFRegistro8 UNIQUE (pf8IndicePlanillaOF,pf8PilaArchivoFRegistro5)
--GO
--ALTER TABLE PilaArchivoFRegistro9 ADD CONSTRAINT UQ_PilaArchivoFRegistro9 UNIQUE (pf9IndicePlanillaOF)
--GO

GO

--Creacion de la tabla  dbo.TemAportante    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.TemAportante(
       tapId bigint IDENTITY(1,1) NOT NULL,
       tapIdTransaccion bigint NOT NULL,
       tapRazonSocial varchar(200) NULL,
       tapTipoDocAportante varchar(20) NULL,
       tapIdAportante varchar(16) NULL,
       tapDigVerAportante smallint NULL,
       tapDireccion varchar(40) NULL,
       tapCodCiudad varchar(3) NULL,
       tapCodDepartamento varchar(2) NULL,
       tapTelefono bigint NULL,
       tapFax bigint NULL,
       tapEmail varchar(60) NULL,
       tapFechaMatricula date NULL,
       tapNaturalezaJuridica smallint NULL,
       tapCodSucursal varchar(10) NULL,
       tapNomSucursal varchar(40) NULL,
       tapFechaHoraSolicitud datetime NULL,
       tapMarcaCreacion varchar(100) NULL,
       tapTipoSolicitud varchar(100) NULL,
 CONSTRAINT PK_TemAportante_tapId PRIMARY KEY CLUSTERED 
(
	tapId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

--Creacion de la tabla  dbo.TemCotizante    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.TemCotizante(
       tctId bigint IDENTITY(1,1) NOT NULL,
       tctIdTransaccion bigint NOT NULL,
       tctTipoIdCotizante varchar(20) NULL,
       tctIdCotizante varchar(16) NULL,
       tctTipoCotizante varchar(50) NULL,
       tctPrimerApellido varchar(20) NULL,
       tctSegundoApellido varchar(30) NULL,
       tctPrimerNombre varchar(20) NULL,
       tctSegundoNombre varchar(30) NULL,
       tctCodigoDepartamento varchar(2) NULL,
       tctCodigoMunicipio varchar(6) NULL,
       tctTipoIdEmpleador varchar(20) NULL,
       tctIdEmpleador varchar(16) NULL,
       tctCodSucursal varchar(10) NULL,
       tctNomSucursal varchar(40) NULL,
	   tctCodSucursalPILA varchar(10) NULL,
       tctNomSucursalPILA varchar(40) NULL,
       tctFechaHoraSolicitud datetime NULL,
       tctMarcaCreacion varchar(100) NULL,
       tctTipoSolicitud varchar(100) NULL,
 CONSTRAINT PK_TemCotizante_tctId PRIMARY KEY CLUSTERED 
(
	tctId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
--Creacion de la tabla  dbo.TemAporte    

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.TemAporte(	
    temId bigint NOT NULL IDENTITY,
    temIdTransaccion bigint, --indicador de transacci�n utilizado para agrupar operaci�n en transaccional del lado del servicio de crear el aporte
    temMarcaAporteSimulado bit, --Indica si el registro a crear es simulado o real  SI=1, NO=0 (indica si se debe crear en tablas de aportes normal aporteGeneral-aporteDetallado o tablas de aportes simulado aporteGeneralSimulado-aporteDetalladoSimulado )
	temMarcaAporteManual bit, --Indica si el registro a crear proviene de una fuente Manual  SI=1, NO=0 (NO deben tenerse en cuenta en el encolador los aportes con esta marca en SI=1)
    --INICIO APORTE GENERAL
    temRegistroGeneral bigint, --Referencia a la entrada del Registro.
    temPeriodoAporte varchar(7), --Per�odo de pago
    temValTotalApoObligatorio bigint, --Aporte obligatorio del aporte
    temValorIntMoraGeneral bigint, --Valor inter�s de mora para un pensionado
    temFechaRecaudo date, --Fecha de recaudo del aporte
    temTipoIdAportante varchar(20), --Referencia al tipo de Identificaci�n del aportante ya existente
    temNumeroIdAportante varchar(16), --Referencia al n�mero de Identificaci�n del aportante ya existente    
    temCodEntidadFinanciera smallint, --C�digo de la entidad financiera recaudadora o receptora
    temOperadorInformacion bigint, --Referencia al operador de informaci�n que se relaciona en el registro de la planilla integrada de liquidaci�n de aportes
    temModalidadPlanilla varchar(40), --Modalidad de la planilla integrada de liquidaci�n de aportes:: UNICA, ASISTIDA
    temModalidadRecaudoAporte varchar(40), --Modalidad del recaudo del aporte:: PILA
    temApoConDetalle bit , --Indica si un aporte tiene detalle 1=Si o 0=No indicado en la estructura del aporte en HU 397
    temFormaReconocimientoAporte varchar(50), --Indica la forma del reconocimiento para el aporte :: puede llegar RECONOCIMIENTO_AUTOMATICO_OPORTUNO
    temNumeroCuenta varchar(60), --Indica el n�mero de cuenta por la cual se recauda el aporte
    --FIN APORTE GENERAL
    --INICIO APORTE DETALLADO
    temRegistroDetallado bigint, --Referencia a la entrada del registro tipo 2 de los archivos PILA tipo I-IR (Dependientes - Independientes)
    temTipoIdCotizante varchar(20), --Referencia al tipo de Identificaci�n del cotizante ya existente
    temNumeroIdCotizante varchar(16), --Referencia al numero de Identificaci�n del cotizante ya existente       
    temDiasCotizados smallint, --N�mero de d�as cotizados
    temHorasLaboradas smallint, --N�mero de horas laboradas
    temSalarioBasico bigint, --Salario b�sico
    temMunicipioLaboral varchar(6), --Municipio Laboral
    temDepartamentoLaboral varchar(2), --Departamento Laboral
    temValorIBC int, --Ingreso Base Cotizaci�n (IBC)
    temTarifa numeric (5,5), --Tarifa
    temSalarioIntegral bit, --Presenta Salario integral SI=1 NO=0
    temAporteObligatorio bigint, --Aporte obligatorio (sumatoria de Aporte obligatorio)
    temValorSaldoAporte bigint, --Valor Saldo Aporte (se afecta cuando se presenta un movimiento en el aporte sea un ajuste o devoluci�n)
    temValorIntMoraDetalle bigint, --Valor de mora individual
    temCorrecciones varchar(400), --Contenido del Registro tipo 2 campo 29: Correcciones
    temFechaProcesamiento datetime, --Fecha de procesamiento del aporte (Sistema al momento de relacionar o registrar)
    temEstadoAporteRecaudo varchar(50), --Descripci�n del estado del aporte del recaudo:: VIGENTE, CORREGIDO, EVALUACION_NO_APLICADA, ANULADO, REEMPLAZADO
    temEstadoAporteAjuste varchar(50), --Descripci�n del estado del aporte para el ajuste:: VIGENTE, CORREGIDO, EVALUACION_NO_APLICADA, ANULADO, REEMPLAZADO
    temEstadoRegistroAporte varchar(50), --Descripci�n del estado del registro a nivel de Cotizante o pensionado:: REGISTRADO, RELACIONADO, OTROS_INGRESOS
    --FIN APORTE DETALLADO
    CONSTRAINT PK_temAporte_temId PRIMARY KEY (temId) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

--Creacion de la tabla 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE dbo.TemNovedad(	
    tenId bigint NOT NULL IDENTITY,
    tenIdTransaccion bigint NOT NULL, --indicador de transacci�n utilizado para agrupar operaci�n en transaccional del lado del servicio de crear la novedad
    tenMarcaNovedadSimulado bit, --Indica si el registro a crear es simulado o real  SI=1, NO=0 
	tenMarcaNovedadManual bit, --Indica si el registro a crear proviene de una fuente Manual  SI=1, NO=0 (NO deben tenerse en cuenta en el encolador las novedades con esta marca en SI=1)
    tenRegistroGeneral bigint NOT NULL, --Referencia a la fuente de RegistroGeneral
    tenRegistroDetallado bigint NOT NULL, --Referencia a la fuente de RegistroDetallado
    tenTipoIdAportante varchar(20) NOT NULL, --Referencia al tipo de Identificaci�n del aportante ya existente
    tenNumeroIdAportante varchar(16) NOT NULL, --Referencia al n�mero de Identificaci�n del aportante ya existente    
    tenTipoIdCotizante varchar(20) NOT NULL, --Referencia al tipo de Identificaci�n del cotizante ya existente
    tenNumeroIdCotizante varchar(16) NOT NULL, --Referencia al numero de Identificaci�n del cotizante ya existente
	tenTipoTransaccion varchar(100) NULL, --Si la novedad es diferente de ingreso y retiro ac� va la novedad en terminos de CORE de otro modo va NULL
	tenEsIngreso BIT NOT NULL, --Si es una novedad de Ingreso va 1 sino va 0
	tenEsRetiro BIT NOT NULL, --Si es una novedad de Retiro va 1 sino va 0
    tenFechaInicioNovedad date NOT NULL,
	tenFechaFinNovedad date NULL,
	tenAccionNovedad VARCHAR(20) NOT NULL,
	tenMensajeNovedad VARCHAR(250) NULL,
	--Informacion del cotizante
    tenTipoCotizante varchar(50) NULL,
    tenPrimerApellido varchar(20) NULL,
    tenSegundoApellido varchar(30) NULL,
    tenPrimerNombre varchar(20) NULL,
    tenSegundoNombre varchar(30) NULL,
    tenCodigoDepartamento smallint NULL,
    tenCodigoMunicipio smallint NULL,
    --FIN APORTE DETALLADO
    CONSTRAINT PK_tenNovedad_tenId PRIMARY KEY (tenId) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

--Creacion de Tipo de datos NovedadesTYPE
CREATE TYPE NovedadesTYPE AS TABLE (TipoNovedad VARCHAR(15), FechaInicio DATE, FechaFin DATE, SituacionPrimaria BIT)
GO

--Creacion de la tabla  dbo.ReferenciaNovedad
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE dbo.ReferenciaNovedad(
	renId BigInt IDENTITY NOT NULL,
	renEstadoCotizante varchar(50) NULL,
	renSituacionPrimaria varchar(20) NULL,
	renTipoNovedad varchar(20) NULL,
	renResultado varchar(20) NULL,
	renCondicion varchar(20) NULL,
	renMensaje varchar(200) NULL
) ON [PRIMARY]


GO
ALTER TABLE staging.Transaccion ADD CONSTRAINT PK_Transaccion PRIMARY KEY (traId)
GO
ALTER TABLE staging.RegistroGeneral ADD CONSTRAINT PK_RegistroGeneral PRIMARY KEY (regId)
GO
ALTER TABLE staging.RegistroGeneral ADD CONSTRAINT FK_RegistroGeneral_Transaccion FOREIGN KEY(regTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.RegistroDetallado ADD CONSTRAINT PK_RegistroDetallado PRIMARY KEY (redId)
GO
ALTER TABLE staging.RegistroDetallado ADD CONSTRAINT FK_RegistroDetallado_RegistroGeneral FOREIGN KEY(redRegistroGeneral) REFERENCES staging.RegistroGeneral (regId);
GO
ALTER TABLE staging.StagingParametros ADD CONSTRAINT PK_StagingParametros PRIMARY KEY (stpId)
GO
ALTER TABLE staging.Aportante ADD CONSTRAINT PK_Aportante PRIMARY KEY (apoId)
GO
ALTER TABLE staging.Aportante ADD CONSTRAINT FK_Aportante_Transaccion FOREIGN KEY(apoTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.Cotizante ADD CONSTRAINT PK_Cotizante PRIMARY KEY (cotId)
GO
ALTER TABLE staging.Cotizante ADD CONSTRAINT FK_Cotizante_Transaccion FOREIGN KEY(cotTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.AportePeriodo ADD CONSTRAINT PK_AportePeriodo PRIMARY KEY (appId)
GO
ALTER TABLE staging.AportePeriodo ADD CONSTRAINT FK_AportePeriodo_Transaccion FOREIGN KEY(appTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.Novedad ADD CONSTRAINT PK_Novedad PRIMARY KEY (novId)
GO
ALTER TABLE staging.Novedad ADD CONSTRAINT FK_Novedad_Transaccion FOREIGN KEY(novTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.NovedadSituacionPrimaria ADD CONSTRAINT PK_NovedadSituacionPrimaria PRIMARY KEY (nspId)
GO
ALTER TABLE staging.NovedadSituacionPrimaria ADD CONSTRAINT FK_NovedadSituacionPrimaria_Transaccion FOREIGN KEY(nspTransaccion) REFERENCES staging.Transaccion (traId);
GO
ALTER TABLE staging.SucursalEmpresa ADD CONSTRAINT PK_SucursalEmpresa PRIMARY KEY (sueId)
GO
ALTER TABLE staging.SucursalEmpresa ADD CONSTRAINT FK_SucursalEmpresa_Transaccion FOREIGN KEY(sueTransaccion) REFERENCES staging.Transaccion (traId);
GO

ALTER TABLE dbo.ReferenciaNovedad ADD CONSTRAINT PK_ReferenciaNovedad PRIMARY KEY (renId)
GO

--CREATE INDEX IX_Aportante ON staging.Aportante (apoTransaccion, apoTipoIdentificacion, apoNumeroIdentificacion)
--GO
--CREATE INDEX IX_Cotizante ON staging.Cotizante (cotTransaccion, cotTipoIdentificacion, cotNumeroIdentificacion, cotTipoIdentificacionEmpleador, cotNumeroIdentificacionEmpleador)
--GO
