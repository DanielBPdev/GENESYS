-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de recorrer los cotizantes
-- de un empleador para el proceso de novedades
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteRegistrarNovedadesNoAplicadas]
(
	@IdRegistroGeneral BIGINT,
	@EsSimulado BIT = 0,
	@EsRegistroManual BIT = 0
)
AS
BEGIN
SET NOCOUNT ON;
	INSERT INTO #Novedades (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
							,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion, tenEsIngreso, tenEsRetiro, tenFechaInicioNovedad,tenFechaFinNovedad
							,tenAccionNovedad,tenEnProceso)
	SELECT 
		redId,
		@EsSimulado,
		@EsRegistroManual,
		regId,
		redId,
		regTipoIdentificacionAportante,
		regNumeroIdentificacionAportante,
		redTipoIdentificacionCotizante,
		redNumeroIdentificacionCotizante,
		rdnTipoTransaccion,
		CASE WHEN rdnTipoNovedad = 'NOVEDAD_ING' THEN 1 ELSE 0 END,
		CASE WHEN rdnTipoNovedad = 'NOVEDAD_RET' THEN 1 ELSE 0 END,
		rdnFechaInicioNovedad,
		rdnFechaFinNovedad,
		rdnAccionNovedad,
		0 AS tenEnProceso
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND ISNULL(red.redCorrecciones,'') <> 'A'
	AND red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
;
END;