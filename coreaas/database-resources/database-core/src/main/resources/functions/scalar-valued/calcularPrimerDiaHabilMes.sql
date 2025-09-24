-- =============================================
-- Author:      Villamarín, Julián
-- Create Date: julio 14 de 2022
-- Description: Función para calcular la fecha de Contabilización.
-- =============================================
CREATE OR ALTER FUNCTION [sap].[calcularPrimerDiaHabilMes]()
	RETURNS INT
AS
BEGIN
    -- Declaración de variables
    DECLARE @fecha DATE
	DECLARE @fechaInicio DATE

	DECLARE @diaHabilMes INT
	DECLARE @diaSemana INT
	DECLARE @Cnt INT --esta variable nos sirve de contador para saber cuando lleguemos al ultimo dia del rango
	
	SET @fecha = dbo.GetLocalDate()
	SET @fechaInicio = DATEADD(DAY, 1, EOMONTH(@fecha,-1))
	SET @diaHabilMes = DATEPART(DAY, @fechaInicio) 
	SET @Cnt = @diaHabilMes
	SET @diaSemana = DATEPART(DW, @fechaInicio)

	WHILE @Cnt <= 31
	BEGIN
		
		IF @diaSemana > 1 AND @diaSemana < 7 AND NOT EXISTS (SELECT * FROM dbo.DiasFestivos WHERE pifFecha = @fechaInicio)
			SET @Cnt = 31
		ELSE 
		BEGIN
				SET @diaHabilMes = @diaHabilMes + 1
				SET @fechaInicio = DATEADD(DAY, 1, @fechaInicio)
				SET @diaSemana = DATEPART(DW, @fechaInicio)
		END
		
		SET @Cnt = @Cnt + 1
	END

    RETURN @diaHabilMes

END