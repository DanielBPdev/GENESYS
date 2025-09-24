/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 14/08/2024 9:50:51 a. m. ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 2023-11-17 9:43:44 AM ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 2023-11-17 8:31:18 AM ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 2023-09-05 8:42:03 AM ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 2023-08-31 9:17:28 AM ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 17/05/2023 9:24:05 a. m. ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 13/04/2023 5:39:26 p. m. ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 16/02/2023 3:42:47 p. m. ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 14/12/2022 10:14:46 a. m. ******/
/****** Object:  StoredProcedure [dbo].[ReporteAjustesenlaInformacion]    Script Date: 23/11/2022 9:03:53 a. m. ******/
/*
creado por: olga vega 
Objetivo: reporte normativo 18a 

Ajustes de información en les autoliquidaciones de aportes. Son aquellos solicitados por los aportantes, cotizantes o administradoras, que corresponden a errores en la transcripción de tipos de documentos, periodos, novedades entre otras y que hayan sido objeto de corrección por la respectiva administradora en sus sistemas de información o por la ADRES.
Estos ajustes pueden ocasionarse por indebido diligenciamiento de las planillas PILA.
Pese a que las correcciones se deben hacer por el mismo mecanismo de pago, 
el aportante o cotizante en algunas ocasiones acude directamente a la administradora para ajustar la información sin quedar el registro en la base del Ministerio de Salud y Protección Social.

---EXEC [ReporteAjustesenlaInformacion] '2023-10-01','2023-10-31'
*/

CREATE OR ALTER       PROCEDURE [dbo].[ReporteAjustesenlaInformacion]
(
@fechaInicio DATETIME,
@fechaFin DATETIME
)
AS
BEGIN

	SET NOCOUNT ON;
	SET QUOTED_IDENTIFIER ON;
	SET ANSI_NULLS ON;
 
 

 ----*** auditoria***----


 ---AUDITORIA PARA LOS trabajadores RETIRADOS Y QUE ESTAN ACTIVOS EN ESTE MOMENTO 
DECLARE @sqlnov NVARCHAR(MAX)

  
SET @sqlnov ='	 
	 SELECT REV	, perId,  perTipoIdentificacion, perNumeroIdentificacion
		FROM ( 
	    SELECT  CASE WHEN p.REV = MAX(p.REV) 
				OVER (PARTITION BY perid   
				ORDER BY perid desc) THEN p.REV ELSE NULL END AS  REV,  
			    perId , perTipoIdentificacion, perNumeroIdentificacion
		  FROM persona_aud p  WITH(NOLOCK)
	INNER JOIN SolicitudNovedadPersona_aud  AS solicitudPersonaNovedad  WITH(NOLOCK)
	        ON solicitudPersonaNovedad.snpPersona = p.perId AND snpBeneficiario is null
	INNER JOIN SolicitudNovedad_aud AS solicitudNovedad  WITH(NOLOCK)
	        ON solicitudPersonaNovedad.snpSolicitudNovedad = solicitudNovedad.snoId
	INNER JOIN Solicitud_aud   AS solicitudGlobal  WITH(NOLOCK)
	        ON solicitudGlobal.solId = solicitudNovedad.snoSolicitudGlobal	     
		 WHERE CONVERT(DATE,solicitudGlobal.solFechaRadicacion) BETWEEN @fechaInicio AND @fechaFin
		   AND solicitudNovedad.snoEstadoSolicitud = ''CERRADA''
		   AND p.revtype = 0
		   AND solicitudGlobal.solTipoTransaccion IN (
													 ''CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS'',
													 ''CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD'' ,
													 ''ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB'',
													 ''ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB'',
													 ''ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL''
													 
											        ) 
 
		   ) Audtrab 
	  WHERE Audtrab.REV IS NOT NULL
	 GROUP BY REV,  perId ,perTipoIdentificacion, perNumeroIdentificacion'

 

CREATE TABLE #Trabnov (
	rev VARCHAR(500),
	perid bigint ,	 
	perTipoIdentificacion VARCHAR(20), 
	perNumeroIdentificacion VARCHAR(20),
	shard VARCHAR(500)
 
)
 	 
INSERT INTO #Trabnov (
		rev ,
		perid  ,	 
		perTipoIdentificacion  , 
	   perNumeroIdentificacion ,	 
		shard
	)

EXEC sp_execute_remote N'CoreAudReferenceData',
	@sqlnov,
	N'@fechaInicio DATE, @fechaFin DATE',
	@fechaInicio = @fechaInicio, 
	@fechaFin = @fechaFin
	


 ---**-----------

SELECT 	DISTINCT
CONVERT(VARCHAR(2), CASE WHEN pe.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pe.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pe.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pe.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pe.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pe.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pe.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pe.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pe.perTipoIdentificacion END) as Tipo_documento_aportante,
						pe.pernumeroidentificacion  as Documento_aportante,
						pe.perRazonSocial as [Razon_social],
						CONVERT(VARCHAR(2),left(munCodigo,2)) as Id_departamento,
						CONVERT(VARCHAR(5), munCodigo,5 ) as Id_municipio ,
						CONVERT(VARCHAR(2), CASE WHEN pa.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pa.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pa.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pa.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pa.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pa.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pa.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pa.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pa.perTipoIdentificacion END) Tipo_documento_cotizante,
						CONVERT(VARCHAR(50),pa.perNumeroIdentificacion) AS Documento_cotizante,
						pa.perRazonSocial as Nombre_cotizante,
						
				   CASE WHEN solTipoTransaccion = 'NOVEDAD_REINTEGRO' THEN LEFT(convert(date,roaFechaAfiliacion),7)
						WHEN solTipoTransaccion LIKE '%RETIRO%' THEN isnull(LEFT(convert(date,roaFecharetiro),7),LEFT (convert(date,solFechaRadicacion),7))
						WHEN  (solTipoTransaccion LIKE ('VARIACION%') OR solTipoTransaccion = 'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL') 
						THEN LEFT (convert(date,nopFechaInicio),7)
						ELSE LEFT (convert(date,solFechaRadicacion),7) END  AS [Periodo reportado],

						convert(date,solFechaRadicacion) AS Fecha_Ajuste,
						'' AS Ajuste_T_ID_aportante,
						'' AS Ajuste_numero_aportante,
						'' AS Ajuste_dias,
						'' AS Ajuste_periodo,
						'' AS Ajuste_IBC,
						'' AS [Ajuste cambio administradora],
						
						CASE WHEN solTipoTransaccion = 'NOVEDAD_REINTEGRO' 
						          OR solTipoTransaccion LIKE '%AFILIACION%'
								  OR solTipoTransaccion IN ('ACTIVAR_BENEFICIARIO_PADRE_DEPWEB'	,	 
															'ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL',
															'ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS_DEPWEB') THEN '1'
							 WHEN solTipoTransaccion LIKE '%RETIRO%' THEN '2'
							 WHEN solTipoTransaccion LIKE ('VARIACION_PERMANENTE%') THEN '7'
						     WHEN solTipoTransaccion = 'CORRECCION_APORTES' THEN '8'
							 WHEN solTipoTransaccion LIKE 'VARIACION_TRANSITORIA_SALARIO%'
							                      --    OR  solTipoTransaccion = 'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL' 
												  THEN '9'--cambio 20231117 en reunión con ccf
							 WHEN solTipoTransaccion = 'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL' THEN '10'
							 WHEN solTipoTransaccion = 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL' THEN '11'
							 WHEN solTipoTransaccion = 'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL' THEN '12'						
							 WHEN solTipoTransaccion = 'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL' THEN '13'					 
							 WHEN solTipoTransaccion = 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB' THEN '1'	 
							  WHEN solTipoTransaccion = 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB' THEN '8'
							 ELSE solTipoTransaccion	END				 
 					
						 AS Ajuste_novedad,
						 CASE WHEN solTipoTransaccion IN ( 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL',
							 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
							 'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD' ) THEN 
			 CONVERT(VARCHAR(2), CASE WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'PASAPORTE' THEN 'PA' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'NIT' THEN 'NI' 
						WHEN ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE ISNULL(Audtrab.perTipoIdentificacion,pa.perTipoIdentificacion) END)
					ELSE '' END AS Ajuste_T_ID_Cotizante ,
					 CASE WHEN solTipoTransaccion IN ( 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL',
							 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
							'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD' ) THEN 
					CONVERT(VARCHAR(50),ISNULL(Audtrab.pernumeroIdentificacion,pa.pernumeroIdentificacion)) 
					ELSE '' END AS Ajuste_T_ID_Cotizante---, solNumeroRadicacion

						  FROM Persona pa WITH(NOLOCK)
					INNER JOIN Afiliado  WITH(NOLOCK) ON afipersona =perid 
					INNER JOIN Rolafiliado  WITH(NOLOCK) ON roaafiliado = afiid 
					INNER JOIN Solicitudnovedadpersona  WITH(NOLOCK) ON  snpRolAfiliado  =  roaId
					INNER JOIN Solicitudnovedad  WITH(NOLOCK)ON snoid = snpSolicitudNovedad
					INNER JOIN solicitud  WITH(NOLOCK) ON solid = snoSolicitudGlobal
					 LEFT JOIN NovedadDetalle  WITH(NOLOCK) ON nopSolicitudNovedad= snoId
					INNER JOIN Empleador emp ON emp.empid = roaempleador 
					INNER JOIN empresa e ON emp.empempresa = e.empid 
					INNER JOIN persona pe ON pe.perid = e.emppersona 
					INNER JOIN UbicacionEmpresa ON ubeEmpresa = e.empid and ubeTipoUbicacion= 'UBICACION_PRINCIPAL'
					INNER JOIN Ubicacion ON ubiid = ubeUbicacion
					INNER JOIN Municipio ON munId = ubiMunicipio
					 		 LEFT JOIN #Trabnov Audtrab ON Audtrab.perid= pa.perId  
					WHERE solTipoTransaccion IN (
							'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL',
							'VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL',
							'NOVEDAD_REINTEGRO',
							'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL',
							'VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL',
							'VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL',
							'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL', 
							'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',
							'RETIRO_TRABAJADOR_DEPENDIENTE',
						--	'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL',
							'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL',
							'ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS_DEPWEB',
							'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB',
							'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB',
							'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL'  
						  
					) AND solCanalRecepcion NOT IN ('PILA','PRESENCIAL_INT') 
					AND solResultadoProceso in ( 'APROBADA','CERRADA')
					AND convert(date,solFechaRadicacion) BETWEEN @fechaInicio AND @fechaFin
 UNION ALL
SELECT 
CONVERT(VARCHAR(2), CASE WHEN 	pe.perTipoIdentificacion  = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pe.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pe.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pe.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pe.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pe.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pe.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pe.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pe.perTipoIdentificacion END) AS Tipo_documento_aportante,
						pe.pernumeroidentificacion  as Documento_aportante,
						pe.perRazonSocial as [Razon_social],
						CONVERT(VARCHAR(2),left(munCodigo,2)) as Id_departamento,
						CONVERT(VARCHAR(5), munCodigo,5 ) as Id_municipio ,
						CONVERT(VARCHAR(2), CASE WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'PERM_PROT_TEMPORAL' THEN 'PT' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'PASAPORTE' THEN 'PA' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'NIT' THEN 'NI' 
						WHEN ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE ISNULL(tn.perTipoIdentificacion,pa.perTipoIdentificacion) END) Tipo_documento_cotizante,
						CONVERT(VARCHAR(50),ISNULL(tn.perNumeroIdentificacion,pa.perNumeroIdentificacion)) AS Documento_cotizante,
						pa.perRazonSocial as Nombre_cotizante, 
				CASE WHEN solTipoTransaccion = 'NOVEDAD_REINTEGRO' THEN LEFT(convert(date,roaFechaAfiliacion),7)
						WHEN solTipoTransaccion LIKE '%RETIRO%' THEN isnull(LEFT(convert(date,roaFecharetiro),7),LEFT (convert(date,solFechaRadicacion),7))
						
						ELSE LEFT (convert(date,solFechaRadicacion),7) END  AS [Periodo reportado],

						convert(date,solFechaRadicacion) AS Fecha_Ajuste,
						'' AS Ajuste_T_ID_aportante,
						'' AS Ajuste_numero_aportante,
						'' AS Ajuste_dias,
						'' AS Ajuste_periodo,
						'' AS Ajuste_IBC,
						'' AS [Ajuste cambio administradora],
						
						'8'						
						 AS Ajuste_novedad,
						 CASE WHEN pa.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pa.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pa.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pa.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pa.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' THEN 'PT' 
						WHEN pa.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pa.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pa.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pa.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                     ELSE pa.perTipoIdentificacion  
					 END AS Ajuste_T_ID_Cotizante ,
					  pa.pernumeroIdentificacion 
					 AS Ajuste_T_ID_Cotizante---, solNumeroRadicacion

						  FROM Persona pa WITH(NOLOCK)
					 LEFT JOIN #Trabnov tn ON tn.perid= pa.perId  
					INNER JOIN Afiliado  WITH(NOLOCK) ON afipersona =pa.perid 
					INNER JOIN Rolafiliado  WITH(NOLOCK) ON roaafiliado = afiid 
					INNER JOIN Solicitudnovedadpersona  WITH(NOLOCK) ON  snppersona  =  pa.perid 
					 AND snpBeneficiario IS NULL
					INNER JOIN Solicitudnovedad  WITH(NOLOCK)ON snoid = snpSolicitudNovedad
					INNER JOIN solicitud  WITH(NOLOCK) ON solid = snoSolicitudGlobal
					INNER JOIN Empleador emp ON emp.empid = roaempleador 
					INNER JOIN empresa e ON emp.empempresa = e.empid 
					INNER JOIN persona pe ON pe.perid = e.emppersona 
					INNER JOIN UbicacionEmpresa ON ubeEmpresa = e.empid and ubeTipoUbicacion= 'UBICACION_PRINCIPAL'
					INNER JOIN Ubicacion ON ubiid = ubeUbicacion
					INNER JOIN Municipio ON munId = ubiMunicipio			  
					WHERE solTipoTransaccion IN (
							 'CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS',
							 'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD' ,
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB',
							 'ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL'
					) AND solCanalRecepcion NOT IN ('PILA','PRESENCIAL_INT') 
					AND roaEstadoAfiliado = 'ACTIVO'
					AND solResultadoProceso in ( 'APROBADA','CERRADA')
					AND convert(date,solFechaRadicacion) BETWEEN @fechaInicio AND @fechaFin
 UNION  

				SELECT DISTINCT   
				CONVERT(VARCHAR(2), CASE WHEN pe.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pe.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pe.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pe.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pe.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pe.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pe.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pe.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pe.perTipoIdentificacion END) as Tipo_documento_aportante,
					CONVERT(VARCHAR(50),pe.perNumeroIdentificacion)  as Documento_aportante,
					pe.perRazonSocial as [Razon_social],
					CONVERT(VARCHAR(2),LEFT(munCodigo,2)) as Id_departamento,
					CONVERT(VARCHAR(5), munCodigo)  as Id_municipio ,
					CONVERT(VARCHAR(2), CASE WHEN pa.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pa.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pa.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pa.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pa.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pa.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pa.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pa.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pa.perTipoIdentificacion END) as Tipo_documento_cotizante,
					CONVERT(VARCHAR(50),pa.perNumeroIdentificacion) AS Documento_cotizante,
					pa.perRazonSocial as Nombre_cotizante,
					ISNULL(ag.apgPeriodoAporte,'') AS Periodo_reportado,
					convert(date,agc.apgFechaProcesamiento)  AS Fecha_Ajuste,
					
					case when pec.perTipoIdentificacion <> pe.perTipoIdentificacion then
					CONVERT(VARCHAR(2), CASE WHEN pec.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pec.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pec.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pec.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pec.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pec.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pec.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pec.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pec.perTipoIdentificacion END) else '' end
					AS Ajuste_T_ID_aportante,
					case when pec.perNumeroIdentificacion <> pe.perNumeroIdentificacion
					then pec.perNumeroIdentificacion else
					'' end AS 'Ajuste_numero_aportante',
					ISNULL(CONVERT(VARCHAR,adc.apdDiasCotizados),'') AS Ajuste_dias,
					ISNULL(agc.apgPeriodoAporte,'') AS Ajuste_periodo,
					CONVERT(VARCHAR,adc.apdValorIBC) AS Ajuste_IBC,
					'' AS [Ajuste cambio administradora],
					'8' AS Ajuste_novedad,
					CASE WHEN pa.perTipoIdentificacion <> PAC.perTipoIdentificacion THEN 
					CONVERT(VARCHAR(2), CASE WHEN pac.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
					WHEN pac.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
					WHEN pac.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
					WHEN pac.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
					WHEN pac.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
					WHEN pac.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
					WHEN pac.perTipoIdentificacion = 'NIT' THEN 'NI' 
					WHEN pac.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                ELSE pac.perTipoIdentificacion END)
				ELSE '' END AS Ajuste_T_ID_Cotizante ,
					CASE WHEN pa.perNumeroIdentificacion<>pac.perNumeroIdentificacion 
					THEN
					pac.perNumeroIdentificacion
					ELSE '' END AS Ajuste_N_ID_Cotizante 
					FROM Solicitud  WITH(NOLOCK)
					INNER JOIN SolicitudCorreccionAporte  WITH(NOLOCK) ON solid = scaSolicitudGlobal
					left JOIN AporteGeneral2 ag WITH(NOLOCK) ON ag.apgId = scaAporteGeneral  
					left JOIN Empleador em WITH(NOLOCK) ON em.empEmpresa = ag.apgEmpresa
					left JOIN Empresa e WITH(NOLOCK)  ON ag.apgEmpresa = e.empId
					inner JOIN persona pe WITH(NOLOCK) ON pe.perId = e.emppersona
					left JOIN UbicacionEmpresa ON ubeEmpresa = e.empid and ubeTipoUbicacion= 'UBICACION_PRINCIPAL'
					left JOIN Ubicacion ON ubiid = ubeUbicacion
					left JOIN Municipio ON munId = ubiMunicipio
					left JOIN AporteDetallado2 apd WITH(NOLOCK) ON apd.apdAporteGeneral =  apgid 
					left JOIN persona pa WITH(NOLOCK) ON pa.perid = apdPersona
					left JOIN Afiliado WITH(NOLOCK) ON pa.perid = afipersona 
					left JOIN RolAfiliado WITH(NOLOCK) ON roaAfiliado = afiId and roaEmpleador = em.empid 
					left JOIN Correccion WITH(NOLOCK) ON corAporteDetallado = apd.apdid
					left JOIN AporteGeneral2 agc WITH(NOLOCK) ON agc.apgId = corAporteGeneral 
					 ----PARA LA EMPRESA NUEVA
					LEFT JOIN Empresa ec WITH(NOLOCK)  ON agc.apgEmpresa = ec.empId
					LEFT JOIN persona pec WITH(NOLOCK) ON pec.perId = ec.emppersona
		 		----PARA EL TRABAJADOR 
					
					left JOIN AporteDetallado2 adc  WITH(NOLOCK) ON adc.apdAporteGeneral =  agc.apgid 
					left JOIN persona pac WITH(NOLOCK) ON pac.perid = adc.apdPersona
 
					WHERE Soltipotransaccion IN ('CORRECCION_APORTES' )	
					AND scaResultadoSupervisor = 'APROBADA' 
					AND solCanalRecepcion = 'PRESENCIAL' 
					AND convert(date,agc.apgFechaProcesamiento) BETWEEN @fechaInicio AND @fechaFin
UNION 
					
			SELECT DISTINCT  
				CONVERT(VARCHAR(2), CASE WHEN pa.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pa.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pa.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pa.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pa.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pa.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pa.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pa.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pa.perTipoIdentificacion END) as Tipo_documento_aportante,
					CONVERT(VARCHAR(50),pa.perNumeroIdentificacion)  as Documento_aportante,
					pa.perRazonSocial as [Razon_social],
					CONVERT(VARCHAR(2),LEFT(munCodigo,2)) 
					 as Id_departamento,
					CONVERT(VARCHAR(5), munCodigo) 
					 as Id_municipio ,
					CONVERT(VARCHAR(2), CASE WHEN pa.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN pa.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
						WHEN pa.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
						WHEN pa.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
						WHEN pa.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
						WHEN pa.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
						WHEN pa.perTipoIdentificacion = 'NIT' THEN 'NI' 
						WHEN pa.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                    ELSE pa.perTipoIdentificacion END) as Tipo_documento_cotizante,

					CONVERT(VARCHAR(50),pa.perNumeroIdentificacion) AS Documento_cotizante,
					pa.perRazonSocial as Nombre_cotizante,
					ISNULL(ag.apgPeriodoAporte,'') AS Periodo_reportado,
					convert(date,agc.apgFechaProcesamiento)  AS Fecha_Ajuste,
					CASE WHEN pa.perTipoIdentificacion <> PAC.perTipoIdentificacion THEN 
					CONVERT(VARCHAR(2), CASE WHEN pac.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
					WHEN pac.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
					WHEN pac.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
					WHEN pac.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
					WHEN pac.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
					WHEN pac.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
					WHEN pac.perTipoIdentificacion = 'NIT' THEN 'NI' 
					WHEN pac.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                ELSE pac.perTipoIdentificacion END)
				ELSE '' END AS Ajuste_T_ID_aportante,
					CASE WHEN pa.perNumeroIdentificacion<>pac.perNumeroIdentificacion 
					THEN
					pac.perNumeroIdentificacion
					ELSE '' END as 'Ajuste_numero_aportante',
					ISNULL(CONVERT(VARCHAR,adc.apdDiasCotizados),'') AS Ajuste_dias,
					ISNULL(agc.apgPeriodoAporte,'') AS Ajuste_periodo,
					CONVERT(VARCHAR,adc.apdValorIBC) AS Ajuste_IBC,
					'' AS [Ajuste cambio administradora],
					'8' AS Ajuste_novedad,
					CASE WHEN pa.perTipoIdentificacion <> PAC.perTipoIdentificacion THEN 
					CONVERT(VARCHAR(2), CASE WHEN pac.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
					WHEN pac.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
					WHEN pac.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
					WHEN pac.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
					WHEN pac.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
					WHEN pac.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
					WHEN pac.perTipoIdentificacion = 'NIT' THEN 'NI' 
					WHEN pac.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
                ELSE pac.perTipoIdentificacion END)
				ELSE '' END AS Ajuste_T_ID_Cotizante ,
					CASE WHEN pa.perNumeroIdentificacion<>pac.perNumeroIdentificacion 
					THEN
					pac.perNumeroIdentificacion
					ELSE '' END AS Ajuste_N_ID_Cotizante --, solNumeroRadicacion
					FROM Solicitud  WITH(NOLOCK)
					INNER JOIN SolicitudCorreccionAporte  WITH(NOLOCK) ON solid = scaSolicitudGlobal
					left JOIN AporteGeneral2 ag WITH(NOLOCK) ON ag.apgId = scaAporteGeneral  	
					left JOIN AporteDetallado2 apd WITH(NOLOCK) ON apd.apdAporteGeneral =  apgid 
					inner JOIN persona pa WITH(NOLOCK) ON pa.perid = apdPersona
					left JOIN Ubicacion ON ubiid = pa.perUbicacionPrincipal
					left JOIN Municipio ON munId = ubiMunicipio
					left JOIN Correccion WITH(NOLOCK) ON corAporteDetallado = apd.apdid
					left JOIN AporteGeneral2 agc WITH(NOLOCK) ON agc.apgId = corAporteGeneral 			
					left JOIN AporteDetallado2 adc  WITH(NOLOCK) ON adc.apdAporteGeneral =  agc.apgid 
					left JOIN persona pac WITH(NOLOCK) ON pac.perid = adc.apdPersona
 
					WHERE Soltipotransaccion IN ('CORRECCION_APORTES' )	
					AND scaResultadoSupervisor = 'APROBADA' 
					AND solCanalRecepcion = 'PRESENCIAL' 
					AND convert(date,agc.apgFechaProcesamiento) BETWEEN  @fechaInicio AND @fechaFin
					and scaTipoSolicitante <> 'EMPLEADOR'
			 ORDER BY 2

			  

END;