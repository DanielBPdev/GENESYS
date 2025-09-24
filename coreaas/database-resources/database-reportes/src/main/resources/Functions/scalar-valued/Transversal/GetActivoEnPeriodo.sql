--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetFechaDeber]


IF (OBJECT_ID('GetActivoEnPeriodo') IS NOT NULL)
	DROP FUNCTION [dbo].[GetActivoEnPeriodo]
GO

CREATE FUNCTION GetActivoEnPeriodo
(
	@dFechaIni DATE,
	@dFechaFin DATE,
	@iIdPersona BIGINT,
	@iOpcion SMALLINT = null
)
RETURNS BIT
AS
BEGIN
	DECLARE @bActivoPrimerFecha BIT
	
	SELECT TOP 1 @bActivoPrimerFecha = CASE WHEN eacEstadoAfiliacion = 'ACTIVO' THEN 1 ELSE 0 END
	FROM  EstadoAfiliacionPersonaCaja
	WHERE eacFechaCambioEstado <= @dFechaIni
	  AND eacPersona = @iIdPersona
	ORDER BY eacFechaCambioEstado DESC

	IF @bActivoPrimerFecha = 1
	BEGIN 
		RETURN 1
	END 
	
	IF EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaCaja 
				WHERE eacFechaCambioEstado BETWEEN @dFechaIni AND @dFechaFin
					AND eacEstadoAfiliacion = 'ACTIVO'
					AND eacPersona = @iIdPersona)
	BEGIN 
		RETURN 1
	END	
	RETURN 0
END
;
