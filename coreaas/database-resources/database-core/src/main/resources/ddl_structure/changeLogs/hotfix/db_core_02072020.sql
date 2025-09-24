--liquibase formatted sql

--changeset jocampo:01
--comment: creacion de tablas externas 
DROP EXTERNAL TABLE [pila].[TemCotizante]
    
CREATE EXTERNAL TABLE [pila].[TemCotizante](
	[tctId] [bigint] NOT NULL,
	[tctIdTransaccion] [bigint] NOT NULL,
	[tctTipoCotizante] [varchar](50) NULL,
	[tctCodSucursal] [varchar](10) NULL,
	[tctNomSucursal] [varchar](100) NULL,
	[tctCodSucursalPILA] [varchar](10) NULL,
	[tctNomSucursalPILA] [varchar](40) NULL,
	[tctTipoIdCotizante] [varchar](20) NULL,
	[tctIdCotizante] [varchar](16) NULL,
	[tctPrimerApellido] [varchar](20) NULL,
	[tctSegundoApellido] [varchar](30) NULL,
	[tctPrimerNombre] [varchar](20) NULL,
	[tctSegundoNombre] [varchar](30) NULL,
	[tctCodigoDepartamento] [varchar](2) NULL,
	[tctCodigoMunicipio] [varchar](6) NULL,
	[tctTipoIdEmpleador] [varchar](20) NULL,
	[tctIdEmpleador] [varchar](16) NULL,
	[tctFechaHoraSolicitud] [datetime] NULL,
	[tctMarcaCreacion] [varchar](100) NULL,
	[tctTipoSolicitud] [varchar](100) NULL,
	[tctEsFallecido] [bit] NULL,
	[tctEsTrabajadorReintegrable] [bit] NULL,
	[tctGrupoFamiliarReintegrable] [bit] NULL,
)
WITH
(
 DATA_SOURCE = PilaReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'TemCotizante'
);