-- =============================================
-- Author:		María Cuéllar - Eprocess
-- Create date: 2022/07/10
-- Description:	SP para generar los registros correspondientes a las liquidaciones especificas
-- =============================================

CREATE OR ALTER PROCEDURE [dbo].[sp_consultar_liquidaciones_especificas]
@entidadesReceptoras varchar(200),
@idEntidad varchar(200),
@tipoLiquidacionEspecifica varchar(200),
@tipoAjuste varchar(200),
@tipoIdentificacion varchar(200),
@numeroIdentificacion varchar(200),
@periodoRegular varchar(200),
@fechaInicial varchar(200),
@fechaFinal varchar(200),
@numeroOperacion varchar(200),
@id_tipo_entidad int

AS
BEGIN
/*
IF @periodoRegular <> ''
BEGIN
	SET @periodoRegular = concat(@periodoRegular,'-01')
END
*/
/*
IF @tipoLiquidacionEspecifica = 'SUBSUDIO_DE_DEFUNCION'
BEGIN
	SET @periodoRegular = concat(@periodoRegular,'-01')
	slsTipoDesembolso = UNA_CUOTA
END
*/
declare @entidadJudicial bit =0;
		IF @idEntidad = 12
		BEGIN
			SET @entidadJudicial = 1
		END	
IF @id_tipo_entidad = 1--Bancos
BEGIN
	SELECT
	Acumulados.solNumeroRadicacion,
	'Liquidación Específica' as tipoLiquidacion,
	-- priPeriodo,
	slsFechaInicio as fecha_liquidacion,
	CONVERT(numeric(10,0), Acumulados.totalLiquidar) as monto_liquidacion_CONFIRMAR,--confirmar
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
	WHERE Acumulados.solNumeroRadicacion=Solicitud.solNumeroRadicacion
	--Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	AND SolicitudLiquidacionSubsidio.slsTipoLiquidacion <> 'MASIVA'
	AND	(@tipoLiquidacionEspecifica = '' or SolicitudLiquidacionSubsidio.slsTipoLiquidacion= case @tipoLiquidacionEspecifica when 1 then 'AJUSTES_DE_CUOTA' when 2 then 'RECONOCIMIENTO_DE_SUBSIDIOS' 
	when 3 then 'SUBSUDIO_DE_DEFUNCION' end)--revisar esto
	AND (@tipoAjuste ='' OR SolicitudLiquidacionSubsidio.slsTipoLiquidacionEspecifica = 
	case @tipoAjuste when 1 then 'COMPLEMENTAR_CUOTA' when 2 then 'RECONOCER_CUOTA_AGRARIA' 
	when 3 then 'RECONOCER_DISCAPACIDAD' when 4 then 'VALOR_CUOTA' end
	) 
	AND 
	(@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	AND Banco.banId IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))--(Estos son los bancos receptores asociados al banco emisor)
	--AND     (MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--Depende si es o no judicial
	AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso IN ('DISPERSADA','RECHAZADA_PRIMER_NIVEL','CANCELADA')	--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST((@periodoRegular) AS DATE))) --Depende del filtro de periodo
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
select 
a.solNumeroRadicacion,
a.slsFechaInicio,
COALESCE(SUM(a.montoDispersar), 0) AS montoDispersar,
COALESCE(SUM(a.totalDescuentos), 0) AS totalDescuentos,
COALESCE(SUM(a.totalLiquidar), 0) AS totalLiquidar,
sum(a.cantidadCuotas) as cantidadCuotas
from (
	SELECT  
	Solicitud.solNumeroRadicacion as solNumeroRadicacion,
	SolicitudLiquidacionSubsidio.slsFechaInicio,
	DetalleSubsidioAsignado.dsaValorSubsidioMonetario AS montoDispersar ,
	DetalleSubsidioAsignado.dsaValorDescuento        AS totalDescuentos,
	DetalleSubsidioAsignado.dsaValorOriginalAbonado  AS totalLiquidar  ,
	count(distinct dsaid) as cantidadCuotas
	FROM DetalleSubsidioAsignado
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	INNER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
	INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	WHERE
	SolicitudLiquidacionSubsidio.slsTipoLiquidacion <> 'MASIVA'
	--AND Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	AND (@tipoLiquidacionEspecifica = '' or SolicitudLiquidacionSubsidio.slsTipoLiquidacion= case @tipoLiquidacionEspecifica when 1 then 'AJUSTES_DE_CUOTA' when 2 then 'RECONOCIMIENTO_DE_SUBSIDIOS' 
	when 3 then 'SUBSUDIO_DE_DEFUNCION' end)--revisar esto
	AND (@tipoAjuste ='' OR SolicitudLiquidacionSubsidio.slsTipoLiquidacionEspecifica = 
	case @tipoAjuste when 1 then 'COMPLEMENTAR_CUOTA' when 2 then 'RECONOCER_CUOTA_AGRARIA' 
	when 3 then 'RECONOCER_DISCAPACIDAD' when 4 then 'VALOR_CUOTA' end
	) 
	AND 
	(@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	AND Banco.banCodigoPILA IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))--(Estos son los bancos receptores asociados al banco emisor)
	--AND     (MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--Depende si es o no judicial
	AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso IN ('DISPERSADA','RECHAZADA_PRIMER_NIVEL','CANCELADA')	--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST((@periodoRegular) AS DATE))) --Depende del filtro de periodo
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	AND (
    @tipoLiquidacionEspecifica != 3
    OR (
        @tipoLiquidacionEspecifica = 3
        AND DetalleSubsidioAsignado.dsaFechaHoraUltimaModificacion >= (
            SELECT COALESCE(rpb.RADI_FECH, dsa.dsaFechaProgramadaPago)
            FROM Solicitud s
            OUTER APPLY (
                SELECT MAX(RADI_FECH) as RADI_FECH 
                FROM RADICACIONES_PAGOS_BANCOS 
                WHERE RADI_RADI = s.solNumeroRadicacion
            ) rpb
            OUTER APPLY (
                SELECT TOP 1 CAST(MIN(dsaFechaHoraUltimaModificacion) as date) as dsaFechaProgramadaPago 
                FROM Solicitud 
                INNER JOIN SolicitudLiquidacionSubsidio ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
                INNER JOIN DetalleSubsidioAsignado ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
                WHERE Solicitud.solNumeroRadicacion = s.solNumeroRadicacion
            ) dsa
            WHERE s.solNumeroRadicacion = Solicitud.solNumeroRadicacion
        )
        AND CAST(DetalleSubsidioAsignado.dsaFechaHoraUltimaModificacion AS DATE) <= CAST(GETDATE() AS DATE)
        AND NOT EXISTS (
            SELECT 1 
			FROM RADICACIONES_PAGOS_BANCOS rpb2
			WHERE rpb2.RADI_RADI = Solicitud.solNumeroRadicacion
			AND CAST(rpb2.RADI_FECH AS DATE) >= CAST(DetalleSubsidioAsignado.dsaFechaHoraUltimaModificacion AS DATE)
		)
    )
)



	group by Solicitud.solNumeroRadicacion,SolicitudLiquidacionSubsidio.slsFechaInicio,dsaId,DetalleSubsidioAsignado.dsaValorSubsidioMonetario,
	DetalleSubsidioAsignado.dsaValorDescuento  ,
	DetalleSubsidioAsignado.dsaValorOriginalAbonado
	
	) as a
	group by a.solNumeroRadicacion,a.slsFechaInicio
	) AS Acumulados
	where Acumulados.totalLiquidar >0
END
ELSE 
BEGIN
	SELECT
	Acumulados.solNumeroRadicacion,
	'Liquidación Específica' as tipoLiquidacion,
	-- priPeriodo,
	slsFechaInicio as fecha_liquidacion,
	CONVERT(numeric(10,0), Acumulados.totalLiquidar) as monto_liquidacion_CONFIRMAR,--confirmar
			(
	SELECT 
	COUNT(*) AS totalAdministradores
	FROM
	(
	SELECT
			COUNT(*) AS cantidadAdministradores
	FROM DetalleSubsidioAsignado
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	LEFT OUTER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
	--INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN AdministradorSubsidio ON CuentaAdministradorSubsidio.casAdministradorSubsidio = AdministradorSubsidio.asuId
	INNER JOIN Persona ON AdministradorSubsidio.asuPersona = Persona.perId
	WHERE Acumulados.solNumeroRadicacion=Solicitud.solNumeroRadicacion and 
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion <> 'MASIVA'
	AND	MedioTransferencia.mdpId IS NULL AND
	(@tipoLiquidacionEspecifica = '' or SolicitudLiquidacionSubsidio.slsTipoLiquidacion= case @tipoLiquidacionEspecifica when 1 then 'AJUSTES_DE_CUOTA' when 2 then 'RECONOCIMIENTO_DE_SUBSIDIOS' 
	when 3 then 'SUBSUDIO_DE_DEFUNCION' end)--revisar esto
	AND (@tipoAjuste ='' OR SolicitudLiquidacionSubsidio.slsTipoLiquidacionEspecifica = 
	case @tipoAjuste when 1 then 'COMPLEMENTAR_CUOTA' when 2 then 'RECONOCER_CUOTA_AGRARIA' 
	when 3 then 'RECONOCER_DISCAPACIDAD' when 4 then 'VALOR_CUOTA' end
	) 
	AND 
	(@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	--AND Banco.banId IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))--(Estos son los bancos receptores asociados al banco emisor)
	--AND     (MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--Depende si es o no judicial
	AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso IN ('DISPERSADA','RECHAZADA_PRIMER_NIVEL','CANCELADA')	--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST((@periodoRegular) AS DATE))) --Depende del filtro de periodo
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	GROUP BY
			Persona.perNumeroIdentificacion                                                                           ,
			Persona.perTipoIdentificacion                                                                             ,
			CuentaAdministradorSubsidio.casMedioDePagoTransaccion) as cantidad_administradores_subsidios) AS TotalAdministradores,
	Acumulados.cantidadCuotas as numero_cuotas_dispersar,
	0 as bancos
	--Acumulados.montoDispersar ,
	--Acumulados.totalDescuentos,

	FROM
	(
select 
a.solNumeroRadicacion,
a.slsFechaInicio,
COALESCE(SUM(a.montoDispersar), 0) AS montoDispersar,
COALESCE(SUM(a.totalDescuentos), 0) AS totalDescuentos,
COALESCE(SUM(a.totalLiquidar), 0) AS totalLiquidar,
sum(a.cantidadCuotas) as cantidadCuotas
from (
	SELECT  
	Solicitud.solNumeroRadicacion as solNumeroRadicacion,
	SolicitudLiquidacionSubsidio.slsFechaInicio,
	DetalleSubsidioAsignado.dsaValorSubsidioMonetario AS montoDispersar ,
	DetalleSubsidioAsignado.dsaValorDescuento        AS totalDescuentos,
	DetalleSubsidioAsignado.dsaValorOriginalAbonado  AS totalLiquidar  ,
	count(distinct dsaid) as cantidadCuotas
	FROM DetalleSubsidioAsignado
	INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
	INNER JOIN PeriodoLiquidacion ON PeriodoLiquidacion.pelSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId 
	INNER JOIN Periodo ON Periodo.priId = PeriodoLiquidacion.pelPeriodo 
	INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
	INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
	LEFT OUTER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
	--INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
	WHERE
	Solicitud.solNumeroRadicacion not in(select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)--para que quite las liquidaciones ya aprobadas en pagos bancos.
	and SolicitudLiquidacionSubsidio.slsTipoLiquidacion <> 'MASIVA'
	AND MedioTransferencia.mdpId IS NULL AND
	(@tipoLiquidacionEspecifica = '' or SolicitudLiquidacionSubsidio.slsTipoLiquidacion= case @tipoLiquidacionEspecifica when 1 then 'AJUSTES_DE_CUOTA' when 2 then 'RECONOCIMIENTO_DE_SUBSIDIOS' 
	when 3 then 'SUBSUDIO_DE_DEFUNCION' end)--revisar esto
	AND (@tipoAjuste ='' OR SolicitudLiquidacionSubsidio.slsTipoLiquidacionEspecifica = 
	case @tipoAjuste when 1 then 'COMPLEMENTAR_CUOTA' when 2 then 'RECONOCER_CUOTA_AGRARIA' 
	when 3 then 'RECONOCER_DISCAPACIDAD' when 4 then 'VALOR_CUOTA' end
	) 
	AND 
	(@numeroOperacion = '' or Solicitud.solNumeroRadicacion = @numeroOperacion)--opcional
	--AND Banco.banId IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))--(Estos son los bancos receptores asociados al banco emisor)
	--AND     (MedioTransferencia.metCobroJudicial IS NULL OR      MedioTransferencia.metCobroJudicial       =0)--Depende si es o no judicial
	AND ISNULL(MedioTransferencia.metCobroJudicial,0)  = @entidadJudicial
	AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'--fijo
	AND PeriodoLiquidacion.pelTipoPeriodo = 'REGULAR'--fijo
	AND Solicitud.solResultadoProceso IN ('DISPERSADA','RECHAZADA_PRIMER_NIVEL','CANCELADA')	--fijo
	AND (@periodoRegular = '' OR CAST(Periodo.priPeriodo AS DATE) = (CAST((@periodoRegular) AS DATE))) --Depende del filtro de periodo
	AND convert(date,SolicitudLiquidacionSubsidio.slsFechaInicio) BETWEEN @fechaInicial AND @fechaFinal--Obligatorio y se recibe desde el front
	group by Solicitud.solNumeroRadicacion,SolicitudLiquidacionSubsidio.slsFechaInicio,dsaId,DetalleSubsidioAsignado.dsaValorSubsidioMonetario,
	DetalleSubsidioAsignado.dsaValorDescuento  ,
	DetalleSubsidioAsignado.dsaValorOriginalAbonado
	
	) as a
	group by a.solNumeroRadicacion,a.slsFechaInicio
	) AS Acumulados
	where Acumulados.totalLiquidar >0
END

END
