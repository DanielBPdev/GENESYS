--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetFechaDeber]


IF (OBJECT_ID('GetFechaDeber') IS NOT NULL)
	DROP FUNCTION [dbo].[GetFechaDeber]
GO

CREATE FUNCTION GetFechaDeber
(
	@dPeriodo DATETIME,
	@iCantidadDias SMALLINT
)
RETURNS DATETIME
AS
BEGIN
	DECLARE @iContador SMALLINT
	DECLARE @dFechaDeber DATETIME
	
	SET @iContador = 1
	SET @dFechaDeber = @dPeriodo
	
	WHILE @iContador <= @iCantidadDias
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM dbo.DiasFestivos WHERE pifFecha = @dFechaDeber)
		AND DATEPART(dw, @dFechaDeber) NOT IN (1,7)
		BEGIN
			SET @iContador = @iContador + 1
		END
		IF @iContador <= @iCantidadDias
			SET @dFechaDeber = DATEADD(dd,1,@dFechaDeber)	
		
	END

	RETURN @dFechaDeber
END
