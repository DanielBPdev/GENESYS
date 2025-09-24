/****** Object:  StoredProcedure [dbo].[reporteConsolidadoAsigPagoReinFovis]    Script Date: 26/06/2021 8:12:38 a. m. ******/

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 20 Abril 2021
-- Description: Procedimiento almacenado encargado de ejecutar la consulta Consolidado Histórico Asignación, pago y reintegro de subsidios de viviendas FOVIS con sus rangos de fechas
-- Reporte Normativo 28
-- =============================================
CREATE PROCEDURE [dbo].[reporteConsolidadoAsigPagoReinFovis]
(
    @fechaInicio DATE,
	@fechaFin DATE
)
AS
BEGIN
    -- (REPORTE 28) Consolidado histórico asignaciones, pagos y reintegros (ANUAL)
	-- Descripción: Consolidado histórico asignaciones, pagos y reintegros 
	-- Select que agrupa las columnas
	SELECT
	detallePostulaciones.[Año vigencia de asignación del subsidio],
	detallePostulaciones.[Código tipo plan de vivienda],
	detallePostulaciones.[Estado del subsidio],
	COUNT(1) [Cantidad de subsidios],
	SUM(detallePostulaciones.Valor) [Valor subsidios]
	FROM (
		-- Select con la lógica de los datos y homologaciones para Asignaciones
		SELECT
		-- Año vigencia de asignación del subsidio
		CAST(YEAR(ca.ciaFechaInicio)as varchar) [Año vigencia de asignación del subsidio],
		-- Código tipo plan de vivienda
		CASE (SELECT paramModalidad.pmoId FROM ParametrizacionModalidad paramModalidad 
				WHERE paramModalidad.pmoNombre = pof.pofmodalidad)
			WHEN 2 THEN 1 -- Adquisición de vivienda nueva urbana
			WHEN 6 THEN 2 -- Construcción en sitio propio urbano
			WHEN 9 THEN 3 -- Mejoramiento de vivienda urbana
			WHEN 4 THEN 4 -- Adquisición de vivienda usada urbana
			WHEN 8 THEN 5 -- Mejoramiento de vivienda saludable
			WHEN 5 THEN 6 -- Construcción en sitio propio rural
			WHEN 1 THEN 7 -- Adquisición de vivienda nueva rural
			WHEN 7 THEN 8 -- Mejoramiento de vivienda rural
		END	[Código tipo plan de vivienda],
		-- Estado del subsidio
		1 [Estado del subsidio],
		-- Valor
		pof.pofValorAsignadoSFV [Valor]

		FROM SolicitudAsignacion sAsig (NOLOCK)
		INNER JOIN PostulacionFOVIS pof ON pof.pofSolicitudAsignacion = sAsig.safId
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN ActaAsignacionFovis actAsig (NOLOCK) on sAsig.safId = actAsig.aafSolicitudAsignacion
		INNER JOIN Solicitud sol (NOLOCK) ON sAsig.safsolicitudglobal = sol.solId
		LEFT JOIN ProyectoSolucionVivienda psv (NOLOCK) on pof.pofProyectoSolucionVivienda = psv.psvId
		OUTER APPLY (SELECT TOP 1 saInterno.* FROM SolicitudLegalizacionDesembolso saInterno WHERE saInterno.sldPostulacionFOVIS = pof.pofId AND saInterno.sldLegalizacionDesembolso IS NOT NULL AND saInterno.sldEstadoSolicitud = 'LEGALIZACION_Y_DESEMBOLSO_CERRADO') sa
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
			AND sAsig.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin

		UNION ALL 

		-- Select con la lógica de los datos y homologaciones para Pagos
		SELECT
		-- Año vigencia de asignación del subsidio
		CAST(YEAR(ca.ciaFechaInicio)as varchar) [Año vigencia de asignación del subsidio],
		-- Código tipo plan de vivienda
		CASE (SELECT paramModalidad.pmoId FROM ParametrizacionModalidad paramModalidad 
				WHERE paramModalidad.pmoNombre = pof.pofmodalidad)
			WHEN 2 THEN 1 -- Adquisición de vivienda nueva urbana
			WHEN 6 THEN 2 -- Construcción en sitio propio urbano
			WHEN 9 THEN 3 -- Mejoramiento de vivienda urbana
			WHEN 4 THEN 4 -- Adquisición de vivienda usada urbana
			WHEN 8 THEN 5 -- Mejoramiento de vivienda saludable
			WHEN 5 THEN 6 -- Construcción en sitio propio rural
			WHEN 1 THEN 7 -- Adquisición de vivienda nueva rural
			WHEN 7 THEN 8 -- Mejoramiento de vivienda rural
		END	[Código tipo plan de vivienda],
		-- Estado del subsidio
		8 [Estado del subsidio],
		-- Valor
		legDes.lgdMontoDesembolsado [Valor]

		FROM [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK)
		INNER JOIN legalizaciondesembolso legDes WITH(NOLOCK) on sa.[sldLegalizacionDesembolso] = legDes.lgdid
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = sa.sldPostulacionFOVIS
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN Solicitud sol (NOLOCK) ON sa.sldSolicitudGlobal = sol.solId
		LEFT JOIN ProyectoSolucionVivienda psv (NOLOCK) on pof.pofProyectoSolucionVivienda = psv.psvId
		WHERE sa.sldEstadoSolicitud IN ('LEGALIZACION_Y_DESEMBOLSO_CONFIRMADO', 'REINTENTO_DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA', 'DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA',
			'LEGALIZACION_Y_DESEMBOLSO_AUTORIZADO', 'LEGALIZACION_Y_DESEMBOLSO_CERRADO')
				AND sa.sldFechaOperacion BETWEEN @fechaInicio AND @fechaFin

		UNION ALL

		-- Select con la lógica de los datos y homologaciones para Novedades
		SELECT
		-- Año vigencia de asignación del subsidio
		CAST(YEAR(ca.ciaFechaInicio)as varchar) [Año vigencia de asignación del subsidio],
		-- Código tipo plan de vivienda
		CASE (SELECT paramModalidad.pmoId FROM ParametrizacionModalidad paramModalidad 
				WHERE paramModalidad.pmoNombre = pof.pofmodalidad)
			WHEN 2 THEN 1 -- Adquisición de vivienda nueva urbana
			WHEN 6 THEN 2 -- Construcción en sitio propio urbano
			WHEN 9 THEN 3 -- Mejoramiento de vivienda urbana
			WHEN 4 THEN 4 -- Adquisición de vivienda usada urbana
			WHEN 8 THEN 5 -- Mejoramiento de vivienda saludable
			WHEN 5 THEN 6 -- Construcción en sitio propio rural
			WHEN 1 THEN 7 -- Adquisición de vivienda nueva rural
			WHEN 7 THEN 8 -- Mejoramiento de vivienda rural
		END	[Código tipo plan de vivienda],
		-- Estado del subsidio
		CASE
			WHEN paramNov.novTipoTransaccion = 'REEMBOLSO_VOLUNTARIO_SUBSIDIO' OR paramNov.novTipoTransaccion = 'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO'
				THEN 6
			WHEN paramNov.novTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
				THEN 4
			WHEN paramNov.novTipoTransaccion = 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA'
				THEN 5
		END	[Estado del subsidio],
		-- Valor
		ISNULL(lgdMontoDesembolsado, pof.pofValorAsignadoSFV) [Valor]

		FROM [dbo].[SolicitudNovedadFovis] postNov (NOLOCK)
		INNER JOIN ParametrizacionNovedad paramNov WITH(NOLOCK) ON paramNov.novId = postNov.snfParametrizacionNovedad
		INNER JOIN SolicitudNovedad solNov WITH(NOLOCK) ON solNov.snoId = paramNov.novId
		INNER JOIN [dbo].[SolicitudNovedadPersonaFovis] solNovPer (NOLOCK) ON solNovPer.spfSolicitudNovedadFovis = postNov.snfId
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = solNovPer.spfPostulacionFovis
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN Solicitud sol (NOLOCK) ON postNov.snfSolicitudGlobal = sol.solId
		LEFT JOIN ProyectoSolucionVivienda psv (NOLOCK) on pof.pofProyectoSolucionVivienda = psv.psvId
		LEFT JOIN SolicitudLegalizacionDesembolso sa (NOLOCK) on sa.sldPostulacionFOVIS = pof.pofId
		LEFT JOIN LegalizacionDesembolso ld (NOLOCK) ON ld.lgdId = sa.sldLegalizacionDesembolso
		WHERE (paramNov.novProceso = 'NOVEDADES_FOVIS_REGULAR' AND paramNov.novTipoTransaccion IN ('REEMBOLSO_VOLUNTARIO_SUBSIDIO', 'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO',
			'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA', 'RENUNCIO_SUBISIDIO_ASIGNADO')) -- 'RECHAZO_DE_POSTULACION', 'HABILITACION_POSTULACION_RECHAZADA',
				AND sol.solFechaRadicacion BETWEEN @fechaInicio AND @fechaFin

		UNION ALL

		SELECT 
		-- Año vigencia de asignación del subsidio
		CAST(YEAR(ca.ciaFechaInicio)as varchar) [Año vigencia de asignación del subsidio],
		-- Código tipo plan de vivienda
		CASE (SELECT paramModalidad.pmoId FROM ParametrizacionModalidad paramModalidad 
				WHERE paramModalidad.pmoNombre = pof.pofmodalidad)
			WHEN 2 THEN 1 -- Adquisición de vivienda nueva urbana
			WHEN 6 THEN 2 -- Construcción en sitio propio urbano
			WHEN 9 THEN 3 -- Mejoramiento de vivienda urbana
			WHEN 4 THEN 4 -- Adquisición de vivienda usada urbana
			WHEN 8 THEN 5 -- Mejoramiento de vivienda saludable
			WHEN 5 THEN 6 -- Construcción en sitio propio rural
			WHEN 1 THEN 7 -- Adquisición de vivienda nueva rural
			WHEN 7 THEN 8 -- Mejoramiento de vivienda rural
		END	[Código tipo plan de vivienda],
		-- Estado del subsidio
		1	[Estado del subsidio],
		-- Valor
		pof.pofValorAsignadoSFV [Valor]

		FROM PostulacionFOVIS pof
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN SolicitudPostulacion solPos ON solPos.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol ON sol.solId = solPos.spoSolicitudGlobal
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO' AND sol.solFechaRadicacion < @fechaInicio
		-- Consulta las solicitudes de asignacion y puede existir alguna de la postulacion que sea del periodo seleccionado
		AND (SELECT TOP 1 sAsig.safId FROM SolicitudAsignacion sAsig WHERE pof.pofSolicitudAsignacion = sAsig.safId AND sAsig.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin) IS NULL
		-- Valida que no existan legalizaciones
		AND (SELECT TOP 1 sLegDes.sldId FROM SolicitudLegalizacionDesembolso sLegDes WHERE sLegDes.sldPostulacionFOVIS = pof.pofId) IS NULL
		-- Valida que no existan novedades
		AND (SELECT TOP 1 solNovPer.spfId FROM SolicitudNovedadPersonaFovis solNovPer WHERE pof.pofId = solNovPer.spfPostulacionFovis) IS NULL

	) detallePostulaciones
	GROUP BY
		detallePostulaciones.[Año vigencia de asignación del subsidio],
		detallePostulaciones.[Código tipo plan de vivienda],
		detallePostulaciones.[Estado del subsidio]
	ORDER BY
		detallePostulaciones.[Año vigencia de asignación del subsidio],
		detallePostulaciones.[Código tipo plan de vivienda]
END
