-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/05/10
-- Description: Procedimiento almacenado encargado de calcular el valor de V1, para los
-- cotizantes independientes.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V1Independientes] 
	@IdRegistroGeneral Bigint
AS

BEGIN
SET NOCOUNT ON;
	
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate();
	
	UPDATE red 
	SET
		redDateTimeUpdate = @redDateTimeUpdate 
		,redOUTEstadoValidacionV1 = CASE WHEN 
		(
			SELECT MAX(red2.redTarifa)
			FROM staging.RegistroDetallado red2 WITH (NOLOCK)
			WHERE red2.redRegistroGeneral = @IdRegistroGeneral
			AND red2.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante
			AND red2.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante
			AND ISNULL(red2.redCorrecciones, '') != 'A'
		) >= redOUTPorcentajePagoAportes 
		THEN 'OK' ELSE 'NO_OK' END,
		redOUTEstadoRegistroAporte = CASE WHEN 
		(
			SELECT MAX(red2.redTarifa)
			FROM staging.RegistroDetallado red2 WITH (NOLOCK)
			WHERE red2.redRegistroGeneral = @IdRegistroGeneral
			AND red2.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante
			AND red2.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante
			AND ISNULL(red2.redCorrecciones, '') != 'A'
		) >= redOUTPorcentajePagoAportes 
		THEN 'OK' ELSE'NO_OK' END 
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	WHERE redOUTMarcaValRegistroAporte = 'VALIDAR_COMO_INDEPENDIENTE'
	AND redRegistroGeneral = @IdRegistroGeneral
	AND ISNULL(redOUTEstadoRegistroAporte,'NO_OK') = 'NO_OK';

END;