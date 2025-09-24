/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroPersonas]    Script Date: 2023-07-08 9:38:45 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroPersonas]    Script Date: 13/06/2023 9:58:44 a. m. ******/
--exec 
CREATE OR ALTER   PROCEDURE [dbo].[reporteCuotaMonetariaNumeroPersonas]( @FECHA_INICIAL DATETIME, @FECHA_FINAL DATETIME )
----exec [reporteCuotaMonetariaNumeroPersonas] '2023-06-01','2023-06-30'
AS
BEGIN
SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 
SET NOCOUNT ON

SELECT 
	YEAR(sls.slsFechaDispersion) AS annio,
	MONTH(sls.slsFechaDispersion) AS Mes,
							
	dsaAfiliadoPrincipal,
	dsaBeneficiarioDetalle,
	dsaTipoCuotaSubsidio,
	dsaPeriodoLiquidado,
	dsaId,
	DATEADD(MONTH,-1,CONVERT(VARCHAR(10),DATEADD(dd,-(DAY(sls.slsFechaDispersion)-1),sls.slsFechaDispersion),121) ) AS Fechaperiodoreg,
	dsaValorSubsidioMonetario AS Valor 
	 						  
INTO #REP
FROM 
	DetalleSubsidioAsignado dsa 
	INNER JOIN SolicitudLiquidacionSubsidio sls ON dsa.dsaSolicitudLiquidacionSubsidio = sls.slsid 
	INNER JOIN Solicitud s ON sls.slsSolicitudGlobal = s.solid 
	INNER JOIN CuentaAdministradorSubsidio cuenta with(nolock) ON DSA.dsaCuentaAdministradorSubsidio= cuenta.casId 
	inner join Afiliado on dsaAfiliadoPrincipal = afiId
	inner join Persona perafi on perafi.perId = afiPersona
	--where perafi.perNumeroIdentificacion = '1102834550' and dsa.sls.slsFechaDispersion BETWEEN '01-01-2022' AND '01-31-2022'

	inner join BeneficiarioDetalle bendet on bendet.bedId = dsaBeneficiarioDetalle 
	inner join Beneficiario  ben  on ben.benBeneficiarioDetalle = bendet.bedId and ben.benAfiliado= afiId 
	inner join PersonaDetalle perdetben on perdetben.pedId = bendet.bedPersonaDetalle
	inner join Persona perben on perben.perId = perdetben.pedPersona

	inner join Empleador emplea on emplea.empId = dsaEmpleador --AND empEstadoEmpleador = 'ACTIVO'
	inner join Empresa empres on empres.empId = emplea.empEmpresa
	inner join Persona perempre on perempre.perId = empres.empPersona
	--where perempre.perNumeroIdentificacion = '901303582'	and perafi.perNumeroIdentificacion = '33967621' AND perben.perNumeroIdentificacion = '1056135423'
	inner join Persona adm on adm.perId = dsaAdministradorSubsidio

	LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
	LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor
WHERE 

	CAST ( sls.slsFechaDispersion AS DATE) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	 
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND s.solResultadoProceso = 'DISPERSADA'
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaTipoliquidacionSubsidio NOT IN ('ESPECIFICA_AJUSTE')
	AND cuenta.casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND cuenta.casTipoTransaccionSubsidio = 'ABONO'
			 

CREATE NONCLUSTERED INDEX afil_ben_annio_mes
	ON #REP  (dsaAfiliadoPrincipal, dsaBeneficiarioDetalle,annio,  mes)

/*------------------------------------------*/
-- REGULARES
/*------------------------------------------*/
SELECT 
	YEAR(sls.slsFechaDispersion) AS annio, 
	MONTH(sls.slsFechaDispersion) AS Mes, 
	/*dsaPeriodoLiquidado, 
	dsaAfiliadoPrincipal, 
	COUNT (DISTINCT dsaBeneficiarioDetalle) AS dsaBeneficiarioDetalle*/
	dsaAfiliadoPrincipal, 
	COUNT (DISTINCT dsaBeneficiarioDetalle) as dsaBeneficiarioDetalle,	
	--dsaTipoCuotaSubsidio,	
	dsaPeriodoLiquidado,
	--Fechaperiodoreg,
	'REG' AS TipoCuota
	/*CASE 
		WHEN dsaTipoCuotaSubsidio like 'DISCAPACIDAD%' 
		THEN COUNT(dsaId)*2 
		ELSE COUNT(DISTINCT dsaId) 
	END AS [Número de cuotas]*/

	--CONVERT (decimal(15,2), SUM(Valor) ) as Valor
INTO #REP2
FROM 
	DetalleSubsidioAsignado dsa 
	INNER JOIN CuentaAdministradorSubsidio cas ON casId = dsaCuentaAdministradorSubsidio
	INNER JOIN SolicitudLiquidacionSubsidio sls ON dsa.dsaSolicitudLiquidacionSubsidio = sls.slsid 
	INNER JOIN Solicitud s
		ON sls.slsSolicitudGlobal = s.solid 
	

	inner join Afiliado on dsaAfiliadoPrincipal = afiId
	inner join Persona perafi on perafi.perId = afiPersona
	--where perafi.perNumeroIdentificacion = '1102834550' and dsa.sls.slsFechaDispersion BETWEEN '01-01-2022' AND '01-31-2022'

	INNER join BeneficiarioDetalle bendet on bendet.bedId = dsaBeneficiarioDetalle  
	inner join Beneficiario  ben  on ben.benBeneficiarioDetalle = bendet.bedId and ben.benAfiliado= afiId 
	INNER join PersonaDetalle perdetben on perdetben.pedId = bendet.bedPersonaDetalle
	INNER join Persona perben on perben.perId = perdetben.pedPersona

	INNER join Empleador emplea on emplea.empId = dsaEmpleador --AND empEstadoEmpleador = 'ACTIVO'
	INNER join Empresa empres on empres.empId = emplea.empEmpresa
	INNER join Persona perempre on perempre.perId = empres.empPersona
	--where perempre.perNumeroIdentificacion = '901303582'	and perafi.perNumeroIdentificacion = '33967621' AND perben.perNumeroIdentificacion = '1056135423'
	INNER join Persona adm on adm.perId = dsaAdministradorSubsidio

	LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
	LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor
WHERE 
	CAST ( sls.slsFechaDispersion AS DATE) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL		 
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND s.solResultadoProceso = 'DISPERSADA'
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaTipoliquidacionSubsidio NOT IN ('ESPECIFICA_AJUSTE')
	AND cas.casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND cas.casTipoTransaccionSubsidio = 'ABONO'
	/*dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND sls.slsFechaDispersion BETWEEN  @FECHA_INICIAL AND @FECHA_FINAL*/
	AND dsaPeriodoLiquidado = DATEADD(MONTH,-1,CONVERT(VARCHAR(10),DATEADD(dd,-(DAY(@FECHA_INICIAL)-1),@FECHA_INICIAL),121) )
	/*AND dsaTipoliquidacionSubsidio IN ('ESPECIFICA_RECONOCIMIENTO','MASIVA', 'ESPECIFICA_FALLECIMIENTO')*/
GROUP BY 
	YEAR(sls.slsFechaDispersion), 
	MONTH(sls.slsFechaDispersion), 
	dsaAfiliadoPrincipal,
	dsaPeriodoLiquidado,
	dsaBeneficiarioDetalle,	
	dsaTipoCuotaSubsidio 
	 
	--PRINT DATEADD(MONTH,-1,CONVERT(VARCHAR(10),DATEADD(dd,-(DAY('2021-01-01')-1),'2021-01-01'),121) )

	--  SELECT 'prueba',* FROM #REP2
	--DROP TABLE #REP2
	--dsaId,
	--Fechaperiodoreg
/*SELECT 
	annio, 
	Mes, 
	dsaAfiliadoPrincipal, 
	dsaBeneficiarioDetalle,	
	dsaTipoCuotaSubsidio,	
	dsaPeriodoLiquidado,
	Fechaperiodoreg,
	'REG' AS TipoCuota,
	CASE 
		WHEN dsaTipoCuotaSubsidio like 'DISCAPACIDAD%' 
		THEN COUNT(dsaId)*2 
		ELSE COUNT(DISTINCT dsaId) 
	END AS [Número de cuotas],

	CONVERT (decimal(15,2), SUM(Valor) ) as Valor
INTO #REP2
FROM #REP 
WHERE Fechaperiodoreg = dsaPeriodoLiquidado
GROUP BY 
	annio, 
	Mes, 
	dsaAfiliadoPrincipal, 
	dsaBeneficiarioDetalle,	
	dsaTipoCuotaSubsidio,	
	dsaPeriodoLiquidado,
	--dsaId,
	Fechaperiodoreg*/

/*------------------------------------------*/
-- RETROACTIVO
/*------------------------------------------*/
SELECT 
	annio, 
	Mes, 
	dsaAfiliadoPrincipal, 
	dsaBeneficiarioDetalle,	
	dsaTipoCuotaSubsidio,	
	dsaPeriodoLiquidado,
	--dsaId,
	Fechaperiodoreg,
	'RET' AS TipoCuota,

	CASE 
		WHEN dsaTipoCuotaSubsidio like 'DISCAPACIDAD%' 
		THEN COUNT(dsaId)*2 
		ELSE COUNT(DISTINCT dsaId) 
	END AS [Número de cuotas],

	CONVERT (decimal(15,2), SUM(Valor) ) as Valor
INTO #REP3
FROM #REP 
WHERE Fechaperiodoreg <> dsaPeriodoLiquidado
GROUP BY 
	annio, 
	Mes, 
	dsaAfiliadoPrincipal, 
	dsaBeneficiarioDetalle,	
	dsaTipoCuotaSubsidio,	
	dsaPeriodoLiquidado,
	dsaId,
	Fechaperiodoreg

/*------------------------------------------*/
-- CONTEO
/*------------------------------------------*/
	SELECT
		----------  Codigo Departamento DIVIPOLA  ----------
		dep.depCodigo AS [COD_DEPARTAMENTO],--[Departamento],

		----------  Total de beneficiarios en el periodo consultado  ---------
		SUM( dsaBeneficiarioDetalle ) AS [NUM_PERSONAS_MES], --[Número de personas mes],

		----------  Total de beneficiarios en el periodo anterior al consultado ----------
		0 AS [NUM_PERSONAS_RETROACTIVO]

	INTO #REP4
	FROM 
		#REP2  
		LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
		LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor
	GROUP BY 
		dep.depCodigo

------------
UNION ALL
------------

	SELECT 
		----------  Codigo Departamento DIVIPOLA  ----------
		dep.depCodigo AS [COD_DEPARTAMENTO],--[Departamento],

		----------  Total de beneficiarios en el periodo consultado  ---------
		0 AS [NUM_PERSONAS_MES], --[Número de personas mes],

		----------  Total de beneficiarios en el periodo anterior al consultado ----------
		COUNT( TipoCuota ) AS [NUM_PERSONAS_RETROACTIVO]
	FROM 
		#REP3
		LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
		LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor

	GROUP BY 
		dep.depCodigo

/*------------------------------------------*/
-- UNIFICACION PARA ENTREGA DE RESULTADO
/*------------------------------------------*/
SELECT 
	----------  Codigo Departamento DIVIPOLA  ----------
	[COD_DEPARTAMENTO] AS [COD_DEPARTAMENTO],--[Departamento],

	----------  Total de beneficiarios en el periodo consultado  ---------
	SUM([NUM_PERSONAS_MES]) AS [NUM_PERSONAS_MES], --[Número de personas mes],

	----------  Total de beneficiarios en el periodo anterior al consultado ----------
	SUM([NUM_PERSONAS_RETROACTIVO]) AS [NUM_PERSONAS_RETROACTIVO]
FROM 
	#REP4
GROUP BY 
	[COD_DEPARTAMENTO]

END