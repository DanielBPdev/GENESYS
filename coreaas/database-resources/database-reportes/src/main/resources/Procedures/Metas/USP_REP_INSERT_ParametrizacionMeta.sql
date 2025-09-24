-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/05/30
-- Description:	Inserta valores de Metas de acuerdo a un archivo JSON
-- discriminado por Meta, Canal y frecuencia
-- =============================================
CREATE PROCEDURE USP_REP_INSERT_ParametrizacionMeta
(
	@sValores NVARCHAR(MAX)
)
AS

	DECLARE @sMeta VARCHAR(44) --PORCENTAJE_AFILIACIONES_EMPLEADORES, PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES
	DECLARE @sFrecuencia VARCHAR(10) --MENSUAL, BIMENSUAL, TRIMESTRAL, SEMESTRAL, ANUAL
	DECLARE @sPeriodicidad VARCHAR(9) -- ANUAL, SEMESTRAL
	DECLARE @sPeriodo VARCHAR(7) -- 2018 (Anual), 2018-I, 2018-II (Semestral)
	DECLARE @iAnio SMALLINT
	DECLARE @sDatosParametrizacion NVARCHAR(MAX)
	DECLARE @iMpkId INT
		
	CREATE TABLE #ValoresPeriodo
	(
		Canal TINYINT,
		Mes TINYINT,
		Valor INT,
		Anio SMALLINT
	)	

	SET @sMeta = JSON_VALUE(@sValores,'$.meta')
	SET @sFrecuencia = JSON_VALUE(@sValores,'$.frecuencia')
	SET @sPeriodicidad = JSON_VALUE(@sValores,'$.periodicidad')
	SET @sPeriodo = JSON_VALUE(@sValores,'$.periodo')
	SET @sDatosParametrizacion = JSON_QUERY(@sValores,'$.datosParametrizacion')

	SELECT @iAnio = CAST(Data AS INT)
	FROM dbo.Split(@sPeriodo,'-') 
	WHERE Id = 1

	SELECT
		CAST(JSON_VALUE(j.[value], '$.codigoPeriodo') AS TINYINT) codigoPeriodo,
		CAST(JSON_VALUE(j.[value], '$.valor') AS INT) valor,
		JSON_VALUE(j.[value], '$.canal') AS canal,
		dic.dicId
	INTO #Valores
	FROM OPENJSON (@sDatosParametrizacion) j
	INNER JOIN dbo.DimCanal dic ON JSON_VALUE(j.[value], '$.canal') = dic.dicDescripcion	

	SELECT @iMpkId = mpkId FROM dbo.MetaPeriodoKPI WHERE mpkMeta = @sMeta AND mpkAnio = @iAnio AND mpkFrecuencia = @sFrecuencia
	IF @iMpkId IS NULL
	BEGIN
		INSERT INTO dbo.MetaPeriodoKPI (mpkMeta,mpkAnio,mpkFrecuencia)
		VALUES (@sMeta,@iAnio,@sFrecuencia)
		SET @iMpkId = @@IDENTITY
	END

	MERGE dbo.ValorMetaPeriodoCanalKPI AS T
	USING #Valores AS S
	ON T.vmpMetaPeriodoKPI = @iMpkId AND T.vmpCanal = S.canal AND T.vmpPeriodo = S.codigoPeriodo
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (vmpMetaPeriodoKPI,vmpCanal,vmpPeriodo,vmpValorInt)
		VALUES (@iMpkId,S.canal,S.codigoPeriodo,S.valor)
	WHEN MATCHED 
		THEN UPDATE SET vmpValorInt	= S.valor;

	IF @sFrecuencia = 'MENSUAL'
	BEGIN
		INSERT INTO #ValoresPeriodo (Canal, Mes, Valor, Anio)
		SELECT v.dicId, Numero AS Mes, v.valor, @iAnio Anio
		FROM dbo.Periodos() p
		INNER JOIN #Valores v ON v.codigoPeriodo = p.Numero		
		WHERE p.Periodo = 'MENSUAL'
	END
	IF @sFrecuencia = 'BIMENSUAL'
	BEGIN
		INSERT INTO #ValoresPeriodo (Canal, Mes, Valor, Anio)
		SELECT v.dicId, Numero AS Mes, v.valor, @iAnio Anio
		FROM dbo.Periodos() p
		INNER JOIN #Valores v ON v.codigoPeriodo = p.Bimestre
		WHERE p.Periodo = 'MENSUAL'
	END
	IF @sFrecuencia = 'TRIMESTRAL'
	BEGIN
		INSERT INTO #ValoresPeriodo (Canal, Mes, Valor, Anio)
		SELECT v.dicId, Numero AS Mes, v.valor, @iAnio Anio
		FROM dbo.Periodos() p
		INNER JOIN #Valores v ON v.codigoPeriodo = p.Trimestre
		WHERE p.Periodo = 'MENSUAL'
	END
	IF @sFrecuencia = 'SEMESTRAL'
	BEGIN
		INSERT INTO #ValoresPeriodo (Canal, Mes, Valor, Anio)
		SELECT v.dicId, Numero AS Mes, v.valor, @iAnio Anio
		FROM dbo.Periodos() p
		INNER JOIN #Valores v ON v.codigoPeriodo = p.Semestre
		WHERE p.Periodo = 'MENSUAL'
	END
	IF @sFrecuencia = 'ANUAL'
	BEGIN
		INSERT INTO #ValoresPeriodo (Canal, Mes, Valor, Anio)
		SELECT v.dicId, Numero AS Mes, v.valor, @iAnio Anio
		FROM dbo.Periodos() p
		CROSS JOIN #Valores v
		WHERE p.Periodo = 'MENSUAL'
	END
	;
	
	IF @sMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES'
	BEGIN
		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiEmpMes
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,S.dmcMetaAfiEmpMes,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiEmpMes = S.dmcMetaAfiEmpMes				
			;
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiEmpBimensual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,S.dmcMetaAfiEmpBimensual,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiEmpBimensual = S.dmcMetaAfiEmpBimensual		
			;
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiEmpTrimestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,S.dmcMetaAfiEmpTrimestral,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiEmpTrimestral = S.dmcMetaAfiEmpTrimestral
			;
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiEmpSemestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,S.dmcMetaAfiEmpSemestral,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiEmpSemestral = S.dmcMetaAfiEmpSemestral
			;
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiEmpAnual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,S.dmcMetaAfiEmpAnual,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiEmpAnual = S.dmcMetaAfiEmpAnual
			;
		END
		
	END
	IF @sMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES'
	BEGIN

		
		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechEmpMes
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,S.dmcMetaRechEmpMes,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechEmpMes = S.dmcMetaRechEmpMes				
			;
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechEmpBimensual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,S.dmcMetaRechEmpBimensual,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechEmpBimensual = S.dmcMetaRechEmpBimensual		
			;
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechEmpTrimestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,S.dmcMetaRechEmpTrimestral,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechEmpTrimestral = S.dmcMetaRechEmpTrimestral
			;
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechEmpSemestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,0,S.dmcMetaRechEmpSemestral,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechEmpSemestral = S.dmcMetaRechEmpSemestral
			;
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechEmpAnual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,0,0,S.dmcMetaRechEmpAnual,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechEmpAnual = S.dmcMetaRechEmpAnual
			;
		END

	END

	
	IF @sMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS'
	BEGIN
		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiPerMes
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,S.dmcMetaAfiPerMes,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiPerMes = S.dmcMetaAfiPerMes				
			;
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiPerBimensual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,S.dmcMetaAfiPerBimensual,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiPerBimensual = S.dmcMetaAfiPerBimensual		
			;
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiPerTrimestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,S.dmcMetaAfiPerTrimestral,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiPerTrimestral = S.dmcMetaAfiPerTrimestral
			;
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiPerSemestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,S.dmcMetaAfiPerSemestral,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiPerSemestral = S.dmcMetaAfiPerSemestral
			;
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaAfiPerAnual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,S.dmcMetaAfiPerAnual,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaAfiPerAnual = S.dmcMetaAfiPerAnual
			;
		END
		
	END
	IF @sMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS'
	BEGIN

		
		IF @sFrecuencia = 'MENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechPerMes
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,S.dmcMetaRechPerMes,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechPerMes = S.dmcMetaRechPerMes				
			;
		END
		IF @sFrecuencia = 'BIMENSUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechPerBimensual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,S.dmcMetaRechPerBimensual,0,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechPerBimensual = S.dmcMetaRechPerBimensual		
			;
		END
		IF @sFrecuencia = 'TRIMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechPerTrimestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,S.dmcMetaRechPerTrimestral,0,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechPerTrimestral = S.dmcMetaRechPerTrimestral
			;
		END
		IF @sFrecuencia = 'SEMESTRAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechPerSemestral
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,0,S.dmcMetaRechPerSemestral,0,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechPerSemestral = S.dmcMetaRechPerSemestral
			;
		END
		IF @sFrecuencia = 'ANUAL'
		BEGIN
			MERGE dbo.DimMetasCanalPeriodo AS T
			USING (	SELECT dip.dipId AS dmcDimPeriodo, V.canal dmcDimCanal, V.Valor AS dmcMetaRechPerAnual
					FROM #ValoresPeriodo V
					INNER JOIN dbo.DimPeriodo dip ON dip.dipMes = V.Mes AND dip.dipAnio = V.Anio) AS S
			ON T.dmcDimPeriodo = S.dmcDimPeriodo AND T.dmcDimCanal = S.dmcDimCanal
			WHEN NOT MATCHED BY TARGET
				THEN INSERT (dmcDimCanal,dmcDimPeriodo,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual)
				VALUES (S.dmcDimCanal,S.dmcDimPeriodo,0,0,0,0,0,0,0,0,0,S.dmcMetaRechPerAnual,0,0,0,0,0,0,0,0,0,0)
			WHEN MATCHED 
				THEN UPDATE SET
				dmcMetaRechPerAnual = S.dmcMetaRechPerAnual
			;
		END

	END

;