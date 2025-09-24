-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 201/04/20
-- Description:	Procedimiento almacenado encargado de actualizar todos los estados en NO_OK, en el caso que 
-- la validacion no aplique.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396BD590BD1429_T2T3]
	@IdRegistroGeneral Bigint,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@estadoValidacionV0 Varchar(50) OUTPUT,
	@estadoValidacionV1 Varchar(50) OUTPUT,
	@estadoValidacionV2 Varchar(50) OUTPUT,
	@estadoValidacionV3 Varchar(50) OUTPUT 
AS

BEGIN
SET NOCOUNT ON;
	
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
		SET @estadoValidacionV0 = 'NO_CUMPLE'
		SET @estadoValidacionV1 = 'NO_OK'
		SET @estadoValidacionV2 = 'NO_OK'
		SET @estadoValidacionV3 = 'NO_OK'	
			
		DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
		UPDATE red SET redOUTEstadoValidacionV0 = @estadoValidacionV0
			,redOUTEstadoValidacionV1 = @estadoValidacionV1
			,redOUTEstadoValidacionV2 = @estadoValidacionV2
			,redOUTEstadoValidacionV3 = @estadoValidacionV3
			,redDateTimeUpdate = @redDateTimeUpdate 
		FROM staging.RegistroDetallado red WITH (NOLOCK)
		INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
		WHERE redRegistroGeneral = @IdRegistroGeneral
		AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
		AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
END;