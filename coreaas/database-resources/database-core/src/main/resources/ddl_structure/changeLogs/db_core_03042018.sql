--liquibase formatted sql

--changeset borozco:01
--comment:Actualizacion tabla ParametrizacionEjecucionProgramada
UPDATE ParametrizacionEjecucionProgramada SET pepProceso='FINALIZACION_CICLOS_VENCIDOS' WHERE pepProceso='FINALIZACION_CICLO_FISCALIZACION';

--changeset alquintero:02
--comment:Actualizacion tablas RecursoComplementario, datotemporalsolicitud y solicitudlegalizacionDesembolso
UPDATE RecursoComplementario SET recNombre = 'CESANTIAS_NO_INMOVILIZADAS' WHERE recNombre = 'CESANTIAS_NO_INMOBILIZADAS';
UPDATE DatoTemporalSolicitud SET dtsJsonPayload = REPLACE ( CAST(dtsJsonPayload AS nvarchar(max)) , 'CESANTIAS_NO_INMOBILIZADAS' , 'CESANTIAS_NO_INMOVILIZADAS' ) WHERE dtsJsonPayload LIKE '%CESANTIAS_NO_INMOBILIZADAS%';
UPDATE SolicitudLegalizacionDesembolso SET sldjsonPostulacion = REPLACE ( CAST(sldjsonPostulacion AS nvarchar(max)) , 'CESANTIAS_NO_INMOBILIZADAS' , 'CESANTIAS_NO_INMOVILIZADAS' ) WHERE sldjsonPostulacion LIKE '%CESANTIAS_NO_INMOBILIZADAS%';

--changeset jzambrano:03
--comment:Actualizacion tablas RecursoComplementario, datotemporalsolicitud y solicitudlegalizacionDesembolso
UPDATE ValidacionProceso SET vapObjetoValidacion = 'TRABAJADOR_DEPENDIENTE' WHERE vapBloque LIKE '122-361-%'

--changeset rlopez:04
--comment: Creacion tabla DetalleSubsidioAsignadoProgramado
CREATE TABLE [DetalleSubsidioAsignadoProgramado](
	[dprId] [bigint] IDENTITY(1,1) NOT NULL,
	[dprUsuarioCreador] [varchar](200) NOT NULL,
	[dprFechaHoraCreacion] [datetime] NOT NULL,
	[dprEstado] [varchar](20) NOT NULL,
	[dprMotivoAnulacion] [varchar](40) NULL,
	[dprDetalleAnulacion] [varchar](250) NULL,
	[dprOrigenRegistroSubsidio] [varchar](30) NOT NULL,
	[dprTipoliquidacionSubsidio] [varchar](60) NOT NULL,
	[dprTipoCuotaSubsidio] [varchar](80) NOT NULL,
	[dprValorSubsidioMonetario] [numeric](19, 5) NOT NULL,
	[dprValorDescuento] [numeric](19, 5) NOT NULL,
	[dprValorOriginalAbonado] [numeric](19, 5) NOT NULL,
	[dprValorTotal] [numeric](19, 5) NOT NULL,
	[dprFechaTransaccionRetiro] [date] NULL,
	[dprUsuarioTransaccionRetiro] [varchar](200) NULL,
	[dprFechaTransaccionAnulacion] [date] NULL,
	[dprUsuarioTransaccionAnulacion] [varchar](200) NULL,
	[dprFechaHoraUltimaModificacion] [datetime] NULL,
	[dprUsuarioUltimaModificacion] [varchar](200) NULL,
	[dprSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[dprEmpleador] [bigint] NOT NULL,
	[dprAfiliadoPrincipal] [bigint] NOT NULL,
	[dprGrupoFamiliar] [bigint] NOT NULL,
	[dprAdministradorSubsidio] [bigint] NOT NULL,
	[dprIdRegistroOriginalRelacionado] [bigint] NULL,
	[dprCuentaAdministradorSubsidio] [bigint] NOT NULL,
	[dprBeneficiarioDetalle] [bigint] NOT NULL,
	[dprPeriodoLiquidado] [date] NOT NULL,
	[dprResultadoValidacionLiquidacion] [bigint] NOT NULL,
	[dprCondicionPersonaBeneficiario] [bigint] NULL,
	[dprCondicionPersonaAfiliado] [bigint] NULL,
	[dprCondicionPersonaEmpleador] [bigint] NULL,
 CONSTRAINT [PK_DetalleSubsidioAsignado_dprId] PRIMARY KEY CLUSTERED 
(
	[dprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

--changeset jocorrea:05
--comment:Actualizacion tablas RecursoComplementario, datotemporalsolicitud y solicitudlegalizacionDesembolso
UPDATE ValidacionProceso SET vapProceso = 'NOVEDADES_PERSONAS_WEB' WHERE vapBloque = 'NOVEDAD_VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_WEB';
