-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/04/23
-- Description:	Procedimiento almacenado encargado de actualizar el estado V0 igual a
-- CUMPLE, para los casos que aplique.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V0_T1T2T3]
	@IdRegistroGeneral Bigint,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@estadoValidacionV0 Varchar(50) OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	UPDATE red SET redOUTEstadoValidacionV0 = 'CUMPLE'
		,redDateTimeUpdate = @redDateTimeUpdate 
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
	AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
	
	
END;