/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoAsignacionEntregaReintegroFOVIS]    Script Date: 2023-08-14 10:33:42 AM ******/
-- =============================================
-- Author:		 
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte HistoricoDesagregadoCarteraAportante
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 5.
-- Reporte 5---5433 B
---EXEC [SP_ReporteConsolidadoAsignacionPagoReintegroMicrodatoFOVIS] '2023-07-01','2023-08-14'
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[SP_ReporteConsolidadoAsignacionPagoReintegroMicrodatoFOVIS]
(
	@fechaInicial DATE,
	@fechaFinal DATE
)
AS
BEGIN TRY
	SET NOCOUNT ON;
	DECLARE @historico BIT = NULL
	DECLARE @sql NVARCHAR(4000);	


	DROP TABLE IF EXISTS #Comun
		SELECT
			pof.pofId
			,per.perNumeroIdentificacion
			, pof.pofModalidad
			,year(aaf.aafFechaOficio) as 'aafFechaOficioAño' ---cambio 20230321 de aafFechaOficio por aafFechaActaAsignacionFovis
			,month(aaf.aafFechaOficio) as aafFechaOficioMes
			,case
				when pof.pofModalidad like '%URBAN%' then 'URBANO'
				when  pof.pofModalidad like '%RURAL%' then 'RURAL'
				when cia.ciaNombre like '%URBAN%' then 'URBANO'
				when cia.ciaNombre like '%RURAL%' then 'RURAL'
			end as [Sector], pofJefeHogar, jehAfiliado, per.perid as peridjefe, safFechaAceptacion, aafFinVigencia
		into #Comun
		from postulacionFovis as pof
			LEFT JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
			LEFT JOIN ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
			INNER JOIN CicloAsignacion as cia on cia.ciaId = pof.pofCicloAsignacion
			INNER JOIN JefeHogar as jeh on pof.pofJefeHogar = jeh.jehId
			INNER JOIN Afiliado as afi on jeh.jehAfiliado = afi.afiId
			INNER JOIN Persona as per on per.perId = afi.afiPersona
		where pof.pofEstadoHogar <> 'HABIL'

		
		DROP TABLE IF EXISTS #Asignaciones
		SELECT distinct
			com.perNumeroIdentificacion as [Identificación Jefe de Hogar]
			,com.pofModalidad as [Modalidad]
			,com.[aafFechaOficioAño] as 'Año de Asignación del Subsidio (Vigencia)'
			,com.aafFechaOficioMes as [Mes de Asignación del Subsidio (Vigencia)]
			,pof.pofValorAsignadoSFV as [Valor Movimiento]
			,aaf.aafFechaOficio as [Fecha Movimiento]
			,'ASIGNACION' as [Movimiento]
			,com.Sector as [Sector], com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion , com.aafFinVigencia,null as sldFechaOperacion
		   INTO #Asignaciones
		   FROM PostulacionFOVIS as pof
	 INNER JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
	 INNER JOIN Solicitud as sol on saf.safSolicitudGlobal = sol.solId
	  LEFT JOIN ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
     INNER JOIN #Comun as com on com.pofId = pof.pofId
		  WHERE saf.safEstadoSolicitudAsignacion in ('CERRADA')
			AND pofEstadoHogar <> 'HABIL'
		    AND aaf.aafFechaOficio BETWEEN @fechaInicial AND @fechaFinal
		
		--SELECT '#Asignaciones',* FROM #Asignaciones

		DROP TABLE IF EXISTS #Desembolsos
		SELECT 	
			com.perNumeroIdentificacion
			,com.pofModalidad
			,com.[aafFechaOficioAño]
			,com.aafFechaOficioMes
			,lgd.lgdValorDesembolsar
			,isnull(lgd.lgdFechaTransferencia, sol.solFechaRadicacion) as [Fecha Movimiento]
			,'DESEMBOLSO' as movimiento
			,com.Sector, com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion , com.aafFinVigencia,sldFechaOperacion
		INTO #Desembolsos
		FROM PostulacionFOVIS as pof
			INNER JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
			INNER JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
			INNER JOIN Solicitud as sol on sld.sldSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		WHERE sld.sldEstadoSolicitud in ('LEGALIZACION_Y_DESEMBOLSO_CERRADO')
		 AND lgd.lgdFechaTransferencia BETWEEN @fechaInicial AND @fechaFinal

		DROP TABLE IF EXISTS #Reintegros
		SELECT distinct
			com.perNumeroIdentificacion
			,com.pofModalidad
			,com.[aafFechaOficioAño]
			,com.aafFechaOficioMes
			,lgd.lgdMontoDesembolsado
			,sol.solFechaRadicacion as [Fecha Movimiento]
			,'REINTEGRO' as movimiento
			,com.Sector, com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion , com.aafFinVigencia,sldFechaOperacion
	 	into #Reintegros
		from PostulacionFOVIS as pof
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snf.snfSolicitudGlobal = sol.solId
			LEFT JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
			LEFT JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		WHERE snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
			and solTipoTransaccion in 
			('REEMBOLSO_VOLUNTARIO_SUBSIDIO'
			,'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO')
		AND solFechaRadicacion BETWEEN @fechaInicial AND @fechaFinal

		DROP TABLE IF EXISTS #Renuncias
		SELECT distinct
			com.perNumeroIdentificacion
			,com.pofModalidad
			,com.[aafFechaOficioAño]
			,com.aafFechaOficioMes
			,isnull(pof.pofvalorSFVAjustado, pofValorSFVSolicitado) as [Valor Movimiento] 
			,sol.solFechaRadicacion
			,'RENUNCIA' as movimiento
			,com.Sector, com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion , com.aafFinVigencia, null as sldFechaOperacion
		into #Renuncias
		from PostulacionFOVIS as pof
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
			INNER JOIN JefeHogar as jeh on pof.pofJefeHogar = jeh.jehId
			INNER JOIN Afiliado as afi on jeh.jehAfiliado = afi.afiId
			INNER JOIN Persona as per on per.perId = afi.afiPersona
		WHERE sol.solTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
			AND sol.solFechaRadicacion BETWEEN @fechaInicial AND @fechaFinal

		DROP TABLE IF EXISTS #Vencimientos
		SELECT distinct
			com.perNumeroIdentificacion
			,com.pofModalidad
			,com.[aafFechaOficioAño]
			,com.aafFechaOficioMes
			,pof.pofValorAsignadoSFV
			,sol.solFechaRadicacion
			,'VENCIMIENTO' as movimiento
			,com.Sector, com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion  , com.aafFinVigencia, null as sldFechaOperacion
		into #Vencimientos
		from PostulacionFOVIS as pof
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		WHERE  sol.solTipoTransaccion = 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA'
		AND sol.solFechaRadicacion BETWEEN @fechaInicial AND @fechaFinal

		DROP TABLE IF EXISTS #Indexados
		SELECT distinct
			com.perNumeroIdentificacion
			,com.pofModalidad
			,com.[aafFechaOficioAño]
			,com.aafFechaOficioMes
			,ISNULL((ISNULL(pof.pofValorSFVAjustado,0) -ISNULL(pof.pofValorAsignadoSFV,0)),0) as pofValorAsignadoSFV
			,sol.solFechaRadicacion
			,'INDEXADO' as movimiento
			,com.Sector, com.safFechaAceptacion, com.pofId, sol.solTipoTransaccion , com.aafFinVigencia, null as sldFechaOperacion
		into #Indexados
		from PostulacionFOVIS as pof
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		where solTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018'
		  and snfEstadoSolicitud IN ('NOV_FOVIS_CERRADA','NOV_FOVIS_APROBADA')
		  and pofValorSFVAjustado>0
		  AND sol.solFechaRadicacion BETWEEN @fechaInicial AND @fechaFinal


		DROP TABLE IF EXISTS #Final
		SELECT *
		into #Final
		from #Asignaciones as a
		union all
		SELECT * from #Desembolsos
		union all
		SELECT * from #Reintegros
		union all
		SELECT * from #Renuncias
		union all
		SELECT * from #Vencimientos
		union all
		SELECT * from #Indexados



	---	SELECT * from #Final

-------***-----------------------***************------------
	 --INICIO REPORTE
-------***-----------------------***************------------	


		SELECT DISTINCT 
			---------- Tipo de Identificacion del Aportante ----------
			CASE PERJEFE.perTipoIdentificacion 
				WHEN 'CEDULA_CIUDADANIA' THEN '1'
				WHEN 'TARJETA_IDENTIDAD' THEN '2'
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7'
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
				ELSE  PERJEFE.perTipoIdentificacion 

			END AS [tipoIdentificacionJefe],	
			---------- Numero de Identificacion Aportante  ----------
			PERJEFE.perNumeroIdentificacion AS numeroIdentificacion,		
			---------- Componente Hogar Objeto. ----------
			case 
				when inh.inhid is null then '1'
				else '2'
			end as componenteHogar,
			----------  Tipo Identificacion Integrante del Hogar. ----------
			CASE
				WHEN INTEGRANTES.TIPOINTEGRANTE <> 'PRINCIPAL'
				THEN 
					CASE PERINTE.perTipoIdentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'
						WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
						ELSE PERINTE.perTipoIdentificacion
					END 
				ELSE
					CASE WHEN INTEGRANTES.TIPOINTEGRANTE = 'PRINCIPAL' 
					    THEN  CASE   PERJEFE.perTipoIdentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'
						WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
						END
			          ELSE PERJEFE.perTipoIdentificacion
					 
				 END
			END as [tipoIdIntegrante],	
			---------- Numero de Identificacion Integrante del Hogar. ----------
			CASE
				WHEN INTEGRANTES.TIPOINTEGRANTE <> 'PRINCIPAL'  
				THEN PERINTE.perNumeroIdentificacion
				ELSE 
				CASE WHEN INTEGRANTES.TIPOINTEGRANTE = 'PRINCIPAL'  	
				THEN PERJEFE.perNumeroIdentificacion
				END
			END as numIdIntegrante,	
			---------- Titular Subsidio ----------
			CASE 
				WHEN PERINTE.perNumeroIdentificacion = PERJEFE.perNumeroIdentificacion --OR integrante.numDocumentoIntegrante IS NULL
					THEN 1 
				ELSE 2 
		    END AS afiliadoACaja,		
			PERINTE.perPrimerNombre AS primerNombre,
 
							PERINTE.perSegundoNombre AS segundoNombre,
			
		 
							PERINTE.perPrimerApellido AS primerApellido,
		 
							PERINTE.perSegundoApellido AS segundoApellido,
			
							CASE 
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('HIJO_BIOLOGICO_HOGAR', 'HIJO_BIOLOGICO') THEN '1 '
								WHEN INTEGRANTES.TIPOINTEGRANTE IN (
									'PADRE_HOGAR',
									'MADRE_HOGAR',
									'PADRE_MADRE_ADOPTANTE_HOGAR', 
									'MADRE', 
									'PADRE') THEN '2'
								WHEN INTEGRANTES.TIPOINTEGRANTE = 'HERMANO_HOGAR' THEN '3'
								WHEN INTEGRANTES.TIPOINTEGRANTE = 'HIJASTRO_HOGAR' THEN '4'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('CONYUGE', 'CONYUGE_HOGAR') THEN '5'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('ABUELO', 'ABUELO_HOGAR', 'ABUELA', 'ABUELA_HOGAR') THEN '7'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('NIETO', 'NIETO_HOGAR', 'NIETA', 'NIETA_HOGAR') THEN '8'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('TIO', 'TIO_HOGAR', 'TIA', 'TIA_HOGAR') THEN '9'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('SOBRINO', 'SOBRINA', 'SOBRINO_HOGAR', 'SOBRINA_HOGAR') THEN '10'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('BISABUELO','BISABUELO_HOGAR', 'BISABUELA', 'BISABUELA_HOGAR') THEN '11'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('BISNIETO','BISNIETO_HOGAR', 'BISNIETA', 'BISNIETA_HOGAR') THEN '12'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('SUEGRO', 'SUEGRA', 'SUEGRO_HOGAR', 'SUEGRA_HOGAR') THEN '13'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('CUÑADO', 'CUÑADA', 'CUÑADO_HOGAR', 'CUÑADA_HOGAR') THEN '14'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('NUERA', 'NUERA_HOGAR') THEN '15'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('YERNO', 'YERNO_HOGAR') THEN '16'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PADRE_ADOPTIVO') THEN '17'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('HIJO_ADOPTIVO_HOGAR') THEN '18'
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL') THEN '19'
								ELSE  '6'
							END AS parentezcoIntegrante,

							CASE
				
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
								THEN  ISNULL( INTEGRANTES.SALARIOINTEGRANTE, 0 ) 
								ELSE ISNULL( INTEGRANTES.SALARIOINTEGRANTE, 0 ) 
							END AS ingresosIntegrante,

			
							CASE
								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) IS NULL OR INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL') --integrante.jefeHogar IS NULL
								 AND  final.modalidad IN (
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 1

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 0 AND 1
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 2

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 1 AND 1.5
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 3

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 1.5 AND 2
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 4

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 2 AND 2.25
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 5

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 2.25 AND 2.5
							     AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 6

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 2.5 AND 2.75
																AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 7

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 2.75 AND 3
																AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 8

								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) 
								BETWEEN 3 AND 3.5
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA'
										) 
								THEN 9
					
								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) 
								BETWEEN 3.5 AND 4
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_USADA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_RURAL' 										
										) 
								THEN 10
 
					
								WHEN ROUND(CONVERT(FLOAT,pof.pofSalarioAsignacion)/prm.plsSMLMV,2) BETWEEN 0.0 AND 2
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_NUEVA_RURAL',
											'CONSTRUCCION_SITIO_PROPIO_URBANO',
											'MEJORAMIENTO_VIVIENDA_URBANA',
											'MEJORAMIENTO_VIVIENDA_SALUDABLE',
											'MEJORAMIENTO_VIVIENDA_RURAL')
								THEN 15
					
								WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.plsSMLMV,2) BETWEEN 2 AND 4
								AND  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_NUEVA_RURAL',
											'CONSTRUCCION_SITIO_PROPIO_URBANO',
											'MEJORAMIENTO_VIVIENDA_URBANA',
											'MEJORAMIENTO_VIVIENDA_SALUDABLE',
											'MEJORAMIENTO_VIVIENDA_RURAL')
								THEN 16

								ELSE
									CASE 
										WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.plsSMLMV,2) > 4
										THEN 10
									END

							END AS nivelIngreso,
				CASE 
								WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
								THEN
									CASE
										WHEN  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_URBANA',
											'ADQUISICION_VIVIENDA_USADA_URBANA',
											'CONSTRUCCION_SITIO_PROPIO_URBANO',
											'MEJORAMIENTO_VIVIENDA_URBANA',
											'MEJORAMIENTO_VIVIENDA_SALUDABLE'
										) THEN 1

										WHEN  final.modalidad IN(
											'ADQUISICION_VIVIENDA_NUEVA_RURAL',
											'ADQUISICION_VIVIENDA_USADA_RURAL',
											'CONSTRUCCION_SITIO_PROPIO_RURAL',
											'MEJORAMIENTO_VIVIENDA_RURAL'
										)THEN 2

										--WHEN integrante.numIdIntegrante = per.perNumeroIdentificacion
									END
								ELSE 4
							END AS componente,
	
							--ISNULL(YEAR(SAF.safFechaAceptacion), YEAR(@FECHA_INICIAL)) AS anioVigencia,
							ISNULL(YEAR(safFechaAceptacion), YEAR(@fechaInicial)) AS anioVigencia,
							--pof.pofestadoHogar,
					 
					        FINAL.[Valor Movimiento]
							 --ISNULL(ISNULL(POF.pofValorAjusteIPCSFV,pof.pofValorAsignadoSFV),pof.pofValorSFVSolicitado)   --AS valorSubsidio
							
							AS valorSubsidio, 
							CASE
								WHEN Movimiento = 'ASIGNACION'
								THEN '1'

								WHEN   Movimiento = 'RENUNCIA'
								THEN '2'
								
								WHEN Movimiento = 'VENCIMIENTO' 
								THEN '3'

								WHEN  Movimiento = 'REINTEGRO'
								THEN '4'

								WHEN  Movimiento = 'INDEXADO'
								THEN '5'

								WHEN Movimiento = 'DESEMBOLSO'
								THEN '6'
  
							END   
							AS valor, 
							pof.pofEstadoHogar,
						 
			   POF.pofId , final.Modalidad  , TIPOINTEGRANTE, final.Movimiento
			 
			   
				INTO #TABLE_DATA_SUBSIDIO_TEMP	
			   ------CONSULTAS
			   FROM PostulacionFOVIS pof with(nolock)
          
			 INNER JOIN #Final final ON final.pofid = pof.pofId  

					---cambio 20230227 se adiciona tabla parametrica para los salarios minimos de cada año
			 LEFT JOIN ParametrizacionLiquidacionSubsidio PRM 
						ON YEAR(safFechaAceptacion) = prm.plsAnioVigenciaParametrizacion   
   
 	
			INNER JOIN JefeHogar JEHJEFE ON (POF.pofJefeHogar = JEHJEFE.jehId)
			INNER JOIN Afiliado AFIJEFE ON (JEHJEFE.jehAfiliado = AFIJEFE.afiId)
			INNER JOIN Persona PERJEFE ON (AFIJEFE.afiPersona = PERJEFE.perId)
			LEFT JOIN IntegranteHogar as inh on inh.inhPostulacionFovis = pof.pofId
			-----------------------------------------------------------------------
			INNER JOIN ( 
				SELECT 
					INH.inhPostulacionFovis POFID,
					INH.inhTipoIntegrante TIPOINTEGRANTE,
					INH.inhPersona IDPERSONA,
					INH.inhSalarioMensual SALARIOINTEGRANTE
				FROM 
					IntegranteHogar INH
						

				----------
				UNION 
				----------

				SELECT 
					POFJEH.pofId,
					'PRINCIPAL',
					AFIJEH.afiPersona,
					JEH.jehIngresoMensual

				FROM 
					PostulacionFOVIS POFJEH
					INNER JOIN JefeHogar JEH ON JEH.jehId = POFJEH.pofJefeHogar
					INNER JOIN Afiliado AFIJEH ON AFIJEH.afiId = JEH.jehAfiliado
			)AS INTEGRANTES ON INTEGRANTES.POFID = POF.pofId
			-----------------------------------------------------------------------

			INNER JOIN Persona PERINTE ON PERINTE.perId = INTEGRANTES.IDPERSONA

			-----------------------------------------------------------------------
			----------INICIO SALARIO
			LEFT JOIN (
				SELECT 
					ingresoHogar.jehId AS jefeHogar, 
					SUM(ingresoHogar.ingreso) AS ingresos
				FROM (
	
					SELECT 
						jeh.jehId, 
						CASE 
							WHEN jeh.jehIngresoMensual IS NULL THEN ing.ingresoAfiliado
							ELSE jeh.jehIngresoMensual 
						END AS ingreso
					FROM 
						JefeHogar jeh
						JOIN (
							SELECT 
								afi.afiId, 
								SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
							FROM 
								Afiliado afi 
								JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
							WHERE 
								roa.roaEstadoAfiliado = 'ACTIVO'
						GROUP BY 
							afi.afiId
						) AS ing ON  (jeh.jehAfiliado = ing.afiId)
 
	
				) AS ingresoHogar 
				GROUP BY ingresoHogar.jehId
	
			) AS salario ON (salario.jefeHogar = POF.pofJefeHogar)
			----------FIN SALARIO
			-----------------------------------------------------------------------
 	------------Se obtiene informacion del estado-------
		
		 
			 

 	 

--		-----------------------------------------------------------------------------------------------------------------
--		/*-------------------------------------------------------------------------------------------------------------*/
--		-----------------------------------------------------------------------------------------------------------------
	 
	 	DELETE rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS 
		
			   
		IF exists (SELECT * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'niving')
		     delete from dbo.niving


		IF exists (SELECT * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'niving2')
			delete from dbo.niving2

		
		insert dbo.niving (pofmodalidad,nivelingreso,numeroIdentificacion,pofId,ingresosintegrante,numIdIntegrante)
				SELECT DISTINCT pofmodalidad	
				    ,0 AS nivelingreso, 
					 XX.numeroIdentificacion, 
					 XX.pofId,
					 ingresosIntegrante AS ingresosintegrante,   XX.numIdIntegrante
			 FROM #TABLE_DATA_SUBSIDIO_TEMP XX
			INNER JOIN postulacionfovis p on p.pofid = xx.pofid 
		 

		 insert dbo.niving2 (pofmodalidad,nivelingreso,numeroIdentificacion,pofId,ingresosintegrante)
				 SELECT   pofmodalidad	
				    ,0 AS nivelingreso, 
					 XX.numeroIdentificacion, 
					 XX.pofId,
					 sum(ingresosIntegrante) AS ingresosintegrante 
			 FROM niving XX
			 GROUP BY XX.numeroIdentificacion, XX.pofId
					,pofmodalidad 


 

			 UPDATE  X  
			 SET X.nivelIngreso=  
			 CASE WHEN   ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) IS NULL  --integrante.jefeHogar IS NULL
				  THEN 1

				 WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 0 AND 1
				 THEN 2

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 1 AND 1.50
								THEN 3

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 1.51 AND 2
								THEN 4

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 2.01 AND 2.25
								THEN 5

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 2.26 AND 2.50
								THEN 6

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 2.51 AND 2.75
								THEN 7

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 2.76 AND 3.00
								THEN 8

								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 3.01 AND 3.50
								THEN 9
					
								WHEN x.pofmodalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 3.51 AND 4
								THEN 10

					
								WHEN x.pofmodalidad NOT IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 0.0 AND 2.0
								THEN 15
					
								WHEN x.pofmodalidad NOT IN ('ADQUISICION_VIVIENDA_USADA_URBANA','ADQUISICION_VIVIENDA_USADA_RURAL') 
				       AND ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) BETWEEN 2.01 AND 4
								THEN 16

								ELSE
									CASE 
										WHEN ROUND(CONVERT(FLOAT, (x.ingresosIntegrante))/prm.prmValor,2) > 4
										THEN 10
									END
					 END 
			 FROM  niving2 x 
	    LEFT JOIN Parametro PRM 
		       ON (PRM.prmNombre = 'SMMLV')
		


	 	 UPDATE  final  
			 SET final.nivelIngreso=  X.nivelingreso  
			 FROM #TABLE_DATA_SUBSIDIO_TEMP Final			 
       INNER JOIN niving2 x 
	           ON x.numeroIdentificacion = final.numeroIdentificacion AND x.pofId = final.pofId
	    LEFT JOIN Parametro PRM 
		       ON (PRM.prmNombre = 'SMMLV')
			    
 

   SELECT *,
		  	--CASE valor 
					--					WHEN 2 THEN 5
					--					WHEN 3 THEN 6
					--					WHEN 4 THEN 6
					--					WHEN 5 THEN 2
					--					WHEN 6 THEN 3
					--					WHEN 7 THEN 4
					--					ELSE 1 end
							 
							Valor   AS estadoSubsidio,
								CASE
								WHEN  TIPOINTEGRANTE IN ('PRINCIPAL')
								THEN
									CASE
										WHEN  Modalidad IN (
											'ADQUISICION_VIVIENDA_NUEVA_URBANA', 					
											'ADQUISICION_VIVIENDA_NUEVA_RURAL'
										)THEN 1

										WHEN   Modalidad IN (
											'ADQUISICION_VIVIENDA_USADA_URBANA', 					
											'ADQUISICION_VIVIENDA_USADA_RURAL'
										)THEN 2

										WHEN   Modalidad IN (
											'CONSTRUCCION_SITIO_PROPIO_URBANO', 					
											'CONSTRUCCION_SITIO_PROPIO_RURAL'
										)THEN 3

										WHEN   Modalidad IN (
											'MEJORAMIENTO_VIVIENDA_URBANA', 					
											'MEJORAMIENTO_VIVIENDA_RURAL'
										)THEN 4

										WHEN   Modalidad IN (
											'MEJORAMIENTO_VIVIENDA_SALUDABLE'
										)THEN 7
									END
								ELSE 8
							END AS tipoPlanVivienda 
				INTO #TABLE_DATA_SUBSIDIO_TEMP_II
				FROM #TABLE_DATA_SUBSIDIO_TEMP

				---SELECT 'FROM #TABLE_DATA_SUBSIDIO_TEMP',*   FROM #TABLE_DATA_SUBSIDIO_TEMP
 
	 INSERT INTO rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS 
		 
		SELECT GETDATE() as FechaReporte,
			---------- Tipo de Identificacion del Aportante ----------
			consolidado_end.tipoIdentificacionJefe AS [Tipo de Identificacion],	
			---------- Numero de Identificacion Aportante  ----------
			consolidado_end.numeroIdentificacion AS [Numero de Identificacion],		
			---------- Componente Hogar Objeto. ----------
			consolidado_end.componenteHogar AS [Componente del hogar objeto],		
			----------  Tipo Identificacion Integrante del Hogar. ----------
			consolidado_end.tipoIdIntegrante as [Tipo de Identificacion de los integrantes del hogar],	
			---------- Numero de Identificacion Integrante del Hogar. ----------
			consolidado_end.numIdIntegrante AS [Numero de Identificacion de los integrantes del hogar],
			---------- Titular Subsidio ----------
			consolidado_end.afiliadoACaja AS [Titular del subsidio],			
			---------- Primer Nombre de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.primerNombre IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.primerNombre 
			END AS [Primer Nombre],
	
			---------- Segundo Nombre de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.segundoNombre IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.segundoNombre
			END AS [Segundo Nombre],
	
			---------- Primer Apellido de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.primerApellido IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.primerApellido
			END AS [Primer Apellido],
	
			---------- Segundo Apellido de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.segundoApellido IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.segundoApellido
			END AS [Segundo Apellido],
		
			
			---------- Parentezco con el Titular. ----------	
			consolidado_end.parentezcoIntegrante AS [Parentesco de la persona con el titular],	
			---------- Ingresos Integrante. ----------
			consolidado_end.ingresosIntegrante AS [Ingreso del integrante del hogar],		
			---------- Nivel de Ingreso. ----------
			consolidado_end.nivelIngreso AS [Nivel de ingreso],	
			---------- Componente. ----------
			consolidado_end.componente AS [Componente],	
			---------- A;o asignacion. ----------
			consolidado_end.anioVigencia AS [Ano de asignacion],	
			---------- Estado Subsidio. ----------
			consolidado_end.estadoSubsidio AS [Estado del subsidio],	
			---------- Valor Subsidio. ----------
			SUM(consolidado_end.valorSubsidio) AS [Valor del subsidio],
			----------Codigo Tipo Plan de Vivienda----------
			consolidado_end.tipoPlanVivienda AS [Codigo tipo plan de vivienda],
			----------Fuente de Financiamiento----------
			consolidado_end.fuentefinanciamiento AS [Fuente de financiamiento]
			--consolidado_end.pofEstadoHogar
			,@fechaInicial AS Fechainicial
			,@fechaFinal as FechaFinal
			,consolidado_end.pofId
	 
		FROM(

			SELECT 
				---------- Tipo de Identificacion del Aportante ----------
				consolidado.tipoIdentificacionJefe,	
				---------- Numero de Identificacion Aportante  ----------
				consolidado.numeroIdentificacion,	
				---------- Componente Hogar Objeto. ----------
				consolidado.componenteHogar,		
				----------  Tipo Identificacion Integrante del Hogar. ----------
				consolidado.tipoIdIntegrante,	
				---------- Numero de Identificacion Integrante del Hogar. ----------
				consolidado.numIdIntegrante,
				---------- Titular Subsidio ----------
				consolidado.afiliadoACaja,		
				---------- Primer Nombre de la persona reportada. ----------
				consolidado.primerNombre,	
				---------- Segundo Nombre de la persona reportada. ----------
				consolidado.segundoNombre,	
				---------- Primer Apellido de la persona reportada. ----------
				consolidado.primerApellido,	
				---------- Segundo Apellido de la persona reportada. ----------
				consolidado.segundoApellido,		
				---------- Parentezco con el Titular. ----------	
				consolidado.parentezcoIntegrante,
				---------- Ingresos Integrante. ----------
				consolidado.ingresosIntegrante,		
				---------- Nivel de Ingreso. ----------
				consolidado.nivelIngreso,	
				---------- Componente. ----------
				consolidado.componente,	
				---------- A;o asignacion. ----------
				consolidado.anioVigencia,	
				---------- Estado Subsidio. ----------
				consolidado.estadoSubsidio,	
				---------- Valor Subsidio. ----------
				CASE WHEN consolidado.afiliadoACaja = 1 THEN SUM(consolidado.valorSubsidio) ELSE 0 END AS valorSubsidio,
				----------Codigo Tipo Plan de Vivienda----------
				consolidado.tipoPlanVivienda,
				----------Fuente de Financiamiento----------
			 
				 CASE
				WHEN consolidado.componenteHogar = '2'
				THEN '8'
				ELSE 
					'7'
			    END as fuentefinanciamiento,
				consolidado.pofEstadoHogar, 
				consolidado.pofId
			FROM 
				#TABLE_DATA_SUBSIDIO_TEMP_II AS consolidado
				 
			WHERE 
				consolidado.pofEstadoHogar not in ('RECHAZADO')
			group by
					consolidado.nivelIngreso,
					consolidado.componente,
					consolidado.estadoSubsidio,
					consolidado.anioVigencia,
					consolidado.tipoIdentificacionJefe,
					consolidado.numeroIdentificacion,
					consolidado.componenteHogar,
					consolidado.tipoIdIntegrante,
					consolidado.numIdIntegrante,
					consolidado.afiliadoACaja,
					consolidado.primerNombre,
					consolidado.segundoNombre,
					consolidado.primerApellido,
					consolidado.segundoApellido,
					consolidado.parentezcoIntegrante,
					consolidado.ingresosIntegrante,
					consolidado.tipoPlanVivienda,
					consolidado.pofId,
					--consolidado.valorSubsidio,
					--consolidado.pofJsonPostulacion,
					consolidado.tipoIdentificacionJefe,
					consolidado.pofEstadoHogar 
					, consolidado.Movimiento
			 
			)AS consolidado_end

		GROUP BY
			consolidado_end.nivelIngreso,
			consolidado_end.componente,
			consolidado_end.estadoSubsidio,
			consolidado_end.anioVigencia,
			consolidado_end.tipoIdentificacionJefe,
			consolidado_end.numeroIdentificacion,
			consolidado_end.componenteHogar,
			consolidado_end.tipoIdIntegrante,
			consolidado_end.numIdIntegrante,
			consolidado_end.afiliadoACaja,
			consolidado_end.primerNombre,
			consolidado_end.segundoNombre,
			consolidado_end.primerApellido,
			consolidado_end.segundoApellido,
			consolidado_end.parentezcoIntegrante,
			consolidado_end.ingresosIntegrante,
			consolidado_end.tipoPlanVivienda,
			--consolidado_end.valorSubsidio,
			consolidado_end.tipoIdentificacionJefe,
			consolidado_end.fuentefinanciamiento,
			consolidado_end.pofEstadoHogar,
			consolidado_end.pofId

 
		
					 
-----SHOW REPORT
--ALTER TABLE rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS ADD hamCodigoTipoPlanVivienda int, hamFuenteFinanciamiento int
			SELECT DISTINCT 
					hamTipoIdentificacion AS 'Tipo de Identificacion',
					hamNumeroIdentificacion AS 'Numero de Identificacion',	
					hamComponenteHogar AS 'Componente del hogar objeto',	
					hamTipoIdIntegrante AS 'Tipo de Identificacion de los integrantes del hogar', 
					hamNumeroIdIntegrante AS 'Numero de Identificacion de los integrantes del hogar',
					hamAfiliadoACaja AS 'Titular del subsidio',	
					hamPrimerNombre AS 'Primer Nombre',	
					hamSegundoNombre AS 'Segundo Nombre',	
					hamPrimerApellido AS 'Primer Apellido',	
					hamSegundoApellido AS 'Segundo Apellido',	
					CONVERT(BIGINT,hamParentezcoIntegrante) AS 'Parentesco de la persona con el titular',	
					REPLACE(hamIngresosIntegrante,'.00000','') AS 'Ingreso del integrante del hogar',
		
					hamNivelIngreso AS 'Nivel de ingreso',	
					hamComponente AS 'Componente',
					hamAnioVigenciaAsignacionSubsidio AS 'Ano de asignacion',	
					hamEstadoSubsidio AS 'Estado del subsidio',
					hamValorSubsidios AS 'Valor del subsidio',
					hamCodigoTipoPlanVivienda AS 'Codigo tipo plan de vivienda',	
					hamFuenteFinanciamiento AS 'Fuente de financiamiento'
			   FROM rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS
		      WHERE hamFechaInicialReporte >= @fechaInicial 
		        AND hamFechaFinalReporte <= @fechaFinal
			GROUP BY  hamTipoIdentificacion  ,
					hamNumeroIdentificacion ,	
					hamComponenteHogar  ,	
					hamTipoIdIntegrante  , 
					hamNumeroIdIntegrante  ,
					hamAfiliadoACaja  ,	
					hamPrimerNombre ,	
					hamSegundoNombre  ,	
					hamPrimerApellido  ,	
					hamSegundoApellido  ,	
					hamParentezcoIntegrante ,	
					hamIngresosIntegrante ,
					hamNivelIngreso ,	
					hamComponente  ,
					hamAnioVigenciaAsignacionSubsidio  ,	
					hamEstadoSubsidio  ,
					hamValorSubsidios ,
					hamCodigoTipoPlanVivienda  ,	
					hamFuenteFinanciamiento 
				ORDER BY 2 ASC , 11  DESC

  


END TRY
BEGIN CATCH
	THROW;
END CATCH
;