if not exists (select * from sys.schemas where name = 'masivos') 
begin
exec('create schema masivos')
end


if not exists (select * from information_schema.tables  where table_name = 'aportesDevolucion') 
begin
create table [masivos].[aportesDevolucion](
	[apgId] [bigint] NULL,
	[apgFechaRecaudo] [date] NULL,
	[apgModalidadPlanilla] [varchar](50) NULL,
	[apgApoConDetalle] [varchar](2) NULL,
	[pipIdPlanilla] [bigint] NULL,
	[estadoArchivo] [varchar](30) NULL,
	[tipoArchivoDetalle] [varchar](3) NULL,
	[regTipoPlanilla] [varchar](2) NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[tieneModificaciones] [varchar](2) NULL,
	[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
	[apgValorIntMora] [numeric](19, 5) NULL,
	[totalAporte] [numeric](19, 5) NULL,
	[perTipoIdentificacion] [varchar](25) NULL,
	[perNumeroIdentificacion] [varchar](25) NULL,
	[perRazonSocial] [varchar](70) NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[numeroRadicado] [varchar](25) NULL,
	[apgEstadoAporteAportante] [varchar](40) NULL,
	[apgModalidadRecaudoAporte] [varchar](30) NULL,
	[soaEstadoSolicitud] [varchar](40) NULL
);
end

if not exists (select * from information_schema.tables  where table_name = 'aportesDevolucion')
begin
create table [masivos].[aportesDevolucion](
	[apgId] [bigint] NULL,
	[apgFechaRecaudo] [date] NULL,
	[apgModalidadPlanilla] [varchar](50) NULL,
	[apgApoConDetalle] [varchar](2) NULL,
	[pipIdPlanilla] [bigint] NULL,
	[estadoArchivo] [varchar](30) NULL,
	[tipoArchivoDetalle] [varchar](3) NULL,
	[regTipoPlanilla] [varchar](2) NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[tieneModificaciones] [varchar](2) NULL,
	[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
	[apgValorIntMora] [numeric](19, 5) NULL,
	[totalAporte] [numeric](19, 5) NULL,
	[perTipoIdentificacion] [varchar](25) NULL,
	[perNumeroIdentificacion] [varchar](25) NULL,
	[perRazonSocial] [varchar](70) NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[numeroRadicado] [varchar](25) NULL,
	[apgEstadoAporteAportante] [varchar](40) NULL,
	[apgModalidadRecaudoAporte] [varchar](30) NULL,
	[soaEstadoSolicitud] [varchar](40) NULL
);
end


if not exists (select * from information_schema.tables  where table_name = 'aportesDevolucionDetalle')
begin
create table [masivos].[aportesDevolucionDetalle](
	[numeroRadicado] [varchar](25) NULL,
	[apgId] [bigint] NULL,
	[apdId] [bigint] NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[perTipoIdentificacion] [varchar](25) NULL,
	[perNumeroIdentificacion] [varchar](25) NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perSegundoNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[perSegundoApellido] [varchar](50) NULL,
	[apdRegistroDetallado] [bigint] NULL,
	[apdSalarioBasico] [numeric](19, 5) NULL,
	[apdDiasCotizados] [int] NULL,
	[apdTarifa] [numeric](19, 5) NULL,
	[redNovIngreso] [varchar](2) NULL,
	[redNovRetiro] [varchar](2) NULL,
	[redNovVSP] [varchar](2) NULL,
	[redNovVST] [varchar](2) NULL,
	[redNovSLN] [varchar](2) NULL,
	[redNovIGE] [varchar](2) NULL,
	[redNovLMA] [varchar](2) NULL,
	[redNovVACLR] [varchar](2) NULL,
	[redNovSUS] [varchar](2) NULL,
	[redDiasIRL] [varchar](2) NULL,
	[tieneSub] [bit] NULL,
	[tieneMod] [varchar](2) NULL
);
end

if not exists (select * from information_schema.tables  where table_name = 'aportesDevolucionGeneralSimular')
begin
create table [masivos].[aportesDevolucionGeneralSimular](
	[iden] [int] identity(1,1) NOT NULL,
	[id] [int] NULL,
	[idSolicitud] [bigint] NULL,
	[apgId] [bigint] NULL,
	[apgFechaRecaudo] [date] NULL,
	[apgModalidadPlanilla] [varchar](50) NULL,
	[apgApoConDetalle] [varchar](2) NULL,
	[pipIdPlanilla] [bigint] NULL,
	[estadoArchivo] [varchar](30) NULL,
	[tipoArchivoDetalle] [varchar](3) NULL,
	[regTipoPlanilla] [varchar](3) NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[tieneModificaciones] [varchar](2) NULL,
	[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
	[apgValorIntMora] [numeric](19, 5) NULL,
	[totalAporte] [numeric](19, 5) NULL,
	[perTipoIdentificacion] [varchar](25) NULL,
	[perNumeroIdentificacion] [varchar](25) NULL,
	[perRazonSocial] [varchar](70) NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[numeroRadicado] [varchar](25) NULL,
	[apgEstadoAporteAportante] [varchar](40) NULL,
	[apgModalidadRecaudoAporte] [varchar](30) NULL,
	[soaEstadoSolicitud] [varchar](40) NULL,
	[radicadoSimulado] [varchar](30) NULL
);
end

if not exists (select * from information_schema.tables  where TABLE_NAME = 'crearPersonaAporCot') 
begin
create table [masivos].[crearPersonaAporCot](
	[perTipoIdentificacion] [varchar](30) NULL,
	[perNumeroIdentificacion] [varchar](20) NULL,
	[perRazonSocial] [varchar](300) NULL,
	[crear] [bit] NULL,
	[procesado] [bit] NULL,
	[perPrimerNombre] [varchar](70) NULL,
	[perSegundoNombre] [varchar](70) NULL,
	[perPrimerApellido] [varchar](70) NULL,
	[perSegundoApellido] [varchar](70) NULL,
	[magTipoAportante] [varchar](50) NULL
)
end


if not exists (select * from information_schema.tables  where table_name = 'MasivoApGeeralRechazos')
begin
create table [masivos].[MasivoApGeeralRechazos](
	[magrId] [bigint] identity(1,1) NOT NULL,
	[magrMasivoNombreArchivo] [varchar](250) NULL,
	[magrMasivoGeneralId] [bigint] NULL,
	[magrMasivoDetalleId] [bigint] NULL,
	[magrObservacion] [varchar](300) NULL,
	[magrProceso] [varchar](20) NULL
);
end

if not exists (select * from information_schema.tables  where table_name = 'MasivoArchivo')
begin
create table [masivos].[MasivoArchivo](
	[maaId] [bigint] identity(1,1) NOT NULL,
	[maaNombreArchivo] [varchar](150) NOT NULL,
	[maaNombreOriginalArchivo] [varchar](150) NULL,
	[maaEstado] [varchar](50) NOT NULL,
	[maaNumeroRadicacion] [varchar](50) NULL,
	[maaFechaProcesamiento] [datetime] NULL,
	[maaFechaActualizacion] [datetime] NULL,
	[maaUsuario] [varchar](50) NULL,
	[maaSolicitud] [bigint] NULL,
	[maaTipoArchivo] [varchar](20) NULL,
	[maaValSubsidio] [bit] NULL,
	[maaIdCargue] [bigint] NULL,
	[maaMotivoDevolucion] [varchar](100) NULL,
	[maaOtroMotivoDevolucion] [varchar](100) NULL,
	[maaDestinatario] [varchar](100) NULL,
	[maaOtroDestinatario] [varchar](100) NULL,
	[maaOtroCaja] [varchar](50) NULL,
 	constraint [PK_MAA_maaId] primary key clustered (maaId)
);
end

if not exists (select * from information_schema.tables  where table_name = 'MasivoDetallado')
begin
create table [masivos].[MasivoDetallado](
	[madId] [bigint] identity(1,1) NOT NULL,
	[madMasivoGeneral] [bigint] NOT NULL,
	[madTipoCotizante] [varchar](20) NULL,
	[madTipoIdentificacionCotizante] [varchar](50) NOT NULL,
	[madNumeroIdentificacionCotizante] [varchar](50) NOT NULL,
	[madConceptoPago] [varchar](100) NULL,
	[madIbc] [numeric](19, 5) NULL,
	[madSalarioBasico] [numeric](19, 5) NULL,
	[madDiasCotizados] [tinyint] NULL,
	[madAporteObligatorio] [numeric](19, 5) NULL,
	[madTarifa] [numeric](19, 5) NULL,
	[madTotalAporte] [numeric](19, 5) NULL,
	[madValorIntereses] [numeric](19, 5) NULL,
	[madHorasLaboradas] [int] NULL,
	[madDiasMora] [tinyint] NULL,
	[madNovING] [varchar](1) NULL,
	[madNovRET] [varchar](1) NULL,
	[madNovIRL] [varchar](1) NULL,
	[madNovVSP] [varchar](1) NULL,
	[madNovVST] [varchar](1) NULL,
	[madNovSLN] [varchar](1) NULL,
	[madNovIGE] [varchar](1) NULL,
	[madNovLMA] [varchar](1) NULL,
	[madNovVACLR] [varchar](1) NULL,
	[madPrimerNombreCotizante] [varchar](350) NULL,
	[madSegundoNombreCotizante] [varchar](350) NULL,
	[madPrimerApellidoCotizante] [varchar](350) NULL,
	[madSegundoApellidoCotizante] [varchar](350) NULL,
	constraint [PK_mad_madId] primary key clustered (madId)
);
end
if not exists (select * from information_schema.tables  where table_name = 'MasivoGeneral')
begin
create table [masivos].[MasivoGeneral](
	[magId] [bigint] identity(1,1) NOT NULL,
	[magTipoIdentificacionAportante] [varchar](50) NULL,
	[magNumeroIdentificacionAportante] [varchar](50) NULL,
	[magDepartamento] [varchar](50) NULL,
	[magMunicipio] [varchar](50) NULL,
	[magRazonSocial] [varchar](300) NULL,
	[magEstadoArchivo] [varchar](50) NULL,
	[magPeriodoPago] [varchar](7) NULL,
	[magFechaRecepcionAporte] [date] NULL,
	[magFechaPago] [date] NULL,
	[magMasivoArchivo] [bigint] NULL,
	[magTipoAportante] [varchar](50) NULL,
 	constraint [PK_MAG_magId] primary key clustered (magId)
);

alter table [masivos].[MasivoGeneral] with check add constraint [FK_MasivoArchivo_magMasivoArchivo] foreign key ([magMasivoArchivo]) references [masivos].[MasivoArchivo] ([maaId]);
alter table [masivos].[MasivoGeneral] check constraint [FK_MasivoArchivo_magMasivoArchivo]
end

if not exists (select * from information_schema.tables  where table_name = 'MasivoGeneralDevolucion')
begin
create table [masivos].[MasivoGeneralDevolucion](
	[mgdId] [smallint] identity(1,1) NOT NULL,
	[mgdTipoIdentificacionAportante] [varchar](50) NULL,
	[mgdNumeroIdentificacionAportante] [varchar](50) NULL,
	[mgdRazonSocial] [varchar](100) NULL,
	[mgdPeriodoPago] [varchar](7) NULL,
	[mgdMasivoArchivo] [bigint] NULL,
	[mgdTipoAportante] [varchar](20) NULL,
 constraint [PK_MGD_mgdId] primary key clustered (mgdId)
);
end

if not exists (select * from information_schema.tables  where table_name = 'MasivoSimulado')
begin
create table [masivos].[MasivoSimulado](
	[maaFechaProcesamiento] [datetime] NULL,
	[maaNombreArchivo] [varchar](300) NULL,
	[magTipoIdentificacionAportante] [varchar](25) NULL,
	[magNumeroIdentificacionAportante] [varchar](20) NULL,
	[magRazonSocial] [varchar](250) NULL,
	[magPeriodoPago] [varchar](7) NULL,
	[maaEstado] [varchar](20) NULL,
	[maaUsuario] [varchar](30) NULL,
	[cantRegistros] [int] NULL,
	[cantidadNovedade] [int] NULL,
	[MontoToal] [numeric](19, 5) NULL,
	[masid] [bigint] identity(1,1) NOT NULL
);
end

if not exists (select * from information_schema.tables  where table_name = 'SimilacionLogGeneral')
begin
create table [masivos].[SimilacionLogGeneral](
	[maaId] [bigint] NOT NULL,
	[maaNombreArchivo] [varchar](250) NOT NULL,
	[maaEstado] [varchar](50) NOT NULL,
	[maaNumeroRadicacion] [varchar](22) NULL,
	[maaFechaProcesamiento] [date] NULL,
	[maaFechaActualizacion] [date] NULL,
	[maaUsuario] [varchar](80) NULL,
	[maaSolicitud] [bigint] NULL,
	[maaTipoArchivo] [varchar](20) NULL,
	[magId] [bigint] NOT NULL,
	[magTipoIdentificacionAportante] [varchar](30) NULL,
	[magNumeroIdentificacionAportante] [varchar](20) NULL,
	[magDepartamento] [varchar](50) NULL,
	[magMunicipio] [varchar](50) NULL,
	[magRazonSocial] [varchar](300) NULL,
	[magEstadoArchivo] [varchar](50) NULL,
	[magPeriodoPago] [varchar](7) NULL,
	[magFechaRecepcionAporte] [date] NULL,
	[magFechaPago] [date] NULL,
	[magMasivoArchivo] [bigint] NULL,
	[magTipoAportante] [varchar](50) NULL,
	[madId] [bigint] NOT NULL,
	[madMasivoGeneral] [bigint] NOT NULL,
	[madTipoCotizante] [varchar](20) NULL,
	[madTipoIdentificacionCotizante] [varchar](50) NOT NULL,
	[madNumeroIdentificacionCotizante] [varchar](50) NOT NULL,
	[madConceptoPago] [varchar](100) NULL,
	[madIbc] [numeric](19, 5) NULL,
	[madSalarioBasico] [numeric](19, 5) NULL,
	[madDiasCotizados] [tinyint] NULL,
	[madAporteObligatorio] [numeric](19, 5) NULL,
	[madTarifa] [numeric](19, 5) NULL,
	[madTotalAporte] [numeric](19, 5) NULL,
	[madValorIntereses] [numeric](19, 5) NULL,
	[madHorasLaboradas] [int] NULL,
	[madDiasMora] [tinyint] NULL,
	[madNovING] [varchar](1) NULL,
	[madNovRET] [varchar](1) NULL,
	[madNovIRL] [varchar](1) NULL,
	[madNovVSP] [varchar](1) NULL,
	[madNovVST] [varchar](1) NULL,
	[madNovSLN] [varchar](1) NULL,
	[madNovIGE] [varchar](1) NULL,
	[madNovLMA] [varchar](1) NULL,
	[madNovVACLR] [varchar](1) NULL,
	[extApo] [int] NULL,
	[extAporLisBlan] [int] NULL,
	[extCot] [int] NULL,
	[cantNov] [int] NULL,
	[idParaJoinRegistroGeneral] [bigint] NULL,
	[regIdRegistroGeneral] [bigint] NULL,
	[regIdTransaccion] [bigint] NULL
);
end

if not exists (select * from information_schema.tables  where table_name = 'solicitudAporteMasivo')
begin
create table [masivos].[solicitudAporteMasivo](
	[magTipoAportante] [varchar](50) NULL,
	[magRazonSocial] [varchar](150) NULL,
	[magTipoIdentificacionAportante] [varchar](30) NULL,
	[magNumeroIdentificacionAportante] [varchar](30) NULL,
	[magPeriodoPago] [varchar](7) NULL,
	[magFechaPago] [date] NULL,
	[maaNumeroRadicacion] [varchar](50) NULL,
	[maaSolId] [bigint] NULL,
	[maaRegistroGeneral] [bigint] NULL
);
end