-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/04/23
-- Description:	Procedimiento almacenado encargado de calcular el valor del estado V1, para los casos 
-- BD1429 y BD590 cuando el archivo pila es igual 1429 y 590 
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V1BD1429BD590_T2T3] 
	@IdRegistroGeneral Bigint,
	@IdTransaccion bigint,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@Tarifa Numeric(5,5), --Tarifa del registro 2
	@tarifaAportante Numeric(5,5),--tarifa BD
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
	
	DECLARE @TotalDiasNovedades Integer = 0
	IF(@Tarifa NOT IN (0.00000,0.01000,0.02000,0.03000,@tarifaBaseEmpleador))
	BEGIN
		SET @estadoValidacionV1 = 'NO_OK'
	END
	ELSE IF(@Tarifa = @tarifaBaseEmpleador)
	BEGIN
		SET @estadoValidacionV1 = 'OK'
	END
	ELSE IF(@Tarifa = 0.01000 OR @Tarifa = 0.02000 OR @Tarifa = 0.03000  )
	BEGIN
		IF(@Tarifa >= @tarifaAportante )
		BEGIN
			SET @estadoValidacionV1 = 'OK'
		END
		ELSE
		BEGIN
			SET @estadoValidacionV1 = 'NO_OK'
		END
	END
	ELSE IF(@Tarifa = 0.00000 AND @diasCotizados = 0 )
	BEGIN
		SET @estadoValidacionV1 = @EstadoValidacionNovedadesPILA 
	END
	ELSE
	BEGIN
		SET @estadoValidacionV1 = 'NO_OK'
	END

	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	UPDATE red 
	SET redOUTEstadoValidacionV1 = @estadoValidacionV1
	,redDateTimeUpdate = @redDateTimeUpdate 
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
	AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
	
END;