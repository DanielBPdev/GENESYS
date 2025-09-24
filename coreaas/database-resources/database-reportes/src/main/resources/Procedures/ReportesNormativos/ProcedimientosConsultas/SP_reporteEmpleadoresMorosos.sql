/****** Object:  StoredProcedure [dbo].[reporteEmpleadoresMorosos]    Script Date: 16/03/2021 10:27:38 a. m. ******/

CREATE   PROCEDURE [dbo].[reporteEmpleadoresMorosos](
	@FECHA_FINAL DATE,
	@FECHA_INICIO DATE
)


AS
BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

	SELECT
--
		CASE WHEN per.perRazonSocial IS NULL 
			THEN RTrim(
				Coalesce(per.perPrimerNombre + ' ','') 
				+ Coalesce(per.perSegundoNombre + ' ', '')
				+ Coalesce(per.perPrimerApellido + ' ', '')
				+ Coalesce(per.perSegundoApellido, '')
			)	            
			ELSE REPLACE(per.perRazonSocial,'  ',' ') 
		END AS [Nombre de la empresa],
--
		per.perNumeroIdentificacion AS [NIT],
--
		ubi.ubiDireccionFisica AS [Dirección],
--
		mun.munNombre AS [Ciudad],
--
		CONCAT(perLegal.perPrimerNombre,' ',perLegal.perSegundoNombre,' ',perLegal.perPrimerApellido,' ',perLegal.perSegundoApellido) AS [Representante legal],
--
		cart.periodoMora AS [Número de periodos sin pago],
--
		CONVERT(INTEGER, cart.saldoMora)  AS [Presunto valor de cartera] 
		

--FROM 
	/*Persona per
	INNER JOIN Empresa empr ON empr.empPersona = per.perId
	INNER JOIN Empleador empl ON empl.empEmpresa = empr.empId
	INNER JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = empr.empId
	INNER JOIN Ubicacion ubi ON ubi.ubiId = ube.ubeUbicacion
	INNER JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
	INNER JOIN Persona repLeg ON repLeg.perId = empr.empRepresentanteLegal
	INNER JOIN (
		SELECT
			car.carPersona AS carPersona,
			SUM(car.carDeudaPresunta) AS deudaPresunta,
			COUNT(car.carId) AS periodosSinPago,
			MIN(car.carFechaCreacion) AS fechaMora
		FROM Cartera car
		WHERE car.carEstadoOperacion = 'VIGENTE'
			AND car.carEstadoCartera = 'MOROSO'
			AND car.carTipoLineaCobro IN( 'LC1', 'C6')
			AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_PROCESAMIENTO) > 60
			AND car.carDeudaPresunta > 0
		GROUP BY 
			car.carPersona
	) tabla ON tabla.carPersona = per.perId
	
	LEFT JOIN ConvenioPago cop ON cop.copPersona = per.perId AND 
		cop.copEstadoConvenioPago = 'ACTIVO'
	WHERE ubi.ubiDescripcionIndicacion = 'ENVIO_CORRESPONDENCIA' */


	FROM Persona per

		INNER JOIN Empresa emp ON per.perId = emp.empPersona
		LEFT JOIN Persona perLegal ON emp.empRepresentanteLegal = perLegal.perId
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN UbicacionEmpresa ubeNot ON ubeNot.ubeEmpresa = emp.empId AND ubeNot.ubeTipoUbicacion='NOTIFICACION_JUDICIAL'
		LEFT JOIN Ubicacion ubiNot ON ubiNot.ubiId = ubeNot.ubeUbicacion
		LEFT JOIN Ubicacion ubiLegal ON perLegal.perUbicacionPrincipal = ubiLegal.ubiId
		INNER JOIN Ubicacion ubi ON ubi.ubiId = ube.ubeUbicacion
		INNER JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio

		INNER JOIN (
			SELECT  
				car.carPersona, 
				car.carTipoAccionCobro,
				MIN(car.carFechaCreacion) as fechaCreacion,
				SUM(car.carDeudaPresunta) as saldoMora,
				COUNT(DISTINCT car.carPeriodoDeuda) as periodoMora
				
			FROM Cartera car
			WHERE 
				car.cardeudapresunta > 0
				AND car.carTipoSolicitante = 'EMPLEADOR'
				AND car.carTipoLineaCobro IN ( 'LC1', 'C6')
				AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_FINAL) > 60
			GROUP BY car.carTipoAccionCobro,car.carPersona
		) cart ON cart.carPersona = per.perId AND cart.saldoMora > 0
		
		
		LEFT JOIN BitacoraCartera bitCart 
			ON bitCart.bcaPersona = per.perId 
			AND bitCart.bcaFecha BETWEEN @FECHA_INICIO AND @FECHA_FINAL
			AND bitCart.bcaResultado = 'EXITOSO'


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
			GROUP BY car.carTipoAccionCobro,car.carPersona

		) carDep ON carDep.carPersona = per.perId AND carDep.carTipoAccionCobro = cart.carTipoAccionCobro

		--SELECT * from Cartera inner join persona on perId = carPersona  where perNumeroIdentificacion = '319367'
		LEFT JOIN ConvenioPago cnp 
			ON cnp.copPersona = per.perId 
			AND cnp.copEstadoConvenioPago = 'ACTIVO'
			--AND DATEDIFF(DAY,cnp.copFechaRegistro,@FECHA_FINAL) > 60

		--SELECT * FROM ConvenioPago INNER JOIN Persona ON perId = 14 INNER JOIN Cartera ON carPersona = perId

		LEFT JOIN (
			SELECT 
				SUM( apg.apgValTotalApoObligatorio ) carteraRecuperada,
				emp1.empId
			FROM 
				Empresa emp1
				INNER JOIN AporteGeneral apg ON apg.apgEmpresa = emp1.empId
					AND apg.apgFechaRecaudo BETWEEN @FECHA_INICIO AND @FECHA_FINAL
				INNER JOIN Cartera car ON car.carPersona = emp1.empPersona

				WHERE 
						car.carTipoLineaCobro IN ('LC1' , 'C6') 
						AND car.carDeudaPresunta > 0
						AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_FINAL) > 60 
						AND FORMAT(car.carPeriodoDeuda, 'yyyy-MM') = apg.apgPeriodoAporte
						--LOS APORTES DE PERIODOS EN MORA
						AND apg.apgPeriodoAporte IN (
							SELECT DISTINCT FORMAT(car1.carPeriodoDeuda, 'yyyy-MM')
								FROM Cartera car1
								WHERE 
									car1.carTipoLineaCobro in ('LC1' , 'C6')
									AND DATEDIFF(DAY,car1.carFechaCreacion,CAST(@FECHA_FINAL AS DATE)) > 60
									AND car1.carPersona = emp1.empPersona
								GROUP BY car1.carPersona, car1.carPeriodoDeuda)
			group by

				emp1.empId
		) cartRec ON cartRec.empId = emp.empId

END
		--AS reporte
		--GROUP BY reporte.nombreEmpresa, reporte.nit, reporte.direccion, reporte.ciudad, reporte.representanteLegal, reporte.fechaMora