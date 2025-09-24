-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/11/22
-- Description:	Carga la tabla de hechos FactGestionCarteraV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactGestionCarteraV2
AS

	--FactCondicionPersona
	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @DimPeriodoAnteriorId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1

	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);
	SELECT @DimPeriodoAnteriorId = dipId FROM DimPeriodo WHERE dipMes = MONTH(DATEADD(mm,-1,@FechaFinRevision)) AND dipAnio = YEAR(DATEADD(mm,-1,@FechaFinRevision));
		
	--Morosos período anterior
	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactGestionCarteraV2 WHERE fgcDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteCarteraPeriodoAnterior AS (
				SELECT fgcCartera,fgcPersona,@DimPeriodoId AS fgcDimPeriodo,fgcDimPeriodoRetroactivo,fgcDimTipoSolicitante,fgcValorMontoMora,fgcFechaEstadoMoroso,fgcFechaAlDia,fgcMoroso
				FROM FactGestionCarteraV2 fgc			
				WHERE fgc.fgcDimPeriodo = @DimPeriodoAnteriorId
				AND fgcMoroso = 'true'
			)
			INSERT INTO FactGestionCarteraV2 (fgcCartera,fgcPersona,fgcDimPeriodo,fgcDimPeriodoRetroactivo,fgcDimTipoSolicitante,fgcValorMontoMora,fgcFechaEstadoMoroso,fgcFechaAlDia,fgcMoroso)
			SELECT fgcCartera,fgcPersona,fgcDimPeriodo,fgcDimPeriodoRetroactivo,fgcDimTipoSolicitante,fgcValorMontoMora,fgcFechaEstadoMoroso,fgcFechaAlDia,fgcMoroso
			FROM cteCarteraPeriodoAnterior
		END
	END;
	
	WITH cteDimTipoSolicitante
	AS 
	(
		SELECT	carTipoSolicitante, fgcDimTipoSolicitante 
		FROM (
			VALUES
				('EMPLEADOR', 1),
				('INDEPENDIENTE', 2),
				('PENSIONADO', 3)
			) AS T (carTipoSolicitante, fgcDimTipoSolicitante)
	),

	cteWaterMarkedRows
	AS
	(
		SELECT car.carId, MAX(wmrLastRevisionDateTime) AS FechaEstadoCartera
		FROM WaterMarkedRows wmr
		INNER JOIN Cartera car ON wmr.wmrKeyRowValue = car.carId
		WHERE wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'Cartera'
		GROUP BY car.carId
	),

	cteAttachDimension
	AS
	(
		SELECT	car.carId AS fgcCartera, car.carPersona AS fgcPersona, @DimPeriodoId AS fgcDimPeriodo, dip.dipId AS fgcDimPeriodoRetroactivo, ctedta.fgcDimTipoSolicitante, car.carDeudaPresunta AS fgcValorMontoMora,
				CASE WHEN car.carEstadoCartera = 'MOROSO' THEN wmrCartera.FechaEstadoCartera END AS fgcFechaEstadoMoroso, --revisar regla negocio de "moroso"
				CASE WHEN car.carEstadoCartera = 'AL_DIA' THEN wmrCartera.FechaEstadoCartera END AS fgcFechaAlDia,
				CASE WHEN car.carEstadoCartera = 'MOROSO' THEN 'true' ELSE 'false' END AS fgcMoroso --revisar regla negocio de "moroso"
		FROM Cartera car
		INNER JOIN cteWaterMarkedRows wmrCartera ON wmrCartera.carId = car.carId
		INNER JOIN cteDimTipoSolicitante ctedta ON car.carTipoSolicitante = ctedta.carTipoSolicitante
		INNER JOIN DimPeriodo dip ON car.carPeriodoDeuda = CAST(dip.dipAnio AS VARCHAR(4)) + '-' + CAST(dip.dipMes AS VARCHAR(2)) + '-01'
		INNER JOIN Persona per ON car.carPersona = per.perId
	)

	MERGE FactGestionCarteraV2 AS T
	USING cteAttachDimension AS S
	ON (T.fgcDimPeriodo = S.fgcDimPeriodo AND T.fgcCartera = S.fgcCartera)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fgcCartera,fgcPersona,fgcDimPeriodo,fgcDimPeriodoRetroactivo,fgcDimTipoSolicitante,fgcValorMontoMora,fgcFechaEstadoMoroso,fgcFechaAlDia,fgcMoroso)
		VALUES (S.fgcCartera,S.fgcPersona,S.fgcDimPeriodo,S.fgcDimPeriodoRetroactivo,S.fgcDimTipoSolicitante,S.fgcValorMontoMora,S.fgcFechaEstadoMoroso,S.fgcFechaAlDia,S.fgcMoroso)
	WHEN MATCHED
		THEN UPDATE SET T.fgcValorMontoMora = S.fgcValorMontoMora,T.fgcFechaEstadoMoroso = ISNULL(T.fgcFechaEstadoMoroso,S.fgcFechaEstadoMoroso),T.fgcFechaAlDia = ISNULL(S.fgcFechaAlDia,T.fgcFechaAlDia),T.fgcMoroso = S.fgcMoroso
;
