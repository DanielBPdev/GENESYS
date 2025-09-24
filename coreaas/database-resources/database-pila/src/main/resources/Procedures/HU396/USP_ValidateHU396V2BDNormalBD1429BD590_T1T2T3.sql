-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/05/27
-- Description:	Procedimiento almacenado encargado de calcula el estado V2 para los cotizantes dependientes,
-- esto aplica para todos los estados del archivo pila y cuando en BD sea igual a normal o 1429.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V2BDNormalBD1429BD590_T1T2T3] 
	@IdRegistroGeneral Bigint,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@estadoEmpleador Varchar(50),
	@tipoCotizante Smallint,
	@claseTrabajador Varchar (50),
	@claseAportante Varchar(1),
	@estadoSolicitante Varchar(50), 
	@estadoValidacionV2 Varchar(50) OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	--DECLARE @TablaIndependientes AS TABLE (tipoCotizante VARCHAR(2))
	CREATE TABLE #TablaIndependientes (tipoCotizante VARCHAR(2));
	
	INSERT INTO #TablaIndependientes
	SELECT Data FROM dbo.Split( (
		SELECT stpValorParametro FROM staging.StagingParametros WITH (NOLOCK)
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',');
		

	IF((@estadoEmpleador='ACTIVO' OR @estadoEmpleador='INACTIVO' OR @estadoEmpleador='NO_FORMALIZADO_RETIRADO_CON_APORTES'))
	BEGIN
		IF (@estadoSolicitante IS NULL OR @claseTrabajador IS NULL)
		BEGIN 
			IF ((@estadoSolicitante IS NULL OR @estadoSolicitante = 'NO_FORMALIZADO_CON_INFORMACION' OR  @estadoSolicitante = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'))
			BEGIN
				SET @estadoValidacionV2 = 'NO_APLICA'
			END
			ELSE IF(@claseTrabajador IS NULL)
			BEGIN
				SET @estadoValidacionV2 = 'NO_OK'
			END
		END
		ELSE IF((@tipoCotizante NOT IN (SELECT tipoCotizante FROM #Tablaindependientes) AND @tipoCotizante NOT IN (51)) 
			AND (@claseTrabajador = 'REGULAR' OR @claseTrabajador = 'MADRE_COMUNITARIA' OR @claseTrabajador = 'SERVICIO_DOMESTICO'))
		BEGIN
			SET @estadoValidacionV2 = 'OK'
		END
		ELSE IF( @tipoCotizante = 51 AND @claseTrabajador = 'TRABAJADOR_POR_DIAS' )
		BEGIN
			SET @estadoValidacionV2 = 'OK'
		END
		ELSE IF(@tipoCotizante = 4 AND (@claseAportante = 'C' OR @claseAportante= 'D'))
		BEGIN
			SET @estadoValidacionV2 = 'NO_OK'
		END
		ELSE 
		BEGIN
			SET @estadoValidacionV2 = 'NO_OK'
		END
	END
	ELSE
	BEGIN
		SET @estadoValidacionV2 = 'NO_APLICA'
	END
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate();
	
	UPDATE red 
	SET
	redDateTimeUpdate = @redDateTimeUpdate 
	,redOUTEstadoValidacionV2 = @estadoValidacionV2
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
	AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante;
	
END;
