-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de recorrer los cotizantes
-- de un empleador para el proceso de novedades
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidarNovedadesRetroactivasEmpleador]
(
	@IdRegistroGeneral BIGINT,
	@EsSimulado BIT = 0,
	@EsRegistroManual BIT = 0
)
AS
BEGIN
SET NOCOUNT ON;
	--print 'USP_ValidarNovedadesRetroactivasEmpleador'
	create table #rdnTmp(rdnId BIGINT, AplicarRetiro BIT, periodo VARCHAR(7))

	INSERT INTO #rdnTmp
	SELECT
		rdnId,
		CASE WHEN rdnFechaInicioNovedad IS NOT NULL 
		THEN (
			CASE WHEN redOUTFechaUltimaNovedad IS NOT NULL 
			THEN
				(CASE WHEN redOUTFechaIngresoCotizante < redFechaRetiro AND (NOT redFechaRetiro BETWEEN redOUTFechaIngresoCotizante AND redOUTFechaUltimaNovedad)
				THEN 1 ELSE 0 END)
			ELSE
				(CASE WHEN redOUTFechaIngresoCotizante < redFechaRetiro
				THEN 1 ELSE 0 END)
			END)
		ELSE (
			CASE WHEN ISNULL(red.redOUTEstadoSolicitante, '') = 'ACTIVO' AND red.redOUTFechaIngresoCotizante IS NOT NULL 
					AND CONVERT(VARCHAR(7), red.redOUTFechaIngresoCotizante, 121) < reg.regPeriodoAporte AND rdnTipoNovedad = 'NOVEDAD_RET' 
			THEN 1 ELSE 0 END
		) 
		END AS AplicarRetiro, 
		reg.regPeriodoAporte AS periodo
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND ISNULL(red.redCorrecciones,'') <> 'A'
	AND rdnTipoNovedad = 'NOVEDAD_RET'
	AND rdnOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'

	UPDATE rdn
	SET rdnDateTimeUpdate =  dbo.getLocalDate(),
		rdnAccionNovedad = CASE WHEN rdnTmp.AplicarRetiro = 1 THEN 'APLICAR_NOVEDAD' ELSE 'NO_APLICADA' END,
		rdnMensajeNovedad = CASE WHEN rdnTmp.AplicarRetiro = 1 THEN 'La novedad de retiro ha sido registrada' ELSE 'La novedad de retiro no ha sido registrada' END,
		rdnFechaInicioNovedad = CASE WHEN rdnTmp.AplicarRetiro = 1 AND rdnFechaInicioNovedad IS NULL 
			THEN CONVERT(DATE, (rdnTmp.periodo + '-' + CAST(EOMONTH(CONVERT(Date,rdnTmp.periodo+'-01')) AS VARCHAR(2))), 121) ELSE rdnFechaInicioNovedad END
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN #rdnTmp rdnTmp with (nolock) ON rdn.rdnId = rdnTmp.rdnId
	
	INSERT INTO #Novedades (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
							,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion, tenEsIngreso, tenEsRetiro, tenFechaInicioNovedad,tenFechaFinNovedad
							,tenAccionNovedad,tenMensajeNovedad,tenEnProceso)
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
		rdnMensajeNovedad,
		0 AS tenEnProceso
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND ISNULL(red.redCorrecciones,'') <> 'A'
	AND rdnOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE';

END;