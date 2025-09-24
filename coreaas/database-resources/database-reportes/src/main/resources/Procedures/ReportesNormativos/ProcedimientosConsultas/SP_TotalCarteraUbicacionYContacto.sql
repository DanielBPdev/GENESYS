

CREATE PROCEDURE [dbo].[totalCarteraUbicacionYContacto](
	@FECHA_INICIAL DATETIME,
	@FECHA_FINAL DATETIME
)

AS
BEGIN
--sSET ANSI_WARNINGS OFF
SET NOCOUNT ON

/*declare @FECHA_INICIAL date, @FECHA_FINAL date
set @FECHA_INICIAL = '07-01-2021'
set @FECHA_FINAL = '07-30-2021'*/

SELECT 	
-----------------------------------------
---------DATA PENSIONADOS E INDEPENDIENTES

	-----------------------------
	CONVERT(BIGINT, personaAporte.apgValTotalApoObligatorio) [valor]
FROM
	SolicitudNovedadPersona solNovPersona 
	LEFT JOIN SolicitudNovedad solNov 
		ON solNov.snoId = solNovPersona.snpSolicitudNovedad
	LEFT JOIN Solicitud sol 
		ON sol.solId = solNov.snoSolicitudGlobal		

	----------Ubicacion Persona----------
	inner JOIN Persona per ON (solNovPersona.snpPersona = per.perId)

	left JOIN Ubicacion ubi ON (per.perUbicacionPrincipal = ubi.ubiId)

	left JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
	left JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
	left JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
	left JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')
	
	LEFT JOIN Afiliado afiliado ON afiliado.afiPersona = per.perId

	INNER JOIN RolAfiliado rolAfiliado 
		ON rolAfiliado.roaAfiliado = afiliado.afiId 
			AND rolAfiliado.roaEstadoAfiliado = 'ACTIVO'
			AND rolAfiliado.roaTipoAfiliado <> 'DEPENDIENTE'
			AND rolAfiliado.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'

	left join ( 

		SELECT 
			apg.apgPersona, 
			max(apg.apgFechaProcesamiento) AS fechaUltimo,
			apg.apgValTotalApoObligatorio
		FROM AporteGeneral apg 
		WHERE 
			apg.apgPersona IS NOT NULL AND
			apg.apgEstadoAportante = 'ACTIVO' 
		GROUP BY
			apg.apgPersona,
			apg.apgValTotalApoObligatorio
		UNION 
	
		SELECT 
			emp.empPersona, 
			max(apg.apgFechaProcesamiento) AS fechaUltimo,
			apg.apgValTotalApoObligatorio
		FROM 
			AporteGeneral apg 
			JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
		WHERE
			apg.apgEstadoAportante = 'ACTIVO'
		GROUP BY 
			emp.empPersona,
			apg.apgValTotalApoObligatorio

	) AS personaAporte ON personaAporte.apgPersona = solNovPersona.snpPersona
	
	WHERE sol.solFechaRadicacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL

	GROUP BY
		cns.cnsValor,
		prm.prmValor,
		PER.perPrimerNombre,
		per.perPrimerApellido,
		perSegundoNombre,
		perSegundoApellido,
		perDigitoVerificacion,
		per.perRazonSocial,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,
		perTipoIdentificacion,
		ubi.ubiDireccionFisica,
		dep.depCodigo,
		mun.munCodigo,
		dep.depNombre,
		mun.munNombre,
		ubi.ubiTelefonoFijo,
		ubi.ubiIndicativoTelFijo,
		ubi.ubiTelefonoCelular,
		ubi.ubiEmail,
		rolAfiliado.roaEstadoAfiliado,
		personaAporte.apgValTotalApoObligatorio
	----

-----------------------------------------
-----------------------------------------
UNION 
-----------------------------------------
---------DATA EMPLEADORES novedades

SELECT 	
	CONVERT(BIGINT, personaAporte.apgValTotalApoObligatorio) [valor]
	
FROM
	SolicitudNovedadEmpleador solNovEmpleador
	LEFT JOIN SolicitudNovedad solNov 
		ON solNov.snoId = solNovEmpleador.sneIdEmpleador
		AND snoEstadoSolicitud in ('CERRADA', 'APROBADA')
	INNER JOIN Solicitud sol 
		ON sol.solId = solNov.snoSolicitudGlobal AND 
		sol.solFechaRadicacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL

	----------Ubicacion Persona----------
	INNER JOIN Empleador AS empleador 
		ON empleador.empId = sneIdEmpleador
		AND empleador.empEstadoEmpleador = 'ACTIVO'

	inner join Empresa AS empresa ON empresa.empId = empleador.empEmpresa
	LEFT JOIN PERSONA per ON per.perId = empresa.empPersona
	
	----datos ubicacion uno
	LEFT JOIN UbicacionEmpresa as ubiE 
		on ubiE.ubeEmpresa = empresa.empId AND
		ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
	left JOIN Ubicacion ubi ON ubiE.ubeUbicacion = ubi.ubiId
	left JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
	left JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
	
	----datos ubicacion 2
	LEFT JOIN UbicacionEmpresa as ubiE2 
		on ubiE2.ubeEmpresa = empresa.empId AND
		ubiE2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
	left JOIN Ubicacion ubi2 ON ubiE2.ubeUbicacion = ubi2.ubiId
	left JOIN Municipio mun2 ON (ubi2.ubiMunicipio = mun2.munId)
	left JOIN Departamento dep2 ON (mun2.munDepartamento = dep2.depId)

	left JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
	left JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')

	LEFT JOIN ( 

			SELECT 
				apg.apgPersona, 
				max(apg.apgFechaProcesamiento) AS fechaUltimo,
				apg.apgValTotalApoObligatorio
			FROM AporteGeneral apg 
			WHERE 
				apg.apgPersona IS NOT NULL AND
				apg.apgEstadoAportante = 'ACTIVO' 
			GROUP BY
				apg.apgPersona,
				apg.apgValTotalApoObligatorio

		UNION 
	
			SELECT 
				emp.empPersona, 
				max(apg.apgFechaProcesamiento) AS fechaUltimo,
				apg.apgValTotalApoObligatorio
			FROM 
				AporteGeneral apg 
				JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
			WHERE
				apg.apgEstadoAportante = 'ACTIVO'
			GROUP BY 
				emp.empPersona,
				apg.apgValTotalApoObligatorio

	) AS personaAporte ON personaAporte.apgPersona = per.perId

GROUP BY
		cns.cnsValor,
		prm.prmValor,
		PER.perPrimerNombre,
		per.perPrimerApellido,
		perSegundoNombre,
		perSegundoApellido,
		perDigitoVerificacion,
		per.perRazonSocial,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,
		perTipoIdentificacion,
		ubi.ubiDireccionFisica,
		dep.depCodigo,
		mun.munCodigo,
		dep.depNombre,
		mun.munNombre,
		ubi.ubiTelefonoFijo,
		ubi.ubiIndicativoTelFijo,
		ubi.ubiTelefonoCelular,
		ubi.ubiEmail,
		ubi2.ubiDireccionFisica,
		dep2.depCodigo,
		mun2.munCodigo,
		dep2.depNombre,
		mun2.munNombre,
		ubi2.ubiTelefonoFijo,
		ubi2.ubiIndicativoTelFijo,
		ubi2.ubiTelefonoCelular,
		ubi2.ubiEmail,
		personaAporte.apgValTotalApoObligatorio


UNION 

-------NUEVAS AFILIACIONES
SELECT 	
	CONVERT(BIGINT, personaAporte.apgValTotalApoObligatorio) [valor]

FROM
	SolicitudAfiliaciEmpleador solAfiEmpleador
	INNER JOIN Solicitud sol 
		ON sol.solId = solAfiEmpleador.saeSolicitudGlobal AND 
		sol.solResultadoProceso = 'APROBADA'

	----------Ubicacion Persona----------
	INNER JOIN Empleador AS empleador 
		ON empleador.empId = solAfiEmpleador.saeEmpleador
		AND empleador.empEstadoEmpleador = 'ACTIVO'

	inner join Empresa AS empresa ON empresa.empId = empleador.empEmpresa
	inner JOIN PERSONA per ON per.perId = empresa.empPersona

	----datos ubicacion uno
	INNER JOIN UbicacionEmpresa as ubiE 
		on ubiE.ubeEmpresa = empresa.empId AND
		ubiE.ubeTipoUbicacion IN ( 'UBICACION_PRINCIPAL' )
	INNER JOIN Ubicacion ubi ON ubiE.ubeUbicacion = ubi.ubiId
	INNER JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
	INNER JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
	
	----datos ubicacion 2
	INNER JOIN UbicacionEmpresa as ubiE2 
		on ubiE2.ubeEmpresa = empresa.empId AND
		ubiE2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
	INNER JOIN Ubicacion ubi2 ON ubiE2.ubeUbicacion = ubi2.ubiId
	INNER JOIN Municipio mun2 ON (ubi2.ubiMunicipio = mun2.munId)
	INNER JOIN Departamento dep2 ON (mun2.munDepartamento = dep2.depId)



	left JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
	left JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')

	LEFT JOIN ( 

			SELECT 
				apg.apgPersona, 
				max(apg.apgFechaProcesamiento) AS fechaUltimo,
				apg.apgValTotalApoObligatorio
			FROM AporteGeneral apg 
			WHERE 
				apg.apgPersona IS NOT NULL AND
				apg.apgEstadoAportante = 'ACTIVO' 
			GROUP BY
				apg.apgPersona,
				apg.apgValTotalApoObligatorio

		UNION 
	
			SELECT 
				emp.empPersona, 
				max(apg.apgFechaProcesamiento) AS fechaUltimo,
				apg.apgValTotalApoObligatorio
			FROM 
				AporteGeneral apg 
				JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
			WHERE
				apg.apgEstadoAportante = 'ACTIVO'
			GROUP BY 
				emp.empPersona,
				apg.apgValTotalApoObligatorio

	) AS personaAporte ON personaAporte.apgPersona = per.perId

WHERE 
	solAfiEmpleador.saeEstadoSolicitud IN ( 'CERRADA', 'APROBADA' ) 
	AND sol.solFechaRadicacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 

GROUP BY
		cns.cnsValor,
		prm.prmValor,
		PER.perPrimerNombre,
		per.perPrimerApellido,
		perSegundoNombre,
		perSegundoApellido,
		perDigitoVerificacion,
		per.perRazonSocial,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,
		perTipoIdentificacion,
		ubi.ubiDireccionFisica,
		dep.depCodigo,
		mun.munCodigo,
		dep.depNombre,
		mun.munNombre,
		ubi.ubiTelefonoFijo,
		ubi.ubiIndicativoTelFijo,
		ubi.ubiTelefonoCelular,
		ubi.ubiEmail,
		ubi2.ubiDireccionFisica,
		dep2.depCodigo,
		mun2.munCodigo,
		dep2.depNombre,
		mun2.munNombre,
		ubi2.ubiTelefonoFijo,
		ubi2.ubiIndicativoTelFijo,
		ubi2.ubiTelefonoCelular,
		ubi2.ubiEmail,
		personaAporte.apgValTotalApoObligatorio

END