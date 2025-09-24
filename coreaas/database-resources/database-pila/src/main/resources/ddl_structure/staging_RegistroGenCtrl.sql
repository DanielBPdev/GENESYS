--- Tabla de control para las planillas N
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'RegistroGenCtrl' and TABLE_SCHEMA = 'staging')
begin

CREATE TABLE [staging].[RegistroGenCtrl]([regId] [bigint] IDENTITY(1,1) NOT NULL,[regTransaccion] [bigint] NULL,[regEsAportePensionados] [bit] NOT NULL,[regNombreAportante] [varchar](200) NOT NULL,
[regTipoIdentificacionAportante] [varchar](20) NOT NULL,[regNumeroIdentificacionAportante] [varchar](16) NOT NULL,[regDigVerAportante] [smallint] NULL,[regPeriodoAporte] [varchar](7) NOT NULL,
[regTipoPlanilla] [varchar](1) NULL,[regClaseAportante] [varchar](1) NULL,[regCodSucursal] [varchar](10) NULL,[regNomSucursal] [varchar](40) NULL,[regDireccion] [varchar](40) NULL,
[regCodCiudad] [varchar](3) NULL,[regCodDepartamento] [varchar](2) NULL,[regTelefono] [bigint] NULL,[regFax] [bigint] NULL,[regEmail] [varchar](60) NULL,[regFechaMatricula] [date] NULL,
[regNaturalezaJuridica] [smallint] NULL,[regCantPensionados] [int] NULL,[regModalidadPlanilla] [smallint] NULL,[regValTotalApoObligatorio] [numeric](19, 5) NULL,[regValorIntMora] [numeric](19, 5) NULL,
[regFechaRecaudo] [date] NULL,[regCodigoEntidadFinanciera] [smallint] NULL,[regOperadorInformacion] [bigint] NULL,[regNumeroCuenta] [varchar](17) NULL,[regFechaActualizacion] [date] NULL,
[regRegistroControl] [bigint] NULL,[regRegistroControlManual] [bigint] NULL,[regRegistroFControl] [bigint] NULL,[regOUTTarifaEmpleador] [numeric](5, 5) NULL,[regOUTFinalizadoProcesoManual] [bit] NULL,
[regOUTEsEmpleador] [bit] NULL,[regOUTEstadoEmpleador] [varchar](50) NULL,[regOUTTipoBeneficio] [varchar](10) NULL,[regOUTBeneficioActivo] [bit] NULL,[regOUTEsEmpleadorReintegrable] [bit] NULL,
[regOUTEstadoArchivo] [varchar](60) NULL,[regNumPlanilla] [varchar](10) NULL,[regNovedadFutura] [bit] NULL,[regOUTTarifaBaseEmpleador] [numeric](5, 5) NULL,[regOUTSMMLV] [numeric](19, 5) NULL,[regEsSimulado] [bit] NOT NULL,
[regEstadoEvaluacion] [varchar](22) NOT NULL,[regOUTMarcaSucursalPILA] [bit] NULL,[regOUTCodSucursalPrincipal] [varchar](10) NULL,[regOUTNomSucursalPrincipal] [varchar](100) NULL,[regOUTEnviadoAFiscalizacion] [bit] NULL,
[regOUTMotivoFiscalizacion] [varchar](30) NULL,[regOUTNovedadFuturaProcesada] [bit] NULL,[regOUTMotivoProcesoManual] [varchar](20) NULL,[regNumPlanillaAsociada] [varchar](10) NULL,[regOUTPrimerNombreAportante] [varchar](20) NULL,
[regOUTSegundoNombreAportante] [varchar](30) NULL,[regOUTPrimerApellidoAportante] [varchar](20) NULL,[regOUTSegundoApellidoAportante] [varchar](30) NULL,[regDiasMora] [smallint] NULL,
[regFechaPagoAporte] [date] NULL,[regFormaPresentacion] [varchar](1) NULL,[regCantidadEmpleados] [int] NULL,[regCantidadAfiliados] [int] NULL,[regTipoPersona] [varchar](1) NULL,[regOUTEnProceso] [bit] NULL,
[regCantidadReg2] [int] NULL,[regFechaPagoPlanillaAsociada] [date] NULL,[regOUTRegistroActual] [bit] NULL,[regOUTReginicial] [bigint] NULL,[regDateTimeInsert] [datetime] NULL,
[regDateTimeUpdate] [datetime] NULL,[regCuentaBancariaRecaudo] [int] NULL)

create unique nonclustered index IXNC_regTransaccion_ctrl on staging.RegistroGenCtrl (regTransaccion) where regTransaccion is not null -- Evitar duplicados en la transacción. 
create unique nonclustered index IXNC_regRegistroControl_ctrl on staging.RegistroGenCtrl (regRegistroControl) where regRegistroControl is not null -- Evitar duplicados en la relación del id planilla

end