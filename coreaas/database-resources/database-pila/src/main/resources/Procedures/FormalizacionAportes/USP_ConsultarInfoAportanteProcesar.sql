-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2021-04-20
-- Description:	consulta datos el aportante de la planilla para formalizar   
-- =============================================
CREATE PROCEDURE [dbo].[USP_ConsultarInfoAportanteProcesar]
	@idRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;	
BEGIN TRY
	
	DECLARE @tapTipoDocAportante VARCHAR(20)
	DECLARE @tapIdAportante VARCHAR(20)
	
	SELECT TOP 1
	    @tapTipoDocAportante = tap.tapTipoDocAportante, 
	    @tapIdAportante = tap.tapIdAportante
	FROM TemAportante tap
	INNER JOIN TemAporte tem ON tap.tapIdTransaccion = temIdTransaccion
	WHERE tem.temRegistroGeneral = @idRegistroGeneral
	
	DECLARE @AportanteAporteTMP TABLE (
	    idPersona BIGINT,
	    perTipoIdentificacion VARCHAR(20),
	    perNumeroIdentificacion VARCHAR(20),
		idEmpresa BIGINT, 
		idEmpleador BIGINT, 
		estadoEmpleador VARCHAR(8),
	    shardName VARCHAR(500) 
	);
	
	IF (@tapTipoDocAportante IS NOT NULL AND @tapIdAportante IS NOT NULL)
	BEGIN
		INSERT INTO @AportanteAporteTMP (
		    idPersona,
		    perTipoIdentificacion,
		    perNumeroIdentificacion,
			idEmpresa, 
			idEmpleador, 
			estadoEmpleador,
		    shardName
		)
		EXEC sp_execute_remote CoreReferenceData, 
		N'SELECT 
		    perId idPersona,
		    perTipoIdentificacion,
		    perNumeroIdentificacion,
			empr.empId idEmpresa, 
			empl.empId idEmpleador, 
			empEstadoEmpleador estadoEmpleador 
		FROM Persona 
		LEFT JOIN Empresa empr ON perId = empr.empPersona
		LEFT JOIN Empleador empl ON empr.empId = empl.empEmpresa
		WHERE perTipoIdentificacion = @tapTipoDocAportante AND perNumeroIdentificacion = @tapIdAportante',
		N'@tapTipoDocAportante VARCHAR(20),@tapIdAportante VARCHAR(20)',
		@tapTipoDocAportante=@tapTipoDocAportante, 
		@tapIdAportante=@tapIdAportante
	END
	
	DECLARE @modalidadRecaudoAporte VARCHAR(40)
	SELECT TOP 1 
	@modalidadRecaudoAporte = temModalidadRecaudoAporte 
	FROM TemAporte WHERE temIdTransaccion = @idRegistroGeneral
	
	DECLARE @periodoAporte VARCHAR(7)
	SELECT @periodoAporte = regPeriodoAporte FROM staging.RegistroGeneral WHERE regId = @idRegistroGeneral
	
	DECLARE @tieneCotizanteDependienteReintegrable BIT = 0
	IF EXISTS(SELECT 1 FROM TemCotizante WHERE tctIdTransaccion = @idRegistroGeneral AND tctEsTrabajadorReintegrable = 1 AND tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE')
	BEGIN
	    SET @tieneCotizanteDependienteReintegrable = 1
	END
	
	SELECT DISTINCT
	tap.*, 
	idPersona,
	idEmpresa, 
	idEmpleador, 
	estadoEmpleador, 
	@tieneCotizanteDependienteReintegrable tieneCotizanteDependienteReintegrable,
	@periodoAporte periodoAporte,
	@modalidadRecaudoAporte modalidadRecaudoAporte
	FROM TemAportante tap
	INNER JOIN TemAporte tem ON tap.tapIdTransaccion = tem.temIdTransaccion
	LEFT JOIN @AportanteAporteTMP tmp ON tmp.perTipoIdentificacion = tap.tapTipoDocAportante AND tmp.perNumeroIdentificacion = tap.tapIdAportante
	WHERE tem.temRegistroGeneral = @idRegistroGeneral
	

END TRY
BEGIN CATCH

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate()
		,'USP_ConsultarInfoAportanteProcesar | @idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,ERROR_MESSAGE());
	
	THROW
END CATCH;
END;