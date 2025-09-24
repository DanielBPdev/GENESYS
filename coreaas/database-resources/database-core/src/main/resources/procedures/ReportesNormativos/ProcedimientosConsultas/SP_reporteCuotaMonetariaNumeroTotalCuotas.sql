/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroTotalCuotas]    Script Date: 2023-11-24 9:21:11 AM ******/
 
/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroTotalCuotas]    Script Date: 2023-08-03 11:56:52 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroTotalCuotas]    Script Date: 2023-07-08 9:39:02 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteCuotaMonetariaNumeroTotalCuotas]    Script Date: 13/06/2023 9:58:56 a. m. ******/

-- =============================================
-- Author:      Miguel Angel Perilla
-- Last Update Date: 16 Junio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 24.
-- Reporte 24
---exec reporteCuotaMonetariaNumeroTotalCuotas '2023-01-01','2023-11-24'
-- =============================================

CREATE OR ALTER     PROCEDURE [dbo].[reporteCuotaMonetariaNumeroTotalCuotas]( @FECHA_INICIAL DATE, @FECHA_FINAL DATE )
--SET @FECHA_INICIAL = '2023-06-01' 
--SET @FECHA_FINAL = '2023-06-30'
AS
BEGIN
--SET ANSI_WARNINGS OFF
SET NOCOUNT ON
SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 
--/---------------------------------------**********---------------------------------------\--
--                REPORTE DE CUOTA MONETARIA - NUMERO TOTAL DE CUOTAS  -  N� 24.
--\---------------------------------------**********---------------------------------------/--
--------------
SELECT 
--------------
	----------  Codigo Departamento  ----------
	DEP.depCodigo AS [COD_DEPARTAMENTO],--[Departamento],

	----------  Periodo Liquidado  ----------
	CASE WHEN YEAR(DSA.dsaPeriodoLiquidado) >YEAR(GETDATE()) 
			THEN YEAR(GETDATE()) ELSE YEAR(DSA.dsaPeriodoLiquidado) END AS [ANIO_VIGENCIA_APORTES],--[A�o],

	----------  Valor  ----------
	SUM( CONVERT(numeric(10,0) , DSA.dsaValorSubsidioMonetario) ) AS [VAL_CUOTAS_PAGADAS],--[Valor total de las cuotas pagadas],

	---------- Valor cuota monetaria para cada vigencia ----------
	(
		SELECT TOP 1 CONVERT(numeric(10,0), parametroSubsidio.pcsValorCuotaAnualBase )
		FROM ParametrizacionCondicionesSubsidio as parametroSubsidio
		WHERE parametroSubsidio.pcsAnioVigenciaParametrizacion 
		   IN ( CASE WHEN YEAR( DSA.dsaPeriodoLiquidado ) >YEAR(GETDATE()) THEN   YEAR(GETDATE()) ELSE  YEAR( DSA.dsaPeriodoLiquidado ) END )
		ORDER BY parametroSubsidio.pcsId DESC
	)  AS [VAL_CUOTA_VIGENCIA]--[Valor cuota monetaria para cada vigencia]
	--,PEREMP.perNumeroIdentificacion AS EMPLEADOR
	--, PERAFI.perNumeroIdentificacion AS AFILIADO 
	--,PERBEN.perNumeroIdentificacion AS BENEFICIARIO
	INTO #REPORTE
----------
FROM 
----------
	DetalleSubsidioAsignado DSA with(nolock)
	INNER JOIN CuentaAdministradorSubsidio cuenta with(nolock) ON DSA.dsaCuentaAdministradorSubsidio= cuenta.casId 
	INNER JOIN SolicitudLiquidacionSubsidio SLS ON DSA.dsaSolicitudLiquidacionSubsidio = SLS.slsId
	INNER JOIN Solicitud SOL ON SOL.solId = SLS.slsSolicitudGlobal
 

	INNER JOIN Afiliado AFI ON DSA.dsaAfiliadoPrincipal = AFI.afiId
	INNER JOIN Persona PERAFI ON PERAFI.perId = AFI.afiPersona

	INNER JOIN BeneficiarioDetalle BENDET on BENDET.bedId = DSA.dsaBeneficiarioDetalle 
	INNER JOIN Beneficiario BEN  
	        ON BEN.benBeneficiarioDetalle = BENDET.bedId 
		   AND BEN.benAfiliado = AFI.afiId
	INNER JOIN PersonaDetalle PERDETBEN on PERDETBEN.pedId = BENDET.bedPersonaDetalle
	INNER JOIN Persona PERBEN on PERBEN.perId = PERDETBEN.pedPersona

	INNER JOIN Empleador EMPLEA on emplea.empId = dsaEmpleador --AND empEstadoEmpleador = 'ACTIVO'
	INNER JOIN Empresa EMP on EMP.empId = EMPLEA.empEmpresa
	INNER JOIN Persona PEREMP on PEREMP.perId = EMP.empPersona
	
	INNER JOIN Persona ADM on ADM.perId = DSA.dsaAdministradorSubsidio

	LEFT JOIN Parametro PAR ON PAR.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
	LEFT JOIN Departamento DEP on DEP.depCodigo = PAR.prmValor
	
WHERE 
	CAST ( SLS.slsFechaDispersion AS DATE) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND sol.solResultadoProceso = 'DISPERSADA'
	AND DSA.dsaEstado = 'DERECHO_ASIGNADO'
	AND DSA.dsaTipoliquidacionSubsidio NOT IN ('ESPECIFICA_AJUSTE')
	AND cuenta.casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND cuenta.casTipoTransaccionSubsidio = 'ABONO'
		---AND PERBEN.perNumeroIdentificacion ='1056128117'	 
GROUP BY 
	DEP.depCodigo,
	YEAR(DSA.dsaPeriodoLiquidado)
	--		,PEREMP.perNumeroIdentificacion  
	--, PERAFI.perNumeroIdentificacion  
	--,PERBEN.perNumeroIdentificacion  
ORDER BY 
	DEP.depCodigo,
	YEAR(DSA.dsaPeriodoLiquidado)


	SELECT COD_DEPARTAMENTO,	ANIO_VIGENCIA_APORTES,	
	       SUM(VAL_CUOTAS_PAGADAS) AS VAL_CUOTAS_PAGADAS, 	 VAL_CUOTA_VIGENCIA   
	FROM  #REPORTE
	GROUP BY COD_DEPARTAMENTO,	ANIO_VIGENCIA_APORTES, VAL_CUOTA_VIGENCIA

END