-- =============================================
-- Author:		Diego Suesca
-- Create date: 2021/03/18
-- Description: Procedimiento que calcula la categoría actual de los afiliados
-- NOTA_ SE DEBE CREAR TABLA #CategoriaAfiliadoCalculada ANTES DE LLAMAR ESTE SP Y INSERTAR LOS perId EN EL CAMPO persona DE LAS PERSONAS A CALCULAR CATEGORIA
-- =============================================

CREATE PROCEDURE USP_GET_CategoriaAfiliado
AS


		CREATE TABLE #perId (perId BIGINT primary key);

	INSERT #perId (perId)
	SELECT persona FROM #CategoriaAfiliadoCalculada;

	TRUNCATE TABLE #CategoriaAfiliadoCalculada;

	SELECT roa.roaAfiliado AS afiliado, 
			afi.afiPersona AS persona, 
			roa.roaTipoAfiliado AS tipoCotizante, 
			SUM(roa.roaValorSalarioMesadaIngresos) AS salario, 
			'ACTIVO' AS estadoAfiliacion, 
			MAX(afi.afifechaFinServicioSinAfiliacion) AS fechaFinServicioSinAfiliacion,
			MAX(roa.roaFechaAfiliacion) maxFechaAfiliacion
	INTO #cteSalarioTipoCotizanteActivos
	FROM dbo.RolAfiliado roa
	JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
	JOIN #perId p 
					ON p.perId = afi.afiPersona	
	WHERE roa.roaEstadoAfiliado = 'ACTIVO'
	GROUP BY roa.roaAfiliado, afi.afiPersona, roa.roaTipoAfiliado

	SELECT roa.roaAfiliado AS afiliado, afi.afiPersona AS persona, emp.empEmpresa as empresa, roa.roaTipoAfiliado AS tipoCotizante, roa.roaValorSalarioMesadaIngresos AS salario, 'ACTIVO' AS estadoAfiliacion, afi.afifechaFinServicioSinAfiliacion AS fechaFinServicioSinAfiliacion, roa.roaFechaAfiliacion AS maxFechaAfiliacion
	INTO #cteSalarioTipoCotizanteActivosDiscriminados
	FROM dbo.RolAfiliado roa
	INNER JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
	INNER JOIN dbo.Empleador emp ON roa.roaEmpleador = emp.empId	
	JOIN #perId p 
					ON p.perId = afi.afiPersona	
	WHERE roa.roaEstadoAfiliado = 'ACTIVO'

    
	SELECT roa.roaAfiliado AS afiliado, afi.afiPersona AS persona, roa.roaTipoAfiliado AS tipoCotizante, SUM(roa.roaValorSalarioMesadaIngresos) AS salario, 'INACTIVO' AS estadoAfiliacion, MAX(afi.afifechaFinServicioSinAfiliacion) AS fechaFinServicioSinAfiliacion,MAX(roa.roaFechaAfiliacion) maxFechaAfiliacion
	into #cteSalarioTipoCotizanteInactivos
	FROM dbo.RolAfiliado roa	
	INNER JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId	
	JOIN #perId p 
					ON p.perId = afi.afiPersona		
	LEFT JOIN (
				SELECT roa1.roaAfiliado, roa1.roaTipoAfiliado
				FROM dbo.RolAfiliado roa1 
				WHERE roa1.roaEstadoAfiliado = 'ACTIVO'
			  ) b ON b.roaAfiliado = roa.roaAfiliado
				 AND b.roaTipoAfiliado = roa.roaTipoAfiliado 
	WHERE ISNULL(roa.roaEstadoAfiliado,'INACTIVO') = 'INACTIVO'		
	  AND b.roaAfiliado IS NULL
	GROUP BY roa.roaAfiliado, afi.afiPersona, roa.roaTipoAfiliado 
	

	
	SELECT * 
	INTO #cteSalarioTipoCotizanteAll
	FROM (
			SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion, maxFechaAfiliacion
			FROM #cteSalarioTipoCotizanteActivos
			UNION ALL
			SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion, maxFechaAfiliacion
			FROM #cteSalarioTipoCotizanteInactivos
		) a

	SELECT empresa, tipoCotizante, persona, periodoAporte, SUM(salario) AS salarioMaxEmpleador,
			CAST(MAX(AporteEmpleadorNoAfiliado) AS BIT) AS aporteEmpleadorNoAfiliado
			--,MAX(fechaRecaudo) AS fechaRecaudo
	INTO #cteSalarioTipoCotizanteAportes
	FROM (
		SELECT apg.apgEmpresa empresa,
				apd.apdTipoCotizante AS tipoCotizante,
				apg.apgRegistroGeneral,
				apd.apdPersona AS persona,
				apg.apgPeriodoAporte AS periodoAporte,
				MAX(apd.apdSalarioBasico) salario,
				--MAX(apg.apgFechaRecaudo) fechaRecaudo,
			CASE WHEN ISNULL(empl.empEstadoEmpleador,'INACTIVO') = 'INACTIVO' THEN 1 ELSE 0 END AS AporteEmpleadorNoAfiliado
		FROM dbo.AporteDetallado apd
		INNER JOIN dbo.AporteGeneral apg ON (apg.apgId = apd.apdAporteGeneral)
		LEFT JOIN dbo.Empleador empl ON apg.apgEmpresa = empl.empEmpresa
		JOIN #perId p 
					ON p.perId = apd.apdPersona		
		WHERE apd.apdEstadoRegistroAporteArchivo = 'OK'
			AND apd.apdEstadoAporteRecaudo = 'VIGENTE'
			AND apd.apdMarcaPeriodo IN ('PERIODO_REGULAR','PERIODO_FUTURO','PERIODO_RETROACTIVO')
			AND CAST(apg.apgPeriodoAporte+'-01' AS DATE) <= 
			--dbo.GetPeriodoRegular(apd.apdTipoCotizante,CAST(dbo.GetLocalDate() AS DATE))
			-- se cambia el código de la función a CASE por rendimiento
			CASE WHEN apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE' OR apd.apdTipoCotizante = 'PENSIONADO' 
				THEN DATEFROMPARTS(YEAR(dbo.GetLocalDate()),MONTH(dbo.GetLocalDate()),1)
				--DATEFROMPARTS(YEAR(DATEADD(MONTH,-1,dbo.GetLocalDate())),MONTH(DATEADD(MONTH,-1,dbo.GetLocalDate())),1)
			ELSE
				DATEFROMPARTS(YEAR(DATEADD(MONTH,-1,dbo.GetLocalDate())),MONTH(DATEADD(MONTH,-1,dbo.GetLocalDate())),1)
			END
		GROUP BY apg.apgEmpresa, apd.apdTipoCotizante, apg.apgRegistroGeneral, apd.apdPersona, apg.apgPeriodoAporte,
				 CASE WHEN ISNULL(empl.empEstadoEmpleador,'INACTIVO') = 'INACTIVO' THEN 1 ELSE 0 END
	) as p
	GROUP BY empresa, persona, tipoCotizante, periodoAporte
    
  
	
	SELECT  persona, tipoCotizante, MAX(periodoAporte) periodoAporte
	INTO #cteMAXPeriodoAportePersona
	FROM #cteSalarioTipoCotizanteAportes
	GROUP BY persona, tipoCotizante, aporteEmpleadorNoAfiliado
		
	declare @aporteEmpleadorNoAfiliadosegunaporte int;
	set @aporteEmpleadorNoAfiliadosegunaporte=0;
	begin
	if(select COUNT(*) from #cteSalarioTipoCotizanteAportes where aporteEmpleadorNoAfiliado >=1)>0
	set @aporteEmpleadorNoAfiliadosegunaporte=1;
	end	
	SELECT afi.afiId AS afiliado,
			afi.afiPersona AS persona,
			sal.tipoCotizante,
			SUM(sal.salarioMaxEmpleador) + (CASE WHEN b.salario IS NOT NULL THEN b.salario ELSE 0 END) AS salario,
			cte.estadoAfiliacion,
			cte.fechaFinServicioSinAfiliacion,
			sal.aporteEmpleadorNoAfiliado,
			DATEADD(MONTH,1, CAST(max(maxP.periodoAporte)+'-01' AS DATE)) maxFechaAporte,
			cte.maxFechaAfiliacion,
			cte.salario salarioAfiliacion
	INTO #cteSalarioTipoCotizanteAux
	FROM #cteMAXPeriodoAportePersona maxP
	INNER JOIN #cteSalarioTipoCotizanteAportes sal ON maxP.persona = sal.persona
												AND maxP.tipoCotizante = sal.tipoCotizante
												AND maxP.periodoAporte = sal.periodoAporte
--AND sal.aporteEmpleadorNoAfiliado= @aporteEmpleadorNoAfiliadosegunaporte
	INNER JOIN dbo.Afiliado afi ON sal.persona = afi.afiPersona
	INNER JOIN #cteSalarioTipoCotizanteAll cte ON maxP.persona = cte.persona
											AND maxP.tipoCotizante = cte.tipocotizante
	LEFT JOIN (
				SELECT 
					stc.persona as persona,
					stc.empresa as empresa,
					SUM(stc.salario) AS salario
				FROM #cteSalarioTipoCotizanteActivosDiscriminados stc
				LEFT JOIN #cteSalarioTipoCotizanteAportes sta ON (stc.persona = sta.persona AND stc.empresa = sta.empresa)
				where sta.persona is null and sta.empresa is null
				group by stc.persona, stc.empresa
			  ) as b on (b.persona = afi.afiPersona)
	GROUP BY afi.afiId, afi.afiPersona, sal.tipoCotizante, estadoAfiliacion, fechaFinServicioSinAfiliacion,
	sal.aporteEmpleadorNoAfiliado,cte.maxFechaAfiliacion,cte.salario, b.salario

	 ;with prueba as (
	 select *, ROW_NUMBER() over (order by maxFechaAporte desc) as id
	 from #cteSalarioTipoCotizanteAux)
	 delete from prueba where id > 1

	SELECT *
	INTO #cteSalarioTipoCotizante
	FROM
	(
		SELECT afiliado,persona,tipoCotizante,
		CASE WHEN maxFechaAporte = DATEFROMPARTS(YEAR(maxFechaAfiliacion),MONTH(maxFechaAfiliacion),1) THEN salario
			WHEN maxFechaAporte < maxFechaAfiliacion THEN salarioAfiliacion
			WHEN maxFechaAporte > maxFechaAfiliacion THEN salario
		END salario,
		estadoAfiliacion, fechaFinServicioSinAfiliacion, aporteEmpleadorNoAfiliado
		FROM #cteSalarioTipoCotizanteAux cte

		UNION ALL 
		SELECT afiliado, cte.persona, cte.tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion,
		CAST(0 AS BIT) AS aporteEmpleadorNoAfiliado
		FROM #cteSalarioTipoCotizanteAll cte
		LEFT JOIN (SELECT persona, tipoCotizante
					 FROM #cteMAXPeriodoAportePersona					
				  ) c ON c.persona = cte.persona
					 AND c.tipoCotizante = cte.tipoCotizante
		WHERE c.persona IS NULL
	) a

	INSERT #CategoriaAfiliadoCalculada 
	(afiliado ,persona ,tipoIdentificacion,numeroIdentificacion,tipoCotizante,clasificacion,salario,estadoAfiliacion,fechaFinServicioSinAfiliacion,aporteEmpleadorNoAfiliado,categoria)
	SELECT t.afiliado,
	t.persona,
	per.perTipoIdentificacion AS tipoIdentificacion,
	per.perNumeroIdentificacion AS numeroIdentificacion,
	t.tipoCotizante,
	sol.solClasificacion AS clasificacion,
	t.salario,
	t.estadoAfiliacion,
	t.fechaFinServicioSinAfiliacion,
	t.aporteEmpleadorNoAfiliado,
	dbo.GetCategoria(t.tipoCotizante, sol.solClasificacion, t.salario, t.estadoAfiliacion,
						t.fechaFinServicioSinAfiliacion,salarioMinimo.prmValor) AS categoria
	FROM (SELECT prmValor FROM Parametro WHERE prmNombre = 'SMMLV') salarioMinimo,
	#cteSalarioTipoCotizante t
	INNER JOIN (	SELECT MAX(sapSolicitudGlobal) sapSolicitudGlobal, roaAfiliado, roaTipoAfiliado
					FROM SolicitudAfiliacionPersona sap
					INNER JOIN RolAfiliado roa ON sap.sapRolAfiliado = roa.roaId
					WHERE sapEstadoSolicitud <> 'PRE_RADICADA'
					GROUP BY roaAfiliado, roaTipoAfiliado) sap ON t.afiliado = sap.roaAfiliado
																AND t.tipoCotizante = sap.roaTipoAfiliado
	INNER JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
	INNER JOIN Persona per ON t.persona = per.perId;