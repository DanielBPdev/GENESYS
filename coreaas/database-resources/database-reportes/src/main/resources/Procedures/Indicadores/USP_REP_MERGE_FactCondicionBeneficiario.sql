-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/22
-- Description:	Carga la tabla de hechos USP_REP_MERGE_FactCondicionBeneficiario para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionBeneficiario
AS
	--FactCondicionBeneficiario
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

	DECLARE @HomologTipoBeneficiario AS TABLE (tipoBeneficiario VARCHAR(100), dtbId TINYINT)
	
	

--	drop table #CondicionBeneficiario
	CREATE TABLE #CondicionBeneficiario
	(
		Id BIGINT IDENTITY,
		TipoIdentificacion VARCHAR(20) COLLATE Latin1_General_CI_AI, 
		NumeroIdentificacion VARCHAR(16) COLLATE Latin1_General_CI_AI,
		TipoBeneficiario VARCHAR(30) COLLATE Latin1_General_CI_AI,
		PrimerNombre VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		SegundoNombre VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		PrimerApellido VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		SegundoApellido VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		EstadoAfiliacion VARCHAR(50) COLLATE Latin1_General_CI_AI,
		FechaCambioEstadoAfiliacion DATETIME,
		FechaPrimeraRevision DATETIME,
		DimPersona BIGINT,
	);

	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactCondicionBeneficiario WHERE fcbDimPeriodo = @DimPeriodoId AND fcbActivoPeriodoAnterior = 'true')
		BEGIN
			WITH cteBeneficiariosActivasPeriodoAnterior AS (
				SELECT @DimPeriodoId AS fcbDimPeriodo,fcbDimPersona,fcbDimBeneficiario,fcbDimEstadoBeneficiario,fcbDimTipoBeneficiario,'false' AS fcbAccionActivacion,'true' AS fcbActivoPeriodoAnterior
				FROM FactCondicionBeneficiario fcb			
				WHERE fcb.fcbDimPeriodo = @DimPeriodoAnteriorId
				AND fcb.fcbDimEstadoBeneficiario = 1
			)
			INSERT INTO FactCondicionBeneficiario (fcbDimPeriodo,fcbDimPersona,fcbDimBeneficiario,fcbDimEstadoBeneficiario,fcbDimTipoBeneficiario,fcbAccionActivacion,fcbActivoPeriodoAnterior)
			SELECT fcbDimPeriodo,fcbDimPersona,fcbDimBeneficiario,fcbDimEstadoBeneficiario,fcbDimTipoBeneficiario,fcbAccionActivacion,fcbActivoPeriodoAnterior
			FROM cteBeneficiariosActivasPeriodoAnterior
		END
	END;

	--Beneficiarios que cambiaron su estado de afiliación durante el incremental de auditoria	
	WITH cteBeneficiario AS (
		SELECT per.perId, per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, per.perPrimerNombre AS PrimerNombre, per.perSegundoNombre AS SegundoNombre, per.perPrimerApellido AS PrimerApellido, per.perSegundoApellido AS SegundoApellido, eab.eabEstadoAfiliacion AS Estadoafiliacion, eab.eabFechaCambioEstado AS FechaCambioEstadoAfiliacion, eab1.eabFechaCambioEstado AS FechaPrimeraRevision, dpe.dpeId AS DimPersona, ben.benTipoBeneficiario AS TipoBeneficiario
		FROM Beneficiario ben
		INNER JOIN Persona per ON ben.benPersona = per.perId
		INNER JOIN Afiliado afi ON ben.benAfiliado = afi.afiId
		INNER JOIN Persona perA ON afi.afiPersona = perA.perId
		INNER JOIN DimPersona dpe ON perA.perTipoIdentificacion = dpe.dpeTipoIdentificacion AND perA.perNumeroIdentificacion = dpe.dpeNumeroIdentificacion
		INNER JOIN (SELECT eab.eabPersona, MAX(eabId) eabId, MIN(eab.eabFechaCambioEstado) eabFechaCambioEstado
					FROM EstadoAfiliacionBeneficiario eab 
					WHERE eab.eabFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eab.eabPersona) eab1 ON perA.perId = eab1.eabPersona
		INNER JOIN EstadoAfiliacionBeneficiario eab ON eab1.eabId = eab.eabId
	)

	INSERT INTO #CondicionBeneficiario (TipoIdentificacion,NumeroIdentificacion,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido,EstadoAfiliacion, FechaCambioEstadoAfiliacion, FechaPrimeraRevision, DimPersona, TipoBeneficiario)
	SELECT TipoIdentificacion,NumeroIdentificacion,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido,EstadoAfiliacion, FechaCambioEstadoAfiliacion, FechaPrimeraRevision, DimPersona, TipoBeneficiario
	FROM cteBeneficiario
	
	MERGE DimBeneficiario AS T
	USING (SELECT DISTINCT S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido FROM #CondicionBeneficiario S) AS S
	ON (	T.dbeTipoIdentificacion = S.TipoIdentificacion AND
			T.dbeNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dbeTipoIdentificacion,dbeNumeroIdentificacion,dbePrimerNombre,dbeSegundoNombre,dbePrimerApellido,dbeSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET dbePrimerNombre = S.PrimerNombre, dbeSegundoNombre = S.SegundoNombre, dbePrimerApellido = S.PrimerApellido, dbeSegundoApellido = S.SegundoApellido;

	INSERT INTO @HomologTipoBeneficiario
	VALUES
		('BENEFICIARIO_EN_CUSTODIA',5),
		('HERMANO_HUERFANO_DE_PADRES', 3),
		('HIJASTRO', 4),
		('HIJO_ADOPTIVO', 4),
		('HIJO_BIOLOGICO', 4),
		('MADRE', 2),
		('PADRE', 2),
		('CONYUGE',1)
		
	;
	WITH cteCondicionBeneficiario AS
	(
		SELECT 
			dbe.dbeId AS fcbDimBeneficiario,
			@DimPeriodoId AS fcbDimPeriodo,
			b.DimPersona AS fcbDimPersona,
			
			(SELECT dtbId FROM @HomologTipoBeneficiario dtb WHERE dtb.tipoBeneficiario = b.TipoBeneficiario) AS fcbDimTipoBeneficiario,
			
			CASE WHEN ISNULL(b.EstadoAfiliacion,'INACTIVO') = 'ACTIVO' THEN 1 ELSE 2 END AS fcbDimEstadoBeneficiario,
			
			CAST((CASE WHEN b.EstadoAfiliacion = 'ACTIVO' AND ISNULL(fcb.fcbDimEstadoBeneficiario,0) <> 1 THEN 'true' ELSE 'false' END) AS BIT) AS fcbAccionActivacion

		FROM #CondicionBeneficiario b
		INNER JOIN DimBeneficiario dbe ON dbe.dbeTipoIdentificacion = b.TipoIdentificacion AND dbe.dbeNumeroIdentificacion = b.NumeroIdentificacion
		LEFT JOIN --Ultimo periodo Activo de diferente Periodo
				( SELECT fcbDimBeneficiario, MAX(fcbId) fcbId
				FROM FactCondicionBeneficiario fcb
				WHERE fcb.fcbDimPeriodo <> @DimPeriodoId
				AND fcb.fcbDimEstadoBeneficiario = 1 --Activo
				GROUP BY fcb.fcbDimBeneficiario
				) fcb1
				ON dbe.dbeId = fcb1.fcbDimBeneficiario	
		LEFT JOIN --Ultimo periodo registrado
				(
					( SELECT fcbDimBeneficiario, MAX(fcbId) fcbId
					FROM FactCondicionBeneficiario fcb
					GROUP BY fcb.fcbDimBeneficiario
					) fcb2
					INNER JOIN FactCondicionBeneficiario fcb ON fcb2.fcbId = fcb.fcbId						
				) ON dbe.dbeId = fcb.fcbDimBeneficiario	
		
		
	)
	
	MERGE FactCondicionBeneficiario AS T
	USING cteCondicionBeneficiario AS S
	ON (T.fcbDimBeneficiario = S.fcbDimBeneficiario AND T.fcbDimPersona = S.fcbDimPersona AND T.fcbDimPeriodo = S.fcbDimPeriodo)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fcbDimPeriodo,fcbDimPersona,fcbDimBeneficiario,fcbDimEstadoBeneficiario,fcbDimTipoBeneficiario,fcbAccionActivacion)
		VALUES (S.fcbDimPeriodo,S.fcbDimPersona,S.fcbDimBeneficiario,S.fcbDimEstadoBeneficiario,S.fcbDimTipoBeneficiario,S.fcbAccionActivacion)
	WHEN MATCHED
		THEN UPDATE SET T.fcbDimTipoBeneficiario = S.fcbDimTipoBeneficiario, T.fcbDimEstadoBeneficiario = S.fcbDimEstadoBeneficiario,T.fcbAccionActivacion = S.fcbAccionActivacion	

;