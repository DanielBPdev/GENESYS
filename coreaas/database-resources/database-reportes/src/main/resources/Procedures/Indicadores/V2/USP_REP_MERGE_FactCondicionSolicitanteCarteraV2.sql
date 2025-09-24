-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/11/22
-- Description:	Carga la tabla de hechos FactGestionCartera para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionSolicitanteCarteraV2
AS

	--FactCondicionPersona
	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @DimPeriodoAnteriorId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1

	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);
	SELECT @DimPeriodoAnteriorId = dipId FROM DimPeriodo WHERE dipMes = MONTH(DATEADD(mm,-1,@FechaFinRevision)) AND dipAnio = YEAR(DATEADD(mm,-1,@FechaFinRevision));
		
	
	--Morosos período anterior
	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactCondicionSolicitanteCarteraV2 WHERE fcaDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteMorososPeriodoAnterior AS (
				SELECT fcaPersona,@DimPeriodoId AS fcaDimPeriodo,fcaDimTipoSolicitante,fcaDimTamanioEmpleador,fcaDimNaturalezaJuridica,fcaDimClasificacion,fcaDimEstadoSolicitante,'true' AS fcaMorosoPeriodoAnterior, 'true' AS fcaMoroso, fcaFechaDesafiliacion,fcaFechaAfiliacion,fcaMontoRecaudoAportes
				FROM FactCondicionSolicitanteCarteraV2 fca			
				WHERE fca.fcaDimPeriodo = @DimPeriodoAnteriorId
				AND fca.fcaMoroso = 'true'
			)
			INSERT INTO FactCondicionSolicitanteCarteraV2 (fcaPersona,fcaDimPeriodo,fcaDimTipoSolicitante,fcaDimTamanioEmpleador,fcaDimNaturalezaJuridica,fcaDimClasificacion,fcaDimEstadoSolicitante,fcaMorosoPeriodoAnterior,fcaMoroso,fcaFechaDesafiliacion,fcaFechaAfiliacion,fcaMontoRecaudoAportes)
			SELECT fcaPersona,fcaDimPeriodo,fcaDimTipoSolicitante,fcaDimTamanioEmpleador,fcaDimNaturalezaJuridica,fcaDimClasificacion,fcaDimEstadoSolicitante,fcaMorosoPeriodoAnterior,fcaMoroso,fcaFechaDesafiliacion,fcaFechaAfiliacion,fcaMontoRecaudoAportes
			FROM cteMorososPeriodoAnterior
		END
	END;
	
	WITH cteDimClasificacion
	AS 
	(
		SELECT	solClasificacion, fcaDimClasificacion 
		FROM (
			VALUES
				('ENTIDAD_SIN_ANIMO_DE_LUCRO', 1),
				('COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO', 2),
				('EMPLEADOR_DE_SERVICIO_DOMESTICO', 3),
				('ORGANIZACION_RELIGIOSA_O_PARROQUIA', 4),
				('PERSONA_JURIDICA', 5),
				('PERSONA_NATURAL', 6),
				('PROPIEDAD_HORIZONTAL', 7)
			) AS T (solClasificacion, fcaDimClasificacion)				
	),

	cteDimNaturalezaJuridica
	AS 
	(
		SELECT	empNaturalezaJuridica, fcaDimNaturalezaJuridica 
		FROM (
			VALUES
				('PUBLICA', 1),
				('PRIVADA', 2),
				('MIXTA', 3),
				('ORGANISMOS_MULTILATERALES', 4),
				('ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS', 5)
			) AS T (empNaturalezaJuridica, fcaDimNaturalezaJuridica)				
	),

	cteDimTipoSolicitante
	AS 
	(
		SELECT	carTipoSolicitante, fcaDimTipoSolicitante 
		FROM (
			VALUES
				('EMPLEADOR', 1),
				('INDEPENDIENTE', 2),
				('PENSIONADO', 3)
			) AS T (carTipoSolicitante, fcaDimTipoSolicitante)
	),

	cteDimEstadoSolicitante
	AS 
	(
		SELECT	eaxEstadoAfiliacion, fcaDimEstadoSolicitante 
		FROM (
			VALUES
				('ACTIVO', 1),
				('INACTIVO', 2),
				('NO_FORMALIZADO_RETIRADO_CON_APORTES', 3),
				('NO_FORMALIZADO_CON_INFORMACION', 4),
				('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 5)
			) AS T (eaxEstadoAfiliacion, fcaDimEstadoSolicitante)
	
	),

	cteDimTamanioEmpleador
	AS 
	(
		SELECT	cantidadTrabajadoresMin, cantidadTrabajadoresMax, fcaDimTamanioEmpleador
		FROM (
			VALUES
				(0, 10, 1),
				(11, 50, 2),
				(51, 200, 3),
				(201, NULL, 4)
			) AS T (cantidadTrabajadoresMin, cantidadTrabajadoresMax, fcaDimTamanioEmpleador)
	
	),


	cteClasificacion AS
	(
		SELECT sae.saeEmpleador, sol.solClasificacion
		FROM SolicitudAfiliaciEmpleador sae
		INNER JOIN Solicitud sol ON sae.saeSolicitudGlobal = sol.solId
		INNER JOIN (
			SELECT sae.saeEmpleador, max(sae.saeId) saeId 
			FROM SolicitudAfiliaciEmpleador sae
			INNER JOIN Solicitud sol ON sae.saeSolicitudGlobal = sol.solId
			WHERE sol.solClasificacion IN (
				'ENTIDAD_SIN_ANIMO_DE_LUCRO',
				'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',
				'EMPLEADOR_DE_SERVICIO_DOMESTICO',
				'ENTIDAD_SIN_ANIMO_DE_LUCRO',
				'ORGANIZACION_RELIGIOSA_O_PARROQUIA',
				'PERSONA_JURIDICA',
				'PERSONA_NATURAL',
				'PROPIEDAD_HORIZONTAL'
			)
			GROUP BY sae.saeEmpleador
		) saeT ON sae.saeId = saet.saeId
	),

	cteEstadoSolicitanteEmpleador
	AS 
	(
		SELECT eec.eecPersona AS eaxPersona, eec.eecEstadoAfiliacion AS eaxEstadoAfiliacion, eec.eecFechaCambioEstado AS eaxFechaCambioEstado
		FROM EstadoAfiliacionEmpleadorCaja eec 
		INNER JOIN (SELECT eec.eecPersona, MAX(eecId) eecId
					FROM EstadoAfiliacionEmpleadorCaja eec
					GROUP BY eec.eecPersona) eec1 ON eec1.eecId = eec.eecId AND eec1.eecPersona = eec.eecPersona
	),
	cteEstadoSolicitanteIndependiente
	AS 
	(
		SELECT eai.eaiPersona AS eaxPersona, eai.eaiEstadoAfiliacion AS eaxEstadoAfiliacion, eai.eaiFechaCambioEstado AS eaxFechaCambioEstado
		FROM EstadoAfiliacionPersonaIndependiente eai
		INNER JOIN (SELECT eai.eaiPersona, MAX(eaiId) eaiId
					FROM EstadoAfiliacionPersonaIndependiente eai
					GROUP BY eai.eaiPersona) eai1 ON eai1.eaiId = eai.eaiId AND eai1.eaiPersona = eai.eaiPersona
	),
	cteEstadoSolicitantePensionado
	AS 
	(
		SELECT eap.eapPersona AS eaxPersona, eap.eapEstadoAfiliacion AS eaxEstadoAfiliacion, eap.eapFechaCambioEstado AS eaxFechaCambioEstado
		FROM EstadoAfiliacionPersonaPensionado eap 
		INNER JOIN (SELECT eap.eapPersona, MAX(eapId) eapId
					FROM EstadoAfiliacionPersonaPensionado eap
					GROUP BY eap.eapPersona) eap1 ON eap1.eapId = eap.eapId AND eap1.eapPersona = eap.eapPersona
	),

	cteCantidadTrabajadoresActivos
	AS
	(
		SELECT roa.roaEmpleador, COUNT(1) AS cantidadTrabajadoresActivos
		FROM RolAfiliado roa
		WHERE roa.roaEstadoAfiliado = 'ACTIVO'
		AND roa.roaEmpleador IS NOT NULL
		GROUP BY roa.roaEmpleador
	),	

	cteMontoRecaudoAportesEmpleador
	AS
	(
		SELECT apg.apgEmpresa, SUM(moa.montoRecaudoAportes) AS montoRecaudoAportes
		FROM AporteGeneral apg
		INNER JOIN (
			SELECT moaAporteGeneral, SUM(moaValorAporte + moaValorInteres) montoRecaudoAportes
			FROM MovimientoAporte moa
			WHERE moa.moaFechaActualizacionEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
			GROUP BY moaAporteGeneral
		) moa ON apg.apgId = moa.moaAporteGeneral
		WHERE apg.apgEmpresa IS NOT NULL
		GROUP BY apg.apgEmpresa
	),	

	cteMontoRecaudoAportesPersona
	AS
	(
		SELECT apg.apgPersona, apg.apgTipoSolicitante, SUM(moa.montoRecaudoAportes) AS montoRecaudoAportes
		FROM AporteGeneral apg
		INNER JOIN (
			SELECT moaAporteGeneral, SUM(moaValorAporte + moaValorInteres) montoRecaudoAportes
			FROM MovimientoAporte moa
			WHERE moa.moaFechaActualizacionEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
			GROUP BY moaAporteGeneral
		) moa ON apg.apgId = moa.moaAporteGeneral
		WHERE apg.apgPersona IS NOT NULL
		GROUP BY apg.apgPersona, apg.apgTipoSolicitante
	),

	cteCruceCartera
	AS
	(
		SELECT	car.carId, car.carPersona AS persona, cteeax.eaxEstadoAfiliacion, empl.empFechaRetiro AS fechaRetiro, cteeax.eaxFechaCambioEstado, ctecla.solClasificacion, emp.empNaturalezaJuridica, car.carTipoSolicitante AS tipoSolicitante, COALESCE(ctecta.cantidadTrabajadoresActivos, empl.empNumeroTotalTrabajadores, 0) AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM Cartera car
		INNER JOIN cteEstadoSolicitanteEmpleador cteeax ON car.carPersona = cteeax.eaxPersona
		INNER JOIN Empresa emp ON car.carPersona = emp.empPersona
		INNER JOIN Empleador empl ON emp.empId = empl.empEmpresa
		INNER JOIN cteClasificacion ctecla ON empl.empId = ctecla.saeEmpleador
		LEFT JOIN cteCantidadTrabajadoresActivos ctecta ON empl.empId = ctecta.roaEmpleador
		LEFT JOIN cteMontoRecaudoAportesEmpleador ctemra ON empl.empId = ctemra.apgEmpresa
		WHERE car.carTipoSolicitante = 'EMPLEADOR'

		UNION ALL

		SELECT car.carId, car.carPersona AS persona, cte.eaxEstadoAfiliacion, roa.roaFechaRetiro AS fechaRetiro, cte.eaxFechaCambioEstado, NULL AS solClasificacion, NULL AS empNaturalezaJuridica, car.carTipoSolicitante AS tipoSolicitante, NULL AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM Cartera car
		INNER JOIN cteEstadoSolicitanteIndependiente cte ON car.carPersona = cte.eaxPersona
		INNER JOIN Afiliado afi ON car.carPersona = afi.afiPersona
		INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
		LEFT JOIN cteMontoRecaudoAportesPersona ctemra ON car.carPersona = ctemra.apgPersona AND car.carTipoSolicitante = ctemra.apgTipoSolicitante
		WHERE car.carTipoSolicitante = 'INDEPENDIENTE'
		
		UNION ALL

		SELECT car.carId, car.carPersona AS persona, cte.eaxEstadoAfiliacion, roa.roaFechaRetiro AS fechaRetiro, cte.eaxFechaCambioEstado, NULL AS solClasificacion, NULL AS empNaturalezaJuridica, car.carTipoSolicitante AS tipoSolicitante, NULL AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM Cartera car
		INNER JOIN cteEstadoSolicitantePensionado cte ON car.carPersona = cte.eaxPersona
		INNER JOIN Afiliado afi ON car.carPersona = afi.afiPersona
		INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND roa.roaTipoAfiliado = 'PENSIONADO'
		LEFT JOIN cteMontoRecaudoAportesPersona ctemra ON car.carPersona = ctemra.apgPersona AND car.carTipoSolicitante = ctemra.apgTipoSolicitante
		WHERE car.carTipoSolicitante = 'PENSIONADO'
	),

	cteWaterMarkedRows
	AS
	(
		SELECT car.carId, car.carPersona, car.carTipoSolicitante
		FROM WaterMarkedRows wmr
		INNER JOIN Cartera car ON wmr.wmrKeyRowValue = car.carId
		WHERE wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'Cartera'
	),

	cteNewRows
	AS
	(
		SELECT DISTINCT cte.persona, cte.eaxEstadoAfiliacion, cte.fechaRetiro, cte.eaxFechaCambioEstado, cte.solClasificacion, cte.empNaturalezaJuridica, cte.tipoSolicitante, cte.cantidadTrabajadoresActivos, cte.montoRecaudoAportes
		FROM cteCruceCartera cte
		WHERE EXISTS (			
			SELECT 1
			FROM cteWaterMarkedRows wmr
			WHERE wmr.carId = cte.carId
		)
	),

	cteCurrentRows
	AS
	(

		SELECT fact.fcaPersona AS persona, cteeax.eaxEstadoAfiliacion, empl.empFechaRetiro AS fechaRetiro, cteeax.eaxFechaCambioEstado, ctecla.solClasificacion, emp.empNaturalezaJuridica, 'EMPLEADOR' AS tipoSolicitante, COALESCE(ctecta.cantidadTrabajadoresActivos, empl.empNumeroTotalTrabajadores, 0) AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM FactCondicionSolicitanteCarteraV2 fact
		INNER JOIN cteEstadoSolicitanteEmpleador cteeax ON fact.fcaPersona = cteeax.eaxPersona
		INNER JOIN Empresa emp ON fact.fcaPersona = emp.empPersona
		INNER JOIN Empleador empl ON emp.empId = empl.empEmpresa
		INNER JOIN cteClasificacion ctecla ON empl.empId = ctecla.saeEmpleador
		LEFT JOIN cteCantidadTrabajadoresActivos ctecta ON empl.empId = ctecta.roaEmpleador
		LEFT JOIN cteMontoRecaudoAportesEmpleador ctemra ON empl.empId = ctemra.apgEmpresa 
		WHERE fact.fcaDimTipoSolicitante = 1 --'Empleador'
		AND fact.fcaDimPeriodo = @DimPeriodoId

		UNION ALL

		SELECT fact.fcaPersona AS persona, cteeax.eaxEstadoAfiliacion, roa.roaFechaRetiro AS fechaRetiro, cteeax.eaxFechaCambioEstado, NULL AS solClasificacion, NULL AS empNaturalezaJuridica, 'INDEPENDIENTE' AS tipoSolicitante, NULL AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM FactCondicionSolicitanteCarteraV2 fact
		INNER JOIN cteEstadoSolicitanteIndependiente cteeax ON fact.fcaPersona = cteeax.eaxPersona
		INNER JOIN Afiliado afi ON fact.fcaPersona = afi.afiPersona
		INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'	
		LEFT JOIN cteMontoRecaudoAportesPersona ctemra ON fact.fcaPersona = ctemra.apgPersona AND ctemra.apgTipoSolicitante = 'INDEPENDIENTE'
		WHERE fact.fcaDimTipoSolicitante = 2 --'Independiente'
		AND fact.fcaDimPeriodo = @DimPeriodoId

		UNION ALL

		SELECT fact.fcaPersona AS persona, cteeax.eaxEstadoAfiliacion, roa.roaFechaRetiro AS fechaRetiro, cteeax.eaxFechaCambioEstado, NULL AS solClasificacion, NULL AS empNaturalezaJuridica, 'PENSIONADO' AS tipoSolicitante, NULL AS cantidadTrabajadoresActivos, ctemra.montoRecaudoAportes
		FROM FactCondicionSolicitanteCarteraV2 fact
		INNER JOIN cteEstadoSolicitantePensionado cteeax ON fact.fcaPersona = cteeax.eaxPersona	
		INNER JOIN Afiliado afi ON fact.fcaPersona = afi.afiPersona
		INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND roa.roaTipoAfiliado = 'PENSIONADO'	
		LEFT JOIN cteMontoRecaudoAportesPersona ctemra ON fact.fcaPersona = ctemra.apgPersona AND ctemra.apgTipoSolicitante = 'PENSIONADO'
		WHERE fact.fcaDimTipoSolicitante = 3 --'Pensionado'
		AND fact.fcaDimPeriodo = @DimPeriodoId
	),

	cteAllRows
	AS 
	(
		SELECT *
		FROM cteNewRows cte
		
		UNION ALL

		SELECT *
		FROM cteCurrentRows cte
		WHERE NOT EXISTS (			
			SELECT 1
			FROM cteWaterMarkedRows wmr
			WHERE wmr.carPersona = cte.persona
			AND wmr.carTipoSolicitante = cte.tipoSolicitante
		)
		
	),

	cteAttachDimension 
	AS
	(
		SELECT 
			@DimPeriodoId AS fcaDimPeriodo,
			cte.persona AS fcaPersona,
			ctedta.fcaDimTipoSolicitante,
			cteeax.fcaDimEstadoSolicitante,
			ctedte.fcaDimTamanioEmpleador,
			ctedin.fcaDimNaturalezaJuridica,
			ctedcl.fcaDimClasificacion,
			CASE WHEN cte.eaxEstadoAfiliacion = 'ACTIVO' THEN cte.eaxFechaCambioEstado END fcaFechaAfiliacion,
			cte.fechaRetiro AS fcaFechaDesafiliacion,
			CASE WHEN EXISTS 
					(	
						SELECT 1
						FROM Cartera AS carMora
						WHERE carEstadoCartera = 'MOROSO' --revisar regla negocio de "moroso"
						AND carMora.carPersona = cte.persona AND carMora.carTipoSolicitante = cte.tipoSolicitante
					) THEN 'true' ELSE 'false' END AS fcaMoroso,
			cte.montoRecaudoAportes AS fcaMontoRecaudoAportes
		FROM cteAllRows cte
		INNER JOIN cteDimTipoSolicitante ctedta ON ctedta.carTipoSolicitante = cte.tipoSolicitante
		LEFT JOIN cteDimEstadoSolicitante cteeax ON cte.eaxEstadoAfiliacion = cteeax.eaxEstadoAfiliacion
		LEFT JOIN cteDimTamanioEmpleador ctedte ON cte.cantidadTrabajadoresActivos BETWEEN ctedte.cantidadTrabajadoresMin AND ISNULL(ctedte.cantidadTrabajadoresMax, cte.cantidadTrabajadoresActivos)
		LEFT JOIN cteDimNaturalezaJuridica ctedin ON cte.empNaturalezaJuridica = ctedin.empNaturalezaJuridica
		LEFT JOIN cteDimClasificacion ctedcl ON cte.solClasificacion = ctedcl.solClasificacion
		--LEFT JOIN	(	
		--			SELECT carId, carPersona, carTipoSolicitante
		--			FROM Cartera 
		--			WHERE carEstadoCartera = 'MOROSO' --revisar regla negocio de "moroso"
		--			) carMora ON carMora.carPersona = cte.persona AND carMora.carTipoSolicitante = cte.tipoSolicitante
	)
	
	MERGE FactCondicionSolicitanteCarteraV2 AS T
	USING cteAttachDimension AS S
	ON (T.fcaDimPeriodo = S.fcaDimPeriodo AND T.fcaPersona = S.fcaPersona AND T.fcaDimTipoSolicitante = S.fcaDimTipoSolicitante)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fcaDimPeriodo,fcaPersona,fcaDimTipoSolicitante,fcaDimEstadoSolicitante,fcaDimTamanioEmpleador,fcaDimNaturalezaJuridica,fcaDimClasificacion,fcaFechaAfiliacion,fcaFechaDesafiliacion,fcaMoroso,fcaMorosoPeriodoAnterior,fcaMontoRecaudoAportes)
		VALUES (S.fcaDimPeriodo,S.fcaPersona,S.fcaDimTipoSolicitante,S.fcaDimEstadoSolicitante,S.fcaDimTamanioEmpleador,S.fcaDimNaturalezaJuridica,S.fcaDimClasificacion,S.fcaFechaAfiliacion,S.fcaFechaDesafiliacion,S.fcaMoroso,NULL,S.fcaMontoRecaudoAportes)
	WHEN MATCHED
		THEN UPDATE SET T.fcaDimEstadoSolicitante = S.fcaDimEstadoSolicitante, T.fcaDimTamanioEmpleador = S.fcaDimTamanioEmpleador, T.fcaDimNaturalezaJuridica = S.fcaDimNaturalezaJuridica, T.fcaDimClasificacion = S.fcaDimClasificacion,
		T.fcaFechaAfiliacion = S.fcaFechaAfiliacion, T.fcaFechaDesafiliacion = S.fcaFechaDesafiliacion, T.fcaMoroso = S.fcaMoroso, T.fcaMontoRecaudoAportes = ISNULL(T.fcaMontoRecaudoAportes,0) + S.fcaMontoRecaudoAportes
;