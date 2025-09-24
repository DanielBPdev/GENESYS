-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/08/09
-- Description:	Procedimiento almacenado encargado de determinar que se 
-- está haciendo un aporte propio (PILA o Manual)
-- =============================================
CREATE PROCEDURE [dbo].[USP_DeterminarAportePropio]
	@idRegistroGeneral BIGINT,
	@esAportePropio BIT OUTPUT
AS	
BEGIN
SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
--print 'Inicia USP_DeterminarAportePropio'


	DECLARE @hayDependientes BIT = 0
	DECLARE @hayOtrosCotizantes BIT = 0

	DECLARE @TablaIndependientes AS TABLE (tipoCotizante VARCHAR(2))
		INSERT INTO @TablaIndependientes
		SELECT Data FROM dbo.Split( (
				SELECT stpValorParametro FROM staging.StagingParametros 
				WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',')


	IF EXISTS (SELECT TOP 1 1
		FROM staging.RegistroGeneral reg
		LEFT JOIN staging.RegistroDetallado red ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @idRegistroGeneral
		AND ISNULL(red.redTipoCotizante, '') NOT IN (SELECT tipoCotizante FROM @TablaIndependientes))
	BEGIN
		SET @hayDependientes = 1
	END

	IF EXISTS(SELECT 1
		FROM staging.RegistroGeneral reg
		LEFT JOIN staging.RegistroDetallado red ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @idRegistroGeneral
		AND reg.regTipoIdentificacionAportante != red.redTipoIdentificacionCotizante
		AND reg.regNumeroIdentificacionAportante != redNumeroIdentificacionCotizante)
	BEGIN
		SET @hayOtrosCotizantes = 1
	END

	SET @esAportePropio = 0
	IF @hayDependientes = 0 AND @hayOtrosCotizantes = 0
	BEGIN
		SET @esAportePropio = 1
	END

END;