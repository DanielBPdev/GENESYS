-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/04/30
-- Description:	Procedimiento almacenado encargado de calcular el estado V1 cuando el archivo PILA es igual a normal 
-- y en la bade de datos es igual Normal,1429 y 590.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V1BDNormal_T1T2T3]
	@IdRegistroGeneral Bigint,
	@IdTransaccion bigint,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@tarifa Numeric(5,5), --Tarifa del registro 2
	@tarifaAportante Numeric(5,5),--Tarifa BD
	@claseTrabajador Varchar(50),
	@diasCotizados Smallint,
	@periodoAporte Varchar(7),
	@tarifaBaseEmpleador numeric(19,5),
	@EstadoValidacionNovedadesPILA Varchar(50),
	@estadoValidacionV1 Varchar(50) OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	IF(@tarifa = @TarifaBaseEmpleador AND @tarifaAportante = @tarifaBaseEmpleador )
	BEGIN
		SET @estadoValidacionV1 = 'OK';
	END
	ELSE IF(@diasCotizados = 0 )
	BEGIN
		SET @estadoValidacionV1 = @EstadoValidacionNovedadesPILA;
	END
	-- Validación eliminada en el CC 0251307
	--ELSE IF(@tarifa > 0.00000 AND @diasCotizados = 0 )
	--BEGIN
	--	SET @estadoValidacionV1 = 'NO_OK'
	--END
	ELSE IF(@tarifaAportante > 0.00000 OR @tarifaAportante < @tarifaBaseEmpleador )
	BEGIN
		SET @estadoValidacionV1 = 'NO_OK';
	END
	ELSE
	BEGIN
		SET @estadoValidacionV1 = 'NO_OK';
	END
		
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate();
	
	UPDATE red 
	SET redOUTEstadoValidacionV1 = @estadoValidacionV1
	,redDateTimeUpdate = @redDateTimeUpdate 
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
	AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante;
	
END;