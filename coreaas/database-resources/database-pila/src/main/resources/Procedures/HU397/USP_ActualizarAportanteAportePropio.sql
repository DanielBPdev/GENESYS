-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/02
-- Description:	Procedimiento almacenado encargado de actualizar los 
-- nombres del aportante con aporte propio en el registro general
-- =============================================
CREATE PROCEDURE dbo.USP_ActualizarAportanteAportePropio
	@IdRegistroGeneral BIGINT
AS	
BEGIN
SET NOCOUNT ON;
	--print 'Inicia USP_ActualizarAportanteAportePropio'

	-- se establece sí el aporte es propio (sólo un cotizante y su ID es igual al del aportante)
	DECLARE @iCantidadCotizantes INT

	SELECT @iCantidadCotizantes = COUNT(*)
	FROM staging.RegistroDetallado with (nolock)
	WHERE redRegistroGeneral = @IdRegistroGeneral
	GROUP BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante

	IF @iCantidadCotizantes = 1 AND EXISTS (
		SELECT TOP 1 redId
		FROM staging.RegistroGeneral with (nolock)
		INNER JOIN staging.RegistroDetallado with(nolock) ON redRegistroGeneral = regId
		WHERE regId = @IdRegistroGeneral
		AND regTipoIdentificacionAportante = redTipoIdentificacionCotizante
		AND regNumeroIdentificacionAportante = redNumeroIdentificacionCotizante
	)
	BEGIN 
		--print 'Actualizar nombre aportante en Registro General'
		UPDATE reg
		SET reg.regOUTPrimerNombreAportante = red.redPrimerNombre,
			reg.regOUTSegundoNombreAportante = red.redSegundoNombre,
			reg.regOUTPrimerApellidoAportante = red.redPrimerApellido,
			reg.regOUTSegundoApellidoAportante = red.redSegundoApellido,
			reg.regDateTimeUpdate = dbo.getLocalDate()
		FROM staging.RegistroGeneral AS reg with(nolock)
		INNER JOIN staging.RegistroDetallado AS red with(nolock) ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @IdRegistroGeneral
	END

	--print 'Finaliza USP_ActualizarAportanteAportePropio'
END;