
-- =============================================
-- Author:		Diego Fajardo
-- Create date: 2023/09/13
-- Description:	Obtener la decha de pago programada
-- en los parametros de liquidacion
-- =============================================

CREATE  PROCEDURE [dbo].[USP_SM_GET_LiquidacionesFallecimientoProgramado]
(
	@sNumeroRadicado VARCHAR(23),
	@tipoIdentificacion VARCHAR(100),
	@numeroIdentificacion VARCHAR(10), 
	@medioPago VARCHAR(255), 
	@fechaInicio VARCHAR(20), 
	@fechaFin VARCHAR(20), 
	@periodoRegular VARCHAR(20),
    @fechaProgramadaPago VARCHAR(20) 

)
AS
	
	SET NOCOUNT ON;

	declare @Periodo table (pcfPeriodo date,pcfResultadoValidacionLiquidacion bigint,pcfFechaProgramadaPago date, origen varchar(200))
	insert @Periodo
	execute sp_execute_remote SubsidioReferenceData, N'select pcfPeriodo, pcfResultadoValidacionLiquidacion, pcfFechaProgramadaPago
	from ProyeccionCuotaFallecimiento'

	select  so.solNumeroRadicacion,
	so.solFechaRadicacion,
	padim.perTipoIdentificacion,
	padim.perNumeroIdentificacion,
	padim.perRazonSocial,
	dsa.dsaPeriodoLiquidado,
	dsa.dsaValorSubsidioMonetario,
	dsa.dsaValorDescuento,
	dsa.dsaValorTotal,
	p.pcfFechaProgramadaPago,
	cas.casMedioDePagoTransaccion

	from CuentaAdministradorSubsidio cas
	INNER JOIN SolicitudLiquidacionSubsidio sls ON sls.slsId = cas.casSolicitudLiquidacionSubsidio
	INNER JOIN Solicitud so ON so.solid = sls.slsSolicitudGlobal
	INNER JOIN DetalleSubsidioAsignado dsa ON dsa.dsaCuentaAdministradorSubsidio = cas.casid
	INNER JOIN @Periodo p ON dsa.dsaResultadoValidacionLiquidacion = p.pcfResultadoValidacionLiquidacion AND dsa.dsaPeriodoLiquidado = p.pcfPeriodo
	INNER JOIN AdministradorSubsidio asu ON asu.asuId = dsa.dsaAdministradorSubsidio
	INNER JOIN Persona padim ON asu.asuPersona = padim.perId
	INNER JOIN Afiliado ON afiId = dsaAfiliadoPrincipal
	INNER JOIN Persona pafil ON pafil.perId = afiPersona
	WHERE 
	(@tipoIdentificacion = '' OR pafil.perTipoIdentificacion = @tipoIdentificacion)
	AND (@numeroIdentificacion = '' OR pafil.perNumeroIdentificacion = @numeroIdentificacion)
	AND (@sNumeroRadicado = '' OR so.solNumeroRadicacion = @sNumeroRadicado)
	AND (@medioPago = '' OR cas.casMedioDePagoTransaccion = @medioPago)
	AND (@fechaInicio = '' OR sls.slsFechaDispersion BETWEEN @fechaInicio AND @fechaFin)
	AND (@periodoRegular = '' OR CAST(dsa.dsaPeriodoLiquidado AS DATE)=(CAST(@periodoRegular AS DATE)))
    AND (@fechaProgramadaPago = '' OR CAST(p.pcfFechaProgramadaPago AS DATE)=(CAST(@fechaProgramadaPago AS DATE)))
    AND slsEstadoLiquidacion='CERRADA'
    AND solResultadoProceso='DISPERSADA'
    AND slsTipoLiquidacion='SUBSUDIO_DE_DEFUNCION'
	AND convert(date,dbo.GetLocalDate()) >= p.pcfFechaProgramadaPago
