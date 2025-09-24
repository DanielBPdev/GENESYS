CREATE OR ALTER proc [dbo].[SP_ActualizarEstadisticasGenesys]
AS
BEGIN
SET NOCOUNT ON;

CREATE TABLE #DatosFinalesDashboard (
    Periodo VARCHAR(7),
    Cantidad BIGINT,
    Proceso VARCHAR (5),
	origen VARCHAR(200)
);

INSERT INTO #DatosFinalesDashboard (Periodo, Cantidad, Proceso, origen)
exec sp_execute_remote subsidioreferencedata, N'
	SELECT
		FORMAT(DATEADD(MONTH, -5, GETDATE()), ''yyyy-MM'') AS Periodo,
		ISNULL(COUNT(*), 0) AS Cantidad,
		''1S'' AS Proceso
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -5, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -4, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''2S''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -4, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -3, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''3S''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -3, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -2, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''4S''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -2, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -1, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''5S''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -1, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
		UNION ALL
	SELECT
		FORMAT( GETDATE(), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''6S''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT( GETDATE(), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_NO_APROBADO''
		UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -5, GETDATE()), ''yyyy-MM'') AS Periodo,
		ISNULL(COUNT(*), 0) AS Cantidad,
		''1C'' AS Proceso
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -5, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -4, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''2C''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -4, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -3, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''3C''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -3, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -2, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''4C''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -2, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO''
	UNION ALL
	SELECT
		FORMAT(DATEADD(MONTH, -1, GETDATE()), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
		''5C''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT(DATEADD(MONTH, -1, GETDATE()), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO''
		UNION ALL
	SELECT
		FORMAT( GETDATE(), ''yyyy-MM''),
		ISNULL(COUNT(*), 0),
	   ''6C''
	FROM solicitudliquidacion
		INNER JOIN ResultadoValidacionLiquidacion
		ON sllnumeroradicacion = rvlnumeroradicado
	WHERE
		FORMAT(sllFechaInicio, ''yyyy-MM'') = FORMAT( GETDATE(), ''yyyy-MM'')
		AND rvlEstadoDerechoLiquidacion = ''DERECHO_ASIGNADO'''

INSERT INTO #DatosFinalesDashboard (Periodo, Cantidad, Proceso)
	SELECT FORMAT(DATEADD(MONTH, -5, GETDATE()), 'yyyy-MM') AS Periodo ,
		ISNULL(COUNT(*), 0) AS Cantidad,
	   '1REL' AS Proceso
	   FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -5, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -4, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '2REL'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -4, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -3, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '3REL'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -3, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -2, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '4REL'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -2, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '5REL'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(GETDATE(), 'yyyy-MM'),
		ISNULL(COUNT(*), 0),
	   '6REL'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(GETDATE(), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'RELACIONADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -5, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '1REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -5, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -4, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '2REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -4, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -3, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '3REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -3, GETDATE()),'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -2, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '4REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -2, GETDATE()), 'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM') ,
		ISNULL(COUNT(*), 0),
	   '5REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(DATEADD(MONTH, -1, GETDATE()),'yyyy-MM')
		AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT FORMAT(GETDATE(), 'yyyy-MM'),
		ISNULL(COUNT(*), 0),
	   '6REG'
	FROM AporteGeneral as apg
	WHERE apgPeriodoAporte = FORMAT(GETDATE(), 'yyyy-MM')
	AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT e.empId), 0) AS cantidad, 'EMPA' AS Proceso 
	FROM Empresa e
		INNER JOIN Empleador em on em.empEmpresa = e.empId 
	WHERE em.empEstadoEmpleador = 'ACTIVO'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT roaAfiliado), 0) AS cantidad,'AFIA' AS Proceso
	FROM RolAfiliado 
	WHERE roaEstadoAfiliado = 'ACTIVO'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT benPersona), 0) AS cantidad,'BENA' AS Proceso
	FROM Beneficiario
	WHERE benEstadoBeneficiarioAfiliado = 'ACTIVO'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT carPersona), 0) AS cantidad,'EMPM' AS Proceso
	FROM  Cartera 
	WHERE carEstadoOperacion = 'VIGENTE'
	AND carTipoSolicitante = 'EMPLEADOR'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT dsaId), 0) AS cantidad,'LSDD' AS Proceso
	FROM solicitudliquidacionsubsidio
		INNER JOIN Solicitud ON slsSolicitudGlobal = SOLID
		inner join DetalleSubsidioAsignado on slsid = dsaSolicitudLiquidacionSubsidio
	WHERE slsFechaInicio BETWEEN CAST(DATEADD(YEAR, -1, GetDate()) AS DATE) AND GetDate()
		AND solResultadoProceso IN ('DISPERSADA') AND slsTipoLiquidacion = 'SUBSUDIO_DE_DEFUNCION'
		and dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		and dsaEstado = 'DERECHO_ASIGNADO' 
		and datetrunc(MM, convert(date, slsFechaDispersion)) =  datetrunc(MM, convert(date,dbo.getlocaldate()))
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT dsaId), 0) AS cantidad,'LADC' AS Proceso
	FROM solicitudliquidacionsubsidio
		INNER JOIN Solicitud ON slsSolicitudGlobal = SOLID
		inner join DetalleSubsidioAsignado on slsid = dsaSolicitudLiquidacionSubsidio
	WHERE slsFechaInicio BETWEEN CAST(DATEADD(YEAR, -1, GetDate()) AS DATE) AND GetDate()
		AND solResultadoProceso IN ('DISPERSADA') AND slsTipoLiquidacion = 'AJUSTES_DE_CUOTA'
		and dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		and dsaEstado = 'DERECHO_ASIGNADO' 
		and datetrunc(MM, convert(date, slsFechaDispersion)) =  datetrunc(MM, convert(date,dbo.getlocaldate()))
	UNION ALL	
	SELECT NULL, ISNULL(COUNT(DISTINCT dsaId), 0) AS cantidad,'LRDS' AS Proceso
	FROM solicitudliquidacionsubsidio
		INNER JOIN Solicitud ON slsSolicitudGlobal = SOLID
		inner join DetalleSubsidioAsignado on slsid = dsaSolicitudLiquidacionSubsidio
	WHERE slsFechaInicio BETWEEN CAST(DATEADD(YEAR, -1, GetDate()) AS DATE) AND GetDate()
		AND solResultadoProceso IN ('DISPERSADA') AND slsTipoLiquidacion = 'RECONOCIMIENTO_DE_SUBSIDIOS'
		and dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		and dsaEstado = 'DERECHO_ASIGNADO' 
		and datetrunc(MM, convert(date, slsFechaDispersion)) =  datetrunc(MM, convert(date,dbo.getlocaldate()))
	UNION ALL	
	SELECT NULL, ISNULL(COUNT(DISTINCT dsaId), 0) AS cantidad,'LMAS' AS Proceso
	FROM solicitudliquidacionsubsidio
		INNER JOIN Solicitud ON slsSolicitudGlobal = SOLID
		inner join DetalleSubsidioAsignado on slsid = dsaSolicitudLiquidacionSubsidio
	WHERE slsFechaInicio BETWEEN CAST(DATEADD(YEAR, -1, GetDate()) AS DATE) AND GetDate()
		AND solResultadoProceso IN ('DISPERSADA') AND slsTipoLiquidacion = 'MASIVA'
		and dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		and dsaEstado = 'DERECHO_ASIGNADO' 
		and datetrunc(MM, convert(date, slsFechaDispersion)) =  datetrunc(MM, convert(date,dbo.getlocaldate()))
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT pofId), 0) AS cantidad,'PASI'
	FROM Postulacionfovis
		INNER JOIN SolicitudAsignacion ON safId = pofSolicitudAsignacion
		INNER JOIN Solicitud ON solId = safSolicitudGlobal
	WHERE pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA', 'ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
		AND solFechaCreacion BETWEEN  CAST(DATEADD(YEAR, -1, GETDATE()) AS DATE) AND GETDATE()
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT pofId), 0) AS cantidad,'PTOT'
	FROM Postulacionfovis
		INNER JOIN SolicitudPostulacion ON spoPostulacionFOVIS = pofId
		INNER JOIN Solicitud ON spoSolicitudGlobal = solid
	WHERE solFechaCreacion BETWEEN CAST(DATEADD(YEAR, -1, GETDATE()) AS DATE) AND GETDATE()
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT pofId), 0) AS cantidad,'PPAS'
	FROM PostulacionFOVIS
	WHERE pofEstadoHogar IN ('HABIL', 'HABIL_SEGUNDO_ANIO')
	UNION ALL
	SELECT NULL,ISNULL( SUM(safValorSFVAsignado),0) AS cantidad,'VASI'
	FROM SolicitudAsignacion
		INNER JOIN Solicitud ON solId = safSolicitudGlobal
	WHERE solResultadoProceso = 'APROBADA'AND safFechaAceptacion BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
		AND DATEADD(SECOND, -1, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) + 1, 0))
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT lgdId), 0) AS cantidad,'PLEG'
	FROM LegalizacionDesembolso
		INNER JOIN SolicitudLegalizacionDesembolso ON sldLegalizacionDesembolso = lgdId
		INNER JOIN PostulacionFOVIS ON sldPostulacionFOVIS = pofid
		INNER JOIN Solicitud on sldSolicitudGlobal = solid
	WHERE pofestadoHogar = 'SUBSIDIO_DESEMBOLSADO' AND solFechaCreacion BETWEEN  CAST(DATEADD(YEAR, -1, GETDATE()) AS DATE) AND GETDATE()
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT solId), 0) AS cantidad,'AFIP'
	FROM Solicitud
		INNER JOIN SolicitudAfiliacionPersona ON sapSolicitudGlobal = solid
	WHERE solTipoTransaccion IN ('AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION', 'AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION')
		AND sapEstadoSolicitud NOT IN ('CERRADA', 'RECHAZADA')
		AND solFechaCreacion BETWEEN  CAST(DATEADD(YEAR, -1, dbo.GetLocalDate()) AS DATE) AND dbo.GetLocalDate()
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT moaId), 0) AS cantidad,'APOD'
	FROM AporteGeneral as apg
		JOIN MovimientoAporte as moa on moa.moaAporteGeneral = apg.apgId
	WHERE apg.apgPeriodoAporte = FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM')
		AND moaTipoMovimiento = 'DEVOLUCION_APORTES'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT moaId), 0) AS cantidad,'APOC'
	FROM AporteGeneral as apg
		JOIN MovimientoAporte as moa on moa.moaAporteGeneral = apg.apgId
	WHERE apg.apgPeriodoAporte = FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM')
		AND moaTipoMovimiento = 'CORRECCION_APORTES'
	UNION ALL
	SELECT NULL, ISNULL(COUNT(DISTINCT moaId), 0) AS cantidad,
	'APRM'
	FROM AporteGeneral as apg
		JOIN MovimientoAporte as moa on moa.moaAporteGeneral = apg.apgId
	WHERE apg.apgPeriodoAporte = FORMAT(DATEADD(MONTH, -1, GETDATE()), 'yyyy-MM')
		AND moaTipoMovimiento = 'RECAUDO_MANUAL_APORTES'

INSERT INTO #DatosFinalesDashboard (Periodo, Cantidad, Proceso, origen)
exec sp_execute_remote pilareferencedata, N'
	SELECT NULL,
		 ISNULL(COUNT(pipId), 0) AS cantidad,
		''PNOT'' AS Proceso
	FROM PilaIndicePlanilla p
	WHERE PIPID >0
		AND pipestadoarchivo = ''RECAUDO_NOTIFICADO''
	AND pipFechaRecibo between FORMAT(DATEADD(MONTH, -1, GETDATE()), ''yyyy-MM-dd'')  and FORMAT(DATEADD(DAY, 1, GETDATE()), ''yyyy-MM-dd'')'


INSERT INTO EstadisticasGenesys (
    esgLiquidacionMesUno, esgLiquidacionConDerechoUno,esgLiquidacionSinDerechoUno,
    esgLiquidacionMesDos, esgLiquidacionConDerechoDos,esgLiquidacionSinDerechoDos,
    esgLiquidacionMesTres, esgLiquidacionConDerechoTres,esgLiquidacionSinDerechoTres,
    esgLiquidacionMesCuatro, esgLiquidacionConDerechoCuatro,esgLiquidacionSinDerechoCuatro,
    esgLiquidacionMesCinco, esgLiquidacionConDerechoCinco,esgLiquidacionSinDerechoCinco,
    esgLiquidacionMesSeis, esgLiquidacionConDerechoSeis,esgLiquidacionSinDerechoSeis,
	esgAporteMesUno, esgAportesRelacionadosUno, esgAportesRegistradosUno,
	esgAporteMesDos, esgAportesRelacionadosDos, esgAportesRegistradosDos,
	esgAporteMesTres, esgAportesRelacionadosTres, esgAportesRegistradosTres,
	esgAporteMesCuatro, esgAportesRelacionadosCuatro, esgAportesRegistradosCuatro,
	esgAporteMesCinco, esgAportesRelacionadosCinco, esgAportesRegistradosCinco,
	esgAporteMesSeis, esgAportesRelacionadosSeis, esgAportesRegistradosSeis,
	esgCantidadEmpresas, esgCantidadAfiliados, esgCantidadBeneficiarios, esgCantidadEmpresasMorosas,
	esgLiquidacionesPorFallecimiento,esgLiquidacionesPorAjuste, esgLiquidacionesPorReconocimiento, esgLiquidacionesMasivas,
	esgPostulacionesAsignadas, esgPostulacionesRecibidasEnElAnio, esgPostulacionesPorAsignar,
	esgValorAsignacionUltimoMes, esgPostulacionesLegalizadas ,esgAfiliacionesSinTerminarEnAnio,
	esgAportesDevoluciones, esgAportesCorreciones, esgAportesManuales, esgAportesPlanillasNotificadas, esgFechaRegistro


)
SELECT
    MAX(CASE WHEN Proceso = '1C' THEN Periodo END) AS Mes1,
    MAX(CASE WHEN Proceso = '1C' THEN Cantidad END) AS Cantidad1C,
	MAX(CASE WHEN Proceso = '1S' THEN Cantidad END) AS Cantidad1S,
    MAX(CASE WHEN Proceso = '2C' THEN Periodo END) AS Mes2,
    MAX(CASE WHEN Proceso = '2C' THEN Cantidad END) AS Cantidad2C,
	MAX(CASE WHEN Proceso = '2S' THEN Cantidad END) AS Cantidad2S,
    MAX(CASE WHEN Proceso = '3C' THEN Periodo END) AS Mes3,
    MAX(CASE WHEN Proceso = '3C' THEN Cantidad END) AS Cantidad3C,
	MAX(CASE WHEN Proceso = '3S' THEN Cantidad END) AS Cantidad3S,
    MAX(CASE WHEN Proceso = '4C' THEN Periodo END) AS Mes4,
    MAX(CASE WHEN Proceso = '4C' THEN Cantidad END) AS Cantidad4C,
	MAX(CASE WHEN Proceso = '4S' THEN Cantidad END) AS Cantidad4S,
    MAX(CASE WHEN Proceso = '5C' THEN Periodo END) AS Mes5,
    MAX(CASE WHEN Proceso = '5C' THEN Cantidad END) AS Cantidad5C,
	MAX(CASE WHEN Proceso = '5S' THEN Cantidad END) AS Cantidad5S,
    MAX(CASE WHEN Proceso = '6C' THEN Periodo END) AS Mes6,
    MAX(CASE WHEN Proceso = '6C' THEN Cantidad END) AS Cantidad6C,
	MAX(CASE WHEN Proceso = '6S' THEN Cantidad END) AS Cantidad6S,

	MAX(CASE WHEN Proceso = '1REL' THEN Periodo END) AS Mes1,
    MAX(CASE WHEN Proceso = '1REL' THEN Cantidad END) AS Cantidad1REL,
	MAX(CASE WHEN Proceso = '1REG' THEN Cantidad END) AS Cantidad1REG,
    MAX(CASE WHEN Proceso = '2REL' THEN Periodo END) AS Mes2,
    MAX(CASE WHEN Proceso = '2REL' THEN Cantidad END) AS Cantidad2REL,
	MAX(CASE WHEN Proceso = '2REG' THEN Cantidad END) AS Cantidad2REG,
    MAX(CASE WHEN Proceso = '3REL' THEN Periodo END) AS Mes3,
    MAX(CASE WHEN Proceso = '3REL' THEN Cantidad END) AS Cantidad3REL,
	MAX(CASE WHEN Proceso = '3REG' THEN Cantidad END) AS Cantidad3REG,
    MAX(CASE WHEN Proceso = '4REL' THEN Periodo END) AS Mes4,
    MAX(CASE WHEN Proceso = '4REL' THEN Cantidad END) AS Cantidad4REL,
	MAX(CASE WHEN Proceso = '4REG' THEN Cantidad END) AS Cantidad4REG,
    MAX(CASE WHEN Proceso = '5REL' THEN Periodo END) AS Mes5,
    MAX(CASE WHEN Proceso = '5REL' THEN Cantidad END) AS Cantidad5REL,
	MAX(CASE WHEN Proceso = '5REG' THEN Cantidad END) AS Cantidad5REG,
    MAX(CASE WHEN Proceso = '6REL' THEN Periodo END) AS Mes6,
    MAX(CASE WHEN Proceso = '6REL' THEN Cantidad END) AS Cantidad6REL,
	MAX(CASE WHEN Proceso = '6REG' THEN Cantidad END) AS Cantidad6REG,

	MAX(CASE WHEN Proceso = 'EMPA' THEN Cantidad END) AS EmpresasActivas,
	MAX(CASE WHEN Proceso = 'AFIA' THEN Cantidad END) AS AfiliadosActivos,
	MAX(CASE WHEN Proceso = 'BENA' THEN Cantidad END) AS BeneficiariosActivos,
	MAX(CASE WHEN Proceso = 'EMPM' THEN Cantidad END) AS EmpresasEnMora,
	MAX(CASE WHEN Proceso = 'LSDD' THEN Cantidad END) AS LiquidacionDefuncion,
	MAX(CASE WHEN Proceso = 'LADC' THEN Cantidad END) AS LiquidacionAjuste,
	MAX(CASE WHEN Proceso = 'LRDS' THEN Cantidad END) AS LiquidacionReconocimiento,
	MAX(CASE WHEN Proceso = 'LMAS' THEN Cantidad END) AS LiquidacionMasiva,

	MAX(CASE WHEN Proceso = 'PASI' THEN Cantidad END) AS PostulacionesAsignadas,
	MAX(CASE WHEN Proceso = 'PTOT' THEN Cantidad END) AS [PostulacionesRecibidasAño],
	MAX(CASE WHEN Proceso = 'PPAS' THEN Cantidad END) AS PostulacionesPendAsignacion,
	MAX(CASE WHEN Proceso = 'VASI' THEN Cantidad END) AS ValorAsignacionUltimoMes,
	MAX(CASE WHEN Proceso = 'PLEG' THEN Cantidad END) AS PostulacionesLegalizadas,
	MAX(CASE WHEN Proceso = 'AFIP' THEN Cantidad END) AS AfiliacionesSinTerminar,

	MAX(CASE WHEN Proceso = 'APOD' THEN Cantidad END) AS Devoluciones,
	MAX(CASE WHEN Proceso = 'APOC' THEN Cantidad END) AS Correciones,
	MAX(CASE WHEN Proceso = 'APRM' THEN Cantidad END) AS AportesManuales,
	MAX(CASE WHEN Proceso = 'PNOT' THEN Cantidad END) AS PlanillasNotificadas,
	dbo.getLocalDate()
	FROM #DatosFinalesDashboard;

	DROP TABLE #DatosFinalesDashboard
END
