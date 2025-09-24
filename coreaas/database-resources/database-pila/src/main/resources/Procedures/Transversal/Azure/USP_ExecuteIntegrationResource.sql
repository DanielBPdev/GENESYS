-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/10/13
-- Description:	Procedimiento almacenado encargado  de cargar las tablas del esquema Staging  
-- por Id de Transaccion)
-- Ajuste 2022-06-16
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteIntegrationResource]
       @IdTransaccion Bigint
AS
BEGIN
SET NOCOUNT ON;

DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource INICIO');
	COMMIT TRANSACTION; 

	--print 'Eliminar Info Planillas Tmp CORE'
	EXEC sp_execute_remote CoreReferenceData, N'DELETE FROM pila.Aporte WHERE appTransaccion = @IdTransaccion',N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN borrar core.pila.Aporte');
	COMMIT TRANSACTION; 

	DECLARE @PeriodoAporte date
	DECLARE @PeriodoAporteYYYYMM varchar(7)
	DECLARE @TipoIdentificacionAportante varchar(20)
	DECLARE @NumeroIdentificacionAportante varchar(16)
	DECLARE @TipoIdentificacionCotizante varchar(20)
	DECLARE @NumeroIdentificacionCotizante varchar(16)
	DECLARE @TipoPlanilla varchar(15)
	DECLARE @TipoCotizante varchar(30)
	DECLARE @Transaccion bigint

	EXEC sp_execute_remote CoreReferenceData,
		N'EXEC USP_CopiarPilaAportesStaging @IdTransaccion',
	  	N'@IdTransaccion bigint',
 		@IdTransaccion=@IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN poblar core.pila.Aporte');
	COMMIT TRANSACTION;
	
	--print 'Obtener Parametros CORE'
	DECLARE @parametrosCORE AS TABLE (TarifaBaseEmpleador VARCHAR(255), SMMLV VARCHAR(255), ShardName VARCHAR(1500))

	INSERT INTO @parametrosCORE (TarifaBaseEmpleador, SMMLV, ShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'SELECT
	(SELECT prmValor FROM Parametro WHERE prmNombre = ''TARIFA_BASE_EMPLEADOR'') AS TarifaBaseEmpleador,
	(SELECT prmValor FROM Parametro WHERE prmNombre = ''SMMLV'') AS SMMLV'

	--print 'Actualizar Parametros PILA (Si estos cambian)'

	declare @param1 VARCHAR(255)
	declare @param2 VARCHAR(255)
	SELECT @param1 = TarifaBaseEmpleador FROM @parametrosCORE
	SELECT @param2 = SMMLV FROM @parametrosCORE

	UPDATE stp SET stpValorParametro = @param1 FROM staging.StagingParametros stp WHERE stpNombreParametro = 'TARIFA_BASE_EMPLEADOR' AND stpValorParametro <> @param1 
	UPDATE stp SET stpValorParametro = @param2 FROM staging.StagingParametros stp WHERE stpNombreParametro = 'SMMLV' AND stpValorParametro <> @param2

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN actualizar parametros en pila desde core');
	COMMIT TRANSACTION;

	-- Datos Aportante
	INSERT INTO staging.Aportante (apoTipoIdentificacion,apoNumeroIdentificacion,apoEsEmpleador,apoEstadoEmpleador,apoMarcaSucursalPILA,apoEsEmpleadorReintegrable,apoPeriodoAporte,apoTransaccion,apoShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'
	DECLARE @Parametro AS TABLE (prmNombre VARCHAR(100), prmValor VARCHAR(150));

	DECLARE @TiempoReintegro BIGINT
	SELECT @TiempoReintegro =
		CAST(CASE 
			WHEN prmValor LIKE ''%s'' THEN CAST(REPLACE(prmValor, ''s'', '''') AS BIGINT) * 1000
			WHEN prmValor LIKE ''%m'' THEN CAST(REPLACE(prmValor, ''m'', '''') AS BIGINT) * 60 * 1000
			WHEN prmValor LIKE ''%h'' THEN CAST(REPLACE(prmValor, ''h'', '''') AS BIGINT) * 60 * 60 * 1000
			WHEN prmValor LIKE ''%d'' THEN CAST(REPLACE(prmValor, ''d'', '''') AS BIGINT) * 24 * 60 * 60 * 1000
			ELSE prmValor
		END AS VARCHAR(150)) 
	FROM dbo.Parametro WHERE prmNombre = ''TIEMPO_REINTEGRO'';

	SET @TiempoReintegro = CAST(@TiempoReintegro AS BIGINT)/86400000;

	WITH cteEstadoAportante AS
	(

		SELECT perTipoIdentificacion,perNumeroIdentificacion,
				Empleador.empId,
					case when apg.existe is null and Empleador.empId is null then ''NO_FORMALIZADO_CON_INFORMACION''
					 when apg.existe is not null and Empleador.empId is null then ''NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES''
					 when apg.existe is not null and isnull(Empleador.empEstadoEmpleador, vw.empEstadoEmpleador) = ''INACTIVO''
						THEN (CASE WHEN convert(varchar(7), Empleador.empFechaRetiro,126) < appPeriodoAporteYYYYMM THEN ''NO_FORMALIZADO_RETIRADO_CON_APORTES'' ELSE isnull(Empleador.empEstadoEmpleador, vw.empEstadoEmpleador) END)
					else isnull(Empleador.empEstadoEmpleador, vw.empEstadoEmpleador)
					end as estadoEmpleador,
				empValidarSucursalPila,
				empMotivoDesafiliacion,
				empFechaRetiro,
				appPeriodoAporteYYYYMM
		FROM dbo.VW_EstadoAfiliacionEmpleadorCaja vw
		INNER JOIN Empresa ON vw.perId = Empresa.empPersona
		INNER JOIN pila.Aporte app ON vw.perTipoIdentificacion = app.appTipoIdentificacionAportante 
									AND vw.perNumeroIdentificacion = app.appNumeroIdentificacionAportante 
									AND app.appTipoPlanilla IN (''DEPENDIENTE'', ''MIXTA'')
									AND app.appTransaccion = @IdTransaccion
		LEFT JOIN  Empleador ON Empresa.empId = Empleador.empEmpresa
		left join (select count(1) existe, apgEmpresa from AporteGeneral with (nolock) group by apgEmpresa) as apg on apg.apgEmpresa = Empresa.empId
	)


	SELECT DISTINCT cte.perTipoIdentificacion, cte.perNumeroIdentificacion,
	CASE WHEN cte.empId IS NULL THEN 0 ELSE 1 END AS esEmpleador,
	cte.estadoEmpleador,ISNULL(cte.empValidarSucursalPila,0) AS MarcaSucursalPILA,
	CASE WHEN cte.estadoEmpleador IN (''INACTIVO'',''NO_FORMALIZADO_RETIRADO_CON_APORTES'')
				AND cte.empMotivoDesafiliacion IN (''CERO_TRABAJADORES_SOLICITUD_EMPLEADOR'',''CERO_TRABAJADORES_NOVEDAD_INTERNA'')
				AND DATEDIFF(day, cte.empFechaRetiro, dbo.GetLocalDate()) <= @TiempoReintegro
				AND ISNULL((
					SELECT MAX(CAST(pedFallecido AS SMALLINT))
					FROM PersonaDetalle ped
					INNER JOIN Persona per ON ped.pedPersona = per.perId
					WHERE per.perTipoIdentificacion = cte.perTipoIdentificacion
					AND per.perNumeroIdentificacion = cte.perNumeroIdentificacion
				), 0) = 0 
		THEN 1 ELSE 0 END AS EsEmpleadorReintegrable,
		cte.appPeriodoAporteYYYYMM,
		@IdTransaccion AS IdTransaccion
	FROM cteEstadoAportante cte ',N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos aportante');
	COMMIT TRANSACTION;

	--print 'Cotizantes CORE -> PILA (Stg)'
	INSERT INTO staging.Cotizante (
	cotTipoIdentificacion,cotNumeroIdentificacion,cotTipoIdentificacionEmpleador,cotNumeroIdentificacionEmpleador,
	cotFechaFallecido,cotPorcentajePagoAportes,cotEstadoAfiliado,
	cotClaseTrabajador,cotTipoAfiliado,
	cotCodigoSucursal,cotNombreSucursal,cotFechaIngreso,cotFechaRetiro,cotFechaUltimaNovedad,
	cotEsTrabajadorReintegrable,cotGrupoFamiliarReintegrable,
	cotPeriodoAporte,cotTransaccion,cotPeriodicidad,cotShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'
	DECLARE @localDate DATETIME = dbo.getLocalDate()

	DECLARE @TiempoReintegroGF BIGINT
	SELECT @TiempoReintegroGF =
		CAST(CASE 
			WHEN prmValor LIKE ''%s'' THEN CAST(REPLACE(prmValor, ''s'', '''') AS BIGINT) * 1000
			WHEN prmValor LIKE ''%m'' THEN CAST(REPLACE(prmValor, ''m'', '''') AS BIGINT) * 60 * 1000
			WHEN prmValor LIKE ''%h'' THEN CAST(REPLACE(prmValor, ''h'', '''') AS BIGINT) * 60 * 60 * 1000
			WHEN prmValor LIKE ''%d'' THEN CAST(REPLACE(prmValor, ''d'', '''') AS BIGINT) * 24 * 60 * 60 * 1000
			ELSE prmValor
		END AS VARCHAR(150))
	FROM dbo.Parametro WHERE prmNombre = ''TIEMPO_REINTEGRO_GF'';
	
	SET @TiempoReintegroGF = CAST(@TiempoReintegroGF AS BIGINT)/86400000

	DECLARE @Porcentajes AS TABLE (Tipo VARCHAR(30), Clasificacion VARCHAR(100), Valor DECIMAL(5,5));
	INSERT INTO @Porcentajes (Tipo,Clasificacion,Valor)
	VALUES 
		(''TRABAJADOR_DEPENDIENTE'',''TRABAJADOR_DEPENDIENTE'', 0),
		(''TRABAJADOR_INDEPENDIENTE'',''TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO'', 0.02),
		(''TRABAJADOR_INDEPENDIENTE'',''TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO'', 0.006),
		(''PENSIONADO'',''FIDELIDAD_25_ANIOS'', 0),
		(''PENSIONADO'',''MENOS_1_5_SM_0_POR_CIENTO'', 0),
		(''PENSIONADO'',''MENOS_1_5_SM_0_6_POR_CIENTO'', 0.006),
		(''PENSIONADO'',''MENOS_1_5_SM_2_POR_CIENTO'', 0.02),
		(''PENSIONADO'',''MAS_1_5_SM_0_6_POR_CIENTO'', 0.006),
		(''PENSIONADO'',''MAS_1_5_SM_2_POR_CIENTO'', 0.02),
		(''PENSIONADO'',''PENSION_FAMILIAR'', 0);

	CREATE TABLE #estadosAfiliacionCotizantes (
		idPersona BIGINT, 
		idEmpleador BIGINT, 
		estadoAfiliacion VARCHAR(50),
		tipoAfiliado VARCHAR(50),
		rolAfiliado BIGINT, 
		fechaRetiro DATETIME, 
		motivo VARCHAR(50),
		periodicidad VARCHAR(11)
	);

	INSERT INTO #estadosAfiliacionCotizantes (idPersona, idEmpleador, estadoAfiliacion, tipoAfiliado, rolAfiliado, fechaRetiro, motivo, periodicidad)
		SELECT persona, empleador, estado, tipo, roaId, roaFechaRetiro, roaMotivoDesafiliacion, roaOportunidadPago 
		FROM pila.Aporte
		INNER JOIN Persona ON (perTipoIdentificacion = appTipoIdentificacionCotizante 
			AND perNumeroIdentificacion = appNumeroIdentificacionCotizante)
		INNER JOIN (
			SELECT vw.perId persona, vw.roaEmpleador AS empleador, vw.roaEstadoAfiliado estado, appTipoCotizante tipo 
			FROM VW_EstadoAfiliacionPersonaEmpresa vw
			INNER JOIN pila.Aporte app ON app.appTipoIdentificacionCotizante = vw.perTipoIdentificacion AND app.appNumeroIdentificacionCotizante = vw.perNumeroIdentificacion AND app.appTransaccion = @IdTransaccion
			WHERE appTipoCotizante = ''TRABAJADOR_DEPENDIENTE''
			UNION
			SELECT vw.perId persona, NULL AS empleador, vw.roaEstadoAfiliado estado, appTipoCotizante tipo 
			FROM VW_EstadoAfiliacionPersonaIndependiente vw
			INNER JOIN pila.Aporte app ON app.appTipoIdentificacionCotizante = vw.perTipoIdentificacion AND app.appNumeroIdentificacionCotizante = vw.perNumeroIdentificacion AND app.appTransaccion = @IdTransaccion
			WHERE appTipoCotizante = ''TRABAJADOR_INDEPENDIENTE''
			UNION
			SELECT vw.perId persona, NULL AS empleador, vw.roaEstadoAfiliado estado, appTipoCotizante tipo 
			FROM VW_EstadoAfiliacionPersonaPensionado vw
			INNER JOIN pila.Aporte app ON app.appTipoIdentificacionCotizante = vw.perTipoIdentificacion AND app.appNumeroIdentificacionCotizante = vw.perNumeroIdentificacion AND app.appTransaccion = @IdTransaccion
			WHERE appTipoCotizante = ''PENSIONADO''
		) estados ON persona = perId
		LEFT JOIN Afiliado afi ON afi.afiPersona = perId 
		LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId 
			AND roa.roaTipoAfiliado = estados.tipo
			AND ISNULL(roa.roaEmpleador, 0) = ISNULL(estados.empleador, 0);
	
	CREATE INDEX #IX_estadosAfiliacionCotizantes1 ON #estadosAfiliacionCotizantes (idPersona)
	--CREATE INDEX #IX_estadosAfiliacionCotizantes2 ON #estadosAfiliacionCotizantes (idPersona, tipoAfiliado)
	
	;
	WITH cteEstadosCotizante AS
	(
		--TipoCotizante: DEPENDIENTE
		SELECT 
		roaId
		,ptr.perId
		,roaAfiliado,roaSucursalEmpleador,appPeriodoAporteYYYYMM	
		,appTipoIdentificacionCotizante
		,appNumeroIdentificacionCotizante
		,appTipoIdentificacionAportante
		,appNumeroIdentificacionAportante
		,roa.roaClaseTrabajador,roa.roaTipoAfiliado,appTipoCotizante
		,roa.roaFechaAfiliacion, roa.roaFechaRetiro
		,CASE 
			WHEN vw.estadoAfiliacion IS NULL AND ptr.perId IS NOT NULL THEN ''NO_FORMALIZADO_CON_INFORMACION'' 
			WHEN vw.estadoAfiliacion IS NOT NULL THEN vw.estadoAfiliacion 
			ELSE NULL 
		END roaEstadoAfiliado
		,roa.roaMotivoDesafiliacion, roa.roaPorcentajePagoAportes, roa.roaEmpleador
		, ISNULL(periodicidad, ''MES_VENCIDO'') oportunidadPago
		FROM pila.Aporte app 
		LEFT JOIN Persona pem ON 
			pem.perTipoIdentificacion = app.appTipoIdentificacionAportante 
			AND pem.perNumeroIdentificacion = app.appNumeroIdentificacionAportante
		LEFT JOIN Persona ptr ON
			ptr.perTipoIdentificacion = app.appTipoIdentificacionCotizante 
			AND ptr.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante
		LEFT JOIN Empresa emp ON pem.perId = emp.empPersona
		LEFT JOIN Empleador eml ON emp.empId = eml.empEmpresa
		LEFT JOIN #estadosAfiliacionCotizantes vw ON
			tipoAfiliado = appTipoCotizante
			AND idPersona = ptr.perId
			AND idEmpleador = eml.empId
		LEFT JOIN (Afiliado afi
					INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado) ON ptr.perId = afi.afiPersona AND roa.roaEmpleador = eml.empId
		WHERE app.appTransaccion = @IdTransaccion
		AND app.appTipoCotizante = ''TRABAJADOR_DEPENDIENTE''
	
		UNION ALL

		--INDEPENDIENTES
		SELECT
		roaId,ptr.perId,roaAfiliado,roaSucursalEmpleador,appPeriodoAporteYYYYMM	
		,appTipoIdentificacionCotizante
		,appNumeroIdentificacionCotizante
		,appTipoIdentificacionAportante
		,appNumeroIdentificacionAportante
		,roa.roaClaseTrabajador,roa.roaTipoAfiliado,appTipoCotizante
		,roa.roaFechaAfiliacion, roa.roaFechaRetiro
		,CASE 
			WHEN vw.estadoAfiliacion IS NULL AND ptr.perId IS NOT NULL THEN ''NO_FORMALIZADO_CON_INFORMACION'' 
			WHEN vw.estadoAfiliacion IS NOT NULL THEN vw.estadoAfiliacion 
			ELSE NULL 
		END roaEstadoAfiliado
		,roa.roaMotivoDesafiliacion, roa.roaPorcentajePagoAportes, roa.roaEmpleador
		, ISNULL(periodicidad, ''MES_VENCIDO'') oportunidadPago
		FROM pila.Aporte app 
		LEFT JOIN Persona ptr ON
			ptr.perTipoIdentificacion = app.appTipoIdentificacionCotizante 
			AND ptr.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante
		LEFT JOIN Afiliado afi ON ptr.perId = afi.afiPersona
		LEFT JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND app.appTipoCotizante = roa.roaTipoAfiliado
		LEFT JOIN #estadosAfiliacionCotizantes vw ON
			tipoAfiliado = appTipoCotizante
			AND idPersona = ptr.perId
		WHERE app.appTransaccion = @IdTransaccion
		AND app.appTipoCotizante IN (''TRABAJADOR_INDEPENDIENTE'')

		UNION ALL

		-- PENSIONADOS
		SELECT
		roaId,ptr.perId,roaAfiliado,roaSucursalEmpleador,appPeriodoAporteYYYYMM	
		,appTipoIdentificacionCotizante
		,appNumeroIdentificacionCotizante
		,appTipoIdentificacionAportante
		,appNumeroIdentificacionAportante
		,roa.roaClaseTrabajador,roa.roaTipoAfiliado,appTipoCotizante
		,roa.roaFechaAfiliacion, roa.roaFechaRetiro
		,CASE 
			WHEN vw.estadoAfiliacion IS NULL AND ptr.perId IS NOT NULL THEN ''NO_FORMALIZADO_CON_INFORMACION'' 
			WHEN vw.estadoAfiliacion IS NOT NULL THEN vw.estadoAfiliacion 
			ELSE NULL 
		END roaEstadoAfiliado
		,roa.roaMotivoDesafiliacion, roa.roaPorcentajePagoAportes, roa.roaEmpleador
		, ISNULL(periodicidad, ''MES_VENCIDO'') oportunidadPago
		FROM pila.Aporte app 
		LEFT JOIN Persona ptr ON
			ptr.perTipoIdentificacion = app.appTipoIdentificacionCotizante 
			AND ptr.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante
		LEFT JOIN Afiliado afi ON ptr.perId = afi.afiPersona
		LEFT JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado AND app.appTipoCotizante = roa.roaTipoAfiliado
		LEFT JOIN #estadosAfiliacionCotizantes vw ON
			tipoAfiliado = appTipoCotizante
			AND idPersona = ptr.perId
		WHERE app.appTransaccion = @IdTransaccion
		AND app.appTipoCotizante IN (''PENSIONADO'')
	)

	SELECT 
		appTipoIdentificacionCotizante perTipoIdentificacion
		,appNumeroIdentificacionCotizante perNumeroIdentificacion
		,appTipoIdentificacionAportante empTipoIdentificacion
		,appNumeroIdentificacionAportante empNumeroIdentificacion
		,PersonaDetalle.pedFechaFallecido
		,CASE 
			WHEN cte.appTipoCotizante = ''TRABAJADOR_DEPENDIENTE'' THEN 0 
			WHEN cte.appTipoCotizante != ''TRABAJADOR_DEPENDIENTE'' AND cte.roaPorcentajePagoAportes IS NOT NULL THEN cte.roaPorcentajePagoAportes 
			ELSE p.Valor 
		END PorcentajePagoAportes
		,cte.roaEstadoAfiliado AS roaEstadoAfiliado
		,cte.roaClaseTrabajador,appTipoCotizante AS roaTipoAfiliado
		,SucursalEmpresa.sueCodigo, SucursalEmpresa.sueNombre,cte.roaFechaAfiliacion, cte.roaFechaRetiro, ISNULL(snp.FechaUltimaNovedad, cte.roaFechaRetiro) FechaUltimaNovedad
		, CASE
			--Si la pesona está fallecida no se permite su activación
			WHEN ISNULL(PersonaDetalle.pedFallecido,0) = 1 THEN 0  
			-- caso 1: estado actual por tipo de afiliación reintegrable
			WHEN ISNULL(cte.roaEstadoAfiliado, '''') IN (''INACTIVO'',''NO_FORMALIZADO_RETIRADO_CON_APORTES'') AND ISNULL(cte.roaMotivoDesafiliacion,'''') NOT IN (''FALLECIMIENTO'', ''AFILIACION_ANULADA'') THEN 1
			-- caso 2: está o estuvo formalizado por otro empleador o tipo de afiliación
			WHEN EXISTS (
				SELECT TOP 1 1 
				FROM #estadosAfiliacionCotizantes epc
				WHERE epc.idPersona = cte.perId 
					AND epc.rolAfiliado IS NOT NULL
					AND epc.rolAfiliado != ISNULL(cte.roaAfiliado, 0)
					AND (epc.idEmpleador IS NULL OR epc.idEmpleador != ISNULL(roaEmpleador, 0))
					AND ISNULL(epc.motivo, '''') NOT IN (''FALLECIMIENTO'', ''AFILIACION_ANULADA'')
			) THEN 1 ELSE 0 
		END EsTrabajadorReintegrable
		,CASE WHEN EXISTS (
			SELECT TOP 1 1 
			FROM #estadosAfiliacionCotizantes epc
			WHERE epc.idPersona = cte.perId
				AND ISNULL(PersonaDetalle.pedFallecido,0) = 0  
				AND epc.fechaRetiro IS NOT NULL
				AND ISNULL(epc.motivo, '''') NOT IN (''FALLECIMIENTO'', ''AFILIACION_ANULADA'')
				AND DATEDIFF(day, epc.fechaRetiro, dbo.GetLocalDate()) <= @TiempoReintegroGF 
		) THEN 1 ELSE 0 END grupoFamiliarReintegrable
		,cte.appPeriodoAporteYYYYMM
		, @IdTransaccion AS IdTransaccion, oportunidadPago
	FROM cteEstadosCotizante cte
	LEFT JOIN (
		SELECT MAX(sapSolicitudGlobal) sapSolicitudGlobal, sapRolAfiliado
		FROM SolicitudAfiliacionPersona
		INNER JOIN cteEstadosCotizante cte2 ON cte2.roaId = sapRolAfiliado
		WHERE sapEstadoSolicitud <> ''PRE_RADICADA''
		GROUP BY sapRolAfiliado
	) sap ON cte.roaId = sapRolAfiliado
	LEFT JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
	LEFT JOIN @Porcentajes p ON cte.roaTipoAfiliado = p.Tipo AND sol.solClasificacion = p.Clasificacion
	LEFT JOIN PersonaDetalle ON cte.perId = PersonaDetalle.pedPersona
	LEFT JOIN (
		SELECT snp.snpRolAfiliado, MAX(ISNULL(nop.nopFechaFin, ISNULL(nop.nopFechaInicio, sol.solFechaRadicacion))) FechaUltimaNovedad
		FROM Solicitud sol
		INNER JOIN SolicitudNovedad sno ON sol.solId = sno.snoId
		INNER JOIN SolicitudNovedadPersona snp ON sno.snoId = snp.snpSolicitudNovedad
		INNER JOIN cteEstadosCotizante cte2 ON cte2.roaId = snpRolAfiliado
		INNER JOIN NovedadDetalle nop ON sno.snoId = nop.nopSolicitudNovedad
		WHERE sol.solResultadoProceso = ''APROBADA''
		GROUP BY snp.snpRolAfiliado
	) snp ON snp.snpRolAfiliado = cte.roaAfiliado
	LEFT JOIN SucursalEmpresa ON cte.roaSucursalEmpleador = SucursalEmpresa.sueId
	
	DROP TABLE #estadosAfiliacionCotizantes'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos cotizante');
	COMMIT TRANSACTION;

	--print 'Novedades CORE -> PILA (Stg)'
	INSERT INTO staging.Novedad (novTipoIdentificacion,novNumeroIdentificacion,novTipoNovedad,novFechaInicio,novFechaFin,novPeriodoInicio,novPeriodoFin,novTransaccion,novShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'
	SELECT DISTINCT app.appPeriodoAporte FechaInicio, EOMONTH(appPeriodoAporte) FechaFin, app.appTipoIdentificacionCotizante, app.appNumeroIdentificacionCotizante
	INTO #Aportes
	FROM pila.Aporte app
	WHERE app.appTransaccion = @IdTransaccion

	CREATE INDEX #IX_Aportes ON #Aportes (appTipoIdentificacionCotizante, appNumeroIdentificacionCotizante)

	;
	WITH NovedadeCore (TipoTransaccion, TipoNovedad)
	AS
	(
		SELECT *
		FROM (VALUES
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL'', ''NOVEDAD_LMA''),
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB'', ''NOVEDAD_LMA''),
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB'', ''NOVEDAD_LMA''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL'', ''NOVEDAD_IGE''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB'', ''NOVEDAD_IGE''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB'', ''NOVEDAD_IGE''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB'', ''NOVEDAD_SLN''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_WEB'', ''NOVEDAD_VAC_LR''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB'', ''NOVEDAD_VST''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_DEPWEB'', ''NOVEDAD_VSP''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL'',''NOVEDAD_IRL''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB'',''NOVEDAD_IRL''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB'',''NOVEDAD_IRL'')
		) t(TipoTransaccion, TipoNovedad)
	)
	
	SELECT DISTINCT
			 perTipoIdentificacion
			,perNumeroIdentificacion
			,CAST(nov.novTipoTransaccion AS VARCHAR(15)) AS TipoNovedad
			,nop.nopFechaInicio,nop.nopFechaFin
			,CONVERT(VARCHAR(7),nop.nopFechaInicio,120)  periodoInicio
			,CONVERT(VARCHAR(7),nop.nopFechaFin,120)  periodoFin
			,@IdTransaccion AS IdTransaccion
	FROM Solicitud sol
	INNER JOIN SolicitudNovedad sno ON sol.solId = sno.snoSolicitudGlobal
	INNER JOIN SolicitudNovedadPersona snp ON sno.snoId = snp.snpSolicitudNovedad
	INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
	INNER JOIN NovedadDetalle nop ON sno.snoId = nop.nopSolicitudNovedad
	INNER JOIN Persona per ON snpPersona = per.perId
	INNER JOIN NovedadeCore NC ON nov.novTipoTransaccion = NC.TipoTransaccion
	INNER JOIN #Aportes app ON per.perTipoIdentificacion = app.appTipoIdentificacionCotizante AND per.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante
	WHERE sol.solResultadoProceso = ''APROBADA''
	AND nov.novAplicaTodosRoles = 1
	AND (
			nop.nopFechaInicio BETWEEN app.FechaInicio AND app.FechaFin OR
			nop.nopFechaFin BETWEEN app.FechaInicio AND app.FechaFin OR
			app.FechaInicio BETWEEN nop.nopFechaInicio AND nop.nopFechaFin OR
			app.FechaFin BETWEEN nop.nopFechaInicio AND nop.nopFechaFin
		)

	DROP TABLE #Aportes'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos novedades');
	COMMIT TRANSACTION;

	--print 'SucursalEmpresa CORE -> PILA (Stg)'
	INSERT INTO staging.SucursalEmpresa (sueTipoIdentificacion,sueNumeroIdentificacion,sueCodigoSucursal,sueNombreSucursal,sueEsSucursalPrincipal,sueTransaccion,sueShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N' 
	
		;with suc as (
		SELECT per.perTipoIdentificacion, per.perNumeroIdentificacion, sue.sueCodigo, sue.sueNombre, sueSucursalPrincipal, sueEstadoSucursal
		, count(*) over (partition by per.perTipoIdentificacion, per.perNumeroIdentificacion, sueSucursalPrincipal, upper(ltrim(rtrim(sue.sueNombre))) order by sueEstadoSucursal) as cant
		FROM dbo.SucursalEmpresa sue with (nolock)
		INNER JOIN dbo.Empresa emp with (nolock) ON sue.sueEmpresa = emp.empId
		INNER JOIN dbo.Persona per with (nolock) ON emp.empPersona = per.perId
		WHERE EXISTS (
				SELECT TOP 1 1 
				FROM pila.Aporte app with (nolock)
				WHERE per.perTipoIdentificacion = app.appTipoIdentificacionAportante AND per.perNumeroIdentificacion = app.appNumeroIdentificacionAportante 
				AND app.appTipoPlanilla IN (''DEPENDIENTE'', ''MIXTA'')
				AND app.appTransaccion = @IdTransaccion
				)
		order by per.perNumeroIdentificacion 
		offset 0 rows fetch next 100000000 rows only),
		suc2 as (
		select *, case when cant = 2 and sueSucursalPrincipal = 1 then 0 else 1 end as dep
		from suc)
		select perTipoIdentificacion, perNumeroIdentificacion, sueCodigo, sueNombre, sueSucursalPrincipal, @IdTransaccion AS IdTransaccion
		from suc2
		where dep = 1

	'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos sucursal empresa');
	COMMIT TRANSACTION;
		

	--print 'AportesPeriodo CORE -> PILA (Stg)'
	
	INSERT INTO staging.aporteperiodo (appTipoIdentificacionCotizante,appNumeroIdentificacionCotizante,appTipoIdentificacionAportante,appNumeroIdentificacionAportante,
	appPeriodoAporte,appDiasCotizados,appTransaccion,appShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'SELECT perC.perTipoIdentificacion copTipoIdentificacion, perC.perNumeroIdentificacion copNumeroIdentificacion, perA.perTipoIdentificacion copTipoIdentificacionAportante, perA.perNumeroIdentificacion copNumeroIdentificacionAportante, 
	apg.apgPeriodoAporte copPeriodoAporte, SUM(ISNULL(apd.apdDiasCotizados,0)) apdDiasCotizados, @IdTransaccion AS IdTransaccion 
	FROM dbo.AporteGeneral apg
	INNER JOIN dbo.AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
	INNER JOIN dbo.Empresa emp ON apg.apgEmpresa = emp.empId
	INNER JOIN dbo.Persona perA ON emp.empPersona = perA.perId
	INNER JOIN dbo.Persona perC ON apd.apdPersona = perC.perId
	INNER JOIN pila.Aporte app 
			ON perC.perTipoIdentificacion = app.appTipoIdentificacionCotizante AND perC.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante 
			AND perA.perTipoIdentificacion = app.appTipoIdentificacionAportante AND perA.perNumeroIdentificacion = app.appNumeroIdentificacionAportante
			AND app.appPeriodoAporteYYYYMM = apg.apgPeriodoAporte
			AND app.appTipoCotizante = ''TRABAJADOR_DEPENDIENTE''
			AND app.appTransaccion = @IdTransaccion
	GROUP BY perC.perTipoIdentificacion, perC.perNumeroIdentificacion, perA.perTipoIdentificacion, perA.perNumeroIdentificacion, 
	apg.apgPeriodoAporte'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;
	

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos periodo aporte');
	COMMIT TRANSACTION;


	--print 'NovedadesSituacionPrimaria CORE -> Pila (Stg)'
	INSERT INTO staging.NovedadSituacionPrimaria (nspTipoIdentificacionCotizante,nspNumeroIdentificacionCotizante,nspTipoIdentificacionAportante,nspNumeroIdentificacionAportante,nspTipoNovedad,nspFechaInicio,nspFechaFin,nspPeriodoRegular,nspTransaccion,nspShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'
	DECLARE 
	@FechaInicioR DATETIME,
	@FechaFinR DATETIME,
	@FechaInicioA DATETIME,
	@FechaFinA DATETIME

	
	SET @FechaInicioR = CONVERT(VARCHAR(6), DATEADD(MONTH,-1,dbo.GetLocalDate()), 112) + ''01''
	--SET @FechaInicioR = ''20200201''
	SET @FechaFinR = DATEADD(DAY,-1,DATEADD(MONTH,1,@FechaInicioR))
	
	SET @FechaInicioA = CONVERT(VARCHAR(6), dbo.GetLocalDate(), 112) + ''01''
	--SET @FechaInicioA = ''20200301''
	SET @FechaFinA = DATEADD(DAY,-1,DATEADD(MONTH,1,@FechaInicioA))

	;
	WITH NovedadeCore (TipoTransaccion, TipoNovedad)
	AS
	(
	SELECT *
	FROM (VALUES
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL'', ''NOVEDAD_LMA''),
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB'', ''NOVEDAD_LMA''),
		(''LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB'', ''NOVEDAD_LMA''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL'', ''NOVEDAD_IGE''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB'', ''NOVEDAD_IGE''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB'', ''NOVEDAD_IGE''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_SLN''),
		(''SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB'', ''NOVEDAD_SLN''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VAC_LR''),
		(''VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_WEB'', ''NOVEDAD_VAC_LR''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VST''),
		(''VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB'', ''NOVEDAD_VST''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL'', ''NOVEDAD_VSP''),
		(''VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_DEPWEB'', ''NOVEDAD_VSP''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL'',''NOVEDAD_IRL''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB'',''NOVEDAD_IRL''),
		(''INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB'',''NOVEDAD_IRL'')
		) t(TipoTransaccion, TipoNovedad)
	)

	--Fecha última novedad registrada del período por persona (cono individual o como dependiente)
	,NPersonas
	AS (
		SELECT
				per.perTipoIdentificacion AS TipoIdentificacionCotizante
				,per.perNumeroIdentificacion AS NumeroIdentificacionCotizante
				,ISNULL(perE.perTipoIdentificacion, per.perTipoIdentificacion) AS TipoIdentificacionAportante
				,ISNULL(perE.perNumeroIdentificacion, per.perNumeroIdentificacion) AS NumeroIdentificacionAportante
				,NC.TipoNovedad
				,ISNULL(nop.nopFechaInicio, sol.solFechaRadicacion) AS FechaInicio
				,ISNULL(nop.nopFechaFin, ISNULL(nop.nopFechaInicio, sol.solFechaRadicacion)) AS FechaFin
				,sol.solFechaRadicacion AS FechaRadicacion,
				nop.nopFechaInicio,
				nop.nopFechaFin,
				app.appPeriodoAporte
		FROM Solicitud sol
		INNER JOIN SolicitudNovedad sno ON sol.solId = sno.snoId
		INNER JOIN SolicitudNovedadPersona snp ON sno.snoId = snp.snpSolicitudNovedad
		INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
		INNER JOIN NovedadDetalle nop ON sno.snoId = nop.nopSolicitudNovedad
		INNER JOIN Persona per ON snpPersona = per.perId		
		INNER JOIN NovedadeCore NC ON nov.novTipoTransaccion = NC.TipoTransaccion
		LEFT JOIN RolAfiliado ON snp.snpRolAfiliado = RolAfiliado.roaAfiliado
		LEFT JOIN Empleador ON RolAfiliado.roaEmpleador = Empleador.empId
		LEFT JOIN Empresa ON Empresa.empId = Empleador.empEmpresa
		LEFT JOIN Persona PerE ON PerE.perId = Empresa.empPersona
		INNER JOIN pila.Aporte app on per.perTipoIdentificacion = app.appTipoIdentificacionCotizante AND per.perNumeroIdentificacion = app.appNumeroIdentificacionCotizante
		WHERE sol.solResultadoProceso = ''APROBADA''
		AND app.appTransaccion = @IdTransaccion
	)
	SELECT *
	INTO #NPersonas
	FROM NPersonas
		
	;
	WITH NPersonasFechasPR AS 
	(
		SELECT	TipoIdentificacionCotizante, NumeroIdentificacionCotizante, 
				TipoIdentificacionAportante, NumeroIdentificacionAportante,
				TipoNovedad, FechaInicio, FechaFin, FechaRadicacion
		FROM #NPersonas
		WHERE (
				nopFechaInicio BETWEEN @FechaInicioR AND @FechaFinR OR
				nopFechaFin BETWEEN @FechaInicioR AND @FechaFinR OR
				@FechaInicioR BETWEEN nopFechaInicio AND nopFechaFin OR
				@FechaFinR BETWEEN nopFechaInicio AND nopFechaFin
			)
		AND appPeriodoAporte = @FechaInicioR
	)
	, NNovedadesPR AS
	(
		Select	P.TipoIdentificacionCotizante,
				P.NumeroIdentificacionCotizante,
				P.TipoIdentificacionAportante,
				P.NumeroIdentificacionAportante,
				P.TipoNovedad,
				P.FechaInicio,
				P.FechaFin,
				CONVERT(VARCHAR(7),@FechaInicioR,120)  PeriodoRegular
		from NPersonasFechasPR P
		INNER JOIN (
			Select P1.TipoIdentificacionCotizante,P1.NumeroIdentificacionCotizante, P1.TipoIdentificacionAportante, P1.NumeroIdentificacionAportante, MAX(FechaRadicacion) AS FechaRadicacion
			from NPersonasFechasPR P1
			INNER JOIN (
						Select TipoIdentificacionCotizante,NumeroIdentificacionCotizante, TipoIdentificacionAportante, NumeroIdentificacionAportante,  MAX(FechaFin) AS FechaFin
						from NPersonasFechasPR
						GROUP BY TipoIdentificacionCotizante,NumeroIdentificacionCotizante,TipoIdentificacionAportante,NumeroIdentificacionAportante
					   ) P2 ON P1.TipoIdentificacionCotizante = P2.TipoIdentificacionCotizante AND P1.NumeroIdentificacionCotizante = P2.NumeroIdentificacionCotizante AND P1.TipoIdentificacionAportante = P2.TipoIdentificacionAportante AND P1.NumeroIdentificacionAportante = P2.NumeroIdentificacionAportante AND P1.FechaFin = P2.FechaFin
			GROUP BY P1.TipoIdentificacionCotizante,P1.NumeroIdentificacionCotizante,P1.TipoIdentificacionAportante,P1.NumeroIdentificacionAportante
		) P3 ON P.TipoIdentificacionCotizante = P3.TipoIdentificacionCotizante AND P.TipoIdentificacionAportante = P3.TipoIdentificacionAportante AND P.NumeroIdentificacionAportante = P3.NumeroIdentificacionAportante AND P.NumeroIdentificacionCotizante = P3.NumeroIdentificacionCotizante AND P.FechaRadicacion = P3.FechaRadicacion
	)
	,NPersonasFechasPA AS 
	(
		SELECT TipoIdentificacionCotizante, NumeroIdentificacionCotizante, 
				TipoIdentificacionAportante, NumeroIdentificacionAportante,
				TipoNovedad, FechaInicio, FechaFin, FechaRadicacion
		FROM #NPersonas
		WHERE (
				nopFechaInicio BETWEEN @FechaInicioA AND @FechaFinA OR
				nopFechaFin BETWEEN @FechaInicioA AND @FechaFinA OR
				@FechaInicioA BETWEEN nopFechaInicio AND nopFechaFin OR
				@FechaFinA BETWEEN nopFechaInicio AND nopFechaFin
			)
		AND appPeriodoAporte = @FechaInicioA
	)
	, NNovedadesPA AS
	(
		Select	P.TipoIdentificacionCotizante,
				P.NumeroIdentificacionCotizante,
				P.TipoIdentificacionAportante,
				P.NumeroIdentificacionAportante,
				P.TipoNovedad,
				P.FechaInicio,
				P.FechaFin,
				CONVERT(VARCHAR(7),@FechaInicioA,120)  PeriodoRegular
		from NPersonasFechasPA P
		INNER JOIN (
			Select P1.TipoIdentificacionCotizante,P1.NumeroIdentificacionCotizante, P1.TipoIdentificacionAportante, P1.NumeroIdentificacionAportante, MAX(FechaRadicacion) AS FechaRadicacion
			from NPersonasFechasPA P1
			INNER JOIN (
						Select TipoIdentificacionCotizante,NumeroIdentificacionCotizante, TipoIdentificacionAportante, NumeroIdentificacionAportante,  MAX(FechaFin) AS FechaFin
						from NPersonasFechasPA
						GROUP BY TipoIdentificacionCotizante,NumeroIdentificacionCotizante,TipoIdentificacionAportante,NumeroIdentificacionAportante
					   ) P2 ON P1.TipoIdentificacionCotizante = P2.TipoIdentificacionCotizante AND P1.NumeroIdentificacionCotizante = P2.NumeroIdentificacionCotizante AND P1.TipoIdentificacionAportante = P2.TipoIdentificacionAportante AND P1.NumeroIdentificacionAportante = P2.NumeroIdentificacionAportante AND P1.FechaFin = P2.FechaFin
			GROUP BY P1.TipoIdentificacionCotizante,P1.NumeroIdentificacionCotizante,P1.TipoIdentificacionAportante,P1.NumeroIdentificacionAportante
		) P3 ON P.TipoIdentificacionCotizante = P3.TipoIdentificacionCotizante AND P.TipoIdentificacionAportante = P3.TipoIdentificacionAportante AND P.NumeroIdentificacionAportante = P3.NumeroIdentificacionAportante AND P.NumeroIdentificacionCotizante = P3.NumeroIdentificacionCotizante AND P.FechaRadicacion = P3.FechaRadicacion
	)

	SELECT 
		[TipoIdentificacionCotizante],
		[NumeroIdentificacionCotizante],
		[TipoIdentificacionAportante],
		[NumeroIdentificacionAportante],
		[TipoNovedad],
		[FechaInicio],
		[FechaFin],
		[PeriodoRegular],
		@IdTransaccion IdTransaccion
	FROM NNovedadesPR

	UNION ALL 

	SELECT 
		[TipoIdentificacionCotizante],
		[NumeroIdentificacionCotizante],
		[TipoIdentificacionAportante],
		[NumeroIdentificacionAportante],
		[TipoNovedad],
		[FechaInicio],
		[FechaFin],
		[PeriodoRegular],
		@IdTransaccion IdTransaccion
	FROM NNovedadesPA

	DROP TABLE #NPersonas'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos situación primaria novedad');
	COMMIT TRANSACTION;

	
	--print 'BeneficioEmpresaPeriodo CORE > Pila (Stg)'
	INSERT INTO staging.BeneficioEmpresaPeriodo (bepTipoIdentificacion,bepNumeroIdentificacion,bepPeriodoAporte,bepTipoBeneficio,bepBeneficioActivo,bepTarifaEmpleador,bepTransaccion,bepShardName)
	EXEC sp_execute_remote CoreReferenceData
	, N'
	SELECT DISTINCT appPeriodoAporte, appPeriodoAporteYYYYMM, appTipoIdentificacionAportante, appNumeroIdentificacionAportante 
	INTO #Aportes
	FROM pila.Aporte
	WHERE appTipoPlanilla IN (''DEPENDIENTE'', ''MIXTA'')
	AND appTransaccion = @IdTransaccion

	CREATE INDEX #IX_Aportes ON #Aportes (appTipoIdentificacionAportante, appNumeroIdentificacionAportante)

	SELECT DISTINCT perTipoIdentificacion,perNumeroIdentificacion,appPeriodoAporteYYYYMM PeriodoAporte,
	Beneficio.befTipoBeneficio,BeneficioEmpleador.bemBeneficioActivo,
	ISNULL(
		CASE Beneficio.befVigenciaFiscal
			 WHEN 1 THEN (SELECT CAST(ParamTBE.prmValor AS DECIMAL(5,5))*pbePorcentaje FROM PeriodoBeneficio WHERE pbePeriodo = (DATEDIFF(year,  DATEADD(m, DATEDIFF(m, 0, BeneficioEmpleador.bemFechaVinculacion), 0), appPeriodoAporte))+1 AND pbeBeneficio = BeneficioEmpleador.bemBeneficio)
			 WHEN 0 THEN (SELECT CAST(ParamTBE.prmValor AS DECIMAL(5,5))*pbePorcentaje FROM PeriodoBeneficio WHERE pbePeriodo = (DATEDIFF(month, DATEADD(m, DATEDIFF(m, 0, BeneficioEmpleador.bemFechaVinculacion), 0), appPeriodoAporte)/12)+1 AND pbeBeneficio = BeneficioEmpleador.bemBeneficio)
		END, CAST(ParamTBE.prmValor AS DECIMAL(5,5))) AS TarifaEmpleador,	
	 @IdTransaccion AS IdTransaccion
	FROM Persona
	INNER JOIN Empresa ON Persona.perId = Empresa.empPersona
	CROSS JOIN Parametro ParamTBE
	INNER JOIN  Empleador ON Empresa.empId = Empleador.empEmpresa
	INNER JOIN BeneficioEmpleador ON Empleador.empId = BeneficioEmpleador.bemEmpleador AND bemBeneficioActivo = 1
	INNER JOIN Beneficio ON BeneficioEmpleador.bemBeneficio = Beneficio.befId
	INNER JOIN #Aportes AporteEmpresa ON Persona.perTipoIdentificacion = AporteEmpresa.appTipoIdentificacionAportante AND Persona.perNumeroIdentificacion = AporteEmpresa.appNumeroIdentificacionAportante
	WHERE ParamTBE.prmNombre = ''TARIFA_BASE_EMPLEADOR''

	DROP TABLE #Aportes'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	BEGIN TRANSACTION 
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			'@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL'),
			'USP_ExecuteIntegrationResource FIN datos beneficio de empresa');
	COMMIT TRANSACTION;

	-- se realiza el cálculo de días de novedad por periodo
	EXEC USP_CalcularDiasNovedadesBD @IdTransaccion;
	
	--print 'Eliminar Info Planillas Tmp CORE'
	EXEC sp_execute_remote CoreReferenceData
	, N'DELETE FROM pila.Aporte WHERE appTransaccion = @IdTransaccion'
	,N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;

	--print 'Finaliza USP_ExecuteIntegrationResource'
END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecuteIntegrationResource]|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(), '@IdTransaccion=' + CAST(@IdTransaccion AS VARCHAR(20)), @ErrorMessage);

	--print 'Finaliza USP_ExecuteIntegrationResource'
    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;