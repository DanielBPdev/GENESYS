--liquibase formatted sql

--changeset anbuitrago:01 
--comment: Se elimina llave foranea relacionada con los campos picIndicePlanillaAfectada y picFechaAccion 
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaAfectada;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaCargado;

--Se elimina las columnas picIndicePlanillaAfectada, picIndicePlanillaCargado y picFechaAccion
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP COLUMN picIndicePlanillaAfectada;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP COLUMN picIndicePlanillaCargado;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP COLUMN picFechaAccion;

--Cambios para HU-392 y HU-411 gestion de cambio de numero de identificacion del aportante
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picFechaSolicitud date NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picUsuario varchar(25) NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picNumeroIdentificacion bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picArchivosCorreccion varchar(225);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picUsuarioAprobador varchar(25);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picFechaRespuesta date;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picRazonRechazo varchar(25);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picIdPlanillaInformacion bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picIdPlanillaFinanciera bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picPilaIndicePlanilla bigint NOT NULL;

ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picPilaIndicePlanilla FOREIGN KEY (picPilaIndicePlanilla) REFERENCES dbo.PilaIndicePlanilla(pipId) ON DELETE NO ACTION ON UPDATE NO ACTION;

--changeset abaquero:02 
--comment: Se agregan columnas con el tamaño del archivo con el fin de cumplir con requerimiento de bandeja de inconsistencias HU-211-392
ALTER TABLE dbo.PilaIndicePlanilla ADD pipTamanoArchivo bigint;
ALTER TABLE dbo.PilaIndicePlanillaOF ADD pioTamanoArchivo bigint;

--Se elimina el campo pevIdIndicePlanilla de la tabla PilaErrorValidacionLog
ALTER TABLE dbo.PilaErrorValidacionLog DROP COLUMN pevIdIndicePlanilla;

--Se agrega columna de estado de gestión de inconsistencia de acuerdo a lo especificado en HU-211-392
ALTER TABLE dbo.PilaErrorValidacionLog ADD pevEstadoInconsistencia varchar(30);

--Se separa la referencia a los índices de planilla OI y OF para mejor eficiencia de consulta de inconsistencias
ALTER TABLE dbo.PilaErrorValidacionLog ADD pevIndicePlanilla bigint;
ALTER TABLE dbo.PilaErrorValidacionLog ADD pevIndicePlanillaOF bigint;
ALTER TABLE dbo.PilaErrorValidacionLog ADD CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanilla FOREIGN KEY (pevIndicePlanilla) REFERENCES PilaIndicePlanilla (pipId);
ALTER TABLE dbo.PilaErrorValidacionLog ADD CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanillaOF FOREIGN KEY (pevIndicePlanillaOF) REFERENCES PilaIndicePlanillaOF (pioId);

--Se agrega marca que identifica que el estado de validación PILA vs BD corresponde a un análisis integral (HU-211-480)
ALTER TABLE PilaArchivoIRegistro2  ADD pi2AnalisisIntegral bit;

--Se eliminan las columnas del estado por bloque que hacían referencia a los log de lectura de FileProcessor debido al cambio en el manejo de inconsistencias centralizado empleado para la HU-211-392
ALTER TABLE dbo.PilaEstadoBloque DROP COLUMN pebIdLogLecturaBloque2;
ALTER TABLE dbo.PilaEstadoBloque DROP COLUMN pebIdLogLecturaBloque4;

-- se agregan campos de estadoBloque y accionBloque para las 3 fases de PILA
-- Bloque 7 -> Fase 1: Validación vs BD (HU-211-395, 396 y 480)
-- Bloque 8 -> Fase 2: Registro o relación del aporte (HU-211-397)
-- Bloque 9 -> Fase 3: Registro o relación de novedades (HU-211-398)
ALTER TABLE dbo.PilaEstadoBloque ADD pebEstadoBloque7 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebAccionBloque7 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebEstadoBloque8 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebAccionBloque8 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebEstadoBloque9 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebAccionBloque9 varchar(75);


--changeset hhernandez:03
--comment: Se adicionan los campos apgApoConDetalle, apgFormaReconocimientoAporte y apgNumeroCuenta de la tabla AporteGeneral
ALTER TABLE dbo.AporteGeneral ADD apgApoConDetalle bit;
ALTER TABLE dbo.AporteGeneral ADD apgFormaReconocimientoAporte varchar(75);
ALTER TABLE dbo.AporteGeneral ADD apgNumeroCuenta varchar(17);

--Se adicionan los campos agsApoConDetalle, agsFormaReconocimientoAporte y agsNumeroCuenta de la tabla AporteGeneralSimulado
ALTER TABLE dbo.AporteGeneralSimulado ADD agsApoConDetalle bit;
ALTER TABLE dbo.AporteGeneralSimulado ADD agsFormaReconocimientoAporte varchar(75);
ALTER TABLE dbo.AporteGeneralSimulado ADD agsNumeroCuenta varchar(17);

--Se crea nuevamente el campo apdValorSalarioIntegral para las tablas AporteDetallado y AporteDetalladoSimulado para que tenga coherencia con la HU-211-397 y PILA
ALTER TABLE dbo.AporteDetallado DROP COLUMN apdValorSalarioIntegral;
ALTER TABLE dbo.AporteDetallado ADD apdSalarioIntegral bit;
ALTER TABLE dbo.AporteDetalladoSimulado DROP COLUMN adsValorSalarioIntegral;
ALTER TABLE dbo.AporteDetalladoSimulado ADD adsSalarioIntegral bit;

-- se elimina la referencia en forma de ubicación para el departamento y municipio del aporte detallado
-- la información incluida en PILA es muy limitada para generar una entrada de Ubicación
ALTER TABLE dbo.AporteDetallado DROP CONSTRAINT FK_AporteDetallado_apdUbicacionLaboral;
ALTER TABLE dbo.AporteDetallado DROP COLUMN apdUbicacionLaboral;
ALTER TABLE dbo.AporteDetallado ADD apdMunicipioLaboral smallint;
ALTER TABLE dbo.AporteDetallado ADD apdDepartamentoLaboral smallint;

ALTER TABLE dbo.AporteDetalladoSimulado DROP COLUMN adsUbicacionLaboral;
ALTER TABLE dbo.AporteDetalladoSimulado ADD adsMunicipioLaboral smallint;
ALTER TABLE dbo.AporteDetalladoSimulado ADD adsDepartamentoLaboral smallint;

--Se agrega el campo de referencia con una sucursal de empresa para los casos en los que aplica según PILA
ALTER TABLE dbo.Aportante ADD apoSucursalEmpresa bigint;
ALTER TABLE dbo.Aportante ADD CONSTRAINT FK_Aportante_apoSucursalEmpresa FOREIGN KEY (apoSucursalEmpresa) REFERENCES dbo.SucursalEmpresa(sueId);

--Se adiciona los campos apoEmpresa y apoPagadorPorTerceros de la tabla Aportante.
ALTER TABLE dbo.Aportante ADD apoEmpresa bigint;
ALTER TABLE dbo.Aportante ADD apoPagadorPorTerceros bit;

--Cambio de nombre de la columna apoEstadoAportanteInicial a apoEstadoAportante de la tabla Aportante
EXEC sp_RENAME 'dbo.Aportante.apoEstadoAportanteInicial' , 'apoEstadoAportante';

--Eliminación de la columnna apoEstadoAportanteFinal, apoEmpleador y apoEntidadPagadora de la tabla Aportante
ALTER TABLE dbo.Aportante DROP COLUMN apoEstadoAportanteFinal;
ALTER TABLE dbo.Aportante DROP CONSTRAINT FK_Aportante_apoEmpleador;
ALTER TABLE dbo.Aportante DROP CONSTRAINT FK_Aportante_apoEntidadPagadora;
ALTER TABLE dbo.Aportante DROP COLUMN apoEmpleador;
ALTER TABLE dbo.Aportante DROP COLUMN apoEntidadPagadora;

--Cambio de nombre de la columna cotEstadoCotizanteInicial a cotEstadoCotizante de la tabla Cotizante
EXEC sp_RENAME 'dbo.Cotizante.cotEstadoCotizanteInicial' , 'cotEstadoCotizante';

--Eliminación de la columnna cotEstadoCotizanteFinal, cotAfiliadoCotizante de la tabla Cotizante
ALTER TABLE dbo.Cotizante DROP COLUMN cotEstadoCotizanteFinal;
ALTER TABLE dbo.Cotizante DROP CONSTRAINT FK_Cotizante_cotAfiliadoCotizante;
ALTER TABLE dbo.Cotizante DROP COLUMN cotAfiliadoCotizante;

--Se agrega el campo de referencia con una sucursal de empresa para los casos en los que aplica según PILA
ALTER TABLE dbo.Cotizante ADD cotPersona bigint;
ALTER TABLE dbo.Cotizante ADD CONSTRAINT FK_Cotizante_cotPersona FOREIGN KEY (cotPersona) REFERENCES dbo.Persona(perId);

--Se agrega campo perCreadoPorPila para la tabla Persona
ALTER TABLE dbo.Persona ADD perCreadoPorPila bit NULL;

--Se agrega campo perCreadoPorPila para la tabla Empresa
ALTER TABLE dbo.Empresa ADD empCreadoPorPila bit NULL;

--changeset arocha:04
--Creación del Schema [staging]   
CREATE SCHEMA [staging];
GO

--Creación de la tabla [staging].[Aportante]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[Aportante](
	[apoTipoIdentificacion] [varchar](20) NOT NULL,
	[apoNumeroIdentificacion] [varchar](16) NOT NULL,
	[apoEsEmpleador] [bit] NOT NULL,
	[apoEstadoEmpleador] [varchar](50) NULL,
	[apoTipoBeneficio] [varchar](10) NULL,
	[apoBeneficioActivo] [bit] NULL,
	[apoMarcaSucursalPILA] [bit] NOT NULL,
	[apoIndicePlanilla] [bigint] NULL,
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[Cotizante]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[Cotizante](
	[cotTipoIdentificacion] [varchar](2) NOT NULL,
	[cotNumeroIdentificacion] [varchar](16) NOT NULL,
	[cotTipoIdentificacionEmpleador] [varchar](2) NULL,
	[cotNumeroIdentificacionEmpleador] [varchar](16) NULL,
	[cotPorcentajePagoAportes] [numeric](19, 2) NULL,
	[cotEstadoAfiliado] [varchar](50) NULL,
	[cotClaseTrabajador] [varchar](50) NULL,
	[cotTipoAfiliado] [varchar](50) NULL,
	[cotCodigoSucursal] [varchar](3) NULL,
	[cotNombreSucursal] [varchar](100) NULL,
	[cotIndicePlanilla] [bigint] NULL,
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[Novedad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[Novedad](
	[novTipoIdentificacion] [varchar](2) NULL,
	[novNumeroIdentificacion] [varchar](16) NULL,
	[novTipoNovedad] [varchar](15) NULL,
	[novFechaInicio] [date] NULL,
	[novFechaFin] [date] NULL,
	[novPeriodoInicio] [date] NULL,
	[novPeriodoFin] [date] NULL,
	[novIndicePlanilla] [bigint] NULL
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[StagingParametros]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[StagingParametros](
	[stpId] [bigint] IDENTITY(1,1) NOT NULL,
	[stpNombreParametro] [varchar](150) NULL,
	[stpValorParametro] [varchar](250) NULL
) ON [PRIMARY]

GO

--changeset masanchez:05
--Creación de la tabla [staging].[NovedadPila]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[NovedadPila](
	[pi2Id] [bigint] NULL,
	[pi2IndicePlanilla] [bigint] NULL,
	[nopFechaInicio] [date] NULL,
	[nopFechaFin] [date] NULL,
	[nopTipoNovedadPila] [varchar](15) NULL,
	[cotProcesoManual] [bit]
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[PilaArchivoIPRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[PilaArchivoIPRegistro1](
	[ip1Id] [bigint] NOT NULL,
	[ip1IndicePlanilla] [bigint] NOT NULL,
	[ip1NombrePagador] [varchar](150) NOT NULL,
	[ip1PeriodoAporte] [varchar](7) NOT NULL,
	[ip1FechaPago] [date] NULL,
	[ip1NumPlanilla] [varchar](10) NOT NULL,
	[ip1FormaPresentacion] [varchar](1) NOT NULL,
	[ip1CodSucursal] [varchar](10) NULL,
	[ip1NomSucursal] [varchar](40) NULL,
	[ip1ValorTotalMesadas] [bigint] NOT NULL,
	[ip1CantPensionados] [int] NOT NULL,
	[ip1DiasMora] [smallint] NOT NULL,
	[estadoArchivo] [varchar](60) NULL
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[PilaArchivoIPRegistro2]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[PilaArchivoIPRegistro2](
	[ip2Id] [bigint] NOT NULL,
	[ip2IndicePlanilla] [bigint] NOT NULL,
	[ip2TipoIdPensionado] [varchar](2) NOT NULL,
	[ip2IdPensionado] [varchar](16) NOT NULL,
	[ip2PrimerApellido] [varchar](20) NOT NULL,
	[ip2SegundoApellido] [varchar](30) NULL,
	[ip2PrimerNombre] [varchar](20) NOT NULL,
	[ip2SegundoNombre] [varchar](30) NULL,
	[ip2Tarifa] [numeric](5, 5) NOT NULL,
	[ip2ValorAporte] [int] NOT NULL,
	[ip2ValorMesada] [int] NOT NULL,
	[ip2DiasCotizados] [smallint] NOT NULL,
	[ip2NovING] [varchar](1) NULL,
	[ip2NovRET] [varchar](1) NULL,
	[ip2NovVSP] [varchar](1) NULL,
	[ip2NovSUS] [varchar](1) NULL,
	[ip2FechaIngreso] [date] NULL,
	[ip2FechaRetiro] [date] NULL,
	[ip2FechaInicioVSP] [date] NULL,
	[ip2MarcaValRegistroAporte] [varchar](50) NULL,
	[ip2EstadoRegistroAporte] [varchar](60) NULL,
	[ip2FechaProcesamientoValidRegAporte] [datetime] NULL,
	[ip2EstadoValidacionV1] [varchar](30) NULL,
	[roaPorcentajePagoAportes] [numeric](19, 2) NULL,
	[estadoSolicitante] [varchar](50) NULL
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[PilaArchivoIRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[PilaArchivoIRegistro1](
	[pi1Id] [bigint] NULL,
	[pi1IndicePlanilla] [bigint] NOT NULL,
	[pi1IdCCF] [varchar](16) NOT NULL,
	[pi1DigVerCCF] [smallint] NOT NULL,
	[pi1RazonSocial] [varchar](200) NOT NULL,
	[pi1TipoDocAportante] [varchar](20) NOT NULL,
	[pi1IdAportante] [varchar](16) NOT NULL,
	[pi1DigVerAportante] [smallint] NULL,
	[pi1TipoAportante] [varchar](2) NOT NULL,
	[pi1PeriodoAporte] [varchar](7) NOT NULL,
	[pi1TipoPlanilla] [varchar](1) NOT NULL,
	[pi1ClaseAportante] [varchar](1) NULL,
	[esEmpleador] [bit] NULL,
	[empEstadoEmpleador] [varchar](50) NULL,
	[bemTipoBeneficio] [varchar](10) NULL,
	[bemBeneficioActivo] [bit] NULL,
	[pi1IndicePlanillaPrincipal] [bigint] NULL,
	[estadoArchivo] [varchar](60) NULL
) ON [PRIMARY]
GO

--Creación de la tabla [staging].[PilaArchivoIRegistro2]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [staging].[PilaArchivoIRegistro2](
	[pi2Id] [bigint] NULL,
	[pi2IndicePlanilla] [bigint] NULL,
	[pi2TipoIdCotizante] [varchar](2) NULL,
	[pi2IdCotizante] [varchar](16) NULL,
	[pi2TipoCotizante] [smallint] NULL,
	[pi2CodDepartamento] [varchar](2) NULL,
	[pi2CodMunicipio] [varchar](3) NULL,
	[pi2PrimerApellido] [varchar](20) NULL,
	[pi2SegundoApellido] [varchar](30) NULL,
	[pi2PrimerNombre] [varchar](20) NULL,
	[pi2SegundoNombre] [varchar](30) NULL,
	[pi2NovIngreso] [varchar](1) NULL,
	[pi2NovRetiro] [varchar](1) NULL,
	[pi2NovVSP] [varchar](1) NULL,
	[pi2NovVST] [varchar](1) NULL,
	[pi2NovSLN] [varchar](1) NULL,
	[pi2NovIGE] [varchar](1) NULL,
	[pi2NovLMA] [varchar](1) NULL,
	[pi2NovVACLR] [varchar](1) NULL,
	[pi2DiasIRL] [varchar](2) NULL,
	[pi2DiasCotizados] [smallint] NULL,
	[pi2SalarioBasico] [int] NULL,
	[pi2ValorIBC] [int] NULL,
	[pi2Tarifa] [numeric](5, 5) NULL,
	[pi2AporteObligatorio] [int] NULL,
	[pi2Correcciones] [varchar](1) NULL,
	[pi2SalarioIntegral] [varchar](1) NULL,
	[pi2FechaIngreso] [date] NULL,
	[pi2FechaRetiro] [date] NULL,
	[pi2FechaInicioVSP] [date] NULL,
	[pi2FechaInicioSLN] [date] NULL,
	[pi2FechaFinSLN] [date] NULL,
	[pi2FechaInicioIGE] [date] NULL,
	[pi2FechaFinIGE] [date] NULL,
	[pi2FechaInicioLMA] [date] NULL,
	[pi2FechaFinLMA] [date] NULL,
	[pi2FechaInicioVACLR] [date] NULL,
	[pi2FechaFinVACLR] [date] NULL,
	[pi2FechaInicioVCT] [date] NULL,
	[pi2FechaFinVCT] [date] NULL,
	[pi2FechaInicioIRL] [date] NULL,
	[pi2FechaFinIRL] [date] NULL,
	[pi2HorasLaboradas] [smallint] NULL,
	[pi2MarcaValRegistroAporte] [varchar](50) NULL,
	[pi2EstadoRegistroAporte] [varchar](60) NULL,
	[pi2AnalisisIntegral] [bit] NULL,
	[pi2FechaProcesamientoValidRegAporte] [datetime] NULL,
	[pi2EstadoValidacionV0] [varchar](30) NULL,
	[pi2EstadoValidacionV1] [varchar](30) NULL,
	[pi2EstadoValidacionV2] [varchar](30) NULL,
	[pi2EstadoValidacionV3] [varchar](30) NULL,
	[roaClaseTrabajador] [varchar](50) NULL,
	[roaEmpleador] [bigint] NULL,
	[roaPorcentajePagoAportes] [numeric](19, 2) NULL,
	[estadoSolicitante] [varchar](50) NULL,
	[pi2IndicePlanillaPrincipal] [bigint] NULL
) ON [PRIMARY]
GO

