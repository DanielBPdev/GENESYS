-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/08/10
-- Description:	Procedimiento almacenado que se encarga de consultar el histórico de afiliaciones de un empleador, respecto a la CCF
-- Req-Consultas - Vista360
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoAfiliacionEmpleador]
	@idPersona BIGINT	-- idPersona del empleador
AS
SET NOCOUNT ON

DECLARE @numeroTrabajadores INT

DECLARE @estadoActual VARCHAR(50)
DECLARE @estadoAnterior VARCHAR(50)
DECLARE @motivo VARCHAR(100)

DECLARE @fechaCambioEstado DATETIME
DECLARE @fechaIngreso DATETIME
DECLARE @fechaRetiro DATETIME

DECLARE @tablaHistorico TABLE(
	fechaIngreso DATETIME, 
	fechaRetiro DATETIME, 
	estadoAnterior VARCHAR(50), 
	motivo VARCHAR(100), 
	numeroTrabajadores INT)
	
DECLARE @historicoCursor AS CURSOR

-- Lista de registros históricos
SET @historicoCursor = CURSOR FAST_FORWARD FOR	
	SELECT ISNULL(eec.eecEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eec.eecFechaCambioEstado fecha, eec.eecMotivoDesafiliacion, eec.eecNumeroTrabajadores
	FROM dbo.EstadoAfiliacionEmpleadorCaja eec
	WHERE eec.eecPersona = @idPersona
	ORDER BY eec.eecFechaCambioEstado DESC	
	
OPEN @historicoCursor	
FETCH NEXT FROM @historicoCursor INTO @estadoActual, @fechaCambioEstado, @motivo, @numeroTrabajadores

SET @estadoAnterior = @estadoActual

-- Recorre el histórico de afiliaciones 
WHILE @@FETCH_STATUS = 0
BEGIN	
	IF @estadoActual = 'ACTIVO'
	BEGIN
		SET @fechaIngreso = @fechaCambioEstado			
	END
	ELSE
	BEGIN
		SET @estadoAnterior = NULL
	END
	
	IF @estadoActual = 'INACTIVO' AND @estadoAnterior = 'ACTIVO'
	BEGIN
		SET @fechaRetiro = @fechaCambioEstado			
	END
	
	INSERT INTO @tablaHistorico(fechaIngreso, fechaRetiro, estadoAnterior, motivo, numeroTrabajadores)
	VALUES(@fechaIngreso, @fechaRetiro, @estadoAnterior, @motivo, @numeroTrabajadores)
	
	SET @estadoAnterior = @estadoActual
	SET @fechaIngreso = NULL
	SET @fechaRetiro = NULL		
	
	FETCH NEXT FROM @historicoCursor INTO @estadoActual, @fechaCambioEstado, @motivo, @numeroTrabajadores
END

CLOSE @historicoCursor
DEALLOCATE @historicoCursor	

-- Respuesta
SELECT fechaIngreso, fechaRetiro, estadoAnterior, motivo, numeroTrabajadores
FROM @tablaHistorico


