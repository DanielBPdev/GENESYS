--liquibase formatted sql

--changeset jocampo:01
--comment: creacion de tablas externas 
CREATE SCHEMA core
GO

CREATE EXTERNAL TABLE [core].[Persona](
 	[perId] [bigint] NOT NULL,
	[perNumeroIdentificacion] [varchar](16) NULL,
	[perTipoIdentificacion] [varchar](20) NULL,
	[perDigitoVerificacion] [smallint] NULL,
	[perRazonSocial] [varchar](250) NULL,
	[perUbicacionPrincipal] [bigint] NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perSegundoNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[perSegundoApellido] [varchar](50) NULL,
	[perCreadoPorPila] [bit] NULL
)
WITH
(
 DATA_SOURCE = CoreReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'Persona'
);

CREATE EXTERNAL TABLE [core].[Empresa](
	[empId] [bigint] NOT NULL,
	[empPersona] [bigint] NULL,
	[empNombreComercial] [varchar](250) NULL,
	[empFechaConstitucion] [date] NULL,
	[empNaturalezaJuridica] [varchar](100) NULL,
	[empCodigoCIIU] [smallint] NULL,
	[empArl] [smallint] NULL,
	[empUltimaCajaCompensacion] [int] NULL,
	[empPaginaWeb] [varchar](256) NULL,
	[empRepresentanteLegal] [bigint] NULL,
	[empRepresentanteLegalSuplente] [bigint] NULL,
	[empEspecialRevision] [bit] NULL,
	[empUbicacionRepresentanteLegal] [bigint] NULL,
	[empUbicacionRepresentanteLegalSuplente] [bigint] NULL,
	[empCreadoPorPila] [bit] NULL,
	[empEnviadoAFiscalizacion] [bit] NULL,
	[empMotivoFiscalizacion] [varchar](30) NULL,
	[empFechaFiscalizacion] [date] NULL
)
WITH
(
 DATA_SOURCE = CoreReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'Empresa'
);

CREATE EXTERNAL TABLE [core].[Empleador](
	[empId] [bigint] NOT NULL,
	[empEmpresa] [bigint] NULL,
	[empEstadoEmpleador] [varchar](8) NULL,
	[empExpulsionSubsanada] [bit] NULL,
	[empFechaCambioEstadoAfiliacion] [datetime2](7) NULL,
	[empMotivoDesafiliacion] [varchar](100) NULL,
	[empNumeroTotalTrabajadores] [int] NULL,
	[empPeriodoUltimaNomina] [date] NULL,
	[empValorTotalUltimaNomina] [numeric](19, 0) NULL,
	[empFechaRetiro] [datetime] NULL,
	[empFechaSubsanacionExpulsion] [date] NULL,
	[empMotivoSubsanacionExpulsion] [varchar](200) NULL,
	[empCantIngresoBandejaCeroTrabajadores] [smallint] NULL,
	[empFechaRetiroTotalTrabajadores] [date] NULL,
	[empFechaGestionDesafiliacion] [date] NULL,
	[empMedioPagoSubsidioMonetario] [varchar](30) NULL,
	[empValidarSucursalPila] [bit] NULL,
	[empDiaHabilVencimientoAporte] [smallint] NULL,
	[empMarcaExpulsion] [varchar](22) NULL,
	[empRetencionSubsidioActiva] [bit] NULL,
	[empMotivoRetencionSubsidio] [varchar](24) NULL,
	[empMotivoInactivaRetencionSubsidio] [varchar](26) NULL,
	[empCanalReingreso] [varchar](21) NULL,
	[empReferenciaAporteReingreso] [bigint] NULL,
)
WITH
(
 DATA_SOURCE = CoreReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'Empleador'
);

