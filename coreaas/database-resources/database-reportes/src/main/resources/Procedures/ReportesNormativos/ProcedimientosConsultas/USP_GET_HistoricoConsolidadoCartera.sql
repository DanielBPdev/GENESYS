-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Author: Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/09/09
-- Description:	Inserta datos para reporte HistoricoConsolidadoCartera
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoConsolidadoCartera
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
		INSERT INTO rno.HistoricoConsolidadoCartera(
				hccFechaHistorico,
				hccCodAdministradora,
				hccNombreAdministradora,
				hccTipoDeuda,
				hccOrigenCartera,
				hccAnioCartera,
				hccNumeroPeriodos,
				hccTotalDeuda,
				hccFechaInicialReporte,
				hccFechaFinalReporte
		)
		SELECT
			@fechaFin,
			@codigoAdministradora AS codigoAdministradora,
			@nombreAdministradora AS nombreAdministradora,
			Reporte.tipoCartera AS tipoCartera,
			Reporte.origenCartera AS origenCartera,
			Reporte.anioCartera AS anioCartera,
			Reporte.periodosSinPago AS periodosSinPago,
			SUM(Reporte.valorCartera) AS valorCartera,
			@fechaInicio,
			@fechaFin
		FROM
		(SELECT
			Tabla.carPersona,
			Tabla.tipoCartera AS tipoCartera,
			Tabla.origenCartera AS origenCartera,
			Tabla.anioCartera AS anioCartera,
			CASE WHEN COUNT(Tabla.carId) < 6 THEN 1
				 WHEN COUNT(Tabla.carId) < 12 THEN 2
				 WHEN COUNT(Tabla.carId) < 36 THEN 3
				 ELSE 4
			END AS periodosSinPago,
			SUM(Tabla.valorCartera) AS valorCartera
		FROM
		(SELECT
			car.carPersona,
			car.carId,
			cardeu.tipoCartera AS tipoCartera,
			CASE WHEN car.carTipoLineaCobro = 'LC1' THEN 1 ELSE 2 END origenCartera,
			CASE WHEN CEILING(DATEDIFF(DAY, car.carFechaCreacion, @fechaFin)/365.) > 5 THEN 6 ELSE CEILING(DATEDIFF(DAY, car.carFechaCreacion, @fechaFin)/365.) END AS anioCartera,
			CASE WHEN cardeu.valorCartera IS NULL THEN CONVERT(BIGINT,car.carDeudaPresunta) ELSE cardeu.valorCartera END AS valorCartera
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
		WHERE car.carEstadoOperacion = 'VIGENTE'
			AND car.carTipoLineaCobro IN ('LC1','LC2','LC3','LC4','LC5')
			AND car.carDeudaPresunta > 0
			AND DATEDIFF(DAY,car.carFechaCreacion,@fechaFin) > 30) AS Tabla
		GROUP BY Tabla.carPersona, Tabla.tipoCartera, Tabla.origenCartera, Tabla.anioCartera
		) AS Reporte
		GROUP BY tipoCartera, origenCartera, anioCartera, periodosSinPago
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
