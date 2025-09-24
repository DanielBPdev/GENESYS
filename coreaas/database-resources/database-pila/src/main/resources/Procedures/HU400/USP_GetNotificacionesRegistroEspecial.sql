-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de obtener la informacion
-- requerida para el proceso de Notificaciones HU400 (Pensionados e independientes)
-- =============================================
CREATE PROCEDURE USP_GetNotificacionesRegistroEspecial
(
	@IdRegistroGeneral BIGINT,
	@TipoIdentificacionCotizante VARCHAR(20),
	@NumeroIdentificacionCotizante VARCHAR(16)
)

AS
BEGIN
SET NOCOUNT ON;
SELECT 
CASE WHEN regEsAportePensionados = 1 THEN 'PENSIONADO' ELSE 'INDEPENDIENTE' END AS regTipoNotificacion, --VARCHAR(20) valores:(EMPLEADOR,INDEPENDIENTE,PENSIONADO)
redTipoIdentificacionCotizante AS TipoIdentificacionAportante,--regTipoIdentificacionAportante VARCHAR(20)
redNumeroIdentificacionCotizante AS NumeroIdentificacionAportante,--regNumeroIdentificacionAportante VARCHAR(20)
redNombreCotizante AS NombreAportante, --regNombreAportante VARCHAR(200)
regCodCiudad AS Municipio,
regCodDepartamento AS Departamento,
regNumPlanilla NumPlanilla, --regNumPlanilla VARCHAR(10)
regPeriodoAporte AS PeriodoAporte, --regPeriodoAporte VARCHAR(7)
regFechaRecaudo AS FechaPago, --regFechaRecaudo DATE
redEstadoSolicitante AS EstadoAportante, --regOUTEstadoEmpleador VARCHAR(50) NULL (Si es NULL es porque no aparece como empleador)
red.NumeroTrabajadores, --INT NOT NULL
red.NumeroTrabajadoresActivos, --INT NOT NULL,
red.NumeroTrabajadoresDiferenteActivos, --INT NOT NULL
CAST((CASE WHEN red.Aporte > 0 THEN 1 ELSE 0 END) AS BIT) AS ExisteRegistroConAporteMayorACero,
red.MontoTotalAportesRecibidos AS MontoTotalAportesRecibidos,
red.CantidadAportesRecibidos,
red.CantidadAportesMayorACero,
reg.regValorIntMora AS InteresesMoraRecibidos,
CAST((CASE WHEN red.RegistrosMarcadosOK = red.CantidadAportesRecibidos THEN 1 ELSE 0 END) AS BIT) AS TodosLosRegistrosMarcadosOK,
red.CantidadCotizantesConNovedades,
red.CantidadNovedadesPlanilla
FROM staging.RegistroGeneral reg with (nolock)
INNER JOIN
		( SELECT redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,
				 MAX(REPLACE(REPLACE(ISNULL(redPrimerNombre,'') + '#' + ISNULL(redSegundoNombre,'') + '#' + ISNULL(redPrimerApellido,'') + '#' + ISNULL(redSegundoApellido,''),'##','#'),'#',' ')) redNombreCotizante, 
				 MIN(redOUTEstadoSolicitante) AS redEstadoSolicitante,
				 COUNT(DISTINCT redTipoIdentificacionCotizante + '-' + redNumeroIdentificacionCotizante) NumeroTrabajadores,
				 COUNT(DISTINCT CASE WHEN redOUTEstadoSolicitante = 'ACTIVO' THEN redTipoIdentificacionCotizante + '-' + redNumeroIdentificacionCotizante ELSE NULL END) NumeroTrabajadoresActivos,
				 COUNT(DISTINCT CASE WHEN redOUTEstadoSolicitante <> 'ACTIVO' THEN redTipoIdentificacionCotizante + '-' + redNumeroIdentificacionCotizante ELSE NULL END) NumeroTrabajadoresDiferenteActivos,
				 MAX(redAporteObligatorio) Aporte,
				 SUM(redAporteObligatorio) MontoTotalAportesRecibidos,
				 COUNT(1) CantidadAportesRecibidos,
				 SUM(CASE WHEN redAporteObligatorio > 0 THEN 1 ELSE 0 END) CantidadAportesMayorACero,
				 SUM(CASE WHEN 
					(redOUTMarcaValRegistroAporte = 'VALIDAR_COMO_DEPENDIENTE'
						AND redOUTEstadoValidacionV0 = 'CUMPLE'
						AND redOUTEstadoValidacionV1 = 'OK'
						AND redOUTEstadoValidacionV2 IN ('OK', 'NO_APLICA')
						AND redOUTEstadoValidacionV3 = 'OK')
					OR (redOUTMarcaValRegistroAporte IN ('VALIDAR_COMO_INDEPENDIENTE', 'VALIDAR_COMO_PENSIONADO')
						AND redOUTEstadoValidacionV1 = 'OK')
					THEN 1 ELSE 0 END) RegistrosMarcadosOK,
				 --SUM(CASE WHEN redOUTEstadoRegistroAporte = 'ACTIVO' THEN 1 ELSE 0 END) RegistrosMarcadosOK,
				 SUM(CASE WHEN	redNovIngreso IS NOT NULL OR
								redNovRetiro IS NOT NULL OR
								redNovVSP IS NOT NULL OR
								redNovVST IS NOT NULL OR
								redNovSLN IS NOT NULL OR
								redNovIGE IS NOT NULL OR
								redNovLMA IS NOT NULL OR
								redNovVACLR IS NOT NULL OR
								ISNULL(redDiasIRL, 0) > 0 OR
								redNovSUS IS NOT NULL
						THEN 1 ELSE 0 END) CantidadCotizantesConNovedades,
				 SUM(	CASE WHEN redNovIngreso IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovRetiro IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovVSP IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovVST IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovSLN IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovIGE IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovLMA IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN redNovVACLR IS NOT NULL THEN 1 ELSE 0 END +
						CASE WHEN ISNULL(redDiasIRL, 0) > 0 THEN 1 ELSE 0 END +
						CASE WHEN redNovSUS IS NOT NULL THEN 1 ELSE 0 END 
					) CantidadNovedadesPlanilla	
		  FROM staging.RegistroDetallado red with (nolock)
		  GROUP BY redRegistroGeneral, redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante) red ON reg.regId = red.redRegistroGeneral
																																	
WHERE	reg.regId = @IdRegistroGeneral AND
		red.redTipoIdentificacionCotizante = @TipoIdentificacionCotizante AND
		red.redNumeroIdentificacionCotizante = @NumeroIdentificacionCotizante
;
END;