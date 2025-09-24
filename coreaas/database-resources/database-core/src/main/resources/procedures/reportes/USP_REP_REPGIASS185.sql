/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS185]    Script Date: 2023-09-20 11:58:54 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS185]    Script Date: 2023-09-18 10:25:50 AM ******/
 
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS185]    Script Date: 2023-09-14 8:26:24 AM ******/
-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 185 afiliaciones trabajadores
-- =============================================
create or ALTER     PROCEDURE [dbo].[USP_REP_REPGIASS185]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON

--REPORTE 185 GIASS
--Calculo de las fechas de reporte
DECLARE @fecha DATE = dbo.getLocalDate(),
		@fechaHora DATETIME ;
SET @fechaHora = DATEADD (HOUR,16,CONVERT (DATETIME,@fecha)); 




	SELECT apdPersona,MAX(salario) as salario, roaAfiliado ,roaTipoAfiliado,
						apgPeriodoAporte,dsaEmpleador,apdSalarioIntegral
			 	INTO   #salarios
				FROM (
				SELECT DISTINCT  CASE WHEN apgPeriodoAporte = MAX(apgPeriodoAporte)  
						OVER (PARTITION BY apdpersona ) THEN apdPersona ELSE NULL END AS apdPersona  , 
						ISNULL(apdSalarioBasico,roaValorSalarioMesadaIngresos) AS salario, roaAfiliado ,roaTipoAfiliado,
						apgPeriodoAporte, roaempleador as dsaEmpleador,apdSalarioIntegral
					          FROM afiliado  as afix  WITH(NOLOCK)
						INNER JOIN RolAfiliado r  with(nolock) on roaAfiliado = afix.afiid 
						INNER JOIN EMPLEADOR EM ON em.empid = roaEmpleador
						INNER JOIN Persona as px WITH(NOLOCK) on afix.afiPersona = px.perId
						INNER JOIN AporteDetallado  as apdx WITH(NOLOCK) 
						        on apdx.apdPersona = px.perId AND roaTipoAfiliado = apdTipoCotizante
						INNER JOIN AporteGeneral  as apgx WITH(NOLOCK) on apgx.apgId = apdx.apdAporteGeneral and em.empempresa = apgx.apgEmpresa
							 WHERE roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'   
							 ---AND  em.empEmpresa = 8022 AND roaId = 617459
							 AND apdSalarioBasico>0
							 AND roaEstadoAfiliado = 'ACTIVO' 
							 AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
						 
				 
				) salario
				WHERE apdpersona IS NOT NULL
				GROUP BY apdPersona, roaAfiliado ,roaTipoAfiliado,
						apgPeriodoAporte, dsaEmpleador ,apdSalarioIntegral
				
			
		   INSERT INTO  #salarios
		        SELECT 	apdPersona, MAX(salario) AS Salario ,roaAfiliado, roaTipoAfiliado,
						apgPeriodoAporte ,0 as dsaEmpleador,apdSalarioIntegral
					FROM (
								SELECT DISTINCT CASE WHEN apgPeriodoAporte = MAX(apgPeriodoAporte)  
									OVER (PARTITION BY apdpersona) THEN apdPersona ELSE NULL END AS apdPersona  , 
									 ISNULL(apdSalarioBasico,roaValorSalarioMesadaIngresos)  AS salario,
									roaAfiliado ,roaTipoAfiliado,apgPeriodoAporte , null  dsaEmpleador,apdSalarioIntegral
							  FROM afiliado  as afix  WITH(NOLOCK)
						INNER JOIN RolAfiliado r  with(nolock) on roaAfiliado = afix.afiid 
						INNER JOIN Persona as px WITH(NOLOCK) on afix.afiPersona = px.perId
						INNER JOIN AporteDetallado  as apdx WITH(NOLOCK) 
						        ON apdx.apdPersona = px.perId 
								AND roaTipoAfiliado = apdTipoCotizante
						INNER JOIN AporteGeneral  as apgx WITH(NOLOCK) on apgx.apgId = apdx.apdAporteGeneral 
							 WHERE roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE' 
							   AND roaEstadoAfiliado = 'ACTIVO'		
							   AND apdSalarioBasico>0
							   AND apgEstadoRegistroAporteAportante = 'REGISTRADO'
			 ) X
			WHERE  X.apdPersona IS NOT NULL
		 GROUP BY apdPersona,  roaAfiliado,roaTipoAfiliado,
			      apgPeriodoAporte,dsaEmpleador,apdSalarioIntegral

SELECT DISTINCT
CASE ISNULL(pe.perTipoIdentificacion, pa.perTipoIdentificacion) ---SE ADICIONA pa.perTipoIdentificacion para independiente y pensionados YA QUE ES UN CAMPO OBLIGATORIO EL DIA 20210518
       WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'---SE ADICIONA PERMISO ESPECIAL EL DIA 20210518 Y PORQUE ES OBLIGATORIO
	ELSE ''
	END AS 'C8',--PARA ESTE CAMPO QUE ES OBLIGATORIA QUE HACEN CON LOS INDEPENDIENTES Y PENSIONADOS???
ISNULL (pe.perNumeroIdentificacion,pa.perNumeroIdentificacion) AS 'C9',---SE ADICIONA pa.perTipoIdentificacion para independiente y pensionados YA QUE ES UN CAMPO OBLIGATORIO EL DIA 20210518
CASE pa.perTipoIdentificacion 
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'---SE ADICIONA PERMISO ESPECIAL EL DIA 20210518 Y PORQUE ES OBLIGATORIO
	ELSE ''
	END 
AS 'C47',
pa.perNumeroIdentificacion AS 'C48',
ISNULL(CAST(ISNULL(sal.salario,roaValorSalarioMesadaIngresos) AS BIGINT),0) AS 'C64',
CAST(ISNULL(r.roaFechaIngreso,r.roaFechaAfiliacion) AS VARCHAR (10)) AS 'C66',
ISNULL (ur.ubiTelefonoFijo, '') AS 'C59',
ISNULL (ur.ubiEmail, '') AS 'C60',
ISNULL (ur.ubiTelefonoCelular, '') AS 'C652',
SUBSTRING (REPLACE(ur.ubiDireccionFisica,',',''), 1, 40) AS 'C58',
cast(r.roaFechaAfiliacion AS VARCHAR (10)) AS 'C523',
convert(varchar,ISNULL ( r.roaFechaRetiro,r.roaFechaFinContrato),23) AS 'C525',-- De donde se debe considarar la información para extraer este dato, de la fecha fin del contrato?

CASE con.perTipoIdentificacion 
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END AS 'C653',
ISNULL (con.perNumeroIdentificacion, '') AS 'C654',
'' AS 'C591', -- ¿Quién es el tercero a endosar?
'' AS 'C592', -- ¿Quién es el tercero a endosar?
'' AS 'C593', -- ¿De dondé de obtiene el primer nombre de tercedo a endosar?
'' AS 'C594', -- ¿De dondé de obtiene el segundo nombre de tercedo a endosar?
'' AS 'C595', -- ¿De dondé de obtiene el primer apellido de tercedo a endosar?
'' AS 'C596', -- ¿De dondé de obtiene el segundo apellido de tercedo a endosar?
ISNULL (r.roaHorasLaboradasMes, 0) AS 'C147',
ISNULL(CASE WHEN r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN CAST ( ISNULL(sal.salario,roaValorSalarioMesadaIngresos) AS bigint) ELSE '0' END,'0') AS 'C843',
CASE WHEN r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN CAST ( ISNULL(sal.salario,roaValorSalarioMesadaIngresos) AS bigint) ELSE '0' END AS 'C844',
CASE WHEN r.roaTipoAfiliado = 'PENSIONADO' THEN CAST ( ISNULL(sal.salario,roaValorSalarioMesadaIngresos) AS bigint) ELSE '0' END AS 'C845',
CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN 1
	WHEN 'TRABAJADOR_INDEPENDIENTE' THEN 2
	WHEN 'PENSIONADO' THEN 3
	END AS 'C862',
dep.depCodigo AS 'C56',
RIGHT (mun.munCodigo, 3) AS 'C57',
SUBSTRING (REPLACE(ur.ubiDireccionFisica,',',''), 1, 40) AS 'C144',
dep.depCodigo AS 'C145',
RIGHT (mun.munCodigo, 3) AS 'C146',
ISNULL (CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN deps.depCodigo ELSE dep.depCodigo END, '') AS 'C61',
ISNULL (CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN RIGHT (muns.munCodigo, 3) ELSE RIGHT (mun.munCodigo, 3) END, '') AS 'C62',
ISNULL (CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN CAST (sue.sueCodigo AS VARCHAR (15)) ELSE NULL END, '') AS 'C63',
ISNULL (CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN deps.depCodigo ELSE NULL END, '') AS 'C26',
ISNULL (CASE r.roaTipoAfiliado WHEN 'TRABAJADOR_DEPENDIENTE' THEN RIGHT (muns.munCodigo, 3) ELSE NULL END, '') AS 'C27',
CASE WHEN r.roaTipoSalario = 'INTEGRAL' 
	 THEN LEFT (CAST ( roaValorSalarioMesadaIngresos AS VARCHAR (13)), LEN( roaValorSalarioMesadaIngresos)-3) 
	 WHEN SAL.apdSalarioIntegral = 1 THEN  LEFT (CAST ( REPLACE(sal.salario  ,'.00000',0) AS VARCHAR (13)), LEN( sal.salario)-3) 
	 ELSE '0' END AS 'C65',

CASE WHEN r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' AND r.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
	WHEN r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' AND r.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '4'
	WHEN r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' AND isnull(r.roaClaseTrabajador,'') NOT IN ('SERVICIO_DOMESTICO', 'MADRE_COMUNITARIA') THEN '1'
	WHEN r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN '3'
	WHEN r.roaTipoAfiliado = 'PENSIONADO' THEN '10'
	END AS 'C67',

	CASE 
	WHEN ISNULL(sol.solClasificacion,'') = 'MENOS_1_5_SM_0_POR_CIENTO' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '14'
	WHEN  ISNULL(sol.solClasificacion,'') = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO'	 
	  AND r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN '12'
	  	WHEN  ISNULL(sol.solClasificacion,'') <> 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO'	 
	  AND r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN '10'
	WHEN  r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'  THEN '11'
	WHEN ISNULL(sol.solClasificacion,'') = 'MAS_1_5_SM_0_6_POR_CIENTO' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '14'
	WHEN ISNULL(sol.solClasificacion,'') = 'MENOS_1_5_SM_2_POR_CIENTO'  AND r.roaTipoAfiliado = 'PENSIONADO' THEN '15'
	WHEN ISNULL(sol.solClasificacion,'') = 'MENOS_1_5_SM_0_6_POR_CIENTO'  AND r.roaTipoAfiliado = 'PENSIONADO' THEN '14'
	WHEN ISNULL(sol.solClasificacion,'') = 'PENSION_FAMILIAR' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '14'
	WHEN ISNULL(sol.solClasificacion,'') = 'FIDELIDAD_25_ANIOS' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '13'
	WHEN ISNULL(sol.solClasificacion,'') = '' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '14'
	WHEN ISNULL(sol.solClasificacion,'') = 'MAS_1_5_SM_2_POR_CIENTO' AND r.roaTipoAfiliado = 'PENSIONADO' THEN '15'
	WHEN ISNULL(sol.solClasificacion,'') = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO'	 AND r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'	THEN '10'
	ELSE '' END  AS 'C68',
CASE pd.pedEstadoCivil WHEN 'SOLTERO' THEN '1'
	WHEN 'VIUDO' THEN '5'
	WHEN 'CASADO' THEN '2'
	WHEN 'SEPARADO'  THEN '3'
	WHEN 'DIVORCIADO' THEN '6'
	WHEN 'UNION_LIBRE' THEN '4'
	ELSE '6'
	END AS 'C159',
CASE WHEN pedNivelEducativo IN ('NINGUNO', 'PRIMERA_INFANCIA', 'PREESCOLAR') OR pedNivelEducativo IS NULL THEN 0
	WHEN pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS', 'BASICA_PRIMARIA_INCOMPLETA_ADULTOS', 'BASICA_PRIMARIA_COMPLETA', 'BASICA_PRIMARIA_INCOMPLETA') THEN 1
	WHEN pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS', 'MEDIA_INCOMPLETA_ADULTOS', 'BASICA_SECUNDARIA_COMPLETA_ADULTOS', 'BASICA_SECUNDARIA_INCOMPLETA_ADULTOS', 
	'MEDIA_COMPLETA', 'MEDIA_INCOMPLETA', 'BASICA_SECUNDARIA_COMPLETA', 'BASICA_SECUNDARIA_INCOMPLETA') THEN 2
	WHEN pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO'  THEN 3
	WHEN pedNivelEducativo IN ('SUPERIOR', 'SUPERIOR_PREGRADO') THEN 4
	WHEN pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 5
	END AS 'C160',
CASE WHEN pedHabitaCasaPropia = 1 THEN 1 ELSE 2 END AS 'C203', -- No se tiene información de los códigos 3- Familiar o 4- Usufructo
'' AS 'C211', -- ¿De dónde se optiene está información?
'' AS 'C212',-- ¿De dónde se optiene está información?
'' AS 'C213',-- ¿De dónde se optiene está información?
'' AS 'C214',-- ¿De dónde se optiene está información?
'' AS 'C215',-- ¿De dónde se optiene está información?
'' AS 'C216',-- ¿De dónde se optiene está información?
concat (CASE WHEN r.roaTipoAfiliado = 'PENSIONADO' THEN '1'
			  WHEN  ISNULL(sol.solClasificacion,'') = 'FIDELIDAD_25_ANIOS' THEN '2'
	ELSE '' END,';') AS 'C158'
      FROM RolAfiliado r WITH(NOLOCK)
INNER JOIN afiliado a  WITH(NOLOCK) ON r.roaAfiliado = a.afiId
INNER JOIN persona pa  WITH(NOLOCK) ON a.afiPersona = pa.perId
INNER JOIN PersonaDetalle pd  WITH(NOLOCK) ON pd.pedPersona = pa.perId
INNER JOIN SolicitudAfiliacionPersona sap  WITH(NOLOCK) ON sap.sapRolAfiliado = r.roaId
INNER JOIN Solicitud sol  WITH(NOLOCK) ON sol.solId = sap.sapSolicitudGlobal
 LEFT JOIN empleador em  WITH(NOLOCK) ON em.empId = r.roaEmpleador
 LEFT JOIN Empresa e  WITH(NOLOCK) ON e.empId = em.empEmpresa
 LEFT JOIN Persona pe  WITH(NOLOCK) ON pe.perId = e.empPersona
  ----afiliacion empresa
left join SolicitudAfiliaciEmpleador sae on em.empId=sae.saeEmpleador
left join solicitud s2 on s2.solId=sae.saeSolicitudGlobal and sae.saeEstadoSolicitud='CERRADA' and s2.solResultadoProceso='APROBADA'
--Datos Ubicación rolAfiliado
 LEFT JOIN Ubicacion ur  WITH(NOLOCK) ON ur.ubiId = pa.perUbicacionPrincipal
 LEFT JOIN Municipio mun  WITH(NOLOCK) ON mun.munId = ur.ubiMunicipio
 LEFT JOIN Departamento dep  WITH(NOLOCK) ON dep.depId = mun.munDepartamento
-- Datos de sucursar trabajador Dependiente
LEFT JOIN SucursalEmpresa sue  WITH(NOLOCK) ON sue.sueId = r.roaSucursalEmpleador
LEFT JOIN Ubicacion us  WITH(NOLOCK) ON us.ubiId = sue.sueUbicacion
LEFT JOIN Municipio muns  WITH(NOLOCK) ON muns.munId = us.ubiMunicipio
LEFT JOIN Departamento deps  WITH(NOLOCK) ON deps.depId = muns.munDepartamento
--Datos Beneficiario conyuge
LEFT JOIN
(    SELECT pc.perTipoIdentificacion, pc.perNumeroIdentificacion, benAfiliado 
       FROM persona pc
 INNER JOIN Beneficiario ben  WITH(NOLOCK) 
	     ON ben.benPersona = pc.perId 
	    AND ben.benTipoBeneficiario = 'CONYUGE'
	  WHERE ben.benEstadoBeneficiarioAfiliado = 'ACTIVO') Con
ON con.benAfiliado = a.afiId
left join #salarios sal ON isnull(em.empid,0) = isnull(sal.dsaEmpleador,0) 
and sal.roaAfiliado = r.roaAfiliado 
and sal.roaTipoAfiliado = r.roaTipoAfiliado
--Reporte con corte semanal
WHERE roaFechaAfiliacion <=  @fechaHora  
--AND (roaEstadoAfiliado = 'ACTIVO' OR (roaEstadoAfiliado = 'INACTIVO' AND roaFechaRetiro >  @fechaHora))
 AND roaEstadoAfiliado IN ('ACTIVO','INACTIVO')
 and sapEstadoSolicitud = 'CERRADA' AND sol.solResultadoProceso = 'APROBADA'
 and sol.solCajaCorrespondencia is null
 --and s2.solResultadoProceso='APROBADA' and sae.saeEstadoSolicitud='CERRADA'
 --ORDER BY 4
--Reporte Sin corte minimo Semanal
--WHERE r.roaEstadoAfiliado = 'ACTIVO'
--AND r.roaFechaAfiliacion < @fechaHora

--WHERE r.roaId = 1759


--SELECT DISTINCT roaClaseIndependiente FROM RolAfiliado-- WHERE roaTipoSalario = 'INTEGRAL'
--SELECT DISTINCT pedEstadoCivil FROM PersonaDetalle
END