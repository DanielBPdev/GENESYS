-- =============================================
-- Author:	   	  Diego Suesca
-- Create date:   2019/03/22
-- Author: 		  Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/09/09
-- Description:	  Inserta datos para reporte EmpresasEnMora
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoEmpresasMora
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;	
	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoEmpresasMora(
				hemFechaHistorico,
				hemTipoIdentificacion,
				hemPerNumeroIdentificacion,
				hemNombreEmpresa,
				hemMunCodigo,
				hemUbiDireccionFisica,
				hemNombreRepreLegal,
				hemFechaCreacion,
				hemSaldoMora,
				hemPeriodoMora,
				hemGestionProcesoCobro,
				hemAcuerdoPago,
				hemCarteraRecuperada,
				hemEmailEmpresa_repLegal,
				hemFechaInicialReporte,
				hemFechaFinalReporte)
		SELECT
			@fechaFin,
			Tabla.tipoIdentificacion AS tipoIdentificacion,
			Tabla.numeroIdentificacion AS numeroIdentificacion,
			Tabla.nombreEmpresa AS nombreEmpresa,
			Tabla.codigoMunicipio AS codigoMunicipio,
			Tabla.direccionEmpresa AS direccionEmpresa,
			Tabla.representanteLegal AS representanteLegal,
			CONVERT(VARCHAR,MIN(Tabla.fechaInicioMora),112) AS fechaInicioMora,
			SUM(Tabla.saldoMora) AS saldoMora,
			COUNT(Tabla.carId) AS periodosMora,
			CASE WHEN SUM(gestionProcesoCobro) > 0 THEN 1 ELSE 2 END AS gestionProcesoCobro,
			CASE WHEN cnp.copFechaRegistro IS NOT NULL THEN 1 ELSE 2 END AS acuerdoPago,
			carRec.carteraRecuperada AS carteraRecuperada,
			Tabla.correoElectronico,
			@fechaInicio,
			@fechaFin
		FROM
		(SELECT
			car.carPersona,
			car.carId,
			empr.empId,
			CASE per.perTipoIdentificacion
				        WHEN 'CEDULA_CIUDADANIA' THEN '1'
				        WHEN 'TARJETA_IDENTIDAD' THEN '2'
				        WHEN 'REGISTRO_CIVIL' THEN '3'
				        WHEN 'CEDULA_EXTRANJERIA' THEN '4'			      
				        WHEN 'PASAPORTE' THEN '6'
				        WHEN 'NIT' THEN '7'
				        WHEN 'CARNE_DIPLOMATICO' THEN '8'
				        WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
			END AS tipoIdentificacion,
			per.perNumeroIdentificacion AS numeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NULL 
			                THEN RTrim(Coalesce(per.perPrimerNombre + ' ','') 
			                        + Coalesce(per.perSegundoNombre + ' ', '')
			                        + Coalesce(per.perPrimerApellido + ' ', '')
			                        + Coalesce(per.perSegundoApellido, ''))	            
			     ELSE REPLACE(per.perRazonSocial,'  ',' ') 
			END AS nombreEmpresa,
			infoEmpresa.codigoMunicipio AS codigoMunicipio,
			substring(infoEmpresa.direccionEmpresa,1,100) AS direccionEmpresa,
			substring(Coalesce(infoRepresentanteLegal.primerNombre + ' ', '')
					    + Coalesce(infoRepresentanteLegal.segundoNombre + ' ', '')
						+ Coalesce(infoRepresentanteLegal.primerApellido + ' ', '')
					    + Coalesce(infoRepresentanteLegal.segundoApellido, ''),1,250)
			AS representanteLegal,
			car.carFechaCreacion AS fechaInicioMora,
			CASE WHEN cardeu.saldoMora IS NULL THEN CONVERT(BIGINT,car.carDeudaPresunta) ELSE cardeu.saldoMora END AS saldoMora,
			CASE WHEN car.carTipoAccionCobro ='B1' OR
				      car.carTipoAccionCobro ='C1' OR
				      car.carTipoAccionCobro ='D1' OR
				      car.carTipoAccionCobro ='E1' OR
				      car.carTipoAccionCobro ='F1' OR
				      car.carTipoAccionCobro ='B2' OR
				      car.carTipoAccionCobro ='C2' OR
				      car.carTipoAccionCobro ='D2' OR
				      car.carTipoAccionCobro ='E2' OR
				      car.carTipoAccionCobro ='F2' OR
				      car.carTipoAccionCobro ='G2' OR
				      car.carTipoAccionCobro ='H2'
				 THEN 1 ELSE 0
			END AS gestionProcesoCobro,
			ISNULL(CASE
				        WHEN infoEmpresa.email IS NOT NULL THEN infoEmpresa.email
				        ELSE infoRepresentanteLegal.email
				    END,''
			)AS correoElectronico
		FROM Persona per 
		INNER JOIN Cartera car ON car.carPersona = per.perId
		INNER JOIN(SELECT
						card.carId,
						SUM(cadp.valorCartera) AS saldoMora
					FROM Cartera card
					LEFT JOIN(SELECT
									cad.cadCartera AS carId,
									CASE WHEN cad.cadDeudaReal > 0 THEN CONVERT(BIGINT,cad.cadDeudaReal) ELSE CONVERT(BIGINT,cad.cadDeudaPresunta) END AS valorCartera
								FROM CarteraDependiente cad
								WHERE cad.cadEstadoOperacion = 'VIGENTE'
								 AND cad.cadDeudaPresunta > 0 OR cad.cadDeudaReal > 0
					)AS cadp ON cadp.carId = card.carId
					GROUP BY card.carId
		) AS cardeu ON cardeu.carId = car.carId
		INNER JOIN Empresa empr ON empr.empPersona = car.carPersona
		INNER JOIN(SELECT
						emprb.empId,
						ubiEmpr.ubiDireccionFisica AS direccionEmpresa,
						munEmpr.munCodigo AS codigoMunicipio,
						ubiEmpr.ubiEmail AS email
					FROM Empresa emprb
					LEFT JOIN UbicacionEmpresa ubePrin ON ubePrin.ubeEmpresa = emprb.empId AND ubePrin.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
					LEFT JOIN UbicacionEmpresa ubeNot ON ubeNot.ubeEmpresa = emprb.empId AND ubeNot.ubeTipoUbicacion = 'NOTIFICACION_JUDICIAL'
					INNER JOIN Ubicacion ubiEmpr ON ubiEmpr.ubiId = ubePrin.ubeUbicacion
					INNER JOIN Municipio munEmpr ON munEmpr.munId = ubiEmpr.ubiMunicipio
		)AS infoEmpresa ON infoEmpresa.empId = empr.empId
		INNER JOIN(SELECT
						emprc.empId,
						reprLegal.perPrimerNombre AS primerNombre,
						reprLegal.perSegundoNombre AS segundoNombre,
						reprLegal.perPrimerApellido AS primerApellido,
						reprLegal.perSegundoApellido AS segundoApellido,
						ubiLegal.ubiEmail AS email
					FROM Empresa emprc
					LEFT JOIN Persona reprLegal ON emprc.empRepresentanteLegal = reprLegal.perId
					LEFT JOIN Ubicacion ubiLegal ON reprLegal.perUbicacionPrincipal = ubiLegal.ubiId
		)AS infoRepresentanteLegal ON infoRepresentanteLegal.empId = empr.empId
		WHERE car.carDeudaPresunta > 0
			AND car.carTipoLineaCobro = 'LC1'
			AND car.carTipoSolicitante = 'EMPLEADOR'
			AND car.carEstadoOperacion = 'VIGENTE'
			AND DATEDIFF(DAY,car.carFechaCreacion,@fechaFin) > 60)AS Tabla
		LEFT JOIN(SELECT
					empr.empId,
					SUM(apg.apgValTotalApoObligatorio+apg.apgValorIntMora) carteraRecuperada
				  FROM Empresa empr
				  INNER JOIN AporteGeneral apg ON apg.apgEmpresa = empr.empId
				  INNER JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
				  WHERE apg.apgFechaRecaudo BETWEEN @fechaInicio AND @fechaFin
					AND apg.apgPeriodoAporte IN (SELECT
													DISTINCT CAST(carPer.carPeriodoDeuda AS VARCHAR(7))
												 FROM Cartera carPer
												 WHERE carPer.carEstadoOperacion = 'NO_VIGENTE'
													AND carPer.carTipoSolicitante = 'EMPLEADOR'
													AND carPer.carPersona = empr.empPersona)
				  GROUP BY empr.empId
		) AS carRec ON carRec.empId = Tabla.empId	
		LEFT JOIN ConvenioPago cnp ON cnp.copPersona = Tabla.carPersona AND cnp.copEstadoConvenioPago = 'ACTIVO'
		GROUP BY Tabla.carPersona,Tabla.empId,Tabla.tipoIdentificacion, Tabla.numeroIdentificacion, Tabla.nombreEmpresa, Tabla.codigoMunicipio, Tabla.direccionEmpresa, Tabla.representanteLegal, cnp.copFechaRegistro, Tabla.correoElectronico,carRec.carteraRecuperada 
		HAVING SUM(Tabla.saldoMora) > 0
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
