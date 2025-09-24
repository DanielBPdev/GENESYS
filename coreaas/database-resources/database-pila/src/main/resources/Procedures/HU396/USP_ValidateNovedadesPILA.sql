-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/04/30
-- Description:	Procedimiento almacenado encargado de calcular las novedades segun la clase trabajador.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateNovedadesPILA]
	@IdRegistroGeneral BigInt,
	@IdTransaccion bigint,
	@EsSimulado bit,
	@TipoIdentificacionAportante Varchar(20),
	@NumeroIdentificacionAportante Varchar(16),
	@TipoIdentificacionCotizante VARCHAR(20),
	@NumeroIdentificacionCotizante VARCHAR(16),
	@claseTrabajador Varchar(50),
	@periodoAporte Varchar(7),
	@DiasCotizados Smallint,
	@TotalDiasNovedades Smallint OUTPUT,
	@EstadoValidacionNovedadesPILA Varchar(50) OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	---------Calcular dias novedad----------------------------------------------------------------------------------------------			

	EXEC [dbo].[USP_ValidateDiasNovedadesPlanillaPILA]
	@IdRegistroGeneral = @IdRegistroGeneral,
	@IdTransaccion = @IdTransaccion,
	@EsSimulado = @EsSimulado,
	@periodoAporte = @periodoAporte,
	@TipoIdentificacionAportante = @tipoIdentificacionAportante,
	@NumeroIdentificacionAportante = @numeroIdentificacionAportante,
	@TipoIdentificacionCotizante = @tipoIdentificacionCotizante,
	@NumeroIdentificacionCotizante = @numeroIdentificacionCotizante,
	@DiasCotizados = @DiasCotizados, 
	@TotalDiasNovedades = @TotalDiasNovedades OUTPUT

	--print '@DiasCotizados sp 2'
	--print @DiasCotizados

	-- los días de las novedades en planilla, se suman a los días de las novedades en BD y los días cotizados en planilla
	DECLARE @TotalDiasNovedadesComplemento SMALLINT = CASE WHEN @TotalDiasNovedades + @DiasCotizados > 30 
		THEN 30 ELSE @TotalDiasNovedades + @DiasCotizados END

	--SELECT @TotalDiasNovedades = 
	--	CASE WHEN @TotalDiasNovedades + MAX(ISNULL(redOUTDiasCotizadosNovedadesBD, 0)) > 30 
	--	THEN 30 ELSE @TotalDiasNovedades + MAX(ISNULL(redOUTDiasCotizadosNovedadesBD, 0)) END, 
	--	@redOUTDiasCotizadosNovedadesBD = MAX(ISNULL(redOUTDiasCotizadosNovedadesBD, 0))
	--FROM staging.RegistroGeneral reg
	--INNER JOIN staging.RegistroDetallado red ON red.redRegistroGeneral = reg.regId
	--WHERE red.redTipoIdentificacionCotizante = @TipoIdentificacionCotizante
	--AND red.redNumeroIdentificacionCotizante = @NumeroIdentificacionCotizante
	--AND reg.regId = @IdRegistroGeneral

	--print '@TotalDiasNovedadesComplemento sp 2'
	--print @TotalDiasNovedadesComplemento

	IF(@claseTrabajador = 'REGULAR' OR @claseTrabajador = 'MADRE_COMUNITARIA' OR @claseTrabajador = 'SERVICIO_DOMESTICO')
	BEGIN
		DECLARE @ultimoDiaMesAporte SMALLINT = DAY(EOMONTH(CONVERT(Date,@periodoAporte+'-01')))

		IF(@ultimoDiaMesAporte > 30)
		BEGIN
			SET @ultimoDiaMesAporte = 30
		END
		
		IF(@TotalDiasNovedadesComplemento >= @ultimoDiaMesAporte)
		BEGIN
			SET @EstadoValidacionNovedadesPILA = 'OK'
		END
		ELSE
		BEGIN
			SET @EstadoValidacionNovedadesPILA = 'NO_OK'
		END
	END
	ELSE IF(@claseTrabajador = 'TRABAJADOR_POR_DIAS')
	BEGIN
		IF(@TotalDiasNovedadesComplemento > 0)  
		BEGIN
			SET @EstadoValidacionNovedadesPILA = 'OK'
		END
		ELSE
		BEGIN
			SET @EstadoValidacionNovedadesPILA = 'NO_OK'
		END
	END
	ELSE
	BEGIN
		SET @EstadoValidacionNovedadesPILA = 'NO_OK'
	END
END;