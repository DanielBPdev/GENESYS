/****** Object:  StoredProcedure [dbo].[reporteEmpresasMora]    Script Date: 2023-07-06 6:29:40 PM ******/
---exec reporteEmpresasMora '2023-06-01','2023-06-30'
CREATE OR ALTER PROCEDURE [dbo].[reporteEmpresasMora](
	@FECHA_INICIO DATE,
	@FECHA_FINAL DATE
)

AS
BEGIN
--SET ANSI_WARNINGS OFF
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

--/---------------------------------------**********---------------------------------------\--
--                          REPORTE DE EMPRESAS EN MORA  -  N° 4.
--\---------------------------------------**********---------------------------------------/--
---update 20220805
--------------------------------------------
----------SE EXTRAEN DATOS DE AUDITORIA 
--------------------------------------------
DECLARE @sql NVARCHAR(4000)

SET @sql = 
	'		
	SELECT 
		car.carPersona perAportante, 
		car.carDeudaPresuntaUnitaria,
		per.perNumeroIdentificacion,
		car.carEstadoOperacion estado, 
		car.carFechaCreacion fechaInicio,
		MAX(DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') fecRev, 
		car.carId, 
		MAX(car.REV) rev

	FROM 
		Cartera_aud car
		INNER JOIN Persona_aud per ON car.carPersona = per.perId
		INNER JOIN Revision rev ON rev.revId = car.REV
		
	WHERE 
		car.carTipoLineaCobro IN (''LC1'', ''C6'')
		AND (DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') BETWEEN @fechaInicio AND @fechaFin
		AND DATEDIFF(DAY,car.carFechaCreacion, @fechaFin) > 60
				
		
	GROUP BY 
		car.carPersona, 
		car.carDeudaPresuntaUnitaria, 
		per.perNumeroIdentificacion,
		car.carEstadoOperacion, 
		car.carFechaCreacion, 
		car.carId,
		car.carTipoLineaCobro
	'

IF OBJECT_ID('tempdb.dbo.#tmp_cartera_aud', 'U') IS NOT NULL
	DROP TABLE #tmp_cartera_aud; 

CREATE TABLE #tmp_cartera_aud(
	carPersona VARCHAR(500), 
	carDeudaPresunta BIGINT,
	perNumeroIdentificacion VARCHAR(500),
	estado VARCHAR(500), 
	fechaInicio VARCHAR(500), 
	fecRev VARCHAR(500), 
	carId VARCHAR(500),
	rev VARCHAR(500),

	--carTipoLineaCobro VARCHAR(500),
	
	shard VARCHAR(500)
)

INSERT INTO #tmp_cartera_aud(
	carPersona, 
	carDeudaPresunta,
	perNumeroIdentificacion,
	estado, 
	fechaInicio, 
	fecRev, 
	carId, 
	rev,
	--carTipoLineaCobro,
	shard
)

EXEC sp_execute_remote N'CoreAudReferenceData',
	@sql,
	N'@fechaInicio DATE, @fechaFin DATE',
	@fechaInicio = @FECHA_INICIO, 
	@fechaFin = @FECHA_FINAL

SELECT 
	COUNT( SOLNOVGLO.solTipoTransaccion ) AS CANT,
	roaEmpleador
INTO #COTIZANTES_SOLICITUD
FROM 
	Afiliado afi
	inner join RolAfiliado roa on  roa.roaAfiliado = afi.afiId
	inner join Persona per on per.perId = afi.afiPersona
	inner join SolicitudNovedadPersona SOLNOVPER on SOLNOVPER.snpPersona = per.perId
	LEFT JOIN SolicitudNovedad SOLNOV on SOLNOVPER.snpSolicitudNovedad = SOLNOV.snoId
	LEFT JOIN solicitud SOLNOVGLO on SOLNOVGLO.solId = SOLNOV.snoSolicitudGlobal
WHERE
	SOLNOVGLO.solResultadoProceso IN ('APROBADA')
	AND SOLNOVGLO.solTipoTransaccion IN (
		'RETIRO_TRABAJADOR_DEPENDIENTE'/*, 
		'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL', 
		'SUSPENSION_CAMBIO_ANIO_CALENDARIO_AUTOMATICA'*/
	) 
	AND SOLNOVGLO.solFechaCreacion BETWEEN @FECHA_INICIO AND @FECHA_FINAL
GROUP BY 
	SOLNOVGLO.solTipoTransaccion,
	roaEmpleador

--SELECT * FROM #COTIZANTES_SOLICITUD WHERE roaEmpleador = 14792

--DROP TABLE #COTIZANTES_SOLICITUD

----------SELECT QUE CONTRUYE EL RESUTADO FINAL

SELECT
		CASE per.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'TARJETA_IDENTIDAD' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 3
			WHEN 'CEDULA_EXTRANJERIA' THEN 4
			WHEN 'PASAPORTE' THEN 6
			WHEN 'NIT' THEN 7
			WHEN 'CARNE_DIPLOMATICO' THEN 8
			WHEN 'PERM_ESP_PERMANENCIA' THEN 9
			WHEN 'PERM_PROT_TEMPORAL' THEN 15
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
		END AS [TIP_IDENTIFICACION],--[Tipo de Identificación de la empresa],

		per.perNumeroIdentificacion AS [NUM_IDENTIFICACION_EMPRESA],--[Número de Identificación de la empresa],

		LEFT(
			CASE
				WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial
				ELSE RTrim(Coalesce(per.perPrimerNombre + ' ', '')
							+ Coalesce(per.perSegundoNombre + ' ', '')
							+ Coalesce(per.perPrimerApellido + ' ', '')
							+ Coalesce(per.perSegundoApellido, '')
						)
			END,200
		) AS [NOM_EMPRESA],--[Nombre Empresa],

		mun.munCodigo AS [COD_MUNICIPIO_DANE],--[Código municipio de la empresa],

		CASE 
			WHEN substring(ubi.ubiDireccionFisica,1,100) = '' OR substring(ubi.ubiDireccionFisica,1,100) IS NULL
			THEN 'SIN DIRECCION'
			ELSE substring(ubi.ubiDireccionFisica,1,100) 
		END AS [DIR_EMPRESA],--[Dirección de la Empresa],

		substring(
			Coalesce(perLegal.perPrimerNombre + ' ', '')
			+ Coalesce(perLegal.perSegundoNombre + ' ', '')
			+ Coalesce(perLegal.perPrimerApellido + ' ', '')
			+ Coalesce(perLegal.perSegundoApellido, '')
		,1,250) AS [REP_LEGAL],--[Representante Legal],

		CASE 
			WHEN SUM(cart.saldoMora) = 0
			THEN 0

			WHEN MAX( CANT ) IS NULL OR MAX( CANT ) = 0  
			THEN CONVERT(VARCHAR ,MIN(cart.fechaCreacion),112)
			
			WHEN MAX( CANT ) > 0
			THEN	
				CASE
					WHEN ( SUM(cart.saldoMora) /*- SUM(ISNULL(carDep.saldoMora,0))*/ ) > 0 	--CAST( MAX(ISNULL(carAud.carDeudaPresunta,0) ) AS BIGINT) ) > 0
					THEN  CONVERT(VARCHAR ,MIN(cart.fechaCreacion),112)
					ELSE ''
				END

			ELSE ''
		END AS [FEC_INICIO_MORA],--[Fecha de inicio de la mora],
		CAST(
		CASE 
			WHEN MAX( CANT ) > 0
			THEN
				CASE 
					WHEN ( SUM(carDep.saldoMora) IS NULL OR SUM(carDep.saldoMora) = 0 ) 
					THEN SUM(ROUND(cart.saldoMora, 0)) 
					ELSE SUM(ROUND(cart.saldoMora, 0)) /*- SUM(ROUND(carDep.saldoMora, 0)) */	
				END
			ELSE SUM(ROUND(cart.saldoMora, 0))
		END AS BIGINT) AS [SAL_MORA],--[Saldo en Mora],

		CASE 
			WHEN SUM(cart.saldoMora) = 0
			THEN 0

			WHEN MAX( CANT ) IS NULL OR MAX( CANT ) = 0  
			THEN MAX(cart.periodoMora) 

			WHEN MAX( CANT ) > 0
			THEN	
				CASE
					WHEN ( SUM(cart.saldoMora)/* - CAST( MAX(ISNULL(carDep.saldoMora,0) ) AS BIGINT) */) > 0
					THEN  MAX(cart.periodoMora) 
					ELSE 0
				END

			--ELSE 0
		END 
		
		AS [PER_MORA],--[Periodos en mora],

		/*CASE
			WHEN 
				bitCart.bcaActividad ='B1' OR
				bitCart.bcaActividad ='C1' OR
				bitCart.bcaActividad ='D1' OR
				bitCart.bcaActividad ='E1' OR
				bitCart.bcaActividad ='F1' OR
				bitCart.bcaActividad ='B2' OR
				bitCart.bcaActividad ='C2' OR
				bitCart.bcaActividad ='D2' OR
				bitCart.bcaActividad ='E2' OR
				bitCart.bcaActividad ='F2' OR
				bitCart.bcaActividad ='G2' OR
				bitCart.bcaActividad ='H2'
			THEN '1' 
			ELSE '2'
		END AS [GES_COBRO],--[Gestión del proceso de cobro],*/

		CASE
			WHEN 
				bitCart.bcaActividad is null
			THEN '2' 
			ELSE bitCart.bcaActividad
		END AS [GES_COBRO],--[Gestión del proceso de cobro],
		--cart.carTipoAccionCobro,

		CASE
			WHEN cnp.copFechaRegistro IS NOT NULL THEN 1
			ELSE 2
		END AS [ACU_PAGO],--[Acuerdo de pago],

		CAST( 
			/*CASE 
				WHEN MAX( CANT ) > 0
				THEN 
					CASE 
						WHEN ( SUM(carDep.saldoMora) IS NULL OR SUM(carDep.saldoMora) = 0 ) 
						THEN CAST( MAX(ISNULL(carAud.carDeudaPresunta,0) ) AS BIGINT) 
							/*CASE 
								WHEN SUM(cart.saldoMora) IS NULL OR SUM(cart.saldoMora) = 0 
								THEN MAX( CAST(carAud.carDeudaPresunta AS BIGINT) )
								ELSE SUM(cart.saldoMora) 
							END*/
						ELSE SUM(carDep.saldoMora) 
					END
				ELSE ISNULL(cartRec.carteraRecuperada, 0) 
			END*/ISNULL(cartRec.carteraRecuperada, 0)  AS BIGINT
		) AS [CAR_RECUPERADA],--[Cartera recuperada],

		ISNULL(
			CASE
				WHEN ubiLegal.ubiEmail IS NOT NULL THEN ubiLegal.ubiEmail
				ELSE ubi.ubiEmail
			END,'xxxxx@xxx.com'
		) AS [COR_ELECTRONICO]--[Correo electrónico]
		--,retCotizante.CANT, retCotizante.roaEmpleador, empleador.empId--
		/*declare @FECHA_INICIO date, @FECHA_FINAL date
		set @FECHA_INICIO = '04-01-2022'
set @FECHA_FINAL = '05-26-2022'*/
		
		--select *
		--,emp.empId
		--,cart.carEstadoCartera
		--,cart.saldoMora

	/*declare @FECHA_INICIO date, @FECHA_FINAL date
	set @FECHA_INICIO = '04-01-2022'
	set @FECHA_FINAL = '05-26-2022'
	
	select */
	FROM 
		Persona per

		LEFT JOIN Empresa emp ON per.perId = emp.empPersona
		LEFT JOIN Empleador empleador on empleador.empEmpresa = emp.empId
		
		-----seleccionamos los empleados de la empresa
		LEFT JOIN #COTIZANTES_SOLICITUD AS retCotizante ON retCotizante.roaEmpleador = empleador.empId

		-----final seleccionar empleados
		 

		LEFT JOIN Persona perLegal ON emp.empRepresentanteLegal = perLegal.perId

		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId 
			AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		
		LEFT JOIN Ubicacion ubiLegal ON ubiLegal.ubiId = emp.empUbicacionRepresentanteLegal
		

		LEFT JOIN Ubicacion ubi ON ubi.ubiId = ube.ubeUbicacion
		LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio

		INNER JOIN (
			SELECT  
				car.carPersona, 
				car.carTipoAccionCobro,
				MIN(car.carFechaCreacion) as fechaCreacion,
				SUM(car.carDeudaPresunta) as saldoMora,
				COUNT(DISTINCT car.carPeriodoDeuda) as periodoMora,
				car.carEstadoCartera
			FROM Cartera car
			WHERE 
				--car.cardeudapresunta > 0 AND 
				car.carTipoSolicitante = 'EMPLEADOR'
				AND car.carTipoLineaCobro IN ( 'LC1', 'C6')
				AND DATEDIFF(DAY,car.carFechaCreacion, @FECHA_FINAL) > 60
				AND car.carEstadoOperacion IN ('VIGENTE')
			GROUP BY 
				car.carTipoAccionCobro,car.carPersona, car.carEstadoCartera
		) cart ON cart.carPersona = per.perId --AND cart.saldoMora > 0
		
		
		/*LEFT JOIN BitacoraCartera bitCart 
			ON bitCart.bcaPersona = per.perId 
			AND bitCart.bcaFecha BETWEEN @FECHA_INICIO AND @FECHA_FINAL
			AND bitCart.bcaResultado = 'EXITOSO'*/
		
		LEFT JOIN (
			SELECT DISTINCT BCA2.bcaPersona, MIN(BCA2.bcaActividad) bcaActividad 
			FROM(
				SELECT  
					CASE 
						WHEN 
							bitCart1.bcaActividad ='B1' OR
							bitCart1.bcaActividad ='C1' OR
							bitCart1.bcaActividad ='D1' OR
							bitCart1.bcaActividad ='E1' OR
							bitCart1.bcaActividad ='F1' OR
							bitCart1.bcaActividad ='B2' OR
							bitCart1.bcaActividad ='C2' OR
							bitCart1.bcaActividad ='D2' OR
							bitCart1.bcaActividad ='E2' OR
							bitCart1.bcaActividad ='F2' OR
							bitCart1.bcaActividad ='G2' OR
							bitCart1.bcaActividad ='H2'
						THEN '1'
						ELSE '2'
					END bcaActividad, 
					bitCart1.bcaPersona 
				FROM BitacoraCartera bitCart1
				WHERE 
					bitCart1.bcaFecha BETWEEN @FECHA_INICIO AND @FECHA_FINAL
					AND bitCart1.bcaResultado = 'EXITOSO'
					AND bitCart1.bcaActividad = 'C2'
			)BCA2			
			GROUP BY 
				BCA2.bcaPersona
		) bitCart ON bitCart.bcaPersona = per.perId 
		--BitacoraCartera bitCart 
			--ON 

		/*LEFT JOIN (
			SELECT  
				car_AUD.carPersona, 

				SUM(car_AUD.carDeudaPresunta) as saldoMora,

				car_AUD.carEstadoCartera
			FROM #tmp_cartera_aud car_AUD
			WHERE 
				--car.cardeudapresunta > 0 AND 
				car_AUD.carTipoSolicitante = 'EMPLEADOR'
				AND car_AUD.carTipoLineaCobro IN ( 'LC1', 'C6')
				AND DATEDIFF(DAY,car_AUD.carFechaCreacion, @FECHA_FINAL) > 60
				AND car_AUD.carEstadoOperacion IN ('VIGENTE')
			GROUP BY 
				car.carTipoAccionCobro,car.carPersona, car.carEstadoCartera
		) carAud ON carAud.carPersona = cart.carPersona*/

		LEFT JOIN (
			SELECT  
				car.carPersona,
				car.carTipoAccionCobro,
				SUM(cad.cadDeudaReal) as saldoMora
			FROM 
				Cartera car
				INNER JOIN CarteraDependiente cad on cad.cadCartera = car.carId
			WHERE 
				cad.cadDeudaPresunta > 0
				AND car.carTipoSolicitante = 'EMPLEADOR'
				AND car.carTipoLineaCobro IN ( 'LC1', 'C6')
				AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_FINAL) > 60
				AND car.carEstadoOperacion = 'VIGENTE'
			GROUP BY car.carTipoAccionCobro,car.carPersona

		) carDep ON carDep.carPersona = per.perId AND carDep.carTipoAccionCobro = cart.carTipoAccionCobro

		LEFT JOIN ConvenioPago cnp 
			ON cnp.copPersona = per.perId 
			AND cnp.copEstadoConvenioPago = 'ACTIVO'

		----------CARTERA RECUPERADA
		LEFT JOIN (
			SELECT 
				SUM( apg.apgValTotalApoObligatorio )  carteraRecuperada,
				emp1.empId
				--apg.*, car.*	
			FROM 
				Empresa emp1
				INNER join Persona per on emp1.empPersona = per.perId
				INNER JOIN AporteGeneral2 apg ON apg.apgEmpresa = emp1.empId
				LEFT JOIN Cartera car ON car.carPersona = emp1.empPersona

			WHERE 
				car.carTipoLineaCobro IN ('LC1' , 'C6') 
				AND APG.apgFechaRecaudo BETWEEN @FECHA_INICIO AND @FECHA_FINAL
				--AND FORMAT( DATEADD(MONTH, -1, car.carPeriodoDeuda), 'yyyy-MM') = APG.apgPeriodoAporte
				AND FORMAT( car.carPeriodoDeuda, 'yyyy-MM') = APG.apgPeriodoAporte
				AND DATEDIFF(MONTH,car.carPeriodoDeuda, @FECHA_INICIO) >= 2
				AND car.carEstadoOperacion = 'VIGENTE'
				--AND per.perNumeroIdentificacion = '890309556'
				--LOS APORTES DE PERIODOS EN MORA
				AND apg.apgPeriodoAporte IN (
					SELECT DISTINCT FORMAT(car1.carPeriodoDeuda, 'yyyy-MM')
					FROM Cartera car1
					WHERE 
						car1.carTipoLineaCobro in ('LC1' , 'C6')
						AND car1.carPersona = emp1.empPersona
					GROUP BY 
						car1.carPersona, 
						car1.carPeriodoDeuda
				)
		
			GROUP BY
				emp1.empId
			/*SELECT 
				SUM( apg.apgValTotalApoObligatorio )  carteraRecuperada,
				emp1.empId
		
			FROM 
				Empresa emp1
				--join Persona per on emp1.empPersona = per.perId
				INNER JOIN AporteGeneral apg ON apg.apgEmpresa = emp1.empId
				LEFT JOIN Cartera car ON car.carPersona = emp1.empPersona

			WHERE 
				car.carTipoLineaCobro IN ('LC1' , 'C6') 
				AND APG.apgFechaRecaudo BETWEEN @FECHA_INICIO AND @FECHA_FINAL
				AND FORMAT(car.carPeriodoDeuda, 'yyyy-MM') = APG.apgPeriodoAporte
				AND car.carEstadoOperacion = 'VIGENTE'

				--LOS APORTES DE PERIODOS EN MORA
				AND apg.apgPeriodoAporte IN (
					SELECT DISTINCT FORMAT(car1.carPeriodoDeuda, 'yyyy-MM')
					FROM Cartera car1
					WHERE 
						car1.carTipoLineaCobro in ('LC1' , 'C6')
						AND car1.carPersona = emp1.empPersona
					GROUP BY 
						car1.carPersona, 
						car1.carPeriodoDeuda
				)
		
			GROUP BY
				emp1.empId*/

		) cartRec ON cartRec.empId = emp.empId

	--where per.perNumeroIdentificacion = '890309556'

	GROUP BY
		emp.empId,
		cart.carPersona,
		cart.carEstadoCartera,

		per.perId,
		per.perTipoIdentificacion, 
		per.perRazonSocial, 
		per.perTipoIdentificacion, 
		per.perNumeroIdentificacion,
		per.perPrimerNombre,
		per.perSegundoNombre,
		per.perPrimerApellido, 
		per.perSegundoApellido,
		--cart.fechaCreacion,
		mun.munCodigo,
		ubi.ubiDireccionFisica,
		perLegal.perPrimerNombre,
		perLegal.perSegundoNombre,
		perLegal.perPrimerApellido, 
		perLegal.perSegundoApellido,
		--cart.fechaCreacion, 
		--cart.saldoMora, 
		--cart.periodoMora, 
		--cart.carTipoAccionCobro,
		cnp.copFechaRegistro, 
		ubi.ubiEmail, 
		ubiLegal.ubiEmail, 
		emp.empId,
		--carDep.saldoMora,
		cartRec.carteraRecuperada,
		ubiLegal.ubiEmail,
		bitCart.bcaActividad
		--,retCotizante.CANT, empleador.empId, retCotizante.roaEmpleador--,retCotizante.solTipoTransaccion
		--,carAud.carDeudaPresunta
		--,cart.saldoMora
	ORDER BY per.perNumeroIdentificacion ASC

END