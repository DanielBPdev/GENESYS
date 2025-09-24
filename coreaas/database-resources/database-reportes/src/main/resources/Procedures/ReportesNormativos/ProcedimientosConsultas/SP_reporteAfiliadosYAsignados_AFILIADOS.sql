CREATE OR ALTER PROCEDURE [dbo].[reporteAfiliadosYAsignados_AFILIADOS] ( @FECHA_INICIAL DATETIME, @FECHA_FINAL DATETIME )
AS 
--/---------------------------------------**********---------------------------------------\--
--                  REPORTE DE AFILIADOS Y ASIGNADOS - AFILIADOS  -  N� 17A.
--\---------------------------------------**********---------------------------------------/--
BEGIN
-- DECLARE @FECHA_INICIAL DATE = '2025-02-01', @FECHA_FINAL   DATE = '2025-02-28'

/*
* Se incuyen los registros en la tabla temporal, los que por fecha de afiliación o de retiro deben ser considerados en el reporte
*/
drop table if exists #tmpEvaluarRegistros
select distinct p.perTipoIdentificacion, p.perNumeroIdentificacion,p.perRazonSocial, ra.roaId,ra.roaFechaAfiliacion,CAST(ra.roaFechaRetiro AS DATE) [roaFechaRetiro],ra.roaEstadoAfiliado,
CASE  
	WHEN ra.roaFechaAfiliacion <= @FECHA_FINAL AND ra.roaEstadoAfiliado = 'ACTIVO' THEN 'ACTIVO'
	WHEN ra.roaFechaAfiliacion <= @FECHA_FINAL AND ra.roaEstadoAfiliado != 'ACTIVO' AND ra.roaFechaRetiro between @FECHA_INICIAL and @FECHA_FINAL THEN 'ACTIVO'
	WHEN ra.roaFechaAfiliacion <= @FECHA_FINAL AND ra.roaEstadoAfiliado != 'ACTIVO' AND ra.roaFechaRetiro >= @FECHA_FINAL THEN 'ACTIVO'
ELSE 'INACTIVO' END [roaEstadoAfiliadoEvaluado],@FECHA_INICIAL [FECHA_INICIAL],@FECHA_FINAL [FECHA_FINAL]
into #tmpEvaluarRegistros
from Persona p
inner join Afiliado a on p.perId = a.afiPersona
inner join RolAfiliado ra on ra.roaAfiliado = a.afiId
where ra.roaFechaAfiliacion <= @FECHA_FINAL 
--and p.perNumeroIdentificacion in ('1003844887','75096886','24333055','1003844887','1000159862','1002654754','1002546308','1002591846','5920650')
order by CAST(ra.roaFechaRetiro AS DATE) desc


delete from #tmpEvaluarRegistros where roaEstadoAfiliadoEvaluado = 'INACTIVO'

-- select * from #tmpEvaluarRegistros


/*
* Obteniendo los Radicados de Afiliación
*/
drop table if exists #tmpSolicitudes
select s.solCanalRecepcion,s.solNumeroRadicacion,s.solFechaRadicacion,s.solTipoTransaccion,s.solResultadoProceso,sap.sapEstadoSolicitud,sap.sapRolAfiliado,s.solClasificacion,
CASE WHEN  s.solId  = MAX(s.solId)  OVER (PARTITION BY sap.sapRolAfiliado) THEN  s.solId  ELSE NULL END as  [solId]
into #tmpSolicitudes
from Solicitud s
inner join SolicitudAfiliacionPersona sap on s.solId = sap.sapSolicitudGlobal
where  s.solResultadoProceso = 'APROBADA'
--and sap.sapRolAfiliado in (398876,704092,669855,590987,772379,780860,760703,812217)
union
select s.solCanalRecepcion,s.solNumeroRadicacion,s.solFechaRadicacion,s.solTipoTransaccion,s.solResultadoProceso,sn.snoEstadoSolicitud,snp.snpRolAfiliado,s.solClasificacion,
CASE WHEN  s.solId  = MAX(s.solId)  OVER (PARTITION BY snp.snpRolAfiliado) THEN  s.solId  ELSE NULL END as  [solId]
from Solicitud s
inner join SolicitudNovedad sn on s.solId = sn.snoSolicitudGlobal
inner join SolicitudNovedadPersona snp on snp.snpSolicitudNovedad = sn.snoId
where s.solTipoTransaccion in ('NOVEDAD_REINTEGRO') and s.solResultadoProceso = 'APROBADA'
--and snp.snpRolAfiliado in (398876,704092,669855,590987,772379,780860,760703,812217)

delete from #tmpSolicitudes where solId is null

--select *  from #tmpSolicitudes where solId is not null


	drop table if exists #Salario
	SELECT 
			CASE 
				WHEN apgx.apgPeriodoAporte =  MAX(apgx.apgPeriodoAporte) over (partition by apdx.apdpersona,em.empid )
				THEN CONVERT(NUMERIC,(AVG(apdx.apdSalarioBasico))) 
				ELSE NULL 
			END AS apdSalarioBasico,
		
			CASE 
				WHEN apgx.apgPeriodoAporte =  MAX(apgx.apgPeriodoAporte) over (partition by apdx.apdpersona,em.empid )
				THEN apgPeriodoAporte 
				ELSE NULL 
			END AS apgPeriodoAporte,
		
			em.empid AS dsaEmpleadorx, 
			apdx.apdPersona
		  INTO #Salario 
		  FROM 
			afiliado as afix   with(nolock)
			INNER JOIN Persona as px  with(nolock)on afix.afiPersona = px.perId
			INNER JOIN AporteDetallado2 as apdx  with(nolock)on apdx.apdPersona = px.perId 
			INNER JOIN AporteGeneral2 as apgx  with(nolock)on apgx.apgId = apdx.apdAporteGeneral 
			       AND apgx.apgTipoSolicitante = 'EMPLEADOR'
			INNER JOIN Empresa e with(nolock) on e.empId = apgx.apgEmpresa
			INNER JOIN Empleador em  with(nolock) on e.empId = em.empEmpresa 
			       AND CONCAT(apgx.apgPeriodoAporte, '-01') < DATEADD(MONTH, DATEDIFF(MONTH, 0,GETDATE()), 0)	
		GROUP  BY 
			em.empid, 
			apdx.apdPersona, 
			apgx.apgPeriodoAporte

	    --CREATE CLUSTERED INDEX id ON  #salario (dsaEmpleadorx,apdPersona,apgPeriodoAporte)


		drop table if exists #SalarioIndPen
		 SELECT 
			CASE 
				WHEN apgx.apgPeriodoAporte =  MAX(apgx.apgPeriodoAporte) over (partition by apdx.apdpersona,em.empid )
				THEN CONVERT(NUMERIC,(AVG(apdx.apdSalarioBasico))) 
				ELSE NULL 
			END AS apdSalarioBasico,
		
			CASE 
				WHEN apgx.apgPeriodoAporte =  MAX(apgx.apgPeriodoAporte) over (partition by apdx.apdpersona,em.empid )
				THEN apgPeriodoAporte 
				ELSE NULL 
			END AS apgPeriodoAporte,
		
			em.empid AS dsaEmpleadorx, 
			apdx.apdPersona, r.roaid , r.roaTipoAfiliado
		 INTO #SalarioIndPen
		  FROM 
			afiliado as afix  
			inner join RolAfiliado as r  with(nolock) on r.roaAfiliado = afix.afiid
			inner join Persona as px  with(nolock) on afix.afiPersona = px.perId
			inner join AporteDetallado2 as apdx  with(nolock) on apdx.apdPersona = px.perId
			inner join AporteGeneral2 as apgx with(nolock) 
			        ON apgx.apgId = apdx.apdAporteGeneral 
					AND apgx.apgTipoSolicitante IN ( 'INDEPENDIENTE','PENSIONADO')
					and apgx.apgTipoSolicitante = r.roaTipoAfiliado
			LEFT join Empresa e with(nolock) on e.empId = apgx.apgEmpresa
			LEFT join Empleador em with(nolock) on e.empId = em.empEmpresa 
			AND CONCAT(apgx.apgPeriodoAporte, '-01') < (DATEADD(MONTH, DATEDIFF(MONTH, 0,GETDATE()), 0)) 
			WHERE r.roaEstadoAfiliado='ACTIVO'
	
		GROUP  BY 
			em.empid, 
			apdx.apdPersona, 
			apgx.apgPeriodoAporte, r.roaid ,roaTipoAfiliado

			--CREATE CLUSTERED INDEX ids ON  #SalarioIndPen (dsaEmpleadorx,apdPersona,apgPeriodoAporte)




/*
* Población de Afiliados  Dependiente
*/
DROP TABLE IF EXISTS #TMP_Reporte17
SELECT 
-- Fecha de corte de la informaci�n remitida
re.FECHA_FINAL [Fecha de corte de la información remitida],
-- Cantidad de registros enviados de afiliados
1 as [Cantidad de registros enviados de afiliados],
-- NIT Entidad
REPLACE((select prmValor from Parametro where prmNombre IN ('NUMERO_ID_CCF')), '-', '') AS [NIT Entidad],
-- ID Hogar
s.solNumeroRadicacion AS [ID Hogar],
-- Documento Afiliado
p.perNumeroIdentificacion AS [Documento Afiliado],
-- Apellidos del Afiliado
CONCAT ( p.perPrimerApellido, ' ', p.perSegundoApellido ) AS [Apellidos del Afiliado],
-- Nombres del Afiliado
CONCAT ( p.perPrimerNombre,' ', p.perSegundoNombre ) AS [Nombres del Afiliado],
--Nombre Entidad
(select prmValor from Parametro where prmNombre IN ('NOMBRE_CCF')) AS [Nombre Entidad],
--Fecha de afiliaci�n
FORMAT(MAX(ra.roaFechaAfiliacion), 'yyyy/MM/dd') AS [Fecha de afiliacion],
--Ingresos Afiliado
CASE
	WHEN SUM(CAST(sal.apdSalarioBasico AS NUMERIC(15,2)) )  IS NULL
	THEN  SUM(CAST(ra.roaValorSalarioMesadaIngresos AS NUMERIC(15,2)) ) 
	ELSE  SUM(CAST(sal.apdSalarioBasico AS NUMERIC(15,2))) 
	END AS [Ingresos Afiliado],
	----------Tipo de documento
CASE 
	WHEN p.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
	THEN '1'
	WHEN p.perTipoIdentificacion = 'CEDULA_EXTRANJERIA'
	THEN '2'
	WHEN p.perTipoIdentificacion IN ( 'TARJETA_IDENTIDAD' )
	THEN '7'
	WHEN p.perTipoIdentificacion IN ( 'PERM_ESP_PERMANENCIA' )
	THEN '8'
	WHEN p.perTipoIdentificacion IN ( 'PERM_PROT_TEMPORAL' )
	THEN '9'
END AS [Tipo de documento],
	----------Estado Civil
CASE 
	WHEN ISNULL(pd.pedEstadoCivil,'SOLTERO') = 'SOLTERO'
	THEN '1'
	WHEN pd.pedEstadoCivil IN ( 'UNION_LIBRE', 'CASADO' )
	THEN '2'
	WHEN pd.pedEstadoCivil IN ( 'SEPARADO', 'VIUDO','DIVORCIADO' )
	THEN '3'
END AS [Estado Civil],
--Parentesco
'1' AS [Parentesco]--, solicitud.solClasificacion
INTO #TMP_Reporte17
FROM Persona p
inner join PersonaDetalle pd on pd.pedPersona = p.perId
inner join Afiliado a on p.perId = a.afiPersona
inner join RolAfiliado ra on ra.roaAfiliado = a.afiId
inner join #tmpEvaluarRegistros re on re.roaId = ra.roaId
inner join #tmpSolicitudes s on s.sapRolAfiliado = ra.roaId
left join #Salario sal on sal.apdPersona = p.perId and sal.dsaEmpleadorx = ra.roaEmpleador
group by re.FECHA_FINAL,s.solNumeroRadicacion,p.perNumeroIdentificacion,p.perPrimerApellido,p.perSegundoApellido,p.perPrimerNombre,p.perSegundoNombre,p.perTipoIdentificacion,pd.pedEstadoCivil

UNION 
/*
* Población de Afiliados  Independiente y Pensionado
*/

SELECT 
-- Fecha de corte de la informaci�n remitida
re.FECHA_FINAL [Fecha de corte de la información remitida],
-- Cantidad de registros enviados de afiliados
1 as [Cantidad de registros enviados de afiliados],
-- NIT Entidad
REPLACE((select prmValor from Parametro where prmNombre IN ('NUMERO_ID_CCF')), '-', '') AS [NIT Entidad],
-- ID Hogar
s.solNumeroRadicacion AS [ID Hogar],
-- Documento Afiliado
p.perNumeroIdentificacion AS [Documento Afiliado],
-- Apellidos del Afiliado
CONCAT ( p.perPrimerApellido, ' ', p.perSegundoApellido ) AS [Apellidos del Afiliado],
-- Nombres del Afiliado
CONCAT ( p.perPrimerNombre,' ', p.perSegundoNombre ) AS [Nombres del Afiliado],
--Nombre Entidad
(select prmValor from Parametro where prmNombre IN ('NOMBRE_CCF')) AS [Nombre Entidad],
--Fecha de afiliaci�n
FORMAT(MAX(ra.roaFechaAfiliacion), 'yyyy/MM/dd') AS [Fecha de afiliacion],
--Ingresos Afiliado
CASE
	WHEN SUM(CAST(sal.apdSalarioBasico AS NUMERIC(15,2)) )  IS NULL
	THEN  SUM(CAST(ra.roaValorSalarioMesadaIngresos AS NUMERIC(15,2)) ) 
	ELSE  SUM(CAST(sal.apdSalarioBasico AS NUMERIC(15,2))) 
	END AS [Ingresos Afiliado],
	----------Tipo de documento
CASE 
	WHEN p.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
	THEN '1'
	WHEN p.perTipoIdentificacion = 'CEDULA_EXTRANJERIA'
	THEN '2'
	WHEN p.perTipoIdentificacion IN ( 'TARJETA_IDENTIDAD' )
	THEN '7'
	WHEN p.perTipoIdentificacion IN ( 'PERM_ESP_PERMANENCIA' )
	THEN '8'
	WHEN p.perTipoIdentificacion IN ( 'PERM_PROT_TEMPORAL' )
	THEN '9'
END AS [Tipo de documento],
	----------Estado Civil
CASE 
	WHEN ISNULL(pd.pedEstadoCivil,'SOLTERO') = 'SOLTERO'
	THEN '1'
	WHEN pd.pedEstadoCivil IN ( 'UNION_LIBRE', 'CASADO' )
	THEN '2'
	WHEN pd.pedEstadoCivil IN ( 'SEPARADO', 'VIUDO','DIVORCIADO' )
	THEN '3'
END AS [Estado Civil],
--Parentesco
'1' AS [Parentesco]--, solicitud.solClasificacion
FROM Persona p
inner join PersonaDetalle pd on pd.pedPersona = p.perId
inner join Afiliado a on p.perId = a.afiPersona
inner join RolAfiliado ra on ra.roaAfiliado = a.afiId
inner join #tmpEvaluarRegistros re on re.roaId = ra.roaId
inner join #tmpSolicitudes s on s.sapRolAfiliado = ra.roaId
left join #Salario sal on sal.apdPersona = p.perId and sal.dsaEmpleadorx = ra.roaEmpleador
where ra.roaTipoAfiliado in ('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
AND S.solClasificacion IN ('TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO', 'MENOS_1_5_SM_2_POR_CIENTO', 'MAS_1_5_SM_2_POR_CIENTO')
group by re.FECHA_FINAL,s.solNumeroRadicacion,p.perNumeroIdentificacion,p.perPrimerApellido,p.perSegundoApellido,p.perPrimerNombre,p.perSegundoNombre,p.perTipoIdentificacion,pd.pedEstadoCivil

UNION 
----------------------------------------------------
---------- AFILIADOS BENEFICIARIOS
----------------------------------------------------
	select  
	[Fecha de corte de la información remitida],
	[Cantidad de registros enviados de afiliados],
	[NIT Entidad],	
	[ID Hogar],	
	[Documento Afiliado],	
	[Apellidos del Afiliado],	
	[Nombres del Afiliado],	
	[Nombre Entidad],	
	[Fecha de afiliaci�n],	
	[Ingresos Afiliado],	
	[Tipo de documento],	
	[Estado Civil],	
	[Parentesco]
 from (SELECT  DISTINCT
	-- Fecha de corte de la informaci�n remitida
		re.FECHA_FINAL [Fecha de corte de la información remitida],
	-- Cantidad de registros enviados de afiliados
	1 as [Cantidad de registros enviados de afiliados],		
	---------- NIT Entidad
	REPLACE(PARAMETRO.prmValor, '-', '') AS [NIT Entidad],

	---------- ID Hogar
	SOLICITUD.solNumeroRadicacion AS [ID Hogar],

	---------- Documento Afiliado
	PERSONA.perNumeroIdentificacion AS [Documento Afiliado],

	---------- Apellidos del Afiliado
	CONCAT ( PERSONA.perPrimerApellido, ' ', PERSONA.perSegundoApellido ) AS [Apellidos del Afiliado],

	---------- Nombres del Afiliado
	CONCAT ( PERSONA.perPrimerNombre,' ', PERSONA.perSegundoNombre ) AS [Nombres del Afiliado],
	----------Nombre Entidad
	PARAMETRO_NOMBRE.prmValor AS [Nombre Entidad],

	----------Fecha de afiliaci�n
	FORMAT(BENEFICIARIO.benFechaAfiliacion, 'yyyy/MM/dd') AS [Fecha de afiliaci�n],

	----------Ingresos Afiliado
	--CASE 
	--	WHEN INTEGRANTE.inhSalarioMensual IS NULL 
	--	THEN '0'
	--	ELSE SUM ( INTEGRANTE.inhSalarioMensual ) 
	--END 
	0.00 AS [Ingresos Afiliado],

	----------Tipo de documento
	CASE 
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
		THEN '1'
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_EXTRANJERIA'
		THEN '2'
		WHEN PERSONA.perTipoIdentificacion IN ( 'TARJETA_IDENTIDAD', 'REGISTRO_CIVIL')
		THEN '7'
		WHEN PERSONA.perTipoIdentificacion IN ( 'PERM_ESP_PERMANENCIA' )
		THEN '8'
		WHEN PERSONA.perTipoIdentificacion IN ( 'PERM_PROT_TEMPORAL' )
		THEN '9'
		ELSE ' '
	END AS [Tipo de documento],

	----------Estado Civil
	CASE 
		WHEN ISNULL(PERSONA_DET.pedEstadoCivil,'SOLTERO') = 'SOLTERO'
		THEN '1'
		WHEN PERSONA_DET.pedEstadoCivil IN ( 'UNION_LIBRE', 'CASADO' )
		THEN '2'
		WHEN PERSONA_DET.pedEstadoCivil IN ( 'SEPARADO', 'VIUDO','DIVORCIADO' )
		THEN '3'
	END AS [Estado Civil],

	----------Parentesco
	CASE 
		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'CONYUGE', 'CONYUGE_HOGAR' ) 
		THEN '2'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'HIJO_BIOLOGICO', 'HIJO_BIOLOGICO_HOGAR', 'HIJASTRO' ) 
		THEN '3'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'HERMANO', 'HERMANO_HOGAR', 'HERMANO_HUERFANO_DE_PADRES' ) 
		THEN '4'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'PADRE_HOGAR', 'MADRE_HOGAR', 'PADRE_MADRE_ADOPTANTE_HOGAR', 'MADRE', 'PADRE' ) 
		THEN '5'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'ABUELO', 'ABUELO_HOGAR', 'ABUELA', 'ABUELA_HOGAR', 'NIETO', 'NIETO_HOGAR', 'NIETA', 'NIETA_HOGAR' ) 
		THEN '6'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'BISNIETO','BISNIETO_HOGAR', 'BISNIETA', 'BISNIETA_HOGAR', 'BISABUELO','BISABUELO_HOGAR', 'BISABUELA', 'BISABUELA_HOGAR', 'SOBRINO', 'SOBRINA', 'SOBRINO_HOGAR', 'SOBRINA_HOGAR', 'TIO', 'TIO_HOGAR', 'TIA', 'TIA_HOGAR' ) 
		THEN '7'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'SUEGRO', 'SUEGRA', 'SUEGRO_HOGAR', 'SUEGRA_HOGAR', 'CU�ADO', 'CU�ADA', 'CU�ADO_HOGAR', 'CU�ADA_HOGAR' ) 
		THEN '8'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'HIJO_ADOPTIVO', 'HIJO_ADOPTIVO_HOGAR', 'PADRE_ADOPTIVO', 'PADRE_ADOPTIVO_HOGAR', 'BENEFICIARIO_EN_CUSTODIA' ) 
		THEN '9'

		WHEN BENEFICIARIO.benTipoBeneficiario IN( 'NUERA', 'NUERA_HOGAR', 'YERNO', 'YERNO_HOGAR' ) 
		THEN '10'

		ELSE '11'

	END AS [Parentesco],
	case when rab.roaTipoAfiliado IN ( 
			'PENSIONADOS',
			'PENSIONADO',
			'INDEPENDIENTE', 
			'INDEPENDIENTE_REGULAR', 
			'TRABAJADOR_INDEPENDIENTE'
		) and 	sb.saprolafiliado is null then 0 else 1 end as validacion
		
	--SELECT *
			FROM

	---------- DATOS AFILIACION
				Afiliado AS AFILIADO with(nolock)
	 INNER JOIN RolAfiliado AS ROLAFILIADO  with(nolock)
		     ON ROLAFILIADO.roaAfiliado = AFILIADO.afiId
		
 
	INNER JOIN Persona perAfiliado  with(nolock) on perAfiliado.perId = AFILIADO.afiPersona

	----ADICIONAR LAS MISMAS REGLAS DE LOS AFILIADOS
   INNER JOIN #tmpEvaluarRegistros re ON re.perNumeroIdentificacion = perAfiliado.perNumeroIdentificacion---2022-11-04
	
	--INNER JOIN
	
	--#tempSol as SOLICITUD  
	--   ON  RolAfiliado.roaid = solicitud.sapRolAfiliado  
	--   AND SOLICITUD.sapRolAfiliado IS NOT NULL

	INNER JOIN	
	#tmpSolicitudes as SOLICITUD  
	   ON  RolAfiliado.roaid = solicitud.sapRolAfiliado  

	---------- BENEFIIARIO
	LEFT JOIN Beneficiario AS BENEFICIARIO   with(nolock) 
	       ON BENEFICIARIO.benAfiliado = AFILIADO.afiId
	      AND BENEFICIARIO.benEstadoBeneficiarioAfiliado = 'ACTIVO'


	---------- DATOS BENEFICIARIO
	INNER JOIN Persona AS PERSONA   with(nolock) 
		ON PERSONA.perId = BENEFICIARIO.benPersona 
	left JOIN afiliado afiben on afiben.afiPersona = Persona.perid
	left join RolAfiliado rab on rab.roaAfiliado = afiben.afiId
	and rab.roaEstadoAfiliado= 'ACTIVO'
	left join 
		#tmpSolicitudes as sb
	     ON  rab.roaid = sb.sapRolAfiliado  
	 --  AND sb.sapRolAfiliado IS NOT NULL 
	INNER JOIN PersonaDetalle AS PERSONA_DET   with(nolock) 
		ON PERSONA_DET.pedPersona = Persona.perId 
		--AND DATEDIFF(YEAR, PERSONA_DET.pedFechaNacimiento, GETDATE() + 1) >= 18
		AND FLOOR(DATEDIFF(DAY, PERSONA_DET.pedFechaNacimiento, re.FECHA_FINAL) / 365.25) >= 18

	LEFT JOIN IntegranteHogar AS INTEGRANTE ON INTEGRANTE.inhPersona = PERSONA.perId

	INNER JOIN Parametro AS PARAMETRO ON PARAMETRO.prmNombre IN ('NUMERO_ID_CCF')
	INNER JOIN Parametro AS PARAMETRO_NOMBRE ON PARAMETRO_NOMBRE.prmNombre IN ('NOMBRE_CCF')

WHERE
	--rab.roaEstadoAfiliado = 'ACTIVO' and
	  ROLAFILIADO.roaEstadoAfiliado = 'ACTIVO'
	-- AND sb.solResultadoProceso IN ('APROBADA')
  --	 AND perAfiliado.perNumeroIdentificacion = '244820'
	 AND BENEFICIARIO.benFechaAfiliacion <= re.FECHA_FINAL
	 AND persona.perTipoIdentificacion IN ('CEDULA_CIUDADANIA', 'CEDULA_EXTRANJERIA','PERM_ESP_PERMANENCIA' ,'PERM_PROT_TEMPORAL')
	 AND perAfiliado.perTipoIdentificacion IN ('CEDULA_CIUDADANIA', 'CEDULA_EXTRANJERIA', 'TARJETA_IDENTIDAD','PERM_ESP_PERMANENCIA' ,'PERM_PROT_TEMPORAL')
	 AND solicitud.solClasificacion IN ( 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO', 'MENOS_1_5_SM_2_POR_CIENTO', 'MAS_1_5_SM_2_POR_CIENTO','TRABAJADOR_DEPENDIENTE','DEPENDIENTE' )
	--AND sb.solClasificacion IN ( 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO', 'MENOS_1_5_SM_2_POR_CIENTO', 'MAS_1_5_SM_2_POR_CIENTO','TRABAJADOR_DEPENDIENTE','DEPENDIENTE' )
	
	

GROUP BY 
	re.FECHA_FINAL,
	BENEFICIARIO.benTipoBeneficiario,
	PERSONA_DET.pedEstadoCivil,
	PERSONA.perTipoIdentificacion,
	INTEGRANTE.inhSalarioMensual,
	BENEFICIARIO.benFechaAfiliacion,
	PARAMETRO_NOMBRE.prmValor,
	PERSONA.perPrimerNombre,
	PERSONA.perSegundoNombre,
	PERSONA.perPrimerApellido,
	PERSONA.perSegundoApellido,
	PERSONA.perNumeroIdentificacion,
	PARAMETRO.prmValor,
	AFILIADO.afiId,
	--SOLICITUD_POF.solNumeroRadicacion,
	SOLICITUD.solNumeroRadicacion,
		rab.roaTipoAfiliado,
	sb.saprolafiliado
	) t where t.validacion = 1
	ORDER BY 2


-- se visualiza el reporte 
select 
[Fecha de corte de la información remitida]	
,(select count(*) from #TMP_Reporte17) [Cantidad de registros enviados de afiliados]
,[NIT Entidad]	
,[ID Hogar]	
,[Documento Afiliado]	
,[Apellidos del Afiliado]	
,[Nombres del Afiliado]	
,[Nombre Entidad]	
,[Fecha de afiliacion]	
,[Ingresos Afiliado]	
,[Tipo de documento]	
,[Estado Civil]	
,[Parentesco]
from #TMP_Reporte17 r

END
