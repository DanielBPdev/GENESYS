-- =============================================
-- Author:      Villamarín, Julián
-- Create Date: julio 14 de 2022
-- Description: Función para calcular la fecha de Contabilización.
-- =============================================
CREATE OR ALTER FUNCTION [sap].[calcularSiguienteDiaHabil]()
	RETURNS INT
AS
BEGIN
    -- Declaración de variables
    DECLARE @fecha DATE
	DECLARE @diaHabilMes INT
	DECLARE @diaSemana INT
	DECLARE @Cnt INT --esta variable nos sirve de contador para saber cuando lleguemos al ultimo dia del rango
	
	SET @fecha = dbo.GetLocalDate()
	SET @Cnt = DATEPART(DAY, @fecha)
	SET @diaHabilMes = DATEPART(DAY, @fecha)
	SET @diaSemana = DATEPART(DW, @fecha)

	WHILE @Cnt <= 31
	BEGIN
		
		IF @diaSemana > 1 AND @diaSemana < 7 AND NOT EXISTS (SELECT * FROM dbo.DiasFestivos WHERE pifFecha = @fecha)
			SET @Cnt = 31
		ELSE 
		BEGIN
				SET @diaHabilMes = @diaHabilMes + 1
				SET @fecha = DATEADD(DAY, 1, @fecha)
				SET @diaSemana = DATEPART(DW, @fecha)
		END
		
		SET @Cnt = @Cnt + 1
	END

    RETURN @diaHabilMes

END