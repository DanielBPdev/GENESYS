-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/14
-- Description:	Inserta (si no existen) Dimensiones de periodo desde un periodo de Origen hasta el periodo actual
-- =============================================
CREATE PROCEDURE USP_REP_INSERT_DimPeriodo
(
	@dPeriodoOrigen DATETIME = NULL
)
AS

	DECLARE @dPeriodoActual DATETIME
	DECLARE @DimPeriodoId INT
	DECLARE @TablaPeriodos AS TABLE (Periodo VARCHAR(20), Numero TINYINT, Texto VARCHAR(20), Bimestre TINYINT, Trimestre TINYINT, Semestre TINYINT)

	INSERT INTO @TablaPeriodos (Periodo,Numero,Texto,Bimestre,Trimestre,Semestre)
	SELECT Periodo,Numero,Texto,Bimestre,Trimestre,Semestre FROM dbo.Periodos()

	SET @dPeriodoActual = dbo.GetLocalDate()
	SET @dPeriodoActual = DATEADD(dd,1,EOMONTH(DATEADD(mm,-1,@dPeriodoActual)))

	IF @dPeriodoOrigen IS NULL
	BEGIN
		SET @dPeriodoOrigen = @dPeriodoActual
	END
	
	WHILE @dPeriodoOrigen <= @dPeriodoActual
	BEGIN

		SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@dPeriodoOrigen) AND dipAnio = YEAR(@dPeriodoOrigen)

		IF @DimPeriodoId IS NULL
		BEGIN

			INSERT INTO dbo.DimPeriodo (
				dipMes,
				dipAnio,
				dipTextoMes,
				dipBimestre,
				dipTextoBimestre,
				dipTrimestre,
				dipTextoTrimestre,
				dipSemestre,
				dipTextoSemestre
			)
			VALUES
			(
				MONTH(@dPeriodoOrigen),
				YEAR(@dPeriodoOrigen),
				(SELECT Texto FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual') + '-' + CAST(YEAR(@dPeriodoOrigen) AS VARCHAR(4)),
				(SELECT Bimestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual'),
				(SELECT Texto FROM @TablaPeriodos WHERE Numero = (SELECT Bimestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual') AND Periodo = 'Bimensual') + '-' + CAST(YEAR(@dPeriodoOrigen) AS VARCHAR(4)),
				(SELECT Trimestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual'),
				(SELECT Texto FROM @TablaPeriodos WHERE Numero = (SELECT Trimestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual') AND Periodo = 'Trimestral') + '-' + CAST(YEAR(@dPeriodoOrigen) AS VARCHAR(4)),
				(SELECT Semestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual'),
				(SELECT Texto FROM @TablaPeriodos WHERE Numero = (SELECT Semestre FROM @TablaPeriodos WHERE Numero = MONTH(@dPeriodoOrigen) AND Periodo = 'Mensual') AND Periodo = 'Semestral') + '-' + CAST(YEAR(@dPeriodoOrigen) AS VARCHAR(4))
			)

		END
		SET @dPeriodoOrigen = DATEADD(mm,1,@dPeriodoOrigen)
	END
;