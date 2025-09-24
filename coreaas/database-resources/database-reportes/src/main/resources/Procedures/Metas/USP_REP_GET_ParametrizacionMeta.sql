-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/05/30
-- Description:	Obtiene el listado de valores para periodos de una Meta, Canal y frecuencia
-- =============================================
CREATE PROCEDURE USP_REP_GET_ParametrizacionMeta
(
	@sMeta VARCHAR(44), --PORCENTAJE_AFILIACIONES_EMPLEADORES, PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES,PORCENTAJE_AFILIACIONES_PERSONAS, PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS
	@sPeriodicidad VARCHAR(9), -- ANUAL, SEMESTRAL
	@sPeriodo VARCHAR(7), -- 2018 (Anual), 2018-I, 2018-II (Semestral)
	@sFrecuencia VARCHAR(10) --MENSUAL, BIMENSUAL, TRIMESTRAL, SEMESTRAL, ANUAL
)
AS

SET NOCOUNT ON

DECLARE @iPeriodoSemestral TINYINT
DECLARE @iAnio SMALLINT
DECLARE @TablaPeriodos AS TABLE (Canal VARCHAR(20), Periodo VARCHAR(20), Numero TINYINT, Texto VARCHAR(20), Bimestre TINYINT, Trimestre TINYINT, Semestre TINYINT, PeriodoSemestral TINYINT)

	INSERT INTO @TablaPeriodos (Canal, Periodo,Numero,Texto,Bimestre,Trimestre,Semestre,PeriodoSemestral)
	SELECT UPPER(dic.dicDescripcion) AS Canal, Periodo,Numero,TextoLargo,Bimestre,Trimestre,Semestre,PeriodoSemestral 
	FROM dbo.Periodos()
	CROSS JOIN dbo.DimCanal dic
	
	SELECT @iAnio = Data
	FROM dbo.Split(@sPeriodo,'-') 
	WHERE Id = 1

	IF @sPeriodicidad = 'SEMESTRAL'
	BEGIN
		SELECT @iPeriodoSemestral = CASE Data WHEN 'I' THEN 1 WHEN 'II' THEN 2 END
		FROM dbo.Split(@sPeriodo,'-') 
		WHERE Id = 2
	END
		
	SELECT tp.Canal, CASE WHEN @sFrecuencia = 'ANUAL' THEN CAST(@iAnio AS VARCHAR(4)) ELSE Texto END AS periodo, tp.Numero AS codPeriodo, ISNULL(vmp.vmpValorInt, 0) AS valor --dbo.GetValorMeta(@sMeta, @sFrecuencia, @iAnio, tp.Numero, dic.dicId) AS valor
	FROM @TablaPeriodos tp
	INNER JOIN dbo.DimCanal dic ON tp.Canal = dic.dicDescripcion
	LEFT JOIN dbo.MetaPeriodoKPI mpk ON mpk.mpkMeta = @sMeta AND mpk.mpkAnio = @iAnio AND mpk.mpkFrecuencia = @sFrecuencia
	LEFT JOIN dbo.ValorMetaPeriodoCanalKPI vmp ON mpk.mpkId = vmp.vmpMetaPeriodoKPI AND tp.Numero = vmp.vmpPeriodo AND vmp.vmpCanal = dic.dicDescripcion
	WHERE Periodo = @sFrecuencia
	AND (@iPeriodoSemestral IS NULL OR PeriodoSemestral = @iPeriodoSemestral)
	ORDER BY dic.dicId, tp.Numero

SET NOCOUNT OFF
;
