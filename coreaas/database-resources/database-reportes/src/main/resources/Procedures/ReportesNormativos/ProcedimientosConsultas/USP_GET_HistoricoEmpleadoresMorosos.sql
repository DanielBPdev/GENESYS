-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/10/22
-- Description:	Inserta datos para reporte HistoricoEmpleadoresMorosos
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoEmpleadoresMorosos
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
		INSERT rno.HistoricoEmpleadoresMorosos(
			hesFechaHistorico,
			hesNombreEmpresa,
			hesPerNumeroIdentificacion,
			hesNombreRepreLegal,
			hesUbiDireccionFisica,
			hesMunNombre,
			hesFechaMora,
			hesPeriodosSinPago,
			hesPresuntoValorCartera,
			hesFechaInicialReporte,
			hesFechaFinalReporte)
		SELECT
			@fechaFin,
			reporte.nombreEmpresa,
			reporte.nit,
			reporte.representanteLegal,
			reporte.direccion,
			reporte.ciudad,
			CONVERT(DATE,reporte.fechaMora,23),
			SUM(reporte.periodosSinPago),
			SUM(reporte.deudaPresunta),
			@fechaInicio,
			@fechaFin
		FROM(
		SELECT
			CASE WHEN per.perRazonSocial IS NULL 
									THEN RTrim(Coalesce(per.perPrimerNombre + ' ','') 
											+ Coalesce(per.perSegundoNombre + ' ', '')
											+ Coalesce(per.perPrimerApellido + ' ', '')
											+ Coalesce(per.perSegundoApellido, ''))	            
						 ELSE REPLACE(per.perRazonSocial,'  ',' ') 
			END AS nombreEmpresa,
			per.perNumeroIdentificacion AS nit,
			ubi.ubiDireccionFisica AS direccion,
			mun.munNombre AS ciudad,
			CONCAT(repLeg.perPrimerNombre,' ',repLeg.perSegundoNombre,' ',repLeg.perPrimerApellido,' ',repLeg.perSegundoApellido) AS representanteLegal,
			tabla.periodosSinPago,
			tabla.deudaPresunta,
			tabla.fechaMora
		FROM Persona per
		INNER JOIN Empresa empr ON empr.empPersona = per.perId
		INNER JOIN Empleador empl ON empl.empEmpresa = empr.empId
		INNER JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = empr.empId
		INNER JOIN Ubicacion ubi ON ubi.ubiId = ube.ubeUbicacion
		INNER JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
		INNER JOIN Persona repLeg ON repLeg.perId = empr.empRepresentanteLegal
		INNER JOIN (SELECT
						car.carPersona AS carPersona,
						SUM(car.carDeudaPresunta) AS deudaPresunta,
						COUNT(car.carId) AS periodosSinPago,
						MIN(car.carFechaCreacion) AS fechaMora
					FROM Cartera car
					WHERE car.carEstadoOperacion = 'VIGENTE'
						AND car.carEstadoCartera = 'MOROSO'
						AND car.carTipoLineaCobro = 'LC1'
						AND DATEDIFF(DAY,car.carFechaCreacion,@fechaFin) > 60
						AND car.carDeudaPresunta > 0
					GROUP BY car.carPersona
					) tabla ON tabla.carPersona = per.perId
		LEFT JOIN ConvenioPago cop ON cop.copPersona = per.perId AND cop.copEstadoConvenioPago = 'ACTIVO'
		WHERE ubi.ubiDescripcionIndicacion = 'ENVIO_CORRESPONDENCIA') AS reporte
		GROUP BY reporte.nombreEmpresa, reporte.nit, reporte.direccion, reporte.ciudad, reporte.representanteLegal, reporte.fechaMora
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;