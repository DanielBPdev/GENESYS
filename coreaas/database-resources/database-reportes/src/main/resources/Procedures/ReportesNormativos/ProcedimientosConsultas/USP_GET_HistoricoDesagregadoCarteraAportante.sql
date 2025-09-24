-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/09/09
-- Description:	Inserta datos para reporte HistoricoDesagregadoCarteraAportante
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoDesagregadoCarteraAportante
(
   	@fechaInicio DATE,
    @fechaFin DATE,
    @historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;
	
DECLARE @codigoAdministradora VARCHAR(150) 
DECLARE @nombreAdministradora VARCHAR(150)

SET @codigoAdministradora = (SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO')
SET @nombreAdministradora =	(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') 


IF @historico = 1
	BEGIN
    	INSERT INTO rno.HistoricoDesagregadoCarteraAportante(hdcFechaHistorico,
                                         hdcCodAdministradora,
                                         hdcNombreAdministradora,
                                         hdcNombreRazonSocial,
                                         hdcTipoDocumento,
                                         hdcNumeroDocumento,
                                         hdcDigitoVerificacion,
                                         hdcTipoDeuda,
                                         hdcOrigenCartera,
                                         hdcTotalDeuda,
                                         hdcAnioCartera,
                                         hdcNumeroPeriodos,
                                         hdcUltimaAccion,
                                         hdcFechaUltimaAccion,
                                         hdcEstadoAportante,
                                         hdcClasificacion,
                                         hdcConvenioCobro)
		SELECT
			@fechaFin,
			@codigoAdministradora AS codigoAdministradora,
			@nombreAdministradora AS nombreAdministradora,
			Reporte.nombreRazonSocial AS nombreRazonSocial,
			Reporte.tipoDocumento AS tipoDocumento,
			Reporte.numeroDocumento AS numeroDocumento,
			Reporte.digitoVerificacion AS digitoVerificacion,
			Reporte.tipoCartera AS tipoCartera,
			Reporte.origenCartera AS origenCartera,
			CONVERT(BIGINT,SUM(Reporte.valorCartera)) AS valorCartera,
			Reporte.anioCartera AS anioCartera,
			Reporte.periodosSinPago AS periodosSinPago,
			Reporte.ultimaAccionCobro AS ultimaAccionCobro,
			Reporte.fechaUltimaAccionCobro AS fechaUltimaAccionCobro,
			Reporte.estadoAportante AS estadoAportante,
			Reporte.clasificacionEstado AS clasificacionEstado,
			Reporte.convenioCobro AS convenioCobro
		FROM
		(SELECT
			Tabla.carPersona, 
			Tabla.nombreRazonSocial AS nombreRazonSocial, 
			Tabla.tipoDocumento AS tipoDocumento, 
			Tabla.numeroDocumento AS numeroDocumento, 
			Tabla.digitoVerificacion AS digitoVerificacion, 
			Tabla.tipoCartera AS tipoCartera, 
			Tabla.origenCartera AS origenCartera,
			SUM(Tabla.valorCartera) AS valorCartera,
			Tabla.anioCartera AS anioCartera,
			CASE WHEN COUNT(Tabla.carId) < 6 THEN 1
				 WHEN COUNT(Tabla.carId) < 12 THEN 2
				 WHEN COUNT(Tabla.carId) < 36 THEN 3
				 ELSE 4
			END AS periodosSinPago,
			Tabla.ultimaAccionCobro AS ultimaAccionCobro,
			Tabla.fechaUltimaAccionCobro AS fechaUltimaAccionCobro,
			Tabla.estadoAportante AS estadoAportante,
			CASE WHEN tabla.estadoAportante = 'A' THEN 'A' 
				 ELSE CASE(CASE 
								WHEN tabla.idEmpleador IS NOT NULL THEN (SELECT empMotivoDesafiliacion FROM Empleador WHERE empId = tabla.idEmpleador) 
								ELSE (SELECT roaMotivoDesafiliacion FROM RolAfiliado WHERE roaId = tabla.idRolAfiliado) 
					       END
						  ) 
					      WHEN 'EXPULSION_POR_MOROSIDAD' THEN 'E' 
						  WHEN 'RETIRO_POR_MORA_APORTES' THEN 'E' 
					      WHEN 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO' THEN 'L' 
						  WHEN 'FALLECIMIENTO' THEN 'L' 
						  ELSE( CASE WHEN EXISTS (SELECT(1) FROM ConvenioPago WHERE copPersona = tabla.carPersona AND copEstadoConvenioPago = 'ACTIVO') THEN 'R' ELSE 'ND' END)
				 END
			END clasificacionEstado,
			Tabla.convenioCobro AS convenioCobro
		FROM
		(SELECT
			car.carPersona,
			car.carId,
			(SELECT TOP 1 empl.empId FROM Empleador empl JOIN Empresa emp ON empl.empEmpresa = emp.empId WHERE emp.empPersona = per.perId) AS idEmpleador,
			(SELECT TOP 1 roa.roaId FROM RolAfiliado roa JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId WHERE afi.afiPersona = per.perId) AS idRolAfiliado,
			CASE WHEN per.perRazonSocial IS NULL 
			                THEN RTrim(Coalesce(per.perPrimerNombre + ' ','') 
			                        + Coalesce(per.perSegundoNombre + ' ', '')
			                        + Coalesce(per.perPrimerApellido + ' ', '')
			                        + Coalesce(per.perSegundoApellido, ''))	            
			     ELSE REPLACE(per.perRazonSocial,'  ',' ') 
			END AS nombreRazonSocial,
			CASE per.perTipoIdentificacion WHEN 'NIT' THEN 'NI' WHEN 'CEDULA_CIUDADANIA' THEN 'CC' WHEN 'CEDULA_EXTRANJERIA' THEN 'CE' WHEN 'TARJETA_IDENTIDAD' THEN 'TI' WHEN 'REGISTRO_CIVIL' THEN 'RC' WHEN 'PASAPORTE' THEN 'PA' ELSE '' END AS tipoDocumento,
			per.perNumeroIdentificacion AS numeroDocumento,
			CASE per.perTipoIdentificacion WHEN 'NIT' THEN per.perDigitoVerificacion ELSE NULL END AS digitoVerificacion,
			cardeu.tipoCartera AS tipoCartera,
			CASE WHEN car.carTipoLineaCobro = 'LC1' THEN 1 ELSE 2 END origenCartera,
			CASE WHEN cardeu.valorCartera IS NULL THEN CONVERT(BIGINT,car.carDeudaPresunta) ELSE cardeu.valorCartera END AS valorCartera,
			CASE WHEN CEILING(DATEDIFF(DAY, car.carFechaCreacion, @fechaFin)/365.) > 5 THEN 6 ELSE CEILING(DATEDIFF(DAY, car.carFechaCreacion, @fechaFin)/365.) END AS anioCartera,
			CASE WHEN bitacora.ultimaAccionCobro IN ('B1','BC1','B2','BC2') AND EXISTS (SELECT (1) FROM ActividadCartera WHERE acrActividadCartera IN  ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO', 'SOLICITAR_DOCUMENTACION') AND CONVERT(DATE,acrFecha,23) > bitacora.fechaUltimaAccionCobro) THEN '2'
						 WHEN bitacora.ultimaAccionCobro IN ('B1','BC1','B2','BC2') AND NOT EXISTS (SELECT (1) FROM ActividadCartera WHERE acrActividadCartera IN  ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO', 'SOLICITAR_DOCUMENTACION') AND CONVERT(DATE,acrFecha,23) > bitacora.fechaUltimaAccionCobro) THEN '1'
						 WHEN bitacora.ultimaAccionCobro IN('C1','CD1','C2','CD2','D2','DE2','E2','EF2') THEN '3' 
						 WHEN bitacora.ultimaAccionCobro IN('D1','DE1','E1','EF1','F2','FG2','G2','GH2') THEN '4' 
						 WHEN bitacora.ultimaAccionCobro IN('F1','H2') THEN '5' ELSE '6' 
			END AS ultimaAccionCobro,
			CASE WHEN bitacora.ultimaAccionCobro IS NOT NULL THEN ISNULL(CONVERT(VARCHAR(10), bitacora.fechaUltimaAccionCobro, 121), '') ELSE '' END AS fechaUltimaAccionCobro,
			CASE WHEN (ISNULL((SELECT TOP 1 empl.empEstadoEmpleador FROM Empleador empl JOIN Empresa emp ON empl.empEmpresa = emp.empId WHERE emp.empPersona = per.perId),(SELECT TOP 1 roa.roaEstadoAfiliado FROM RolAfiliado roa JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId WHERE afi.afiPersona = per.perId))) = 'ACTIVO' THEN 'A' ELSE 'I' END AS estadoAportante,
			CASE WHEN cop.copId IS NULL THEN '2' ELSE '1' END convenioCobro
		FROM Persona per
		INNER JOIN Cartera car ON car.carPersona = per.perId
		INNER JOIN(SELECT
						card.carId,
						CASE WHEN SUM(cadp.tipoCartera) > 0 THEN 1 ELSE 2 END AS tipoCartera,
						SUM(cadp.valorCartera) AS valorCartera
					FROM Cartera card
					LEFT JOIN(SELECT
									cad.cadCartera AS carId,
									CASE WHEN cad.cadCartera IS NULL OR ISNULL(cad.cadDeudaReal,0) = 0 THEN 0 ELSE 1 END AS tipoCartera,
									CASE WHEN cad.cadDeudaReal > 0 THEN CONVERT(BIGINT,cad.cadDeudaReal) ELSE CONVERT(BIGINT,cad.cadDeudaPresunta) END AS valorCartera
								FROM CarteraDependiente cad
								WHERE cad.cadEstadoOperacion = 'VIGENTE'
								 AND cad.cadDeudaPresunta > 0 OR cad.cadDeudaReal > 0
					)AS cadp ON cadp.carId = card.carId
					GROUP BY card.carId
		) AS cardeu ON cardeu.carId = car.carId
		INNER JOIN(SELECT
					carB.carId,
					bc.fechaUltimaAccionCobro AS fechaUltimaAccionCobro,
					bc.ultimaAccionCobro AS ultimaAccionCobro
					FROM Cartera carb
					LEFT JOIN(SELECT TOP 1 WITH TIES
									cag.cagCartera,
									bca.bcaFecha AS fechaUltimaAccionCobro,
									bca.bcaActividad AS ultimaAccionCobro
								FROM CarteraAgrupadora cag
								INNER JOIN BitacoraCartera bca ON bca.bcaNumeroOperacion = CAST(cag.cagNumeroOperacion AS VARCHAR(12))
								WHERE bca.bcaResultado IN ('EXITOSO','PUBLICADO')
								ORDER BY ROW_NUMBER() OVER (PARTITION BY cag.cagCartera ORDER BY bca.bcaFecha DESC, bca.bcaId DESC)
					)AS bc ON bc.cagCartera = carb.carId
		)AS bitacora ON bitacora.carId = car.carId
		LEFT JOIN ConvenioPago cop ON cop.copPersona = car.carPersona
		WHERE car.carEstadoOperacion = 'VIGENTE'
			AND car.carTipoLineaCobro IN ('LC1','LC2','LC3','LC4','LC5')
			AND car.carDeudaPresunta > 0
			AND DATEDIFF(DAY,car.carFechaCreacion,@fechaFin) > 30) AS Tabla
		GROUP BY Tabla.carPersona, Tabla.nombreRazonSocial, Tabla.tipoDocumento, Tabla.numeroDocumento, Tabla.digitoVerificacion, Tabla.tipoCartera, Tabla.origenCartera, Tabla.anioCartera, Tabla.ultimaAccionCobro, Tabla.fechaUltimaAccionCobro, Tabla.estadoAportante, Tabla.convenioCobro, Tabla.idEmpleador, Tabla.idRolAfiliado
		) AS Reporte
		GROUP BY nombreRazonSocial, tipoDocumento, numeroDocumento, digitoVerificacion, tipoCartera, origenCartera, anioCartera, periodosSinPago, ultimaAccionCobro, fechaUltimaAccionCobro, estadoAportante, clasificacionEstado, convenioCobro
		HAVING SUM(Reporte.valorCartera) > 0
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
