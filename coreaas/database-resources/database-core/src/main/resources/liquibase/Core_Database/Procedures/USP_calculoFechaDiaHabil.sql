-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2017-12-26
-- Description:	Procedimiento almacenado que calcula la fecha calendario para 
-- un día habil especificado en un mes y año
-- =============================================
CREATE PROCEDURE [dbo].[USP_calculoFechaDiaHabil] 
	@iMes AS INT,
	@iAnio AS INT,
	@iDiaHabil AS SMALLINT,
	@dFechaHabil AS DATE OUTPUT
AS
BEGIN
	;WITH N(N)AS 
	(
		SELECT 1 
		FROM(VALUES(1),(1),(1),(1),(1),(1))M(N)),tally(N)AS(
			SELECT ROW_NUMBER()OVER(ORDER BY N.N)
			FROM N,N a
	)

	SELECT fecha, dia, 
		CASE
			WHEN DATEPART(dw, fecha) IN (1, 7) THEN 1
			WHEN fecha IN (
				SELECT pifFecha
				FROM dbo.diasFestivos
			) THEN 1
			ELSE 0
		END esFestivo
	INTO #fechasMesConFestivo
	FROM 
	(
		SELECT DATEFROMPARTS(@iAnio,@iMes,N) fecha, 
			CASE 
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 1 THEN 'DOMINGO'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 2 THEN 'LUNES'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 3 THEN 'MARTES'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 4 THEN 'MIERCOLES'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 5 THEN 'JUEVES'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 6 THEN 'VIERNES'
				WHEN DATEPART(dw, DATEFROMPARTS(@iAnio,@iMes,N)) = 7 THEN 'SABADO'
			END dia
		FROM tally
		WHERE N <= day(EOMONTH(DATEFROMPARTS(@iAnio,@iMes,1)))
	) AS fechasMes

	SELECT fecha, CAST(ROW_NUMBER() OVER (ORDER BY fecha) AS SMALLINT) diaHabil
	INTO #diasHabiles
	FROM #fechasMesConFestivo
	WHERE esFestivo = 0 

	SELECT @dFechaHabil = fecha 
	FROM #diasHabiles
	WHERE diaHabil = @iDiaHabil

	DROP TABLE #fechasMesConFestivo
	DROP TABLE #diasHabiles
END;
