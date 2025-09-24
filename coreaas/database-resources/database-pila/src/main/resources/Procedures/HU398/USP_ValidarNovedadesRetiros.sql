-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de recorrer los cotizantes
-- de un aportante que tengan novedad de retiro (proceso independientes y pensionados)
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidarNovedadesRetiros]
(
	@IdRegistroGeneral BIGINT,
	@EsSimulado BIT = 0,
	@EsRegistroManual BIT = 0, 
	@TipoAfiliado VARCHAR(50) = 'TRABAJADOR_INDEPENDIENTE'
)
AS
BEGIN
SET NOCOUNT ON;

	declare @fechaMes as date = (select dbo.GetLocalDate())
	declare @fechaPeriodo date = null
	
	select @fechaPeriodo = convert(date, concat(regPeriodoAporte, N'-01'))
	from staging.registroGeneral with (nolock)
	where regId = @IdRegistroGeneral
	
	select @fechaMes = (select dateadd(day, 1, eomonth(@fechaMes, -1)))
	
	if  @fechaPeriodo >= @fechaMes
	begin
		--PRINT 'Validacion fecha superior a periodo'
		--print @fechaMes
		--print @fechaPeriodo
		update reg set reg.regNovedadFutura = 1
		FROM staging.RegistroDetalladoNovedad rdn with (nolock)
		INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
		INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @IdRegistroGeneral
		AND rdnTipoNovedad = 'NOVEDAD_RET'
		AND rdnOUTTipoAfiliado = @TipoAfiliado
	end
	else
	begin
		--PRINT 'Validacion fecha superior a periodo: else'


	UPDATE rdn
	SET rdnAccionNovedad = CASE WHEN redOUTEstadoSolicitante = 'ACTIVO' THEN 'APLICAR_NOVEDAD' ELSE 'NO_APLICADA' END,
		rdnMensajeNovedad= CASE WHEN redOUTEstadoSolicitante = 'ACTIVO' THEN 'La novedad de retiro ha sido registrada' ELSE 'La novedad de retiro no ha sido registrada' END,
		rdnDateTimeUpdate =  dbo.getLocalDate()
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND rdnTipoNovedad = 'NOVEDAD_RET'
	AND rdnOUTTipoAfiliado = @TipoAfiliado


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
		CASE 
			WHEN rdnAccionNovedad = 'APLICAR_NOVEDAD' AND rdnFechaInicioNovedad IS NULL THEN EOMONTH(CONVERT(DATE, regPeriodoAporte + '-01')) ELSE rdnFechaInicioNovedad 
		END rdnFechaInicioNovedad,
		CASE 
			WHEN rdnAccionNovedad = 'APLICAR_NOVEDAD' AND rdnFechaFinNovedad IS NULL AND rdnTipoNovedad NOT IN ('NOVEDAD_ING', 'NOVEDAD_RET') 
			THEN EOMONTH(CONVERT(DATE, regPeriodoAporte + '-01')) ELSE rdnFechaFinNovedad 
		END rdnFechaFinNovedad,
		rdnAccionNovedad,
		rdnMensajeNovedad,
		0 AS tenEnProceso
	FROM staging.RegistroDetalladoNovedad rdn with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rdn.rdnRegistroDetallado = red.redId
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND rdnOUTTipoAfiliado = @TipoAfiliado;

	end
END;