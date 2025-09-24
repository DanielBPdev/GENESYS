-- =============================================
-- Author:		María Cuéllar - Eprocess
-- Create date: 2022/07/10
-- Description:	SP para generar los registros correspondientes a las liquidaciones masivas
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[sp_consultar_liquidaciones_masivas]
@entidadesReceptoras varchar(200),
@idEntidad varchar(200),
@periodoRegular varchar(200),
@fechaInicial varchar(200),
@fechaFinal varchar(200),
@numeroOperacion varchar(200),
@id_tipo_entidad int
AS
BEGIN
	declare @entidadJudicial bit =0;
IF @periodoRegular <> ''
BEGIN
	SET @periodoRegular = concat(@periodoRegular,'-01')
END	
IF @idEntidad = 12
BEGIN
	SET @entidadJudicial = 1
END	
IF @id_tipo_entidad = 1--Bancos
BEGIN
	SELECT
	Acumulados.solNumeroRadicacion,
	'Liquidación Masiva' as tipoLiquidacion,
	-- priPeriodo,
	slsFechaInicio as fecha_liquidacion,
	CONVERT(numeric(10,0), Acumulados.totalLiquidar)  as monto_liquidacion_CONFIRMAR,--confirmar
			(
	SELECT 
	COUNT(*) AS totalAdministradores
	FROM
	(
	SELECT
			COUNT(*) AS cantidadAdministradores
	FROM DetalleSubsidioAsignado
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	INNER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
	INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN AdministradorSubsidio ON CuentaAdministradorSubsidio.casAdministradorSubsidio = AdministradorSubsidio.asuId
	INNER JOIN Persona ON AdministradorSubsidio.asuPersona = Persona.perId
	WHERE Acumulados.solNumeroRadicacion=Solicitud.solNumeroRadicacion and 
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and (@numeroOperacion ='' or Solicitud.solNumeroRadicacion = @numeroOperacion)
	AND Banco.banCodigoPILA IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))
		AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	--AND 	(MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--No es pago judicial
	AND 
	CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'
	AND 
	PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'
	AND Solicitud.solResultadoProceso='DISPERSADA'
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion='MASIVA'
	AND (@periodoRegular ='' OR CAST(Periodo.priPeriodo AS DATE) = (CAST(@periodoRegular AS DATE))) 
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	GROUP BY
			Persona.perNumeroIdentificacion                                                                           ,
			Persona.perTipoIdentificacion                                                                             ,
			CuentaAdministradorSubsidio.casMedioDePagoTransaccion) as TotalAdministradores) AS cantidad_administradores_subsidios,
	Acumulados.cantidadCuotas as numero_cuotas_dispersar,
	0 as bancos
	--Acumulados.montoDispersar ,
	--Acumulados.totalDescuentos,

	FROM
	(
	SELECT
	Solicitud.solNumeroRadicacion as solNumeroRadicacion,
	-- periodo.priPeriodo,
	SolicitudLiquidacionSubsidio.slsFechaInicio,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorSubsidioMonetario), 0) AS montoDispersar ,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorDescuento), 0)         AS totalDescuentos,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorOriginalAbonado), 0)   AS totalLiquidar  ,
	COUNT(*)                                                            AS cantidadCuotas
	FROM DetalleSubsidioAsignado
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	INNER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
	INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	WHERE
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and (@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	AND Banco.banCodigoPILA IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))--(Estos son los bancos receptores asociados al banco emisor)
		AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	--AND     	(MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--Depende si es o no judicial
	AND 
	CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND 
	PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso='DISPERSADA'--fijo
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion='MASIVA'--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST(@periodoRegular AS DATE))) --Depende del filtro de periodo
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	group by Solicitud.solNumeroRadicacion/*,periodo.priPeriodo*/,SolicitudLiquidacionSubsidio.slsFechaInicio
	) AS Acumulados
END
ELSE--Efectivo
BEGIN
	SELECT
	Acumulados.solNumeroRadicacion,
	'Liquidación Masiva' as tipoLiquidacion,
	-- priPeriodo,
	slsFechaInicio as fecha_liquidacion,
	CONVERT(numeric(10,0), Acumulados.totalLiquidar)  as monto_liquidacion_CONFIRMAR,--confirmar
			(
	SELECT 
	COUNT(*) AS totalAdministradores
	FROM
	(
	SELECT
			COUNT(*) AS cantidadAdministradores
	FROM DetalleSubsidioAsignado
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	LEFT JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId 
	--INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN AdministradorSubsidio ON CuentaAdministradorSubsidio.casAdministradorSubsidio = AdministradorSubsidio.asuId
	INNER JOIN Persona ON AdministradorSubsidio.asuPersona = Persona.perId
	WHERE Acumulados.solNumeroRadicacion = Solicitud.solNumeroRadicacion and
	MedioTransferencia.mdpId IS NULL AND 
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and (@numeroOperacion ='' or Solicitud.solNumeroRadicacion = @numeroOperacion)
	--AND Banco.banCodigoPILA IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))
	--AND (MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--No es pago judicial
	AND 
	CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'
	AND 
	PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'
	AND Solicitud.solResultadoProceso='DISPERSADA'
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion='MASIVA'
	AND (@periodoRegular ='' OR CAST(Periodo.priPeriodo AS DATE) = (CAST(@periodoRegular AS DATE))) 
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	GROUP BY
			Persona.perNumeroIdentificacion                                                                           ,
			Persona.perTipoIdentificacion                                                                             ,
			CuentaAdministradorSubsidio.casMedioDePagoTransaccion)  as TotalAdministradores) AS cantidad_administradores_subsidios,
	Acumulados.cantidadCuotas as numero_cuotas_dispersar,
	0 as bancos
	--Acumulados.montoDispersar ,
	--Acumulados.totalDescuentos,
	FROM
	(
	SELECT
	Solicitud.solNumeroRadicacion as solNumeroRadicacion,
	-- periodo.priPeriodo,
	SolicitudLiquidacionSubsidio.slsFechaInicio,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorSubsidioMonetario), 0) AS montoDispersar ,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorDescuento), 0)         AS totalDescuentos,
	COALESCE(SUM(DetalleSubsidioAsignado.dsaValorOriginalAbonado), 0)   AS totalLiquidar  ,
	COUNT(*)                                                            AS cantidadCuotas
	FROM DetalleSubsidioAsignado
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	LEFT JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId 
	--INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	WHERE
	MedioTransferencia.mdpId IS NULL AND 
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and (@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	AND 
	CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND 
	PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso='DISPERSADA'--fijo
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion='MASIVA'--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST(@periodoRegular AS DATE))) --Depende del filtro de periodo
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	group by Solicitud.solNumeroRadicacion/*,periodo.priPeriodo*/,SolicitudLiquidacionSubsidio.slsFechaInicio
	) AS Acumulados	
END

END
