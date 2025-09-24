-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 30 Junio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 2.
-- Reporte 2
-- Modificado por olga vega 2022-09-06--20230410
--Modificado Juan Ospina 2024-11-29 87960
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[reporteAfiliados] (
	@FECHA_INICIAL DATE,
	@FECHA_FINAL DATE,
	@idSolicitudReporte INT
	--,@TerminaReporte BIT output
)

AS
--DECLARE @FECHA_INICIAL DATE = '2024-10-01'
--DECLARE @FECHA_FINAL DATE = '2024-10-31'

BEGIN

	--set @TerminaReporte = 0
 
--/------------------------------------------------------------------------------\--
--                              REPORTE DE AFILIADOS  -  N° 2.
--\------------------------------------------------------------------------------/--

DROP TABLE IF EXISTS #SolicitudesAfiliaciones
DROP TABLE IF EXISTS #infoSalarios
DROP TABLE IF EXISTS #CategoriaAfiliado
DROP TABLE IF EXISTS #EsadoAfiliadoCalculado
DROP TABLE IF EXISTS #minimaestro
DROP TABLE IF EXISTS #BeneCuotaMonetaria
DROP TABLE IF EXISTS #tmpReporteAfiliado


-- SE OBTIENEN LAS SOLICITUDES DE AFILIACION  CON ESTADO ( APROBADA / CERRADA ) 
drop table if exists #SolicitudesAfiliaciones
SELECT 
CASE WHEN s.solId = MAX(s.solId) over (partition by snpRolAfiliado) then s.solId else null end [solId],
	snpRolAfiliado,
	s.solClasificacion,
	s.solResultadoProceso,
	sn.snoEstadoSolicitud,
	s.solTipoTransaccion,
	s.solNumeroRadicacion,
	s.solCanalRecepcion
into #SolicitudesAfiliaciones
FROM Solicitud s with (nolock)
inner join SolicitudNovedad sn with (nolock) on s.solId = sn.snoSolicitudGlobal
inner join SolicitudNovedadPersona snp with (nolock) on snp.snpSolicitudNovedad = sn.snoId
WHERE s.solTipoTransaccion in ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO',
'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
'NOVEDAD_REINTEGRO')
and s.solResultadoProceso = 'APROBADA' and sn.snoEstadoSolicitud = 'CERRADA'
UNION
SELECT  
	CASE WHEN s.solId = MAX(s.solId) over (partition by sapRolAfiliado) then s.solId else null end [solId],
	sapRolAfiliado,
	s.solClasificacion,
	s.solResultadoProceso,
	sap.sapEstadoSolicitud,
	s.solTipoTransaccion,
	s.solNumeroRadicacion,
	s.solCanalRecepcion
FROM Solicitud s  with (nolock)
inner join SolicitudAfiliacionPersona sap with (nolock) on s.solId = sap.sapSolicitudGlobal
WHERE s.solTipoTransaccion in ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO',
'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO',
'NOVEDAD_REINTEGRO')
and s.solResultadoProceso = 'APROBADA' and sap.sapEstadoSolicitud = 'CERRADA'

-- se dejan solo las ultimas afiliaciones de cada persona
delete from  #SolicitudesAfiliaciones where solId IS NULL

-- DECLARE @FECHA_INICIAL DATE = '2025-03-01', @FECHA_FINAL   DATE = '2025-03-31'
-- SE CARGAN LOS ULTIMOS SALARIOS REPORTADOS DENTRO DEL RANGO DEL REPORTE
drop table if exists #infoSalarios
-- muestra la información salarial de todos (dependiente, independientes, pensionados)
select distinct apd.apdSalarioBasico,apg.apgPeriodoAporte,apg.apgEmpresa,apd.apdPersona --,apd.apdDiasCotizados,apd.apdHorasLaboradas
into #infoSalarios
from Persona p with (nolock)
inner join AporteDetallado2 apd with (nolock) on apd.apdPersona = p.perId
inner join AporteGeneral2 apg with (nolock) on apg.apgId = apd.apdAporteGeneral
where apg.apgPeriodoAporte between  CAST(@FECHA_INICIAL AS varchar(7))  and CAST(@FECHA_FINAL AS varchar(7))
UNION
-- Muestra la informacion salarial de los dependientes
select apdSalarioBasico,	apgPeriodoAporte,	dsaEmpleador,	apdPersona from UltimoSueldoTrab 
where apgPeriodoAporte between  CAST(@FECHA_INICIAL AS varchar(7))  and CAST(@FECHA_FINAL AS varchar(7))


-- SE OBTIENE LA INFORMACIÓN CON RESPECTO A LAS CATEGORIAS DE LOS AGILIADOS
drop table if exists #CategoriaAfiliado

select ctaId,ctaAfiliado,ctaCategoria,ctaClasificacion,a.afiPersona,
CASE WHEN cta.ctaFechaCambioCategoria = MAX(cta.ctaFechaCambioCategoria) OVER (PARTITION BY ctaAfiliado) THEN  cta.ctaFechaCambioCategoria ELSE NULL END [ctaFechaCambioCategoria]
into #CategoriaAfiliado
from CategoriaAfiliado as cta with(nolock)
inner join Afiliado a on a.afiId = ctaAfiliado
order by cta.ctaAfiliado, cta.ctaFechaCambioCategoria desc


-- SE OBTIENE  LOS ULTIMOS ESTADO CALCULADOS DE LOS AFILIADOS
--DECLARE  @FECHA_INICIAL DATE = '2025-03-01', @FECHA_FINAL DATE = '2025-03-31'

drop table if exists #EsadoAfiliadoCalculado
select 
	ra.roaId,ra.roaEmpleador,ra.roaAfiliado,ra.roaEstadoAfiliado,ra.roaFechaAfiliacion,ra.roaFechaRetiro, @FECHA_INICIAL [FECHA_INICIAL], @FECHA_FINAL [FECHA_FINAL],
	CASE 
		WHEN ra.roaEstadoAfiliado = 'INACTIVO' and ra.roaFechaRetiro < @FECHA_INICIAL THEN 'INACTIVO' -- INACTIVOS RETIRADOS ANTES DE LA FECHA INICIAL DEL REPORTE
		WHEN ra.roaEstadoAfiliado = 'INACTIVO' and ra.roaFechaRetiro > @FECHA_FINAL THEN 'ACTIVO' -- INACTIVOS RETIRADOS ANTES DE LA FECHA INICIAL DEL REPORTE
		WHEN ra.roaEstadoAfiliado = 'INACTIVO' and ra.roaFechaRetiro BETWEEN @FECHA_INICIAL AND @FECHA_FINAL THEN ra.roaEstadoAfiliado -- PARA LOS INACTIVOS DENTRO DEL MISMO RANGO DEL REPORTE SE DEJA EL ULTIMO ESTADO DEL AFILIADO
	ELSE ra.roaEstadoAfiliado
 	END  [ESTADO_AFILIADO_CALCULADO]
into #EsadoAfiliadoCalculado
from RolAfiliado ra 


----POBLACION DEL SUBSIDIO
--DECLARE  @FECHA_INICIAL DATE = '2025-03-01', @FECHA_FINAL DATE = '2025-03-31'
IF OBJECT_ID('tempdb.dbo.#minimaestro', 'U') IS NOT NULL
DROP TABLE #minimaestro 

SELECT * 
INTO #minimaestro
FROM MaestroLiquidacion AS dsaSubsidioAfiliado with (nolock)
WHERE convert(date, dsaSubsidioAfiliado.slsFechaDispersion) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	AND dsaSubsidioAfiliado.casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
	AND dsaSubsidioAfiliado.solResultadoProceso = 'DISPERSADA' 
	AND dsaSubsidioAfiliado.dsaEstado = 'DERECHO_ASIGNADO' 
	AND dsaSubsidioAfiliado.casTipoTransaccionSubsidio = 'ABONO'

CREATE CLUSTERED INDEX IND_LLAVES ON #minimaestro (casId, dsaId)
CREATE NONCLUSTERED INDEX IND2_LLAVES2 ON #minimaestro (dsaempleador, dsaafiliadoprincipal, dsabeneficiariodetalle)
CREATE NONCLUSTERED INDEX IND3_LLAVES3 ON #minimaestro (slsid,slsFechaDispersion)

drop table if exists #BeneCuotaMonetaria
select a.afiId,ra.roaId, IIF(ISNULL((MAX(mm.dsaid)), 0) > 0, '1', '2') AS [BEN_CUOTA_MONETARIA]
into #BeneCuotaMonetaria
from afiliado a
inner join RolAfiliado ra on a.afiId = ra.roaAfiliado
inner join #minimaestro mm with (nolock) on mm.dsaAfiliadoPrincipal = a.afiId and mm.dsaEmpleador = ra.roaEmpleador 	
group by a.afiId,ra.roaId



----POBLACION DEL SUBSIDIO
--DECLARE  @FECHA_INICIAL DATE = '2025-03-01', @FECHA_FINAL DATE = '2025-03-31'
declare @parametroTiempoAdicionalServicios int 
	
select @parametroTiempoAdicionalServicios = cast(prmValor as int)
from Parametro 
where prmnombre = 'TIEMPO_ADICIONAL_SERVICIOS_CAJA'

--TODO: En Confa el reporte se demora en generar 1 min 54 seg.

/*
* PARA PARA LA POBLACIÓN DE AFILIADOS ACTIVOS DEPENDIENTES
*/
DELETE TablaReporteAfiliados
INSERT INTO TablaReporteAfiliados
SELECT  DISTINCT
	CASE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
			ELSE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
	END AS [TIP_IDENTIFICACION_EMPRESA],
	ISNULL(PE.perNumeroIdentificacion, pa.perNumeroIdentificacion) [NUM_IDENTIFICACION_EMPRESA],
	--Tipo de Identificación Afiliado--	
	CASE pa.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
		ELSE pa.perTipoIdentificacion 
	END [TIP_IDENTIFICACION_AFILIADO],
	--Número de Identificación Afiliado--
	pa.perNumeroIdentificacion [NUM_IDENTIFICACION_AFILIADO],
	--Primer Nombre Afiliado--
	CASE WHEN pa.perPrimerNombre IS NULL THEN '' ELSE LEFT (pa.perPrimerNombre,30) END [PRI_NOMBRE],
	--SegundoNombre Afiliado--
	CASE WHEN pa.perSegundoNombre IS NULL THEN '' ELSE LEFT (pa.perSegundoNombre,30)  END [SEG_NOMBRE],
	--Primer Apellido Afiliado--
	CASE WHEN pa.perPrimerApellido IS NULL THEN '' ELSE  LEFT (pa.perPrimerApellido,30)  END  [PRI_APELLIDO],
	--Segundo Apellido Afiliado--
	CASE WHEN pa.perSegundoApellido is null THEN '' ELSE  LEFT (pa.perSegundoApellido,30)  END [SEG_APELLIDO],
	--Fecha Nacimiento--
	CONVERT (VARCHAR,pd.pedFechaNacimiento,112) [FEC_NACIMIENTO],
	--Sexo--
	ISNULL (CASE pd.pedGenero WHEN 'MASCULINO'  THEN '1'
							  WHEN 'FEMENINO'   THEN '2'							  
							  WHEN 'INDEFINIDO' THEN '4' END, '3') [GENERO_CCF],
	--Orientacion Sexual--
	CASE pd.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1 
								 WHEN 'HOMOSEXUAL'   THEN 2
								 WHEN 'BISEXUAL'     THEN 3 ELSE 4 END [ORI_SEXUAL],
	--Nivel Escolaridad--
	CASE WHEN 
		pd.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS','BASICA_PRIMARIA_INCOMPLETA_ADULTOS') THEN 6
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA_ADULTOS','BASICA_SECUNDARIA_INCOMPLETA_ADULTOS') THEN 7
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS','MEDIA_INCOMPLETA_ADULTOS') THEN 8
		WHEN pd.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN pd.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN pd.pedNivelEducativo in ( 'SUPERIOR', 'SUPERIOR_PREGRADO') THEN 11
		WHEN pd.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 12
		WHEN pd.pedNivelEducativo = 'NINGUNO' THEN 13
		ELSE 14 
	END [NIVEL_ESCOLARIDAD],
	--Codigo de Ocupacion--
	CASE 
		WHEN pd.pedOcupacionProfesion IS NULL THEN '9629'
		ELSE CONVERT(VARCHAR(4),(op.ocuCodigo))
	END AS [COD_OCUPACION_DANE],
	--Factor de Vulnerabilidad--
	CASE 
		WHEN pd.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN pd.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN pd.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN pd.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN pd.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN pd.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN pd.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN pd.pedFactorVulnerabilidad IN ('NO_APLICA') THEN 12 
		WHEN pd.pedFactorVulnerabilidad IS NULL THEN 13
		WHEN pd.pedFactorVulnerabilidad IN ('') THEN 13
	END [FACTOR_VULNERABILIDAD],
	--Estado Civi--
	CASE pd.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN '1'
		WHEN 'CASADO' THEN '2'
		WHEN 'DIVORCIADO' THEN '3'
		WHEN 'SEPARADO' THEN '4'
		WHEN 'VIUDO' THEN '5'
		WHEN 'SOLTERO' THEN '6'
	ELSE '' 
	END [ESTADO_CIVIL],
	--Pertenencia étnica--
	CASE pd.pedPertenenciaEtnica 
		WHEN 'AFROCOLOMBIANO' THEN 1
		WHEN 'COMUNIDAD_NEGRA' THEN 2
		WHEN 'INDIGENA' THEN 3
		WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		ELSE 8 
	END [PERTENENCIA_ETNICA],
	--resguardo--
	ISNULL(pd.pedResguardo,2) [RESGUARDO],
	--pueblo indigena--
	ISNULL(pedPuebloIndigena,2) [PUEBLO_INDIGENA],
    --Nombre Pais--
	CASE WHEN pais.paiCodigo IS NULL OR pais.paiCodigo = '' THEN 170 ELSE pais.paiCodigo END [PAIS_RESIDENCIA_BENEFICIARIO],
	--Codigo Municipio--
	munCodigo [COD_MUNICIPIO_DANE],
	--Area Rural--
	CASE WHEN pd.pedResideSectorRural=1 THEN '2' ELSE '1' END  [ARE_GEOGRAFICA_RESIDENCIA],
	--Codigo Municipio Labor--
	munCodigo [COD_MUNICIPIO_LABOR_DANE],
	--Area Geografica Labor--
	CASE 
		WHEN pd.pedResideSectorRural=1 THEN '2' 
		ELSE '1'
	END  [AREA_GEOGRAFICA_LABOR],
	--Salario--
	CASE
		WHEN REPLACE (( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona and sal.apgEmpresa = epr.empId ),'.00000','') IS NULL
		THEN SUBSTRING(REPLACE (ra.roaValorSalarioMesadaIngresos,'.00000',''),0,18)
		ELSE SUBSTRING(REPLACE ( ( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona ),'.00000',''),0,18) 
	END AS [SAL_BASICO],
		----------Tipo Afiliado----------
		CASE 
			WHEN 
				ra.roaTipoAfiliado  = 'TRABAJADOR_DEPENDIENTE' AND 
				ra.roaClaseTrabajador != 'MADRE_COMUNITARIA' AND
				ra.roaClaseTrabajador != 'SERVICIO_DOMESTICO' --AND
				--dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio != 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
				--cat.ctaCategoria != 'C'
			THEN '1'
			WHEN ra.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN ra.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') 
			THEN '4'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO') 
			THEN '5'
			WHEN ra.roaTipoAfiliado = 'FACULTATIVO' 
			THEN '6'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '7'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '8'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				sa.solClasificacion = 'FIDELIDAD_25_ANIOS' 
			THEN '9'
			WHEN ra.ROATIPOAFILIADO = 'PENSIONADO' AND sa.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' 
			THEN '11'
			WHEN ra.roaClaseIndependiente IN ('TAXISTA') 
			THEN '12'
			--WHEN 
			--	(--cat.ctaCategoria = 'C' AND 
			--		dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
			--		pr.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') 
			--THEN '13'
			ELSE '1'
		END AS [TIP_AFILIADO],
		--Categoria--
		CASE cta.ctacategoria 
			WHEN 'A' THEN '1'
			WHEN 'B' THEN '2'
			WHEN 'C' THEN '3'
			WHEN 'SIN_CATEGORIA' THEN 4
			ELSE 4
		END  AS [CATEGORIA_CCF],
		--Beneficiario de cuota monetaria--
		CASE 
			WHEN bcm.[BEN_CUOTA_MONETARIA] = 1 THEN '1' ELSE '2' END [BEN_CUOTA_MONETARIA],
		--Actividad económica principal--
		ISNULL(( SELECT ciicodigo FROM codigociiu WHERE ciiid = epr.empCodigoCIIU ),'0000') [ACTIVIDAD_ECONOMICA_PINCIPAL]
--SELECT pa.perTipoIdentificacion,pa.perNumeroIdentificacion,pa.perRazonSocial,ra.roaId,ra.roaEstadoAfiliado,ra.roaTipoAfiliado,cta.ctacategoria 
FROM PERSONA pa WITH(NOLOCK)
inner join Afiliado a WITH(NOLOCK) on pa.perId = a.afiPersona 
inner join RolAfiliado ra WITH(NOLOCK) on ra.roaAfiliado = a.afiId 
inner join #SolicitudesAfiliaciones sa WITH(NOLOCK) on ra.roaId = sa.snpRolAfiliado
inner join #EsadoAfiliadoCalculado eac WITH(NOLOCK) on (eac.roaId = ra.roaId) and (eac.ESTADO_AFILIADO_CALCULADO = 'ACTIVO')
inner join #CategoriaAfiliado cta WITH(NOLOCK) on cta.ctaAfiliado = a.afiId and cta.ctaClasificacion = ra.roaTipoAfiliado
left  join Empleador epl WITH(NOLOCK) on epl.empId = ra.roaEmpleador
left  join Empresa epr WITH(NOLOCK) on epr.empId = epl.empEmpresa
left  join Persona prl on prl.perId = epr.empRepresentanteLegal
left  join Persona pe WITH(NOLOCK) on pe.perId = epr.empPersona
left  join PersonaDetalle pd on pd.pedPersona = pa.perId
left  join Pais pais on pais.paiId = pd.pedPaisResidencia
left  join Ubicacion ubi on ubi.ubiId = pa.perUbicacionPrincipal
left  join Municipio mun on mun.munId = ubi.ubiMunicipio
left  join OcupacionProfesion op on op.ocuId = pd.pedOcupacionProfesion
left  join #BeneCuotaMonetaria bcm on bcm.afiId = a.afiId and bcm.roaId = ra.roaId	
WHERE pe.perNumeroIdentificacion not in ('99999999') and pa.perNumeroIdentificacion not in ('99999999')
	 --and pa.perNumeroIdentificacion in ('75091491','75144772','75106629') 
	AND ra.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
UNION
/*
* PARA PARA LA POBLACIÓN DE AFILIADOS INACTIVOS PERO QUE AUN TIENEN SERVICIOS DE AFILIACION CAJA 
*/
SELECT  DISTINCT
	CASE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
			ELSE ISNULL(pe.perTipoIdentificacion , pa.perTipoIdentificacion)
	END AS [TIP_IDENTIFICACION_EMPRESA],
	ISNULL(PE.perNumeroIdentificacion, pa.perNumeroIdentificacion) [NUM_IDENTIFICACION_EMPRESA],
	--Tipo de Identificación Afiliado--	
	CASE pa.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
		ELSE pa.perTipoIdentificacion 
	END [TIP_IDENTIFICACION_AFILIADO],
	--Número de Identificación Afiliado--
	pa.perNumeroIdentificacion [NUM_IDENTIFICACION_AFILIADO],
	--Primer Nombre Afiliado--
	CASE WHEN pa.perPrimerNombre IS NULL THEN '' ELSE LEFT (pa.perPrimerNombre,30) END [PRI_NOMBRE],
	--SegundoNombre Afiliado--
	CASE WHEN pa.perSegundoNombre IS NULL THEN '' ELSE LEFT (pa.perSegundoNombre,30)  END [SEG_NOMBRE],
	--Primer Apellido Afiliado--
	CASE WHEN pa.perPrimerApellido IS NULL THEN '' ELSE  LEFT (pa.perPrimerApellido,30)  END  [PRI_APELLIDO],
	--Segundo Apellido Afiliado--
	CASE WHEN pa.perSegundoApellido is null THEN '' ELSE  LEFT (pa.perSegundoApellido,30)  END [SEG_APELLIDO],
	--Fecha Nacimiento--
	CONVERT (VARCHAR,pd.pedFechaNacimiento,112) [FEC_NACIMIENTO],
	--Sexo--
	ISNULL (CASE pd.pedGenero WHEN 'MASCULINO'  THEN '1'
							  WHEN 'FEMENINO'   THEN '2'							  
							  WHEN 'INDEFINIDO' THEN '4' END, '3') [GENERO_CCF],
	--Orientacion Sexual--
	CASE pd.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1 
								 WHEN 'HOMOSEXUAL'   THEN 2
								 WHEN 'BISEXUAL'     THEN 3 ELSE 4 END [ORI_SEXUAL],
	--Nivel Escolaridad--
	CASE WHEN 
		pd.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS','BASICA_PRIMARIA_INCOMPLETA_ADULTOS') THEN 6
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA_ADULTOS','BASICA_SECUNDARIA_INCOMPLETA_ADULTOS') THEN 7
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS','MEDIA_INCOMPLETA_ADULTOS') THEN 8
		WHEN pd.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN pd.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN pd.pedNivelEducativo in ( 'SUPERIOR', 'SUPERIOR_PREGRADO') THEN 11
		WHEN pd.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 12
		WHEN pd.pedNivelEducativo = 'NINGUNO' THEN 13
		ELSE 14 
	END [NIVEL_ESCOLARIDAD],
	--Codigo de Ocupacion--
	CASE 
		WHEN pd.pedOcupacionProfesion IS NULL THEN '9629'
		ELSE CONVERT(VARCHAR(4),(op.ocuCodigo))
	END AS [COD_OCUPACION_DANE],
	--Factor de Vulnerabilidad--
	CASE 
		WHEN pd.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN pd.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN pd.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN pd.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN pd.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN pd.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN pd.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN pd.pedFactorVulnerabilidad IN ('NO_APLICA') THEN 12 
		WHEN pd.pedFactorVulnerabilidad IS NULL THEN 13
		WHEN pd.pedFactorVulnerabilidad IN ('') THEN 13
	END [FACTOR_VULNERABILIDAD],
	--Estado Civi--
	CASE pd.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN '1'
		WHEN 'CASADO' THEN '2'
		WHEN 'DIVORCIADO' THEN '3'
		WHEN 'SEPARADO' THEN '4'
		WHEN 'VIUDO' THEN '5'
		WHEN 'SOLTERO' THEN '6'
	ELSE '' 
	END [ESTADO_CIVIL],
	--Pertenencia étnica--
	CASE pd.pedPertenenciaEtnica 
		WHEN 'AFROCOLOMBIANO' THEN 1
		WHEN 'COMUNIDAD_NEGRA' THEN 2
		WHEN 'INDIGENA' THEN 3
		WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		ELSE 8 
	END [PERTENENCIA_ETNICA],
	--resguardo--
	ISNULL(pd.pedResguardo,2) [RESGUARDO],
	--pueblo indigena--
	ISNULL(pedPuebloIndigena,2) [PUEBLO_INDIGENA],
    --Nombre Pais--
	CASE WHEN pais.paiCodigo IS NULL OR pais.paiCodigo = '' THEN 170 ELSE pais.paiCodigo END [PAIS_RESIDENCIA_BENEFICIARIO],
	--Codigo Municipio--
	munCodigo [COD_MUNICIPIO_DANE],
	--Area Rural--
	CASE WHEN pd.pedResideSectorRural=1 THEN '2' ELSE '1' END  [ARE_GEOGRAFICA_RESIDENCIA],
	--Codigo Municipio Labor--
	munCodigo [COD_MUNICIPIO_LABOR_DANE],
	--Area Geografica Labor--
	CASE 
		WHEN pd.pedResideSectorRural=1 THEN '2' 
		ELSE '1'
	END  [AREA_GEOGRAFICA_LABOR],
	--Salario--
	CASE
		WHEN REPLACE (( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona and sal.apgEmpresa = epr.empId ),'.00000','') IS NULL
		THEN SUBSTRING(REPLACE (ra.roaValorSalarioMesadaIngresos,'.00000',''),0,18)
		ELSE SUBSTRING(REPLACE ( ( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona ),'.00000',''),0,18) 
	END AS [SAL_BASICO],
		----------Tipo Afiliado----------
		CASE 
			WHEN 
				ra.roaTipoAfiliado  = 'TRABAJADOR_DEPENDIENTE' AND 
				ra.roaClaseTrabajador != 'MADRE_COMUNITARIA' AND
				ra.roaClaseTrabajador != 'SERVICIO_DOMESTICO' --AND
				--dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio != 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
				--cat.ctaCategoria != 'C'
			THEN '1'
			WHEN ra.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN ra.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') 
			THEN '4'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO') 
			THEN '5'
			WHEN ra.roaTipoAfiliado = 'FACULTATIVO' 
			THEN '6'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '7'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '8'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				sa.solClasificacion = 'FIDELIDAD_25_ANIOS' 
			THEN '9'
			WHEN ra.ROATIPOAFILIADO = 'PENSIONADO' AND sa.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' 
			THEN '11'
			WHEN ra.roaClaseIndependiente IN ('TAXISTA') 
			THEN '12'
			--WHEN 
			--	(--cat.ctaCategoria = 'C' AND 
			--		dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
			--		pr.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') 
			--THEN '13'
			ELSE '1'
		END AS [TIP_AFILIADO],
		--Categoria--
		CASE cta.ctacategoria 
			WHEN 'A' THEN '1'
			WHEN 'B' THEN '2'
			WHEN 'C' THEN '3'
			WHEN 'SIN_CATEGORIA' THEN 4
			ELSE 4
		END  AS [CATEGORIA_CCF],
		--Beneficiario de cuota monetaria--
		CASE 
			WHEN bcm.[BEN_CUOTA_MONETARIA] = 1 THEN '1' ELSE '2' END [BEN_CUOTA_MONETARIA],
		--Actividad económica principal--
		ISNULL(( SELECT ciicodigo FROM codigociiu WHERE ciiid = epr.empCodigoCIIU ),'0000') [ACTIVIDAD_ECONOMICA_PINCIPAL]

FROM PERSONA pa WITH(NOLOCK)
inner join Afiliado a WITH(NOLOCK) on pa.perId = a.afiPersona
inner join RolAfiliado ra WITH(NOLOCK) on ra.roaAfiliado = a.afiId
inner join #SolicitudesAfiliaciones sa WITH(NOLOCK) on ra.roaId = sa.snpRolAfiliado and datediff(day,ra.roaFechaRetiro,@FECHA_FINAL) < @parametroTiempoAdicionalServicios
inner join #EsadoAfiliadoCalculado eac WITH(NOLOCK) on (eac.roaId = ra.roaId) and (eac.ESTADO_AFILIADO_CALCULADO = 'INACTIVO')
inner join #CategoriaAfiliado cta WITH(NOLOCK) on cta.ctaAfiliado = a.afiId and cta.ctaClasificacion = ra.roaTipoAfiliado
left  join Empleador epl WITH(NOLOCK) on epl.empId = ra.roaEmpleador
left  join Empresa epr WITH(NOLOCK) on epr.empId = epl.empEmpresa
left  join Persona prl on prl.perId = epr.empRepresentanteLegal
left  join Persona pe WITH(NOLOCK) on pe.perId = epr.empPersona
left  join PersonaDetalle pd on pd.pedPersona = pa.perId
left  join Pais pais on pais.paiId = pd.pedPaisResidencia
left  join Ubicacion ubi on ubi.ubiId = pa.perUbicacionPrincipal
left  join Municipio mun on mun.munId = ubi.ubiMunicipio
left  join OcupacionProfesion op on op.ocuId = pd.pedOcupacionProfesion
left  join #BeneCuotaMonetaria bcm on bcm.afiId = a.afiId and bcm.roaId = ra.roaId	
WHERE pe.perNumeroIdentificacion not in ('99999999') and pa.perNumeroIdentificacion not in ('99999999')
	 --and pa.perNumeroIdentificacion in ('75091491','75144772','75106629') 
	AND ra.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'

UNION

/*
* PARA PARA LA POBLACIÓN DE AFILIADOS ACTIVOS INDEPENDIENTE Y PENSIONADO
*/
SELECT  DISTINCT
	CASE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
			ELSE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
	END AS [TIP_IDENTIFICACION_EMPRESA],
	ISNULL(PE.perNumeroIdentificacion, pa.perNumeroIdentificacion) [NUM_IDENTIFICACION_EMPRESA],
	--Tipo de Identificación Afiliado--	
	CASE pa.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
		ELSE pa.perTipoIdentificacion 
	END [TIP_IDENTIFICACION_AFILIADO],
	--Número de Identificación Afiliado--
	pa.perNumeroIdentificacion [NUM_IDENTIFICACION_AFILIADO],
	--Primer Nombre Afiliado--
	CASE WHEN pa.perPrimerNombre IS NULL THEN '' ELSE LEFT (pa.perPrimerNombre,30) END [PRI_NOMBRE],
	--SegundoNombre Afiliado--
	CASE WHEN pa.perSegundoNombre IS NULL THEN '' ELSE LEFT (pa.perSegundoNombre,30)  END [SEG_NOMBRE],
	--Primer Apellido Afiliado--
	CASE WHEN pa.perPrimerApellido IS NULL THEN '' ELSE  LEFT (pa.perPrimerApellido,30)  END  [PRI_APELLIDO],
	--Segundo Apellido Afiliado--
	CASE WHEN pa.perSegundoApellido is null THEN '' ELSE  LEFT (pa.perSegundoApellido,30)  END [SEG_APELLIDO],
	--Fecha Nacimiento--
	CONVERT (VARCHAR,pd.pedFechaNacimiento,112) [FEC_NACIMIENTO],
	--Sexo--
	ISNULL (CASE pd.pedGenero WHEN 'MASCULINO'  THEN '1'
							  WHEN 'FEMENINO'   THEN '2'							  
							  WHEN 'INDEFINIDO' THEN '4' END, '3') [GENERO_CCF],
	--Orientacion Sexual--
	CASE pd.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1 
								 WHEN 'HOMOSEXUAL'   THEN 2
								 WHEN 'BISEXUAL'     THEN 3 ELSE 4 END [ORI_SEXUAL],
	--Nivel Escolaridad--
	CASE WHEN 
		pd.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS','BASICA_PRIMARIA_INCOMPLETA_ADULTOS') THEN 6
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA_ADULTOS','BASICA_SECUNDARIA_INCOMPLETA_ADULTOS') THEN 7
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS','MEDIA_INCOMPLETA_ADULTOS') THEN 8
		WHEN pd.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN pd.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN pd.pedNivelEducativo in ( 'SUPERIOR', 'SUPERIOR_PREGRADO') THEN 11
		WHEN pd.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 12
		WHEN pd.pedNivelEducativo = 'NINGUNO' THEN 13
		ELSE 14 
	END [NIVEL_ESCOLARIDAD],
	--Codigo de Ocupacion--
	CASE 
		WHEN pd.pedOcupacionProfesion IS NULL THEN '9629'
		ELSE CONVERT(VARCHAR(4),(op.ocuCodigo))
	END AS [COD_OCUPACION_DANE],
	--Factor de Vulnerabilidad--
	CASE 
		WHEN pd.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN pd.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN pd.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN pd.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN pd.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN pd.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN pd.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN pd.pedFactorVulnerabilidad IN ('NO_APLICA') THEN 12 
		WHEN pd.pedFactorVulnerabilidad IS NULL THEN 13
		WHEN pd.pedFactorVulnerabilidad IN ('') THEN 13
	END [FACTOR_VULNERABILIDAD],
	--Estado Civi--
	CASE pd.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN '1'
		WHEN 'CASADO' THEN '2'
		WHEN 'DIVORCIADO' THEN '3'
		WHEN 'SEPARADO' THEN '4'
		WHEN 'VIUDO' THEN '5'
		WHEN 'SOLTERO' THEN '6'
	ELSE '' 
	END [ESTADO_CIVIL],
	--Pertenencia étnica--
	CASE pd.pedPertenenciaEtnica 
		WHEN 'AFROCOLOMBIANO' THEN 1
		WHEN 'COMUNIDAD_NEGRA' THEN 2
		WHEN 'INDIGENA' THEN 3
		WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		ELSE 8 
	END [PERTENENCIA_ETNICA],
	--resguardo--
	ISNULL(pd.pedResguardo,2) [RESGUARDO],
	--pueblo indigena--
	ISNULL(pedPuebloIndigena,2) [PUEBLO_INDIGENA],
    --Nombre Pais--
	CASE WHEN pais.paiCodigo IS NULL OR pais.paiCodigo = '' THEN 170 ELSE pais.paiCodigo END [PAIS_RESIDENCIA_BENEFICIARIO],
	--Codigo Municipio--
	munCodigo [COD_MUNICIPIO_DANE],
	--Area Rural--
	CASE WHEN pd.pedResideSectorRural=1 THEN '2' ELSE '1' END  [ARE_GEOGRAFICA_RESIDENCIA],
	--Codigo Municipio Labor--
	munCodigo [COD_MUNICIPIO_LABOR_DANE],
	--Area Geografica Labor--
	CASE 
		WHEN pd.pedResideSectorRural=1 THEN '2' 
		ELSE '1'
	END  [AREA_GEOGRAFICA_LABOR],
	--Salario--
	CASE
		WHEN REPLACE (( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona and sal.apgEmpresa = epr.empId ),'.00000','') IS NULL
		THEN SUBSTRING(REPLACE (ra.roaValorSalarioMesadaIngresos,'.00000',''),0,18)
		ELSE SUBSTRING(REPLACE ( ( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona ),'.00000',''),0,18) 
	END AS [SAL_BASICO],
		----------Tipo Afiliado----------
		CASE 
			WHEN 
				ra.roaTipoAfiliado  = 'TRABAJADOR_DEPENDIENTE' AND 
				ra.roaClaseTrabajador != 'MADRE_COMUNITARIA' AND
				ra.roaClaseTrabajador != 'SERVICIO_DOMESTICO' --AND
				--dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio != 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
				--cat.ctaCategoria != 'C'
			THEN '1'
			WHEN ra.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN ra.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') 
			THEN '4'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO') 
			THEN '5'
			WHEN ra.roaTipoAfiliado = 'FACULTATIVO' 
			THEN '6'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '7'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '8'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				sa.solClasificacion = 'FIDELIDAD_25_ANIOS' 
			THEN '9'
			WHEN ra.ROATIPOAFILIADO = 'PENSIONADO' AND sa.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' 
			THEN '11'
			WHEN ra.roaClaseIndependiente IN ('TAXISTA') 
			THEN '12'
			--WHEN 
			--	(--cat.ctaCategoria = 'C' AND 
			--		dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
			--		pr.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') 
			--THEN '13'
			ELSE '1'
		END AS [TIP_AFILIADO],
		--Categoria--
		CASE cta.ctacategoria 
			WHEN 'A' THEN '1'
			WHEN 'B' THEN '2'
			WHEN 'C' THEN '3'
			WHEN 'SIN_CATEGORIA' THEN 4
			ELSE 4
		END  AS [CATEGORIA_CCF],
		--Beneficiario de cuota monetaria--
		CASE 
			WHEN bcm.[BEN_CUOTA_MONETARIA] = 1 THEN '1' ELSE '2' END [BEN_CUOTA_MONETARIA],
		--Actividad económica principal--
		ISNULL(( SELECT ciicodigo FROM codigociiu WHERE ciiid = epr.empCodigoCIIU ),'0000') [ACTIVIDAD_ECONOMICA_PINCIPAL]

FROM PERSONA pa WITH(NOLOCK)
inner  join Afiliado a WITH(NOLOCK) on pa.perId = a.afiPersona
inner  join RolAfiliado ra WITH(NOLOCK) on ra.roaAfiliado = a.afiId and (ra.roaEmpleador IS NULL)
inner  join #SolicitudesAfiliaciones sa WITH(NOLOCK) on ra.roaId = sa.snpRolAfiliado
inner  join #EsadoAfiliadoCalculado eac WITH(NOLOCK) on (eac.roaId = ra.roaId) and (eac.ESTADO_AFILIADO_CALCULADO = 'ACTIVO')
inner  join #CategoriaAfiliado cta WITH(NOLOCK) on cta.ctaAfiliado = a.afiId and cta.ctaClasificacion = ra.roaTipoAfiliado
left   join Empleador epl WITH(NOLOCK) on epl.empId = ra.roaEmpleador
left  join Empresa epr WITH(NOLOCK) on epr.empId = epl.empEmpresa
left  join Persona prl on prl.perId = epr.empRepresentanteLegal
left  join Persona pe WITH(NOLOCK) on pe.perId = epr.empPersona
left  join PersonaDetalle pd on pd.pedPersona = pa.perId
left  join Pais pais on pais.paiId = pd.pedPaisResidencia
left  join Ubicacion ubi on ubi.ubiId = pa.perUbicacionPrincipal
left  join Municipio mun on mun.munId = ubi.ubiMunicipio
left  join OcupacionProfesion op on op.ocuId = pd.pedOcupacionProfesion
left  join #BeneCuotaMonetaria bcm on bcm.afiId = a.afiId and bcm.roaId = ra.roaId	
WHERE ra.roaTipoAfiliado IN('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
--AND pa.perNumeroIdentificacion in ('75091491','75144772','75106629') 
UNION
/*
* PARA PARA LA POBLACIÓN DE AFILIADOS INACTIVOS PERO QUE AUN TIENEN SERVICIOS DE AFILIACION CAJA 
*/
SELECT  DISTINCT
	CASE ISNULL(PE.perTipoIdentificacion , pa.perTipoIdentificacion)
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
			ELSE ISNULL(pe.perTipoIdentificacion , pa.perTipoIdentificacion)
	END AS [TIP_IDENTIFICACION_EMPRESA],
	ISNULL(PE.perNumeroIdentificacion, pa.perNumeroIdentificacion) [NUM_IDENTIFICACION_EMPRESA],
	--Tipo de Identificación Afiliado--	
	CASE pa.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'CERTIFICADO_CABILDO' THEN '10'
		WHEN 'IDENTIFICACION_SECRETARIA_EDUCIACION' THEN '11'
		WHEN 'TARJETA_MOVILIDAD_FRONTERIZA' THEN '12'
		WHEN 'VISA' THEN '13'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'SALVOCONDUCTO'
		ELSE pa.perTipoIdentificacion 
	END [TIP_IDENTIFICACION_AFILIADO],
	--Número de Identificación Afiliado--
	pa.perNumeroIdentificacion [NUM_IDENTIFICACION_AFILIADO],
	--Primer Nombre Afiliado--
	CASE WHEN pa.perPrimerNombre IS NULL THEN '' ELSE LEFT (pa.perPrimerNombre,30) END [PRI_NOMBRE],
	--SegundoNombre Afiliado--
	CASE WHEN pa.perSegundoNombre IS NULL THEN '' ELSE LEFT (pa.perSegundoNombre,30)  END [SEG_NOMBRE],
	--Primer Apellido Afiliado--
	CASE WHEN pa.perPrimerApellido IS NULL THEN '' ELSE  LEFT (pa.perPrimerApellido,30)  END  [PRI_APELLIDO],
	--Segundo Apellido Afiliado--
	CASE WHEN pa.perSegundoApellido is null THEN '' ELSE  LEFT (pa.perSegundoApellido,30)  END [SEG_APELLIDO],
	--Fecha Nacimiento--
	CONVERT (VARCHAR,pd.pedFechaNacimiento,112) [FEC_NACIMIENTO],
	--Sexo--
	ISNULL (CASE pd.pedGenero WHEN 'MASCULINO'  THEN '1'
							  WHEN 'FEMENINO'   THEN '2'							  
							  WHEN 'INDEFINIDO' THEN '4' END, '3') [GENERO_CCF],
	--Orientacion Sexual--
	CASE pd.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1 
								 WHEN 'HOMOSEXUAL'   THEN 2
								 WHEN 'BISEXUAL'     THEN 3 ELSE 4 END [ORI_SEXUAL],
	--Nivel Escolaridad--
	CASE WHEN 
		pd.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN pd.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS','BASICA_PRIMARIA_INCOMPLETA_ADULTOS') THEN 6
		WHEN pd.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA_ADULTOS','BASICA_SECUNDARIA_INCOMPLETA_ADULTOS') THEN 7
		WHEN pd.pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS','MEDIA_INCOMPLETA_ADULTOS') THEN 8
		WHEN pd.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN pd.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN pd.pedNivelEducativo in ( 'SUPERIOR', 'SUPERIOR_PREGRADO') THEN 11
		WHEN pd.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 12
		WHEN pd.pedNivelEducativo = 'NINGUNO' THEN 13
		ELSE 14 
	END [NIVEL_ESCOLARIDAD],
	--Codigo de Ocupacion--
	CASE 
		WHEN pd.pedOcupacionProfesion IS NULL THEN '9629'
		ELSE CONVERT(VARCHAR(4),(op.ocuCodigo))
	END AS [COD_OCUPACION_DANE],
	--Factor de Vulnerabilidad--
	CASE 
		WHEN pd.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN pd.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN pd.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN pd.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN pd.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN pd.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN pd.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN pd.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN pd.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN pd.pedFactorVulnerabilidad IN ('NO_APLICA') THEN 12 
		WHEN pd.pedFactorVulnerabilidad IS NULL THEN 13
		WHEN pd.pedFactorVulnerabilidad IN ('') THEN 13
	END [FACTOR_VULNERABILIDAD],
	--Estado Civi--
	CASE pd.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN '1'
		WHEN 'CASADO' THEN '2'
		WHEN 'DIVORCIADO' THEN '3'
		WHEN 'SEPARADO' THEN '4'
		WHEN 'VIUDO' THEN '5'
		WHEN 'SOLTERO' THEN '6'
	ELSE '' 
	END [ESTADO_CIVIL],
	--Pertenencia étnica--
	CASE pd.pedPertenenciaEtnica 
		WHEN 'AFROCOLOMBIANO' THEN 1
		WHEN 'COMUNIDAD_NEGRA' THEN 2
		WHEN 'INDIGENA' THEN 3
		WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		ELSE 8 
	END [PERTENENCIA_ETNICA],
	--resguardo--
	ISNULL(pd.pedResguardo,2) [RESGUARDO],
	--pueblo indigena--
	ISNULL(pedPuebloIndigena,2) [PUEBLO_INDIGENA],
    --Nombre Pais--
	CASE WHEN pais.paiCodigo IS NULL OR pais.paiCodigo = '' THEN 170 ELSE pais.paiCodigo END [PAIS_RESIDENCIA_BENEFICIARIO],
	--Codigo Municipio--
	munCodigo [COD_MUNICIPIO_DANE],
	--Area Rural--
	CASE WHEN pd.pedResideSectorRural=1 THEN '2' ELSE '1' END  [ARE_GEOGRAFICA_RESIDENCIA],
	--Codigo Municipio Labor--
	munCodigo [COD_MUNICIPIO_LABOR_DANE],
	--Area Geografica Labor--
	CASE 
		WHEN pd.pedResideSectorRural=1 THEN '2' 
		ELSE '1'
	END  [AREA_GEOGRAFICA_LABOR],
	--Salario--
	CASE
		WHEN REPLACE (( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona and sal.apgEmpresa = epr.empId ),'.00000','') IS NULL
		THEN SUBSTRING(REPLACE (ra.roaValorSalarioMesadaIngresos,'.00000',''),0,18)
		ELSE SUBSTRING(REPLACE ( ( select top 1 sal.apdSalarioBasico from #infoSalarios sal where sal.apdPersona = a.afiPersona ),'.00000',''),0,18) 
	END AS [SAL_BASICO],
		----------Tipo Afiliado----------
		CASE 
			WHEN 
				ra.roaTipoAfiliado  = 'TRABAJADOR_DEPENDIENTE' AND 
				ra.roaClaseTrabajador != 'MADRE_COMUNITARIA' AND
				ra.roaClaseTrabajador != 'SERVICIO_DOMESTICO' --AND
				--dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio != 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
				--cat.ctaCategoria != 'C'
			THEN '1'
			WHEN ra.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN ra.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') 
			THEN '4'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				(sa.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' OR sa.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO') 
			THEN '5'
			WHEN ra.roaTipoAfiliado = 'FACULTATIVO' 
			THEN '6'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '7'
			WHEN 
				sa.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND 
				ISNULL(ra.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') 
			THEN '8'
			WHEN 
				ra.roaTipoAfiliado = 'PENSIONADO' AND 
				sa.solClasificacion = 'FIDELIDAD_25_ANIOS' 
			THEN '9'
			WHEN ra.ROATIPOAFILIADO = 'PENSIONADO' AND sa.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' 
			THEN '11'
			WHEN ra.roaClaseIndependiente IN ('TAXISTA') 
			THEN '12'
			--WHEN 
			--	(--cat.ctaCategoria = 'C' AND 
			--		dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO' AND
			--		pr.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') 
			--THEN '13'
			ELSE '1'
		END AS [TIP_AFILIADO],
		--Categoria--
		CASE cta.ctacategoria 
			WHEN 'A' THEN '1'
			WHEN 'B' THEN '2'
			WHEN 'C' THEN '3'
			WHEN 'SIN_CATEGORIA' THEN 4
			ELSE 4
		END  AS [CATEGORIA_CCF],
		--Beneficiario de cuota monetaria--
		CASE 
			WHEN bcm.[BEN_CUOTA_MONETARIA] = 1 THEN '1' ELSE '2' END [BEN_CUOTA_MONETARIA],
		--Actividad económica principal--
		ISNULL(( SELECT ciicodigo FROM codigociiu WHERE ciiid = epr.empCodigoCIIU ),'0000') [ACTIVIDAD_ECONOMICA_PINCIPAL]
FROM PERSONA pa WITH(NOLOCK)
inner  join Afiliado a WITH(NOLOCK) on pa.perId = a.afiPersona
inner  join RolAfiliado ra WITH(NOLOCK) on ra.roaAfiliado = a.afiId and (ra.roaEmpleador IS NULL)
inner  join #SolicitudesAfiliaciones sa WITH(NOLOCK) on ra.roaId = sa.snpRolAfiliado and datediff(day,ra.roaFechaRetiro,@FECHA_FINAL) < @parametroTiempoAdicionalServicios
inner  join #EsadoAfiliadoCalculado eac WITH(NOLOCK) on (eac.roaId = ra.roaId) and (eac.ESTADO_AFILIADO_CALCULADO = 'INACTIVO')
inner  join #CategoriaAfiliado cta WITH(NOLOCK) on cta.ctaAfiliado = a.afiId and cta.ctaClasificacion = ra.roaTipoAfiliado
left   join Empleador epl WITH(NOLOCK) on epl.empId = ra.roaEmpleador
left  join Empresa epr WITH(NOLOCK) on epr.empId = epl.empEmpresa
left  join Persona prl on prl.perId = epr.empRepresentanteLegal
left  join Persona pe WITH(NOLOCK) on pe.perId = epr.empPersona
left  join PersonaDetalle pd on pd.pedPersona = pa.perId
left  join Pais pais on pais.paiId = pd.pedPaisResidencia
left  join Ubicacion ubi on ubi.ubiId = pa.perUbicacionPrincipal
left  join Municipio mun on mun.munId = ubi.ubiMunicipio
left  join OcupacionProfesion op on op.ocuId = pd.pedOcupacionProfesion
left  join #BeneCuotaMonetaria bcm on bcm.afiId = a.afiId and bcm.roaId = ra.roaId	
WHERE ra.roaTipoAfiliado IN('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
--AND pa.perNumeroIdentificacion in ('75091491','75144772','75106629') 

--DECLARE  @FECHA_INICIAL DATE = '2025-03-01', @FECHA_FINAL DATE = '2025-03-31'
DELETE rno.HistoricoAfiliados

INSERT INTO rno.HistoricoAfiliados
select 	cast(dbo.GetLocalDate() as date)	hraFechaHistorico,	
		tr.TIP_IDENTIFICACION_EMPRESA	hraTipoIdentificacionEmpresa,	
		tr.NUM_IDENTIFICACION_EMPRESA	hraNumeroIdentificacion,	
		tr.TIP_IDENTIFICACION_AFILIADO	hraTipoIdentificacionAfiliado,	
		tr.NUM_IDENTIFICACION_AFILIADO	hraNumeroIdentificacionAfiliado,	
		tr.PRI_NOMBRE	hraPerPrimerNombre,
		tr.SEG_NOMBRE	hraPerSegundoNombre,
		tr.PRI_APELLIDO	hraPerPrimerApellido,
		tr.SEG_APELLIDO	hraPerSegundoApellido,
		tr.FEC_NACIMIENTO	hraPedFechaNacimiento,
		tr.GENERO_CCF	hraGenero,
		tr.COD_MUNICIPIO_DANE	hraCodigoMunicipio,
		tr.ARE_GEOGRAFICA_RESIDENCIA	hraAreaGeografica,
		tr.SAL_BASICO	hraSalarioBasico,
		tr.TIP_AFILIADO	hraTipoAfiliado,
		tr.CATEGORIA_CCF	hraCategoria,
		tr.BEN_CUOTA_MONETARIA	hraBeneficiarioCuota,
		@FECHA_INICIAL	hraFechaInicialReporte,
		@FECHA_FINAL	hraFechaFinalReporte,
		tr.ORI_SEXUAL	hraOrientacionSexual,
		tr.NIVEL_ESCOLARIDAD	hraNivelEducativo,
		tr.COD_OCUPACION_DANE	hraOcupacionProfesional,
		tr.FACTOR_VULNERABILIDAD	hraFactorVulnerabilidad,
		tr.ESTADO_CIVIL	hraEstadoCivil,
		tr.PERTENENCIA_ETNICA	hraPertenenciaEtnica,
		tr.PAIS_RESIDENCIA_BENEFICIARIO	hraPaisResidencia,
		tr.RESGUARDO	hraresguardo,
		tr.PUEBLO_INDIGENA	hrapuebloindigena,
		tr.ACTIVIDAD_ECONOMICA_PINCIPAL	hraActividadEconomicaprincipal
from TablaReporteAfiliados tr


DROP TABLE IF EXISTS #SolicitudesAfiliaciones
DROP TABLE IF EXISTS #infoSalarios
DROP TABLE IF EXISTS #CategoriaAfiliado
DROP TABLE IF EXISTS #EsadoAfiliadoCalculado
DROP TABLE IF EXISTS #minimaestro
DROP TABLE IF EXISTS #BeneCuotaMonetaria
DROP TABLE IF EXISTS #tmpReporteAfiliado

	--set @TerminaReporte = 1

	update SolicitudesReportesNormativos 
	set srEstado = 'POR PROCESAR'
	where srId = @idSolicitudReporte

END