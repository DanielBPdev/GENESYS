--liquibase formatted sql

--changeset fvasquez:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_SumarDiasFecha] 

/****** Object:  UserDefinedFunction [dbo].[UFN_SumarDiasFecha]   ******/
IF (OBJECT_ID('UFN_SumarDiasFecha') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_SumarDiasFecha]
GO

-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/02/21
-- Update date: 2023/01/02 NICOLAS JARAMILLO
-- Description:	Función que agrega cierta cantidad de días (hábiles o calendario) a una fecha específica
-- HU164
-- =============================================
CREATE FUNCTION [dbo].[UFN_SumarDiasFecha](@fecha DATETIME, @dias INT, @contarHabiles BIT)
	RETURNS DATETIME
AS
BEGIN

	IF @fecha IS NULL
		BEGIN
			RETURN NULL
		END

	IF @dias IS NULL OR @dias = 0
		BEGIN
			RETURN @fecha
		END

	IF @contarHabiles IS NULL OR @contarHabiles = 0
		BEGIN
			RETURN DATEADD(dd, @dias, @fecha)
		END

	DECLARE @fechaInicio DATETIME
	DECLARE @fechaFin DATETIME
	DECLARE @nFactorDias INT = 0
	DECLARE @nSigno INT

	IF @dias > 0
		BEGIN
			SET @nSigno = 1
			SET @fechaInicio = @fecha
			SET @fechaFin = DATEADD(dd, @dias, @fechaInicio)
		END
	ELSE
		BEGIN
			SET @nSigno = -1
			SET @fechaFin = @fecha
			SET @fechaInicio = DATEADD(dd, @dias, @fechaFin)
		END

	SET @nFactorDias = ((DATEDIFF(wk, @fechaInicio, @fechaFin) + 1) * 7) + (@dias * @nSigno)
	IF @nFactorDias IS NULL
		BEGIN
			RETURN NULL
		END

	DECLARE @tLosDates TABLE
	                   (
		                   ldaFecha DATE
	                   )

	DECLARE @nCont INT = 1
	WHILE(@nCont <= @nFactorDias)
		BEGIN
			INSERT INTO @tLosDates
			SELECT DATEADD(dd, @nCont * @nSigno, @fecha)

			SET @nCont = @nCont + 1
		END

	IF @dias > 0
		BEGIN
			SELECT @fecha = MAX(ldaFecha)
			FROM (
				     SELECT TOP (@dias) ldaFecha
				     FROM @tLosDates
					          LEFT JOIN DiasFestivos ON pifFecha = ldaFecha
				     WHERE pifid IS NULL                        -- no sea festivo
					   AND DATEPART(dw, ldaFecha) NOT IN (7, 1) -- no sea sabado ni domingo
				     ORDER BY ldaFecha ASC
			     ) AS d
		END
	ELSE
		BEGIN
			SELECT @fecha = MIN(ldaFecha)
			FROM (
				     SELECT TOP (@dias * @nSigno) ldaFecha
				     FROM @tLosDates
					          LEFT JOIN DiasFestivos ON pifFecha = ldaFecha
				     WHERE pifid IS NULL                        -- no sea festivo
					   AND DATEPART(dw, ldaFecha) NOT IN (7, 1) -- no sea sabado ni domingo
				     ORDER BY ldaFecha DESC
			     ) AS d
		END

	RETURN @fecha
END
