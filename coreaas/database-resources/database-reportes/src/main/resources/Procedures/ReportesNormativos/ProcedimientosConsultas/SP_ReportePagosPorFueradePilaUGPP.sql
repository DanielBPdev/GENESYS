/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 2023-12-05 10:50:18 AM ******/
 
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 2023-12-05 7:30:15 AM ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 2023-11-27 9:06:07 AM ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 2023-07-18 10:35:29 AM ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 2023-07-13 8:43:04 AM ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 02/05/2023 11:51:54 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 02/05/2023 10:51:05 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 12/04/2023 11:00:51 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 20/12/2022 9:48:56 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 14/12/2022 9:15:20 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 02/12/2022 11:28:52 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 23/11/2022 10:37:56 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 30/09/2022 12:48:30 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reportePagosPorFueraDePilaUGPP]    Script Date: 01/08/2022 10:14:15 a. m. ******/
 
-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 06 Julio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 19.
-- Reporte 19
---EXEC reportePagosPorFueraDePilaUGPP '2023-10-01','2023-10-31'
------update 20220801
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[reportePagosPorFueraDePilaUGPP] (
	@FECHA_INICIAL DATE,
	@FECHA_FINAL DATE
)

AS 
BEGIN
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

--/---------------------------------------**********---------------------------------------\--
--                          REPORTE DE PAGOS POR FUERA DE PILA  -  N° 19.
--\---------------------------------------**********---------------------------------------/--
-----------------------------------------
-----------------------------------------
 
SELECT  
----------  Tipo Documento  ---------
	CASE PE.perTipoIdentificacion 
		WHEN 'NIT' THEN 'NI' 
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
		WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'PASAPORTE' THEN 'PA'
		WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
		WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		WHEN 'NUIP' THEN 'NU'
		WHEN 'SALVOCONDUCTO' THEN 'SC'
	END AS [Tipo_documento_aportante],--[TIPO_DOCUMENTO],
	
	---------  Numero de Documento. ---------
	SUBSTRING (PE.PERNUMEROIDENTIFICACION,1,15) AS [Número_Documento_aportante],--[NUMERO_DE_IDENTIFICACION],

	---------  Razon Social. ---------
	SUBSTRING (PE.PerRazonSocial,1,100) as [Razón_social],--[RAZON_SOCIAL],

	---------  Departamento. ---------
	d.Depcodigo as [Id_departamento],--[COD_DEPARTAMENTO],

	---------  Municipio. ---------
	M.Muncodigo as [ld_municipio],--[COD_MUNICIPIO],

	--------- Periodo pago --------
	apgperiodoaporte AS [Periodo_pago],--[PERIODO_PAGO],

	--------- Tipo documento cotizante ---------
	CASE PC.perTipoIdentificacion 
		WHEN 'NIT' THEN 'NI' 
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
		WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'PASAPORTE' THEN 'PA'
		WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
		WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		WHEN 'NUIP' THEN 'NU'
		WHEN 'SALVOCONDUCTO' THEN 'SC'
	END AS [Tipo_documento_cotizante], --[TIPO_DOCUMENTO_COT],

	---------  Número documento cotizante ---------
	SUBSTRING (PC.PERNUMEROIDENTIFICACION,1,15) AS [Número_Documento_cotizante], --[NUMERO_DOCUMENTO_COT],

	--------- Nombre cotizante ---------
	SUBSTRING (PC.PerRazonSocial,1,100) as [Nombre_cotizante],--[NOMBRE_COT],

	--------- Fecha de pago ---------
 
	format(apgFechaProcesamiento, 'yyyy-MM-dd') AS [Fecha_pago], --[FECHA_DE_PAGO],---glpi 74036 20231205

	--------- Concepto de pago ---------
	--11 AS [Concepto_pago], --[CONCEPTO_DE_PAGO],
	CASE WHEN apgOrigenAporte = 'TRASLADO_DESDE_OTRAS_CAJAS' THEN 11
	WHEN apgOrigenAporte = 'PAGOS_MORA_NO_PAGO_PILA' THEN 2
	WHEN apgOrigenAporte = 'NOVEDADES' THEN 3
	WHEN apgOrigenAporte = 'PAGOS_REGIMIENES_ESPECIALES' THEN 4
	WHEN apgOrigenAporte = 'PAGOS_REGIMIENES_EXCEPCION' THEN  5
	WHEN apgOrigenAporte = 'PENSIONES_VOLUNTARIAS' THEN  6
	WHEN apgOrigenAporte = 'ACUERDO_DE_PAGO' THEN 	7
	WHEN apgOrigenAporte = 'APORTE_ADICIONAL' THEN 	8
	WHEN apgOrigenAporte = 'APORTE_SOLIDARIO' THEN 	9
	WHEN apgOrigenAporte = 'PAGOS_PENSIONADOS_CCF' THEN 10
	WHEN apgOrigenAporte = 'SIN_INFORMACION_PERIODO' THEN 12
	WHEN apgOrigenAporte = 'CALCULO_ACTUARIAL' THEN  1
	ELSE 12 END AS [Concepto_pago],


	--------- IBC ---------
	REPLACE (  (isnull (aporteDetalle.apdValorIBC,0)),'.00000','') AS IBC,

	--------- ING - Ingreso ---------  
	CASE 
		WHEN regDetalle.redNovIngreso IS NOT NULL
		THEN '3'
		ELSE NULL
	END AS [ING - Ingreso], --[ING],

	--------- RET - Retiro ---------
	CASE 
		WHEN regDetalle.redNovRetiro IS NOT NULL
		THEN '3' 
		ELSE NULL
	END AS [RET - Retiro],

	--------- TDE - Traslado desde otra EPS o EOC ---------
	NULL AS [TDE - Traslado desde otra EPS o EOC],--[TDE],

	--------- TAE - Tralado a otra EPS o EOC ---------
	NULL AS [TAE - Traslado a otra EPS o EOC], --[TAE],

	--------- VSP - Variación permanente de salario ---------
	CASE 
		WHEN regDetalle.redNovVSP IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [VSP - Variación permanente de salario], --[VSP],

	--------- VST: Variación transitoria del salario ---------
	CASE
		WHEN regDetalle.redNovVST IS NOT NULL
		THEN '1' 
		ELSE NULL
	END AS [VST: Variación transitoria del salario], --[VST],

	--------- SLN: Suspensión temporal del contrato de trabajo o licencia no remunerada o comisión de servicios ---------
	CASE 
		WHEN regDetalle.redNovSLN IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [SLN: Suspensión temporal del contrato de trabajo o licencia no remunerada o comisión de servicios],--[SLN],

	--------- IGE: Incapacidad Temporal por Enfermedad General ---------
	CASE 
		WHEN regDetalle.redNovIGE IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [IGE: Incapacidad Temporal por Enfermedad General.], --[IGE],

	--------- LMA: Licencia de Maternidad o de Paternidad ---------
	CASE 
		WHEN regDetalle.redNovLMA IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [LMA: Licencia de Maternidad o de paternidad], --[LMA],

	--------- VAC - LR: Vacaciones, Licencia Remunerada ---------
	CASE
		WHEN regDetalle.redFechaInicioVACLR IS not NULL
		THEN '1'
		ELSE NULL
	END AS [VAC - LR: Vacaciones, Licencia Remunerada], --[VAC_LR],

	--------- Correcciones ---------
	 CASE
		WHEN aporteDetalle.apdMarcaPeriodo = 'PERIODO_REGULAR'
		THEN '1'
		ELSE NULL
	END AS [Correcciones], --[CORRECCIONES],

	--------- Exonerado_L1607_2012 ---------
	'2' AS [Exonerado_L1607_2012], --[EXONERADO_L1607_2012],

	--------- Días_cotizados ---------
	CAST( aporteDetalle.APDDIASCOTIZADOS AS BIGINT ) AS [Días_cotizados], --[DIAS_COTIZADOS],

	--------- Días_mora --------- 
	'' AS [Días_mora], --[DIAS_MORA],

	--------- Valor_pagado --------- 
	--CASE WHEN redOUTRegistroActual = 0 AND redAporteObligatorio<>apdAporteObligatorio
	--THEN 
	REPLACE ( (ISNULL(ISNULL (redAporteObligatorio, apdAporteObligatorio), apgValTotalApoObligatorio)),'.00000','')-----CORRECCIÓN DE VALOR 
	--ELSE REPLACE ( (isnull (apdAporteObligatorio,apgValTotalApoObligatorio)),'.00000','') END
	 AS [Valor_pagado], --[VALOR_PAGADO],

	--------- Valor_UPC_Adicional --------- 
	NULL AS [Valor_UPC_Adicional],

	--------- Valor_intereses --------- 
	ROUND(REPLACE (  (isnull (apdValorIntMora,0)),'.00000',''),0) AS [Valor_intereses] --[VALOR_INTERESES]
  
---SELECT *
FROM 
	 aportegeneral2 aportGeneral 	
	 LEFT JOIN AporteDetallado2 aporteDetalle with(nolock) on apdAporteGeneral = apgId
	 LEFT JOIN RegistroDetallado2 regDetalle with(nolock) on regDetalle.redId = aporteDetalle.apdRegistroDetallado---CAMBIO PARA INCLUIR LOS SIN DETALLES 2023-07-18 PENDIENTEPORQUE NO SE PUEDE ENVIAR SINDETALLE glpi 740
	INNER JOIN empresa with(nolock) on empid=apgempresa
	 LEFT JOIN Empleador EM with(nolock) on EMPRESA.empId=EM.empEmpresa 
	INNER JOIN persona pe with(nolock) on pe.perid=emppersona
	 LEFT JOIN ubicacion U with(nolock) on U.ubiId=PE.Perubicacionprincipal

	INNER JOIN 
		       UbicacionEmpresa relacionUbiEmpresa with(nolock) ON ubeEmpresa = empresa.empId 
		   AND ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
	INNER JOIN Ubicacion ubicacionEmpresa ON ubicacionEmpresa.ubiId = relacionUbiEmpresa.ubeUbicacion
	
	LEFT JOIN municipio M with(nolock) on M.MunId = ubicacionEmpresa.Ubimunicipio
	LEFT JOIN departamento D with(nolock) on D.Depid = M.mundepartamento

	LEFT JOIN solicitudaporte sda with(nolock) on sda.soaRegistroGeneral=apgRegistroGeneral
		  AND sda.soaEstadoSolicitud IN ('CERRADA')
	LEFT JOIN solicitud s with(nolock) on s.solid=sda.soaSolicitudGlobal
		  AND s.solresultadoproceso NOT IN ('CANCELADA','DESISTIDA')
	--LEFT JOIN AporteDetallado AD ON AD.apdAporteGeneral=apgId
	LEFT JOIN Persona PC with(nolock) ON PC.perId=aporteDetalle.apdPersona
	
WHERE apgEstadoAportante IN ( 'ACTIVO','NO_FORMALIZADO_RETIRADO_CON_APORTES','INACTIVO')  AND 
	apgModalidadRecaudoAporte = 'MANUAL'  AND
	CONVERT(DATE,aportGeneral.apgFechaProcesamiento) >= @FECHA_INICIAL AND 
	CONVERT(DATE,aportGeneral.apgFechaProcesamiento) <= @FECHA_FINAL  
	AND isnull(apgOrigenAporte,'')  <> ('CORRECCION_APORTE')
	AND apgValTotalApoObligatorio > 0 -----CAMBIO OLGA VEGA 20220728
    AND apdAporteObligatorio>0
 

UNION ALL


SELECT DISTINCT
	----------  Tipo Documento  ---------
	CASE PE.perTipoIdentificacion 
		WHEN 'NIT' THEN 'NI' 
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
		WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'PASAPORTE' THEN 'PA'
		WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
		WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		WHEN 'NUIP' THEN 'NU'
		WHEN 'SALVOCONDUCTO' THEN 'SC'
	END AS [Tipo documento aportante],
	
	---------  Numero de Documento. ---------
	SUBSTRING (PE.PERNUMEROIDENTIFICACION,1,15) AS [Número documento aportante],

	---------  Razon Social. ---------
	SUBSTRING (PE.PerRazonSocial,1,100) as [Razón social],

	---------  Departamento. ---------
	d.Depcodigo as [Id departamento],

	---------  Municipio. ---------
	M.Muncodigo as [ID municipio],

	--------- Periodo pago --------
	apgperiodoaporte AS [Periodo pago],

	--------- Tipo documento cotizante ---------
	CASE PC.perTipoIdentificacion 
		WHEN 'NIT' THEN 'NI' 
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
		WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'PASAPORTE' THEN 'PA'
		WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
		WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		WHEN 'NUIP' THEN 'NU'
	END AS [Tipo documento cotizante],

	---------  Número documento cotizante ---------
	SUBSTRING (PC.PERNUMEROIDENTIFICACION,1,15) AS [Número documento cotizante],

	--------- Nombre cotizante ---------
	SUBSTRING (PC.PerRazonSocial,1,100) as NOMBRE_COTIZANTE,

	--------- Fecha de pago ---------

	format(apgFechaProcesamiento, 'yyyy-MM-dd') AS [Fecha de pago],

	--------- Concepto de pago ---------
	CASE WHEN apgOrigenAporte = 'TRASLADO_DESDE_OTRAS_CAJAS' THEN 11
		 WHEN apgOrigenAporte = 'PAGOS_MORA_NO_PAGO_PILA' THEN 2
		 WHEN apgOrigenAporte = 'NOVEDADES' THEN 3
		 WHEN apgOrigenAporte = 'PAGOS_REGIMIENES_ESPECIALES' THEN 4
		 WHEN apgOrigenAporte = 'PAGOS_REGIMIENES_EXCEPCION' THEN  5
		 WHEN apgOrigenAporte = 'PENSIONES_VOLUNTARIAS' THEN  6
		 WHEN apgOrigenAporte = 'ACUERDO_DE_PAGO' THEN 	7
		 WHEN apgOrigenAporte = 'APORTE_ADICIONAL' THEN 	8
		 WHEN apgOrigenAporte = 'APORTE_SOLIDARIO' THEN 	9
		 WHEN apgOrigenAporte = 'PAGOS_PENSIONADOS_CCF' THEN 10
		 WHEN apgOrigenAporte = 'SIN_INFORMACION_PERIODO' THEN 12
		 WHEN apgOrigenAporte = 'CALCULO_ACTUARIAL' THEN  1
	ELSE 12 END AS [Concepto_pago],

	--------- IBC ---------
	REPLACE (  (ISNULL (AD.apdValorIBC,0)),'.00000','') AS IBC,

	--------- ING - Ingreso ---------  
	CASE 
		WHEN regDetalle.redNovIngreso IS NOT NULL
		THEN '3'
		ELSE NULL
	END AS [ING - Ingreso],

	--------- RET - Retiro ---------
	CASE 
		WHEN regDetalle.redNovRetiro IS NOT NULL
		THEN '3' 
		ELSE NULL
	END AS [RET - Retiro],

	--------- TDE - Traslado desde otra EPS o EOC ---------
	NULL AS [TDE - Traslado desde otra EPS ó EOC],

	--------- TAE - Tralado a otra EPS o EOC ---------
	NULL AS [TAE - Traslado a otra EPS o EOC],

	--------- VSP - Variación permanente de salario ---------
	CASE 
		WHEN regDetalle.redNovVSP IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [VSP - Variación permanente de salario],

	--------- VST: Variación transitoria del salario ---------
	CASE
		WHEN regDetalle.redNovVST IS NOT NULL
		THEN '1' 
		ELSE NULL
	END AS [VST: Variación transitoria del salario],

	--------- SLN: Suspensión temporal del contrato de trabajo o licencia no remunerada o comisión de servicios ---------
	CASE 
		WHEN regDetalle.redNovSLN IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [SLN: Suspensión temporal del contrato de trabajo o licencia no remunerada o comisión de servicios],

	--------- IGE: Incapacidad Temporal por Enfermedad General ---------
	CASE 
		WHEN regDetalle.redNovIGE IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [IGE: Incapacidad Temporal por Enfermedad General],

	--------- LMA: Licencia de Maternidad o de Paternidad ---------
	CASE 
		WHEN regDetalle.redNovLMA IS NOT NULL
		THEN '1'
		ELSE NULL
	END AS [LMA: Licencia de Maternidad o de Paternidad],

	--------- VAC - LR: Vacaciones, Licencia Remunerada ---------
	CASE
		WHEN regDetalle.redFechaInicioVACLR IS not NULL
		THEN '1'
		ELSE NULL
	END AS [VAC - LR: Vacaciones, Licencia Remunerada],

	--------- Correcciones ---------
	 CASE
		WHEN aporteDetalle.apdMarcaPeriodo = 'PERIODO_REGULAR'
		THEN '1'
		ELSE NULL
	END AS [Correcciones],

	--------- Exonerado_L1607_2012 ---------
	'2' AS [Exonerado_L1607_2012],

	--------- Días_cotizados ---------
	 CAST( AD.APDDIASCOTIZADOS  AS BIGINT )  AS [Días_cotizados],

	--------- Días_mora --------- 
	'' AS [Días_mora],

	--------- Valor_pagado --------- 
	REPLACE (  (isnull (aporteDetalle.apdAporteObligatorio,0)),'.00000','') AS [Valor_pagado],

	--------- Valor_UPC_Adicional --------- 
	NULL AS [Valor_UPC_Adicional],

	--------- Valor_intereses --------- 
	ROUND(REPLACE (  (isnull (aporteDetalle.apdValorIntMora,0)),'.00000',''),0) AS [Valor_intereses]
 
FROM 
	---Movimientoaporte with(nolock)
	---INNER JOIN 
	aportegeneral2 with(nolock)--- ON apgid=moaaportegeneral
	LEFT JOIN AporteDetallado2 aporteDetalle with(nolock) ON apgid = apdAporteGeneral
	 ---on moaAporteDetallado = aporteDetalle.apdId
	LEFT JOIN RegistroDetallado2 regDetalle with(nolock) on regDetalle.redId = aporteDetalle.apdRegistroDetallado

	INNER JOIN persona pe with(nolock) ON perid=apgPersona
	INNER JOIN Afiliado afi with(nolock) ON afi.afiPersona=pe.perId
	INNER JOIN RolAfiliado roa with(nolock) ON roa.roaAfiliado = afi.afiId 
		 AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'
	     AND redOUTTipoAfiliado = roa.roaTipoAfiliado
	INNER JOIN ubicacion U with(nolock) ON U.ubiId=PE.Perubicacionprincipal
	INNER JOIN municipio M with(nolock) ON M.MunId=U.Ubimunicipio
	INNER JOIN departamento D with(nolock) ON D.Depid = M.mundepartamento
	INNER JOIN solicitudaporte sda with(nolock) ON sda.soaRegistroGeneral=apgRegistroGeneral
	INNER JOIN solicitud s with(nolock) ON s.solid=sda.soaSolicitudGlobal
	LEFT JOIN AporteDetallado2 AD with(nolock) ON AD.apdAporteGeneral=apgId
	LEFT JOIN Persona PC ON PC.perId=AD.apdPersona
WHERE apgEstadoAportante IN ( 'ACTIVO','NO_FORMALIZADO_RETIRADO_CON_APORTES','INACTIVO') AND
	apgModalidadRecaudoAporte = 'MANUAL' AND
	apgFechaProcesamiento >= @FECHA_INICIAL AND 
	apgFechaProcesamiento <= @FECHA_FINAL AND
	sda.soaEstadoSolicitud IN ('CERRADA') AND 
	s.solresultadoproceso NOT IN ('CANCELADA','DESISTIDA')  
	AND apgValTotalApoObligatorio > 0 
	-----comentariado por glpi 74036 20231205
END