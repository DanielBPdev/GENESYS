CREATE PROCEDURE [dbo].[reportePastulacionesyAsignacionesFOVIS](
	@FECHA_INICIO DATE,
	@FECHA_FINAL DATE

)

AS

BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

	SELECT 

		---------- AÑO ----------
		YEAR ( cicloAsignacion.ciaFechaInicio ) AS [Año],
	
		---------- FECHA APERTURA ----------
		CONVERT(VARCHAR, cicloAsignacion.ciaFechaInicio, 112) as [Fecha de apertura de la postulación],

		---------- FECHA CIERRE ----------
		CONVERT(VARCHAR, cicloAsignacion.ciaFechaFin, 112) AS [Fecha de Cierre de postulación],
	
		---------- FECHA ASIGNACION ----------
		CONVERT(VARCHAR, cicloAsignacion.ciaFechaAsignacionPlanificada, 112) AS [Fecha de Asignación]

	FROM 
		CicloAsignacion AS cicloAsignacion
		LEFT JOIN PostulacionFOVIS AS posFovis 
			ON cicloAsignacion.ciaId = posFovis.pofCicloAsignacion
			AND 
				posFovis.pofFechaCalificacion <= @FECHA_FINAL AND
				posFovis.pofFechaCalificacion >= @FECHA_INICIO 

	WHERE 
		cicloAsignacion.ciaFechaInicio <= @FECHA_FINAL AND
		cicloAsignacion.ciaFechaFin >= @FECHA_INICIO AND
		cicloAsignacion.ciaCicloActivo = 1 -- AND
		--ciaEstadoCicloAsignacion = 'CERRADO'

	/*GROUP BY 
		cicloAsignacion.ciaFechaInicio,
		cicloAsignacion.ciaFechaFin,
		cicloAsignacion.ciaFechaAsignacionPlanificada*/

END