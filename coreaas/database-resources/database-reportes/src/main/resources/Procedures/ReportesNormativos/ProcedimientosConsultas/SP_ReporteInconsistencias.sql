
---update 20220816 POR OLGA VEGA CONCEPTO 2 Y 3 cambio para ver las fechas de actividad en el periodo para 2 y 3 y en concepto 1 menor a la fecha de generacion
---fecha del último comunicado anterior a la fecha de generacion para los 2 y 3 glpi 44535
---UPDATE 20220824 POR OLGA VEGA ADICION A LOS CONCEPTOS 2 Y 3 COMUNICADOS PARA QUE LOS SALGAN EN ESTE REPORTE SIEMPRE CUANDO TENGAN UN COMUNICADO EXITOSO 
---EXEC [reporteInconsistencias] '2023-01-01', '2024-08-15'
---reporte 21
CREATE OR ALTER PROCEDURE [dbo].[reporteInconsistencias] ( @fechaInicio DATE, @fechaFin DATE )
AS
BEGIN
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

declare @fechaInicioRev BIGINT, @fechaFinRev bigInt
--declare @fechaInicio date = '2024-04-01'
--declare @fechaFin date = '2024-06-30'
set @fechaInicioRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @fechaInicio)
set @fechaFinRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @fechaFin)
 print(@fechaInicioRev)print(@fechaFinRev)
drop table if exists #EstadoAfiliadoPersona
drop table if exists #cteEstadosAFIPER
drop table if exists #EstadoAportantePersona
drop table if exists #Inconsistencias_prew
drop table if exists #Inconsistencias
	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------------------------------------------------------
	------------------SE ESTABLECEN LOS ESTADOS DE LOS AFILIADOS Y APORTANTES.
	-------------------------------------------------------------------------
	
	-------------------------------------------------------------------------------------
	---------- Estado Afiliado Persona
	-------------------------------------------------------------------------------------
	SELECT estado, prioridad
	INTO #cteEstadosAFIPER
	FROM (
		VALUES
			('ACTIVO',1),
			('INACTIVO', 2),
			('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
			('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',4),
			('NO_FORMALIZADO_CON_INFORMACION',5),
			(NULL,99)
		) AS T (estado, prioridad)	
	
	-------------------------
	--EMPLEADORES CON APORTES
	-------------------------
	select distinct p.perId,p.perTipoIdentificacion, p.perNumeroIdentificacion,pe.perId as perIdEmpleador,pe.perTipoIdentificacion as perTipoIdentificacionEmpleador,pe.perNumeroIdentificacion as perNumeroIdentificacionEmpleador,
		Empleador.empId as roaEmpleador,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' roaEstadoAfiliado
	into  #EstadoAfiliadoPersona
	from Persona p
	inner join AporteDetallado2 apd with(nolock) on apd.apdPersona = p.PerId
	inner join AporteGeneral2 apg with(nolock) on apg.apgId = apd.apdAporteGeneral
	inner join Empresa with(nolock) on apg.apgEmpresa = Empresa.EmpId
	inner join Cartera c with(nolock) on c.carPersona= empPersona--------cambio prueba
	inner join Persona pe with(nolock) on pe.perId = Empresa.empPersona
	left join Empleador with(nolock) on Empleador.empEmpresa = Empresa.empId
	WHERE 
		NOT EXISTS (
			SELECT 1
			FROM 
				Afiliado
				INNER JOIN RolAfiliado  WITH(NOLOCK) ON Afiliado.afiId = RolAfiliado.roaAfiliado
				INNER JOIN Empleador  WITH(NOLOCK) ON RolAfiliado.roaEmpleador = Empleador.empId
			WHERE 
				Afiliado.afiPersona =  p.perId
				AND Empresa.empId = Empleador.empEmpresa
				AND roaEstadoAfiliado IS NOT NULL
		)
	GROUP BY 
		p.perId,p.perTipoIdentificacion,p.perNumeroIdentificacion,pe.perId,pe.perTipoIdentificacion,pe.perNumeroIdentificacion,Empleador.empId

	------------------------------------
	UNION ALL
	------------------------------------

	------------------------------------
	--EMPLEADORES
	-------------

	SELECT
		Persona.perId,
		Persona.perTipoIdentificacion,
		Persona.perNumeroIdentificacion,
		NULL AS perIdEmpleador,
		NULL AS perTipoIdentificacionEmpleador,
		NULL AS perNumeroIdentificacionEmpleador,
		CAST(NULL AS BIGINT) AS roaEmpleador,
		'NO_FORMALIZADO_CON_INFORMACION' roaEstadoAfiliado
	FROM Persona
	WHERE
		NOT EXISTS (
			SELECT 1
			FROM 
				Afiliado
				INNER JOIN RolAfiliado  WITH(NOLOCK) ON Afiliado.afiId = RolAfiliado.roaAfiliado
				INNER JOIN Empleador em   WITH(NOLOCK) ON RolAfiliado.roaEmpleador = Em.empId
				inner join empresa e  WITH(NOLOCK) on e.empid= em.empempresa--------cambio prueba
				INNER JOIN Cartera c  WITH(NOLOCK) ON c.carPersona= empPersona--------cambio prueba
			WHERE 
				Afiliado.afiPersona =  Persona.perId
				AND roaEstadoAfiliado IS NOT NULL
		)AND NOT EXISTS (	
			SELECT 1
			FROM 
				AporteGeneral2 apg  WITH(NOLOCK) 
				INNER JOIN AporteDetallado2 apd  WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
			WHERE apd.apdPersona = Persona.PerId
		)
	
	------------------------------------
	------------------------------------
	  CREATE CLUSTERED INDEX ids ON   #EstadoAfiliadoPersona (PERID ,roaestadoafiliado )
	  print('#EstadoAfiliadoPersona')
	INSERT	INTO #EstadoAfiliadoPersona--- CAMBIO
	SELECT 
		perId, 
		perTipoIdentificacion, 
		perNumeroIdentificacion,
		perIdEmpleador, 
		perTipoIdentificacionEmpleador, 
		perNumeroIdentificacionEmpleador,
		roaEmpleador,
		cte.estado
	FROM (
		-------------------------------------------------------------------------------------------------
		SELECT
			Persona.perId,
			Persona.perTipoIdentificacion,
			Persona.perNumeroIdentificacion,
			PersonaEmpleador.perId AS perIdEmpleador,
			PersonaEmpleador.perTipoIdentificacion AS perTipoIdentificacionEmpleador,
			PersonaEmpleador.perNumeroIdentificacion AS perNumeroIdentificacionEmpleador,
			roaEmpleador,
			MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM 
			Persona  WITH(NOLOCK) 
			CROSS JOIN Persona PersonaEmpleador  WITH(NOLOCK) 
			INNER JOIN Empresa  WITH(NOLOCK)  ON PersonaEmpleador.perId = Empresa.empPersona
			INNER JOIN Cartera c  WITH(NOLOCK) ON c.carPersona= empPersona--------cambio prueba
			INNER JOIN Afiliado  WITH(NOLOCK) ON Persona.perId = Afiliado.afiPersona
			INNER JOIN RolAfiliado  WITH(NOLOCK) 
				ON Afiliado.afiId = RolAfiliado.roaAfiliado 
				AND roaEstadoAfiliado IS NOT NULL
			INNER JOIN Empleador  WITH(NOLOCK) 
				ON RolAfiliado.roaEmpleador = Empleador.empId 
				AND Empresa.empId = Empleador.empEmpresa
								
			LEFT JOIN (
				SELECT 
					apd.apdPersona, 
					MAX(CONVERT(DATE, apg.apgPeriodoAporte + '-01', 121)) apgPeriodoAporte, 
					apg.apgEmpresa
				FROM 
					AporteGeneral2 apg
					INNER JOIN AporteDetallado2 apd ON apg.apgId = apd.apdAporteGeneral
				GROUP BY 
					apd.apdPersona, 
					apg.apgEmpresa
			) apg ON apg.apdPersona = Persona.PerId  AND apg.apgEmpresa = Empresa.empId

			LEFT JOIN #cteEstadosAFIPER cte ON ISNULL((
				CASE 
					WHEN apg.apdPersona IS NOT NULL AND ISNULL(roaEstadoAfiliado, 'INACTIVO') = 'INACTIVO' 
					THEN (
						CASE 
							WHEN roaFechaRetiro < apg.apgPeriodoAporte 
							THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' 
							ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') 
						END
					)
					ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), CASE WHEN apg.apdPersona IS NOT NULL THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE 'NO_FORMALIZADO_CON_INFORMACION' END) 
				END),'') = ISNULL(cte.estado,'')

		GROUP BY 
			Persona.perId,
			Persona.perTipoIdentificacion,
			Persona.perNumeroIdentificacion,
			PersonaEmpleador.perId,
			PersonaEmpleador.perTipoIdentificacion,
			PersonaEmpleador.perNumeroIdentificacion,
			roaEmpleador
		-------------------------------------------------------------------------------------------------
		) T

		INNER JOIN #cteEstadosAFIPER cte ON T.prioridad = cte.prioridad


 print('Estado Aportante')
		-------------------------------------------------------------------------------------------------
		---------- Estado Aportante
		-------------------------------------------------------------------------------------------------
		SELECT 
			perId,
			perTipoIdentificacion,
			perNumeroIdentificacion,
			CASE 
				WHEN Empresa.empId IS NOT NULL 
				THEN 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' 
			END empEstadoEmpleador 
		INTO #EstadoAportantePersona 
		FROM 
			Persona
			INNER JOIN Empresa  WITH(NOLOCK) ON Persona.perId = Empresa.empPersona
			LEFT JOIN Cartera c  WITH(NOLOCK) ON c.carPersona= empPersona--------cambio prueba
			LEFT JOIN  Empleador  WITH(NOLOCK) ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
		WHERE 
			Empleador.EmpId IS NULL
			AND EXISTS (	
				SELECT 1
				FROM AporteGeneral2 
				WHERE AporteGeneral2.apgEmpresa = Empresa.EmpId
			)
	
		UNION ALL

		SELECT 
			perId,
			perTipoIdentificacion,
			perNumeroIdentificacion,
			CASE 
				WHEN Empresa.empId IS NOT NULL 
				THEN 'NO_FORMALIZADO_CON_INFORMACION' 
			END empEstadoEmpleador
		FROM 
			Persona
			INNER JOIN Empresa  WITH(NOLOCK) ON Persona.perId = Empresa.empPersona
			INNER JOIN Cartera c  WITH(NOLOCK) ON c.carPersona= empPersona--------cambio prueba
			LEFT JOIN  Empleador  WITH(NOLOCK) ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
		WHERE 
			Empleador.EmpId IS NULL
			AND NOT EXISTS (
				SELECT 1
				FROM AporteGeneral2 
				WHERE AporteGeneral2.apgEmpresa = Empresa.EmpId
			)
	
		UNION ALL
	
		SELECT 
			perId, 
			perTipoIdentificacion, 
			perNumeroIdentificacion, 
			cte.estado roaEstadoAfiliado
		FROM (
			SELECT 
				perId,
				perTipoIdentificacion,
				perNumeroIdentificacion, 
				MIN(ISNULL(cte.prioridad,99)) prioridad
			FROM 
				Persona  WITH(NOLOCK) 
				INNER JOIN Empresa  WITH(NOLOCK) ON Persona.perId = Empresa.empPersona
				INNER JOIN Cartera c  WITH(NOLOCK) ON c.carPersona= empPersona--------cambio prueba
				INNER JOIN Empleador  WITH(NOLOCK) ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
				LEFT JOIN (
					SELECT 
						AporteGeneral2.apgEmpresa, 
						MAX(CONVERT(DATE, apgPeriodoAporte+'-01', 121)) apgPeriodoAporte 
					FROM 
						AporteGeneral2 WITH(NOLOCK)
					GROUP BY 
						AporteGeneral2.apgEmpresa
				) apg ON apg.apgEmpresa = Empresa.EmpId
				LEFT JOIN #cteEstadosAFIPER cte ON ISNULL((
					CASE 
						WHEN apg.apgEmpresa IS NOT NULL AND empEstadoEmpleador = 'INACTIVO' 
						THEN (CASE WHEN empFechaRetiro < apg.apgPeriodoAporte THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE empEstadoEmpleador END)
						ELSE empEstadoEmpleador 
					END),'') = ISNULL(cte.estado,'')
			GROUP BY 
				perId,
				perTipoIdentificacion,
				perNumeroIdentificacion 
		) T
		INNER JOIN #cteEstadosAFIPER cte ON T.prioridad = cte.prioridad

	  CREATE CLUSTERED INDEX ids ON   #EstadoAportantePersona (PERID ,empEstadoEmpleador )

	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------------------
	----REPORTE POR INEXACTITUD 
	print('REPORTE POR INEXACTITUD ')
	SELECT 
		IDENTITY(int, 1, 1) AS new_id,
		----------TIPO ADMIN
		'CCF' AS [TIPO_ADMIN],
		----------COD_ADMIN
		(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS [COD_ADMIN],
		----------NOMBRE_ADMIN
		(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') AS [NOMBRE_ADMIN],
		PEREMP.perId as idEmpleador,
		----------TIPO_DOCUMENTO_APT
		CASE
			WHEN PEREMP.perTipoIdentificacion = 'NIT' THEN 'NI'
			WHEN PEREMP.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN PEREMP.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN PEREMP.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN PEREMP.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
			WHEN PEREMP.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
			WHEN PEREMP.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN PEREMP.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN PEREMP.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC'
			WHEN PEREMP.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' THEN 'PT'
		END AS [TIPO_DOCUMENTO_APT],
		----------NÚMERO_APT
		PEREMP.perNumeroIdentificacion AS [NUMERO_APT],
		----------
			CASE 
			WHEN ISNULL(PEREMP.perPrimerNombre, '') = '' THEN PEREMP.perRazonSocial
			ELSE CONCAT( ISNULL(PEREMP.perPrimerNombre, '')  ,' ',  ISNULL(PEREMP.perSegundoNombre, '')  ,' ', ISNULL(PEREMP.perPrimerApellido, '') , ' ', ISNULL(PEREMP.perSegundoApellido, '')  )
			END
		  AS [RAZON_SOCIAL_APT],
		----------
		dep.depCodigo AS [ID_DEPARTAMENTO],
		----------
		substring(mun.munCodigo,3,6) AS [ID_MUNICIPIO],
		----------
		CASE
			WHEN ubi.ubiDireccionFisica <> 'SIN DIRECCION'
			THEN ubi.ubiDireccionFisica 
			ELSE ubiCo.ubiDireccionFisica
		END AS [DIRECCION],
		----------
		ptr.perId as idCotizante,
		----------
		CASE    
			WHEN ptr.perTipoIdentificacion = 'NIT' THEN 'NI'
			WHEN ptr.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN ptr.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN ptr.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN ptr.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
			WHEN ptr.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
			WHEN ptr.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN ptr.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN ptr.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC'
			WHEN ptr.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' THEN 'PT'
		END AS [TIPO_DOCUMENTO_COT],
		----------
		ptr.perNumeroIdentificacion AS [NUMERO_COT],
 ------
	     '1'   AS [CONCEPTO],
		----------
		DATEPART(YEAR, car.carPeriodoDeuda) AS [ANIO_INICIO],
		----------
		DATEPART(MONTH,  car.carPeriodoDeuda) AS [MES_INICIO],
		----------
		DATEPART(YEAR, car.carPeriodoDeuda) AS [ANIO_FINAL],
		----------
		DATEPART(MONTH, car.carPeriodoDeuda) AS [MES_FINAL],
		----------
		CONVERT(NUMERIC(9, 0), ISNULL( CAD.cadDeudaPresunta , 0)) [VALOR_CONCEPTO],---cambio 20221025 olga vega por deuda individual
		----------
		A.accion AS [TIPO_DE_ACCION],
		A.fecha AS [FECHA_ACCION],
	    INEX.Desconc AS [OBSERVACIONES],

		APG.apgEstadoAportante,
		APG.apgModalidadRecaudoAporte, 
		inex.apgEmpresa,
		ROW_NUMBER() OVER (PARTITION BY ptr.perId,PEREMP.perId ORDER BY carPeriodoDeuda) AS RowNum,
		LAG(carPeriodoDeuda) OVER (ORDER BY ptr.perId,PEREMP.perId,carPeriodoDeuda) AS PrevPeriodo,
		isnull(DATEDIFF(month, LAG(carPeriodoDeuda) OVER (ORDER BY ptr.perId,PEREMP.perId,carPeriodoDeuda), carPeriodoDeuda),1) AS MonthDiff
		,carPeriodoDeuda,cad.cadEstadoOperacion
		----------
		INTO #Inconsistencias_prew

	FROM 
		Empleador EMPLE  WITH(NOLOCK)
		INNER JOIN Empresa EMP  WITH(NOLOCK) ON EMP.empid = EMPLE.empEmpresa
		INNER JOIN Persona PEREMP  WITH(NOLOCK) ON EMP.empPersona = PEREMP.perid
		INNER JOIN Cartera CAR  WITH(NOLOCK) ON CAR.carPersona = PEREMP.perId AND carTipoLineaCobro IN ('LC3' ,'LC2') AND car.carEstadoOperacion = 'VIGENTE'
		INNER JOIN CarteraDependiente CAD  WITH(NOLOCK) ON CAR.carId = CAD.cadCartera 
		INNER JOIN Persona PERAFI  WITH(NOLOCK) ON PERAFI.perId = cadpersona
		INNER JOIN Afiliado AFI  WITH(NOLOCK) ON AFI.afipersona = perafi.perid 
		INNER JOIN RolAfiliado ROA  WITH(NOLOCK) ON roa.roaafiliado= afi.afiid AND ROA.roaEmpleador = EMPLE.empId
		INNER JOIN AporteGeneral2 APG  WITH(NOLOCK) ON APG.apgEmpresa = EMP.empId AND APG.apgPeriodoAporte = LEFT(carPeriodoDeuda,7)
		INNER JOIN UbicacionEmpresa UBE  WITH(NOLOCK) ON UBE.ubeEmpresa = EMP.empId AND UBE.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
		INNER JOIN dbo.Ubicacion ubi  WITH(NOLOCK) ON UBE.ubeUbicacion = ubi.ubiId
		LEFT JOIN dbo.Municipio mun  WITH(NOLOCK) ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep  WITH(NOLOCK) ON mun.munDepartamento = dep.depId
		LEFT JOIN UbicacionEmpresa ubeCo  WITH(NOLOCK) ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
		LEFT JOIN dbo.Ubicacion ubiCo  WITH(NOLOCK) ON ubeCo.ubeUbicacion = ubiCo.ubiId
		LEFT JOIN dbo.Municipio munCo  WITH(NOLOCK) ON ubiCo.ubiMunicipio = munCo.munId
		LEFT JOIN dbo.Departamento depCo  WITH(NOLOCK) ON munCo.munDepartamento = depCo.depId
		LEFT JOIN dbo.Persona ptr  WITH(NOLOCK) ON ptr.perId = CAD.cadPersona
		INNER JOIN (
			SELECT 
				accion,
				MAX(fecha) AS fecha, 
				carId, Actividad
			FROM (

				SELECT DISTINCT
					CASE
						WHEN acrActividadCartera IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN '4'
						WHEN acrActividadCartera IN ('SOLICITAR_DOCUMENTACION') THEN '1'
						ELSE '3'
					END accion,
					CONVERT(DATE, acrFecha, 121) fecha, 
					acrCartera carId,
					NULL AS Actividad

			   	FROM ActividadCartera WITH(NOLOCK) 
				WHERE 
					acrCartera IS NOT NULL
					AND acrFecha BETWEEN @fechaInicio AND @fechaFin
				
				--------------------------------
				UNION ALL
				--------------------------------

				SELECT DISTINCT
					CASE
						WHEN agrVisitaAgenda IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN '4'
						WHEN agrVisitaAgenda IN ('VISITA', 'VISITA_A_CAJA') THEN '5'
						ELSE NULL
					END accion, 
					agrFecha fecha, 
					agrCartera carId, NULL AS  Actividad

				FROM AgendaCartera WITH(NOLOCK) 
				WHERE 
					agrCartera IS NOT NULL
					AND agrFecha<@fechaFin
				
				--------------------------------
				UNION ALL
				--------------------------------
				select accion,fecha,carid,bcaactividad from (SELECT DISTINCT 
					CASE   
						WHEN bcaMedio IN ('DOCUMENTO_FISICO') AND bcaResultado = 'EXITOSO' THEN '1'
						WHEN bcaMedio IN ('ELECTRONICO') AND bcaResultado = 'EXITOSO' THEN '3'
						ELSE NULL
					END accion, 
					bcaFecha fecha, 
					carid,bcaActividad,
					row_number() over (partition by carid order by bcaFecha desc) as id
				FROM 
					BitacoraCartera  WITH(NOLOCK) 
					inner join cartera  WITH(NOLOCK) on carpersona = bcapersona 
				WHERE 
					carid IS NOT NULL
					--AND datediff(day ,bcaFecha , @fechafin)>30---mod olga vega 20221025		 
					AND bcaActividad  IN ('LC2A','LC3A','NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE')
					AND bcaResultado = 'EXITOSO') s where s.id =1-----Modificacion olga vega 20221025
			) AS T

			WHERE accion IS NOT NULL
			GROUP BY accion,carId, Actividad
		) AS A ON A.carId = car.carId
		----------------------------------------------------------------------------------------------------------------------
		-- INEXACTITUDES
		----------------------------------
		INNER JOIN (
			SELECT distinct
				apg.apgEmpresa,apd.apdPersona , 	
				CASE 
					WHEN redOUTEstadoValidacionV3 = 'NO_OK' 
					THEN 12 --17 
					ELSE 
						CASE 
							WHEN redOUTEstadoValidacionV2 = 'NO_OK' 
							THEN 7 --7 
							ELSE 
								CASE 
									WHEN redOUTEstadoValidacionV1 = 'NO_OK' 
									THEN 9 --13 
									ELSE 
										CASE 
											WHEN redOUTEstadoValidacionV0 = 'NO_CUMPLE' 
											THEN 11 --13 
												ELSE
											CASE 
											WHEN carTipoLineaCobro = 'LC2' 
											THEN 5 --3
											END
										END 
								END 
						END 
				END AS Desconc,clc3.carId
			FROM
				AporteGeneral2 apg  WITH(NOLOCK) 
				INNER JOIN aportedetallado2 apd  WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
				INNER JOIN RegistroDetallado2 red  WITH(NOLOCK) ON red.redId = apd.apdRegistroDetallado
				INNER JOIN carteradependiente cad  WITH(NOLOCK) on cadpersona = apd.apdpersona 
				LEFT JOIN cartera clc3 on clc3.carid = cad.cadcartera and apgPeriodoAporte = LEFT(carPeriodoDeuda,7) 
				      AND clc3.carTipoLineaCobro IN ('LC3' ,'LC2') ---cambio 20221025
					  AND clc3.carEstadoOperacion = 'VIGENTE'---cambio 20221025			
					  and carPeriodoDeuda BETWEEN @fechaInicio AND @fechaFin
		) inex 
			ON inex.apgEmpresa = emp.empId 
			AND inex.apdPersona = ptr.perId
			AND inex.carid = cad.cadcartera 
			AND Desconc is not null

		INNER JOIN #EstadoAportantePersona est on est.perid = PEREMP.perId --idEmpleador
		LEFT JOIN #EstadoAfiliadoPersona  estAfiPer on estAfiPer.perid = ptr.perId and estAfiPer.perIdEmpleador=PEREMP.perId --idCotizante
		INNER JOIN Parametro AS paramSMMLV on prmNombre = 'SMMLV'
	where est.empestadoempleador NOT IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') 
		and carPeriodoDeuda BETWEEN @fechaInicio AND @fechaFin
		--and ptr.perNumeroIdentificacion in ('39790609','10272176','10171242') 	
		
	group by 
		PEREMP.perId,PEREMP.perNumeroIdentificacion,PEREMP.perTipoIdentificacion,PEREMP.perPrimerNombre,PEREMP.perSegundoNombre,PEREMP.perSegundoApellido,PEREMP.perPrimerApellido,
		PEREMP.perRazonSocial,dep.depCodigo,mun.munCodigo,ubi.ubiDireccionFisica,ptr.perId,ptr.perTipoIdentificacion,ptr.perNumeroIdentificacion,car.carPeriodoDeuda
		,ubiCo.ubiDireccionFisica,car.carEstadoOperacion,cad.cadDeudaPresunta
		,apgModalidadRecaudoAporte,apgModalidadRecaudoAporte,apgEstadoAportante,apgModalidadRecaudoAporte,prmValor,inex.apgEmpresa,A.accion,a.fecha,
		apgEstadoAportante,apgModalidadRecaudoAporte,est.empEstadoEmpleador,APG.apgEmpresa,estAfiPer.roaEstadoAfiliado,inex.Desconc
		
		,cad.cadEstadoOperacion

	select TIPO_ADMIN,COD_ADMIN,NOMBRE_ADMIN,idEmpleador,TIPO_DOCUMENTO_APT,NUMERO_APT,RAZON_SOCIAL_APT,ID_DEPARTAMENTO,ID_MUNICIPIO,DIRECCION,idcotizante,
	TIPO_DOCUMENTO_COT,NUMERO_COT,CONCEPTO, year(min(periodo)) ANIO_INICIO, month(min(periodo)) MES_INICIO, year(max(periodo)) ANIO_FINAL, month(max(periodo)) MES_FINAL
	, sum(VALOR_CONCEPTO) as VALOR_CONCEPTO,TIPO_DE_ACCION,FECHA_ACCION,OBSERVACIONES,grupo_id 
	 into #Inconsistencias
	 from
	(select * ,convert(date,concat(anio_inicio,'-',mes_inicio,'-01')) as periodo, sum(sub_grupo) over (order by idcotizante,idempleador, anio_inicio, mes_inicio) as grupo_id 
		from (select  TIPO_ADMIN,COD_ADMIN,NOMBRE_ADMIN,idEmpleador,TIPO_DOCUMENTO_APT,NUMERO_APT,RAZON_SOCIAL_APT,ID_DEPARTAMENTO,ID_MUNICIPIO,DIRECCION,idcotizante
		,TIPO_DOCUMENTO_COT,NUMERO_COT,CONCEPTO,ANIO_INICIO,MES_INICIO,TIPO_DE_ACCION,FECHA_ACCION,OBSERVACIONES,
	case 
	when abs(lag(mes_inicio) over (partition by idcotizante, idempleador order by anio_inicio asc, mes_inicio asc) - mes_inicio)=1
	and lag(anio_inicio) over (partition by idcotizante, idempleador order by  anio_inicio asc, mes_inicio asc) =anio_inicio
	then 0
	 when anio_inicio-1= lag(anio_inicio) over (partition by idcotizante, idempleador order by  anio_inicio asc, mes_inicio asc) 
	 and mes_inicio=1 and lag(mes_inicio) over (partition by idcotizante, idempleador order by anio_inicio asc, mes_inicio asc) =12
	then 0
	else 1
	end as sub_grupo,VALOR_CONCEPTO
	from #Inconsistencias_prew	
	where cadEstadoOperacion = 'VIGENTE'
	) t)t2
	group by TIPO_ADMIN,COD_ADMIN,NOMBRE_ADMIN,idEmpleador,TIPO_DOCUMENTO_APT,NUMERO_APT,RAZON_SOCIAL_APT,ID_DEPARTAMENTO,ID_MUNICIPIO,DIRECCION,idCotizante,
	TIPO_DOCUMENTO_COT,NUMERO_COT,CONCEPTO,TIPO_DE_ACCION,FECHA_ACCION,OBSERVACIONES
	, grupo_id




 --------------------  La generación del Reporte se deja hasta aquí ya que las acciones por ( Omiso Afiliación y Omiso Vinculación) aún no estan definidas

 
 	----CAMBIO
		 CREATE CLUSTERED INDEX ids ON   #Inconsistencias (idEmpleador ,idcotizante )
	

	
	-----GUARDAR LA HISTORIA PARA VALIDACIONES DEL CONCEPTO 2 Y 3  

	print ('GUARDAR LA HISTORIA PARA VALIDACIONES DEL CONCEPTO')
	DELETE rno.HistoricoInconsistenciasUGPP 
	WHERE LEFT(hinFechaInicialReporte,7) = LEFT(@fechaInicio,7) AND LEFT(hinFechaFinalReporte,7)= LEFT(@fechaFin,7)

	INSERT INTO rno.HistoricoInconsistenciasUGPP

	SELECT  GETDATE(),XXX.TIPO_ADMIN AS TIPO_ADMINISTRADORA,
			XXX.COD_ADMIN AS COD_ADMINISTRADORA,
			XXX.NOMBRE_ADMIN AS NOMBRE_ADMINISTRADORA,
			XXX.TIPO_DOCUMENTO_APT AS TIPO_DOCUMENTO_APORTANTE,
			XXX.NUMERO_APT AS NUMERO_APORTANTE,
			XXX.RAZON_SOCIAL_APT AS RAZON_SOCIAL_APORTANTE,

			XXX.ID_DEPARTAMENTO,
			XXX.ID_MUNICIPIO,
			XXX.DIRECCION,

			XXX.TIPO_DOCUMENTO_COT AS TIPO_DOCUMENTO_COTIZANTE,
			XXX.NUMERO_COT AS NUMERO_COTIZANTE,
			XXX.CONCEPTO,

			XXX.ANIO_INICIO,
			XXX.MES_INICIO,
			XXX.ANIO_FINAL,
			XXX.MES_FINAL,
			XXX.VALOR_CONCEPTO,
			XXX.TIPO_DE_ACCION,
			XXX.FECHA_ACCION AS FECHA_ACCION,

			XXX.OBSERVACIONES AS DESCRIPCION_CONCEPTO,
			 @fechaInicio  ,
			@fechaFin
		FROM (
			SELECT 
				
				TIPO_ADMIN,COD_ADMIN,NOMBRE_ADMIN,TIPO_DOCUMENTO_APT,NUMERO_APT,RAZON_SOCIAL_APT,ID_DEPARTAMENTO,ID_MUNICIPIO,DIRECCION,TIPO_DOCUMENTO_COT,NUMERO_COT,CONCEPTO,ANIO_INICIO,MES_INICIO,ANIO_FINAL,MES_FINAL,VALOR_CONCEPTO,TIPO_DE_ACCION,FECHA_ACCION,OBSERVACIONES

			FROM 
				#Inconsistencias tempInconsistencias
				left join #EstadoAportantePersona est on est.perid = idEmpleador
			GROUP BY 
				TIPO_ADMIN,COD_ADMIN,NOMBRE_ADMIN,TIPO_DOCUMENTO_APT,NUMERO_APT,RAZON_SOCIAL_APT,ID_DEPARTAMENTO,ID_MUNICIPIO,DIRECCION,TIPO_DOCUMENTO_COT,NUMERO_COT,CONCEPTO,ANIO_INICIO,MES_INICIO,ANIO_FINAL,MES_FINAL,VALOR_CONCEPTO,TIPO_DE_ACCION,FECHA_ACCION,OBSERVACIONES
		
 		)XXX

		print('DEPURANDO LO QUE YA ESTUVO REPORTADO')
		------DEPURANDO LO QUE YA ESTUVO REPORTADO
		DELETE A
		FROM rno.HistoricoInconsistenciasUGPP A
		INNER JOIN rno.HistoricoInconsistenciasUGPP B ON A.hinTipoIdAportante = B.hinTipoIdAportante AND a.hinNumIdAportante = b.hinNumIdAportante AND	a.hinTipoIdCotizante = b.hinTipoIdCotizante AND	a.hinNumIdCotizante = b.hinNumIdCotizante AND	a.hinConcepto = b.hinConcepto AND A.hinFechaInicialReporte>b.hinFechaFinalReporte
		WHERE a.hinUltimaAccion IN (2,3)
		  AND LEFT(A.hinFechaInicialReporte,7) = LEFT(@Fechainicio,7) 
		  AND LEFT(a.hinFechaFinalReporte,7) = LEFT(@Fechafin,7)

		SELECT		
					'' [NO. RADICADO DE LA DENUNCIA], '' [FECHA DE RADICADO DE LA DENUNCIA],XXX.hinTipoAdmin AS TIPO_ADMINISTRADORA,XXX.hinCodAdmin AS COD_ADMINISTRADORA,XXX.hinNomAdmin AS NOMBRE_ADMINISTRADORA,XXX.hinTipoIdAportante AS TIPO_DOCUMENTO_APORTANTE,XXX.hinNumIdAportante AS NUMERO_APORTANTE,XXX.hinRazonSocial AS RAZON_SOCIAL_APORTANTE,
					XXX.hinCodigoDep AS ID_DEPARTAMENTO,XXX.hinCod AS ID_MUNICIPIO,XXX.hinDireccion as DIRECCION,
					isnull((select top 1 u.ubiEmail from Persona p
						inner join Empresa epr on p.perId = epr.empPersona
						inner join UbicacionEmpresa ube on epr.empId = ube.ubeEmpresa
						inner join Ubicacion u on u.ubiId = ube.ubeUbicacion and ube.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
						where p.perNumeroIdentificacion = XXX.hinNumIdAportante),'') [EMAIL],
					XXX.hinTipoIdCotizante AS TIPO_DOCUMENTO_COTIZANTE,XXX.hinNumIdCotizante AS NUMERO_COTIZANTE,XXX.hinConcepto AS CONCEPTO,XXX.hinAnioInicio AS ANIO_INICIO,XXX.hinMesInicio AS MES_INICIO,XXX.hinAnioFin AS ANIO_FINAL,XXX.hinMesFin AS MES_FINAL,XXX.hinDeuda AS VALOR_CONCEPTO,
					XXX.hinUltimaAccion AS TIPO_DE_ACCION,XXX.hinFechaAccion AS FECHA_ACCION,XXX.hinObservaciones AS DESCRIPCION_CONCEPTO
			FROM rno.HistoricoInconsistenciasUGPP XXX
			WHERE XXX.hinFechaInicialReporte = @Fechainicio AND XXX.hinFechaFinalReporte = @Fechafin 
END;